package com.info.web.pojo;

public class IndexPage {

	private int id;
	private String indexBanner;
	private String indexNotice;
	private String indexCv;
	private String indexAmount;
	private String indexHotBorrow;
	private String indexTTender;
	private String indexCTender;
	private String indexReport;
	private String indexQuestion;
	private String indexLink;
	private String updateDate;
	private String remark;
	private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIndexBanner() {
		return indexBanner;
	}

	public void setIndexBanner(String indexBanner) {
		this.indexBanner = indexBanner;
	}

	public String getIndexNotice() {
		return indexNotice;
	}

	public void setIndexNotice(String indexNotice) {
		this.indexNotice = indexNotice;
	}

	public String getIndexCv() {
		return indexCv;
	}

	public void setIndexCv(String indexCv) {
		this.indexCv = indexCv;
	}

	public String getIndexAmount() {
		return indexAmount;
	}

	public void setIndexAmount(String indexAmount) {
		this.indexAmount = indexAmount;
	}

	public String getIndexHotBorrow() {
		return indexHotBorrow;
	}

	public void setIndexHotBorrow(String indexHotBorrow) {
		this.indexHotBorrow = indexHotBorrow;
	}

	public String getIndexTTender() {
		return indexTTender;
	}

	public void setIndexTTender(String indexTTender) {
		this.indexTTender = indexTTender;
	}

	public String getIndexCTender() {
		return indexCTender;
	}

	public void setIndexCTender(String indexCTender) {
		this.indexCTender = indexCTender;
	}

	public String getIndexReport() {
		return indexReport;
	}

	public void setIndexReport(String indexReport) {
		this.indexReport = indexReport;
	}

	public String getIndexQuestion() {
		return indexQuestion;
	}

	public void setIndexQuestion(String indexQuestion) {
		this.indexQuestion = indexQuestion;
	}

	public String getIndexLink() {
		return indexLink;
	}

	public void setIndexLink(String indexLink) {
		this.indexLink = indexLink;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ZbUser [id=" + id + ", indexAmount=" + indexAmount
				+ ", indexBanner=" + indexBanner + ", indexCTender="
				+ indexCTender + ", indexCv=" + indexCv + ", indexHotBorrow="
				+ indexHotBorrow + ", indexLink=" + indexLink
				+ ", indexNotice=" + indexNotice + ", indexQuestion="
				+ indexQuestion + ", indexReport=" + indexReport
				+ ", indexTTender=" + indexTTender + ", remark=" + remark
				+ ", status=" + status + ", updateDate=" + updateDate + "]";
	}

}
