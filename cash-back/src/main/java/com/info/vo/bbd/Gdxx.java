package com.info.vo.bbd;

public class Gdxx {
	private String no;
	private String shareholder_name;
	private String shareholder_type;
	private String invest_amount;
	private String invest_ratio;
	
	public String getInvest_amount() {
		return invest_amount;
	}

	public void setInvest_amount(String investAmount) {
		invest_amount = investAmount;
	}

	public String getInvest_ratio() {
		return invest_ratio;
	}

	public void setInvest_ratio(String investRatio) {
		invest_ratio = investRatio;
	}

	private String idtype;
	private String idno;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getShareholder_name() {
		return shareholder_name;
	}

	public void setShareholder_name(String shareholderName) {
		shareholder_name = shareholderName;
	}

	public String getShareholder_type() {
		return shareholder_type;
	}

	public void setShareholder_type(String shareholderType) {
		shareholder_type = shareholderType;
	}

	public String getIdtype() {
		return idtype;
	}

	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

}
