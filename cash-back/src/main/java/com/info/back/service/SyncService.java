package com.info.back.service;

import com.info.back.dao.ICreditLoanPayDao;
import com.info.back.dao.ILocalDataDao;
import com.info.constant.Constant;
import com.info.vo.bigAmount.Loan;
import com.info.vo.bigAmount.Repayment;
import com.info.vo.bigAmount.RepaymentDetail;
import com.info.web.pojo.CreditLoanPay;
import com.info.web.pojo.CreditLoanPayDetail;
import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.pojo.MmanUserLoan;
import com.info.web.synchronization.dao.IDataDao;
import com.info.web.synchronization.syncUtils;
import com.info.web.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.event.EventListenerSupport;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2017/11/17 0017下午 02:36
 */
@Service
public class SyncService implements ISyncService {
    private static Logger logger = Logger.getLogger(SyncService.class);
    @Autowired
    private IDataDao dataDao;
    @Autowired
    private ILocalDataDao localDataDao;
    @Autowired
    private ICreditLoanPayDao creditLoanPayDao;
    @Autowired
    private MmanLoanCollectionOrderService orderService;
    @Autowired
    private CreditLoanPayService creditLoanPayService;

    @Override
    public void handleOverdue(Repayment repayment, Loan loan,List<RepaymentDetail> repaymentDetails) {

        String payId = repayment.getId();
        String userId = loan.getUserId();
        String loanId = loan.getId();
        CreditLoanPay creditLoanPay1 = creditLoanPayDao.get(payId);
        if (creditLoanPay1 == null){
            logger.info("accept_overdue_order_begin=" + loanId);
            HashMap<String,String> map = new HashMap<>();
            map.put("USER_ID", loan.getUserId());
            logger.info("get_userInfo=" + userId);
            HashMap<String, Object> userInfo = dataDao.getUserInfo(map);
            logger.info("get_userCardInfo=" + userId);
            HashMap<String, Object> cardInfo = dataDao.getUserCardInfo(map);	//银行卡--app端
            logger.info("get_userContacts=" + userId);
            List<HashMap<String, Object>> userContactsList = dataDao.getUserContacts(map);	//用户联系人--app端

            //保存借款表-逾期同步
            MmanUserLoan mmanUserLoan = new MmanUserLoan();
            mmanUserLoan.setId(loanId);
            mmanUserLoan.setBorrowingType(Constant.BIG);
            mmanUserLoan.setTermNumber(loan.getTermNumber());
            mmanUserLoan.setUserId(loan.getUserId());
            mmanUserLoan.setLoanMoney(new BigDecimal(Integer.parseInt(loan.getLoanMoney())/100.00));
            mmanUserLoan.setLoanRate(String.valueOf(loan.getLoanRate()));
            mmanUserLoan.setPaidMoney(new BigDecimal(Integer.parseInt(loan.getPaidMoney())/100.00));//服务费+本金
            mmanUserLoan.setLoanPenalty(new BigDecimal(Integer.parseInt(loan.getLoanPenalty())/100.00));
            mmanUserLoan.setServiceCharge(new BigDecimal(Integer.parseInt(loan.getServiceCharge())/100.00));//服务费
            mmanUserLoan.setLoanPenaltyRate(loan.getLoanPenaltyRate());
            mmanUserLoan.setAccrual(new BigDecimal(Integer.parseInt(loan.getAccrual())/100.00));//利息
            mmanUserLoan.setLoanStartTime(DateUtil.getDateTimeFormat(loan.getLoanStartTime(), "yyyy-MM-dd"));
            mmanUserLoan.setLoanEndTime(DateUtil.getDateTimeFormat(loan.getLoanEndTime(), "yyyy-MM-dd"));
            mmanUserLoan.setUpdateTime(new Date());
            mmanUserLoan.setLoanStatus(Constant.STATUS_OVERDUE_FOUR);//4：逾期
            mmanUserLoan.setCreateTime(new Date());
            mmanUserLoan.setDelFlag("0");//0正常1：删除
            mmanUserLoan.setMerchantNo(loan.getMerchantNo());
            mmanUserLoan.setLoanPyId(loanId);
            // 标识新老用户 0 新用户  1 老用户
            if(userInfo == null){
                mmanUserLoan.setCustomerType(0);
            }else {
                mmanUserLoan.setCustomerType(Integer.valueOf(userInfo.get("customer_type") == null ? "0" : userInfo.get("customer_type").toString()));
            }
            localDataDao.saveMmanUserLoan(mmanUserLoan);

            // 保存还款表-逾期同步
            CreditLoanPay creditLoanPay = handleRCreditLoanPay(repayment,loanId,loan,null);
            localDataDao.saveCreditLoanPay(creditLoanPay);//保存还款表

            //保存用户信息表--联系人表--银行卡
            syncUtils.saveUserInfo(localDataDao,payId,userId,userInfo,userContactsList,cardInfo);

            //派单
//            taskJobMiddleService.dispatchforLoanId(loanId,userInfo.get("id_number").toString(),Constant.BIG);
            orderService.dispatchOrderNew(loanId,userInfo.get("id_number").toString(),Constant.BIG);
            //如果还款详情不为空则保存还款详情
            if(null!=repaymentDetails && 0<repaymentDetails.size()){
                HashMap<String,String> reMap = new HashMap<String,String>();
                reMap.put("PAY_ID", payId);
                List<String> idList = localDataDao.selectCreditLoanPayDetail(reMap);//查询目前插入的还款记录
                for(RepaymentDetail detail : repaymentDetails) {
                    if (syncUtils.checkDetailId(idList, detail.getId())){ //判断该详情是否存在，如果不存在则保存
                        detail.setRemark("大额未逾期部分还款");
                        CreditLoanPayDetail repaymentDetail = handleRepaymentDetail(detail,payId,loanId);
                        localDataDao.saveCreditLoanPayDetail(repaymentDetail);
                    }
                }
                //更新订单表已还金额
                HashMap<String,Object> repaymentMap = new HashMap<>();
                repaymentMap.put("user_id",userId);
                repaymentMap.put("repaymented_amount",repayment.getRealMoney());
                syncUtils.updateMmanLoanCollectionOrder(localDataDao,loanId,repaymentMap,Constant.STATUS_OVERDUE_ONE);
            }
        }else {
            //每日更新

            if(loan.getOverdueDays() > 1){
                logger.info("accept_overdue_order_update=" + loanId);
                updateOverdue(repayment,loan);
            }
        }

    }

