package com.info.web.pojo.dto;

public class CollectionNotifyDto {

    private CollectionNotifyLoanDto loan;
    private CollectionNotifyRepaymentDto repayment;
    private CollectionNotifyRepaymentDetailDto repaymentDetail;
    private Long scheduleTaskOverdueId;


    public CollectionNotifyLoanDto getLoan() {
        return loan;
    }

    public void setLoan(CollectionNotifyLoanDto loan) {
        this.loan = loan;
    }

    public CollectionNotifyRepaymentDto getRepayment() {
        return repayment;
    }

    public void setRepayment(CollectionNotifyRepaymentDto repayment) {
        this.repayment = repayment;
    }

    public CollectionNotifyRepaymentDetailDto getRepaymentDetail() {
        return repaymentDetail;
    }

    public void setRepaymentDetail(CollectionNotifyRepaymentDetailDto repaymentDetail) {
        this.repaymentDetail = repaymentDetail;
    }

    public Long getScheduleTaskOverdueId() {
        return scheduleTaskOverdueId;
    }

    public void setScheduleTaskOverdueId(Long scheduleTaskOverdueId) {
        this.scheduleTaskOverdueId = scheduleTaskOverdueId;
    }
}

