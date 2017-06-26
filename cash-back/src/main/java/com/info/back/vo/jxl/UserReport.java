package com.info.back.vo.jxl;

/**
 * 聚信立报告总VO
 * @author duj
 *
 * @version 2016-12-24
 */
public class UserReport {
	private String realName;
	private String gender;
	private String idNumber;
	private Integer age;
	private String note;
	private ReportData report_data;
	private String success;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public ReportData getReport_data() {
		return report_data;
	}

	public void setReport_data(ReportData reportData) {
		report_data = reportData;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
