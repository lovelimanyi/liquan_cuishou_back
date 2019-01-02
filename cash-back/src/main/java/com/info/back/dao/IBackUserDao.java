package com.info.back.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.web.pojo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 类描述：用户dao层 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午01:53:41 <br>
 */
@Repository
public interface IBackUserDao {
    /**
     * 根据条件查询用户
     *
     * @param map 参数名 ：userAccount，含义：用户名 <br>
     *            参数名 ：status 含义：状态
     * @return 角色list
     */
    List<BackUser> findAll(HashMap<String, Object> params);

    /**
     * 插入用户对象
     *
     * @param backUser
     */
    void insert(BackUser backUser);

    /**
     * 根据主键删除对象
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 更新用户对象
     *
     * @param backUser
     */
    void updateById(BackUser backUser);

    /**
     * 更新密码
     *
     * @param backUser
     */
    void updatePwdById(BackUser backUser);

    /**
     * 查询BackUser等综合信息
     */
    List<MmanLoanCollectionPerson> findUnCompleteCollectionOrderByCurrentUnCompleteCountListByMap(Map<String, String> param);

    /**
     * 查询BackUser等综合信息
     */
    List<MmanLoanCollectionPerson> findbackUserByLoanUserPhone(Map<String, String> param);


    /**
     * 查询当前催收员今日派到手里的订单数(包括已完成的)
     *
     * @param mmanLoanCollectionPerson 催收员
     */
    Integer findTodayAssignedCount(MmanLoanCollectionPerson mmanLoanCollectionPerson);

    /**
     * 查询公司列表
     *
     * @return
     */
    List<MmanLoanCollectionCompany> selectCompanyList();


    /**
     * 根据用户Id查询用户角色Id
     *
     * @param userId
     * @return
     */
    List<BackUserRole> findUserRoleByUserId(String userId);

    /**
     * 修改用户roleID
     *
     * @param roleMap
     */
    void updateRoleId(HashMap<String, Object> roleMap);

    /**
     * 根据id查询对应用户的手机号个数
     *
     * @param
     * @return
     */
    Integer getUserPhoneCount(Map<String, Object> params);

    /**
     * 查询用户列表
     *
     * @param backUser
     */
    List<BackUser> findList(BackUser backUser);

    /**
     * 根据用户ID查找用户
     *
     * @param userId
     * @return
     */
    BackUser getBackUserByUuid(String userId);

    /**
     * 查询用户列表
     */
    List<BackUser> findUserByDispatch(BackUser backUser);

    /**
     * 查询出含有M3+及以上组的公司
     *
     * @param companyPartmer
     * @return
     */
    List<BackUser> getM3Companys(HashMap<String, Object> companyPartmer);

    /**
     * 查询出公司最早添加的催收员(启用中)
     *
     * @param
     * @return
     */
    BackUser getEarliestCollection(HashMap<String, Object> params);

    /**
     * 查询出公司M3+及以上组其他催收员
     *
     * @param
     * @return
     */
    List<BackUser> getOtherCollections(HashMap<String, Object> params);


    /**
     * 根据状态和公司id查询对应的催收员
     *
     * @param
     * @return
     */
    List<BackUser> getUsersByStatusAndCompanyId(HashMap<String, Object> params);

    /**
     * 根据参数修改催收员的状态
     *
     * @param
     */
    void disableOrEnableCollections(HashMap<String, Object> params);

    BackUser getUserByAccount(String userAccount);

    Integer selectUserCount4Estimate(@Param("groupLevel")String groupLevel);

    List<BackUser> getUserByCompanyIdAndGroupLevel(Map<String, Object> userMap);

    DianXiaoBackUser dianXiaoBackUserByOrderCount(Map<String, Object> map);

    List<CollectionBackUser> getBackUserGroupByOrderCount(Map<String, Object> map);

    BackUser getUserByName(String user);

    List<BackUser> getUserByCompany(String companyName);

    CollectionBackUser getOneCollectionBackUserGroupByOrderCount(Map<String, Object> param);
}
