package com.info.web.pojo;

import java.util.Date;

/**
 * 状态流转表
 */
public class MmanLoanCollectionStatusChangeLog {
	//
	private String id;
	// 催收订单ID
	private String loanCollectionOrderId;
	// 操作前状态
	private String beforeStatus;
	// 操作后状态
	private String afterStatus;
	// 操作类型(操作类型 1入催 2派单 3逾期等级转换 4转单 5委外 6取消委外 7委外成功 100催收完成 8月初分组
	// 9回收(重置催收状态为待催收) 字典)
	private String type;
	// 创建时间
	private Date createDate;
	// 操作人
	private String operatorName;
	// 操作备注
	private String remark;
	// 催收公司ID
	private String companyId;
	// 当前催收员id
	private String currentCollectionUserId;
	//当前催收员等级
	private String currentCollectionUserLevel;
	//当前订单组的等级
	private String currentCollectionOrderLevel;

	private String companyTitle;
	


	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setLoanCollectionOrderId(String loanCollectionOrderId) {
		this.loanCollectionOrderId = loanCollectionOrderId;
	}

	public String getLoanCollectionOrderId() {
		return this.loanCollectionOrderId;
	}

	public void setBeforeStatus(String beforeStatus) {
		this.beforeStatus = beforeStatus;
	}

	public String getBeforeStatus() {
		return this.beforeStatus;
	}

	public void setAfterStatus(String afterStatus) {
		this.afterStatus = afterStatus;
	}

	public String getAfterStatus() {
		return this.afterStatus;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorName() {
		return this.operatorName;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyId() {
		return this.companyId;
	}


	public String getCurrentCollectionUserId() {
		return currentCollectionUserId;
	}

	public void setCurrentCollectionUserId(String currentCollectionUserId) {
		this.currentCollectionUserId = currentCollectionUserId;
	}

	public String getCurrentCollectionUserLevel() {
		return currentCollectionUserLevel;
	}

	public void setCurrentCollectionUserLevel(String currentCollectionUserLevel) {
		this.currentCollectionUserLevel = currentCollectionUserLevel;
	}

	public String getCurrentCollectionOrderLevel() {
		return currentCollectionOrderLevel;
	}

	public void setCurrentCollectionOrderLevel(String currentCollectionOrderLevel) {
		this.currentCollectionOrderLevel = currentCollectionOrderLevel;
	}


	public String getCompanyTitle() {
		return companyTitle;
	}

	public void setCompanyTitle(String companyTitle) {
		this.companyTitle = companyTitle;
	}
}