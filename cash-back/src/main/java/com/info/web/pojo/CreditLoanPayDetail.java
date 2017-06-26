package com.info.web.pojo;


import java.math.BigDecimal;
import java.util.Date;

/**
  还款详细表
*/
public class CreditLoanPayDetail {
	// 
	private String id;
	// 还款id
	private String payId;
	// 实收本金
	private BigDecimal realMoney;
	// 实收罚息
	private BigDecimal realPenlty;
	// 剩余应还本金
	private BigDecimal realPrinciple;
	// 剩余应还利息
	private BigDecimal realInterest;
	// 还款方式
	private String returnType;
	// 备注
	private String remark;
	// 更新时间
	private Date updateDate;
	// (这里不用)
	private String createBy;
	// (这里不用)
	private String updateBy;
	// (这里不用)
	private Date createDate;
	// 收款银行ID(这里不用)
	private String bankId;
	// 银行流水(这里不用)
	private String bankFlownum;
	// 实收时间(这里不用)
	private Date realDate;
	// 催收员ID
	private String currentCollectionUserId;
	//纪录2-11号S1组订单到S2人手上的标志
	private String s1Flag;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setPayId(String payId){
		this.payId = payId;
	}

	public String getPayId(){
		return this.payId;
	}

	public void setRealMoney(BigDecimal realMoney){
		this.realMoney = realMoney;
	}

	public BigDecimal getRealMoney(){
		return this.realMoney;
	}

	public void setRealPenlty(BigDecimal realPenlty){
		this.realPenlty = realPenlty;
	}

	public BigDecimal getRealPenlty(){
		return this.realPenlty;
	}

	public void setRealPrinciple(BigDecimal realPrinciple){
		this.realPrinciple = realPrinciple;
	}

	public BigDecimal getRealPrinciple(){
		return this.realPrinciple;
	}

	public void setRealInterest(BigDecimal realInterest){
		this.realInterest = realInterest;
	}

	public BigDecimal getRealInterest(){
		return this.realInterest;
	}

	public void setReturnType(String returnType){
		this.returnType = returnType;
	}

	public String getReturnType(){
		return this.returnType;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}

	public String getCreateBy(){
		return this.createBy;
	}

	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}

	public String getUpdateBy(){
		return this.updateBy;
	}

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setBankId(String bankId){
		this.bankId = bankId;
	}

	public String getBankId(){
		return this.bankId;
	}

	public void setBankFlownum(String bankFlownum){
		this.bankFlownum = bankFlownum;
	}

	public String getBankFlownum(){
		return this.bankFlownum;
	}

	public void setRealDate(Date realDate){
		this.realDate = realDate;
	}

	public Date getRealDate(){
		return this.realDate;
	}

	public void setCurrentCollectionUserId(String currentCollectionUserId){
		this.currentCollectionUserId = currentCollectionUserId;
	}

	public String getCurrentCollectionUserId(){
		return this.currentCollectionUserId;
	}

	public String getS1Flag() {
		return s1Flag;
	}

	public void setS1Flag(String s1Flag) {
		this.s1Flag = s1Flag;
	}

	@Override
	public String toString() {
		return "CreditLoanPayDetail [bankFlownum=" + bankFlownum + ", bankId="
				+ bankId + ", createBy=" + createBy + ", createDate="
				+ createDate + ", currentCollectionUserId="
				+ currentCollectionUserId + ", id=" + id + ", payId=" + payId
				+ ", realDate=" + realDate + ", realInterest=" + realInterest
				+ ", realMoney=" + realMoney + ", realPenlty=" + realPenlty
				+ ", realPrinciple=" + realPrinciple + ", remark=" + remark
				+ ", returnType=" + returnType + ", s1Flag=" + s1Flag
				+ ", updateBy=" + updateBy + ", updateDate=" + updateDate + "]";
	}

}