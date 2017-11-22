package com.info.back.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.back.result.JsonResult;
import com.info.web.pojo.MmanLoanCollectionCompany;
import com.info.web.util.PageConfig;
import org.apache.commons.lang3.StringUtils;

public interface ICollectionCompanyService {
	
	/**
	 * 查询公司列表
	 * @param params
	 * @return
	 */
	PageConfig<MmanLoanCollectionCompany> findPage(HashMap<String, Object> params);
	/**
	 * 添加公司
	 * @param
	 * @return
	 */
	JsonResult saveCompany(Map<String, String>  params);
	/**
	 * 修改
	 * @param
	 * @return
	 */
	JsonResult updateCompany(Map<String, String>  params);
	/**
	 * 删除公司
	 * @param id
	 * @return
	 */
	JsonResult deleteCompany(String id);
	/**
	 * 根据id查询公司信息
	 * @param compayId
	 * @return
	 */
	MmanLoanCollectionCompany get(String compayId);
	/**
	 * 查询所有公司记录
	 * @return
	 */
	List<MmanLoanCollectionCompany> selectCompanyList();


	List<MmanLoanCollectionCompany> getCompanyList(HashMap<String,Object> map);

	List<MmanLoanCollectionCompany> getCompanyIps();

	List<MmanLoanCollectionCompany> dealwithCompanyInfo(List<MmanLoanCollectionCompany> companys);
}
