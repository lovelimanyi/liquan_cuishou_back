package com.info.web.pojo;

import java.util.Date;

public class CollectionStatisticsOrder {
	private String status;  //催收状态
	private Integer loanNum;   //总订单数
	private Integer penalty;  //滞纳金数
    private Integer notRepayment;  //未还订单数
    private Integer yetRepayment;  //已还订单数
    private Integer yesterday;  //昨日新增订单数
    private double loanRate;   //还款率
    private Date createDate;  //创建时间
    private String dateStr;  //创建时间str
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getLoanNum() {
		return loanNum;
	}
	public void setLoanNum(Integer loanNum) {
		this.loanNum = loanNum;
	}
	public Integer getPenalty() {
		return penalty;
	}
	public void setPenalty(Integer penalty) {
		this.penalty = penalty;
	}
	public Integer getNotRepayment() {
		return notRepayment;
	}
	public void setNotRepayment(Integer notRepayment) {
		this.notRepayment = notRepayment;
	}
	public Integer getYetRepayment() {
		return yetRepayment;
	}
	public void setYetRepayment(Integer yetRepayment) {
		this.yetRepayment = yetRepayment;
	}
	public Integer getYesterday() {
		return yesterday;
	}
	public void setYesterday(Integer yesterday) {
		this.yesterday = yesterday;
	}
	public double getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(double loanRate) {
		this.loanRate = loanRate;
	}
   
}
