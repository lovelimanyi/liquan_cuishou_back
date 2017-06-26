package com.info.back.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.BackRole;
import com.info.web.pojo.BackTree;

/**
 * 
 * 类描述：角色dao层 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午01:53:41 <br>
 * 
 * @version
 * 
 */
@Repository
public interface IBackRoleDao {

	/**
	 * 获得超级管理员的所有子角色
	 * 
	 * @param params
	 * @return
	 */
	public List<BackTree> findAdminRoleTree(HashMap<String, Object> params);

	/**
	 * 获得某个用户的角色
	 * 
	 * @param params
	 * @return
	 */
	public List<BackTree> findUserRoleTree(HashMap<String, Object> params);

	/**
	 * 根据主键获得对象
	 * 
	 * @param id
	 * @return
	 */
	public BackRole findById(Integer id);

	/**
	 * 根据主键删除对象
	 * 
	 * @param backModule
	 */
	public int deleteById(Integer id);

	/**
	 * 根据主键更新对象
	 * 
	 * @param backModule
	 */
	public void updateById(BackRole backRole);

	/**
	 * 插入对象
	 * 
	 * @param backModule
	 */
	public void insert(BackRole backRole);

	/**
	 * 根据角色ID查询该角色及该角色的子角色（向下无线递归）
	 * 
	 * @param id
	 * @return
	 */
	public List<Integer> showChildRoleListByRoleId(Integer id);

	/**
	 * 根据角色列表查询出用户
	 * 
	 * @param params
	 *            参数：roleChildIds，含义：角色ID
	 * @return
	 */
	public List<BackTree> showUserListByRoleId(HashMap<String, Object> params);

	/**
	 * 根据角色ID得到角色权限树
	 * 
	 * @param params
	 *            角色ID
	 * @return
	 */
	public List<BackTree> getTreeByRoleId(Integer roleId);
}
