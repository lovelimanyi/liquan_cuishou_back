package com.info.back.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.back.result.JsonResult;
import com.info.web.pojo.SysDict;
import com.info.web.util.PageConfig;

public interface ISysDictService {
	/**
	 * 查询数据字典列表
	 * @param params
	 * @return
	 */
	 PageConfig<SysDict> findPage(HashMap<String, Object> params);
	/**
	 * 添加数据字典
	 * @param params
	 * @return
	 */
	 JsonResult saveSysDict(Map<String, String> params);
	/**
	 * 根据id查询数据字典
	 * @param compayId
	 * @return
	 */
	 SysDict get(String id);
	/**
	 * 修改数据字典
	 * @param params
	 * @return
	 */
	 JsonResult updateSysDict(Map<String, String> params);
	/**
	 * 逻辑删除数据字典
	 * @param sysDictId
	 * @return
	 */
	 JsonResult deleteSysDict(String id);
	/**
	 * 根据传入的类型查询该类型所有的状态
	 * @param type
	 * @return
	 */
	 List<SysDict> getStatus(String type);
	
	 List<SysDict> findDictByType(String type);

	List<SysDict> getOtherStatus(HashMap<String, Object> params);

}
