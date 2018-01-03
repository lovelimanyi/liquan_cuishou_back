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
    private String realMoney;
    private String receivableDate;
    private String receiveMoney;
    private String loanPenalty;

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
