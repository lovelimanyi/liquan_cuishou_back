package com.info.back.task;

import com.google.common.collect.Lists;
import com.info.back.dao.IMmanLoanCollectionOrderDao;
import com.info.back.dao.IMmanLoanCollectionStatusChangeLogDao;
import com.info.back.dao.IOrderDispatchRuleDao;
import com.info.back.result.JsonResult;
import com.info.back.service.IBackUserService;
import com.info.back.service.IMmanLoanCollectionOrderService;
import com.info.back.service.IMmanUserLoanService;
import com.info.back.utils.BackConstant;
import com.info.back.utils.IdGen;
import com.info.constant.Constant;
import com.info.web.pojo.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Administrator
 * @Description: 重新分派订单（针对特殊需求使用）
 * @CreateTime 2018-08-29 下午 3:01
 **/

@Component
public class ReDispatchOrder {
    private static Logger log = Logger.getLogger(ReDispatchOrder.class);
    @Autowired
    private IMmanLoanCollectionOrderService orderService;
    @Autowired
    private IBackUserService userService;
    @Autowired
    private IMmanLoanCollectionStatusChangeLogDao statusChangeLogDao;
    @Autowired
    private IMmanLoanCollectionOrderDao orderDao;
    @Autowired
    private IMmanUserLoanService loanService;
    @Autowired
    private IOrderDispatchRuleDao ruleDao;


    public void batchdispatchOrder() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("orderStatus", BackConstant.XJX_COLLECTION_ORDER_STATE_PAYING);
        map.put("borrowingType", Constant.SMALL);
        map.put("groupLevel", BackConstant.XJX_OVERDUE_LEVEL_M6P);

