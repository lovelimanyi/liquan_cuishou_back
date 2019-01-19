package com.info.web.synchronization.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

public interface IDataDao {
	/**
	 * 获取逾期
	 * @param map
	 * @return
	 */
	public HashMap<String,Object> getAssetRepayment(HashMap<String,String> map);
	/**
	 * 获取还款详情
	 * @param map
	 * @return
	 */
	public List<HashMap<String,Object>> getAssetRepaymentDetail(HashMap<String,String> map);
	/**
	 * 获取手机通讯录
	 * @param map
	 * @return
	 */
	public List<HashMap<String,Object>> getUserContacts(HashMap<String,String> map);
	/**
	 * 获取银行卡
	 * @param map
	 * @return
	 */
	public HashMap<String,Object> getUserCardInfo(HashMap<String,String> map);
	/**
	 * 获取用户信息
	 * @param map
	 * @return
	 */
	public HashMap<String,Object> getUserInfo(HashMap<String,String> map);
	/**
	 * 获取借款详情
	 * @param map
	 * @return
	 */
	public HashMap<String,Object> getAssetBorrowOrder(HashMap<String,String> map);
	/**
	 * 获取即将逾期订单
	 * @param map
	 * @return
	 */
	List<HashMap<String, Object>> getEstimateOrder(HashMap<String,Object> map);

	HashMap<String,Object> getDianXiaoOrder(HashMap<String, String> map);

	String getMerchantNumberByLoanId(String loanId);

	HashMap<String,String> getBorrowOrderOnBorrowing2(HashMap<String, String> map);
}
