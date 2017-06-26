package com.info.back.vo.jxl;

import java.util.List;

public class CollectionContact {
	private String begin_date;
	private float total_amount;
	private String end_date;
	private Integer total_count;
	private List<ContactDetails> contact_details;
	private String contact_name;
	public String getBegin_date() {
		return begin_date;
	}

	public void setBegin_date(String beginDate) {
		begin_date = beginDate;
	}

	public float getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(float totalAmount) {
		total_amount = totalAmount;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String endDate) {
		end_date = endDate;
	}

	public Integer getTotal_count() {
		return total_count;
	}

	public void setTotal_count(Integer totalCount) {
		total_count = totalCount;
	}

	public List<ContactDetails> getContact_details() {
		return contact_details;
	}

	public void setContact_details(List<ContactDetails> contact_details) {
		this.contact_details = contact_details;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
}
