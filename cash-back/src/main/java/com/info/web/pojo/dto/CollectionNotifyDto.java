package com.info.web.pojo.dto;

import java.util.List;

public class CollectionNotifyDto {

    private CollectionNotifyLoanDto loan;
    private CollectionNotifyRepaymentDto repayment;
    private List<CollectionNotifyRepaymentDetailDto> repaymentDetailList;
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

    public List<CollectionNotifyRepaymentDetailDto> getRepaymentDetailList() {
        return repaymentDetailList;
    }

    public void setRepaymentDetailList(List<CollectionNotifyRepaymentDetailDto> repaymentDetailList) {
        this.repaymentDetailList = repaymentDetailList;
    }

    public Long getScheduleTaskOverdueId() {
        return scheduleTaskOverdueId;
    }

    public void setScheduleTaskOverdueId(Long scheduleTaskOverdueId) {
        this.scheduleTaskOverdueId = scheduleTaskOverdueId;
    }

    @Override
    public String toString() {
        return "CollectionNotifyDto{" +
                "loan=" + loan +
                ", repayment=" + repayment +
                ", repaymentDetailList=" + repaymentDetailList +
                ", scheduleTaskOverdueId=" + scheduleTaskOverdueId +
                '}';
    }
}

