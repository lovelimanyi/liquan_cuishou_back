package com.info.web.pojo.dto;

import java.math.BigDecimal;

public class CollectionNotifyRepaymentDto{

    private Long id;
    private Long loanId;
    private Long createDate;
    private Long receivableStartdate;
    private Long receivableDate;
    private Integer receiveMoney; //应还总额
    private Integer realMoney; //总实收金额（总实收本金+总实收利息+总实收滞纳金）
    private Integer loanPenalty; //滞纳金
    private String status;

    private Integer realgetPrinciple;//总实收本金
    private Integer receivablePrinciple;//剩余应还本金
    private Integer realgetInterest;//总实收滞纳金
    private Integer receivableInterest;//剩余应还滞纳金
    private Integer realgetServiceCharge;//实收服务费
    private Integer remainServiceCharge;//剩余应还服务费
    private Integer realgetAccrual;//总实收利息
    private Integer remainAccrual;//剩余利息

    public Integer getRealgetAccrual() {
        return realgetAccrual;
    }

    public void setRealgetAccrual(Integer realgetAccrual) {
        this.realgetAccrual = realgetAccrual;
    }

    public Integer getRemainAccrual() {
        return remainAccrual;
    }

    public void setRemainAccrual(Integer remainAccrual) {
        this.remainAccrual = remainAccrual;
    }

    public Integer getRealgetPrinciple() {
        return realgetPrinciple;
    }

    public void setRealgetPrinciple(Integer realgetPrinciple) {
        this.realgetPrinciple = realgetPrinciple;
    }

    public Integer getReceivablePrinciple() {
        return receivablePrinciple;
    }

    public void setReceivablePrinciple(Integer receivablePrinciple) {
        this.receivablePrinciple = receivablePrinciple;
    }

    public Integer getRealgetInterest() {
        return realgetInterest;
    }

    public void setRealgetInterest(Integer realgetInterest) {
        this.realgetInterest = realgetInterest;
    }

    public Integer getReceivableInterest() {
        return receivableInterest;
    }

    public void setReceivableInterest(Integer receivableInterest) {
        this.receivableInterest = receivableInterest;
    }

    public Integer getRealgetServiceCharge() {
        return realgetServiceCharge;
    }

    public void setRealgetServiceCharge(Integer realgetServiceCharge) {
        this.realgetServiceCharge = realgetServiceCharge;
    }

    public Integer getRemainServiceCharge() {
        return remainServiceCharge;
    }

    public void setRemainServiceCharge(Integer remainServiceCharge) {
        this.remainServiceCharge = remainServiceCharge;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getReceivableStartdate() {
        return receivableStartdate;
    }

    public void setReceivableStartdate(Long receivableStartdate) {
        this.receivableStartdate = receivableStartdate;
    }

    public Long getReceivableDate() {
        return receivableDate;
    }

    public void setReceivableDate(Long receivableDate) {
        this.receivableDate = receivableDate;
    }

    public Integer getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(Integer receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public Integer getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Integer realMoney) {
        this.realMoney = realMoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Integer getLoanPenalty() {
        return loanPenalty;
    }

    public void setLoanPenalty(Integer loanPenalty) {
        this.loanPenalty = loanPenalty;
    }

    @Override
    public String toString() {
        return "CollectionNotifyRepaymentDto{" +
                "id=" + id +
                ", loanId=" + loanId +
                ", createDate=" + createDate +
                ", receivableStartdate=" + receivableStartdate +
                ", receivableDate=" + receivableDate +
                ", receiveMoney=" + receiveMoney +
                ", realMoney=" + realMoney +
                ", loanPenalty=" + loanPenalty +
                ", status='" + status + '\'' +
                '}';
    }
}