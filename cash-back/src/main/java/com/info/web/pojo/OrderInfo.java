package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/6 0006.
 * 记录借款订单和借款人信息
 */

public class OrderInfo {
    private String loanId;
    private String loanUserName;
    private String loanUserId;
    private String orderId;
    private String loanUserPhone;
    private BigDecimal loanMoney;
    private String loanStartTime;
    private String loanEndTime;
    private int overDuedays;
    private BigDecimal loanPenalty;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getLoanUserName() {
        return loanUserName;
    }

    public void setLoanUserName(String loanUserName) {
        this.loanUserName = loanUserName;
    }

    public BigDecimal getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(BigDecimal loanMoney) {
        this.loanMoney = loanMoney;
    }

    public int getOverDuedays() {
        return overDuedays;
    }

    public void setOverDuedays(int overDuedays) {
        this.overDuedays = overDuedays;
    }

    public BigDecimal getLoanPenalty() {
        return loanPenalty;
    }

    public void setLoanPenalty(BigDecimal loanPenalty) {
        this.loanPenalty = loanPenalty;
    }

    public String getLoanUserPhone() {
        return loanUserPhone;
    }

    public void setLoanUserPhone(String loanUserPhone) {
        this.loanUserPhone = loanUserPhone;
    }

    public String getLoanStartTime() {
        return loanStartTime;
    }

    public void setLoanStartTime(String loanStartTime) {
        this.loanStartTime = loanStartTime;
    }

    public String getLoanEndTime() {
        return loanEndTime;
    }

    public void setLoanEndTime(String loanEndTime) {
        this.loanEndTime = loanEndTime;
    }

    public String getLoanUserId() {
        return loanUserId;
    }

    public void setLoanUserId(String loanUserId) {
        this.loanUserId = loanUserId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
