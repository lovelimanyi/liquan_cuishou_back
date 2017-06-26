//package com.info.back.test;
//
//import com.info.back.dao.ILocalDataDao;
//import com.info.back.utils.BackConstant;
//import com.info.back.utils.IdGen;
//import com.info.constant.Constant;
//import com.info.web.pojo.BackUser;
//import com.info.web.pojo.MmanLoanCollectionOrder;
//import com.info.web.pojo.MmanLoanCollectionStatusChangeLog;
//import com.info.web.synchronization.RedisUtil;
//import com.info.web.synchronization.dao.IDataDao;
//import com.info.web.synchronization.syncUtils;
//import com.info.web.util.DateUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * Created by Administrator on 2017/5/8 0008.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"file:src/config/applicationContext.xml"})
//public class TestNewRepayThread {
//    private static Logger loger = Logger.getLogger(TestNewRepayThread.class);
//
//    @Autowired
//    private IDataDao dataDao;
//    @Autowired
//    private ILocalDataDao localDataDao;
//
//    @Test
//    public void test() {
//        String payId = "968618";
//        if(StringUtils.isNotBlank(payId)){
//            loger.error("sync-OperaRepayDataThread:"+payId);
//            HashMap<String,String> map = new HashMap<String,String>();
//            map.put("ID", payId);//还款id
//            //获取app端还款信息
//            HashMap<String,Object> repayment = this.dataDao.getAssetRepayment(map);
//            String loanId = String.valueOf(repayment.get("asset_order_id"));//借款id
//            map.put("ORDER_ID", loanId);//还款id
//            if(null!=repayment){
//                try{
//                    if(!checkLoanStatus(loanId)){
//                        //获取app端借款信息
//                        HashMap<String,Object> borrowOrder = this.dataDao.getAssetBorrowOrder(map);
//                        //获取app端还款详情信息
//                        List<HashMap<String,Object>> repaymentDetailList = this.dataDao.getAssetRepaymentDetail(map);
//                        if(null!=borrowOrder && null!=repaymentDetailList){
//                            //更新借款表
//                            syncUtils.updateMmanUserLoan(localDataDao,loanId,repayment, Constant.STATUS_OVERDUE_FIVE);
//                            //更新还款表
//                            syncUtils.updateCreditLoanPay(localDataDao,payId,repayment);
//                            //更新订单表
//                            updateMmanLoanCollectionOrder(loanId,repayment);
//                            //保存还款详情表
//                            syncUtils.saveCreditLoanPayDetail(localDataDao,repayment,payId, repaymentDetailList);
//                        }
//                        RedisUtil.delRedisKey(Constant.TYPE_REPAY_+payId);
//                    }else{
//                        RedisUtil.delRedisKey(Constant.TYPE_REPAY_+payId);
//                    }
//                }catch(Exception e0){
//                    e0.printStackTrace();
//                }
//            }
//        }
//    }
//    private void updateMmanLoanCollectionOrder(String loanId,HashMap<String, Object> repaymentMap){
//        HashMap<String,Object> map = new HashMap<String,Object>();
//        map.put("ORDER_ID", loanId);
//        map.put("USER_ID", repaymentMap.get("user_id"));
//        MmanLoanCollectionOrder order = this.localDataDao.selectLoanOrder(map);
//        BigDecimal realMoney = new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("repaymented_amount")))/100.00);
//        map.put("REAL_MONEY", realMoney);
//        map.put("STATUS", Constant.STATUS_OVERDUE_FOUR);
//        this.localDataDao.updateOrderStatus(map);
//        BackUser backUser = null;
//        if(null!=order){
//            String backUserId = order.getCurrentCollectionUserId();
//            HashMap<String,Object> umap = new HashMap<String,Object>();
//            umap.put("ID", backUserId);
//            backUser = this.localDataDao.selectBackUser(umap);
//        }
//        if(null!=backUser){
//            loger.info("保存流转日志=借款loanID"+loanId+"执行start"+ DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
//            MmanLoanCollectionStatusChangeLog loanChangeLog = new MmanLoanCollectionStatusChangeLog();
//            loanChangeLog.setId(IdGen.uuid());
//            loanChangeLog.setLoanCollectionOrderId(order.getOrderId());
//            loanChangeLog.setBeforeStatus(order.getStatus());
//            loanChangeLog.setAfterStatus(Constant.STATUS_OVERDUE_FOUR);
//            loanChangeLog.setType(Constant.STATUS_OVERDUE_FIVE);//催收完成
//            loanChangeLog.setOperatorName(Constant.OPERATOR_NAME);
//            loanChangeLog.setRemark(Constant.PAY_MENT_SUCCESS+backUser.getUserName());
//            loanChangeLog.setCompanyId(backUser.getCompanyId());
//            loanChangeLog.setCurrentCollectionUserId(backUser.getUuid());
//            loanChangeLog.setCurrentCollectionUserLevel(backUser.getGroupLevel());
//            loger.error("start-setsetCurrentCollectionOrderLevel="+loanId+"S1Flag="+order.getS1Flag());
//            if(StringUtils.isNotBlank(order.getS1Flag())){
//                loanChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_S1);
//            }else{
//                loanChangeLog.setCurrentCollectionOrderLevel(order.getCurrentOverdueLevel());
//            }
//            loger.error("end-setsetCurrentCollectionOrderLevel="+loanId+"S1Flage="+order.getS1Flag());
//            loger.error("start-setsetCurrentCollectionOrderLevel="+loanId+"Level="+loanChangeLog.getCurrentCollectionOrderLevel());
//            loanChangeLog.setCreateDate(new Date());
//            this.localDataDao.saveLoanChangeLog(loanChangeLog);
//        }
//
//    }
//
//    /**
//     * 验证是否重已经还款完成
//     *
//     * @return true 已经还款完成  false未还款完成
//     */
//    public boolean checkLoanStatus(String id){
//        if(StringUtils.isNotBlank(id)){
//            HashMap<String,String> map = new HashMap<String,String>();
//            map.put("ID", id);
//            String userLoan = this.localDataDao.checkLoanStatus(map);
//            if(userLoan.equals("5")){
//                return true;
//            }else {
//                return false;
//            }
//        }
//        return true;
//    }
//
//}