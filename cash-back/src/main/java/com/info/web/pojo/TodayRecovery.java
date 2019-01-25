package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 类描述：当日催回率实体类
 * 创建人：yyf
 * 创建时间：2018/6/14 0014上午 11:56
 */

public class TodayRecovery {

    private Long id;
    private Date createDate;
    private String companyName;
    private String companyId;
    private String backUserName;
    private String uuid;
    private String groupLevel; //催收组
    private String borrowingType;//借款类型

    private BigDecimal totalPrincipal;//总本金
    private BigDecimal realgetTotalPrincipal;//已还本金
    private BigDecimal remainPrincipal; //剩余本金
    private BigDecimal repaymentProbability;//本金还款率

    private Integer todayDoneCount;
    private BigDecimal todayDoneMoney;

    private BigDecimal totalPenalty;//总滞纳金
    private BigDecimal realgetTotalPenalty;//已还滞纳金
    private BigDecimal penaltyProbability;//滞纳金还款率

    private int totalOrderCount;//总订单数
    private int doneOrderCount;//完成订单数
    private BigDecimal orderProbability;//订单完成率
    private String merchantNo;

    public Integer getTodayDoneCount() {
        return todayDoneCount;
    }

    public void setTodayDoneCount(Integer todayDoneCount) {
        this.todayDoneCount = todayDoneCount;
    }

    public BigDecimal getTodayDoneMoney() {
        return todayDoneMoney;
    }

    public void setTodayDoneMoney(BigDecimal todayDoneMoney) {
        this.todayDoneMoney = todayDoneMoney;
    }

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
}
