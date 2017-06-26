package com.info.back.test;

import com.info.back.dao.ILocalDataDao;
import com.info.constant.Constant;
import com.info.web.pojo.CreditLoanPay;
import com.info.web.pojo.CreditLoanPayDetail;
import com.info.web.pojo.CreditLoanPaySum;
import com.info.web.pojo.MmanUserLoan;
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
@ContextConfiguration(locations={"file:src/config/applicationContext.xml"})
public class LocalOverdue {
    private static Logger loger = Logger.getLogger(LocalOverdue.class);

    @Autowired
    private IDataDao dataDao;
    @Autowired
    private ILocalDataDao localDataDao;

    @Test
    public void test() {

//        String payId = "1570135,1576244,1570901,1633602,1638470,1637796,1638836,1640581,1637904,1643406,1664111,1664729,1648949,1652159,1652491,1652624,1652824,1671581,1677125,1679717,1678738,1679464,1679297,1679565,1679845,1679989,1679485\n";
//        String[] list = payId.split(",");
//        for (int i=0;i<list.length;i++){
//            doOverDue(list[i]);
//            System.out.println(list[i]);
//        }


        String payId="381506";
        doOverDue(payId);

    }
    public void doOverDue(String payId){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("ID", payId);//还款id
        //还款信息--app端
        HashMap<String,Object> repayment = this.dataDao.getAssetRepayment(map);
        String loanId = String.valueOf(repayment.get("asset_order_id"));//借款id
        String userId  = String.valueOf(repayment.get("user_id"));    	//用户id
        map.put("ORDER_ID", loanId);
        map.put("USER_ID", userId);

        try {
            HashMap<String, Object> borrowOrder = null;					//借款信息--app端
            List<HashMap<String, Object>> repaymentDetailList = null;	//还款详情信息--app端

            borrowOrder = this.dataDao.getAssetBorrowOrder(map);
            repaymentDetailList = this.dataDao.getAssetRepaymentDetail(map);

            if (null!= repayment && null != borrowOrder && null != repaymentDetailList) {
                //更新用户借款表
                updateMmanUserLoan(loanId, repayment, Constant.STATUS_OVERDUE_FOUR);
                //更新还款表
                updateCreditLoanPay(payId,repayment);
                //保存还款详情表
                saveCreditLoanPayDetail(repayment,payId, repaymentDetailList);
                //更新订单表
                syncUtils.updateMmanLoanCollectionOrder(localDataDao,loanId,repayment,Constant.STATUS_OVERDUE_ONE);
            }
            //验证是否减免
//            syncUtils.checkReduction(repayment,localDataDao);
        } catch (Exception e0) {
            loger.error("OperaOverdueDataThread-异常-loanId"+loanId);
            e0.printStackTrace();
        }
    }


    private void updateMmanUserLoan(String loanId,HashMap<String,Object> repaymentMap,String status){
        MmanUserLoan mmanUserLoan = new MmanUserLoan();
        mmanUserLoan.setId(loanId);//借款id
        mmanUserLoan.setLoanPenalty(new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("plan_late_fee")))/100.00));//滞纳金
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
            BigDecimal sumRealMoneyBig = creditLoanPaySum.getSumRealMoney().setScale(2,   BigDecimal.ROUND_HALF_UP);
            BigDecimal sumRealPenltyBig = creditLoanPaySum.getSumRealPenlty().setScale(2,   BigDecimal.ROUND_HALF_UP);
            BigDecimal big100 = new BigDecimal(100);

            int sumRealMoney = sumRealMoneyBig.multiply(big100).intValue();;//总实收本金
            int sumRealPenlty = sumRealPenltyBig.multiply(big100).intValue();//总实收罚息
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
}
