package com.info.back.service;

import java.util.HashMap;

import com.info.web.pojo.SmsLog;
import com.info.web.util.PageConfig;

public interface ISmsLogService {
	/**
	 * 批量更新
	 * 
	 * @param params
	 * @return
	 */
	public int insert(SmsLog smsLog);

	/**
	 * 分页查询
	 * 
	 * @param params
	 * @return
	 */
	PageConfig<SmsLog> findPage(HashMap<String, Object> params);
}
