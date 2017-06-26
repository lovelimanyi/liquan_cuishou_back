package com.info.back.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.back.result.JsonResult;
import com.info.web.pojo.FengKong;
import com.info.web.util.PageConfig;

public interface IFengKongService {
	/**
	 * 分页查询风控标签
	 * @param params
	 * @return
	 */
	PageConfig<FengKong> findPage(HashMap<String, Object> params);
	/**
	 * 查询可用的风控标签列表
	 * @param 
	 * @return
	 */
	List<FengKong> getFengKongList();
	/**
	 * 根据id获取风控标签
	 * @param id
	 * @return
	 */
	FengKong getFengKongById(Integer id);
	/**
	 * 更新风控标签
	 * @param fengKong
	 * @return
	 */
	JsonResult updateFengKong(FengKong fengKong);
	/**
	 * 新增风控标签
	 * @param fengKong
	 * @return
	 */
	JsonResult insert(FengKong fengKong);

	/**
	 * 保存催收建议
	 * @param params
	 * @return
	 */
    JsonResult saveCollectionAdvice(Map<String, String> params);
}
