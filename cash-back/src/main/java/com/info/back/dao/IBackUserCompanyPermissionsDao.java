package com.info.back.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.BackUserCompanyPermissions;


/**
 * 
 * 类描述：用户查询公司信息dao层 <br>
 * 创建人：xieyaling<br>
 * 创建时间：2017-2-16 下午15:18:57 <br>
 * 
 * @version
 * 
 */
@Repository
public interface IBackUserCompanyPermissionsDao {
	/**
	 * 根据条件查询用户角色
	 * 
	 * @param map
	 * @return 角色list
	 */
	public List<BackUserCompanyPermissions> findAll(HashMap<String, Object> params);

	/**
	 * 根据角色ID删除
	 * 
	 * @param roleId
	 */
	public void deleteBycompanyId(Integer roleId);

	/**
	 * 根据用户删除所有关联的角色
	 * 
	 * @param roleId
	 */
	public void deleteUserCompanyByUserId(Integer id);

	/**
	 * 插入用户关联的角色
	 * 
	 * @param params
	 */
	public void inserUserCompanyList(HashMap<String, Object> params);

	/**
	 * 插入用户关联的角色
	 * 
	 * @param params
	 */
	public void inserCompanyPermission(BackUserCompanyPermissions backUserRole);

	public List<BackUserCompanyPermissions> findCompanyPermissions(Integer userId);
	
	public List<BackUserCompanyPermissions> findSelfCompanyPermissions(Integer userId);
	
}
