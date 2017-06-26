package com.info.back.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.BackModule;
import com.info.web.pojo.BackTree;

/**
 * 
 * 类描述：菜单dao层 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午01:53:41 <br>
 * 
 * @version
 * 
 */
@Repository
public interface IBackModuleDao {

	/**
	 * administrator查询所有状态为显示的菜单
	 * 
	 * @param map
	 *            参数名 ：parentId，含义：父节点ID <br>
	 * @return 角色list
	 */
	public List<BackModule> findAdminAll(HashMap<String, Object> params);

	/**
	 * 根据用户ID查询菜单总数
	 * 
	 * @param map
	 *            参数名 ：userId，含义：用户id <br>
	 *            参数名 ：parentId，含义：父节点ID <br>
	 * @return 总数
	 */
	public Integer findUserCount(HashMap<String, Object> params);

	/**
	 * 根据用户ID查询菜单
	 * 
	 * @param map
	 *            参数名 ：userId，含义：用户id <br>
	 *            参数名 ：parentId，含义：父节点ID <br>
	 * @return 角色list
	 */
	public List<BackModule> findUserAll(HashMap<String, Object> params);

	/**
	 * administrator查询所有状态为显示的菜单
	 * 
	 * @param map
	 * @return 角色list
	 */
	public List<BackTree> findAdminTree(HashMap<String, Object> params);

	/**
	 * 根据用户ID查询菜单
	 * 
	 * @param map
	 *            参数名 ：userId，含义：用户id <br>
	 * @return 角色list
	 */
	public List<BackTree> findUserTree(HashMap<String, Object> params);

	/**
	 * 根据主键获得菜单
	 * 
	 * @param id
	 * @return
	 */
	public BackModule findById(Integer id);

	/**
	 * 根据主键删除菜单
	 * 
	 * @param backModule
	 */
	public void deleteById(Integer id);

	/**
	 * 根据主键更新菜单
	 * 
	 * @param backModule
	 */
	public void updateById(BackModule backModule);

	/**
	 * 插入菜单
	 * 
	 * @param backModule
	 */
	public void insert(BackModule backModule);

	/**
	 * 根据用户ID和url查询
	 * 
	 * @param params
	 *            参数名：id，用户主键ID<br>
	 *            参数名：moduleUrl,权限url
	 */
	public int findModuleByUrl(HashMap<String, Object> params);
}
