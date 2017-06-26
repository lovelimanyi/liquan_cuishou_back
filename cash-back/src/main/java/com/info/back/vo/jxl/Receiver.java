package com.info.back.vo.jxl;

import java.util.List;

public class Receiver {

	private Integer count;
	private float amount;
	private String name;
	private List<String> phone_num_list;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getPhone_num_list() {
		return phone_num_list;
	}

	public void setPhone_num_list(List<String> phoneNumList) {
		phone_num_list = phoneNumList;
	}

}
