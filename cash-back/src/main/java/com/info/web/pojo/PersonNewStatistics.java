package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2019/1/23 0023上午 11:35
 */

public class PersonNewStatistics {
    private Long id;
    private int backUserId;//催收员id
    private String roleId;//角色id
    private String companyId;//公司id
    private String companyName;
    private String groupLevel; //催收组
    private String backUserName;//催收员姓名
    private BigDecimal totalPrincipal;//入催本金
    private BigDecimal totalPenalty;//入催滞纳金
    private int totalOrderCount;//入催单数
    private int todayDoneCount;//当日完成单数
    private BigDecimal todayDoneMoney;//当日完成金额
    private int doneOrderCount;//完成单数
    private BigDecimal donePrincipal;//完成本金
    private BigDecimal donePenalty;//完成订单的滞纳金
    private BigDecimal realgetPenalty;//实收滞纳金
    private BigDecimal noCheckPenalty;//不考核滞纳金·
    private BigDecimal cleanPrincipalProbability; // 本金结清率
    private BigDecimal cleanPenaltyProbability; //滞纳金结清率
    private Date createDate;
    private Date updateDate;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBackUserId() {
        return backUserId;
    }

    public void setBackUserId(int backUserId) {
        this.backUserId = backUserId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(String groupLevel) {
        this.groupLevel = groupLevel;
    }

    public String getBackUserName() {
        return backUserName;
    }

    public void setBackUserName(String backUserName) {
        this.backUserName = backUserName;
    }

    public BigDecimal getTotalPrincipal() {
        return totalPrincipal;
    }

    public void setTotalPrincipal(BigDecimal totalPrincipal) {
        this.totalPrincipal = totalPrincipal;
    }

    public BigDecimal getTotalPenalty() {
        return totalPenalty;
    }

    public void setTotalPenalty(BigDecimal totalPenalty) {
        this.totalPenalty = totalPenalty;
    }

    public int getTotalOrderCount() {
        return totalOrderCount;
    }

    public void setTotalOrderCount(int totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
    }

    public int getTodayDoneCount() {
        return todayDoneCount;
    }

    public void setTodayDoneCount(int todayDoneCount) {
        this.todayDoneCount = todayDoneCount;
    }

    public BigDecimal getTodayDoneMoney() {
        return todayDoneMoney;
    }

    public void setTodayDoneMoney(BigDecimal todayDoneMoney) {
        this.todayDoneMoney = todayDoneMoney;
    }

    public int getDoneOrderCount() {
        return doneOrderCount;
    }

    public void setDoneOrderCount(int doneOrderCount) {
        this.doneOrderCount = doneOrderCount;
    }

    public BigDecimal getDonePrincipal() {
        return donePrincipal;
    }

    public void setDonePrincipal(BigDecimal donePrincipal) {
        this.donePrincipal = donePrincipal;
    }

    public BigDecimal getDonePenalty() {
        return donePenalty;
    }

    public void setDonePenalty(BigDecimal donePenalty) {
        this.donePenalty = donePenalty;
    }

    public BigDecimal getRealgetPenalty() {
        return realgetPenalty;
    }

    public void setRealgetPenalty(BigDecimal realgetPenalty) {
        this.realgetPenalty = realgetPenalty;
    }

    public BigDecimal getNoCheckPenalty() {
        return noCheckPenalty;
    }

    public void setNoCheckPenalty(BigDecimal noCheckPenalty) {
        this.noCheckPenalty = noCheckPenalty;
    }

    public BigDecimal getCleanPrincipalProbability() {
        return cleanPrincipalProbability;
    }

    public void setCleanPrincipalProbability(BigDecimal cleanPrincipalProbability) {
        this.cleanPrincipalProbability = cleanPrincipalProbability;
    }

    public BigDecimal getCleanPenaltyProbability() {
        return cleanPenaltyProbability;
    }

    public void setCleanPenaltyProbability(BigDecimal cleanPenaltyProbability) {
        this.cleanPenaltyProbability = cleanPenaltyProbability;
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
}
