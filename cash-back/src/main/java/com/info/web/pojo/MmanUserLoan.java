package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
  用户借款表
*/
public class MmanUserLoan {
	// 
	private String id;
	// 借款用户id
	private String userId;
	// 借款订单号或借款编号
	private String loanPyId;
	// 借款金额，元
	private BigDecimal loanMoney;
	// 借款利率（如10）
	private String loanRate;
	// 罚息金额
	private BigDecimal loanPenalty;
	// 罚息率(如10，按天算)
	private String loanPenaltyRate;
	// 借款服务费
	private BigDecimal serviceCharge;
	// 本金+服务费
	private BigDecimal paidMoney;

	public BigDecimal getPaidMoney() {
		return paidMoney;
	}

	public void setPaidMoney(BigDecimal paidMoney) {
		this.paidMoney = paidMoney;
	}

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	// 放款时间（借款起始时间）
	private Date loanStartTime;
	// 应还时间（借款结束时间）
	private Date loanEndTime;
	// 借款状态（'4'-逾期,'5'-还款结束）
	private String loanStatus;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;
	// 删除标记（'1'-已删除，'0'正常）
	private String delFlag;
	// 这里不用
	private String loanPenaltyTime;
	// 这里不用
	private String loanSysStatus;
	// 这里不用
	private String loanSysRemark;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setLoanPyId(String loanPyId){
		this.loanPyId = loanPyId;
	}

	public String getLoanPyId(){
		return this.loanPyId;
	}

	public void setLoanMoney(BigDecimal loanMoney){
		this.loanMoney = loanMoney;
	}

	public BigDecimal getLoanMoney(){
		return this.loanMoney;
	}

	public void setLoanRate(String loanRate){
		this.loanRate = loanRate;
	}

	public String getLoanRate(){
		return this.loanRate;
	}

	public void setLoanPenalty(BigDecimal loanPenalty){
		this.loanPenalty = loanPenalty;
	}

	public BigDecimal getLoanPenalty(){
		return this.loanPenalty;
	}

	public void setLoanPenaltyRate(String loanPenaltyRate){
		this.loanPenaltyRate = loanPenaltyRate;
	}

	public String getLoanPenaltyRate(){
		return this.loanPenaltyRate;
	}

	public void setLoanStartTime(Date loanStartTime){
		this.loanStartTime = loanStartTime;
	}

	public Date getLoanStartTime(){
		return this.loanStartTime;
	}

	public void setLoanEndTime(Date loanEndTime){
		this.loanEndTime = loanEndTime;
	}

	public Date getLoanEndTime(){
		return this.loanEndTime;
	}

	public void setLoanStatus(String loanStatus){
		this.loanStatus = loanStatus;
	}

	public String getLoanStatus(){
		return this.loanStatus;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return this.createTime;
	}

	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}

	public Date getUpdateTime(){
		return this.updateTime;
	}

	public void setDelFlag(String delFlag){
		this.delFlag = delFlag;
	}

	public String getDelFlag(){
		return this.delFlag;
	}

	public void setLoanPenaltyTime(String loanPenaltyTime){
		this.loanPenaltyTime = loanPenaltyTime;
	}

	public String getLoanPenaltyTime(){
		return this.loanPenaltyTime;
	}

	public void setLoanSysStatus(String loanSysStatus){
		this.loanSysStatus = loanSysStatus;
	}

	public String getLoanSysStatus(){
		return this.loanSysStatus;
	}

	public void setLoanSysRemark(String loanSysRemark){
		this.loanSysRemark = loanSysRemark;
	}

	public String getLoanSysRemark(){
		return this.loanSysRemark;
	}

}