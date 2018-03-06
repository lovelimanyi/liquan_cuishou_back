package com.info.back.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.back.result.JsonResult;
import com.info.web.pojo.MmanUserLoan;

/**
 * 查询MmanUseLoan列表信息
 * @author Administrator
 *
 */
public interface IMmanUserLoanService {
	
	public List<MmanUserLoan> findMmanUserLoanList(MmanUserLoan queryMmanUsersLoan);
	
	public List<MmanUserLoan> findMmanUserLoanList2(MmanUserLoan queryMmanUsersLoan);
	
	public void updateMmanUserLoan(MmanUserLoan manUsersLoan);
	
	public int saveNotNull(MmanUserLoan mmanUserLoan);
	
	public int updateNotNull(MmanUserLoan mmanUserLoan);

	public MmanUserLoan get(String loanId);
	
	public int updatePaymoney(MmanUserLoan loan);


	List<String> getOverdueOrder(Map<String, Object> map);
}
