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
	
	List<MmanUserLoan> findMmanUserLoanList(MmanUserLoan queryMmanUsersLoan);
	
	List<MmanUserLoan> findMmanUserLoanList2(MmanUserLoan queryMmanUsersLoan);
	
	void updateMmanUserLoan(MmanUserLoan manUsersLoan);
	
	int saveNotNull(MmanUserLoan mmanUserLoan);
	
	int updateNotNull(MmanUserLoan mmanUserLoan);

	MmanUserLoan get(String loanId);
	
	int updatePaymoney(MmanUserLoan loan);


	List<String> getOverdueOrder(Map<String, Object> map);

	List<String> getOverdueOrderIds(Map<String, Object> map);

	List<String> getOverdueOrderIdsNeedBeDispatch(Map<String, Object> map);

	void updateRemark(List<String> list);

    List<String> getNeedUpgradeOrderLoanIds();
}
