package com.info.back.service;

import com.info.vo.bigAmount.Loan;
import com.info.vo.bigAmount.Repayment;
import com.info.vo.bigAmount.RepaymentDetail;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2017/11/17 0017下午 02:36
 */
public interface ISyncService {
    void handleOverdue(Repayment repayment, Loan loan);

    void handleRepay(Repayment repayment, Loan loan, RepaymentDetail repaymentDetail);
}
