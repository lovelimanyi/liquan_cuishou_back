package com.info.back.vo.jxl;

import java.util.List;

public class MainService {
	private List<ServiceDetails> service_details;
	private Integer total_service_cnt;
	private String company_type;
	private String company_name;

	public List<ServiceDetails> getService_details() {
		return service_details;
	}

	public void setService_details(List<ServiceDetails> serviceDetails) {
		service_details = serviceDetails;
	}

	public Integer getTotal_service_cnt() {
		return total_service_cnt;
	}

	public void setTotal_service_cnt(Integer totalServiceCnt) {
		total_service_cnt = totalServiceCnt;
	}

	public String getCompany_type() {
		return company_type;
	}

	public void setCompany_type(String companyType) {
		company_type = companyType;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String companyName) {
		company_name = companyName;
	}

}
