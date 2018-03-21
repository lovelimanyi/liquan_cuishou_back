package com.info.back.task;

import com.info.back.service.IMmanLoanCollectionOrderService;
import org.apache.log4j.Logger;

import java.util.Date;


public class DealwithOrderUpgradeThread implements Runnable {

    private static Logger logger = Logger.getLogger(DealwithOrderUpgradeThread.class);
    private String loanId;
    private IMmanLoanCollectionOrderService orderService;

    public DealwithOrderUpgradeThread(String loanId, IMmanLoanCollectionOrderService orderService) {
        this.loanId = loanId;
        this.orderService = orderService;
    }

    @Override
    public void run() {
        logger.info("处理订单逾期升级开始  " + new Date().toLocaleString() + " 借款id:" + loanId);

        try {
            orderService.orderUpgrade(loanId);
        } catch (Exception e) {
            logger.error("处理订单逾期升级出错 " + new Date().toLocaleString() + " 借款id:" + loanId);
            e.printStackTrace();
        }
        logger.info("处理订单逾期升级结束  " + new Date().toLocaleString() + " 借款id:" + loanId);
    }

}
