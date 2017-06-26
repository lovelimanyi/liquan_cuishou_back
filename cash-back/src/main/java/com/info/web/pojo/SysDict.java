package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
  字典表
*/
public class SysDict {
	// 编号
	private String id;
	// 数据值
	private String value;
	// 标签名
	private String label;
	// 类型
	private String type;
	// 描述
	private String description;
	// 排序（升序）
	private BigDecimal sort;
	// 父级编号
	private String parentId;
	// 创建者
	private String createBy;
	// 创建时间
	private Date createDate;
	// 更新者
	private String updateBy;
	// 更新时间
	private Date updateDate;
	// 备注信息
	private String remarks;
	// 删除标记
	private char delFlag;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return this.value;
	}

	public void setLabel(String label){
		this.label = label;
	}

	public String getLabel(){
		return this.label;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setSort(BigDecimal sort){
		this.sort = sort;
	}

	public BigDecimal getSort(){
		return this.sort;
	}

	public void setParentId(String parentId){
		this.parentId = parentId;
	}

	public String getParentId(){
		return this.parentId;
	}

	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}

	public String getCreateBy(){
		return this.createBy;
	}

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}

	public String getUpdateBy(){
		return this.updateBy;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setRemarks(String remarks){
		this.remarks = remarks;
	}

	public String getRemarks(){
		return this.remarks;
	}

	public char getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(char delFlag) {
		this.delFlag = delFlag;
	}


}