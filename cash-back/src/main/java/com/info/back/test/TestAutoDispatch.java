package com.info.back.test;

import com.info.back.dao.IMmanLoanCollectionOrderDao;
import com.info.back.dao.ITemplateSmsDao;
import com.info.back.service.*;
import com.info.back.task.TaskDealwithOrderUpgrade;
import com.info.back.task.TaskUpdateOrderInfo;
import com.info.back.task.TaskUpdateTotalOverdueDays;
import com.info.constant.Constant;
import com.info.web.pojo.*;
import com.info.web.synchronization.service.DataSyncService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/applicationContext.xml"})
public class TestAutoDispatch {

    private static Logger log = Logger.getLogger(TestAutoDispatch.class);
    @Autowired
    private ICreditLoanPayService payService;
    @Autowired
    private IMmanLoanCollectionOrderService orderService;
    @Autowired
    private ICollectionWithholdingRecordService collectionWithholdingRecordService;
    @Autowired
    private IBackConfigParamsService backConfigParamsService;
    @Autowired
    private IMmanUserInfoService mmanUserInfoService;
    @Autowired
    private IMmanLoanCollectionOrderService mmanLoanCollectionOrderService;
    @Autowired
    private ITemplateSmsDao templateSmsDao;
    @Autowired
    private IMmanLoanCollectionOrderDao mmanLoanCollectionOrderDao;
    @Autowired
    private MmanLoanCollectionOrderService mmanLoanCollectionOrderService2;
    @Autowired
    private TaskUpdateOrderInfo taskUpdateOrderInfo;
    @Autowired
    private DataSyncService dataSyncService;
    @Autowired
    private TaskUpdateTotalOverdueDays taskUpdateTotalOverdueDays;
    @Autowired
    private TaskDealwithOrderUpgrade taskDealwithOrderUpgrade;

    @Test
    public void test1() {
        System.out.println(mmanLoanCollectionOrderService2.isBeforePeroidCollectionCompleted("143595515"));
        System.out.println(mmanLoanCollectionOrderService2.isBeforePeroidCollectionCompleted("143490-1"));
        System.out.println(mmanLoanCollectionOrderService2.isBeforePeroidCollectionCompleted("143490-2"));
    }

    @Test
    public void test2() {
        MmanUserLoan mmanUserLoan = new MmanUserLoan();
        MmanUserLoan mmanUserLoan2;
        mmanUserLoan.setBorrowingType("1");//大额
        mmanUserLoan.setId("9000000156-8");
        mmanUserLoan2 = mmanLoanCollectionOrderService2.getFirstMmanUserLoan(mmanUserLoan);
        System.out.println(mmanUserLoan2);
    }

    @Test
    public void test3() {
        MmanUserLoan mmanUserLoan = new MmanUserLoan();
        mmanUserLoan.setBorrowingType("1");//大额
        mmanUserLoan.setId("9000000063-2");
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-21 00:00:00");
            mmanUserLoan.setLoanEndTime(date);
        }catch (Exception e){
            System.out.println("ddddddddd");
        }
        int i = mmanLoanCollectionOrderService2.getOverdueDays(mmanUserLoan);
        System.out.println(i);
        System.out.println(i);
    }
    @Test
    public void test4() {
        mmanLoanCollectionOrderService2.dispatchOrderNew("143937-3","412726198911142015","1");
    }

    @Test
    public void test6() {
        mmanLoanCollectionOrderService2.orderUpgrade("143848-1");
    }

    @Test
    public void test7() {
        dataSyncService.syncOverdueDate();
    }

    @Test
    public void test8() {
        taskUpdateTotalOverdueDays.updateTotalOverdueDays();
    }

    @Test
    public void test9() {
        mmanLoanCollectionOrderService2.updateTotalOverdueDays("143936-2");
    }

    @Test
    public void test10() {
        taskDealwithOrderUpgrade.orderUpgrade();
    }
    @Test
    public void test11() {
        String [] loanIds={"143936-2"};
        for (int i=0;i<loanIds.length;i++){
            mmanLoanCollectionOrderService2.dealwithBigOrderUpgrade(loanIds[i]);
        }
    }





    //只更新小额
    @Test
    public void test5() {
        mmanLoanCollectionOrderService2.updateOrderInfo("143936-2 " );
    }

    //
    @Test
    public void testdispachForLoanId() {
        String loadIds = "";
        for (String loadId : loadIds.split(",")) {
            System.err.println("===================testdispachForLoanId start" + loadId);
//            orderService.dispatchOrderNew("545258552", "123456789", "2");
//            MmanLoanCollectionOrder order = orderService.getOrderByLoanId(loadId);

            orderService.dispatchOrderNew(loadId, null, Constant.SMALL);

        }
    }

    @Test
    public void testOrderUpGrade() {
//        String id = "10379152";
//        orderService.orderUpgrade(id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sysType", "SMTP");
        List<BackConfigParams> mailParams = backConfigParamsService.findParams(map);
        System.out.println(mailParams);
        Map<String, Object> mailMap = new HashMap<>();
        for (BackConfigParams param : mailParams) {
            mailMap.put(param.getSysKey(), param.getSysValue());
        }
        System.out.println(mailMap);


//        int res = sendEmail("smtp.qq.com", "413977035@qq.com", "bozvkenlsqnobiec", "413977035@qq.com", new String[]{
//                "85442776@qq.com" //这里就是一系列的收件人的邮箱了
//        }, "节日祝福", "祝你国庆节快乐,欢迎来我的blog: <a href='http://blog.csdn.net/u013871100'>我的blog</a>,祝您生活愉快!", "text/html;charset=utf-8");
//
//        System.out.println("\n发送结果:" + res);
    }


    @Test
    public void testBigOrderDispatch() {
        /*String id = "142995-1";
        String idNumber = "123456789987";
        orderService.dispatchOrderNew(id, idNumber, Constant.BIG);*/

        MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderByLoanId("143405-1");
        MmanUserInfo userInfo = mmanUserInfoService.getUserInfoById(order.getUserId());
        CreditLoanPay pay = payService.findByLoanId(order.getLoanId());
        List<TemplateSms> msgList = templateSmsDao.getMsgs();
        int code = RandomUtils.nextInt(0, msgList.size());
        TemplateSms msg = msgList.get(code);
        StringBuilder msgParam = new StringBuilder();
        if (StringUtils.isNotEmpty(userInfo.getUserSex())) {
            if ("男".equals(userInfo.getUserSex())) {
                msgParam.append(order.getLoanUserName() + "先生");
            } else {
                msgParam.append(order.getLoanUserName() + "女士");
            }

        }
        msgParam.append(",");
        msgParam.append(order.getOverdueDays()).append(",").append(100);

        String content = MessageFormat.format(msg.getContenttext(), StringUtils.split(msgParam.toString(), ','));
        System.out.println(content);
    }
}


//		System.out.println(checkNumer("61232219901"));
//
//
//
//
//    }
////    @Test
////    public void testDispatch() {
////        taskJobMiddleService.autoDispatch();
////    }
////	@Test
////	public static void main(String[] args) {
////		System.out.println(checkNumer("123"));
////	}
//
////	public static boolean checkNumer(String str){
////		boolean flag = false;
////		char num[] = str.toCharArray();
////		if(num.length < 11){
////			for (char c : num) {
////				if(!Character.isDigit(c)){
////					return flag;
////				}
////				flag = true;
////			}
////		}
////		return flag;
////	}
//}
