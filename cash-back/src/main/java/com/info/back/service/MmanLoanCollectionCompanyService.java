package com.info.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IMmanLoanCollectionCompanyDao;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.MmanLoanCollectionCompany;

@Service
public class MmanLoanCollectionCompanyService implements IMmanLoanCollectionCompanyService{
	
	@Autowired
	private IMmanLoanCollectionCompanyDao mmanLoanCollectionCompanyDao;

	@Override
	public List<MmanLoanCollectionCompany> getList(MmanLoanCollectionCompany mmanLoanCollectionCompany) {
		return mmanLoanCollectionCompanyDao.getList(mmanLoanCollectionCompany);
	}
	@Override
	public List<MmanLoanCollectionCompany> selectCompanyList() {
		return mmanLoanCollectionCompanyDao.selectCompanyList();
	}
	@Override
	public List<MmanLoanCollectionCompany> findCompanyByUserId(BackUser user) {
		//当前用户既不是催收员又无法查询所有公司数据
		Integer userId=null;
		if(!"10021".equals(user.getRoleId()) && "1".equals(user.getViewdataStatus())){
			userId = user.getId();
		}
		return mmanLoanCollectionCompanyDao.findCompanyByUserId(userId);
	}

	@Override
	public MmanLoanCollectionCompany getCompanyById(String id) {
		return mmanLoanCollectionCompanyDao.getCompanyById(id);
	}

}
