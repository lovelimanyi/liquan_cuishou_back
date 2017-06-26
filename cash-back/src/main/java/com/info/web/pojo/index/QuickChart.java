package com.info.web.pojo.index;

public class QuickChart {

	private double rate;// 市场费率
	private String count;// 当日成交
	private String tCount;// 投保总份额
	private String tAveRate;// 投保平均费率
	private String tAmount;// 投保盈亏
	private String cCount;// 承保总份额
	private String cAveRate;// 投保平均费率
	private String cAmount;// 投保盈亏
	// 图表数据
	private String chartContent;// 曲线图
	private String chartAmount;// 量表图

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String gettCount() {
		return tCount;
	}

	public void settCount(String tCount) {
		this.tCount = tCount;
	}

	public String gettAveRate() {
		return tAveRate;
	}

	public void settAveRate(String tAveRate) {
		this.tAveRate = tAveRate;
	}

	public String gettAmount() {
		return tAmount;
	}

	public void settAmount(String tAmount) {
		this.tAmount = tAmount;
	}

	public String getcCount() {
		return cCount;
	}

	public void setcCount(String cCount) {
		this.cCount = cCount;
	}

	public String getcAveRate() {
		return cAveRate;
	}

	public void setcAveRate(String cAveRate) {
		this.cAveRate = cAveRate;
	}

	public String getcAmount() {
		return cAmount;
	}

	public void setcAmount(String cAmount) {
		this.cAmount = cAmount;
	}

	public String getChartContent() {
		return chartContent;
	}

	public void setChartContent(String chartContent) {
		this.chartContent = chartContent;
	}

	public String getChartAmount() {
		return chartAmount;
	}

	public void setChartAmount(String chartAmount) {
		this.chartAmount = chartAmount;
	}

	@Override
	public String toString() {
		return "QuickChart [cAmount=" + cAmount + ", cAveRate=" + cAveRate
				+ ", cCount=" + cCount + ", chartAmount=" + chartAmount
				+ ", chartContent=" + chartContent + ", count=" + count
				+ ", rate=" + rate + ", tAmount=" + tAmount + ", tAveRate="
				+ tAveRate + ", tCount=" + tCount + "]";
	}

	public QuickChart(double rate, String count, String tCount,
			String tAveRate, String tAmount, String cCount, String cAveRate,
			String cAmount, String chartContent, String chartAmount) {
		this.rate = rate;
		this.count = count;
		this.tCount = tCount;
		this.tAveRate = tAveRate;
		this.tAmount = tAmount;
		this.cCount = cCount;
		this.cAveRate = cAveRate;
		this.cAmount = cAmount;
		this.chartContent = chartContent;
		this.chartAmount = chartAmount;
	}

	public QuickChart() {
	}

	public QuickChart(double rate, String count, String tCount,
			String tAveRate, String tAmount, String cCount, String cAveRate,
			String cAmount) {
		this.rate = rate;
		this.count = count;
		this.tCount = tCount;
		this.tAveRate = tAveRate;
		this.tAmount = tAmount;
		this.cCount = cCount;
		this.cAveRate = cAveRate;
		this.cAmount = cAmount;
	}

}
