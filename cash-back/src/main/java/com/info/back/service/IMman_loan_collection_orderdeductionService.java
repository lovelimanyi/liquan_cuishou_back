package com.info.back.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.back.result.JsonResult;
import com.info.web.pojo.AuditCenter;
import com.info.web.pojo.CountCollectionManage;
import com.info.web.pojo.Mman_loan_collection_orderdeduction;
import com.info.web.util.PageConfig;

public interface IMman_loan_collection_orderdeductionService {
	/**
	 * 
	 * 减免申请
	 * @param params
	 * @return
	 */
	public  JsonResult saveorderdeduction(HashMap<String, Object> params);
	/**
	 *  减免记录列表
	 * @param params
	 * @return
	 */
	public PageConfig<Mman_loan_collection_orderdeduction> findPage(HashMap<String, Object> params);

	/**
	 * 单条查询
	 * 
	 */
    public List<Mman_loan_collection_orderdeduction> findAllList(String id);
}
