package com.info.back.service;

import com.info.back.result.JsonResult;
import com.info.web.pojo.*;
import com.info.web.util.PageConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IMmanLoanCollectionOrderService {

	/**
	 *查询MmanLoanCollectionOrder表所有逾期未催收完成的大额订单；
	 *
	 */
	List<String> getOverdueOrder();

	void updateTotalOverdueDays(String loanId);

	/**
	 * 根据条件查询符合的订单
	 * @param mmanLoanCollectionOrder
	 * @return
	 */
	 List<MmanLoanCollectionOrder> getOrderList(MmanLoanCollectionOrder mmanLoanCollectionOrder);


	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
	 PageConfig<MmanLoanCollectionOrder> findPage(HashMap<String, Object> params);


	 PageConfig<OrderBaseResult> getPage(HashMap<String, Object> params);



	 PageConfig<OrderBaseResult> getCollectionUserPage(HashMap<String, Object> params);
	/**
	 * 查询派单信息
	 * @param queryManLoanCollectionOrder
	 * @return
	 */
	 List<MmanLoanCollectionOrder> findList(MmanLoanCollectionOrder queryManLoanCollectionOrder);


	/**
	 * 保存派单 信息
	 * @param order
	 * 备注：若order.id为空，则新增，否则 更新
	 */
	 void saveMmanLoanCollectionOrder(MmanLoanCollectionOrder order);

	/**
	 * 统计订单数量
	 * @param params
	 * @return
	 */
	 int findAllCount(HashMap<String, Object> params);


	 void updateRecord(MmanLoanCollectionOrder mmanLoanCollectionOrder);

	 MmanLoanCollectionOrder getOrderById(String id);

	/**
	 * 标记订单重要程度
	 * @param params
	 * @return
	 */
	 JsonResult saveTopOrder(Map<String, Object> params);

    /**
     * 根据orderId查询一条记录
     * @param orderId
     * @return
     */
	 MmanLoanCollectionOrder getOrderWithId(String orderId);

	/**
	 * 查询我的订单
	 * @param params
	 * @return
	 */
	 PageConfig<OrderBaseResult> getMyPage(HashMap<String, Object> params);
	/**
	 *ccc
	 *     减免状态更新
	 * @param collectionOrder
	 * @return
	 */
	 int updateJmStatus(MmanLoanCollectionOrder collectionOrder);

	/**
	 * 根据userId获取订单
	 *@param userId
	 */
    MmanLoanCollectionOrder getOrderByUserId(String userId);

	/**
	 * 根据orderId获取baseOrder
	 *@param
	 */
	OrderBaseResult getBaseOrderById(String orderId);
	/**
	 * 获取减免页面的参数
	 *@param
	 */
    HashMap<String,Object> toReductionPage(HashMap<String, Object> params);

    /**
	 * 审核状态更新
	 *
	 *//*

	public void sveUpdateJmStatus(HashMap<String, String> params);*/

	/**
	 * 根据借款id查询订单
	 * @param loanId
	 * @return
	 */
	MmanLoanCollectionOrder getOrderByLoanId(String loanId);

	/**
	 * 根据id查询订单信息
	 * @param id
	 * @return
	 */
	OrderInfo getStopOrderInfoById(String id );


	int deleteOrderInfoAndLoanInfoByloanId(String loanId);

	MmanLoanCollectionOrder getOrderloanId(String loanId);

	void updateOrderInfo(String loanId);

	/**
	 * 新订单派单
	 * @param loanId
	 * @param idNumber
	 * @param type
	 */
	void dispatchOrderNew(String loanId,String idNumber,String type);

	void orderUpgrade(String loanId);

	void updateOverdueDays(MmanLoanCollectionOrder order);

	void dealwithBigOrderUpgrade(String loanId);

    void updateProductName(MmanLoanCollectionOrder order);

	/**
	 * 获取商户信息map
	 * @return
	 */
	Map<String, String> getMerchantMap();
	/**
	 * 获取放款主体map
	 * @return
	 */
	Map<Integer, String> getRepayChannelMap();

    void distributeOrder(MmanLoanCollectionOrder order, String merchant_number);
}
