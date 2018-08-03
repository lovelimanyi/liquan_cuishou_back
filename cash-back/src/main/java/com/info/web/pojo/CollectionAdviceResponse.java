package com.info.web.pojo;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/8/3 0003上午 11:35
 */

public class CollectionAdviceResponse {
    private String loanId;
    private String advice;
    private String createDate;
    private int termNumber;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getTermNumber() {
        return termNumber;
    }

    public void setTermNumber(int termNumber) {
        this.termNumber = termNumber;
    }
}
