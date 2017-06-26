package com.info.web.pojo.index;

/**
 * 横幅
 * 
 * @author gaoyuhai
 * 
 */
public class Banner {

	private String imgUrl;
	private String httpUrl;
	private String index;

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getHttpUrl() {
		return httpUrl;
	}

	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "Banner [httpUrl=" + httpUrl + ", imgUrl=" + imgUrl + ", index="
				+ index + "]";
	}

}
