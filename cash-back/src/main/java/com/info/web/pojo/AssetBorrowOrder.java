package com.info.web.pojo;

public class AssetBorrowOrder {
    private Long loanId;
    private Long userId;
    private Integer merchantNumber;
    private Integer status;
    private String mobile;

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getMerchantNumber() {
        return merchantNumber;
    }

    public void setMerchantNumber(Integer merchantNumber) {
        this.merchantNumber = merchantNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
