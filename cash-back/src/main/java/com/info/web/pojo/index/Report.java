package com.info.web.pojo.index;

import java.util.List;

/**
 * 报道
 * 
 * @author gaoyuhai
 * 
 */
public class Report {

	private String reportTitle;
	private String reportContent;
	private List<String> reportList;
	private String moreUrl;

	public String getReportTitle() {
		return reportTitle;
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	public String getReportContent() {
		return reportContent;
	}

	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}

	public List<String> getReportList() {
		return reportList;
	}

	public void setReportList(List<String> reportList) {
		this.reportList = reportList;
	}

	public String getMoreUrl() {
		return moreUrl;
	}

	public void setMoreUrl(String moreUrl) {
		this.moreUrl = moreUrl;
	}

	@Override
	public String toString() {
		return "Report [moreUrl=" + moreUrl + ", reportContent="
				+ reportContent + ", reportList=" + reportList
				+ ", reportTitle=" + reportTitle + "]";
	}

}
