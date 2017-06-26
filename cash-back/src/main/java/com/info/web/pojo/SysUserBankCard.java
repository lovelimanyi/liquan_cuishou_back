package com.info.web.pojo;


/**
  系统用户绑定的银行卡
*/
public class SysUserBankCard {
	// 
	private String id;
	// 用户id
	private String userId;
	// 卡号
	private String bankCard;
	// 开户行
	private String depositBank;
	// 银行机构号
	private String bankInstitutionNo;
	// 开户支行
	private String branchBank;
	// 姓名
	private String name;
	// 银行预留手机号
	private String mobile;
	// 身份证
	private String idCard;
	// 信用卡CVN
	private String cvn;
	// 代付总行代码
	private String bankCode;
	// 开户行城市代码
	private String cityCode;
	// 开户行城市名称
	private String cityName;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setBankCard(String bankCard){
		this.bankCard = bankCard;
	}

	public String getBankCard(){
		return this.bankCard;
	}

	public void setDepositBank(String depositBank){
		this.depositBank = depositBank;
	}

	public String getDepositBank(){
		return this.depositBank;
	}

	public void setBankInstitutionNo(String bankInstitutionNo){
		this.bankInstitutionNo = bankInstitutionNo;
	}

	public String getBankInstitutionNo(){
		return this.bankInstitutionNo;
	}

	public void setBranchBank(String branchBank){
		this.branchBank = branchBank;
	}

	public String getBranchBank(){
		return this.branchBank;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return this.mobile;
	}

	public void setIdCard(String idCard){
		this.idCard = idCard;
	}

	public String getIdCard(){
		return this.idCard;
	}

	public void setCvn(String cvn){
		this.cvn = cvn;
	}

	public String getCvn(){
		return this.cvn;
	}

	public void setBankCode(String bankCode){
		this.bankCode = bankCode;
	}

	public String getBankCode(){
		return this.bankCode;
	}

	public void setCityCode(String cityCode){
		this.cityCode = cityCode;
	}

	public String getCityCode(){
		return this.cityCode;
	}

	public void setCityName(String cityName){
		this.cityName = cityName;
	}

	public String getCityName(){
		return this.cityName;
	}

}