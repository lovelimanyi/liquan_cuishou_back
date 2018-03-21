package com.info.back.task;

import com.info.back.service.ICreditLoanPayService;
import com.info.back.service.IMmanLoanCollectionOrderService;
import com.info.back.service.IMmanUserLoanService;
import com.info.back.utils.BackConstant;
import com.info.constant.Constant;
import com.info.web.pojo.CreditLoanPay;
import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.pojo.MmanUserLoan;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @Description: 更新逾期订单信息
 * @CreateTime 2018-03-05 下午 4:45
 **/

@Component
public class TaskUpdateOrderInfo {

    Logger logger = Logger.getLogger(TaskUpdateOrderInfo.class);

    @Autowired
    private IMmanUserLoanService mmanUserLoanService;

    @Autowired
    private IMmanLoanCollectionOrderService orderService;

    /**
     * 只更新原来小额订单来源的订单，经过交易系统的订单由对方更新后推送给我们
     */
    public void updateOrderInfo() {
        logger.info("更新逾期订单开始... ");
        Map<String, Object> map = new HashMap<>();
        map.put("status", BackConstant.CREDITLOANAPPLY_OVERDUE);
        map.put("borrowingType", Constant.SMALL);
        List<String> loanIds = mmanUserLoanService.getOverdueOrder(map);
        ThreadPoolUpdateOrderInfo poolUpdateOrderInfo = ThreadPoolUpdateOrderInfo.getInstance();
        poolUpdateOrderInfo.setDaemon(true);
        for (String loanId : loanIds) {
            try {
                if (StringUtils.isEmpty(loanId)) {
                    continue;
                }
                UpdateOrderInfoThread thread = new UpdateOrderInfoThread(loanId, orderService);
                poolUpdateOrderInfo.execute(thread);
            } catch (Exception e) {
                logger.error("更新逾期订单出错，订单id: " + loanId);
                e.printStackTrace();
                continue;
            }
        }
        logger.info("更新逾期订单完成...");
    }
}
