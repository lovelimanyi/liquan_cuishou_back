//package com.info.back.test;
//
//import com.info.back.dao.ILocalDataDao;
//import com.info.constant.Constant;
//import com.info.web.synchronization.RedisUtil;
//import com.info.web.synchronization.dao.IDataDao;
//import org.apache.log4j.Logger;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.HashMap;
//
//
///**
// * Created by Administrator on 2017/5/4 0004.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"file:src/config/applicationContext.xml"})
//public class Test_RenewalThread {
//    private static Logger loger = Logger.getLogger(Test_RenewalThread.class);
//
//    @Autowired
//    private IDataDao dataDao;
//    @Autowired
//    private ILocalDataDao localDataDao;
//    @Test
//    public void test() {
//        String repaymentId ="309102";
//        HashMap<String,String> map = new HashMap<String,String>();
//        map.put("ID", repaymentId);
//        HashMap<String,Object> repayment = this.dataDao.getAssetRepayment(map);
//        loger.info("repayment="+repayment);
//        if(null!=repayment){
//            try{
//                String loanId = String.valueOf(repayment.get("asset_order_id"));//还款id
//                //删除对应的借款表，订单表，还款表，还款详情表，催收记录表，催收流转日志表
//                localDataDao.deleteOrderAndOther(loanId);
//
//                RedisUtil.delRedisKey(Constant.TYPE_RENEWAL_+repaymentId);
//            }catch(Exception e0) {
//                e0.printStackTrace();
//            }
//        }
//    }
//
//
//}
