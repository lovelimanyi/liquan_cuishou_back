package com.info.back.task;

import com.info.back.service.IMmanLoanCollectionOrderService;
import org.apache.log4j.Logger;
import java.util.Date;

public class UpdateTotalOverdueDaysThread implements Runnable {

    private static Logger logger = Logger.getLogger(UpdateTotalOverdueDaysThread.class);
    private String loanId;
    private IMmanLoanCollectionOrderService orderService;

    public UpdateTotalOverdueDaysThread(String loanId, IMmanLoanCollectionOrderService orderService) {
        this.loanId = loanId;
        this.orderService = orderService;
    }

    @Override
    public void run() {
        logger.info("更新订单总逾期天数开始  " + new Date().toLocaleString() + " 借款id:" + loanId);

        try {
            orderService.updateTotalOverdueDays(loanId);
        } catch (Exception e) {
            logger.error("更新订单总逾期天数出错 " + new Date().toLocaleString() + " 借款id:" + loanId);
            e.printStackTrace();
        }
        logger.info("更更新订单总逾期天数结束  " + new Date().toLocaleString() + " 借款id:" + loanId);
    }

}
