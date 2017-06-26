package com.info.web.pojo.index;

/**
 * 公告栏
 * 
 * @author gaoyuhai
 * 
 */
public class Notice {

	private String contentTitle;
	private String contentTxt;
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public String getContentTxt() {
		return contentTxt;
	}

	public void setContentTxt(String contentTxt) {
		this.contentTxt = contentTxt;
	}

}
