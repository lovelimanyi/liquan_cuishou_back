package com.info.back.dao;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.SmsLog;

@Repository
public interface ISmsLogDao {
	public int insert(SmsLog smsLog);
}
