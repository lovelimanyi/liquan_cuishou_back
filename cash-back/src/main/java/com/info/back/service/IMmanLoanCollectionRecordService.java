package com.info.back.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.back.result.JsonResult;
import com.info.web.pojo.*;
import com.info.web.util.PageConfig;

public interface IMmanLoanCollectionRecordService {

	/**
	 * 催款记录分页
	 * 
	 * @param params
	 * @return
	 */
	public PageConfig<MmanLoanCollectionRecord> findPage(
			HashMap<String, Object> params);

	/**
	 * 根据主键查询一条催收记录
	 * 
	 * @return 查询到的催收记录对象
	 */
	public MmanLoanCollectionRecord getOne(HashMap<String, Object> params);

	/**
	 * 添加一条催收记录
	 * 
	 * @param record
	 *            要添加的记录对象
	 */
	public void insert(MmanLoanCollectionRecord record);

	/**
	 * 更新一条催收记录
	 * 
	 * @param record
	 *            要更新的记录对象
	 */
	public void update(MmanLoanCollectionRecord record);

	/**
	 * 根据订单号查询历史
	 * 
	 * @return
	 */
	public List<MmanLoanCollectionRecord> findListRecord(String OrderId);

	public void assignCollectionOrderToRelatedGroup(
			List<MmanLoanCollectionOrder> mmanLoanCollectionOrderList,
			List<MmanLoanCollectionPerson> mmanLoanCollectionPersonList,
			Date date);
 
	public JsonResult saveCollection(Map<String, String> params,
			BackUser backUser);

	/**
	 * 发起代扣
	 * 
	 * @param params
	 * @return
	 */
	public JsonResult xjxWithholding(Map<String, String> params);

	/**
	 * 转派
	 * 
	 * @param user
	 * @param mmanLoanCollectionOrder
	 * @return
	 */
	public JsonResult batchDispatch(BackUser user,
			MmanLoanCollectionOrder mmanLoanCollectionOrder);

	/**
	 * 根据订单号查询代扣记录
	 * 
	 * @param id
	 * @return
	 */
	public List<CollectionWithholdingRecord> findWithholdRecord(String id);

	/**
	 *  创建分期还款业务
	 * @param list
	 * @param mmanLoanCollectionOrderOri
     * @return
     */
	public JsonResult insertInstallmentPayRecord(List<InstallmentPayInfoVo> list,MmanLoanCollectionOrder mmanLoanCollectionOrderOri);


	/**
	 * 根据订单ID查分期记录
	 * @param id
	 * @return
     */
	public List<InstallmentPayRecord> findInstallmentList(String id);

	/**
	 * 处理分期付款
	 * @param params
	 * @return
     */
	public JsonResult fqWithholding(Map<String, String> params);
}
