package com.info.back.service;

import java.util.HashMap;

import com.info.web.pojo.User;
import com.info.web.util.PageConfig;

public interface IBackInfoUserService {
	public int updateUser(User zbUser);

	/**
	 * 
	 * @param params
	 *            参数 userAccount 用户名(模糊查询)<br>
	 *            userMobile 手机号码<br>
	 *            id 主键<br>
	 *            userAccountEq(全匹配)<br>
	 *            userEmail邮箱，全匹配<br>
	 * @return
	 */
	public PageConfig<User> findPage(HashMap<String, Object> params);

	/**
	 * 根据主键查询对象
	 * 
	 * @param id
	 * @return
	 */
	public User findById(Integer id);

	/**
	 * 根据用户名获得信息
	 * 
	 * @param userAccount
	 * @return
	 */
	User findByUserAccount(String userAccount);

	/**
	 * 根据电话获得信息
	 * 
	 * @param userAccount
	 * @return
	 */
	User findByUserTelePhone(String userTelePhone);

	/**
	 * 根据邮箱获得信息
	 * 
	 * @param userAccount
	 * @return
	 */
	User findByUserEmail(String userEmail);

	/**
	 * 根据手机号码获得信息
	 * 
	 * @param userMobileEq
	 * @return
	 */
	User findByUserMobile(String userMobileEq);
}
