package com.info.back.dao;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.CreditLoanPay;

@Repository
public interface ICreditLoanPayDao {
	
	CreditLoanPay findByLoanId(String loanId);
	
	void updateCreditLoanPay(CreditLoanPay creditLoanPay);
	
	void insertCreditLoanPay(CreditLoanPay creditLoanPay);

	int saveNotNull(CreditLoanPay creditLoanPay);
	
	int FindCount(String id);
	
	int updateNotNull(CreditLoanPay creditLoanPay);

	CreditLoanPay get(String payId);
}
