package com.info.web.pojo;


import java.math.BigDecimal;
import java.util.Date;

/**
  还款信息
*/
public class CreditLoanPay {
	// 
	private String id;
	// 借款Id
	private String loanId;
	// 起始日
	private Date receivableStartdate;
	// 应还日期
	private Date receivableDate;
	// 应还金额
	private BigDecimal receivableMoney;
	// 剩余应还本金
	private BigDecimal receivablePrinciple;
	// 剩余应还罚息
	private BigDecimal receivableInterest;
	// 实收金额
	private BigDecimal realMoney;
	// 实收本金
	private BigDecimal realgetPrinciple;
	// 实收罚息
	private BigDecimal realgetInterest;
	//减免金额
	private BigDecimal reductionMoney;
	// 还款状态（3，4，5，6，7对应S1，S2，M1-M2，M2-M3，M3+对应1-10,11-30（1），1个月-2个月，2个月-3个月，3个月+）
	private Integer status;
	// 创建时间
	private Date createDate;
	// 最后更新时间
	private Date updateDate;
	// 待收本金（这里不用）
	private BigDecimal restPrinciple;
	// 分期方式（这里不用）
	private Integer installmentMethod;
	// （这里不用）
	private String createBy;
	// （这里不用）
	private String updateBy;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setLoanId(String loanId){
		this.loanId = loanId;
	}

	public String getLoanId(){
		return this.loanId;
	}

	public void setReceivableStartdate(Date receivableStartdate){
		this.receivableStartdate = receivableStartdate;
	}

	public Date getReceivableStartdate(){
		return this.receivableStartdate;
	}

	public void setReceivableDate(Date receivableDate){
		this.receivableDate = receivableDate;
	}

	public Date getReceivableDate(){
		return this.receivableDate;
	}

	public void setReceivableMoney(BigDecimal receivableMoney){
		this.receivableMoney = receivableMoney;
	}

	public BigDecimal getReceivableMoney(){
		return this.receivableMoney;
	}

	public void setReceivablePrinciple(BigDecimal receivablePrinciple){
		this.receivablePrinciple = receivablePrinciple;
	}

	public BigDecimal getReceivablePrinciple(){
		return this.receivablePrinciple;
	}

	public void setReceivableInterest(BigDecimal receivableInterest){
		this.receivableInterest = receivableInterest;
	}

	public BigDecimal getReceivableInterest(){
		return this.receivableInterest;
	}

	public void setRealMoney(BigDecimal realMoney){
		this.realMoney = realMoney;
	}

	public BigDecimal getRealMoney(){
		return this.realMoney;
	}

	public void setRealgetPrinciple(BigDecimal realgetPrinciple){
		this.realgetPrinciple = realgetPrinciple;
	}

	public BigDecimal getRealgetPrinciple(){
		return this.realgetPrinciple;
	}

	public void setRealgetInterest(BigDecimal realgetInterest){
		this.realgetInterest = realgetInterest;
	}

	public BigDecimal getRealgetInterest(){
		return this.realgetInterest;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setRestPrinciple(BigDecimal restPrinciple){
		this.restPrinciple = restPrinciple;
	}

	public BigDecimal getRestPrinciple(){
		return this.restPrinciple;
	}

	public void setInstallmentMethod(Integer installmentMethod){
		this.installmentMethod = installmentMethod;
	}

	public Integer getInstallmentMethod(){
		return this.installmentMethod;
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

	public BigDecimal getReductionMoney() {
		return reductionMoney;
	}

	public void setReductionMoney(BigDecimal reductionMoney) {
		this.reductionMoney = reductionMoney;
	}
	
}