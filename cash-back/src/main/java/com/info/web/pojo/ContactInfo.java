package com.info.web.pojo;

public class ContactInfo {
	
	// 借款人姓名
	private String loanUserName;
	// 借款人电话
	private String loanUserPhone;
	// 联系人姓名
	private String contactUserName;
	// 联系人电话
	private String contactUserPhone;
	// 联系人类型
	private String contactUserType;
	
	public String getLoanUserName() {
		return loanUserName;
	}
	public void setLoanUserName(String loanUserName) {
		this.loanUserName = loanUserName;
	}
	public String getLoanUserPhone() {
		return loanUserPhone;
	}
	public void setLoanUserPhone(String loanUserPhone) {
		this.loanUserPhone = loanUserPhone;
	}
	public String getContactUserName() {
		return contactUserName;
	}
	public void setContactUserName(String contactUserName) {
		this.contactUserName = contactUserName;
	}
	public String getContactUserPhone() {
		return contactUserPhone;
	}
	public void setContactUserPhone(String contactUserPhone) {
		this.contactUserPhone = contactUserPhone;
	}
	public String getContactUserType() {
		return contactUserType;
	}
	public void setContactUserType(String contactUserType) {
		this.contactUserType = contactUserType;
	}

	
}