    private CreditLoanPayDetail handleRepaymentDetail(RepaymentDetail repaymentDetail,String payId,String loanId) {
        CreditLoanPayDetail creditLoanPayDetail  = new CreditLoanPayDetail();

        creditLoanPayDetail.setCreateDate(DateUtil.getDateTimeFormat(repaymentDetail.getCreateDate(), "yyyy-MM-dd"));
        creditLoanPayDetail.setId(repaymentDetail.getId());
        creditLoanPayDetail.setPayId(repaymentDetail.getPayId());
        creditLoanPayDetail.setUpdateDate(new Date());
        creditLoanPayDetail.setReturnType(repaymentDetail.getReturnType());
        creditLoanPayDetail.setRealMoney(new BigDecimal(Integer.parseInt(repaymentDetail.getRealMoney())/100.00));
        Integer realPenlty = Integer.parseInt(repaymentDetail.getRealPenlty());//滞纳金
        creditLoanPayDetail.setRealPenlty(new BigDecimal(realPenlty).divide(new BigDecimal(100)));
        creditLoanPayDetail.setRealPrinciple(new BigDecimal(Integer.parseInt(repaymentDetail.getRealPrinciple())/100.00));
        creditLoanPayDetail.setRealInterest(new BigDecimal(Integer.parseInt(repaymentDetail.getRealInterest())/100.00));
        creditLoanPayDetail.setRealgetAccrual(new BigDecimal(Integer.parseInt(repaymentDetail.getRealgetAccrual())/100.00));
        creditLoanPayDetail.setRemainAccrual(new BigDecimal(Integer.parseInt(repaymentDetail.getRemainAccrual())/100.00));
        if ("99".equals(repaymentDetail.getReturnType())) {
            creditLoanPayDetail.setRemark("大额订单减免");
            //更新还款表的reductionMoney
            CreditLoanPay creditLoanPay = new CreditLoanPay();
            creditLoanPay.setId(repaymentDetail.getPayId());
            creditLoanPay.setReductionMoney(new BigDecimal(realPenlty).divide(new BigDecimal(100)));
            localDataDao.updateCreditLoanPay(creditLoanPay);
        }else {
            creditLoanPayDetail.setRemark(repaymentDetail.getRemark());
        }
        HashMap<String,Object> repayDetail = new HashMap<>();
        repayDetail.put("asset_repayment_id",payId);
        repayDetail.put("asset_order_id",loanId);
        repayDetail.put("created_at",repaymentDetail.getCreateDate());
        HashMap<String,String> resultMap = syncUtils.checkOrderByS1(repayDetail,localDataDao);
        if(null!=resultMap){
            String flag = null;
            try{
                flag = resultMap.get("sFlag");
            }catch(Exception e){

            }
            if(StringUtils.isNotBlank(flag)){
                creditLoanPayDetail.setS1Flag(Constant.S_FLAG);
            }
            creditLoanPayDetail.setCurrentCollectionUserId(resultMap.get("currentUserId"));
        }
        return  creditLoanPayDetail;

    }

