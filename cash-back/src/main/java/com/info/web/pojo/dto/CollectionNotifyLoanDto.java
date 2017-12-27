package com.info.web.pojo.dto;

public class CollectionNotifyLoanDto{
    private Long id;
    private Long userId;
    private Integer loanMoney;
    private Integer loanRate;
    private Integer paidMoney;
    private Integer loanPenalty;
    private Integer serviceCharge;
    private Integer loanPenaltyRate;
    private Integer termNumber;
    private Long loanEndTime;
    private Long loanStartTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(Integer loanMoney) {
        this.loanMoney = loanMoney;
    }

    public Integer getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(Integer loanRate) {
        this.loanRate = loanRate;
    }

    public Integer getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(Integer paidMoney) {
        this.paidMoney = paidMoney;
    }

    public Integer getLoanPenalty() {
        return loanPenalty;
    }

    public void setLoanPenalty(Integer loanPenalty) {
        this.loanPenalty = loanPenalty;
    }

    public Integer getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Integer serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Integer getLoanPenaltyRate() {
        return loanPenaltyRate;
    }

    public void setLoanPenaltyRate(Integer loanPenaltyRate) {
        this.loanPenaltyRate = loanPenaltyRate;
    }

    public Long getLoanEndTime() {
        return loanEndTime;
    }

    public void setLoanEndTime(Long loanEndTime) {
        this.loanEndTime = loanEndTime;
    }

    public Long getLoanStartTime() {
        return loanStartTime;
    }

    public void setLoanStartTime(Long loanStartTime) {
        this.loanStartTime = loanStartTime;
    }

    public Integer getTermNumber() {
        return termNumber;
    }

    public void setTermNumber(Integer termNumber) {
        this.termNumber = termNumber;
    }

    @Override
    public String toString() {
        return "CollectionNotifyLoanDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", loanMoney=" + loanMoney +
                ", loanRate=" + loanRate +
                ", paidMoney=" + paidMoney +
                ", loanPenalty=" + loanPenalty +
                ", serviceCharge=" + serviceCharge +
                ", loanPenaltyRate=" + loanPenaltyRate +
                ", termNumber=" + termNumber +
                ", loanEndTime=" + loanEndTime +
                ", loanStartTime=" + loanStartTime +
                '}';
    }
}