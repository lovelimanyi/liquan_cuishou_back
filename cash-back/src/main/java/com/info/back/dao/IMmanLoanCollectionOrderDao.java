package com.info.back.dao;

import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.pojo.OrderBaseResult;
import com.info.web.pojo.OrderInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface IMmanLoanCollectionOrderDao {

    void upTotalOverdueDays(Map<String, String> map);

    List<String> getOverdueOrder();

    List<MmanLoanCollectionOrder> getOrderList(MmanLoanCollectionOrder mmanLoanCollectionOrder);


    List<MmanLoanCollectionOrder> findList(MmanLoanCollectionOrder queryParam);

    void insertCollectionOrder(MmanLoanCollectionOrder queryParam);


    void updateCollectionOrder(MmanLoanCollectionOrder queryParam);


    int getOrderPageCount(HashMap<String, Object> params);


    MmanLoanCollectionOrder getOrderById(String id);

    /**
     * 标记订单
     *
     * @param params
     * @return
     */
    int updateTopOrder(Map<String, Object> params);


    void updateAuditStatus(HashMap<String, String> params);

    /**
     * 根据订单id查询一条订单记录
     *
     * @param orderId
     * @return
     */
    MmanLoanCollectionOrder getOrderWithId(String orderId);

    /**
     * 减免状态更新
     *
     * @param collectionOrder
     * @return
     */
    int updateJmStatus(MmanLoanCollectionOrder collectionOrder);

    /**
     * 审核更新状态
     *
     * @param params
     */
    void sveUpdateJmStatus(HashMap<String, String> params);


    void updateReductionMoney(HashMap<String, Object> order);

    /**
     * 根据userId获取订单
     */
    MmanLoanCollectionOrder getOrderByUserId(String userId);

    void sveUpdateNotNull(HashMap<String, String> ordermap);

    /**
     * 根据orderId获取baseOrder
     */
    OrderBaseResult getBaseOrderById(String orderId);

    MmanLoanCollectionOrder getOrderloanId(String loanId);


    void updateReductionOrder(HashMap<String, Object> map);

    String getLatestLoanByUserPhoneAndLoanEndTime(HashMap<String, Object> params);

    MmanLoanCollectionOrder getOrderByLoanId(String loanId);

    MmanLoanCollectionOrder getCollectionOrderByLoanId(String loanId);

    OrderInfo getStopOrderInfoById(String id);

    int deleteOrderInfoAndLoanInfoByloanId(String orderId);

    /**
     * 查询一号还款订单信息
     *
     * @param map
     * @return
     */
    List<MmanLoanCollectionOrder> getOrderInFirstDay(HashMap<String, Object> map);

    /**
     * 更新订单信息到指定催收员手上
     *
     * @param map
     * @return
     */
    int updateOrderInfo(HashMap<String, Object> map);

    void updateOrderOverdueDays(MmanLoanCollectionOrder collectionOrder);

    List<String> getOverdueOrderIds(Map<String, Object> map);
    void updateOverdueDays(MmanLoanCollectionOrder order);

    Integer selectOrderCount4SmallEstemate(@Param("groupLevel")String groupLevel);
    Integer selectOrderCount4BigEstemate(@Param("overdueDays")Integer overdueDays);

    void updateProductName(MmanLoanCollectionOrder order);

    void updateUpgradeOrder(MmanLoanCollectionOrder order);

    List<MmanLoanCollectionOrder> getLastMonthOrder(String getLastMonthOrder);

    void updateVirtualDispathTime(String loanId);
}
