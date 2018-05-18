package com.info.back.task;

import com.info.back.service.IEstimateOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskEstimateOrder {

    @Autowired
    private IEstimateOrderService estimateOrderService;

    public void pullEstimateOrder() {
        System.out.println("开始pullEstimateOrder");
        estimateOrderService.pullEstimateOrder(null);
    }
}
