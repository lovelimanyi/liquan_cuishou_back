package com.info.vo.bigAmount;

/**
 * 类描述：借款表参数
 * 创建人：yyf
 * 创建时间：2017/11/16 0016上午 11:04
 */

public class Loan {
    private String id ;
    private String loanEndTime;
    private String loanMoney;
    private String loanPenalty;
    private String loanPenaltyRate;
    private String loanRate;
    private String loanStartTime;
    private String paidMoney;
    private String accrual;
    private String serviceCharge;
    private String userId;
    private String termNumber;
    private Integer overdueDays;

    public String getAccrual() {
        return accrual;
    }

    public void setAccrual(String accrual) {
        this.accrual = accrual;
    }

    public Integer getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(Integer overdueDays) {
        this.overdueDays = overdueDays;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoanEndTime() {
        return loanEndTime;
    }

    public void setLoanEndTime(String loanEndTime) {
        this.loanEndTime = loanEndTime;
    }

    public String getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(String loanMoney) {
        this.loanMoney = loanMoney;
    }

    public String getLoanPenalty() {
        return loanPenalty;
    }

    public void setLoanPenalty(String loanPenalty) {
        this.loanPenalty = loanPenalty;
    }

    public String getLoanPenaltyRate() {
        return loanPenaltyRate;
    }

    public void setLoanPenaltyRate(String loanPenaltyRate) {
        this.loanPenaltyRate = loanPenaltyRate;
    }

    public String getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(String loanRate) {
        this.loanRate = loanRate;
    }

    public String getLoanStartTime() {
        return loanStartTime;
    }

    public void setLoanStartTime(String loanStartTime) {
        this.loanStartTime = loanStartTime;
    }

    public String getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(String paidMoney) {
        this.paidMoney = paidMoney;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTermNumber() {
        return termNumber;
    }

    public void setTermNumber(String termNumber) {
        this.termNumber = termNumber;
    }
}
