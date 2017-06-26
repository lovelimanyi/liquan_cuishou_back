package com.info.web.pojo;

import java.util.Date;

/**
 * Created by Administrator on 2017/5/18 0018.
 * <p>
 * 问题反馈
 */
public class ProblemFeedback {
    private long id;
    private Date createDate;  // 反馈时间
    private String type;   //  问题类型
    private String collectionCompanyId;  // 催收公司id
    private String operatorId;   // 操作人id
    private String loanId;   //借款id
    private String loanUserPhone;   // 借款人电话
    private String details;    // 反馈内容
    private String createUserRoleId;   // 创建人角色Id
    private String createUserId;   // 创建人Id
    private String status;   //  状态

    private String companyTitle;  // 公司名称
    private String operator;    //操作人
    private String createUsername;  // 创建人姓名

    public String getType() {
        return type;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCompanyTitle() {
        return companyTitle;
    }

    public void setCompanyTitle(String companyTitle) {
        this.companyTitle = companyTitle;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCollectionCompanyId() {
        return collectionCompanyId;
    }

    public void setCollectionCompanyId(String collectionCompanyId) {
        this.collectionCompanyId = collectionCompanyId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getLoanUserPhone() {
        return loanUserPhone;
    }

    public void setLoanUserPhone(String loanUserPhone) {
        this.loanUserPhone = loanUserPhone;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCreateUserRoleId() {
        return createUserRoleId;
    }

    public void setCreateUserRoleId(String createUserRoleId) {
        this.createUserRoleId = createUserRoleId;
    }

}