    @Override
    public void handleRepay(Repayment repayment, Loan loan, List<RepaymentDetail> repaymentDetails) {

        String payId = repayment.getId();
        String userId = loan.getUserId();
        String loanId = loan.getId();
        CreditLoanPay creditLoanPay1 = creditLoanPayDao.get(payId);
        int receivablePrinciple = Integer.parseInt(String.valueOf(repayment.getReceivablePrinciple()));//剩余应还本金
        if (creditLoanPay1 != null && creditLoanPay1.getReceivableInterest().compareTo(BigDecimal.ZERO)==1){
            logger.info("order_repayment_begin=" + loanId);
            //保存还款详情
            //如果还款详情不为空则保存还款详情

            HashMap<String,String> reMap = new HashMap<String,String>();
            reMap.put("PAY_ID", payId);
            List<String> idList = localDataDao.selectCreditLoanPayDetail(reMap);//查询目前插入的还款记录
            for(RepaymentDetail detail : repaymentDetails) {
                if (syncUtils.checkDetailId(idList, detail.getId())){ //判断该详情是否存在，如果不存在则保存
                    CreditLoanPayDetail repaymentDetail = handleRepaymentDetail(detail,payId,loanId);
                    localDataDao.saveCreditLoanPayDetail(repaymentDetail);
                }
            }
            // 还款 --更新还款表
            CreditLoanPay creditLoanPay  = handleRCreditLoanPay(repayment,loanId,loan,creditLoanPay1);
            creditLoanPay.setUpdateDate(new Date());
            localDataDao.updateCreditLoanPay(creditLoanPay);//更新还款表
            logger.info("更新还款表_还款表数据=" + payId+"----"+creditLoanPay);

            //更新订单表，催收流转日志表
            HashMap<String,Object> repaymentMap = new HashMap<>();
            repaymentMap.put("user_id",userId);
            repaymentMap.put("repaymented_amount",repayment.getRealMoney());
            //如果还款完成，则更新借款表,更新订单表，添加催收流转日志
            if (BigDecimal.ZERO.compareTo(creditLoanPay.getReceivableInterest())==0 &&
                    BigDecimal.ZERO.compareTo(creditLoanPay.getReceivablePrinciple())==0 &&
                    BigDecimal.ZERO.compareTo(creditLoanPay.getRemainAccrual())==0
                    ) {
                // 更新借款表-还款完成同步
                logger.info("update_mmanUserLoan=" + loanId + loan );

                MmanUserLoan mmanUserLoan = new MmanUserLoan();
                mmanUserLoan.setId(loanId);
                Integer loanPenalty = Integer.parseInt(loan.getLoanPenalty()); //滞纳金
                mmanUserLoan.setLoanPenalty(new BigDecimal(loanPenalty).divide(new BigDecimal(100)));
                Integer accrual = Integer.parseInt(loan.getAccrual());//利息
                mmanUserLoan.setAccrual(new BigDecimal(accrual).divide(new BigDecimal(100)));
                mmanUserLoan.setLoanStatus(Constant.STATUS_OVERDUE_FIVE);//借款状态5 还款完成
                mmanUserLoan.setUpdateTime(new Date());
                localDataDao.updateMmanUserLoan(mmanUserLoan);
                syncUtils.updateOrderAndLog(loanId,repaymentMap,localDataDao,payId);
                //更新逾期天数
                MmanLoanCollectionOrder order = new MmanLoanCollectionOrder();
                order.setOverdueDays(loan.getOverdueDays());
                order.setUpdateDate(new Date());
                orderService.updateOverdueDays(order);

            }else {//如果部分还款，只更新订单表
                syncUtils.updateMmanLoanCollectionOrder(localDataDao,loanId,repaymentMap,Constant.STATUS_OVERDUE_ONE);
            }
        }
    }



