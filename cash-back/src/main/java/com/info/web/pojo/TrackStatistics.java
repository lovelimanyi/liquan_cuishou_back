package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 类描述：追踪统计实体类
 * 创建人：yyf
 * 创建时间：2018/6/6 0006下午 04:06
 */

public class TrackStatistics {
    private Long id;
    private Date dispatchTime;//派单时间
    private String companyName; //催收公司名称
    private String companyId;//催收公司id
    private String backUserName;//催收员姓名
    private int backUserId; //催收员id
    private String uuid; //催收员uuid
    private String groupLevel;//催收员组别
    private String borrowingType;//借款类型 1大额，2小额，3分期商城
    private String userType;//借款用户类型
    private BigDecimal totalPrincipal;//总本金
    private BigDecimal realgetTotalPrincipal;//已还本金
    private BigDecimal remainPrincipal; //剩余本金
    private BigDecimal repaymentProbability;//本金还款率
    private BigDecimal totalPenalty;//总滞纳金
    private BigDecimal realgetTotalPenalty;//已还滞纳金
    private BigDecimal remainPenalty;//剩余滞纳金
    private BigDecimal penaltyProbability;//滞纳金还款率
    private int totalOrderCount;//总订单数
    private int doneOrderCount;//完成订单数
    private int undoneOrderCount;//未还订单数
    private BigDecimal orderProbability;//订单完成率
    private int handleCount;//订单处理量
    private Date createDate;
    private String merchantNo;

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(Date dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getBackUserName() {
        return backUserName;
    }

    public void setBackUserName(String backUserName) {
        this.backUserName = backUserName;
    }

    public int getBackUserId() {
        return backUserId;
    }

    public void setBackUserId(int backUserId) {
        this.backUserId = backUserId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(String groupLevel) {
        this.groupLevel = groupLevel;
    }

    public String getBorrowingType() {
        return borrowingType;
    }

    public void setBorrowingType(String borrowingType) {
        this.borrowingType = borrowingType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public BigDecimal getTotalPrincipal() {
        return totalPrincipal;
    }

    public void setTotalPrincipal(BigDecimal totalPrincipal) {
        this.totalPrincipal = totalPrincipal;
    }

    public BigDecimal getRealgetTotalPrincipal() {
        return realgetTotalPrincipal;
    }

    public void setRealgetTotalPrincipal(BigDecimal realgetTotalPrincipal) {
        this.realgetTotalPrincipal = realgetTotalPrincipal;
    }

    public BigDecimal getRemainPrincipal() {
        return remainPrincipal;
    }

    public void setRemainPrincipal(BigDecimal remainPrincipal) {
        this.remainPrincipal = remainPrincipal;
    }

    public BigDecimal getRepaymentProbability() {
        return repaymentProbability;
    }

    public void setRepaymentProbability(BigDecimal repaymentProbability) {
        this.repaymentProbability = repaymentProbability;
    }

    public BigDecimal getTotalPenalty() {
        return totalPenalty;
    }

    public void setTotalPenalty(BigDecimal totalPenalty) {
        this.totalPenalty = totalPenalty;
    }

    public BigDecimal getRealgetTotalPenalty() {
        return realgetTotalPenalty;
    }

    public void setRealgetTotalPenalty(BigDecimal realgetTotalPenalty) {
        this.realgetTotalPenalty = realgetTotalPenalty;
    }

    public BigDecimal getRemainPenalty() {
        return remainPenalty;
    }

    public void setRemainPenalty(BigDecimal remainPenalty) {
        this.remainPenalty = remainPenalty;
    }

    public BigDecimal getPenaltyProbability() {
        return penaltyProbability;
    }

    public void setPenaltyProbability(BigDecimal penaltyProbability) {
        this.penaltyProbability = penaltyProbability;
    }

    public int getTotalOrderCount() {
        return totalOrderCount;
    }

    public void setTotalOrderCount(int totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
    }

    public int getUndoneOrderCount() {
        return undoneOrderCount;
    }

    public void setUndoneOrderCount(int undoneOrderCount) {
        this.undoneOrderCount = undoneOrderCount;
    }

    public int getDoneOrderCount() {
        return doneOrderCount;
    }

    public void setDoneOrderCount(int doneOrderCount) {
        this.doneOrderCount = doneOrderCount;
    }

    public BigDecimal getOrderProbability() {
        return orderProbability;
    }

    public void setOrderProbability(BigDecimal orderProbability) {
        this.orderProbability = orderProbability;
    }

    public int getHandleCount() {
        return handleCount;
    }

    public void setHandleCount(int handleCount) {
        this.handleCount = handleCount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
