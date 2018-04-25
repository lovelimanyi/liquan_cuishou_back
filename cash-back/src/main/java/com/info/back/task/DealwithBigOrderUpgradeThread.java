package com.info.back.task;

import com.info.back.service.IMmanLoanCollectionOrderService;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * 大额订单逾期升级处理
 */
public class DealwithBigOrderUpgradeThread implements Runnable {

    private static Logger logger = Logger.getLogger(DealwithBigOrderUpgradeThread.class);
    private String loanId;
    private IMmanLoanCollectionOrderService orderService;

    public DealwithBigOrderUpgradeThread(String loanId, IMmanLoanCollectionOrderService orderService) {
        this.loanId = loanId;
        this.orderService = orderService;
    }

    @Override
    public void run() {
        logger.info("处理订单逾期升级开始  " + new Date().toLocaleString() + " 借款id:" + loanId);

        try {
            orderService.dealwithBigOrderUpgrade(loanId);
        } catch (Exception e) {
            logger.error("处理订单逾期升级出错 " + new Date().toLocaleString() + " 借款id:" + loanId);
            e.printStackTrace();
        }
        logger.info("处理订单逾期升级结束  " + new Date().toLocaleString() + " 借款id:" + loanId);
    }

}
