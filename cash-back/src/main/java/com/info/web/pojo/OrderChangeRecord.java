package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Administrator
 * @Description: 订单流转数据记录（统计需求）
 * @CreateTime 2018-05-17 下午 3:12
 **/
public class OrderChangeRecord {

    private String id;

    private String loanId;
    // 订发生流转前的催收员id
    private String currentUserId;
    // 该订单转派(逾期升级)后的催收员id
    private String nextUserId;
    // 实收本金
    private BigDecimal realgetPrinciple;
    // 剩余应还本金
    private BigDecimal remainPrinciple;
    // 实收利息
    private BigDecimal realgetAccrual;
    // 剩余应还利息
    private BigDecimal remainAccrual;
    // 订单转派(逾期升级)前的催收员公司id
    private String currentCompanyId;
    // 订单转派(逾期升级)后的催收员公司id
    private String nextCompanyId;

    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getNextUserId() {
        return nextUserId;
    }

    public void setNextUserId(String nextUserId) {
        this.nextUserId = nextUserId;
    }

    public BigDecimal getRealgetPrinciple() {
        return realgetPrinciple;
    }

    public void setRealgetPrinciple(BigDecimal realgetPrinciple) {
        this.realgetPrinciple = realgetPrinciple;
    }

    public BigDecimal getRemainPrinciple() {
        return remainPrinciple;
    }

    public void setRemainPrinciple(BigDecimal remainPrinciple) {
        this.remainPrinciple = remainPrinciple;
    }

    public BigDecimal getRealgetAccrual() {
        return realgetAccrual;
    }

    public void setRealgetAccrual(BigDecimal realgetAccrual) {
        this.realgetAccrual = realgetAccrual;
    }

    public BigDecimal getRemainAccrual() {
        return remainAccrual;
    }

    public void setRemainAccrual(BigDecimal remainAccrual) {
        this.remainAccrual = remainAccrual;
    }

    public String getCurrentCompanyId() {
        return currentCompanyId;
    }

    public void setCurrentCompanyId(String currentCompanyId) {
        this.currentCompanyId = currentCompanyId;
    }

    public String getNextCompanyId() {
        return nextCompanyId;
    }

    public void setNextCompanyId(String nextCompanyId) {
        this.nextCompanyId = nextCompanyId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
