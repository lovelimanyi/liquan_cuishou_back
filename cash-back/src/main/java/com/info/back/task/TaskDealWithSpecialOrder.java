package com.info.back.task;

import com.info.back.dao.IMmanLoanCollectionOrderDao;
import com.info.back.service.IBackUserService;
import com.info.back.utils.BackConstant;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.MmanLoanCollectionOrder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 * @Description: 应产品需求，修改每月1号还款订单至特定催收员名下
 * @CreateTime 2017-11-13 上午 10:27
 **/

@Component
public class TaskDealWithSpecialOrder {

    private static Logger logger = Logger.getLogger(TaskDealWithSpecialOrder.class);

    @Autowired
    private IMmanLoanCollectionOrderDao mmanLoanCollectionOrderDao;
    @Autowired
    private IBackUserService backUserService;

    public void updateOrderInfo() {
        logger.info("开始执行修改1号完成订单信息......");
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderStatus", BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS);  // 催收完成订单
        map.put("overDueLevel", BackConstant.XJX_OVERDUE_LEVEL_S2);  // 逾期等级s2（4）
        List<MmanLoanCollectionOrder> orderList = mmanLoanCollectionOrderDao.getOrderInFirstDay(map);
        int count = 0;
        int totalCount = 0;
        totalCount = updateOrderAndLog(orderList, totalCount);
        logger.info("修改订单信息完成，本次共修改数据 " + totalCount + " 条.");
    }

    /**
     * 更新订单信息和流转日志
     * @param orderList
     * @param totalCount
     * @return
     */
    private int updateOrderAndLog(List<MmanLoanCollectionOrder> orderList, int totalCount) {
        int count;
        if (orderList.size() > 0) {
            BackUser user = backUserService.getBackUserByUuid(BackConstant.COLLECTION_USER_ID);
            HashMap<String, Object> param = new HashMap<>();
            param.put("companyId", user.getCompanyId());
            param.put("orderCompanyId", user.getCompanyId());
            param.put("currentCollectionUserId", user.getUuid());
            param.put("orderCurrentCollectionUserId", user.getUuid());
            param.put("collectionUserOverDueLevel", user.getGroupLevel());
            param.put("orderCollectionUserOverDueLevel", user.getGroupLevel());
            param.put("remark", "还款成功催收员:" + user.getUserName());
            for (MmanLoanCollectionOrder order : orderList) {
                param.put("orderLevel", "".equals(order.getS1Flag()) ? order.getCurrentOverdueLevel() : 3);
                param.put("loanId", order.getLoanId());
                count = mmanLoanCollectionOrderDao.updateOrderInfo(param);
                totalCount += count;
            }
        }
        return totalCount;
    }
}
