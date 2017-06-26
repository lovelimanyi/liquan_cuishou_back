package com.info.back.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IPaginationDao;
import com.info.back.dao.ISmsUserDao;
import com.info.constant.Constant;
import com.info.web.pojo.SmsUser;
import com.info.web.util.PageConfig;

@Service
public class SmsUserService implements ISmsUserService {
	@Autowired
	private ISmsUserDao smsUserDao;
	@Autowired
	private IPaginationDao paginationDao;

	@Override
	public PageConfig<SmsUser> findPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "SmsUser");
		return paginationDao.getMyPage("findAll", "findAllCount", params, null);
	}

	@Override
	public PageConfig<SmsUser> findPage2(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "SmsUser");
		return paginationDao.findPage("findList", "findCount", params, null);
	}

	@Override
	public int batchInsert(HashMap<String, Object> params) {
		return smsUserDao.batchInsert(params);
	}

	@Override
	public List<Integer> findIds(HashMap<String, Object> params) {
		return smsUserDao.findIds(params);
	}

	@Override
	public List<String> findPhones(HashMap<String, Object> params) {
		return smsUserDao.findPhones(params);
	}

	@Override
	public int findAllCount(HashMap<String, Object> params) {
		return smsUserDao.findAllCount(params);
	}

	@Override
	public String findStringPhones(HashMap<String, Object> params) {
		return smsUserDao.findStringPhones(params);
	}

	@Override
	public List<SmsUser> findPartList(HashMap<String, Object> params) {
		return smsUserDao.findPartList(params);
	}

	@Override
	public String findPhonesForSend(HashMap<String, Object> params) {
		return smsUserDao.findPhonesForSend(params);
	}

	@Override
	public void insert(SmsUser msg) {
		this.smsUserDao.insert(msg);
	}

	@Override
	public int getSendMsgCount(String orderId) {
		return smsUserDao.getSendMsgCount(orderId);
	}
}
