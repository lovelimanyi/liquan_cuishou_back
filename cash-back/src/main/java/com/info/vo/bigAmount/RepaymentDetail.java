package com.info.vo.bigAmount;

/**
 * 类描述：还款详情参数
 * 创建人：yyf
 * 创建时间：2017/11/16 0016上午 11:04
 */

public class RepaymentDetail {
    private String createDate;
    private String id;
    private String payId;
    private String returnType;
    private String remark;

    private String realMoney;
    private String realPrinciple;
    private String realInterest;
    private String realPenlty;
    private String realgetAccrual;//实收利息
    private String remainAccrual;//剩余利息

    public String getRealgetAccrual() {
        return realgetAccrual;
    }

    public void setRealgetAccrual(String realgetAccrual) {
        this.realgetAccrual = realgetAccrual;
    }

    public String getRemainAccrual() {
        return remainAccrual;
    }

    public void setRemainAccrual(String remainAccrual) {
        this.remainAccrual = remainAccrual;
    }

    public String getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(String realMoney) {
        this.realMoney = realMoney;
    }

    public String getRealPrinciple() {
        return realPrinciple;
    }

    public void setRealPrinciple(String realPrinciple) {
        this.realPrinciple = realPrinciple;
    }

    public String getRealInterest() {
        return realInterest;
    }

    public void setRealInterest(String realInterest) {
        this.realInterest = realInterest;
    }

    public String getRealPenlty() {
        return realPenlty;
    }

    public void setRealPenlty(String realPenlty) {
        this.realPenlty = realPenlty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
