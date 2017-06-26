package com.info.back.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.ICreditLoanPayDao;
import com.info.back.utils.IdGen;
import com.info.web.pojo.CreditLoanPay;

@Service
public class CreditLoanPayService implements ICreditLoanPayService{

	@Autowired
	ICreditLoanPayDao creditLoanPayDao;
	
	public CreditLoanPay findByLoanId(String loanId){
		return creditLoanPayDao.findByLoanId(loanId);
	}

	public void save(CreditLoanPay creditLoanPay) {
		if(StringUtils.isBlank(creditLoanPay.getId())){
			creditLoanPay.setId(IdGen.uuid());
			creditLoanPayDao.insertCreditLoanPay(creditLoanPay);
		}else{
			creditLoanPayDao.updateCreditLoanPay(creditLoanPay);
		}
	}

	@Override
	public int saveNotNull(CreditLoanPay creditLoanPay) {
		return creditLoanPayDao.saveNotNull(creditLoanPay);
	}

	@Override
	public int FindCount(String id) {
		return creditLoanPayDao.FindCount(id);
	}

	@Override
	public int updateNotNull(CreditLoanPay creditLoanPay) {
		return creditLoanPayDao.updateNotNull(creditLoanPay);
	}

	@Override
	public CreditLoanPay get(String payId) {
		return creditLoanPayDao.get(payId);
	}

	@Override
	public void updateCreditLoanPay(CreditLoanPay creditLoanPay) {
		// TODO Auto-generated method stub
		creditLoanPayDao.updateCreditLoanPay(creditLoanPay);
	}
}
