package com.info.back.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.back.result.JsonResult;
import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.pojo.OrderBaseResult;
import com.info.web.util.PageConfig;

public interface IMmanLoanCollectionOrderService {
	
	/**
	 * 根据条件查询符合的订单
	 * @param mmanLoanCollectionOrder
	 * @return
	 */
	public List<MmanLoanCollectionOrder> getOrderList(MmanLoanCollectionOrder mmanLoanCollectionOrder);
	
	
	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
	public PageConfig<MmanLoanCollectionOrder> findPage(HashMap<String, Object> params);
	
	
	public PageConfig<OrderBaseResult> getPage(HashMap<String, Object> params);



	public PageConfig<OrderBaseResult> getCollectionUserPage(HashMap<String, Object> params);
	/**
	 * 查询派单信息
	 * @param queryManLoanCollectionOrder
	 * @return
	 */
	public List<MmanLoanCollectionOrder> findList(MmanLoanCollectionOrder queryManLoanCollectionOrder);
	
	
	/**
	 * 保存派单 信息
	 * @param order
	 * 备注：若order.id为空，则新增，否则 更新
	 */
	public void saveMmanLoanCollectionOrder(MmanLoanCollectionOrder order);

	/**
	 * 统计订单数量
	 * @param params
	 * @return
	 */
	public int findAllCount(HashMap<String, Object> params);


	public void updateRecord(MmanLoanCollectionOrder mmanLoanCollectionOrder);

	public MmanLoanCollectionOrder getOrderById(String id);
	
	/**
	 * 标记订单重要程度
	 * @param params
	 * @return
	 */
	public JsonResult saveTopOrder(Map<String, Object> params);
	
    /**
     * 根据orderId查询一条记录
     * @param orderId
     * @return
     */
	public MmanLoanCollectionOrder getOrderWithId(String orderId);

	/**
	 * 查询我的订单
	 * @param params
	 * @return
	 */
	public PageConfig<OrderBaseResult> getMyPage(HashMap<String, Object> params);
	/**
	 *ccc
	 *     减免状态更新
	 * @param loan
	 * @return
	 */
	public int updateJmStatus(MmanLoanCollectionOrder collectionOrder);

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
}
