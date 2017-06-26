package com.info.back.dao;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.SysUserBankCard;

@Repository
public interface ISysUserBankCardDao {
	
	public int saveNotNull(SysUserBankCard sysUserBankCard);

	public SysUserBankCard findUserId(String userId);

}
