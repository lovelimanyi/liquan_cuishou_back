package com.info.back.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IBackIndexDao;
import com.info.constant.Constant;
import com.info.web.pojo.IndexPage;
import com.info.web.util.JSONUtil;
import com.info.web.util.JedisDataClient;

@Service
public class BackIndexService implements IBackIndexService {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private IBackIndexDao backIndexDao;

	@Override
	public void update(IndexPage zbIndex) {
		backIndexDao.update(zbIndex);
		updateCache();
	}

	@Override
	public IndexPage searchIndex() {
		return backIndexDao.searchIndex();
	}

	/**
	 * 刷新首页缓存
	 */
	public void updateCache() {
		IndexPage index = searchIndex();// 查询数据库
		try {
			JedisDataClient.set(Constant.CACHE_INDEX_KEY, JSONUtil
					.beanToJson(index));// 查询结果放入缓存
		} catch (Exception e) {
			logger.error("initIndex-JedisDataClient.set-"
					+ Constant.CACHE_INDEX_KEY + "-exception", e);
		}
	}
}
