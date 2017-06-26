package com.info.back.sms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"head","body"})
@XmlRootElement(name="xml")
public class XmlDto {
	private Head head;
	private Body body;
	public Head getHead() {
		return head;
	}
	public Body getBody() {
		return body;
	}
	public void setHead(Head head) {
		this.head = head;
	}
	public void setBody(Body body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return "XmlDto [body=" + body + ", head=" + head + ", getBody()="
				+ getBody() + ", getHead()=" + getHead() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	

}
