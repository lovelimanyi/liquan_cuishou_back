package com.info.back.service;

import java.util.List;
import java.util.Map;

import com.info.web.pojo.CollectionStatistics;
import com.info.web.pojo.CollectionStatisticsOrder;
import com.info.web.pojo.StatisticsDistribute;


public interface ICollectionStatisticsService {
    
	/**
	 * 本金总计
	 * @param map
	 * @return
	 */
	public CollectionStatistics countPrincipal(Map<String,Object> map);
	public CollectionStatistics countPrincipalNew(Map<String,Object> map);

	public CollectionStatisticsOrder countPrincipalOrder(Map<String,Object> map);
	/**
	 * 折线图
	 * @param map
	 * @param countType 统计维度，money or orderNum
	 * @return
	 */
	public List<Object> countBySevenDay(Map<String,Object> map);
	public List<Object> countOrderBySevenDay(Map<String,Object> map);
	/**
	 * 本金分布
	 * @param map
	 * @param countType
	 * @return
	 */
	public StatisticsDistribute countByDistribute(Map<String, Object> map);
	public List<StatisticsDistribute> countByDistributeNew(Map<String, Object> map);
}
