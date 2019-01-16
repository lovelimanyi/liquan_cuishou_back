package com.info.back.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;
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

	int selectCollectionCountByLoanEndTime(@Param("overDate")Date overDate);

	List<HashMap<String,Object>> selectCollectionCountBetweenLoanEndTime(Map<String,Object> map);

	List<String> getOverdueOrderIdsNeedBeDispatch(Map<String, Object> map);

	void updateRemark(List<String> list);

    List<String> getNeedUpgradeOrderLoanIds();

	String getMerchantNoByLoanId(String loanId);
}
