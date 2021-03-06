package com.info.back.service;

import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.CountCashBusiness;
import com.info.web.pojo.CountCollectionManage;
import com.info.web.util.PageConfig;

public interface ICountCollectionManageService {
	
	/**
	 * 考勤记录分页
	 * 
	 * @param params
	 * @return
	 */
	PageConfig<CountCollectionManage> findPage(
			HashMap<String, Object> params);
	/**
	 * 查询出所有满足条件的记录
	 * @param params
	 * @return
	 */
	List<CountCollectionManage> findAll(HashMap<String, Object> params);
	
	Integer findAllCount(HashMap<String, Object> params);
	
	/**
	 * 根据主键查询一条考勤记录
	 * @return 查询到的考勤记录对象
	 */
	CountCollectionManage getOne(HashMap<String, Object> params);
	
	/**
	 * 执行存储过程
	 */
	void countCallManage(HashMap<String, Object> params);

	/**
	 * 分页现金侠业务量统计
	 * @param params
	 * @return
     */
	PageConfig<CountCashBusiness> getCountCashBusinessPage(HashMap<String, Object> params);

	void deleteManageList(HashMap<String, Object> params);
}
