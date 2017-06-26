package com.info.back.service;

import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.BackModule;
import com.info.web.pojo.BackTree;
import com.info.web.util.PageConfig;

/**
 * 
 * 类描述：菜单dao层 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午01:53:41 <br>
 * 
 * @version
 * 
 */
public interface IBackModuleService {
	/**
	 * 根据条件查询菜单
	 * 
	 * @param map
	 *            参数名 ：userId，含义：用户id,等于administratorID时则返回全部可见菜单 <br>
	 *            参数名 ：parentId，含义：父节点ID <br>
	 * @return 菜单list
	 */
	public List<BackModule> findAllModule(HashMap<String, Object> params);

	/**
	 * 根据条件查询菜单
	 * 
	 * @param map
	 *            参数名 ：userId，含义：用户id，等于administratorID时则返回全部可见菜单 <br>
	 * @return 菜单list 树形结构
	 */
	List<BackTree> findModuleTree(HashMap<String, Object> params);

	/**
	 * 
	 * @param params
	 * @return
	 */
	PageConfig<BackModule> findPage(HashMap<String, Object> params);

	/**
	 * 根据主键获得菜单
	 * 
	 * @param id
	 * @return
	 */
	BackModule findById(Integer id);

	/**
	 * 根据主键更新对象
	 * 
	 * @param backModule
	 */
	void updateById(BackModule backModule);

	/**
	 * 插入对象
	 * 
	 * @param backModule
	 */
	void insert(BackModule backModule);

	/**
	 * 根据主键删除对象
	 * 
	 * @param id
	 */
	void deleteById(Integer id);

	/**
	 * 根据用户ID和url查询
	 * 
	 * @param params
	 *            参数名：id，用户主键ID<br>
	 *            参数名：moduleUrl,权限url
	 */
	public int findModuleByUrl(HashMap<String, Object> params);
}
