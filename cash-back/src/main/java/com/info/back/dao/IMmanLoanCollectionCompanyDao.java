package com.info.back.dao;

import java.util.List;

import java.util.HashMap;

import org.springframework.stereotype.Repository;


import com.info.web.pojo.BackUser;
import com.info.web.pojo.MmanLoanCollectionCompany;


@Repository
public interface IMmanLoanCollectionCompanyDao {

    List<MmanLoanCollectionCompany> getList(MmanLoanCollectionCompany mmanLoanCollectionCompany);

    /**
     * 添加公司信息
     *
     * @param mmanLoanCollectionCompany
     * @return
     */
    int insert(MmanLoanCollectionCompany mmanLoanCollectionCompany);

    /**
     * 根据ID查询公司信息
     *
     * @param id
     * @return
     */
    MmanLoanCollectionCompany get(String id);

    /**
     * 修改公司信息
     *
     * @param mmanLoanCollectionCompany
     * @return
     */
    int update(MmanLoanCollectionCompany mmanLoanCollectionCompany);

    /**
     * 根据公司id查询相关用户
     *
     * @param
     * @return
     */
    List<BackUser> findcomapyIdUser(String comapyId);

    /**
     * 根据公司编号删除公司
     *
     * @param compayId
     */
    int del(String compayId);

    /**
     * 查询公司里面是否有未完成的订单
     *
     * @param compayId
     * @return
     */
    int findcomapyIdOrder(String compayId);

    /**
     * 批量删除催收员
     *
     * @param
     * @return
     */
    Integer delUser(HashMap<String, Object> backUserUUId);

    /**
     * 标记订单为删除
     *
     * @param
     */
    void updateOrderStatus(HashMap<String, Object> backUserUUId);

    /**
     * 查询所有公司
     *
     * @return
     */
    List<MmanLoanCollectionCompany> selectCompanyList();

    /**
     * 根据用户查询用户绑定的公司ID
     */
    List<MmanLoanCollectionCompany> findCompanyByUserId(Integer userId);


    List<MmanLoanCollectionCompany> getCompanyList(HashMap<String, Object> map);

    MmanLoanCollectionCompany getCompanyById(String id);

    List<MmanLoanCollectionCompany> getCompanyIps();

    List<MmanLoanCollectionCompany> getCompanyByIds(HashMap<String, Object> param);
}