    /**
     * 每日更新滞纳金，罚息。
     * @param repayment,loan
     */
    @Override
    public void updateOverdue(Repayment repayment, Loan loan) {
        String payId = repayment.getId();
        String userId = loan.getUserId();
        String loanId = loan.getId();
        //更新订单表：逾期天数
        MmanLoanCollectionOrder order = new MmanLoanCollectionOrder();
        order.setLoanId(loanId);
        order.setOverdueDays(loan.getOverdueDays());
        order.setUpdateDate(new Date());
        orderService.updateOverdueDays(order);
        //更新借款表：滞纳金，利息；
        MmanUserLoan mmanUserLoan = new MmanUserLoan();
        mmanUserLoan.setId(loanId);
        mmanUserLoan.setLoanPenalty(new BigDecimal(Integer.parseInt(loan.getLoanPenalty())/100.00));//滞纳金
        mmanUserLoan.setAccrual(new BigDecimal(Integer.parseInt(loan.getAccrual())/100.00));
        mmanUserLoan.setUpdateTime(new Date());
        localDataDao.updateMmanUserLoan(mmanUserLoan);
        //更新还款表：应还金额，剩余应还本金，剩余应还滞纳金，剩余应还利息，剩余应还服务费
        CreditLoanPay creditLoanPay = new CreditLoanPay();
        creditLoanPay.setId(payId);
        creditLoanPay.setReceivableMoney(new BigDecimal(Integer.parseInt(repayment.getReceiveMoney())/100.00));//应还金额(总数)
        creditLoanPay.setRealgetPrinciple(new BigDecimal(Integer.parseInt(repayment.getRealgetPrinciple())/100.00));//实收本金
        creditLoanPay.setReceivablePrinciple(new BigDecimal(Integer.parseInt(repayment.getReceivablePrinciple())/100.00));//剩余应还本金
        creditLoanPay.setRealgetInterest(new BigDecimal(Integer.parseInt(repayment.getRealgetInterest())/100.00));//实收滞纳金
        creditLoanPay.setReceivableInterest(new BigDecimal(Integer.parseInt(repayment.getReceivableInterest())/100.00));//剩余应还滞纳金
        creditLoanPay.setRealgetAccrual(new BigDecimal(Integer.parseInt(repayment.getRealgetAccrual())/100.00));//实收利息
        creditLoanPay.setRemainAccrual(new BigDecimal(Integer.parseInt(repayment.getRemainAccrual())/100.00));//剩余应还利息
        creditLoanPayService.updateCreditLoanPay(creditLoanPay);

    }



    private CreditLoanPay handleRCreditLoanPay(Repayment repayment, String loanId, Loan loan, CreditLoanPay creditLoanPay1) {
        //TODO 根据大额规则还款
        CreditLoanPay creditLoanPay = new CreditLoanPay();
        creditLoanPay.setId(repayment.getId());//还款id
        creditLoanPay.setLoanId(loanId);//借款id
        creditLoanPay.setReceivableDate(DateUtil.getDateTimeFormat(repayment.getReceivableDate(),"yyyy-MM-dd"));//应还日期
        if(creditLoanPay1 == null){ //派单
            creditLoanPay.setCreateDate(new Date());
        }
        int receiveMoney = Integer.parseInt(String.valueOf(repayment.getReceiveMoney()));//总应还款金额(本金+服务费+滞纳金)
        int realMoney = Integer.parseInt(String.valueOf(repayment.getRealMoney()));//实收金额
        int realgetPrinciple = Integer.parseInt(String.valueOf(repayment.getRealgetPrinciple()));//实收本金
        int receivablePrinciple = Integer.parseInt(String.valueOf(repayment.getReceivablePrinciple()));//剩余应还本金
        int realgetInterest = Integer.parseInt(String.valueOf(repayment.getRealgetInterest()));//实收滞纳金
        int receivableInterest = Integer.parseInt(String.valueOf(repayment.getReceivableInterest()));//剩余应还滞纳金
//        int realgetServiceCharge = Integer.parseInt(String.valueOf(repayment.getRealgetServiceCharge()));//实收服务费
//        int remainServiceCharge = Integer.parseInt(String.valueOf(repayment.getRemainServiceCharge()));//剩余应还服务费
        int realgetServiceCharge = 0;//实收服务费
        int remainServiceCharge = 0;//剩余应还服务费
        int realgetAccrual = Integer.parseInt(String.valueOf(repayment.getRealgetAccrual()));//实收利息
        int remainAccrual = Integer.parseInt(String.valueOf(repayment.getRemainAccrual()));//剩余应还利息

        creditLoanPay.setRealMoney(new BigDecimal(realMoney/100.00));
        creditLoanPay.setReceivableMoney(new BigDecimal(receiveMoney/100.00));
        creditLoanPay.setRealgetInterest(new BigDecimal(realgetInterest/100.00));
        creditLoanPay.setRealgetPrinciple(new BigDecimal(realgetPrinciple/100.00));
        creditLoanPay.setRealgetServiceCharge(new BigDecimal(realgetServiceCharge/100.00));
        creditLoanPay.setReceivablePrinciple(new BigDecimal(receivablePrinciple/100.00));
        creditLoanPay.setReceivableInterest(new BigDecimal(receivableInterest/100.00));
        creditLoanPay.setRemainServiceCharge(new BigDecimal(remainServiceCharge/100.00));
        creditLoanPay.setRealgetAccrual(new BigDecimal(realgetAccrual/100.00));
        creditLoanPay.setRemainAccrual(new BigDecimal(remainAccrual/100.00));
        return creditLoanPay;
    }
}
