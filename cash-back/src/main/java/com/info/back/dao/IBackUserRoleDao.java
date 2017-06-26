package com.info.back.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.BackUserRole;

/**
 * 
 * 类描述：用户角色dao层 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午01:53:41 <br>
 * 
 * @version
 * 
 */
@Repository
public interface IBackUserRoleDao {
	/**
	 * 根据条件查询用户角色
	 * 
	 * @param map
	 * @return 角色list
	 */
	public List<BackUserRole> findAll(HashMap<String, Object> params);

	/**
	 * 根据角色ID删除
	 * 
	 * @param roleId
	 */
	public void deleteByRoleId(Integer roleId);

	/**
	 * 根据用户删除所有关联的角色
	 * 
	 * @param roleId
	 */
	public void deleteUserRoleByUserId(Integer id);

	/**
	 * 插入用户关联的角色
	 * 
	 * @param params
	 */
	public void inserUserRoleList(HashMap<String, Object> params);

	/**
	 * 插入用户关联的角色
	 * 
	 * @param params
	 */
	public void inserUserRole(BackUserRole backUserRole);
}
