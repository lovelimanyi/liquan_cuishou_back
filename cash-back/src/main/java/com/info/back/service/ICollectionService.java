package com.info.back.service;

import java.util.HashMap;

import com.info.back.result.JsonResult;
import com.info.web.pojo.Collection;
import com.info.web.util.PageConfig;

import javax.servlet.http.HttpServletRequest;

public interface ICollectionService {
	/**
	 * 查询催收员记录
	 * @param params
	 * @return
	 */
	PageConfig<Collection> findPage(HashMap<String, Object> params);
	/**
	 * 根据id查询用户信息
	 * @param params
	 * @return
	 */
	Collection findOneCollection(Integer id);
	/**
	 * 修改催收员
	 * @param collection
	 */
	JsonResult updateById(Collection collection);
	/**
	 * 添加催收员
	 * @param collection
	 */
	JsonResult insert(Collection collection);
	/**
	 * 删除催收员
	 * @param id
	 * @return
	 */
	JsonResult deleteCollection(Integer id);
	/**
	 * 删除标记为删除的订单
	 */
	void deleteTagDelete();

	/**
	 * 根据催收员姓名获取催收员
	 * @param username
	 * @return
	 */
	Collection getCollectionByUserName(String username);

	/**
	 * 根据催收员账号获取催收员信息
	 * @param userAccount
	 * @return
	 */
	Collection getCollectionByUserAccount(String userAccount);

	boolean verifyCodeAccess(HttpServletRequest request);

}
