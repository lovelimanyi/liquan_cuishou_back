package com.info.web.pojo.dto;

public class CollectionNotifyRepaymentDetailDto{

    private Long id;
    private Long createDate;
    private String returnType;
    private String remark;
    private Long payId;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    @Override
    public String toString() {
        return "CollectionNotifyRepaymentDetailDto{" + "id=" + id + ", createDate=" + createDate + ", returnType='" + returnType + '\'' + ", remark='" + remark + '\'' + ", payId=" + payId + '}';
    }
}