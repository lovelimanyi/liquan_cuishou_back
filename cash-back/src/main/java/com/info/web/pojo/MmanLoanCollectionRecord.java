package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
  催收记录表
*/
public class MmanLoanCollectionRecord {
	// id
	private String id;
	// 催收订单ID（借款编号）
	private String orderId;
	// 催收员ID
	private String collectionId;
	// 借款人ID
	private String userId;
	// 联系人类型 1: 紧急联系人 2:通讯录联系人
	private String contactType;
	// 联系人姓名
	private String contactName;
	// 联系人关系
	private String relation;
	// 联系人电话
	private String contactPhone;
	// 施压等级(施压等级一、二、三 字典)
	private String stressLevel;
	// 当前催收状态 0待催收、1催收中、2承诺还款、3委外中、4委外成功、5催收成功 字典
	private String orderState;
	// 催收类型( 1电话催收、2短信催收（可点击发短信）字典)
	private String collectionType;
	// 催收时间
	private Date collectionDate;
	// 催收组
	private String collectionGroup;
	// 催收员
	private String collectionPerson;
	// 催收到的金额
	private BigDecimal collectionAmount;
	// 催收内容
	private String content;
	// 创建时间
	private Date createDate;
	// 更新时间
	private Date updateDate;
	// 备注（以备催收人员查阅）
	private String remark;


	private String companyTitle; // 公司名称
	private String collectionAdvice; //催收建议
	private String fengKongLabel; //风控标签
	private String collectionAdviceRemark; // 催收建议备注

	public String getCollectionAdviceRemark() {
		return collectionAdviceRemark;
	}

	public void setCollectionAdviceRemark(String collectionAdviceRemark) {
		this.collectionAdviceRemark = collectionAdviceRemark;
	}
	public String getCompanyTitle() {
		return companyTitle;
	}

	public void setCompanyTitle(String companyTitle) {
		this.companyTitle = companyTitle;
	}

	public String getCollectionAdvice() {
		return collectionAdvice;
	}

	public void setCollectionAdvice(String collectionAdvice) {
		this.collectionAdvice = collectionAdvice;
	}

	public String getFengKongLabel() {
		return fengKongLabel;
	}

	public void setFengKongLabel(String fengKongLabel) {
		this.fengKongLabel = fengKongLabel;
	}

	public String getCollectionGroup() {
		return collectionGroup;
	}

	public void setCollectionGroup(String collectionGroup) {
		this.collectionGroup = collectionGroup;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return this.orderId;
	}

	public void setCollectionId(String collectionId){
		this.collectionId = collectionId;
	}

	public String getCollectionId(){
		return this.collectionId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setContactType(String contactType){
		this.contactType = contactType;
	}

	public String getContactType(){
		return this.contactType;
	}

	public void setContactName(String contactName){
		this.contactName = contactName;
	}

	public String getContactName(){
		return this.contactName;
	}

	public void setRelation(String relation){
		this.relation = relation;
	}

	public String getRelation(){
		return this.relation;
	}

	public void setContactPhone(String contactPhone){
		this.contactPhone = contactPhone;
	}

	public String getContactPhone(){
		return this.contactPhone;
	}

	public void setStressLevel(String stressLevel){
		this.stressLevel = stressLevel;
	}

	public String getStressLevel(){
		return this.stressLevel;
	}

	public void setOrderState(String orderState){
		this.orderState = orderState;
	}

	public String getOrderState(){
		return this.orderState;
	}

	public void setCollectionType(String collectionType){
		this.collectionType = collectionType;
	}

	public String getCollectionType(){
		return this.collectionType;
	}

	public void setCollectionDate(Date collectionDate){
		this.collectionDate = collectionDate;
	}

	public Date getCollectionDate(){
		return this.collectionDate;
	}

	public void setCollectionAmount(BigDecimal collectionAmount){
		this.collectionAmount = collectionAmount;
	}

	public BigDecimal getCollectionAmount(){
		return this.collectionAmount;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public String getCollectionPerson() {
		return collectionPerson;
	}

	public void setCollectionPerson(String collectionPerson) {
		this.collectionPerson = collectionPerson;
	}

}