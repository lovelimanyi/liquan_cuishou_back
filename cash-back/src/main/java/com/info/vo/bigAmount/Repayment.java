package com.info.vo.bigAmount;

/**
 * 类描述：还款表参数
 * 创建人：yyf
 * 创建时间：2017/11/16 0016上午 11:04
 */

public class Repayment {
    private String createDate;
    private String id;
    private String loanId;

    private String receivableDate;
    private String receiveMoney;//应还金额
    private String realMoney; //实收金额
    private String loanPenalty;//滞纳金

    private String realgetPrinciple;//实收本金
    private String receivablePrinciple;//剩余应还本金
    private String realgetInterest;//实收滞纳金
    private String receivableInterest;//剩余应还滞纳金
    private String realgetServiceCharge;//实收服务费
    private String remainServiceCharge;//剩余应还服务费
    private String realgetAccrual;//实收利息
    private String remainAccrual;//剩余利息


    public String getRealgetAccrual() {
        return realgetAccrual;
    }

    public void setRealgetAccrual(String realgetAccrual) {
        this.realgetAccrual = realgetAccrual;
    }

    public String getRemainAccrual() {
        return remainAccrual;
    }

    public void setRemainAccrual(String remainAccrual) {
        this.remainAccrual = remainAccrual;
    }

    public String getRealgetPrinciple() {
        return realgetPrinciple;
    }

    public void setRealgetPrinciple(String realgetPrinciple) {
        this.realgetPrinciple = realgetPrinciple;
    }

    public String getReceivablePrinciple() {
        return receivablePrinciple;
    }

    public void setReceivablePrinciple(String receivablePrinciple) {
        this.receivablePrinciple = receivablePrinciple;
    }

    public String getRealgetInterest() {
        return realgetInterest;
    }

    public void setRealgetInterest(String realgetInterest) {
        this.realgetInterest = realgetInterest;
    }

    public String getReceivableInterest() {
        return receivableInterest;
    }

    public void setReceivableInterest(String receivableInterest) {
        this.receivableInterest = receivableInterest;
    }

    public String getRealgetServiceCharge() {
        return realgetServiceCharge;
    }

    public void setRealgetServiceCharge(String realgetServiceCharge) {
        this.realgetServiceCharge = realgetServiceCharge;
    }

    public String getRemainServiceCharge() {
        return remainServiceCharge;
    }

    public void setRemainServiceCharge(String remainServiceCharge) {
        this.remainServiceCharge = remainServiceCharge;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(String realMoney) {
        this.realMoney = realMoney;
    }

    public String getReceivableDate() {
        return receivableDate;
    }

    public void setReceivableDate(String receivableDate) {
        this.receivableDate = receivableDate;
    }

    public String getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(String receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public String getLoanPenalty() {
        return loanPenalty;
    }

    public void setLoanPenalty(String loanPenalty) {
        this.loanPenalty = loanPenalty;
    }
}
