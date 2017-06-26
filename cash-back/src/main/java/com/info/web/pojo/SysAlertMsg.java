package com.info.web.pojo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统消息Entity
 * @author wayne
 * @version 2015-11-03
 */
public class SysAlertMsg{

	public static final String TYPE_COMMON = "0";// 普通通知
	public static final String TYPE_TODOTASK = "1";// 待办任务
	public static final String TYPE_CREDIT = "2";// 授信中心通知
	public static final String TYPE_LOAN = "3";// 借款中心通知
	public static final String TYPE_LOAN_GRANT = "4";// 放款通知
	public static final String TYPE_LOAN_OVERDUE = "5";// 逾期通知
	public static final String TYPE_LOAN_RECEIVBLE = "6";// 还款通知

	public static final String TYPE_CREDIT_LOAN_APPLY = "7";// 贷款申请通知
	public static final String TYPE_BUSINESS_DECLARATION = "8";// 业务员报件通知

	private String id;
	private String title; // 标题
	private String content; // 内容
	private String type; // 类型 0:普通消息,1:待办任务,2:授信中心通知,3:借款中心通知,4:放款通知 ,5:逾期通知,6:还款通知 ,7:贷款申请通知 ,8:业务员报件通知
	private String status; // 状态 0--未读 1.已读
	private String dealStatus; // 是否可处理 0--可处理 1--不可处理
	private String hasPush; // 是否已经发送 0--未发送， 1：已发送
	private String userId; // 接收人
	private Date dueDate; // 处理或者查看时间
	private String businessId; // 关联的业务ID
	private String businessNum; // 关联的业务的编号
	private String taskId;// 关联的流程任务Id
    private Date createDate;
    private Date updateDate;
	
	
	private Map<String, Object> variables = new HashMap<String, Object>();


	public SysAlertMsg() {
		super();
	}

	public String getHasPush() {
		return hasPush;
	}

	public void setHasPush(String hasPush) {
		this.hasPush = hasPush;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(String businessNum) {
		this.businessNum = businessNum;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	
	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	

}