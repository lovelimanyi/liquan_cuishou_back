package com.info.web.pojo.index;

public class TradeDto implements Comparable<TradeDto> {

	private Integer id;
	private double tenderRate;
	private int tenderNum;
	private int userId;
	private int flag;// 交易锁

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getTenderRate() {
		return tenderRate;
	}

	public void setTenderRate(double tenderRate) {
		this.tenderRate = tenderRate;
	}

	public int getTenderNum() {
		return tenderNum;
	}

	public void setTenderNum(int tenderNum) {
		this.tenderNum = tenderNum;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "TradeDto [flag=" + flag + ", id=" + id + ", tenderNum="
				+ tenderNum + ", tenderRate=" + tenderRate + ", userId="
				+ userId + "]";
	}

	@Override
	public int compareTo(TradeDto tradeDto) {
		return getId().compareTo(tradeDto.getId());
	}

	public TradeDto() {
	}

	public TradeDto(Integer id, double tenderRate, int tenderNum, int userId,
			int flag) {
		this.id = id;
		this.tenderRate = tenderRate;
		this.tenderNum = tenderNum;
		this.userId = userId;
		this.flag = flag;
	}

}
