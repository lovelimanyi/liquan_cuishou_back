package com.info.back.dao;

import java.util.List;
import java.util.Map;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.SysDict;

@Repository
public interface ISysDictDao {

	/**
	 * 添加数据字典
	 * @param sysDict
	 * @return
	 */
	int insert(SysDict sysDict);
	/**
	 * 根据ID查询数据字典
	 * @param id
	 * @return
	 */
	SysDict get(String id);
	/**
	 * 修改数据字典
	 * @param sysDict
	 * @return
	 */
	int update(SysDict sysDict);
	/**
	 * 逻辑删除数据字典
	 * @param id
	 * @return
	 */
	int delete(String id);
	public List<SysDict> findDictByType(String type);
	
	/**
	 * 根据传入的类型查询该类型所有的状态
	 * @param type
	 * @return
	 */
	public List<SysDict> getStatus(String type);

    List<SysDict> getOtherStatus (HashMap<String, Object> params);
	
}
