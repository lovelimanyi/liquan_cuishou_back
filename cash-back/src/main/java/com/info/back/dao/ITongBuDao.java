package com.info.back.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


@Repository
public interface ITongBuDao {
	
	/**
	 * 通过借款ID拿到借款详情
	 * @param id
	 * @return
	 */
	public Map<Object,Object> getassetborroworder(java.lang.Long id);
	
	/**
	 * 通过用户ID拿到联系人列表 
	 * @param id
	 * @return
	 */
	public List<Map<Object,Object>> getUserContacts(java.lang.Long id);
	
	
	/**
	 *  银行卡信息
	 * @param id
	 * @return
	 */
	public Map<Object,Object> getuserCardInfo(java.lang.Long id);
	
	/**
	 * 还款详情
	 * @param id
	 * @return
	 */
	public List<Map<Object,Object>> getAssetRepaymentDetail(java.lang.Long id);
	
	/**
	 * 还款信息 
	 * @param id
	 * @return
	 */
	public Map<Object,Object> getAssetRepayment(String id);
	
	
	/**
	 * 用户信息信息
	 * @param id
	 * @return
	 */
	public Map<Object,Object> getUserInfo(java.lang.Long id);
	
	

}
