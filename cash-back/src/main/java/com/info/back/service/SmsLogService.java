package com.info.back.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IPaginationDao;
import com.info.back.dao.ISmsLogDao;
import com.info.constant.Constant;
import com.info.web.pojo.SmsLog;
import com.info.web.util.PageConfig;

@Service
public class SmsLogService implements ISmsLogService {
	@Autowired
	private IPaginationDao paginationDao;
	@Autowired
	private ISmsLogDao smsLogDao;

	@Override
	public PageConfig<SmsLog> findPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "SmsLog");
		return paginationDao.getMyPage("findAll", "findAllCount", params, null);
	}

	@Override
	public int insert(SmsLog smsLog) {
		return smsLogDao.insert(smsLog);
	}

}
