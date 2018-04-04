package com.info.back.task;

import com.info.back.service.IMmanLoanCollectionOrderService;
import org.apache.log4j.Logger;

import java.util.Date;


public class UpdateOrderInfoThread implements Runnable {

    private static Logger logger = Logger.getLogger(UpdateOrderInfoThread.class);
    private String loanId;
    private IMmanLoanCollectionOrderService orderService;

    public UpdateOrderInfoThread(String loanId, IMmanLoanCollectionOrderService orderService) {
        this.loanId = loanId;
        this.orderService = orderService;
    }

    @Override
    public void run() {
        logger.info("更新逾期订单开始  " + new Date().toLocaleString() + " 借款id:" + loanId);

        try {
            orderService.updateOrderInfo(loanId);
        } catch (Exception e) {
            logger.error("更新逾期订单出错 " + new Date().toLocaleString() + " 借款id:" + loanId);
            e.printStackTrace();
        }
        logger.info("更新逾期订单结束  " + new Date().toLocaleString() + " 借款id:" + loanId);
    }

}
