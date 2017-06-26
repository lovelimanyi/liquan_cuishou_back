package com.info.web.pojo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.info.web.util.encrypt.MD5coding;

public class User {

	private Integer id;
	private String userAccount;
	private String encryptUserAccount;

	public String getEncryptUserAccount() {
		return encryptUserAccount;
	}

	public void setEncryptUserAccount(String encryptUserAccount) {
		this.encryptUserAccount = encryptUserAccount;
	}

	private String userPassword;
	private String userPaypassword;
	private Integer userType;
	private String userName;
	private String userSex;
	private String userAge;
	private String userProvince;
	private String userCity;
	private String userArea;
	private String userAddress;
	private String userTelephone;
	private String userMobile;
	private String userEmail;
	private String userQq;
	private String userCardNum;
	private Integer userCartType;
	private Integer nameStatus;
	private Integer phoneStatus;
	private Integer emailStatus;
	private Integer imgStatus;
	private Date createDate;
	private Date updateDate;
	private String registIp;
	private String remark;
	private String encryptUserName;
	private String encryptEmail;
	private String encryptCardNum;
	private String encryptTelephone;
	private String avatarImg;
	private Date loginTime;
	private String loginIp;
	private String encryptMobile;

	public String getEncryptMobile() {
		return encryptMobile;
	}

