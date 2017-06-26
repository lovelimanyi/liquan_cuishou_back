package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class CountCollectionAssessment {
	private Long id;
	//
	private String personId;
	// 姓名
	private String personName;
	// 催单公司id
	private String companyId;
	// 催单公司名称
	private String companyTitle;
	// 催收员分组（3，4，5，6，7对应S1，S2，M1-M2，M2-M3，M3+对应1-10,11-30（1），1个月-2个月，2个月-3个月，3个月+）
	private String groupId;
	// 催收员分组组名
	private String groupName;
	// 订单分组
	private String orderGroupId;
	// 订单分组组名
	private String groupOrderName;
	// 本金
	private BigDecimal loanMoney;
	// 已还本金
	private BigDecimal repaymentMoney;
	// 未还本金
	private BigDecimal notYetRepaymentMoney;
	// 本金还款率
	private BigDecimal repaymentReta;
	// 迁徙率
	private BigDecimal migrateRate;
	// 滞纳金总额
	private BigDecimal penalty;
	// 已还滞纳金
	private BigDecimal repaymentPenalty;
	// 待还滞纳金
	private BigDecimal notRepaymentPenalty;
	// 滞纳金回款率
	private BigDecimal penaltyRepaymentReta;
	// 订单总数
	private Integer orderTotal;
	// 已处理订单数
	private Integer disposeOrderNum;
	//风控标记单量
	private Integer riskOrderNum;
	// 已还款订单数
	private Integer repaymentOrderNum;
	// 订单还款率
	private BigDecimal repaymentOrderRate;
	// 统计时间
	private Date countDate;
	//待催收订单
	private Integer undoneOrderNum;
	//已催收订单
	private Integer doneOrderNum;
	//催记率
	private BigDecimal orderRate;

	public Integer getUndoneOrderNum() {
		return undoneOrderNum;
	}

	public void setUndoneOrderNum(Integer undoneOrderNum) {
		this.undoneOrderNum = undoneOrderNum;
	}

	public Integer getDoneOrderNum() {
		return doneOrderNum;
	}

	public void setDoneOrderNum(Integer doneOrderNum) {
		this.doneOrderNum = doneOrderNum;
	}

	public BigDecimal getOrderRate() {
		return orderRate;
	}

	public void setOrderRate(BigDecimal orderRate) {
		this.orderRate = orderRate;
	}

	public String getOrderGroupId() {
		return orderGroupId;
	}

	public void setOrderGroupId(String orderGroupId) {
		this.orderGroupId = orderGroupId;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyTitle() {
		return companyTitle;
	}
	public void setCompanyTitle(String companyTitle) {
		this.companyTitle = companyTitle;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupOrderName() {
		return groupOrderName;
	}
	public void setGroupOrderName(String groupOrderName) {
		this.groupOrderName = groupOrderName;
	}
	public BigDecimal getLoanMoney() {
		return loanMoney == null ? new BigDecimal(0) : loanMoney;
	}
	public void setLoanMoney(BigDecimal loanMoney) {
		this.loanMoney = loanMoney;
	}
	public BigDecimal getRepaymentMoney() {
		return repaymentMoney ==null ? new BigDecimal(0) : repaymentMoney;
	}
	public void setRepaymentMoney(BigDecimal repaymentMoney) {
		this.repaymentMoney = repaymentMoney;
	}
	public BigDecimal getNotYetRepaymentMoney() {
		return notYetRepaymentMoney ==null ? new BigDecimal(0) : notYetRepaymentMoney;
	}
	public void setNotYetRepaymentMoney(BigDecimal notYetRepaymentMoney) {
		this.notYetRepaymentMoney = notYetRepaymentMoney;
	}
	public BigDecimal getMigrateRate() {
		return migrateRate;
	}
	public void setMigrateRate(BigDecimal migrateRate) {
		this.migrateRate = migrateRate;
	}
	public BigDecimal getPenalty() {
		return penalty ==null ? new BigDecimal(0) : penalty;
	}
	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}
	public BigDecimal getRepaymentPenalty() {
		return repaymentPenalty ==null ? new BigDecimal(0) : repaymentPenalty;
	}
	public void setRepaymentPenalty(BigDecimal repaymentPenalty) {
		this.repaymentPenalty = repaymentPenalty;
	}
	public BigDecimal getNotRepaymentPenalty() {
		return notRepaymentPenalty ==null ? new BigDecimal(0) : notRepaymentPenalty;
	}
	public void setNotRepaymentPenalty(BigDecimal notRepaymentPenalty) {
		this.notRepaymentPenalty = notRepaymentPenalty;
	}
	public BigDecimal getPenaltyRepaymentReta() {
		return penaltyRepaymentReta;
	}
	public void setPenaltyRepaymentReta(BigDecimal penaltyRepaymentReta) {
		this.penaltyRepaymentReta = penaltyRepaymentReta;
	}
	public Integer getOrderTotal() {
		return orderTotal ==null ? 0 : orderTotal;
	}
	public void setOrderTotal(Integer orderTotal) {
		this.orderTotal = orderTotal;
	}
	public Integer getDisposeOrderNum() {
		return disposeOrderNum ==null ? 0 : disposeOrderNum;
	}
	public void setDisposeOrderNum(Integer disposeOrderNum) {
		this.disposeOrderNum = disposeOrderNum;
	}
	public Integer getRepaymentOrderNum() {
		return repaymentOrderNum ==null ? 0 : repaymentOrderNum;
	}
	public void setRepaymentOrderNum(Integer repaymentOrderNum) {
		this.repaymentOrderNum = repaymentOrderNum;
	}
	public BigDecimal getRepaymentOrderRate() {
		return repaymentOrderRate;
	}
	public void setRepaymentOrderRate(BigDecimal repaymentOrderRate) {
		this.repaymentOrderRate = repaymentOrderRate;
	}
	public Date getCountDate() {
		return countDate;
	}
	public void setCountDate(Date countDate) {
		this.countDate = countDate;
	}
	public BigDecimal getRepaymentReta() {
		return repaymentReta;
	}
	public void setRepaymentReta(BigDecimal repaymentReta) {
		this.repaymentReta = repaymentReta;
	}
	public Integer getRiskOrderNum() {
		return riskOrderNum ==null ? 0 : riskOrderNum;
	}
	public void setRiskOrderNum(Integer riskOrderNum) {
		this.riskOrderNum = riskOrderNum;
	}

	
}
