package com.info.web.pojo;


/**
  催收规则分配表，和催收员表中数据一并显示，关联催收公司表
*/
public class MmanLoanCollectionRule {
	// 
	private String id;
	// 催收公司id
	private String companyId;
	// 催收组
	private String collectionGroup;
	// 每人每天单数上限(单数平均分配，0代表无上限)
	private Integer everyLimit;
	//催收公司名称 companyName
	private String companyName;
	//当前人数
	private String personCount;
	

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}

	public String getCompanyId(){
		return this.companyId;
	}

	
	public String getCollectionGroup() {
		return collectionGroup;
	}

	public void setCollectionGroup(String collectionGroup) {
		this.collectionGroup = collectionGroup;
	}

	public void setEveryLimit(Integer everyLimit){
		this.everyLimit = everyLimit;
	}

	public Integer getEveryLimit(){
		return this.everyLimit;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPersonCount() {
		return personCount;
	}

	public void setPersonCount(String personCount) {
		this.personCount = personCount;
	}
	

}