        // 查询所有的分单规则信息
        List<OrderDispatchRule> ruleList = ruleDao.listAllInfo();
        if (CollectionUtils.isEmpty(ruleList)) {
            log.info("没有对应的分派规则...");
            return;
        }
        long totalBeginTime = System.nanoTime();
        for (OrderDispatchRule rule : ruleList) {
            // 查询对应的公司对应催收员
            long beginTime = System.nanoTime();
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("companyId", rule.getCompanyId());
            userMap.put("groupLevel", rule.getGroupLevel());
            userMap.put("status", 1);
            List<BackUser> userList = userService.getUserByCompanyIdAndGroupLevel(userMap);
            if (CollectionUtils.isEmpty(userList)) {
                log.error("查该公司无可用催收员... 催收公司：" + rule.getCompanyName() + " 催收组：" + rule.getGroupLevel());
                continue;
            }
            // 计算该公司的总应分派单数
            int totalCount = userList.size() * rule.getAverageOrder();
            log.info("开始处理催收公司：" + rule.getCompanyName() + " 派单，公司 " + rule.getGroupLevel() + " 组共计催收员 "
                    + userList.size() + " 个，人均 " + rule.getAverageOrder() + " 单，总计应分单 " + totalCount + " 单");
            // 查询出对应订单
            map.put("totalOrderCount", totalCount);
            List<String> orders = loanService.getOverdueOrderIdsNeedBeDispatch(map);
            List<List<String>> lists = Lists.partition(orders, rule.getAverageOrder());
            int i = 0;
            for (List<String> list : lists) {
                BackUser backUser = userList.get(i);
                System.out.println("当前第 " + (i + 1) + " 个催收员，姓名：" + backUser.getUserName());
                try {
                    // 转派
                    dispatchOrder(list, backUser);
                    //更新已经处理的订单（做标记）
                    loanService.updateRemark(list);
                } catch (Exception e) {
                    log.error("转单出错！");
                    e.printStackTrace();
                    continue;
                }
                i++;
            }
            long endTime = System.nanoTime();
            log.info(rule.getCompanyName() + " 派单处理完成，总计派单 " + orders.size() + "单，共计耗时 " + (endTime - beginTime) / 1000000000 + " 秒");
        }
        long totalEndTime = System.nanoTime();
        log.info("派单处理完成，共计耗时 " + (totalEndTime - totalBeginTime) / 1000000000 + " 秒");
    }

    /**
     * @param orderIds
     * @param currentUser
     * @return
     */
    private JsonResult dispatchOrder(List<String> orderIds, BackUser currentUser) {
        JsonResult result = new JsonResult("-1", "转派失败，未知异常");
        //更新我的催收订单
        String currentCollectionUserId = currentUser.getUuid();
        if (orderIds != null && orderIds.size() > 0) {
            int successCount = 0;
            for (String orderId : orderIds) {
                //原始催收订单
                MmanLoanCollectionOrder order = orderService.getOrderByLoanId(orderId);
                if (checkOrderStatus(order)) {
                    continue;
                }
                try {
                    HashMap<String, String> params = new HashMap<>(8);
                    params.put("currentCollectionUserId", currentCollectionUserId);
                    params.put("orderId", orderId);
                    params.put("companyId", currentUser.getCompanyId());
                    params.put("grouplevel", currentUser.getGroupLevel());
                    MmanLoanCollectionPerson person = new MmanLoanCollectionPerson();
                    person.setId(currentUser.getId() + "");

                    //催收订单状态
                    String beforeStatus = order.getStatus();
                    order.setStatus(BackConstant.XJX_COLLECTION_ORDER_STATE_OUTSIDE);//委外

                    //被转派人所在催收组
                    String currentOverdueLevel = currentUser.getGroupLevel();

                    //转派后要将当前级催收状态初始化
                    order.setM6ApproveId(currentCollectionUserId);
                    order.setM6OperateStatus(BackConstant.OFF);
                    order.setCurrentOverdueLevel(currentOverdueLevel);
                    order.setLastCollectionUserId(order.getCurrentCollectionUserId());//上一催收员
                    order.setCurrentCollectionUserId(currentCollectionUserId);
                    order.setOutsideCompanyId(currentUser.getCompanyId());
                    order.setOperatorName("系统转派");
                    order.setDispatchName("系统转派");
                    order.setDispatchTime(new Date());
                    order.setRemark("[系统]转派给[" + currentUser.getUserName() + "]");

                    //更新聚信立报告申请审核状态为初始状态，下一催收员要看需要重新申请
                    order.setJxlStatus(BackConstant.XJX_JXL_STATUS_REFUSE);
                    Date now = new Date();
                    order.setUpdateDate(now);
                    orderDao.updateCollectionOrder(order);

                    saveLog(currentUser, order, beforeStatus, now);
                    successCount++;
                    System.out.println("转单成功：" + "当前第 " + successCount + " 单" + "【当前订单：" + orderId + "】,当前催收员：【" + currentUser.getUserName() + "】");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (successCount == orderIds.size()) {
                result.setMsg("转派成功");
                result.setCode("0");
            }
            return result;
        }
        return null;
    }

    private boolean checkOrderStatus(MmanLoanCollectionOrder order) {
        if ("-1".equals(order.getStatus())) {
            log.info("停催订单不能转派！订单id " + order.getLoanId());
            return true;
        }
        if ("4".equals(order.getStatus())) {
            log.info("催收成功的订单不能转派!订单id " + order.getLoanId());
            return true;
        }
        return false;
    }


    /**
     * 保存转派的流转日志
     *
     * @param currentUser
     * @param order
     * @param beforeStatus
     * @param now
     */
    private void saveLog(BackUser currentUser, MmanLoanCollectionOrder order, String beforeStatus, Date now) {
        MmanLoanCollectionStatusChangeLog mmanLoanCollectionStatusChangeLog = new MmanLoanCollectionStatusChangeLog();
        mmanLoanCollectionStatusChangeLog.setType(BackConstant.XJX_COLLECTION_STATUS_MOVE_TYPE_OTHER);//委外
        mmanLoanCollectionStatusChangeLog.setCurrentCollectionOrderLevel(order.getCurrentOverdueLevel());
        //添加转派记录
        mmanLoanCollectionStatusChangeLog.setLoanCollectionOrderId(order.getOrderId());
        mmanLoanCollectionStatusChangeLog.setCompanyId(currentUser.getCompanyId());
        mmanLoanCollectionStatusChangeLog.setBeforeStatus(beforeStatus);
        mmanLoanCollectionStatusChangeLog.setAfterStatus(order.getStatus());
        mmanLoanCollectionStatusChangeLog.setOperatorName("系统转派");
        mmanLoanCollectionStatusChangeLog.setRemark("转单，催收人：" + currentUser.getUserName() + "，手机：" + currentUser.getUserMobile());
        mmanLoanCollectionStatusChangeLog.setId(IdGen.uuid());
        mmanLoanCollectionStatusChangeLog.setCreateDate(now);
        mmanLoanCollectionStatusChangeLog.setCurrentCollectionUserId(currentUser.getUuid());   //订单转派后的催收人
        mmanLoanCollectionStatusChangeLog.setCurrentCollectionUserLevel(currentUser.getGroupLevel());
        statusChangeLogDao.insert(mmanLoanCollectionStatusChangeLog);
    }
}
