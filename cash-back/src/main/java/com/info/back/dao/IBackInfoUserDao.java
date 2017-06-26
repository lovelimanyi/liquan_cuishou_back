package com.info.back.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.User;

@Repository
public interface IBackInfoUserDao {
	public int updateUser(User zbUser);

	/**
	 * 根据参数获得用户列表
	 * 
	 * @param params
	 *            参数 userAccount 用户名(模糊查询)<br>
	 *            userTelephone 手机号码(模糊匹配)<br>
	 *            id 主键<br>
	 *            userAccountEq(全匹配)<br>
	 *            userTelephoneEq(座机全匹配)<br>
	 *            beginDate注册时间开始<br>
	 *            endDate注册时间开始<br>
	 *            userEmail邮箱，全匹配<br>
	 *            userMobileEq手机号，全匹配<br>
	 * @return
	 */
	public List<User> findByParams(HashMap<String, Object> params);

	public User findById(Integer id);

	/**
	 * 根据参数获得用户列表
	 * 
	 * @param params
	 *            参数 userAccount 用户名(模糊查询)<br>
	 *            userTelephone 手机号码(模糊匹配)<br>
	 *            id 主键<br>
	 *            userAccountEq(全匹配)<br>
	 *            userTelephoneEq(手机号全匹配)<br>
	 *            beginDate注册时间开始<br>
	 *            endDate注册时间开始<br>
	 *            userEmail邮箱，全匹配<br>
	 * @return
	 */
	public List<Integer> findId(HashMap<String, Object> params);
}
