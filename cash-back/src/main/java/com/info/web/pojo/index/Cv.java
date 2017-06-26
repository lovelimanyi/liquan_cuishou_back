package com.info.web.pojo.index;

/**
 * 简介
 * 
 * @author gaoyuhai
 * 
 */
public class Cv {

	private String cvName;
	private String content;
	private String moreUrl;

	public String getCvName() {
		return cvName;
	}

	public void setCvName(String cvName) {
		this.cvName = cvName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMoreUrl() {
		return moreUrl;
	}

	public void setMoreUrl(String moreUrl) {
		this.moreUrl = moreUrl;
	}

	@Override
	public String toString() {
		return "Cv [content=" + content + ", cvName=" + cvName + ", moreUrl="
				+ moreUrl + "]";
	}

}
