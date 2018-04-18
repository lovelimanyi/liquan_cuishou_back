package com.info.back.test;

import com.info.back.service.IBackUserService;
import com.info.back.service.ICollectionWithholdingRecordService;
import com.info.back.service.IMmanLoanCollectionOrderService;
import com.info.back.service.TaskJobMiddleService;
import com.info.constant.Constant;
import com.info.web.pojo.MmanLoanCollectionOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/applicationContext.xml"})
public class TestAutoDispatch {

    @Autowired
    TaskJobMiddleService taskJobMiddleService;

    @Autowired
    IBackUserService backUserService;

    @Autowired
    private IMmanLoanCollectionOrderService orderService;
    @Autowired
    private ICollectionWithholdingRecordService collectionWithholdingRecordService;

    //
    @Test
    public void testdispachForLoanId() {

        String loadIds = "545257745";
        for (String loadId : loadIds.split(",")) {
            System.err.println("===================testdispachForLoanId start" + loadId);
//            orderService.dispatchOrderNew("545258552", "123456789", "2");
            MmanLoanCollectionOrder order = orderService.getOrderByLoanId(loadId);
            if (order != null) {
//                taskJobMiddleService.dispatchforLoanId(order.getLoanId(), null, "1");
//                mmanLoanCollectionOrderService.orderUpgrade(loadId);
            } else {
                System.out.println("借款订单对象为空，借款id = " + loadId);
            }

            System.err.println("===================testdispachForLoanId end");


            System.out.println("*******************");
        }
    }

    @Test
    public void testOrderUpGrade() {
        String id = "10379152";
        orderService.orderUpgrade(id);
    }


    @Test
    public void testBigOrderDispatch() {
        String id = "142995-1";
        String idNumber = "123456789987";
        orderService.dispatchOrderNew(id, idNumber, Constant.BIG);
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
