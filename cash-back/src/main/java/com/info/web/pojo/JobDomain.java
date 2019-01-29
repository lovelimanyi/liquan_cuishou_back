package com.info.web.pojo;

public class JobDomain {
    private String loanUsername;
    private String loanUserGender;
    private String loanChannel;
    private String loanOverdueDay;
    private String loanRepayAmount;
    private String phone;
    private String jobId;

    public String getLoanUsername() {
        return loanUsername;
    }

    public void setLoanUsername(String loanUsername) {
        this.loanUsername = loanUsername;
    }

    public String getLoanUserGender() {
        return loanUserGender;
    }

    public void setLoanUserGender(String loanUserGender) {
        this.loanUserGender = loanUserGender;
    }

    public String getLoanChannel() {
        return loanChannel;
    }

    public void setLoanChannel(String loanChannel) {
        this.loanChannel = loanChannel;
    }

    public String getLoanOverdueDay() {
        return loanOverdueDay;
    }

    public void setLoanOverdueDay(String loanOverdueDay) {
        this.loanOverdueDay = loanOverdueDay;
    }

    public String getLoanRepayAmount() {
        return loanRepayAmount;
    }

    public void setLoanRepayAmount(String loanRepayAmount) {
        this.loanRepayAmount = loanRepayAmount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
}
