package com.info.back.service;

import java.util.HashMap;
import java.util.List;

import com.info.back.result.JsonResult;
import com.info.web.pojo.TemplateSms;
import com.info.web.util.PageConfig;

public interface ITemplateSmsService {
	/**
	 * 查询短信模板列表
	 * @param params
	 * @return
	 */
	PageConfig<TemplateSms> findPage(HashMap<String, Object> params); 
	/**
	 * 根据id查询短信模板
	 * @param id
	 * @return
	 */
	TemplateSms getTemplateSmsById(String id);
	/**
	 * 更新短信模板
	 * @param templateSms
	 * @return
	 */
	JsonResult updateTemplateSms(TemplateSms templateSms);
	/**
	 * 保存新短信模板
	 * @param templateSms
	 * @return
	 */
	JsonResult insert(TemplateSms templateSms);
	/**
	 * 根据id删除短信模板
	 * @param id
	 * @return
	 */
	JsonResult deleteTemplateSms(String id);
    /**
     * 類型查詢
     */
    public List<TemplateSms> getType(HashMap<String, Object> params);

    List<TemplateSms> getAllMsgByChannelFrom(String msgChannelForm);
}
