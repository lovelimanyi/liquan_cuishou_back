package com.info.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.ISysUserBankCardDao;
import com.info.web.pojo.SysUserBankCard;

@Service
public class SysUserBankCardService implements ISysUserBankCardService{
	
	@Autowired
	private ISysUserBankCardDao sysUserBankCardDao;

	@Override
	public int saveNotNull(SysUserBankCard sysUserBankCard) {
		return sysUserBankCardDao.saveNotNull(sysUserBankCard);
	}

	@Override
	public SysUserBankCard findUserId(String userId) {
		return sysUserBankCardDao.findUserId(userId);
	}

}
