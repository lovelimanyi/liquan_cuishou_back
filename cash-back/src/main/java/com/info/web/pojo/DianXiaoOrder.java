package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/11/15 0015上午 10:01
 */

public class DianXiaoOrder {
    //
    private String id;
    // 借款ID
    private String loanId;
    //借款金额
    private BigDecimal loanMoney;
    //服务费
    private BigDecimal loanServiceCharge;
    //借款人姓名
    private String loanUserName;
    //借款人电话
    private String loanUserPhone;
    //借款开始日期
    private Date loanStartDate;
    //应还日期
    private Date loanEndDate;
    //订单状态 1未还款  4已还款
    private Integer orderStatus;
    //当前电催员id
    private String currentCollectionUserId;
    //当前电催员姓名
    private String currentCollectionUserName;
    //商户号
    private String merchantNo;
    //还款意向  1:未接通，2:已还款，3:稍后还款，4:下午还款，5:晚上还款，6:无还款意向，7:挂电话，8：过几天还款，9其他
    private Integer repaymentIntention;
    //备注
    private String remark;
    //创建日期
    private Date createDate;
    //更新日期
    private Date updateDate;
    //
    private Integer flag;

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

    public BigDecimal getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(BigDecimal loanMoney) {
        this.loanMoney = loanMoney;
    }

    public BigDecimal getLoanServiceCharge() {
        return loanServiceCharge;
    }

    public void setLoanServiceCharge(BigDecimal loanServiceCharge) {
        this.loanServiceCharge = loanServiceCharge;
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

    public Date getLoanStartDate() {
        return loanStartDate;
    }

    public void setLoanStartDate(Date loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public Date getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(Date loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
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

    public Integer getRepaymentIntention() {
        return repaymentIntention;
    }

    public void setRepaymentIntention(Integer repaymentIntention) {
        this.repaymentIntention = repaymentIntention;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    @Override
    public String toString() {
        return "DianXiaoOrder{" +
                "id='" + id + '\'' +
                ", loanId='" + loanId + '\'' +
                ", loanMoney=" + loanMoney +
                ", loanServiceCharge=" + loanServiceCharge +
                ", loanUserName='" + loanUserName + '\'' +
                ", loanUserPhone='" + loanUserPhone + '\'' +
                ", loanStartDate=" + loanStartDate +
                ", loanEndDate=" + loanEndDate +
                ", orderStatus=" + orderStatus +
                ", currentCollectionUserId='" + currentCollectionUserId + '\'' +
                ", currentCollectionUserName='" + currentCollectionUserName + '\'' +
                ", merchantNo='" + merchantNo + '\'' +
                ", repaymentIntention=" + repaymentIntention +
                ", remark='" + remark + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", flag=" + flag +
                '}';
    }
}
