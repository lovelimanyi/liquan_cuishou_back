package com.info.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.Content;

@Repository
public interface IContentDao {
	/**
	 * 插入
	 * 
	 * @param zbNews
	 * @return
	 */
	public int insert(Content content);

	/**
	 * 删除
	 * 
	 * @return
	 */
	public int delete(Content content);

	/**
	 * 修改
	 * 
	 * @return
	 */
	public int update(Content content);

	/**
	 * 修改点击量
	 * 
	 * @return
	 */
	public int updateViewCount(Integer id);

	/**
	 * 根据主键查询
	 * 
	 * @param id主键
	 * @return
	 */
	public Content findById(Integer id);

	/**
	 * 批量插入文章
	 * 
	 * @param list
	 * @return
	 */
	public int batchInsert(List<Content> list);

	/**
	 * 批量删除文章
	 * 
	 * @param list
	 * @return
	 */
	public int deleteByFromUrl(List<String> list);
}
