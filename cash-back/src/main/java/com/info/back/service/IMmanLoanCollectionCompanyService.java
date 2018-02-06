package com.info.back.service;

import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.BackUser;
import com.info.web.pojo.MmanLoanCollectionCompany;

public interface IMmanLoanCollectionCompanyService {
	
	/**
	 * 根据条件查询公司list
	 * @param mmanLoanCollectionCompany
	 * @return
	 */
	public List<MmanLoanCollectionCompany> getList(MmanLoanCollectionCompany mmanLoanCollectionCompany);
	/**
	 * 查询公司列表
	 * @return
	 */
	public List<MmanLoanCollectionCompany> selectCompanyList();
	
	/**
	 * 根据用户查询用户绑定的公司ID
	 */
	public List<MmanLoanCollectionCompany> findCompanyByUserId(BackUser user);

	public MmanLoanCollectionCompany getCompanyById(String id);

    List<MmanLoanCollectionCompany> getCompanyByIds(HashMap<String,Object> param);
}
