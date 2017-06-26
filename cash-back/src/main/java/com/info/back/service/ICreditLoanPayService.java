package com.info.back.service;

import com.info.web.pojo.CreditLoanPay;

public interface ICreditLoanPayService {

	public CreditLoanPay findByLoanId(String loanId);
	
	
	public void save(CreditLoanPay creditLoanPay);
	
	public int saveNotNull(CreditLoanPay creditLoanPay);
	
	public int FindCount(String id);
	
	
	public int updateNotNull(CreditLoanPay creditLoanPay);


	public CreditLoanPay get(String payId);
	
	public void updateCreditLoanPay(CreditLoanPay creditLoanPay);
	
}
