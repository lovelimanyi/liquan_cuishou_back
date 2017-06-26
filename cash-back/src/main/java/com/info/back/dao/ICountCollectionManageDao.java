package com.info.back.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.CountCollectionManage;
@Repository
public interface ICountCollectionManageDao {
	/**
	 * 查询所有的管理跟踪记录
	 * @param params
	 * @return
	 */
	public List<CountCollectionManage> findAll(HashMap<String, Object> params);
	public Integer findAllCount(HashMap<String, Object> params);
	
	/**
	 * 获取一条管理跟踪记录 
	 * @param id 要查询的记录id
	 * @return 查询到的记录对象
	 */
	public CountCollectionManage getOne(Integer id);
	/**
	 * 执行存储过程
	 */
	public void callManage(HashMap<String, Object> params);
}
