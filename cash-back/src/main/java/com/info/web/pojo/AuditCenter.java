package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
  审核表
*/
public class AuditCenter {
	//序列号
    private String id;
    //操作用户编号
    private String operationUserId;
    //借款用户编号
    private String loanUserId;
    //借款编号
    private String loanId;
    //还款id
    private String payId;
    //催收订单编号
    private String orderid;
    //减免滞纳金
    private BigDecimal reductionMoney;
    //0:聚信立，1 催收建议 3订单减免
    private String type;
    //状态 0 申请中，2审核通过 3，拒绝 ,4失效 5,通过不计入考核
    private String status;
    //审核时间
    private Date audittime;
	//申请时间
    private Date createtime;
	//修改时间
    private Date updatetime;
    //标签
    private String note;
    //逾期标签
    private String labelValue;
    //借款表 滞纳金
    private BigDecimal loanPenalty;

	public BigDecimal getReductionMoney() {
		return reductionMoney;
	}

	public void setReductionMoney(BigDecimal reductionMoney) {
		this.reductionMoney = reductionMoney;
	}

	//借款人姓名
    private String loanUserName;
    //借款人电话
    private String loanUserPhone;
    //备注
    private String remark;
	//订单状态(mman_loan_collection_order 的status状态，用于减免时状态同步)
	private String orderStatus;
	//申请人与表无关
	private String userName;
	//公司名称
	private String companyName;
	// 催收状态
	private String collectionStatus;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperationUserId() {
		return operationUserId;
	}

	public void setOperationUserId(String operationUserId) {
		this.operationUserId = operationUserId;
	}

	public String getLoanUserId() {
		return loanUserId;
	}

	public void setLoanUserId(String loanUserId) {
		this.loanUserId = loanUserId;
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

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getAudittime() {
		return audittime;
	}

	public void setAudittime(Date audittime) {
		this.audittime = audittime;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLabelValue() {
		return labelValue;
	}

	public void setLabelValue(String labelValue) {
		this.labelValue = labelValue;
	}

	public BigDecimal getLoanPenalty() {
		return loanPenalty;
	}

	public void setLoanPenalty(BigDecimal loanPenalty) {
		this.loanPenalty = loanPenalty;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}


	public String getCollectionStatus() {
		return collectionStatus;
	}

	public void setCollectionStatus(String collectionStatus) {
		this.collectionStatus = collectionStatus;
	}


}