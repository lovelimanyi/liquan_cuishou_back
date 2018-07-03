package com.info.back.task;

import com.info.back.service.IMmanLoanCollectionOrderService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lmy
 * @Description: 更新催收订单的订单总逾期天数（大订单逾期天数）字段，即total_overdueDays字段
 * @CreateTime 2018-06-29 下午 4:45
 **/
@Component
public class TaskUpdateTotalOverdueDays {
    Logger logger = Logger.getLogger(TaskUpdateOrderInfo.class);

    @Autowired
    private IMmanLoanCollectionOrderService orderService;

    /**
     * 更新催收订单的订单总逾期天数（大订单逾期天数）字段，即total_overdueDays字段
     */
    public void updateTotalOverdueDays() {
        logger.info("更新订单总逾期天数（大订单逾期天数）字段开始... ");
        List<String> loanIds = orderService.getOverdueOrder();
        System.out.println("111111111111");
        System.out.println(loanIds);
        System.out.println("222222222222");
        ThreadPoolUpdateTotalOverdueDays poolUpdateTotalOverdueDays = ThreadPoolUpdateTotalOverdueDays.getInstance();
        poolUpdateTotalOverdueDays.setDaemon(true);
        for (String loanId : loanIds) {
            try {
                if (StringUtils.isEmpty(loanId)) {
                    continue;
                }
                UpdateTotalOverdueDaysThread thread = new UpdateTotalOverdueDaysThread(loanId, orderService);
                poolUpdateTotalOverdueDays.execute(thread);
            } catch (Exception e) {
                logger.error("更新订单总逾期天数（大订单逾期天数）字段出错，订单id: " + loanId);
                e.printStackTrace();
                continue;
            }
        }
        logger.info("更新订单总逾期天数（大订单逾期天数）字段完成...");
    }
}
