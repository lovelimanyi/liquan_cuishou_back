package com.info.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IMmanUserLoanDao;
import com.info.web.pojo.MmanUserLoan;

@Service
public class MmanUserLoanService implements IMmanUserLoanService{
	

	@Autowired
	IMmanUserLoanDao manUserLoanDao;
	
	public List<MmanUserLoan> findMmanUserLoanList(
			MmanUserLoan queryMmanUsersLoan) {
		return manUserLoanDao.findMmanUserLoanList(queryMmanUsersLoan);
	}


	public void updateMmanUserLoan(MmanUserLoan manUsersLoan) {
		 manUserLoanDao.updateMmanUserLoan(manUsersLoan);
	}


	@Override
	public int saveNotNull(MmanUserLoan mmanUserLoan) {
		return manUserLoanDao.saveNotNull(mmanUserLoan);
	}


	@Override
	public int updateNotNull(MmanUserLoan mmanUserLoan) {
		return manUserLoanDao.updateNotNull(mmanUserLoan);
	}


	@Override
	public MmanUserLoan get(String loanId) {
		return manUserLoanDao.get(loanId);
	}


	@Override
	public List<MmanUserLoan> findMmanUserLoanList2(
			MmanUserLoan queryMmanUsersLoan) {
		return manUserLoanDao.findMmanUserLoanList2(queryMmanUsersLoan);
	}


	@Override
	public int updatePaymoney(MmanUserLoan loan) {
		
		return manUserLoanDao.updatePaymoney(loan);
	}
}
