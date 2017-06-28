package com.info.back.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.MmanLoanCollectionStatusChangeLog;

@Repository
public interface IMmanLoanCollectionStatusChangeLogDao {
	/**
	 * 查询所有催收流转日志记录
	 *
	 * @return 查询到的催收流转日志记录
	 */
	List<MmanLoanCollectionStatusChangeLog> findAll(HashMap<String, Object> params);

	/**
	 * 新增 MmanLoanCollectionStatusChangeLog
	 *
	 * @param mmanLoanCollectionStatusChangeLog
	 */
	void insert(MmanLoanCollectionStatusChangeLog mmanLoanCollectionStatusChangeLog);


	List<MmanLoanCollectionStatusChangeLog> findListLog(String orderId);

	int findOrderSingle(HashMap<String, String> params);


	Integer findSystemUpToS2Log(HashMap<String, String> params);

	int findAllCount(HashMap<String, Object> params);
}
