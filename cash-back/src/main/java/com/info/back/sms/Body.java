package com.info.back.sms;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"dests","batch_num","sms_type","content"})
@XmlRootElement(name="xml")
public class Body {
	private Dests dests;
	private String batch_num;
	private String sms_type;
	private String content;
	public Dests getDests() {
		return dests;
	}
	public void setDests(Dests dests) {
		this.dests = dests;
	}
	public String getBatch_num() {
		return batch_num;
	}
	public void setBatch_num(String batchNum) {
		batch_num = batchNum;
	}
	public String getSms_type() {
		return sms_type;
	}
	public void setSms_type(String smsType) {
		sms_type = smsType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	

}
