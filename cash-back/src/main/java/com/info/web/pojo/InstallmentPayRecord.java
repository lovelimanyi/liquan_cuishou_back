package com.info.web.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 */
public class InstallmentPayRecord implements Serializable {
    private String id;

    /**
     * 还款批次
     */
    private String repayBatches;

    /**
     * 应还时间
     */
    private Date repayTime;

    /**
     * 应还金额
     */
    private BigDecimal repayMoney;

    /**
     * 还款状态（0：还款成功  1：逾期未还）
     */
    private String repayStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 借款订单id（mman_loan_collection_order的主键）
     */
    private String loanOrderId;

    /**
     * 借款人姓名
     */
    private String loanUserName;

    /**
     * 借款人电话
     */
    private String loanUserPhone;

    /**
     * 操作状态（0：代扣，1：无代扣）
     */
    private String operationStatus;

    private String dateNew;

    private static final long serialVersionUID = 1L;

    public String getDateNew() {
        return dateNew;
    }

    public void setDateNew(String dateNew) {
        this.dateNew = dateNew;
    }

    public String getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(String operationStatus) {
        this.operationStatus = operationStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepayBatches() {
        return repayBatches;
    }

    public void setRepayBatches(String repayBatches) {
        this.repayBatches = repayBatches;
    }

    public Date getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(Date repayTime) {
        this.repayTime = repayTime;
    }

    public BigDecimal getRepayMoney() {
        return repayMoney;
    }

    public void setRepayMoney(BigDecimal repayMoney) {
        this.repayMoney = repayMoney;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLoanOrderId() {
        return loanOrderId;
    }

    public void setLoanOrderId(String loanOrderId) {
        this.loanOrderId = loanOrderId;
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
}