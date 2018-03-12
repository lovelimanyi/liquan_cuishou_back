package com.info.web.pojo.dto;

public class CollectionNotifyRepaymentDetailDto{

    private Long id;
    private Long createDate;
    private String returnType;
    private String remark; //备注
    private Long payId; // 还款id
    private Integer realMoney; //本次还款实收本金
    private Integer realPrinciple; // 剩余应还本金
    private Integer realPenlty; //本次还款实收滞纳金
    private Integer realInterest;// 剩余应还滞纳金
    private Integer realgetAccrual;//本次还款实收利息
    private Integer remainAccrual;//剩余利息

    public Integer getRealgetAccrual() {
        return realgetAccrual;
    }

    public void setRealgetAccrual(Integer realgetAccrual) {
        this.realgetAccrual = realgetAccrual;
    }

    public Integer getRemainAccrual() {
        return remainAccrual;
    }

    public void setRemainAccrual(Integer remainAccrual) {
        this.remainAccrual = remainAccrual;
    }

    public Integer getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Integer realMoney) {
        this.realMoney = realMoney;
    }

    public Integer getRealPrinciple() {
        return realPrinciple;
    }

    public void setRealPrinciple(Integer realPrinciple) {
        this.realPrinciple = realPrinciple;
    }

    public Integer getRealInterest() {
        return realInterest;
    }

    public void setRealInterest(Integer realInterest) {
        this.realInterest = realInterest;
    }

    public Integer getRealPenlty() {
        return realPenlty;
    }

    public void setRealPenlty(Integer realPenlty) {
        this.realPenlty = realPenlty;
    }

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