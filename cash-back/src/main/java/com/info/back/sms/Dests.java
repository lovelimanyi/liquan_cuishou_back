package com.info.back.sms;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"dest"})
@XmlRootElement(name="xml")
public class Dests {
	private List<Dest> dest;

	public List<Dest> getDest() {
		return dest;
	}

	public void setDest(List<Dest> dest) {
		this.dest = dest;
	}
	

}
