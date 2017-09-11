package com.info.back.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.web.pojo.OrderBaseResult;
import com.info.web.pojo.OrderInfo;
import org.springframework.stereotype.Repository;

import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.pojo.MmanUserLoan;

@Repository
public interface IMmanLoanCollectionOrderDao {
	
	
	List<MmanLoanCollectionOrder> getOrderList(MmanLoanCollectionOrder mmanLoanCollectionOrder);

	
	
	List<MmanLoanCollectionOrder> findList(MmanLoanCollectionOrder queryParam);
	
	void insertCollectionOrder(MmanLoanCollectionOrder queryParam);
	
	
	void updateCollectionOrder(MmanLoanCollectionOrder queryParam);



	int getOrderPageCount(HashMap<String, Object> params);



	MmanLoanCollectionOrder getOrderById(String id);

	/**
	 * 标记订单
	 * @param params
	 * @return
	 */
	int updateTopOrder(Map<String, Object> params);


	void updateAuditStatus(HashMap<String, String> params);

	/**
	 * 根据订单id查询一条订单记录
	 * @param orderId
	 * @return
	 */
	MmanLoanCollectionOrder getOrderWithId(String orderId);
	
	
	/**
	 * 
	 */
	int UpdateServiceOrder(Map<String, Object> params);
	/**
	 * 
	 * 减免状态更新
	 * @param collectionOrder
	 * @return
	 */
	int updateJmStatus(MmanLoanCollectionOrder collectionOrder);

	/**
	 *  审核更新状态
	 * @param params
	 */
	void sveUpdateJmStatus(HashMap<String, String> params);


	/**
	 * 查询催收成功订单
	 */

	List<MmanLoanCollectionOrder> getSelectList();


	void updateReductionMoney( HashMap<String, Object> order);
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

	String getLatestLoanByUserPhoneAndLoanEndTime(HashMap<String,Object> params);

	MmanLoanCollectionOrder getOrderByLoanId(String loanId);

	OrderInfo getStopOrderInfoById(String id);

	int deleteOrderInfoAndLoanInfoByloanId(String orderId);

}
