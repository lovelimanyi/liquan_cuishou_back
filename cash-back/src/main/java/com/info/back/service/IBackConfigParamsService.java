package com.info.back.service;

import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.BackConfigParams;

public interface IBackConfigParamsService {
	/**
	 * 
	 * @param params
	 *            sysType参数分类ASSETS_TYPE是资产类型
	 * @return
	 */
	public List<BackConfigParams> findParams(HashMap<String, Object> params);

	/**
	 * 更新
	 * 
	 * @param list
	 * @return
	 */
	int updateValue(List<BackConfigParams> list, String type);
}
