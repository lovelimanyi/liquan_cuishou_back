package com.info.back.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.CollectionStatistics;
import com.info.web.pojo.CollectionStatisticsOrder;
import com.info.web.pojo.StatisticsDistribute;

@Repository
public interface ICollectionStatisticsDao {
	/**
	 * 本金总计
	 * @param map
	 * @return
	 */
	public List<CollectionStatistics> countPrincipal(Map<String,Object> map);
	public List<CollectionStatisticsOrder> countPrincipalOrder(Map<String,Object> map);
	/**
	 * 7日折线图
	 * @param map
	 * @param countType 统计维度，money or orderNum
	 * @return
	 */
	public List<Map<String,Object>> countBySevenDay(Map<String,Object> map);
	public List<Map<String,Object>> countOrderBySevenDay(Map<String,Object> map);
	/**
	 * 本金分布
	 * @param map
	 * @return
	 */
	public List<StatisticsDistribute> countByDistribute(Map<String,Object> map);


}
