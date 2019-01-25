package com.info.back.task;

import com.info.back.service.IMmanLoanCollectionOrderService;
import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.util.DateUtil;
import com.info.web.util.ThreadPoolInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类描述：上月未还款完成订单，流转至当月继续催收，增加虚拟派单时间，增加一条对应的流转日志。
 * 创建人：yyf
 * 创建时间：2019/1/22 0022上午 11:44
 */
@Service
public class TaskOrderExchangeNextMonth {

    @Autowired
    private IMmanLoanCollectionOrderService orderService;

    public void handleLastMonthOrder(){

        String lastMonthFistDay = DateUtil.getDateFormat(DateUtil.getLastMonthFirstDay(),"yyyy-MM-dd");
        List<MmanLoanCollectionOrder> orders = orderService.getLastMonthOrder(lastMonthFistDay);
        for (MmanLoanCollectionOrder order : orders){
            ThreadPoolInstance.getInstance().doExecute(new HandleOrder(order,orderService));
        }
    }

     class HandleOrder implements Runnable {
        private MmanLoanCollectionOrder order;
        private IMmanLoanCollectionOrderService orderService;
        public HandleOrder(MmanLoanCollectionOrder order,IMmanLoanCollectionOrderService orderService) {
            this.order = order;
            this.orderService = orderService;
        }
         @Override
         public void run() {
             orderService.handleLastMonthOrder(order);
         }
     }
}
