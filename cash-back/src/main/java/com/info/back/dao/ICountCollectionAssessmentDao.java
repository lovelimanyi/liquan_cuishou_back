package com.info.back.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.CountCollectionAssessment;
@Repository
public interface ICountCollectionAssessmentDao {
	/**
	 * 查询所有的考核记录
	 * @param params
	 * @return
	 */
	List<CountCollectionAssessment> findAll(HashMap<String, Object> params);
	Integer findAllCount(HashMap<String, Object> params);
	/**
	 * 累计
	 * @param params
	 * @return
	 */
	List<CountCollectionAssessment> findAllByGroup(HashMap<String, Object> params);
	Integer findAllCountByGroup(HashMap<String, Object> params);

	/**
	 * 公司
	 * @param params
	 * @return
     */
	List<CountCollectionAssessment> findAllByCompany(HashMap<String, Object> params);
	Integer findAllCountByCompany(HashMap<String, Object> params);
	
	/**
	 * 获取一条考核记录 
	 * @param id 要查询的记录id
	 * @return 查询到的记录对象
	 */
	CountCollectionAssessment getOne(Integer id);
	/**
	 * 执行存储过程
	 * @param params
	 */
	void callAssessment(HashMap<String, Object> params);
	void callMGroupAssessment(HashMap<String, Object> params);

	/**
	 * 查询考核统计
	 */
	List<CountCollectionAssessment> queryExamineList(HashMap<String, Object> params);

	/**
	 * 查询管理统计
     */
	List<CountCollectionAssessment> queryManageList(HashMap<String, Object> params);

	/**
	 * 查询催记统计
	 * @param params
	 * @return
     */
	List<CountCollectionAssessment> queryCollectionList(HashMap<String, Object> params);

	/**
	 * 插入考核统计记录
	 * @param list
     */
	void insertExamineList(List<CountCollectionAssessment> list);

	/**
	 * 插入管理统计记录
	 * @param list
     */
	void insertManageList(List<CountCollectionAssessment> list);

	/**
	 * 插入催记统计记录
	 * @param list
     */
	void insertCollectionList(List<CountCollectionAssessment> list);

	/**
	 * 删除考核统计记录
	 */
	void deleteAssessmentList(HashMap<String,Object> params);

	/**
	 * 删除订单统计记录
	 */
	void deleteCountCollectionOrder(HashMap<String,Object> params);
}
