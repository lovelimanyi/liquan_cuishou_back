package com.info.back.service;

import java.util.HashMap;

import com.info.back.result.JsonResult;
import com.info.web.pojo.Collection;
import com.info.web.util.PageConfig;

public interface ICollectionService {
	/**
	 * 查询催收员记录
	 * @param params
	 * @return
	 */
	public PageConfig<Collection> findPage(HashMap<String, Object> params);
	/**
	 * 根据id查询用户信息
	 * @param params
	 * @return
	 */
	public Collection findOneCollection(Integer id);
	/**
	 * 修改催收员
	 * @param collection
	 */
	public JsonResult updateById(Collection collection);
	/**
	 * 添加催收员
	 * @param collection
	 */
	public JsonResult insert(Collection collection);
	/**
	 * 删除催收员
	 * @param id
	 * @return
	 */
	public JsonResult deleteCollection(Integer id);
	/**
	 * 删除标记为删除的订单
	 */
	public void deleteTagDelete();

}
