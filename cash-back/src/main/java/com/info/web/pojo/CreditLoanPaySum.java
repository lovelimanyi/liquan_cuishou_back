package com.info.web.pojo;

import java.math.BigDecimal;

public class CreditLoanPaySum {
	//实收本金总额
	private BigDecimal sumRealMoney;
	//实收罚息总额
	private BigDecimal sumRealPenlty;
	public BigDecimal getSumRealMoney() {
		return sumRealMoney;
	}
	public void setSumRealMoney(BigDecimal sumRealMoney) {
		this.sumRealMoney = sumRealMoney;
	}
	public BigDecimal getSumRealPenlty() {
		return sumRealPenlty;
	}
	public void setSumRealPenlty(BigDecimal sumRealPenlty) {
		this.sumRealPenlty = sumRealPenlty;
	}
	
	
}
