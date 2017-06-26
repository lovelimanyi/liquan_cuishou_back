package com.info.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.CreditLoanPayDetail;

@Repository
public interface ICreditLoanPayDetailDao {
	
	public int saveNotNull(CreditLoanPayDetail CreditLoanPayDetail);
	
	
	public int deleteid(String id);


	public List<CreditLoanPayDetail> findPayDetail(String payId);
	
}
