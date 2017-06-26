package com.info.back.sms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"app_key","time_stamp","nonce_str","sign"})
@XmlRootElement(name="xml")
public class Head {
	private String app_key;
	private String time_stamp;
	private String nonce_str;
	private String sign;
	public String getApp_key() {
		return app_key;
	}
	public String getTime_stamp() {
		return time_stamp;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setApp_key(String appKey) {
		app_key = appKey;
	}
	public void setTime_stamp(String timeStamp) {
		time_stamp = timeStamp;
	}
	public void setNonce_str(String nonceStr) {
		nonce_str = nonceStr;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
