package com.info.web.pojo;

import java.util.Date;

public class SmsLog {
	private Integer id;
	private String smsLog;
	private Date addTime;
	private String smsWay;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSmsLog() {
		return smsLog;
	}

	public void setSmsLog(String smsLog) {
		this.smsLog = smsLog;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getSmsWay() {
		return smsWay;
	}

	public void setSmsWay(String smsWay) {
		this.smsWay = smsWay;
	}

	public SmsLog(String smsLog, String smsWay) {
		super();
		this.smsLog = smsLog;
		this.smsWay = smsWay;
	}

	public SmsLog() {
		super();
	}

}
