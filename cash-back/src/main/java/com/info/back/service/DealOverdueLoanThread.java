package com.info.back.service;

import java.util.Date;

import org.apache.log4j.Logger;


public class DealOverdueLoanThread implements Runnable {

	private static Logger logger = Logger
			.getLogger(DealOverdueLoanThread.class);
	private String loanId;
	private TaskJobMiddleService taskJobMiddleService;
	public DealOverdueLoanThread(String loanId,TaskJobMiddleService taskJobMiddleService) {
		this.loanId = loanId;
		this.taskJobMiddleService = taskJobMiddleService;
		
	}

	@Override
	public void run() {
		logger.error("处理逾期订单/派单开始  " + new Date() + "借款id:" + loanId);
		
			try{
				taskJobMiddleService.dispatchforLoanId(loanId);
			}catch(Exception e){
				logger.error("处理逾期订单/派单出错 " + new Date()+ "借款id:" + loanId);
			}
		logger.error("处理逾期订单/派单结束  " + new Date() + "借款id:" + loanId);
	}

}
