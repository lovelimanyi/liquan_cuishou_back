package com.info.back.dao;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.SysAlertMsg;

@Repository
public interface ISysAlertMsgDao {
	
	public void insert(SysAlertMsg sysAlertMsg);

	public int updateTogAlertMsg(String alertId);

}
