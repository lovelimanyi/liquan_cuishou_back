package com.info.web.pojo;

import java.util.Date;

/**
 * @author Administrator
 * @Description: 记录操作记录
 * @CreateTime 2017-10-27 下午 5:04
 **/
public class OperationRecord {
    private Long id;

    private String operateUserAccount; // 操作人账号

    private Date operateTime;  // 操作时间

    private String loanId;  // 借款id

    private String loanUserName;  // 借款人姓名

    private String loanUserPhone;  // 借款人电话

    private String beginCollectionTime;  // 催收时间(开始)

    private String endCollectionTime;  // 催收时间(结束)

    private String beginDispatchTime;  // 派单时间(开始)

    private String endDispatchTime;  // 派单时间(结束)

    private Integer beginOverDuedays; // 逾期天数(开始)

    private Integer endOverDuedays; // 逾期天数(结束)

    private String followUpGrad;  // 跟进等级

    private String collectionCompanyId;  // 催收公司id

    private String collectionGroup;  // 催收组

    private String collectionStatus;  // 催收状态

    private String currentCollectionUserName;  // 当前催收员姓名

    private Integer source;  // 记录来源

    private String extraFileOne;

    private String extraFileTwo;

    private String extraFileThree;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperateUserAccount() {
        return operateUserAccount;
    }

    public void setOperateUserAccount(String operateUserAccount) {
        this.operateUserAccount = operateUserAccount;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
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

    public Integer getBeginOverDuedays() {
        return beginOverDuedays;
    }

    public void setBeginOverDuedays(Integer beginOverDuedays) {
        this.beginOverDuedays = beginOverDuedays;
    }

    public Integer getEndOverDuedays() {
        return endOverDuedays;
    }

    public void setEndOverDuedays(Integer endOverDuedays) {
        this.endOverDuedays = endOverDuedays;
    }

    public String getFollowUpGrad() {
        return followUpGrad;
    }

    public void setFollowUpGrad(String followUpGrad) {
        this.followUpGrad = followUpGrad;
    }

    public String getCollectionCompanyId() {
        return collectionCompanyId;
    }

    public void setCollectionCompanyId(String collectionCompanyId) {
        this.collectionCompanyId = collectionCompanyId;
    }

    public String getCollectionGroup() {
        return collectionGroup;
    }

    public void setCollectionGroup(String collectionGroup) {
        this.collectionGroup = collectionGroup;
    }

    public String getCollectionStatus() {
        return collectionStatus;
    }

    public void setCollectionStatus(String collectionStatus) {
        this.collectionStatus = collectionStatus;
    }

    public String getCurrentCollectionUserName() {
        return currentCollectionUserName;
    }

    public void setCurrentCollectionUserName(String currentCollectionUserName) {
        this.currentCollectionUserName = currentCollectionUserName;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getExtraFileOne() {
        return extraFileOne;
    }

    public void setExtraFileOne(String extraFileOne) {
        this.extraFileOne = extraFileOne;
    }

    public String getExtraFileTwo() {
        return extraFileTwo;
    }

    public void setExtraFileTwo(String extraFileTwo) {
        this.extraFileTwo = extraFileTwo;
    }

    public String getExtraFileThree() {
        return extraFileThree;
    }

    public void setExtraFileThree(String extraFileThree) {
        this.extraFileThree = extraFileThree;
    }

    public String getBeginCollectionTime() {
        return beginCollectionTime;
    }

    public void setBeginCollectionTime(String beginCollectionTime) {
        this.beginCollectionTime = beginCollectionTime;
    }

    public String getEndCollectionTime() {
        return endCollectionTime;
    }

    public void setEndCollectionTime(String endCollectionTime) {
        this.endCollectionTime = endCollectionTime;
    }

    public String getBeginDispatchTime() {
        return beginDispatchTime;
    }

    public void setBeginDispatchTime(String beginDispatchTime) {
        this.beginDispatchTime = beginDispatchTime;
    }

    public String getEndDispatchTime() {
        return endDispatchTime;
    }

    public void setEndDispatchTime(String endDispatchTime) {
        this.endDispatchTime = endDispatchTime;
    }
}
