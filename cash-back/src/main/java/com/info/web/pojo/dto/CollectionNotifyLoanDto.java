package com.info.web.pojo.dto;

public class CollectionNotifyLoanDto{
    private Long id;
    private Long userId; //用户id
    private Integer loanMoney; //借款本金
    private Integer loanRate; //借款利率
    private Integer paidMoney; //(本金+服务费)  由于现在无服务费，次字段赋值为 借款本金即可
    private Integer loanPenalty; //滞纳金
    private Integer serviceCharge; //服务费（暂时未用到，值为 0即可）
    private Integer loanPenaltyRate; //滞纳金利率
    private Integer termNumber; //期数
    private Long loanEndTime;
    private Long loanStartTime;
    private Integer lateDay; //逾期天数
    private Integer accrual; //利息
    private String merchantNo; //商户号
    private String borrowingType;//借款类型  2 大额，3分期商城

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public Integer getAccrual() {
        return accrual;
    }

    public void setAccrual(Integer accrual) {
        this.accrual = accrual;
    }

    public Integer getLateDay() {
        return lateDay;
    }

    public void setLateDay(Integer lateDay) {
        this.lateDay = lateDay;
    }

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

    public String getBorrowingType() {
        return borrowingType;
    }

    public void setBorrowingType(String borrowingType) {
        this.borrowingType = borrowingType;
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
                ", lateDay=" + lateDay +
                ", accrual=" + accrual +
                ", merchantNo='" + merchantNo + '\'' +
                ", borrowingType='" + borrowingType + '\'' +
                '}';
    }
}