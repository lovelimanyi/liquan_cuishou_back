package com.info.back.dao;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.CreditLoanPay;

@Repository
public interface ICreditLoanPayDao {
	
	public CreditLoanPay findByLoanId(String loanId);
	
	public void updateCreditLoanPay(CreditLoanPay creditLoanPay);
	
	public void insertCreditLoanPay(CreditLoanPay creditLoanPay);

	public int saveNotNull(CreditLoanPay creditLoanPay);
	
	public int FindCount(String id);
	
	public int updateNotNull(CreditLoanPay creditLoanPay);

	public CreditLoanPay get(String payId);
}
