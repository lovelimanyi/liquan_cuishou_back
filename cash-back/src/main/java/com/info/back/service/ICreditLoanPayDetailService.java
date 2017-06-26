package com.info.back.service;


import java.util.List;

import com.info.web.pojo.CreditLoanPayDetail;

public interface ICreditLoanPayDetailService {
	
	
	public int saveNotNull(CreditLoanPayDetail CreditLoanPayDetail);
	
	
	public int deleteid(String id);


	public List<CreditLoanPayDetail> findPayDetail(String payId);
	
	

}
