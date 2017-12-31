package com.info.web.pojo;

import java.util.Date;

public class CollectionWithholdingRecord {
    private String id;
    //借款用户id
    private String loanUserId;
    //借款人姓名
    private String loanUserName;
    //借款人手机号码
    private String loanUserPhone;
    //当前订单状态
    private String orderStatus;
    //欠款金额
    private String arrearsMoney;
    //当前已还金额
    private String hasalsoMoney;
    //扣款金额
    private String deductionsMoney;
    //状态 0 初始值  1 成功 2 失败
    private Integer status;
    //操作用户ID
    private String operationUserId;
    //创建时间
    private Date createDate;
    //修改时间
    private Date updateDate;
    //订单编号
    private String orderId;
    //备注
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoanUserId() {
        return loanUserId;
    }

    public void setLoanUserId(String loanUserId) {
        this.loanUserId = loanUserId;
    }

    public String getLoanUserName() {
        return loanUserName;
    }

    public void setLoanUserName(String loanUserName) {
        this.loanUserName = loanUserName;
    }

    public String getLoanUserPhone() {
        return loanUserPhone;
    }

    public void setLoanUserPhone(String loanUserPhone) {
        this.loanUserPhone = loanUserPhone;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getArrearsMoney() {
        return arrearsMoney;
    }

    public void setArrearsMoney(String arrearsMoney) {
        this.arrearsMoney = arrearsMoney;
    }

    public String getHasalsoMoney() {
        return hasalsoMoney;
    }

    public void setHasalsoMoney(String hasalsoMoney) {
        this.hasalsoMoney = hasalsoMoney;
    }

    public String getDeductionsMoney() {
        return deductionsMoney;
    }

    public void setDeductionsMoney(String deductionsMoney) {
        this.deductionsMoney = deductionsMoney;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOperationUserId() {
        return operationUserId;
    }

    public void setOperationUserId(String operationUserId) {
        this.operationUserId = operationUserId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
