package com.info.back.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IBackConfigParamsDao;
import com.info.constant.RedisContants;
import com.info.web.pojo.BackConfigParams;
import com.info.web.util.JedisDataClient;

@Service
public class BackConfigParamsService implements IBackConfigParamsService {
	Logger logger = Logger.getLogger(getClass());
	@Autowired
	IBackConfigParamsDao backConfigParamsDao;

	@Override
	public List<BackConfigParams> findParams(HashMap<String, Object> params) {

		return backConfigParamsDao.findParams(params);
	}

	@Override
	public int updateValue(List<BackConfigParams> list, String type) {
		int result = backConfigParamsDao.updateValue(list);
		if (result > 0) {
		}
		return result;
	}
}
