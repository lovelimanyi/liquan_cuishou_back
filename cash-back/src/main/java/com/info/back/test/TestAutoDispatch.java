package com.info.back.test;

import com.info.back.service.IBackUserService;
import com.info.back.service.ICollectionWithholdingRecordService;
import com.info.back.service.IMmanLoanCollectionOrderService;
import com.info.back.service.TaskJobMiddleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/applicationContext.xml" })
public class TestAutoDispatch {

	@Autowired
	TaskJobMiddleService taskJobMiddleService;

	@Autowired
	IBackUserService backUserService;

	@Autowired
	private IMmanLoanCollectionOrderService mmanLoanCollectionOrderService;
	@Autowired
	private ICollectionWithholdingRecordService collectionWithholdingRecordService;
	@Test
	public void testdispachForLoanId() {

		String loadIds="5680648";
		for (String loadId : loadIds.split(",")) {
			System.err.println("===================testdispachForLoanId start" + loadId);
//			MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderByLoanId(loadId);
//			if(order != null){
					taskJobMiddleService.dispatchforLoanId(loadId,null);
//			}else {
//				System.out.println("借款订单对象为空，借款id = " + loadId);
//			}

			System.err.println("===================testdispachForLoanId end");


			System.out.println("*******************");

		}
//		System.out.println(checkNumer("61232219901"));

	}
//	@Test
//	public static void main(String[] args) {
//		System.out.println(checkNumer("123"));
//	}

//	public static boolean checkNumer(String str){
//		boolean flag = false;
//		char num[] = str.toCharArray();
//		if(num.length < 11){
//			for (char c : num) {
//				if(!Character.isDigit(c)){
//					return flag;
//				}
//				flag = true;
//			}
//		}
//		return flag;
//	}
}
