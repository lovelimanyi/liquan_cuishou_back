package com.info.web.pojo;


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * 
 * 类描述：后台用户类 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 上午11:13:16 <br>
 * 
 * @version
 * 
 */
public class BackUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String userAccount;
	private String userPassword;
	private String userName;
	private String userSex;
	private String userAddress;
	private String userTelephone;
	private String userMobile;
	private String userEmail;
	private String userQq;
	private Date createDate;
	private Date updateDate;
	private String addIp;
	private String remark;
	private Integer userStatus;
	private String companyId;//公司编号
	private String companyTitle;//公司名称
	private String groupLevel;//催收组
	private String uuid;
	private String roleId;
	private Integer viewdataStatus;
	private String notMineId;   //需转派订单的催收人
	
	private String[] dataComapnyIDs;//与数据库无关（对应back_user_company_permissions）该用户可查看到的信息
	
	/** 用户状态 */
	public static final HashMap<Integer, String> ALL_STATUS = new HashMap<Integer, String>();
	/** 启用 */
	public static final Integer STATUS_USE = 1;
	/** 删除 */
	public static final Integer STATUS_DELETE = 2;
	static {
		ALL_STATUS.put(STATUS_USE, "启用");
		ALL_STATUS.put(STATUS_DELETE, "删除");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserTelephone() {
		return userTelephone;
	}

	public void setUserTelephone(String userTelephone) {
		this.userTelephone = userTelephone;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserQq() {
		return userQq;
	}

	public void setUserQq(String userQq) {
		this.userQq = userQq;
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

	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getGroupLevel() {
		return groupLevel;
	}

	public void setGroupLevel(String groupLevel) {
		this.groupLevel = groupLevel;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	
	public String[] getDataComapnyIDs() {
		return dataComapnyIDs;
	}

	public void setDataComapnyIDs(String[] dataComapnyIDs) {
		this.dataComapnyIDs = dataComapnyIDs;
	}

	@Override
	public String toString() {
		return "BackUser [addIp=" + addIp + ", createDate=" + createDate
				+ ", id=" + id + ", remark=" + remark + ", userStatus=" + userStatus
				+ ", updateDate=" + updateDate + ", userAccount=" + userAccount
				+ ", userAddress=" + userAddress + ", userEmail=" + userEmail
				+ ", userMobile=" + userMobile + ", userName=" + userName
				+ ", userPassword=" + userPassword + ", userQq=" + userQq
				+ ", userSex=" + userSex + ", userTelephone=" + userTelephone
				+ "]";
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Integer getViewdataStatus() {
		return viewdataStatus;
	}

	public void setViewdataStatus(Integer viewdataStatus) {
		this.viewdataStatus = viewdataStatus;
	}
	public String getNotMineId() {
		return notMineId;
	}

	public void setNotMineId(String notMineId) {
		this.notMineId = notMineId;
	}

	public String getCompanyTitle() {
		return companyTitle;
	}

	public void setCompanyTitle(String companyTitle) {
		this.companyTitle = companyTitle;
	}
	

}
