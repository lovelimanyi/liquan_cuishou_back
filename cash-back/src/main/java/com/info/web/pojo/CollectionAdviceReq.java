package com.info.web.pojo;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/8/3 0003上午 11:34
 */

public class CollectionAdviceReq {
    private String loanId;
    private int orderType;
    private int termNumber;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getTermNumber() {
        return termNumber;
    }

    public void setTermNumber(int termNumber) {
        this.termNumber = termNumber;
    }
}
