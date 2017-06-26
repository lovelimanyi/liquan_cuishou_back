package com.info.back.service;

import java.util.HashMap;

import com.info.web.pojo.InfoSms;
import com.info.web.pojo.TemplateSms;
import com.info.web.util.PageConfig;

public interface ISmsService {
	/**
	 * 查询短信模板列表
	 * @param params
	 * @return
	 */
	PageConfig<InfoSms> findPage(HashMap<String, Object> params); 

}
