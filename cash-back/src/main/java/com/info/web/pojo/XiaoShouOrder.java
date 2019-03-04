package com.info.web.pojo;

import java.util.Date;

public class XiaoShouOrder {
    private Long id;
    private Long batchId;
    private String merchantNo;
    private String userId;
    private String userName;
    private String mobile;
    private Date registerTime;
    private Date dispatcherTime;
    private Date startDispatcherTime;
    private Date endDispatcherTime;
    private Integer loanOrderStatus;//0 无在借订单；1 有在借订单
    private Integer userIntention;//用户意向：1有意向；2无意向；3 未接通
    private String remark;
    private String currentCollectionUserId;
    private String currentCollectionUserName;
    private String companyId;
    private Date createTime;
    private Date updatetime;
    private String currentStatus;

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getDispatcherTime() {
        return dispatcherTime;
    }

    public void setDispatcherTime(Date dispatcherTime) {
        this.dispatcherTime = dispatcherTime;
    }

    public Date getStartDispatcherTime() {
        return startDispatcherTime;
    }

    public void setStartDispatcherTime(Date startDispatcherTime) {
        this.startDispatcherTime = startDispatcherTime;
    }

    public Date getEndDispatcherTime() {
        return endDispatcherTime;
    }

    public void setEndDispatcherTime(Date endDispatcherTime) {
        this.endDispatcherTime = endDispatcherTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getLoanOrderStatus() {
        return loanOrderStatus;
    }

    public void setLoanOrderStatus(Integer loanOrderStatus) {
        this.loanOrderStatus = loanOrderStatus;
    }

    public Integer getUserIntention() {
        return userIntention;
    }

    public void setUserIntention(Integer userIntention) {
        this.userIntention = userIntention;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCurrentCollectionUserId() {
        return currentCollectionUserId;
    }

    public void setCurrentCollectionUserId(String currentCollectionUserId) {
        this.currentCollectionUserId = currentCollectionUserId;
    }

    public String getCurrentCollectionUserName() {
        return currentCollectionUserName;
    }

    public void setCurrentCollectionUserName(String currentCollectionUserName) {
        this.currentCollectionUserName = currentCollectionUserName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }
}
