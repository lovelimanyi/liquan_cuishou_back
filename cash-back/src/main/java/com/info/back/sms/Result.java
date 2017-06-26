package com.info.back.sms;

import java.io.Serializable;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"error_code","error_msg","outString"})
@XmlRootElement(name="xml")
public class Result implements Serializable {
	
	private String error_code;
	private String error_msg;
	
	private String outString[] = new String[]{"<head>","</head>"};//需要去除的标签
	
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String errorCode) {
		error_code = errorCode;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String errorMsg) {
		error_msg = errorMsg;
	}
	
	
	public String[] getOutString() {
		return outString;
	}
	@Override
	public String toString() {
		return "Result [error_code=" + error_code + ", error_msg=" + error_msg
				+ ", outString=" + Arrays.toString(outString) + "]";
	}
	
}
