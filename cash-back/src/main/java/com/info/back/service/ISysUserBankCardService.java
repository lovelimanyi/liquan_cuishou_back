package com.info.back.service;

import com.info.web.pojo.SysUserBankCard;

public interface ISysUserBankCardService {
	
	
	/**
	 * 添加银行卡信息
	 * @param sysUserBankCard
	 * @return
	 */
	public int saveNotNull(SysUserBankCard sysUserBankCard);
	/**
	 * 根据用户ID查询银行卡信息
	 * @param userId
	 * @return
	 */
	public SysUserBankCard findUserId(String userId);

}
