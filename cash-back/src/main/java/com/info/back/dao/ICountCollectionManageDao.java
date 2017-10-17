package com.info.back.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.CountCollectionManage;

@Repository
public interface ICountCollectionManageDao {
    /**
     * 查询所有的管理跟踪记录
     *
     * @param params
     * @return
     */
    List<CountCollectionManage> findAll(HashMap<String, Object> params);

    Integer findAllCount(HashMap<String, Object> params);

    /**
     * 获取一条管理跟踪记录
     *
     * @param id 要查询的记录id
     * @return 查询到的记录对象
     */
    CountCollectionManage getOne(Integer id);

    /**
     * 执行存储过程
     */
    void callManage(HashMap<String, Object> params);


    /**
     * 删除管理统计记录
     */
    void deleteManageList(HashMap<String, Object> params);

}
