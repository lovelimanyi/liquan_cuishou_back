package com.info.back.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IPaginationDao;
import com.info.back.dao.ISmsDao;
import com.info.constant.Constant;
import com.info.web.pojo.InfoSms;
import com.info.web.pojo.TemplateSms;
import com.info.web.util.PageConfig;



@Service
public class SmsService implements ISmsService {
	private static Logger logger = LoggerFactory.getLogger(SmsService.class);
	
	@Autowired
	private IPaginationDao<InfoSms> paginationDao;
	
	@Autowired
	private ISmsDao smsDao;
	
	@Override
	public PageConfig<InfoSms> findPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "Sms");
		return paginationDao.findPage("findAll", "findAllCount", params,null);
	}

}
