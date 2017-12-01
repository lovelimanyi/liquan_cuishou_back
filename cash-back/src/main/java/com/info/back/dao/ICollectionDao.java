package com.info.back.dao;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.Collection;

@Repository
public interface ICollectionDao {
    /**
     * 添加催收员
     *
     * @param collection
     * @return
     */
    Integer insert(Collection collection);

    /**
     * 查询催收员信息
     *
     * @param id
     * @return
     */
    Collection findOneCollection(Integer id);

    /**
     * 修改催收员信息
     *
     * @param collection
     * @return
     */
    int updateById(Collection collection);

    /**
     * 统计催收员未完成的订单
     *
     * @param id
     * @return
     */
    int findOrderCollection(String id);

    /**
     * 删除催收员
     *
     * @param id
     * @return
     */
    int del(Integer id);

    /**
     * 删除催员订单
     *
     * @param uuid
     */
    void delCollectionIdOrder(String uuid);

    /**
     * 删除标记为删除的订单
     *
     * @return
     */
    int deleteTagDelete();

    /**
     * 统计标记为删除的订单
     *
     * @return
     */
    int findTagDelete();

    /**
     * 标记催收员为删除
     *
     * @param id
     * @return
     */
    int updateDeleteCoection(String id);

    /**
     * 根据姓名查询催收员
     *
     * @param username
     * @return
     */
    Collection getCollectionByUserName(String username);

    /**
     * 根据账号查询催收员
     *
     * @param userAccount
     * @return
     */
    Collection getCollectionByUserAccount(String userAccount);
}
