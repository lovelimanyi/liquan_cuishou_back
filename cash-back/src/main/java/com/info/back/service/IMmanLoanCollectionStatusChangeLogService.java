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

	/**
	 * 根据orderId删除流转日志
	 * @param orderId
	 * @return
	 */
	int deleteLogByOrderId(String orderId);

    void insertMmanLoanCollectionStatusChangeLog(String orderId,String beforeStatus,String afterStatus, String type, String operatorName, String remark, String companyId, String currentCollectionUserId, String currentCollectionUserLevel, String currentCollectionOrderLevel);
}
