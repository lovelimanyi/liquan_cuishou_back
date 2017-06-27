package com.info.back.test;

import com.info.back.service.IBackUserService;
import com.info.back.service.IMmanUserInfoService;
import com.info.back.service.IMmanUserLoanService;
import com.info.back.service.TaskJobMiddleService;
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
	private IMmanUserInfoService mmanUserInfoService;

	@Autowired
	private IMmanUserLoanService mmanUserLoanService;

	@Test
	public void testdispachForLoanId() {

		String loadIds="2894512";
		for (String loadId : loadIds.split(",")) {
			System.err.println("===================testdispachForLoanId start" + loadId);
			MmanUserLoan mmanUserLoan = mmanUserLoanService.get(loadId);
			MmanUserInfo userInfo = mmanUserInfoService.getUserInfoById(mmanUserLoan.getUserId());
			if(userInfo != null){
					taskJobMiddleService.dispatchforLoanId(loadId,userInfo.getIdNumber());
			}else {
				System.out.println("借款人信息对象为空，借款id = " + loadId);
			}

			System.err.println("===================testdispachForLoanId end");
		}

	}

}
