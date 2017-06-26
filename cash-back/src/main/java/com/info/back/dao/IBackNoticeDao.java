package com.info.back.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.BackNotice;

@Repository
public interface IBackNoticeDao {
	/**
	 * 
	 * @param params
	 * @return
	 */
	public List<BackNotice> findParams(HashMap<String, Object> params);

	/**
	 * 根据主键删除
	 * 
	 * @param id
	 * @return
	 */
	public int deleteById(Integer id);

	/**
	 * 根据主键更新
	 * 
	 * @param id
	 * @return
	 */
	public int update(BackNotice backNotice);

	/**
	 * 插入
	 * 
	 * @param BackNotice
	 * @return
	 */
	public int insert(BackNotice backNotice);
}
