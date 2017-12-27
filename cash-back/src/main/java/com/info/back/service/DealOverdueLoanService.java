package com.info.back.service;

import com.info.back.dao.IMmanUserLoanDao;
import com.info.back.service.TaskJobMiddleService;
import com.info.back.utils.BackConstant;
import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.pojo.MmanUserInfo;
import com.info.web.pojo.MmanUserLoan;
import com.info.web.util.DateUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



@Service
public class DealOverdueLoanService {
    private static Logger loger = Logger.getLogger(DealOverdueLoanService.class);
    @Autowired
    private TaskJobMiddleService taskJobMiddleService;
    @Autowired
    private IMmanUserLoanDao mmanUserLoanDao;
    @Autowired
	private IBackUserService backUserService;
    @Autowired
	private IMmanLoanCollectionOrderService manLoanCollectionOrderService;


    public void dealOverdueLoan() throws Exception{
        loger.error("处理预期升级订单 开始" + DateUtil.getDateFormat(new Date(), "yyyy-MM-dd hh:mm:ss"));
        MmanUserLoan mmanUserLoan = new MmanUserLoan();
		mmanUserLoan.setLoanStatus(BackConstant.CREDITLOANAPPLY_OVERDUE);
		List<MmanUserLoan> overdueList = Collections.synchronizedList(new ArrayList<MmanUserLoan>()); 
		overdueList = mmanUserLoanDao.findMmanUserLoanList2(mmanUserLoan);
        ThreadPoolDealOverdueLoan pool = ThreadPoolDealOverdueLoan.getInstance();
        for (MmanUserLoan loan : overdueList) {
        	synchronized(this)
        	{
				MmanLoanCollectionOrder order = manLoanCollectionOrderService.getOrderByLoanId(loan.getId());
				if(order != null){
					DealOverdueLoanThread dealOverdueOrderThread = new DealOverdueLoanThread(loan.getId(),taskJobMiddleService,order.getIdNumber(),loan.getBorrowingType());
					pool.execute(dealOverdueOrderThread);
				}
        	}
		}
		loger.error("处理预期升级订单 结束" + DateUtil.getDateFormat(new Date(), "yyyy-MM-dd hh:mm:ss"));

//		Calendar clrNow = Calendar.getInstance();
//		int dayNow = clrNow.get(Calendar.DAY_OF_MONTH);
        // 启用催收员（每月1号）
//		if(dayNow == 1){
//			try{
//				backUserService.enableCollections();
//			}catch (Exception e){
//				loger.error("启用催收员出错，执行时间：" + DateUtil.getDateFormat(new Date(), "yyyy-MM-dd hh:mm:ss"));
//			}
//		}
    }
    
    
    public static void main(String[] args) {
    	try{
			new DealOverdueLoanService().dealOverdueLoan();
		}catch (Exception e){
			loger.error("");
		}
	}
}
