package com.info.web.pojo.dto;

public class CollectionNotifyRepaymentDto{

    private Long id;
    private Long loanId;
    private Long createDate;
    private Long receivableStartdate;
    private Long receivableDate;
    private Integer receiveMoney;
    private Integer realMoney;
    private Integer loanPenalty;
    private String status;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getReceivableStartdate() {
        return receivableStartdate;
    }

    public void setReceivableStartdate(Long receivableStartdate) {
        this.receivableStartdate = receivableStartdate;
    }

    public Long getReceivableDate() {
        return receivableDate;
    }

    public void setReceivableDate(Long receivableDate) {
        this.receivableDate = receivableDate;
    }

    public Integer getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(Integer receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public Integer getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Integer realMoney) {
        this.realMoney = realMoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Integer getLoanPenalty() {
        return loanPenalty;
    }

    public void setLoanPenalty(Integer loanPenalty) {
        this.loanPenalty = loanPenalty;
    }

    @Override
    public String toString() {
        return "CollectionNotifyRepaymentDto{" +
                "id=" + id +
                ", loanId=" + loanId +
                ", createDate=" + createDate +
                ", receivableStartdate=" + receivableStartdate +
                ", receivableDate=" + receivableDate +
                ", receiveMoney=" + receiveMoney +
                ", realMoney=" + realMoney +
                ", loanPenalty=" + loanPenalty +
                ", status='" + status + '\'' +
                '}';
    }
}