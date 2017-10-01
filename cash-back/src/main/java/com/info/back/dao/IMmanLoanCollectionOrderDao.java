package com.info.back.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.web.pojo.OrderBaseResult;
import org.springframework.stereotype.Repository;

import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.pojo.MmanUserLoan;

@Repository
public interface IMmanLoanCollectionOrderDao {
	
	
	public List<MmanLoanCollectionOrder> getOrderList(MmanLoanCollectionOrder mmanLoanCollectionOrder);

	
	
	public List<MmanLoanCollectionOrder> findList(MmanLoanCollectionOrder queryParam);
	
	public void insertCollectionOrder(MmanLoanCollectionOrder queryParam);
	
	
	public void updateCollectionOrder(MmanLoanCollectionOrder queryParam);



	public int getOrderPageCount(HashMap<String, Object> params);



	public MmanLoanCollectionOrder getOrderById(String id);

	/**
	 * 标记订单
	 * @param params
	 * @return
	 */
	public int updateTopOrder(Map<String, Object> params);


	public void updateAuditStatus(HashMap<String, String> params);

	/**
	 * 根据订单id查询一条订单记录
	 * @param orderId
	 * @return
	 */
	public MmanLoanCollectionOrder getOrderWithId(String orderId);
	
	
	/**
	 * 
	 */
	public int UpdateServiceOrder(Map<String, Object> params);
	/**
	 * 
	 * 减免状态更新
	 * @param collectionOrder
	 * @return
	 */
	public int updateJmStatus(MmanLoanCollectionOrder collectionOrder);

	/**
	 *  审核更新状态
	 * @param params
	 */
	public void sveUpdateJmStatus(HashMap<String, String> params);


	/**
	 * 查询催收成功订单
	 */

	public  List<MmanLoanCollectionOrder> getSelectList();


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

	public String getLatestLoanByUserPhoneAndLoanEndTime(HashMap<String,Object> params);

	MmanLoanCollectionOrder getOrderByLoanId(String loanId);

}
