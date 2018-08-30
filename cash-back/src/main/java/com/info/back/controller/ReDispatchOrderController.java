package com.info.back.controller;

import com.info.back.task.ReDispatchOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类描述：订单重新分配
 * 创建人：hxj
 * 创建时间：2018-8-29 下午02:57:46
 */
@RestController
@RequestMapping("/order")
public class ReDispatchOrderController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ReDispatchOrderController.class);

    @Autowired
    private ReDispatchOrder reDispatchOrder;


    @RequestMapping(value = "/reDispatch", method = RequestMethod.POST)
    public String reDispatchOrder() {
        logger.info("【重新派单】任务开始执行...");
        try {
            reDispatchOrder.batchdispatchOrder();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("【重新派单】任务执行结束...");
        return "SUCCESS";
    }

}
