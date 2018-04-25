package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class OrderBaseResult {
    private String id;
    // 借款编号
    private String loanId;
    // 催收公司
    private String companyTile;
    // 催收组
    private String collectionGroup;
    // 借款人姓名
    private String realName;
    // 身份证号码
    private String idCard;
    // 借款人手机号
    private String phoneNumber;
    // 借款金额
    private BigDecimal loanMoney;
    // 逾期天数
    private Integer overdueDays;
    // 滞纳金
    private BigDecimal loanPenlty;
    // 催收状态
    private String collectionStatus;
    // 应还时间
    private Date loanEndTime;
    // 还款状态
    private Integer returnStatus;
    // 已还金额
    private BigDecimal returnMoney;
    // 最新还款时间
    private Date currentReturnDay;
    // 最后催收时间
    private Date lastCollectionTime;
    // 承诺还款时间
    private Date promiseRepaymentTime;
    // 派单时间
    private Date dispatchTime;
    //派单人
    private String dispatchName;
    // 当前催收员
    private String currUserName;
    // 上一催收员
    private String lastUserName;
    //跟进准太
    private String topImportant;
    // 短信次数 add by 170311
    private Integer msgCount;
    //减免滞纳金金额
    private BigDecimal reductionMoney;
    // 新老用户
    private Integer customerType;

    private BigDecimal accrual;

    private BigDecimal paidMoney;

    private BigDecimal serviceCharge;

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public BigDecimal getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(BigDecimal paidMoney) {
        this.paidMoney = paidMoney;
    }

    public BigDecimal getReductionMoney() {
        return reductionMoney;
    }

    public void setReductionMoney(BigDecimal reductionMoney) {
        this.reductionMoney = reductionMoney;
    }

    public Integer getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(Integer msgCount) {
        this.msgCount = msgCount;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getCompanyTile() {
        return companyTile;
    }

    public void setCompanyTile(String companyTile) {
        this.companyTile = companyTile;
    }

    public String getCollectionGroup() {
        return collectionGroup;
    }

    public void setCollectionGroup(String collectionGroup) {
        this.collectionGroup = collectionGroup;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(BigDecimal loanMoney) {
        this.loanMoney = loanMoney;
    }

    public Integer getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(Integer overdueDays) {
        this.overdueDays = overdueDays;
    }

    public BigDecimal getLoanPenlty() {
        return loanPenlty;
    }

    public void setLoanPenlty(BigDecimal loanPenlty) {
        this.loanPenlty = loanPenlty;
    }

    public String getCollectionStatus() {
        return collectionStatus;
    }

    public void setCollectionStatus(String collectionStatus) {
        this.collectionStatus = collectionStatus;
    }

    public Date getLoanEndTime() {
        return loanEndTime;
    }

    public void setLoanEndTime(Date loanEndTime) {
        this.loanEndTime = loanEndTime;
    }

    public Integer getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(Integer returnStatus) {
        this.returnStatus = returnStatus;
    }

    public BigDecimal getReturnMonest() {
        return returnMoney;
    }

    public void setReturnMonest(BigDecimal returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Date getCurrentReturnDay() {
        return currentReturnDay;
    }

    public void setCurrentReturnDay(Date currentReturnDay) {
        this.currentReturnDay = currentReturnDay;
    }

    public BigDecimal getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(BigDecimal returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Date getLastCollectionTime() {
        return lastCollectionTime;
    }

    public void setLastCollectionTime(Date lastCollectionTime) {
        this.lastCollectionTime = lastCollectionTime;
    }

    public Date getPromiseRepaymentTime() {
        return promiseRepaymentTime;
    }

    public void setPromiseRepaymentTime(Date promiseRepaymentTime) {
        this.promiseRepaymentTime = promiseRepaymentTime;
    }

    public Date getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(Date dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public String getCurrUserName() {
        return currUserName;
    }

    public void setCurrUserName(String currUserName) {
        this.currUserName = currUserName;
    }

    public String getLastUserName() {
        return lastUserName;
    }

    public void setLastUserName(String lastUserName) {
        this.lastUserName = lastUserName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDispatchName() {
        return dispatchName;
    }

    public void setDispatchName(String dispatchName) {
        this.dispatchName = dispatchName;
    }

    public String getTopImportant() {
        return topImportant;
    }

    public void setTopImportant(String topImportant) {
        this.topImportant = topImportant;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public BigDecimal getAccrual() {
        return accrual;
    }

    public void setAccrual(BigDecimal accrual) {
        this.accrual = accrual;
    }
}
