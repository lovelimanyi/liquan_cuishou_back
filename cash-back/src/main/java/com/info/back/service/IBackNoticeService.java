package com.info.back.service;

import java.util.HashMap;

import com.info.web.pojo.BackNotice;
import com.info.web.util.PageConfig;

public interface IBackNoticeService {
	/**
	 * 分页查询
	 * 
	 * @param params
	 * @return
	 */
	public PageConfig<BackNotice> findPage(HashMap<String, Object> params);

	/**
	 * 根据主键查询
	 * 
	 * @param params
	 * @return
	 */
	public BackNotice findById(Integer id);

	/**
	 * 根据code查询对象
	 * 
	 * @param params
	 * @return
	 */
	public BackNotice findByCode(String noticeCode);

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
	public int updateById(BackNotice backNotice);

	/**
	 * 插入
	 * 
	 * @param BackNotice
	 * @return
	 */
	public int insert(BackNotice backNotice);

}
