package com.info.back.service;

import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.BackModule;
import com.info.web.pojo.BackRole;
import com.info.web.pojo.BackTree;
import com.info.web.util.PageConfig;

/**
 * 
 * 类描述：角色dao层 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午01:53:41 <br>
 * 
 * @version
 * 
 */
public interface IBackRoleService {

	/**
	 * 获得超级管理员的所有子角色
	 * 
	 * @param params
	 *            参数名 ：userId，含义：用户id,等于administratorID时则返回全部角色 <br>
	 * @return
	 */
	public List<BackTree> findRoleTree(HashMap<String, Object> params);

	/**
	 * 分页查询某个角色下的所有子角色
	 * 
	 * @param params
	 * @return
	 */
	PageConfig<BackRole> findPage(HashMap<String, Object> params);

	/**
	 * 根据主键获得对象
	 * 
	 * @param id
	 * @return
	 */
	public BackRole findById(Integer id);

	/**
	 * 根据主键删除对象<br>
	 * 删除角色关联的用户和菜单
	 * 
	 * @param backModule
	 */
	public void deleteById(Integer id);

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
	public void insert(BackRole backRole, Integer userId);

	/**
	 * 根据角色ID查询该角色及该角色的子角色（向下无线递归）所关联的用户
	 * 
	 * @param id
	 * @return
	 */
	List<BackTree> showUserListByRoleId(Integer id);

	/**
	 * 根据角色ID得到角色权限树
	 * 
	 * @param params
	 *            角色ID
	 * @return
	 */
	public List<BackTree> getTreeByRoleId(Integer roleId);

	/**
	 * 先删除现有权限，再插入新权限
	 * 
	 * @param params
	 */
	void addRoleModule(HashMap<String, Object> params);
}
