package com.info.back.service;

public interface ITongBuService {
	
	/**
	 * 
	 * @param id 还款id
	 * @param cz  预期 部分还款 全部还款
	 */
	public void tongbu(String id,String cz);

}
