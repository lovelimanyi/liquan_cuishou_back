package com.info.back.vo.jxl2;

import java.util.List;
/**
 * 用户查询信息
 * @author yyf
 *
 * @version 
 */
public class CheckSearchInfo {
	//查询过该用户的相关企业数量
	private Integer searched_org_cnt;
	//查询过该用户的相关企业类型
	private List<String> searched_org_type;
	//身份证组合过的其他姓名
	private List<String> idcard_with_other_names;
	//身份证组合过的其他电话
	private List<String> idcard_with_other_phones;
	//电话号码组合过的其他姓名
	private List<String> phone_with_other_names;
	//电话号码组合过的其他身份证
	private List<String> phone_with_other_idcards;
	//电话号码注册过的相关企业数量
	private Integer register_org_cnt;
	//电话号码注册过的相关企业类型
	private List<String> register_org_type;
	//电话号码出现过的公开网站
	private List<String> arised_open_web;
	
	public List<String> getArised_open_web() {
		return arised_open_web;
	}
	public void setArised_open_web(List<String> arised_open_web) {
		this.arised_open_web = arised_open_web;
	}
	public List<String> getPhone_with_other_idcards() {
		return phone_with_other_idcards;
	}
	public void setPhone_with_other_idcards(List<String> phone_with_other_idcards) {
		this.phone_with_other_idcards = phone_with_other_idcards;
	}
	public List<String> getIdcard_with_other_phones() {
		return idcard_with_other_phones;
	}
	public void setIdcard_with_other_phones(List<String> idcard_with_other_phones) {
		this.idcard_with_other_phones = idcard_with_other_phones;
	}
	public Integer getRegister_org_cnt() {
		return register_org_cnt;
	}
	public void setRegister_org_cnt(Integer register_org_cnt) {
		this.register_org_cnt = register_org_cnt;
	}
	public List<String> getIdcard_with_other_names() {
		return idcard_with_other_names;
	}
	public void setIdcard_with_other_names(List<String> idcard_with_other_names) {
		this.idcard_with_other_names = idcard_with_other_names;
	}
	public List<String> getSearched_org_type() {
		return searched_org_type;
	}
	public void setSearched_org_type(List<String> searched_org_type) {
		this.searched_org_type = searched_org_type;
	}
	public Integer getSearched_org_cnt() {
		return searched_org_cnt;
	}
	public void setSearched_org_cnt(Integer searched_org_cnt) {
		this.searched_org_cnt = searched_org_cnt;
	}
	public List<String> getRegister_org_type() {
		return register_org_type;
	}
	public void setRegister_org_type(List<String> register_org_type) {
		this.register_org_type = register_org_type;
	}
	public List<String> getPhone_with_other_names() {
		return phone_with_other_names;
	}
	public void setPhone_with_other_names(List<String> phone_with_other_names) {
		this.phone_with_other_names = phone_with_other_names;
	}
	
	
}
