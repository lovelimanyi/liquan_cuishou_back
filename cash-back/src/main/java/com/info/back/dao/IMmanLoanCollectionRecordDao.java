package com.info.back.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.MmanLoanCollectionRecord;

/**
 * 催收记录dao层
 * 
 * @author hxj
 * 
 */
@Repository
public interface IMmanLoanCollectionRecordDao {
	/**
	 * 查询所有催收记录
	 * @return 查询到的所有催收记录
	 */
	List<MmanLoanCollectionRecord> findAll(HashMap<String, Object> params);
	
	/**
	 * 获取一条催收记录 
	 * @param id 要查询的记录id
	 * @return 查询到的记录对象
	 */
	MmanLoanCollectionRecord getOneCollectionRecord(Integer id);
	
	/**
	 * 添加一条催收记录
	 */
	void insert(MmanLoanCollectionRecord record);
	
	/**
	 * 更新一条记录
	 */
	void update(MmanLoanCollectionRecord record);
	
	/**
	 * 根据订单号查询历史
	 * @return
	 */
	List<MmanLoanCollectionRecord> findListRecord(HashMap<String,Object> map);
}
