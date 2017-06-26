package com.info.web.pojo.index;

public class QuickTender {

	private double rate;
	private String maxCount;
	private String cTender;
	private String tTender;
	private String borrowCv;
	private String borrowEvent;
	private String base;
	private String endDate;// 到目前为止还剩余的天数

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(String maxCount) {
		this.maxCount = maxCount;
	}

	public String getcTender() {
		return cTender;
	}

	public void setcTender(String cTender) {
		this.cTender = cTender;
	}

	public String gettTender() {
		return tTender;
	}

	public void settTender(String tTender) {
		this.tTender = tTender;
	}

	public String getBorrowCv() {
		return borrowCv;
	}

	public void setBorrowCv(String borrowCv) {
		this.borrowCv = borrowCv;
	}

	public String getBorrowEvent() {
		return borrowEvent;
	}

	public void setBorrowEvent(String borrowEvent) {
		this.borrowEvent = borrowEvent;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "QuickTender [base=" + base + ", borrowCv=" + borrowCv
				+ ", borrowEvent=" + borrowEvent + ", cTender=" + cTender
				+ ", endDate=" + endDate + ", maxCount=" + maxCount + ", rate="
				+ rate + ", tTender=" + tTender + "]";
	}

}
