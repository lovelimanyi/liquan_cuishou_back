package com.info.web.pojo;

import java.util.Date;

/**
  催收机构（公司、本公司不可删除）
*/
public class MmanLoanCollectionCompany {
	// 
	private String id;
	// 催单公司名称
	private String title;
	// 创建时间
	private Date createDate;
	// 优先级，优先级越高，优先分配订单（暂时不提供）
	private String priority;
	// 状态，1启用，0禁用
	private String status;
	// 是否是自营团队，1是，0不是
	private String selfBusiness;
	//修改时间
	private Date updateDate;
	//地区
	private String region;
	//人数 （与数据库无关）
	private Integer peopleCount;
	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setPriority(String priority){
		this.priority = priority;
	}

	public String getPriority(){
		return this.priority;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return this.status;
	}

	public void setSelfBusiness(String selfBusiness){
		this.selfBusiness = selfBusiness;
	}

	public String getSelfBusiness(){
		return this.selfBusiness;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Integer getPeopleCount() {
		return peopleCount;
	}

	public void setPeopleCount(Integer peopleCount) {
		this.peopleCount = peopleCount;
	}
	
}