package com.info.vo.bigAmount;

import java.util.List;

/**
 * 类描述：大额分期参数
 * 创建人：yyf
 * 创建时间：2017/11/16 0016上午 10:36
 */

public class BigAmountRequestParams {
    private Loan loan;
    private Repayment repayment;
    protected List<RepaymentDetail> repaymentDetailList;

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Repayment getRepayment() {
        return repayment;
    }

    public void setRepayment(Repayment repayment) {
        this.repayment = repayment;
    }

    public List<RepaymentDetail> getRepaymentDetailList() {
        return repaymentDetailList;
    }

    public void setRepaymentDetailList(List<RepaymentDetail> repaymentDetailList) {
        this.repaymentDetailList = repaymentDetailList;
    }
}
