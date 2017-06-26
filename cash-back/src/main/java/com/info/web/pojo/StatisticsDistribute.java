package com.info.web.pojo;

import java.math.BigDecimal;

/**
 * 业务量统计-本金分布
 * @author Administrator
 *
 */
public class StatisticsDistribute {
	private String type; //类别
    private BigDecimal waitMoney;   //待催收 ，本金
    private BigDecimal waitPenalty;  //待催收，滞纳金
    private BigDecimal waitRate;
    private BigDecimal waitPenaltyRate;
    private BigDecimal inMoney;    //催收中
    private BigDecimal inPenalty;   //催收中
    private BigDecimal inRate;
    private BigDecimal inPenaltyRate;
    private BigDecimal promiseMoney;  //承诺还款
    private BigDecimal promisePenalty;  
    private BigDecimal promiseRate;
    private BigDecimal promisePenaltyRate;
    private BigDecimal finishMoney;   //已还款
    private BigDecimal finishPenalty;
    private BigDecimal finishRate;
    private BigDecimal finishPenaltyRate;
    
    private BigDecimal loanMoney;   //本金
    private BigDecimal loanPenalty;  //滞纳金

	private String csType; //催收类型
	private BigDecimal ownnerMoney;   //本金
	private BigDecimal ownnerMoneyRate;   //本金比例
	private BigDecimal penalty;    //滞纳金
	private BigDecimal penaltyRate;    //滞纳金比例

	public String getCsType() {
		return csType;
	}

	public void setCsType(String csType) {
		this.csType = csType;
	}

	public BigDecimal getOwnnerMoney() {
		return ownnerMoney;
	}

	public void setOwnnerMoney(BigDecimal ownnerMoney) {
		this.ownnerMoney = ownnerMoney;
	}

	public BigDecimal getOwnnerMoneyRate() {
		return ownnerMoneyRate;
	}

	public void setOwnnerMoneyRate(BigDecimal ownnerMoneyRate) {
		this.ownnerMoneyRate = ownnerMoneyRate;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}

	public BigDecimal getPenaltyRate() {
		return penaltyRate;
	}

	public void setPenaltyRate(BigDecimal penaltyRate) {
		this.penaltyRate = penaltyRate;
	}

	public StatisticsDistribute(){
    	
    }
    public StatisticsDistribute(String type){
    	this.type=type;
    }
    
	public BigDecimal getWaitMoney() {
		return waitMoney == null ? new BigDecimal(0) : waitMoney;
	}
	public void setWaitMoney(BigDecimal waitMoney) {
		this.waitMoney = waitMoney;
	}
	public BigDecimal getWaitPenalty() {
		return waitPenalty == null ? new BigDecimal(0) : waitPenalty;
	}
	public void setWaitPenalty(BigDecimal waitPenalty) {
		this.waitPenalty = waitPenalty;
	}
	public BigDecimal getInMoney() {
		return inMoney == null ? new BigDecimal(0) : inMoney;
	}
	public void setInMoney(BigDecimal inMoney) {
		this.inMoney = inMoney;
	}
	public BigDecimal getInPenalty() {
		return inPenalty == null ? new BigDecimal(0) : inPenalty;
	}
	public void setInPenalty(BigDecimal inPenalty) {
		this.inPenalty = inPenalty;
	}
	public BigDecimal getPromiseMoney() {
		return promiseMoney == null ? new BigDecimal(0) : promiseMoney;
	}
	public void setPromiseMoney(BigDecimal promiseMoney) {
		this.promiseMoney = promiseMoney;
	}
	public BigDecimal getPromisePenalty() {
		return promisePenalty == null ? new BigDecimal(0) : promisePenalty;
	}
	public void setPromisePenalty(BigDecimal promisePenalty) {
		this.promisePenalty = promisePenalty;
	}
	public BigDecimal getFinishMoney() {
		return finishMoney == null ? new BigDecimal(0) : finishMoney;
	}
	public void setFinishMoney(BigDecimal finishMoney) {
		this.finishMoney = finishMoney;
	}
	public BigDecimal getFinishPenalty() {
		return finishPenalty == null ? new BigDecimal(0) : finishPenalty;
	}
	public void setFinishPenalty(BigDecimal finishPenalty) {
		this.finishPenalty = finishPenalty;
	}
	public BigDecimal getWaitRate() {
		return waitRate == null ? new BigDecimal(0) : waitRate;
	}
	public void setWaitRate(BigDecimal waitRate) {
		this.waitRate = waitRate;
	}
	public BigDecimal getInRate() {
		return inRate == null ? new BigDecimal(0) : inRate;
	}
	public void setInRate(BigDecimal inRate) {
		this.inRate = inRate;
	}
	public BigDecimal getPromiseRate() {
		return promiseRate == null ? new BigDecimal(0) : promiseRate;
	}
	public void setPromiseRate(BigDecimal promiseRate) {
		this.promiseRate = promiseRate;
	}
	public BigDecimal getFinishRate() {
		return finishRate == null ? new BigDecimal(0) : finishRate;
	}
	public void setFinishRate(BigDecimal finishRate) {
		this.finishRate = finishRate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getWaitPenaltyRate() {
		return waitPenaltyRate == null ? new BigDecimal(0) : waitPenaltyRate;
	}
	public void setWaitPenaltyRate(BigDecimal waitPenaltyRate) {
		this.waitPenaltyRate = waitPenaltyRate;
	}
	public BigDecimal getInPenaltyRate() {
		return inPenaltyRate == null ? new BigDecimal(0) : inPenaltyRate;
	}
	public void setInPenaltyRate(BigDecimal inPenaltyRate) {
		this.inPenaltyRate = inPenaltyRate;
	}
	public BigDecimal getPromisePenaltyRate() {
		return promisePenaltyRate == null ? new BigDecimal(0) : promisePenaltyRate;
	}
	public void setPromisePenaltyRate(BigDecimal promisePenaltyRate) {
		this.promisePenaltyRate = promisePenaltyRate;
	}
	public BigDecimal getFinishPenaltyRate() {
		return finishPenaltyRate == null ? new BigDecimal(0) : finishPenaltyRate;
	}
	public void setFinishPenaltyRate(BigDecimal finishPenaltyRate) {
		this.finishPenaltyRate = finishPenaltyRate;
	}
	public BigDecimal getLoanMoney() {
		return loanMoney == null ? new BigDecimal(0) : loanMoney;
	}
	public void setLoanMoney(BigDecimal loanMoney) {
		this.loanMoney = loanMoney;
	}
	public BigDecimal getLoanPenalty() {
		return loanPenalty == null ? new BigDecimal(0) : loanPenalty;
	}
	public void setLoanPenalty(BigDecimal loanPenalty) {
		this.loanPenalty = loanPenalty;
	}
   
}
