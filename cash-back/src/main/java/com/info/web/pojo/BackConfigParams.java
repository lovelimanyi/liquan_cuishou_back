package com.info.web.pojo;


public class BackConfigParams {
	/** configParams表中资产类型的sys_type */
	public static final String ASSETS_TYPE = "ASSETS_TYPE";
	/** configParams表中邮箱的sys_type */
	public static final String SMTP = "SMTP";
	/** configParams表中短信的sys_type */
	public static final String SMS = "SMS";
	/** configParams表中助通短信的sys_type */
	public static final String SMS_ZT = "SMS_ZT";
	/** configParams表中短信验证各种限制的的sys_type */
	public static final String SMSCODE = "SMSCODE";
	/** configParams表中会员付费的sys_type */
	public static final String VIP = "VIP";
	/** configParams表中网站参数的sys_type */
	public static final String WEBSITE = "WEBSITE";
	/** configParams表中关于我们的sys_type */
	public static final String CHANNEL = "CHANNEL";
	/** configParams表中短信签名的sys_type */
	public static final String SIGN = "SIGN";
	static {
	}

	public enum inputTypeEnum {
		text, textarea, password, image;
	}

	private Integer id;

	private String sysName;

	private String sysValue;

	private String sysValueBig;
	private String sysValueView;

	public String getSysValueView() {
		return sysValueView;
	}

	public void setSysValueView(String sysValueView) {
		this.sysValueView = sysValueView;
	}

	private String sysKey;

	private String sysType;

	private String inputType;

	private String remark;

	private String limitCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName == null ? null : sysName.trim();
	}

	public String getSysValue() {
		return sysValue;
	}

	public void setSysValue(String sysValue) {
		this.sysValue = sysValue == null ? null : sysValue.trim();
	}

	public String getSysKey() {
		return sysKey;
	}

	public void setSysKey(String sysKey) {
		this.sysKey = sysKey == null ? null : sysKey.trim();
	}

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType == null ? null : sysType.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getLimitCode() {
		return limitCode;
	}

	public void setLimitCode(String limitCode) {
		this.limitCode = limitCode == null ? null : limitCode.trim();
	}

	public String getSysValueBig() {
		return sysValueBig;
	}

	public void setSysValueBig(String sysValueBig) {
		this.sysValueBig = sysValueBig == null ? null : sysValueBig.trim();
		if (ASSETS_TYPE.equals(sysType)) {
		}
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	/**
	 * 自动匹配，不用关心类型 但是InputType必须有值
	 * 
	 * @param value
	 */
	public void setSysValueAuto(String value) {

		if (inputTypeEnum.textarea.name().equals(this.getInputType())) {
			this.setSysValueBig(value);
		} else if (inputTypeEnum.text.name().equals(this.getInputType())) {
			this.setSysValue(value);
		} else if (inputTypeEnum.password.name().equals(this.getInputType())) {
			this.setSysValue(value);
		} else if (inputTypeEnum.image.name().equals(this.getInputType())) {
			this.setSysValue(value);
		}
	}

	/**
	 * 自动匹配，不用关心类型 但是InputType必须有值
	 * 
	 * @param value
	 */
	public String getSysValueAuto() {
		String sysValueAuto = null;
		if (inputTypeEnum.textarea.name().equals(this.getInputType())) {
			sysValueAuto = this.getSysValueBig();
		} else if (inputTypeEnum.text.name().equals(this.getInputType())) {
			sysValueAuto = this.getSysValue();
		} else if (inputTypeEnum.password.name().equals(this.getInputType())) {
			sysValueAuto = this.getSysValue();
		} else if (inputTypeEnum.image.name().equals(this.getInputType())) {
			sysValueAuto = this.getSysValue();
		}
		return sysValueAuto;
	}

}