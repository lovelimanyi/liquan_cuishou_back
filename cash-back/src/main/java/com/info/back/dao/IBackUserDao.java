package com.info.back.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.BackUser;
import com.info.web.pojo.BackUserRole;
import com.info.web.pojo.MmanLoanCollectionCompany;
import com.info.web.pojo.MmanLoanCollectionPerson;

/**
 * 
 * 类描述：用户dao层 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午01:53:41 <br>
 * 
 * @version
 * 
 */
@Repository
public interface IBackUserDao {
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
	 * 插入用户对象
	 * 
	 * @param backUser
	 */
	public void insert(BackUser backUser);

	/**
	 * 根据主键删除对象
	 * 
	 * @param id
	 */
	public void deleteById(Integer id);

	/**
	 * 更新用户对象
	 * 
	 * @param backUser
	 */
	public void updateById(BackUser backUser);

	/**
	 * 更新密码
	 * 
	 * @param backUser
	 */
	public void updatePwdById(BackUser backUser);
	
	/**
	 * 查询BackUser等综合信息
	 */
	List<MmanLoanCollectionPerson>  findUnCompleteCollectionOrderByCurrentUnCompleteCountListByMap(Map<String,String> param);
	
	/**
	 * 查询BackUser等综合信息
	 */
	List<MmanLoanCollectionPerson>  findbackUserByLoanUserPhone(Map<String,String> param);
	
	
	 /**
	  * 查询当前催收员今日派到手里的订单数(包括已完成的)
	  * @param mmanLoanCollectionPerson 催收员
	  */
	 Integer findTodayAssignedCount(MmanLoanCollectionPerson mmanLoanCollectionPerson);
	/**
	 * 查询公司列表
	 * @return
	 */
	public List<MmanLoanCollectionCompany> selectCompanyList();
	
	
	/**
	 * 根据用户Id查询用户角色Id
	 * @param userId
	 * @return
	 */
	public List<BackUserRole> findUserRoleByUserId(String userId);
	/**
	 * 修改用户roleID
	 * @param roleMap
	 */
	public void updateRoleId(HashMap<String, Object> roleMap);
	
	/**
	 * 根据id查询对应用户的手机号个数
	 * @param 
	 * @return
	 */
	public Integer getUserPhoneCount(Map<String, Object> params);
	
	/**
	 * 查询用户列表
	 * @param backUser
	 */
	public List<BackUser> findList(BackUser backUser);
	/**
	 * 根据用户ID查找用户
	 * @param userId
	 * @return
	 */
	public BackUser getBackUserByUuid(String userId);
	
	/**
	 * 查询用户列表
	 */
	public List<BackUser> findUserByDispatch(BackUser backUser);
	
	/**
	 * 根据用户id查询对应的公司id
	 * @param id
	 * @return
	 */
//	public String getCompanyId(Integer userId);
	
	/**
	 * 查询出含有M3+及以上组的公司
	 * @param companyPartmer 
	 * @return
	 */
	public List<BackUser> getM3Companys(HashMap<String, Object> companyPartmer);
	
	/**
	 * 查询出公司最早添加的催收员(启用中)
	 * @param map
	 * @return
	 */
	public BackUser getEarliestCollection(HashMap<String, Object> params);
	
	/**
	 * 查询出公司M3+及以上组其他催收员
	 * @param map
	 * @return
	 */
	public List<BackUser> getOtherCollections(HashMap<String, Object> params);

	
	/**
	 * 根据状态和公司id查询对应的催收员
	 * @param map
	 * @return
	 */
	public List<BackUser> getUsersByStatusAndCompanyId(HashMap<String, Object> params);
	
	/**
	 * 根据参数修改催收员的状态
	 * @param map
	 */
	public void disableOrEnableCollections(HashMap<String, Object> params);

}
