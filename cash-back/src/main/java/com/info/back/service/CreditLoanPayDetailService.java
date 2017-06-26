package com.info.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.ICreditLoanPayDetailDao;
import com.info.web.pojo.CreditLoanPayDetail;

@Service
public class CreditLoanPayDetailService implements ICreditLoanPayDetailService{
	
	@Autowired
	private ICreditLoanPayDetailDao creditLoanPayDetailDao;

	@Override
	public int saveNotNull(CreditLoanPayDetail CreditLoanPayDetail) {
		return creditLoanPayDetailDao.saveNotNull(CreditLoanPayDetail);
	}

	@Override
	public int deleteid(String id) {
		return creditLoanPayDetailDao.deleteid(id);
	}

	@Override
	public List<CreditLoanPayDetail> findPayDetail(String payId) {
		return creditLoanPayDetailDao.findPayDetail(payId);
	}

}
