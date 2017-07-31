package com.info.back.test;

import com.info.back.dao.ILocalDataDao;
import com.info.back.utils.BackConstant;
import com.info.back.utils.IdGen;
import com.info.constant.Constant;
import com.info.web.pojo.*;
import com.info.web.synchronization.RedisUtil;
import com.info.web.synchronization.dao.IDataDao;
import com.info.web.synchronization.syncUtils;
import com.info.web.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/5/16 0016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/resources/applicationContext.xml"})
public class LocalRepay {
    private static Logger loger = Logger.getLogger(LocalRepay.class);

    @Autowired
    private IDataDao dataDao;
    @Autowired
    private ILocalDataDao localDataDao;

    @Test
    public void test() {
//        String payId = "268072,1027289,1342420,1336985,1678738,1637904,1679989,1638836";
//        String[] list = payId.split(",");
//        for (int i=0;i<list.length;i++){
//            doRepay(list[i]);
//            System.out.println(list[i]);
//        }
        String payId = "2439270";
        doRepay(payId);

    }
    public void doRepay(String payId){
        if (StringUtils.isNotBlank(payId)) {
            loger.error("sync-OperaRepayDataThread:" + payId);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ID", payId);//还款id
            //获取app端还款信息
            HashMap<String, Object> repayment = this.dataDao.getAssetRepayment(map);
            String loanId = String.valueOf(repayment.get("asset_order_id"));//借款id
            map.put("ORDER_ID", loanId);//还款id
            if (null != repayment) {
                try {
                    HashMap<String, Object> borrowOrder = null;
                    List<HashMap<String, Object>> repaymentDetailList = null;
//                    if (!checkLoanStatus(loanId)) {
                    //获取app端借款信息
//                    borrowOrder = this.dataDao.getAssetBorrowOrder(map);
                    //获取app端还款详情信息
                    repaymentDetailList = this.dataDao.getAssetRepaymentDetail(map);
//                    if (null != borrowOrder && null != repaymentDetailList) {
                     if (null != repaymentDetailList) {
                            //更新借款表
                            updateMmanUserLoan(loanId, repayment, Constant.STATUS_OVERDUE_FIVE);
                            //更新还款表
                            updateCreditLoanPay(payId, repayment);
                            //更新订单表-保存催收流转日志
                            updateOrderAndLog(loanId, repayment);
                            //保存还款详情表
                            saveCreditLoanPayDetail(repayment, payId, repaymentDetailList);
                        }
//                    }
                } catch (Exception e0) {
                    e0.printStackTrace();
                }
            }
        }
    }

