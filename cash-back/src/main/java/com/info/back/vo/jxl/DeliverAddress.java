package com.info.back.vo.jxl;

import java.util.List;

public class DeliverAddress {
	private String begin_date;
	private float total_amount;
	private String end_date;
	private Integer total_count;
	private List<Receiver> receiver;
	private String address;
	private float lat;
	private float lng;
	private String predict_addr_type;

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

	public List<Receiver> getReceiver() {
		return receiver;
	}

	public void setReceiver(List<Receiver> receiver) {
		this.receiver = receiver;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}

	public String getPredict_addr_type() {
		return predict_addr_type;
	}

	public void setPredict_addr_type(String predictAddrType) {
		predict_addr_type = predictAddrType;
	}

}
