package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class CollectionStatistics {
	private String status;  //催收状态
	private BigDecimal loanMoney;   //本金
	private BigDecimal loanPenalty;  //滞纳金
    private BigDecimal notRepayment;  //未还本金
    private BigDecimal yetRepayment;  //已还本金
    private BigDecimal yesterdayMoney;  //昨日新增本金
    private BigDecimal loanMoneyRate;   //本金还款率
    private Date createDate;  //创建时间
    private String dateStr;  //创建时间str
	public BigDecimal getLoanMoney() {
		return loanMoney == null ? new BigDecimal(0) : loanMoney;
	}
	public void setLoanMoney(BigDecimal loanMoney) {
		this.loanMoney = loanMoney;
	}
	public BigDecimal getNotRepayment() {
		return notRepayment == null ? new BigDecimal(0) : notRepayment;
	}
	public void setNotRepayment(BigDecimal notRepayment) {
		this.notRepayment = notRepayment;
	}
	public BigDecimal getYesterdayMoney() {
		return yesterdayMoney == null ? new BigDecimal(0) : yesterdayMoney;
	}
	public void setYesterdayMoney(BigDecimal yesterdayMoney) {
		this.yesterdayMoney = yesterdayMoney;
	}
	public BigDecimal getLoanMoneyRate() {
		return loanMoneyRate == null ? new BigDecimal(0) : loanMoneyRate;
	}
	public void setLoanMoneyRate(BigDecimal loanMoneyRate) {
		this.loanMoneyRate = loanMoneyRate;
	}
	public BigDecimal getYetRepayment() {
		return yetRepayment == null ? new BigDecimal(0) : yetRepayment;
	}
	public void setYetRepayment(BigDecimal yetRepayment) {
		this.yetRepayment = yetRepayment;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public BigDecimal getLoanPenalty() {
		return loanPenalty == null ? new BigDecimal(0) : loanPenalty;
	}
	public void setLoanPenalty(BigDecimal loanPenalty) {
		this.loanPenalty = loanPenalty;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
   
}
