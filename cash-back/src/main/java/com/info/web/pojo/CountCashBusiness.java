package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class CountCashBusiness {
	private Long id;
	// 日期
	private Date reportDate;
	// 到期金额
	private Long expireAmount;
	// 放款总额
	private Long moneyAmountCount;
	// 7天期限放款总额
	private Long sevendayMoenyCount;
	// 14天期限放款总额
	private Long fourteendayMoneyCount;
	// 逾期金额总量
	private Long overdueAmount;
	// 7天逾期总额
	private Long overdueRateSevenAmount;
	// 14天逾期总额
	private Long overdueRateFourteenAmount;
	// 更新时间
	private Date createdAt;
	//放款总订单数量
	private Integer borrowOrderCount;
	//放款7天总订单数量
	private Integer borrowOrderSevenCount;
	//放款14天总订单数量
	private Integer borrowOrderFourteenCount;
	//到期总量
	private Integer expireCount;
	//逾期总量
	private Integer overdueCount;
	//逾期7天总量
	private Integer overdueSevenCount;
	//逾期14天总量
	private Integer overdueFourteenCount;
	//7天产品金额逾期率
	private BigDecimal overdueMoneyStatistic7value;
	//14天产品金额逾期率
	private BigDecimal overdueMoneyStatistic14value;
	//7天产品数量逾期率
	private BigDecimal overdueMoneySumCountStatistic7Value;
	//14天产品数量逾期率
	private BigDecimal overdueMoneySumCountStatistic14Value;
	//21天期限放款总额
	private Long twentyonedayMoenyCount;
	//21天逾期总额
	private Long overdueRateTwentyoneAmount;
	//放款21天总订单数量
	private Integer borrowOrderTwentyoneCount;
	//逾期21天总量
	private Integer overdueTwentyoneCount;
	//21天产品金额逾期率
	private BigDecimal overdueMoneyStatistic21value;
	//21天产品数量逾期率
	private BigDecimal overdueMoneySumCountStatistic21Value;

	public Integer getOverdueTwentyoneCount() {
		return overdueTwentyoneCount;
	}

	public void setOverdueTwentyoneCount(Integer overdueTwentyoneCount) {
		this.overdueTwentyoneCount = overdueTwentyoneCount;
	}

	public Long getTwentyonedayMoenyCount() {
		return twentyonedayMoenyCount;
	}

	public void setTwentyonedayMoenyCount(Long twentyonedayMoenyCount) {
		this.twentyonedayMoenyCount = twentyonedayMoenyCount;
	}

	public Long getOverdueRateTwentyoneAmount() {
		return overdueRateTwentyoneAmount;
	}

	public void setOverdueRateTwentyoneAmount(Long overdueRateTwentyoneAmount) {
		this.overdueRateTwentyoneAmount = overdueRateTwentyoneAmount;
	}

	public Integer getBorrowOrderTwentyoneCount() {
		return borrowOrderTwentyoneCount;
	}

	public void setBorrowOrderTwentyoneCount(Integer borrowOrderTwentyoneCount) {
		this.borrowOrderTwentyoneCount = borrowOrderTwentyoneCount;
	}

	public BigDecimal getOverdueMoneyStatistic21value() {
		return overdueMoneyStatistic21value;
	}

	public void setOverdueMoneyStatistic21value(BigDecimal overdueMoneyStatistic21value) {
		this.overdueMoneyStatistic21value = overdueMoneyStatistic21value;
	}

	public BigDecimal getOverdueMoneySumCountStatistic21Value() {
		return overdueMoneySumCountStatistic21Value;
	}

	public void setOverdueMoneySumCountStatistic21Value(BigDecimal overdueMoneySumCountStatistic21Value) {
		this.overdueMoneySumCountStatistic21Value = overdueMoneySumCountStatistic21Value;
	}

	public Integer getBorrowOrderCount() {
		return borrowOrderCount;
	}

	public void setBorrowOrderCount(Integer borrowOrderCount) {
		this.borrowOrderCount = borrowOrderCount;
	}

	public Integer getBorrowOrderSevenCount() {
		return borrowOrderSevenCount;
	}

	public void setBorrowOrderSevenCount(Integer borrowOrderSevenCount) {
		this.borrowOrderSevenCount = borrowOrderSevenCount;
	}

	public Integer getBorrowOrderFourteenCount() {
		return borrowOrderFourteenCount;
	}

	public void setBorrowOrderFourteenCount(Integer borrowOrderFourteenCount) {
		this.borrowOrderFourteenCount = borrowOrderFourteenCount;
	}

	public Integer getExpireCount() {
		return expireCount;
	}

	public void setExpireCount(Integer expireCount) {
		this.expireCount = expireCount;
	}

	public Integer getOverdueCount() {
		return overdueCount;
	}

	public void setOverdueCount(Integer overdueCount) {
		this.overdueCount = overdueCount;
	}

	public Integer getOverdueSevenCount() {
		return overdueSevenCount;
	}

	public void setOverdueSevenCount(Integer overdueSevenCount) {
		this.overdueSevenCount = overdueSevenCount;
	}

	public Integer getOverdueFourteenCount() {
		return overdueFourteenCount;
	}

	public void setOverdueFourteenCount(Integer overdueFourteenCount) {
		this.overdueFourteenCount = overdueFourteenCount;
	}


	public BigDecimal getOverdueMoneyStatistic7value() {
		return overdueMoneyStatistic7value;
	}

	public void setOverdueMoneyStatistic7value(BigDecimal overdueMoneyStatistic7value) {
		this.overdueMoneyStatistic7value = overdueMoneyStatistic7value;
	}

	public BigDecimal getOverdueMoneyStatistic14value() {
		return overdueMoneyStatistic14value;
	}

	public void setOverdueMoneyStatistic14value(BigDecimal overdueMoneyStatistic14value) {
		this.overdueMoneyStatistic14value = overdueMoneyStatistic14value;
	}

	public BigDecimal getOverdueMoneySumCountStatistic7Value() {
		return overdueMoneySumCountStatistic7Value;
	}

	public void setOverdueMoneySumCountStatistic7Value(BigDecimal overdueMoneySumCountStatistic7Value) {
		this.overdueMoneySumCountStatistic7Value = overdueMoneySumCountStatistic7Value;
	}

	public BigDecimal getOverdueMoneySumCountStatistic14Value() {
		return overdueMoneySumCountStatistic14Value;
	}

	public void setOverdueMoneySumCountStatistic14Value(BigDecimal overdueMoneySumCountStatistic14Value) {
		this.overdueMoneySumCountStatistic14Value = overdueMoneySumCountStatistic14Value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Long getExpireAmount() {
		return expireAmount;
	}

	public void setExpireAmount(Long expireAmount) {
		this.expireAmount = expireAmount;
	}

	public Long getMoneyAmountCount() {
		return moneyAmountCount;
	}

	public void setMoneyAmountCount(Long moneyAmountCount) {
		this.moneyAmountCount = moneyAmountCount;
	}

	public Long getSevendayMoenyCount() {
		return sevendayMoenyCount;
	}

	public void setSevendayMoenyCount(Long sevendayMoenyCount) {
		this.sevendayMoenyCount = sevendayMoenyCount;
	}

	public Long getFourteendayMoneyCount() {
		return fourteendayMoneyCount;
	}

	public void setFourteendayMoneyCount(Long fourteendayMoneyCount) {
		this.fourteendayMoneyCount = fourteendayMoneyCount;
	}

	public Long getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(Long overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public Long getOverdueRateSevenAmount() {
		return overdueRateSevenAmount;
	}

	public void setOverdueRateSevenAmount(Long overdueRateSevenAmount) {
		this.overdueRateSevenAmount = overdueRateSevenAmount;
	}

	public Long getOverdueRateFourteenAmount() {
		return overdueRateFourteenAmount;
	}

	public void setOverdueRateFourteenAmount(Long overdueRateFourteenAmount) {
		this.overdueRateFourteenAmount = overdueRateFourteenAmount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
