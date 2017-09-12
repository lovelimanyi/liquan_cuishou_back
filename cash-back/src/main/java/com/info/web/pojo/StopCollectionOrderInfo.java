package com.info.web.pojo;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/7 0007.
 */
public class StopCollectionOrderInfo {
    private Long id;
    private String loanId;  // 借款编号
    private String userId;  // 借款人id
    private Date createTime; // 操作时间
    private String operatorId; // 操作人
    private String ipAddress; // ip地址

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
