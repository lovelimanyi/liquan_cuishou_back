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
    private static Logger loger = Logger.getLogger(SyncService.class);
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
    public void handleOverdue(Repayment repayment, Loan loan) {

        String payId = repayment.getId();
        String userId = loan.getUserId();
        String loanId = loan.getId()+"-"+loan.getTermNumber();
        CreditLoanPay creditLoanPay1 = creditLoanPayDao.get(payId);
        if (creditLoanPay1 == null){
            HashMap<String,String> map = new HashMap<>();
            map.put("USER_ID", loan.getUserId());
            HashMap<String, Object> userInfo = dataDao.getUserInfo(map);
            HashMap<String, Object> cardInfo = dataDao.getUserCardInfo(map);	//银行卡--app端
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
            mmanUserLoan.setLoanEndTime(DateUtil.getDateTimeFormat(loan.getLoanEndTime(), "yyyy-MM-dd"));
            mmanUserLoan.setUpdateTime(new Date());
            mmanUserLoan.setLoanStatus(Constant.STATUS_OVERDUE_FOUR);//4：逾期
            mmanUserLoan.setCreateTime(new Date());
            mmanUserLoan.setDelFlag("0");//0正常1：删除
            mmanUserLoan.setLoanPyId(loanId);
            // 标识新老用户 0 新用户  1 老用户
            mmanUserLoan.setCustomerType(Integer.valueOf(userInfo.get("customer_type") == null ? "0" : userInfo.get("customer_type").toString()));
            localDataDao.saveMmanUserLoan(mmanUserLoan);


            // 保存还款表-逾期同步
            CreditLoanPay creditLoanPay = handleRCreditLoanPay(repayment,loanId,loan,null);
            localDataDao.saveCreditLoanPay(creditLoanPay);//保存还款表

            //保存用户信息表--联系人表--银行卡
            syncUtils.saveUserInfo(localDataDao,payId,userId,userInfo,userContactsList,cardInfo);

            //派单
//            taskJobMiddleService.dispatchforLoanId(loanId,userInfo.get("id_number").toString(),Constant.BIG);
            orderService.dispatchOrderNew(loanId,userInfo.get("id_number").toString(),Constant.BIG);
        }

    }

    @Override
    public void handleRepay(Repayment repayment, Loan loan, RepaymentDetail repaymentDetail) {
        String payId = repayment.getId();
        String userId = loan.getUserId();
        String loanId = loan.getId()+"-"+loan.getTermNumber();
        CreditLoanPay creditLoanPay1 = creditLoanPayDao.get(payId);
        int receivablePrinciple = Integer.parseInt(String.valueOf(repayment.getReceivablePrinciple()));//剩余应还本金
        if (creditLoanPay1 != null){
            // 还款 --更新还款表
            CreditLoanPay creditLoanPay = handleRCreditLoanPay(repayment,loanId,loan,creditLoanPay1);
            localDataDao.updateCreditLoanPay(creditLoanPay);//更新还款表
            //保存还款详情
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("PAY_ID", payId);
            List<String> idList = localDataDao.selectCreditLoanPayDetail(map);//查询目前插入的还款记录
            if (syncUtils.checkDetailId(idList, repaymentDetail.getId())){
                CreditLoanPayDetail creditLoanPayDetail = new CreditLoanPayDetail();
                creditLoanPayDetail.setCreateDate(DateUtil.getDateTimeFormat(repaymentDetail.getCreateDate(), "yyyy-MM-dd"));
                creditLoanPayDetail.setId(repaymentDetail.getId());
                creditLoanPayDetail.setPayId(payId);
                creditLoanPayDetail.setUpdateDate(new Date());
                creditLoanPayDetail.setReturnType(repaymentDetail.getReturnType());
                creditLoanPayDetail.setRemark(repaymentDetail.getRemark());
                creditLoanPayDetail.setRealMoney(new BigDecimal(Integer.parseInt(repaymentDetail.getRealMoney())/100.00));
                creditLoanPayDetail.setRealPenlty(new BigDecimal(Integer.parseInt(repaymentDetail.getRealPenlty())/100.00));
                creditLoanPayDetail.setRealPrinciple(new BigDecimal(Integer.parseInt(repaymentDetail.getRealPrinciple())/100.00));
                creditLoanPayDetail.setRealInterest(new BigDecimal(Integer.parseInt(repaymentDetail.getRealInterest())/100.00));
                creditLoanPayDetail.setRealgetAccrual(new BigDecimal(Integer.parseInt(repaymentDetail.getRealgetAccrual())/100.00));
                creditLoanPayDetail.setRemainAccrual(new BigDecimal(Integer.parseInt(repaymentDetail.getRemainAccrual())/100.00));
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

                localDataDao.saveCreditLoanPayDetail(creditLoanPayDetail);
            }

            //更新订单表，催收流转日志表
            HashMap<String,Object> repaymentMap = new HashMap<>();
            repaymentMap.put("user_id",userId);
            repaymentMap.put("repaymented_amount",repayment.getRealMoney());
            //如果还款完成，则更新借款表,更新订单表，添加催收流转日志
            if (receivablePrinciple <= 0) {
                // 更新借款表-还款完成同步
                MmanUserLoan mmanUserLoan = new MmanUserLoan();
                mmanUserLoan.setId(loanId);
                mmanUserLoan.setLoanStatus(Constant.STATUS_OVERDUE_FIVE);//借款状态5 还款完成
                mmanUserLoan.setUpdateTime(new Date());
                localDataDao.updateMmanUserLoan(mmanUserLoan);
                syncUtils.updateOrderAndLog(loanId,repaymentMap,localDataDao,payId);
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
        String loanId = loan.getId()+"-"+loan.getTermNumber();
        //更新订单表：逾期天数
        MmanLoanCollectionOrder order = new MmanLoanCollectionOrder();
        order.setLoanId(loanId);
        order.setOverdueDays(loan.getOverdueDays());
        orderService.updateOverdueDays(order);
        //更新借款表：滞纳金，利息；
        MmanUserLoan mmanUserLoan = new MmanUserLoan();
        mmanUserLoan.setId(loanId);
        mmanUserLoan.setLoanPenalty(new BigDecimal(Integer.parseInt(loan.getLoanPenalty())/100.00));//滞纳金
        mmanUserLoan.setAccrual(new BigDecimal(Integer.parseInt(loan.getAccrual())/100.00));
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

    private CreditLoanPay handleRCreditLoanPay(Repayment repayment, String loanId,Loan loan,CreditLoanPay creditLoanPay1) {
        //TODO 根据大额规则还款
        CreditLoanPay creditLoanPay = new CreditLoanPay();
        creditLoanPay.setId(repayment.getId());//还款id
        creditLoanPay.setLoanId(loanId);//借款id
        creditLoanPay.setReceivableDate(DateUtil.getDateTimeFormat(repayment.getReceivableDate(),"yyyy-MM-dd"));//应还日期
        if(creditLoanPay1 == null){//派单
            creditLoanPay.setCreateDate(new Date());
        }
        int receiveMoney = Integer.parseInt(String.valueOf(repayment.getReceiveMoney()));//总应还款金额(本金+服务费+滞纳金)
        int realMoney = Integer.parseInt(String.valueOf(repayment.getRealMoney()));//实收金额
        int realgetPrinciple = Integer.parseInt(String.valueOf(repayment.getRealgetPrinciple()));//实收本金
        int receivablePrinciple = Integer.parseInt(String.valueOf(repayment.getReceivablePrinciple()));//剩余应还本金
        int realgetInterest = Integer.parseInt(String.valueOf(repayment.getRealgetInterest()));//实收滞纳金
        int receivableInterest = Integer.parseInt(String.valueOf(repayment.getReceivableInterest()));//剩余应还滞纳金
        int realgetServiceCharge = Integer.parseInt(String.valueOf(repayment.getRealgetServiceCharge()));//实收服务费
        int remainServiceCharge = Integer.parseInt(String.valueOf(repayment.getRemainServiceCharge()));//剩余应还服务费
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
        creditLoanPay.setUpdateDate(new Date());
        return creditLoanPay;
    }
}
