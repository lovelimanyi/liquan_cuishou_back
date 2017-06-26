package com.info.back.vo.jxl;

import java.util.List;

public class CellBehavior {
	private String phone_num;
	private List<Behavior> behavior;

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phoneNum) {
		phone_num = phoneNum;
	}

	public List<Behavior> getBehavior() {
		return behavior;
	}

	public void setBehavior(List<Behavior> behavior) {
		this.behavior = behavior;
	}

}
