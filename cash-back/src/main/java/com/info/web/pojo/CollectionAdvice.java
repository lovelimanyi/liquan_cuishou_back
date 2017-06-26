package com.info.web.pojo;

import java.util.Date;

public class CollectionAdvice {
	//催收建议id
	private String id;
	//订单id
	private String orderId;
	//借款id
	private String loanId;
	//还款id
	private String payId;
	//借款人id
	private String userId;
	//后台操作人id
	private Integer backUserId; 
	//借款人姓名
	private String loanUserName;
	//借款人电话
	private String loanUserPhone;
	//风控标签id
	private String fengkongIds;
	//风控标签名称
	private String fkLabels;
	//后台操作人姓名
	private String userName;
	//创建时间
	private Date createDate;
	//催收建议status   1通过 2拒绝 3无建议
	private String status;
	// 催收记录id
	private String collectionRecordId;

	public String getCollectionRecordId() {
		return collectionRecordId;
	}

	public void setCollectionRecordId(String collectionRecordId) {
		this.collectionRecordId = collectionRecordId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getBackUserId() {
		return backUserId;
	}

	public void setBackUserId(Integer backUserId) {
		this.backUserId = backUserId;
	}

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

	public String getFengkongIds() {
		return fengkongIds;
	}

	public void setFengkongIds(String fengkongIds) {
		this.fengkongIds = fengkongIds;
	}

	public String getFkLabels() {
		return fkLabels;
	}

	public void setFkLabels(String fkLabels) {
		this.fkLabels = fkLabels;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
