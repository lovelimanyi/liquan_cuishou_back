package com.info.web.pojo;

import java.util.Date;

public class InfoSms {
	
	private Integer id;
	private String userName;
	private String userPhone;
	private Date addTime;
	private String smscontent;
	private String loanOrderId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getSmscontent() {
		return smscontent;
	}
	public void setSmscontent(String smscontent) {
		this.smscontent = smscontent;
	}
	public String getLoanOrderId() {
		return loanOrderId;
	}
	public void setLoanOrderId(String loanOrderId) {
		this.loanOrderId = loanOrderId;
	}
	
	
}
