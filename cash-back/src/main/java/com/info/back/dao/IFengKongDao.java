package com.info.back.dao;

import java.util.List;

import com.info.web.pojo.Collection;
import com.info.web.pojo.CollectionAdvice;
import org.springframework.stereotype.Repository;

import com.info.web.pojo.FengKong;

@Repository
public interface IFengKongDao {

	/**
	 * 查询可用的风控标签列表
	 * @param
	 * @return
	 */
	List<FengKong> getFengKongList();
	/**
	 * 根据id查询风控标签
	 * @param id
	 * @return
	 */
	FengKong getFengKongById(Integer id);
	/**
	 *更新风控标签
	 * @param id
	 * @return
	 */
	int update(FengKong fengKong);
	/**
	 *新增风控标签
	 * @param id
	 * @return
	 */
	int insert(FengKong fengKong);

	int insertCollectionAdvice(CollectionAdvice collectionAdvice);

	/**
	 * 根据风控标签查询该标签是否存在
	 * @param fengKong
	 * @return
	 */
	int getFengKongByLabel(FengKong fengKong);

	/**
	 * 添加催收建议
	 * @param advice
	 * @return
	 */
	int insertAdvice(CollectionAdvice advice);
}