	public void setEncryptMobile(String encryptMobile) {
		this.encryptMobile = encryptMobile;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getAvatarImg() {
		return avatarImg;
	}

	public void setAvatarImg(String avatarImg) {
		this.avatarImg = avatarImg;
	}

	public String getEncryptUserName() {
		return encryptUserName;
	}

	public void setEncryptUserName(String encryptUserName) {
		this.encryptUserName = encryptUserName;
	}

	public String getEncryptEmail() {
		return encryptEmail;
	}

	public void setEncryptEmail(String encryptEmail) {
		this.encryptEmail = encryptEmail;
	}

	public String getEncryptCardNum() {
		return encryptCardNum;
	}

	public void setEncryptCardNum(String encryptCardNum) {
		this.encryptCardNum = encryptCardNum;
	}

	public String getEncryptTelephone() {
		return encryptTelephone;
	}

	public void setEncryptTelephone(String encryptTelephone) {
		this.encryptTelephone = encryptTelephone;
	}

	/** * 实名认证状态 */
	public static final Map<Integer, String> NAME_STATUS = new HashMap<Integer, String>();
	/** 未申请认证 */
	public static final Integer NAME_DEFAULT = 0;
	/** 未申请认证 */
	public static final Integer NAME_APPLY = 1;
	/** 已认证 */
	public static final Integer NAME_PASS = 2;
	/** 认证失败 */
	public static final Integer NAME_FAIL = 3;

	/** 手机认证状态 */
	public static final Map<Integer, String> PHONE_STATUS = new HashMap<Integer, String>();
	/** 未申请认证 */
	public static final Integer PHONE_DEFAULT = 0;
	/** 申请中 */
	public static final Integer PHONE_APPLY = 1;
	/** 已认证 */
	public static final Integer PHONE_PASS = 2;
	/** 认证失败 */
	public static final Integer PHONE_FAIL = 3;

	/** 邮箱认证状态 */
	public static final Map<Integer, String> EMAIL_STATUS = new HashMap<Integer, String>();
	/** 未申请认证 */
	public static final Integer EMAIL_DEFAULT = 0;
	/** 申请中 */
	public static final Integer EMAIL_APPLY = 1;
	/** 已认证 */
	public static final Integer EMAIL_PASS = 2;
	/** 认证失败 */
	public static final Integer EMAIL_FAIL = 3;
	/*** 图片认证状态 */
	public static final Map<Integer, String> IMAGE_STATUS = new HashMap<Integer, String>();
	/** 未申请认证 */
	public static final Integer IMAGE_DEFAULT = 0;
	/** 申请中 */
	public static final Integer IMAGE_APPLY = 1;
	/** 已认证 */
	public static final Integer IMAGE_PASS = 2;
	/** 认证失败 */
	public static final Integer IMAGE_FAIL = 3;
	/** 普通、正常状态 */
	public static final Integer STATUS_NORMAL = 1;
	/** 黑名单 */
	public static final Integer STATUS_BLACK = 2;
	/*** 认证的证件类型状态 */
	public static final Map<Integer, String> CARD_TYPE = new HashMap<Integer, String>();
	/** 身份证 */
	public static final Integer CARD_ID_NUM = 1;
	public static final Map<Integer, String> ALL_STATUS = new HashMap<Integer, String>();
	static {
		ALL_STATUS.put(STATUS_NORMAL, "正常");
		ALL_STATUS.put(STATUS_BLACK, "黑名单");

		NAME_STATUS.put(NAME_DEFAULT, "未申请");
		NAME_STATUS.put(NAME_APPLY, "申请中");
		NAME_STATUS.put(NAME_PASS, "通过");
		NAME_STATUS.put(NAME_FAIL, "未通过");

		PHONE_STATUS.put(PHONE_DEFAULT, "未申请");
		PHONE_STATUS.put(PHONE_APPLY, "申请中");
		PHONE_STATUS.put(PHONE_PASS, "通过");
		PHONE_STATUS.put(PHONE_FAIL, "未通过");

		EMAIL_STATUS.put(EMAIL_DEFAULT, "未申请");
		EMAIL_STATUS.put(EMAIL_APPLY, "申请中");
		EMAIL_STATUS.put(EMAIL_PASS, "通过");
		EMAIL_STATUS.put(EMAIL_FAIL, "未通过");

		IMAGE_STATUS.put(IMAGE_DEFAULT, "未申请");
		IMAGE_STATUS.put(IMAGE_APPLY, "申请中");
		IMAGE_STATUS.put(IMAGE_PASS, "通过");
		IMAGE_STATUS.put(IMAGE_FAIL, "未通过");

		CARD_TYPE.put(CARD_ID_NUM, "身份证");
	}
	/** 1.正常，2.黑名单 */
	private Integer status;
	private String statusView;

	public String getStatusView() {
		return statusView;
	}

	public void setStatusView(String statusView) {
		this.statusView = statusView;
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
		setEncryptUserAccount(MD5coding.getInstance().encryptImportMsg(
				userAccount, 5, null));
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPaypassword() {
		return userPaypassword;
	}

	public void setUserPaypassword(String userPaypassword) {
		this.userPaypassword = userPaypassword;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
		if (StringUtils.isNotBlank(userName)) {
			setEncryptUserName(MD5coding.getInstance().encryptImportMsg(
					userName, userName.length() - 1, "end"));
		}
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserAge() {
		return userAge;
	}

	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}

	public String getUserProvince() {
		return userProvince;
	}

	public void setUserProvince(String userProvince) {
		this.userProvince = userProvince;
	}

	public String getUserCity() {
		return userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public String getUserArea() {
		return userArea;
	}

	public void setUserArea(String userArea) {
		this.userArea = userArea;
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
		this.userTelephone = (userTelephone == null || "".equals(userTelephone)) ? null
				: userTelephone.trim();
		setEncryptTelephone(MD5coding.getInstance().encryptImportMsg(
				userTelephone, 5, null));
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = (userMobile == null || "".equals(userMobile)) ? null
				: userMobile.trim();
		setEncryptMobile(MD5coding.getInstance().encryptImportMsg(userMobile,
				5, null));
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = (userEmail == null || "".equals(userEmail)) ? null
				: userEmail.trim();
		setEncryptEmail(MD5coding.getInstance().encryptImportMsg(userEmail, 5,
				"begin"));
	}

	public String getUserQq() {
		return userQq;
	}

	public void setUserQq(String userQq) {
		this.userQq = userQq;
	}

	public String getUserCardNum() {
		return userCardNum;
	}

	public void setUserCardNum(String userCardNum) {
		this.userCardNum = (userCardNum == null || "".equals(userCardNum)) ? null
				: userCardNum.trim();
		setEncryptCardNum(MD5coding.getInstance().encryptImportMsg(userCardNum,
				10, null));
	}

	public Integer getUserCartType() {
		return userCartType;
	}

	public void setUserCartType(Integer userCartType) {
		this.userCartType = userCartType;
	}

	public Integer getNameStatus() {
		return nameStatus;
	}

	public void setNameStatus(Integer nameStatus) {
		this.nameStatus = nameStatus;
	}

	public Integer getPhoneStatus() {
		return phoneStatus;
	}

	public void setPhoneStatus(Integer phoneStatus) {
		this.phoneStatus = phoneStatus;
	}

	public Integer getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(Integer emailStatus) {
		this.emailStatus = emailStatus;
	}

	public Integer getImgStatus() {
		return imgStatus;
	}

	public void setImgStatus(Integer imgStatus) {
		this.imgStatus = imgStatus;
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

	public String getRegistIp() {
		return registIp;
	}

	public void setRegistIp(String registIp) {
		this.registIp = registIp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
		setStatusView(ALL_STATUS.get(status));
	}

}
