package com.info.web.pojo.index;

/**
 * 热门标的
 * 
 * @author gaoyuhai
 * 
 */
public class HotBorrow {

	private String companyName;
	private String sortName;
	private String rate;
	private String moreUrl;// 详情链接

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getMoreUrl() {
		return moreUrl;
	}

	public void setMoreUrl(String moreUrl) {
		this.moreUrl = moreUrl;
	}

	@Override
	public String toString() {
		return "HotBorrow [companyName=" + companyName + ", moreUrl=" + moreUrl
				+ ", rate=" + rate + ", sortName=" + sortName + "]";
	}

}
