package com.info.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.MmanUserLoan;

@Repository
public interface IMmanUserLoanDao {

	public List<MmanUserLoan> findMmanUserLoanList(MmanUserLoan queryMmanUsersLoan) ;
	
	public List<MmanUserLoan> findMmanUserLoanList2(MmanUserLoan queryMmanUsersLoan) ;
	
	public void updateMmanUserLoan(MmanUserLoan queryMmanUsersLoan);
	
	public int saveNotNull(MmanUserLoan mmanUserLoan);

	public int updateNotNull(MmanUserLoan mmanUserLoan);

	public MmanUserLoan get(String loanId);
	
	public int updatePaymoney(MmanUserLoan loan);
}
