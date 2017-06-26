package com.info.web.pojo.index;

import java.math.BigDecimal;

public class QuickAttention {
	private Integer borrowId;
	private String sortName;
	private BigDecimal tenderRate;
	private Integer tenderSum;
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public BigDecimal getTenderRate() {
		return tenderRate;
	}

	public void setTenderRate(BigDecimal tenderRate) {
		this.tenderRate = tenderRate;
	}

	public Integer getTenderSum() {
		return tenderSum;
	}

	public void setTenderSum(Integer tenderSum) {
		this.tenderSum = tenderSum;
	}
}