    private void updateMmanUserLoan(String loanId,HashMap<String,Object> repaymentMap,String status){
        MmanUserLoan mmanUserLoan = new MmanUserLoan();
        mmanUserLoan.setId(loanId);//借款id
        mmanUserLoan.setLoanPenalty(new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("plan_late_fee")))/100));//滞纳金
        mmanUserLoan.setLoanStatus(status);//借款状态
        mmanUserLoan.setUpdateTime(DateUtil.getDateTimeFormat(String.valueOf(repaymentMap.get("repayment_real_time")), "yyyy-MM-dd HH:mm:ss"));
        localDataDao.updateMmanUserLoan(mmanUserLoan);
    }
    private void updateCreditLoanPay(String payId,HashMap<String,Object> repaymentMap){
        CreditLoanPay creditLoanPay = new CreditLoanPay();
        creditLoanPay.setId(payId);//还款id
        creditLoanPay.setReceivableMoney(new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("repayment_amount")))/100.00));//应还金额
        creditLoanPay.setRealMoney(new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("repaymented_amount")))/100.00));//实收（本金+服务费）
        creditLoanPay.setStatus(getPayStatus(String.valueOf(repaymentMap.get("status")))); //订单等级组s1,s2等
        creditLoanPay.setUpdateDate(DateUtil.getDateTimeFormat(String.valueOf(repaymentMap.get("repayment_real_time")), "yyyy-MM-dd HH:mm:ss"));
        creditLoanPay = syncUtils.operaRealPenlty(repaymentMap,creditLoanPay);//剩余应还(本金+手续费),剩余应还罚息,实收(本金+手续费),实收罚息
        localDataDao.updateCreditLoanPay(creditLoanPay);
    }
    public int getPayStatus(String status) {
        if (Constant.STATUS_HKZ.equals(status)) {
            return Constant.CREDITLOANPAY_OVERDUE_UNCOMPLETE;
        } else if (Constant.STATUS_YYQ.equals(status) || Constant.STATUS_YHZ.equals(status)) {
            return Constant.CREDITLOANPAY_OVERDUEA;
        } else {
            return Constant.CREDITLOANPAY_COMPLETE;
        }
    }
    public void saveCreditLoanPayDetail(HashMap<String, Object> repayment,String payId,List<HashMap<String,Object>> repaymentDetailList){
        loger.error("start-saveCreditLoanPayDetail-payId =" + payId);
        List<String> idList = null;
        if(null!=repaymentDetailList && 0<repaymentDetailList.size()){//首先删除已有的记录，重新添加
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("PAY_ID", payId);
            idList = localDataDao.selectCreditLoanPayDetail(map);//查询目前插入的还款记录
        }
        CreditLoanPayDetail creditLoanPayDetail =null;
        for(int i=0;i<repaymentDetailList.size();i++){
            HashMap<String,Object> repayDetail = repaymentDetailList.get(i);
            String detailId = String.valueOf(repayDetail.get("id"));
            if(checkDetailId(idList, detailId)){
                //减免罚息    如果还款类型为6，还款状态2.则更新还款表的 减免金额
                syncUtils.ReductionMoney(repayDetail,payId,localDataDao);

                creditLoanPayDetail = new CreditLoanPayDetail();
                creditLoanPayDetail.setId(detailId);
                creditLoanPayDetail.setPayId(payId);
                creditLoanPayDetail.setCreateDate(DateUtil.getDateTimeFormat(String.valueOf(repayDetail.get("created_at")), "yyyy-MM-dd HH:mm:ss"));
                creditLoanPayDetail.setUpdateDate(DateUtil.getDateTimeFormat(String.valueOf(repayDetail.get("created_at")), "yyyy-MM-dd HH:mm:ss"));
                creditLoanPayDetail.setReturnType(String.valueOf(repayDetail.get("repayment_type")));
                creditLoanPayDetail.setRemark(String.valueOf(repayDetail.get("remark")));
                HashMap<String,String> resultMap = checkOrderByS1(repayDetail,localDataDao);
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
                creditLoanPayDetail = syncUtils.operaRealPenltyDetail(repayment, repayDetail, payId,creditLoanPayDetail,localDataDao);
                localDataDao.saveCreditLoanPayDetail(creditLoanPayDetail);
                loger.error("end-saveCreditLoanPayDetail-payId =" + payId);
            }
        }
    }
    /**
     * 验证当前还款详情是否已存在
     * @param idList
     * @param detailId
     * @return false 存在，  true 不存在
     */
    public boolean checkDetailId(List<String> idList,String detailId){
        if(null!=idList && 0<idList.size()){
            for(int i=0;i<idList.size();i++){
                if(detailId.equals(idList.get(i))){
                    return false;
                }
            }
            return true;
        }else{
            return true;
        }
    }

    /**
     * 验证 纪录2-11号S1组订单到S2人手上的标志
     * @param repayDetail
     */
    public HashMap<String,String> checkOrderByS1(HashMap<String,Object> repayDetail,ILocalDataDao localDataDao){
        String payId = String.valueOf(repayDetail.get("asset_repayment_id"));//还款id
        String orderId = String.valueOf(repayDetail.get("asset_order_id"));//借款id
        String createdDate = String.valueOf(repayDetail.get("created_at"));
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("PAY_ID", payId);
        map.put("ORDER_ID", orderId);
        HashMap<String,Object> orderMap = localDataDao.selectOrderByDetail(map);//查询ourder
        if(null!=orderMap){
            HashMap<String,String> resultMap = new HashMap<String,String>();
            try{
                String currentUserId = String.valueOf(orderMap.get("current_collection_user_id"));
                String sFlag = String.valueOf(orderMap.get("s1_flag"));
                int overdueDays = Integer.parseInt(String.valueOf(orderMap.get("overdueDays")));
                if(StringUtils.isNotBlank(sFlag)){
                    if(sFlag.equals(Constant.S_FLAG)){
                        boolean bool = operaSflag(createdDate,overdueDays);
                        if(bool){
                            resultMap.put("sFlag", Constant.S_FLAG);
                            resultMap.put("currentUserId", currentUserId);
                            return resultMap;
                        }
                    }
                }
                resultMap.put("currentUserId", currentUserId);
            }catch(Exception e){
                e.printStackTrace();
            }
            return resultMap;
        }
        return null;
    }
    /**
     * 计算是否在s1里
     * @param createdDate
     * @param overdueDays
     */
    public boolean operaSflag(String createdDate, int overdueDays) {
        int days = DateUtil.getDateFormats(createdDate,"yyyy-MM-dd HH:mm:ss");//还款时间减去当前时间
        int sDay = overdueDays-days;//逾期的天数-还款延迟天数
        if(sDay<=10){
            return true;
        }
        return false;
    }
    /**
     * 计算还款
     * @param repaymentMap 从库还款
     * @param creditLoanPay 主库还款
     */
    public CreditLoanPay operaRealPenlty(HashMap<String,Object> repaymentMap,CreditLoanPay creditLoanPay){
        int repaymentAmount = Integer.parseInt(String.valueOf(repaymentMap.get("repayment_amount")));//总还款金额
        int planLateFee = Integer.parseInt(String.valueOf(repaymentMap.get("plan_late_fee")));//滞纳金
        int repaymentedAmount = Integer.parseInt(String.valueOf(repaymentMap.get("repaymented_amount")));//已还款金额

        int receivablePrinciple = repaymentAmount - planLateFee;// 应还(本金+手续费)

        // 实收的利息 = 已还金额 - 应还(本金+手续费)
        int realPenlty = repaymentedAmount - receivablePrinciple;

        if(realPenlty <= 0){
            creditLoanPay.setReceivablePrinciple(new BigDecimal((receivablePrinciple - repaymentedAmount) / 100.00));//剩余(本金+手续费)
            creditLoanPay.setReceivableInterest(new BigDecimal(planLateFee/100.00));//剩余应还罚息
            creditLoanPay.setRealgetPrinciple(new BigDecimal(repaymentedAmount/100.00));//实收(本金+手续费)
            creditLoanPay.setRealgetInterest(new BigDecimal(0));//实收罚息
        }else{
            creditLoanPay.setReceivablePrinciple(new BigDecimal(0));//剩余应还(本金+手续费)
            creditLoanPay.setReceivableInterest(new BigDecimal((repaymentAmount-repaymentedAmount)/100.00));//剩余应还罚息
            creditLoanPay.setRealgetPrinciple(new BigDecimal((repaymentedAmount-realPenlty)/100.00));//实收(本金+手续费)
            creditLoanPay.setRealgetInterest(new BigDecimal(realPenlty/100.00));//实收罚息
        }
        return creditLoanPay;
    }
    /**
     * 计算还款详情
     *
     */
    public CreditLoanPayDetail operaRealPenltyDetail(HashMap<String,Object> repaymentMap,HashMap<String,Object> repayDetail,String payId,CreditLoanPayDetail creditLoanPayDetail,ILocalDataDao localDataDao){
        int repaymentAmount =  Integer.parseInt(String.valueOf(repaymentMap.get("repayment_amount")));//总还款金额
        int planLateFee = Integer.parseInt(String.valueOf(repaymentMap.get("plan_late_fee")));//滞纳金
//		int repaymentedAmount = Integer.parseInt(String.valueOf(repaymentMap.get("repaymented_amount")));//已还款金额

        int receivablePrinciple = repaymentAmount - planLateFee;// （应还本金+服务费）

        int trueRepaymentMoney = Integer.parseInt(String.valueOf(repayDetail.get("true_repayment_money")));//还款金额，从库中传过来

        int detailCount = localDataDao.getDetailCount(payId);//还款详情表中payId还款条数

        if(detailCount == 0){
            if(trueRepaymentMoney >= receivablePrinciple){
                creditLoanPayDetail.setRealMoney(new BigDecimal(receivablePrinciple/100.00));//实收本金+服务费
                int realPenlty = trueRepaymentMoney-receivablePrinciple;//实收罚息
                creditLoanPayDetail.setRealPenlty(new BigDecimal(realPenlty/100.00));
                creditLoanPayDetail.setRealPrinciple(new BigDecimal(0));
                int realInterest = planLateFee-realPenlty;
                creditLoanPayDetail.setRealInterest(new BigDecimal(realInterest/100.00));
            }else{
                creditLoanPayDetail.setRealMoney(new BigDecimal(trueRepaymentMoney/100.00));
                creditLoanPayDetail.setRealPenlty(new BigDecimal(0));
                creditLoanPayDetail.setRealPrinciple(new BigDecimal((receivablePrinciple-trueRepaymentMoney)/100.00));
                creditLoanPayDetail.setRealInterest(new BigDecimal(planLateFee/100.00));
            }

        }else{
            CreditLoanPaySum creditLoanPaySum = localDataDao.sumRealMoneyAndPenlty(payId);
            int sumRealMoney = creditLoanPaySum.getSumRealMoney().intValue()*100;//总实收本金
            int sumRealPenlty = creditLoanPaySum.getSumRealPenlty().intValue()*100;//总实收罚息
            if(sumRealMoney >= receivablePrinciple){
                creditLoanPayDetail.setRealMoney(new BigDecimal(0));
                creditLoanPayDetail.setRealPenlty(new BigDecimal(trueRepaymentMoney/100.00));
                creditLoanPayDetail.setRealPrinciple(new BigDecimal(0));
                creditLoanPayDetail.setRealInterest(new BigDecimal((planLateFee-sumRealPenlty-trueRepaymentMoney)/100.00));
            }else{

                if((sumRealMoney+trueRepaymentMoney)<receivablePrinciple){
                    creditLoanPayDetail.setRealMoney(new BigDecimal(trueRepaymentMoney/100));
                    creditLoanPayDetail.setRealPenlty(new BigDecimal(0));
                    int realPrinciple = receivablePrinciple-(sumRealMoney+trueRepaymentMoney);//剩余应还本金
                    creditLoanPayDetail.setRealPrinciple(new BigDecimal(realPrinciple/100.00));
                    creditLoanPayDetail.setRealInterest(new BigDecimal(planLateFee/100.00));
                }else{
                    int realPenlty = sumRealMoney+trueRepaymentMoney-receivablePrinciple;//实收罚息
                    creditLoanPayDetail.setRealMoney(new BigDecimal((trueRepaymentMoney-realPenlty)/100.00));
                    creditLoanPayDetail.setRealPenlty(new BigDecimal(realPenlty/100.00));
                    creditLoanPayDetail.setRealPrinciple(new BigDecimal(0));
                    creditLoanPayDetail.setRealInterest(new BigDecimal((planLateFee-realPenlty)/100.00));
                }
            }

        }
        return creditLoanPayDetail;
    }

    private void updateOrderAndLog(String loanId,HashMap<String, Object> repaymentMap){
        MmanLoanCollectionOrder order = null;
        //更新订单
        order = syncUtils.updateMmanLoanCollectionOrder(localDataDao,loanId,repaymentMap,Constant.STATUS_OVERDUE_FOUR);

        BackUser backUser = null;
        if(null!=order){
            String backUserId = order.getCurrentCollectionUserId();
            HashMap<String,Object> umap = new HashMap<String,Object>();
            umap.put("ID", backUserId);
            backUser = this.localDataDao.selectBackUser(umap);
        }
        if(null!=backUser){
            loger.info("保存流转日志=借款loanID"+loanId+"执行start"+ DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
            MmanLoanCollectionStatusChangeLog loanChangeLog = new MmanLoanCollectionStatusChangeLog();
            loanChangeLog.setId(IdGen.uuid());
            loanChangeLog.setLoanCollectionOrderId(order.getOrderId());
            loanChangeLog.setBeforeStatus(order.getStatus());
            loanChangeLog.setAfterStatus(Constant.STATUS_OVERDUE_FOUR);
            loanChangeLog.setType(Constant.STATUS_OVERDUE_FIVE);//催收完成
            loanChangeLog.setOperatorName(Constant.OPERATOR_NAME);
            loanChangeLog.setRemark(Constant.PAY_MENT_SUCCESS+backUser.getUserName());
            loanChangeLog.setCompanyId(backUser.getCompanyId());
            loanChangeLog.setCurrentCollectionUserId(backUser.getUuid());
            loanChangeLog.setCurrentCollectionUserLevel(backUser.getGroupLevel());
            loger.error("start-setsetCurrentCollectionOrderLevel="+loanId+"S1Flag="+order.getS1Flag());
            if(StringUtils.isNotBlank(order.getS1Flag())){
                loanChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_S1);
            }else{
                loanChangeLog.setCurrentCollectionOrderLevel(order.getCurrentOverdueLevel());
            }
            loger.error("end-setsetCurrentCollectionOrderLevel="+loanId+"S1Flage="+order.getS1Flag());
            loger.error("start-setsetCurrentCollectionOrderLevel="+loanId+"Level="+loanChangeLog.getCurrentCollectionOrderLevel());
            loanChangeLog.setCreateDate(DateUtil.getDateTimeFormat(String.valueOf(repaymentMap.get("repayment_real_time")), "yyyy-MM-dd HH:mm:ss"));
            this.localDataDao.saveLoanChangeLog(loanChangeLog);
        }

    }
    /**
     * 验证是否重已经还款完成
     *
     * @return true 已经还款完成  false未还款完成
     */
    public boolean checkLoanStatus(String id){
        if(StringUtils.isNotBlank(id)){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("ID", id);
            String userLoan = this.localDataDao.checkLoanStatus(map);
            if(userLoan.equals("5")){
                return true;
            }else {
                return false;
            }
        }
        return true;
    }

}
