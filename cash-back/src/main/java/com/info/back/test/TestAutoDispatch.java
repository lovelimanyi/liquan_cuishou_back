package com.info.back.test;

import com.info.back.service.*;
import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.pojo.MmanUserInfo;
import com.info.web.pojo.MmanUserLoan;
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

	@Test
	public void testdispachForLoanId() {

		String loadIds="3198727";
		for (String loadId : loadIds.split(",")) {
			System.err.println("===================testdispachForLoanId start" + loadId);
			MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderByLoanId(loadId);
			if(order != null){
					taskJobMiddleService.dispatchforLoanId(loadId,null);
			}else {
				System.out.println("借款订单对象为空，借款id = " + loadId);
			}

			System.err.println("===================testdispachForLoanId end");
		}

	}

}
