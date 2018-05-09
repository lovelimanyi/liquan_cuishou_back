package com.info.back.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.web.pojo.BackUser;
import com.info.web.pojo.BackUserCompanyPermissions;
import com.info.web.pojo.BackUserRole;
import com.info.web.pojo.MmanLoanCollectionCompany;
import com.info.web.pojo.MmanLoanCollectionPerson;
import com.info.web.util.PageConfig;

/**
 * 
 * 类描述：用户dao层 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午01:53:41 <br>
 * 
 * @version
 * 
 */
public interface IBackUserService {
	/**
	 * 根据条件查询用户
	 * 
	 * @param map
	 *            参数名 ：userAccount，含义：用户名 <br>
	 *            参数名 ：status 含义：状态
	 * @return 角色list
	 */
	public List<BackUser> findAll(HashMap<String, Object> params);

	/**
	 * 根据条件查询用户<br>
	 * 只返回第一个用户对象<br>
	 * 
	 * @param map
	 *            参数名 ：userAccount，含义：用户名 <br>
	 *            参数名 ：status 含义：状态 参数名：id 含义：用户主键
	 * @return 用户对象
	 */
	public BackUser findOneUser(HashMap<String, Object> params);

	/**
	 * 根据主键删除对象
	 * 
	 * @param id
	 */
	public void deleteById(Integer id);

	/**
	 * 插入用户对象
	 * 
	 * @param backUser
	 */
	public void insert(BackUser backUser);

	/**
	 * 更新用户对象
	 * 
	 * @param backUser
	 */
	public void updateById(BackUser backUser);

	/**
	 * 分页查询
	 * 
	 * @param params
	 * @return
	 */
	PageConfig<BackUser> findPage(HashMap<String, Object> params);

	/**
	 * 用户授权
	 * 
	 * @param params
	 */
	void addUserRole(HashMap<String, Object> params);

	/**
	 * 更新密码
	 * 
	 * @param backUser
	 */
	public void updatePwdById(BackUser backUser);
	
	
	/**
	 * 根据用户Id查询用户角色Id
	 * @param userId
	 * @return
	 */
	public List<BackUserRole> findUserRoleByUserId(String userId);
	
	/**
	 * 根据用户Id查询用户角色Id
	 * @param userId
	 * @return roleId 
	 * 备注：多个角色Id，用逗号隔开 
	 */
	public String findUserRoleIdByUserId(String userId);
	/**
	 * 查询用户对应公司数据权限
	 * @param params
	 * @return
	 */
	public List<BackUserCompanyPermissions> findCompanyPermissions(Integer userId);
	
	/**
	 * 根据id查询对应用户的手机号个数
	 * @param 
	 * @return
	 */
	public Integer getUserPhoneCount(HashMap<String, Object> params);
	/**
	 * 查询用户列表
	 * @param params
	 * @return
	 */
	public List<BackUser> findList(BackUser backUser);
	
	/**
	 * 查询用户列表
	 */
	public List<BackUser> findUserByDispatch(BackUser backUser);
	
	
	public BackUser getBackUserByUuid(String uuid);
	
	/**
	 * 查询用户对应公司数据权限
	 * @param params
	 * @return
	 */
	public List<BackUserCompanyPermissions> findSelfCompanyPermissions(Integer userId);
	
	/**
	 * 禁用催收员
	 */
	public void disableCollections();
	
	/**
	 * 启用催收员
	 */
	public void enableCollections();
	
	

	List<MmanLoanCollectionPerson>  findUnCompleteCollectionOrderByCurrentUnCompleteCountListByMap(Map<String,String> param);


	BackUser getUserByAccount(String userAccount);
}
