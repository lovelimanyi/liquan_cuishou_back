package com.info.back.vo.jxl2;
/**
 * 数据检查点
 * @author yyf
 *
 * @version 
 */
public class CheckPoints {
	//性别
	private String gender;
	//年龄
	private String age;
	//省
	private String province;
	//市
	private String city;
	//区,县
	private String region;
	//申请表数据值
	private String key_value;
	//联系人关系
	private String relationship;
	//联系人姓名
	private String contact_name;
	//移动运营商
	private String website;
	//实名认证
	private String reliability;
	//注册时间
	private String reg_time;
	//核实姓名结果
	private String check_name;
	//核实身份证结果
	private String check_idcard;
	//核实电商号码结果
	private String check_ebusiness;
	//核实地址结果
	private String check_addr;
	//核实运营商联系号码结果
	private String check_mobile;
	//核实联系号码是否是小号
	private String check_xiaohao;
	//法院黑名单
	private CourtBlacklist court_blacklist;
	//金融服务类机构黑名单检查
	private FinancialBlacklist financial_blacklist;
	
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getKey_value() {
		return key_value;
	}
	public void setKey_value(String key_value) {
		this.key_value = key_value;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getReliability() {
		return reliability;
	}
	public void setReliability(String reliability) {
		this.reliability = reliability;
	}
	public String getReg_time() {
		return reg_time;
	}
	public void setReg_time(String reg_time) {
		this.reg_time = reg_time;
	}
	public String getCheck_name() {
		return check_name;
	}
	public void setCheck_name(String check_name) {
		this.check_name = check_name;
	}
	public String getCheck_idcard() {
		return check_idcard;
	}
	public void setCheck_idcard(String check_idcard) {
		this.check_idcard = check_idcard;
	}
	public String getCheck_ebusiness() {
		return check_ebusiness;
	}
	public void setCheck_ebusiness(String check_ebusiness) {
		this.check_ebusiness = check_ebusiness;
	}
	public String getCheck_addr() {
		return check_addr;
	}
	public void setCheck_addr(String check_addr) {
		this.check_addr = check_addr;
	}
	public String getCheck_mobile() {
		return check_mobile;
	}
	public void setCheck_mobile(String check_mobile) {
		this.check_mobile = check_mobile;
	}
	public String getCheck_xiaohao() {
		return check_xiaohao;
	}
	public void setCheck_xiaohao(String check_xiaohao) {
		this.check_xiaohao = check_xiaohao;
	}
	public CourtBlacklist getCourt_blacklist() {
		return court_blacklist;
	}
	public void setCourt_blacklist(CourtBlacklist court_blacklist) {
		this.court_blacklist = court_blacklist;
	}
	public FinancialBlacklist getFinancial_blacklist() {
		return financial_blacklist;
	}
	public void setFinancial_blacklist(FinancialBlacklist financial_blacklist) {
		this.financial_blacklist = financial_blacklist;
	}
	
}
