package com.info.back.service;

import com.info.back.dao.ICreditLoanPayDao;
import com.info.back.dao.ILocalDataDao;
import com.info.constant.Constant;
import com.info.vo.bigAmount.Loan;
import com.info.vo.bigAmount.Repayment;
import com.info.vo.bigAmount.RepaymentDetail;
import com.info.web.pojo.CreditLoanPay;
import com.info.web.pojo.CreditLoanPayDetail;
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
    private TaskJobMiddleService taskJobMiddleService;
    @Autowired
    private ICreditLoanPayDao creditLoanPayDao;

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


            // 保存还款表-逾期同步
            CreditLoanPay creditLoanPay = handleRCreditLoanPay(repayment,loanId,loan,null);
            localDataDao.saveCreditLoanPay(creditLoanPay);//保存还款表
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
            mmanUserLoan.setLoanPyId("123456789");
            // 标识新老用户 0 新用户  1 老用户
            mmanUserLoan.setCustomerType(Integer.valueOf(userInfo.get("customer_type") == null ? "0" : userInfo.get("customer_type").toString()));
            localDataDao.saveMmanUserLoan(mmanUserLoan);

            //保存用户信息表--联系人表--银行卡
            syncUtils.saveUserInfo(localDataDao,payId,userId,userInfo,userContactsList,cardInfo);

            //派单
            taskJobMiddleService.dispatchforLoanId(loanId,userInfo.get("id_number").toString(),Constant.BIG);
        }

    }

    @Override
    public void handleRepay(Repayment repayment, Loan loan, RepaymentDetail repaymentDetail) {
        String payId = repayment.getId();
        String userId = loan.getUserId();
        String loanId = loan.getId()+"-"+loan.getTermNumber();
        CreditLoanPay creditLoanPay1 = creditLoanPayDao.get(payId);
        if (creditLoanPay1 != null){
            // 更新还款表-逾期同步
            CreditLoanPay creditLoanPay = handleRCreditLoanPay(repayment,loanId,loan,creditLoanPay1);
            localDataDao.updateCreditLoanPay(creditLoanPay);//更新还款表

            // 更新借款表-逾期同步
            MmanUserLoan mmanUserLoan = new MmanUserLoan();
            mmanUserLoan.setId(loanId);
            mmanUserLoan.setLoanStatus(Constant.STATUS_OVERDUE_FIVE);//借款状态5 还款完成
            mmanUserLoan.setUpdateTime(new Date());
            localDataDao.updateMmanUserLoan(mmanUserLoan);
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
                int realMoney = Integer.parseInt(loan.getLoanMoney());//借款本金
                int realPenlty = Integer.parseInt(loan.getLoanPenalty());//滞纳金
                creditLoanPayDetail.setRealMoney(new BigDecimal(realMoney/100.00));
                creditLoanPayDetail.setRealPenlty(new BigDecimal(realPenlty/100.00));
                creditLoanPayDetail.setRealPrinciple(new BigDecimal(0));
                creditLoanPayDetail.setRealInterest(new BigDecimal(0));
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
            syncUtils.updateOrderAndLog(loanId,repaymentMap,localDataDao,payId);
        }

    }

    private CreditLoanPay handleRCreditLoanPay(Repayment repayment, String loanId,Loan loan,CreditLoanPay creditLoanPay1) {
        //TODO 根据大额规则还款
        CreditLoanPay creditLoanPay = new CreditLoanPay();
        creditLoanPay.setId(repayment.getId());//还款id
        creditLoanPay.setLoanId(loanId);//借款id
        creditLoanPay.setReceivableDate(DateUtil.getDateTimeFormat(repayment.getReceivableDate(),"yyyy-MM-dd"));//应还日期
        if(creditLoanPay1 == null){//派单
            creditLoanPay.setReceivableMoney(new BigDecimal(Integer.parseInt(String.valueOf(repayment.getReceiveMoney()))/100.00));//应还金额(总数)
            creditLoanPay.setReceivablePrinciple(new BigDecimal(Integer.parseInt(String.valueOf(loan.getLoanMoney()))/100.00));//应还本金
            creditLoanPay.setRemainServiceCharge(new BigDecimal(Integer.parseInt(String.valueOf(loan.getServiceCharge()))/100.00));//应还服务费
            creditLoanPay.setReceivableInterest(new BigDecimal(Integer.parseInt(String.valueOf(loan.getLoanPenalty()))/100.00));//应还滞纳金
            creditLoanPay.setRealMoney(new BigDecimal(Integer.parseInt(String.valueOf(repayment.getRealMoney()))/100.00));//实收金额
            creditLoanPay.setRealgetInterest(new BigDecimal(0));
            creditLoanPay.setRealgetPrinciple(new BigDecimal(0));
            creditLoanPay.setCreateDate(new Date());
        }else{// 还款
            //TODO 还款逻辑待开发
            int receiveMoney = Integer.parseInt(String.valueOf(repayment.getReceiveMoney()));//总应还款金额(本金+服务费+滞纳金)
            int loanMoney = Integer.parseInt(String.valueOf(loan.getServiceCharge()));//本金
            int loanPenalty = Integer.parseInt(String.valueOf(loan.getLoanPenalty()));//滞纳金
            int serviceCharge = Integer.parseInt(String.valueOf(loan.getServiceCharge()));//服务费
            int realMoney = Integer.parseInt(String.valueOf(repayment.getRealMoney()));//本次实收金额
            //实收罚息
            int realPenalty = realMoney -(receiveMoney-loanPenalty);
//            if ()
        }
        creditLoanPay.setUpdateDate(new Date());
        return creditLoanPay;
    }
}
