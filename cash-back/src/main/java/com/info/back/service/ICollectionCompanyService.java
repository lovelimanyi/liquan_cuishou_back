package com.info.back.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.back.result.JsonResult;
import com.info.web.pojo.MmanLoanCollectionCompany;
import com.info.web.util.PageConfig;

public interface ICollectionCompanyService {
	
	/**
	 * 查询公司列表
	 * @param params
	 * @return
	 */
	public PageConfig<MmanLoanCollectionCompany> findPage(HashMap<String, Object> params);
	/**
	 * 添加公司
	 * @param mmanLoanCollectionCompany
	 * @return
	 */
	public JsonResult saveCompany(Map<String, String>  params);
	/**
	 * 修改
	 * @param mmanLoanCollectionCompany
	 * @return
	 */
	public JsonResult updateCompany(Map<String, String>  params);
	/**
	 * 删除公司
	 * @param id
	 * @return
	 */
	public JsonResult deleteCompany(String id);
	/**
	 * 根据id查询公司信息
	 * @param compayId
	 * @return
	 */
	public MmanLoanCollectionCompany get(String compayId);
	/**
	 * 查询所有公司记录
	 * @return
	 */
	public List<MmanLoanCollectionCompany> selectCompanyList();


	public  List<MmanLoanCollectionCompany> getCompanyList(HashMap<String,Object> map);
}
