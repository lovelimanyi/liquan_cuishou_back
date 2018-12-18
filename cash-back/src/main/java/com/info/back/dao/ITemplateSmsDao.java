package com.info.back.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.TemplateSms;

@Repository
public interface ITemplateSmsDao {
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
	int update(TemplateSms templateSms);
	/**
	 * 添加短信模板
	 * @param templateSms
	 * @return
	 */
	int insert(TemplateSms templateSms);
	/**
	 * 删除短信模板
	 * @param id
	 * @return
	 */
	int delete(String id);
	
	/**
	 * 查询所有  cjxjx渠道来源  的短信模板
	 * @return
	 */
	List<TemplateSms> getMsgs();
	/**
	 * 查询所有  有米管家渠道来源  的短信模板
	 * @return
	 */
	List<TemplateSms> getYoumiAllMsg();
	/**
	 * 查询所有  渠道来源  的短信模板
	 * @return
	 */
	List<TemplateSms> getAllMsg();
	
	List<TemplateSms> getType(HashMap<String, Object> params);

}
