package com.info.back.dao;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.Collection;

@Repository
public interface ICollectionDao {
	//添加催收员
	public Integer insert(Collection collection);
	//查询催收员信息
	public Collection findOneCollection(Integer id);
	//修改催收员信息
	public int updateById(Collection collection);
	//统计催收员未完成的订单
	public int findOrderCollection(String id);
	//删除催收员
	public int del(Integer id);
	//删除催员订单
	public void delCollectionIdOrder(String uuid);
	//删除标记为删除的订单
	public int deleteTagDelete();
	//统计标记为删除的订单
	public int findTagDelete();
	//标记催收员为删除
	public int updateDeleteCoection(String id);
}
