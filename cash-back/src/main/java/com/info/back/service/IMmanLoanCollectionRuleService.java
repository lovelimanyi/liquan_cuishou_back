package com.info.back.service;

import java.util.HashMap;

import com.info.back.result.JsonResult;
import com.info.web.pojo.MmanLoanCollectionRule;
import com.info.web.util.PageConfig;

public interface IMmanLoanCollectionRuleService {
	/**
	 * 查询规则分配记录
	 * @param params
	 * @return
	 */
	PageConfig<MmanLoanCollectionRule> findPage(HashMap<String, Object> params);
	/**
	 * 根据id查找规则
	 * @param id
	 * @return
	 */
	MmanLoanCollectionRule getCollectionRuleById(String id);
	/**
	 * 修改规则
	 * @param id
	 * @return
	 */
	JsonResult updateCollectionRule(MmanLoanCollectionRule collectionRule);

}
