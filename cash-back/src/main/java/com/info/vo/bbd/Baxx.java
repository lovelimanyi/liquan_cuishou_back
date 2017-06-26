package com.info.vo.bbd;

public class Baxx {
	private String no;
	private String name;
	private String position;
	private String company_name;
	private String type;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String companyName) {
		company_name = companyName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if ("director".equals(type)) {
			type = "董事";
		} else if ("supervisor".equals(type)) {
			type = "监事";
		} else if ("executive".equals(type)) {
			type = "高管";
		}
		this.type = type;
	}

}
