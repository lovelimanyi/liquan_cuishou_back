package com.info.back.service;

import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.MmanLoanCollectionStatusChangeLog;
import com.info.web.util.PageConfig;

public interface IMmanLoanCollectionStatusChangeLogService {
	/**
	 * 催款流转日志记录分页
	 *
	 * @param params
	 * @return
	 */
	PageConfig<MmanLoanCollectionStatusChangeLog> findPage(
			HashMap<String, Object> params);



	void insert(MmanLoanCollectionStatusChangeLog mmanLoanCollectionStatusChangeLog);


	/**
	 * 根据orderid查询日志
	 * @param OrderId
	 * @return
	 */
	List<MmanLoanCollectionStatusChangeLog> findListLog(String orderId);



	int getAllCount(HashMap<String,Object> params);
}
