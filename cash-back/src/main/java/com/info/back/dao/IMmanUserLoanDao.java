package com.info.back.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.MmanUserLoan;

@Repository
public interface IMmanUserLoanDao {

	List<MmanUserLoan> findMmanUserLoanList(MmanUserLoan queryMmanUsersLoan) ;
	
	List<MmanUserLoan> findMmanUserLoanList2(MmanUserLoan queryMmanUsersLoan) ;
	
	void updateMmanUserLoan(MmanUserLoan queryMmanUsersLoan);
	
	int saveNotNull(MmanUserLoan mmanUserLoan);

	int updateNotNull(MmanUserLoan mmanUserLoan);

	MmanUserLoan get(String loanId);
	
	int updatePaymoney(MmanUserLoan loan);

    List<String> getOverdueOrder(Map<String, Object> map);

	List<String> getOverdueOrderIds(Map<String, Object> map);
}
