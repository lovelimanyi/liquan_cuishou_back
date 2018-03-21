package com.info.back.task;

import com.info.back.service.IMmanLoanCollectionOrderService;
import com.info.back.utils.BackConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Administrator
 * @Description: 订单逾期升级定时任务
 * @Params
 * @Return
 * @CreateTime 2018/03/07  上午 11:29
 */
@Component
public class TaskDealwithOrderUpgrade {
    protected Logger logger = Logger.getLogger(TaskDealwithOrderUpgrade.class);

    @Autowired
    private IMmanLoanCollectionOrderService orderService;

    public void orderUpgrade() {
        Map<String, Object> map = new HashMap<>();
        map.put("orderStatus", BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS);
        List<String> loanIds = orderService.getOverdueOrderIds(map);
        ThreadPoolDealwithOrderUpgrade pool = ThreadPoolDealwithOrderUpgrade.getInstance();
        pool.setDaemon(true);
        for (String loanId : loanIds) {
            try {
                if (StringUtils.isEmpty(loanId)) {
                    continue;
                }
                DealwithOrderUpgradeThread thread = new DealwithOrderUpgradeThread(loanId, orderService);
                pool.execute(thread);
            } catch (Exception e) {
                logger.error("处理逾期订单升级出错，借款id: " + loanId);
                e.printStackTrace();
                continue;
            }
        }
    }
}
