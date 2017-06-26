package com.info.back.service;

import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.BackRoleModule;

/**
 * 
 * 类描述：角色菜单dao层 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午01:53:41 <br>
 * 
 * @version
 * 
 */
public interface IBackRoleModuleService {
	/**
	 * 根据条件查询角色菜单对象
	 * 
	 * @param map
	 * @return 角色list
	 */
	public List<BackRoleModule> findAll(HashMap<String, Object> params);

	/**
	 * 插入角色菜单关联信息
	 * 
	 * @param params
	 *            rightIds要插入的菜单id集合<br>
	 *            id 要出的角色ID
	 */
	public void insertModuleRole(HashMap<String, Object> params);
}
