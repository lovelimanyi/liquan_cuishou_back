package com.info.web.pojo;


/**
  用户关系表
*/
public class MmanUserRela {
	// 
	private String id;
	// 借款用户id
	private String userId;
	// 联系人类型（1 -直系亲属联系人、2-其他联系人，若为通讯录备份联系人这里不填）
	private String contactsKey;
	// 联系人关系（直系：1 -父亲 、2-母亲、3-儿子、4-女儿、5-配偶；其他：1 -同学、2-亲戚、3-同事、4-朋友、5-其他，若为通讯录备份联系人这里不填）
	private String relaKey;
	// 联系人姓名
	private String infoName;
	// 联系人电话
	private String infoValue;
	// 关联紧急联系人标记，1是0否（为通讯录备份联系人这里不填）
	private String contactsFlag;
	// 删除标记（'1'-已删除，'0'正常）
	private String delFlag;
	// 联系人类型字典值（这里不用）
	private String contactsValue;
	// 联系人关系字典值（这里不用）
	private String relaValue;
	// 这里不用
	private String kvVars;
	// 归属地
	private String phoneNumLoc;
	// 联系次数
	private Integer callCnt;
	// 主叫次数
	private Integer callOutCnt;
	// 被叫次数
	private Integer callInCnt;
	// 联系时间（分）
	private String callLen;
	// 主叫时间（分）
	private String callOutLen;
	// 被叫时间（分）
	private String callInLen;
	
	
	private String realName;

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

	public void setContactsKey(String contactsKey){
		this.contactsKey = contactsKey;
	}

	public String getContactsKey(){
		return this.contactsKey;
	}

	public void setRelaKey(String relaKey){
		this.relaKey = relaKey;
	}

	public String getRelaKey(){
		return this.relaKey;
	}

	public void setInfoName(String infoName){
		this.infoName = infoName;
	}

	public String getInfoName(){
		return this.infoName;
	}

	public void setInfoValue(String infoValue){
		this.infoValue = infoValue;
	}

	public String getInfoValue(){
		return this.infoValue;
	}

	public void setContactsFlag(String contactsFlag){
		this.contactsFlag = contactsFlag;
	}

	public String getContactsFlag(){
		return this.contactsFlag;
	}

	public void setDelFlag(String delFlag){
		this.delFlag = delFlag;
	}

	public String getDelFlag(){
		return this.delFlag;
	}

	public void setContactsValue(String contactsValue){
		this.contactsValue = contactsValue;
	}

	public String getContactsValue(){
		return this.contactsValue;
	}

	public void setRelaValue(String relaValue){
		this.relaValue = relaValue;
	}

	public String getRelaValue(){
		return this.relaValue;
	}

	public void setKvVars(String kvVars){
		this.kvVars = kvVars;
	}

	public String getKvVars(){
		return this.kvVars;
	}

	public void setPhoneNumLoc(String phoneNumLoc){
		this.phoneNumLoc = phoneNumLoc;
	}

	public String getPhoneNumLoc(){
		return this.phoneNumLoc;
	}

	public Integer getCallCnt() {
		return callCnt;
	}

	public void setCallCnt(Integer callCnt) {
		this.callCnt = callCnt;
	}

	public Integer getCallOutCnt() {
		return callOutCnt;
	}

	public void setCallOutCnt(Integer callOutCnt) {
		this.callOutCnt = callOutCnt;
	}

	public Integer getCallInCnt() {
		return callInCnt;
	}

	public void setCallInCnt(Integer callInCnt) {
		this.callInCnt = callInCnt;
	}

	public void setCallLen(String callLen){
		this.callLen = callLen;
	}

	public String getCallLen(){
		return this.callLen;
	}

	public void setCallOutLen(String callOutLen){
		this.callOutLen = callOutLen;
	}

	public String getCallOutLen(){
		return this.callOutLen;
	}

	public void setCallInLen(String callInLen){
		this.callInLen = callInLen;
	}

	public String getCallInLen(){
		return this.callInLen;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
}