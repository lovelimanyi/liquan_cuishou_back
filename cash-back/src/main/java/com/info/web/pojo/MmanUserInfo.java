package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
  借款人信息表
*/
public class MmanUserInfo {
	// ID
	private String id;
	// 用户名称(手机号码)
	private String userName;
	// 密码
	private String password;
	// 交易密码
	private String payPassword;
	// 真实姓名
	private String realname;
	// 实名认证状态（0、未认证，1、已认证）
	private Integer realnameStatus;
	// 实名认证时间
	private Date realnameTime;
	// 身份证号码
	private String idNumber;
	// 性别
	private String userSex;
	// 年龄
	private Integer userAge;
	// qq
	private String qq;
	// 手机号码
	private String userPhone;
	// 淘宝账号
	private String taobaoAccount;
	// 邮箱
	private String email;
	// 微信账号
	private String wechatAccount;
	// 学历（1博士、2硕士、3本科、4大专、5中专、6高中、7初中及以下）
	private Integer education;
	// 婚姻状况:1未婚,2已婚未育,3,未婚已育,4离异,5其他
	private Integer maritalStatus;
	// 现居地
	private String presentAddress;
	// 现居地详细信息
	private String presentAddressDistinct;
	// 现居地地图的纬度
	private String presentLatitude;
	// 现居地地图的经度
	private String presentLongitude;
	// 居住时长
	private Integer presentPeriod;
	// 公司名称
	private String companyName;
	// 公司地址
	private String companyAddress;
	// 公司详细地址
	private String companyAddressDistinct;
	// 工作的地图经度
	private String companyLongitude;
	// 公司的地图的纬度
	private String companyLatitude;
	// 公司电话
	private String companyPhone;
	// 工作时长
	private Integer companyPeriod;
	// 第一联系人姓名
	private String firstContactName;
	// 第一联系人电话
	private String firstContactPhone;
	// 与第一联系人的关系(1父亲、2母亲、3儿子、4女儿、5配偶)
	private Integer fristContactRelation;
	// 第二联系人姓名
	private String secondContactName;
	// 第二联系人电话
	private String secondContactPhone;
	// 与第二联系人的关系(1.同学2.亲戚3.同事4.朋友5.其他)
	private Integer secondContactRelation;
	// 注册的时间
	private Date createTime;
	// 注册的IP
	private String createIp;
	// 修改的时间
	private Date updateTime;
	// 用户状态(1,正常 2,黑名单)
	private Integer status;
	// 邀请好友
	private Integer inviteUserid;
	// 判断信息是否全部填写完成，如果为1，表示不能修改
	private Integer isSave;
	// 头像地址
	private String headPortrait;
	// 身份证正面
	private String idcardImgZ;
	// 身份证反面
	private String idcardImgF;
	// 是否是老用户：0、新用户；1；老用户
	private Integer customerType;
	// 最小额度(单位分)
	private Integer amountMin;
	// 最大额度(单位分)
	private Integer amountMax;
	// 用户剩余额度
	private Integer amountAvailable;
	// 用户注册的手机设备号
	private String equipmentNumber;
	// 芝麻分
	private BigDecimal zmScore;
	// 芝麻分上次更新时间
	private Date zmScoreTime;
	// 芝麻行业关注度黑名单1.是；2否
	private Integer zmIndustyBlack;
	// 行业关注度接口中返回的借贷逾期记录数AA001借贷逾期的记录数
	private Integer zmOver;
	// 行业关注度接口中返回的逾期未支付记录数，包括AD001 逾期未支付、AE001 逾期未支付的记录总数
	private Integer zmNoPayOver;
	// 行业关注度上次更新时间
	private Date zmIndustyTime;
	// 芝麻信用认证状态1.未认证；2已认证
	private Integer zmStatus;
	// 蚂蚁花呗额度
	private BigDecimal myHb;
	// 蚂蚁花呗额度更新时间
	private Date myHbTime;
	// 聚信立开始采集数据时存入token
	private String jxlToken;
	// 聚信立token入库时间
	private Date jxlTokenTime;
	// 聚信立认证状态,有token就认为认证通过1.未认证；2已认证
	private Integer jxlStatus;
	// 聚信立成功采集后返回的数据
	private String jxlDetail;
	// 聚信立成功采集详情数据的时间
	private Date jxlDetailTime;
	// 聚信立贷款类号码主叫个数
	private Integer jxlZjDkNum;
	// 聚信立贷款类号码被叫个数
	private Integer jxlBjDkNum;
	// 聚信立月均话费
	private BigDecimal jxlYjHf;
	// 聚信立通话详单和用户第二联系人最晚联系日期到目前的天数
	private Integer jxlLink2Days;
	// 聚信立通话详单和用户第一联系人最晚联系日期到目前的天数
	private Integer jxlLink1Days;
	// 聚信立通话详单和用户第二联系人的通话次数
	private Integer jxlLink2Num;
	// 聚信立通话详单和用户第一联系人的通话次数
	private Integer jxlLink1Num;
	// 聚信立第二联系人通话次数排名
	private Integer jxlLink2Order;
	// 聚信立第一联系人通话次数排名
	private Integer jxlLink1Order;
	// 聚信立关机天数，手机静默情况
	private Integer jxlGjTs;
	// 聚信立互通电话数量
	private Integer jxlHtPhoneNum;
	// 聚信立澳门通话次数
	private Integer jxlAmthNum;
	// 聚信立手机开户时间距离当前的天数
	private Integer jxlPhoneRegDays;
	// 聚信立分析数据更新时间
	private Date jxlTime;
	// 用户通讯录的联系人数量
	private Integer userContactSize;
	// 历史逾期总记录数，逾期并还款也纳为逾期记录
	private Integer historyOverNum;
	// 最近一次逾期总天数，逾期并还款也算
	private Integer lastOverDays;
	// 1通过；2拒绝；3无建议
	private Integer csjy;
	// 用户来源0:现金侠,1:360,.......（扩展其它渠道）
	private Integer userFrom;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return this.userName;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setPayPassword(String payPassword){
		this.payPassword = payPassword;
	}

	public String getPayPassword(){
		return this.payPassword;
	}

	public void setRealname(String realname){
		this.realname = realname;
	}

	public String getRealname(){
		return this.realname;
	}

	public void setRealnameStatus(Integer realnameStatus){
		this.realnameStatus = realnameStatus;
	}

	public Integer getRealnameStatus(){
		return this.realnameStatus;
	}

	public void setRealnameTime(Date realnameTime){
		this.realnameTime = realnameTime;
	}

	public Date getRealnameTime(){
		return this.realnameTime;
	}

	public void setIdNumber(String idNumber){
		this.idNumber = idNumber;
	}

	public String getIdNumber(){
		return this.idNumber;
	}

	public void setUserSex(String userSex){
		this.userSex = userSex;
	}

	public String getUserSex(){
		return this.userSex;
	}

	public void setUserAge(Integer userAge){
		this.userAge = userAge;
	}

	public Integer getUserAge(){
		return this.userAge;
	}

	public void setQq(String qq){
		this.qq = qq;
	}

	public String getQq(){
		return this.qq;
	}

	public void setUserPhone(String userPhone){
		this.userPhone = userPhone;
	}

	public String getUserPhone(){
		return this.userPhone;
	}

	public void setTaobaoAccount(String taobaoAccount){
		this.taobaoAccount = taobaoAccount;
	}

	public String getTaobaoAccount(){
		return this.taobaoAccount;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}

	public void setWechatAccount(String wechatAccount){
		this.wechatAccount = wechatAccount;
	}

	public String getWechatAccount(){
		return this.wechatAccount;
	}

	public void setEducation(Integer education){
		this.education = education;
	}

	public Integer getEducation(){
		return this.education;
	}

	public void setMaritalStatus(Integer maritalStatus){
		this.maritalStatus = maritalStatus;
	}

	public Integer getMaritalStatus(){
		return this.maritalStatus;
	}

	public void setPresentAddress(String presentAddress){
		this.presentAddress = presentAddress;
	}

	public String getPresentAddress(){
		return this.presentAddress;
	}

	public void setPresentAddressDistinct(String presentAddressDistinct){
		this.presentAddressDistinct = presentAddressDistinct;
	}

	public String getPresentAddressDistinct(){
		return this.presentAddressDistinct;
	}

	public void setPresentLatitude(String presentLatitude){
		this.presentLatitude = presentLatitude;
	}

	public String getPresentLatitude(){
		return this.presentLatitude;
	}

	public void setPresentLongitude(String presentLongitude){
		this.presentLongitude = presentLongitude;
	}

	public String getPresentLongitude(){
		return this.presentLongitude;
	}

	public void setPresentPeriod(Integer presentPeriod){
		this.presentPeriod = presentPeriod;
	}

	public Integer getPresentPeriod(){
		return this.presentPeriod;
	}

	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}

	public String getCompanyName(){
		return this.companyName;
	}

	public void setCompanyAddress(String companyAddress){
		this.companyAddress = companyAddress;
	}

	public String getCompanyAddress(){
		return this.companyAddress;
	}

	public void setCompanyAddressDistinct(String companyAddressDistinct){
		this.companyAddressDistinct = companyAddressDistinct;
	}

	public String getCompanyAddressDistinct(){
		return this.companyAddressDistinct;
	}

	public void setCompanyLongitude(String companyLongitude){
		this.companyLongitude = companyLongitude;
	}

	public String getCompanyLongitude(){
		return this.companyLongitude;
	}

	public void setCompanyLatitude(String companyLatitude){
		this.companyLatitude = companyLatitude;
	}

	public String getCompanyLatitude(){
		return this.companyLatitude;
	}

	public void setCompanyPhone(String companyPhone){
		this.companyPhone = companyPhone;
	}

	public String getCompanyPhone(){
		return this.companyPhone;
	}

	public void setCompanyPeriod(Integer companyPeriod){
		this.companyPeriod = companyPeriod;
	}

	public Integer getCompanyPeriod(){
		return this.companyPeriod;
	}

	public void setFirstContactName(String firstContactName){
		this.firstContactName = firstContactName;
	}

	public String getFirstContactName(){
		return this.firstContactName;
	}

	public void setFirstContactPhone(String firstContactPhone){
		this.firstContactPhone = firstContactPhone;
	}

	public String getFirstContactPhone(){
		return this.firstContactPhone;
	}

	public void setFristContactRelation(Integer fristContactRelation){
		this.fristContactRelation = fristContactRelation;
	}

	public Integer getFristContactRelation(){
		return this.fristContactRelation;
	}

	public void setSecondContactName(String secondContactName){
		this.secondContactName = secondContactName;
	}

	public String getSecondContactName(){
		return this.secondContactName;
	}

	public void setSecondContactPhone(String secondContactPhone){
		this.secondContactPhone = secondContactPhone;
	}

	public String getSecondContactPhone(){
		return this.secondContactPhone;
	}

	public void setSecondContactRelation(Integer secondContactRelation){
		this.secondContactRelation = secondContactRelation;
	}

	public Integer getSecondContactRelation(){
		return this.secondContactRelation;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return this.createTime;
	}

	public void setCreateIp(String createIp){
		this.createIp = createIp;
	}

	public String getCreateIp(){
		return this.createIp;
	}

	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}

	public Date getUpdateTime(){
		return this.updateTime;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setInviteUserid(Integer inviteUserid){
		this.inviteUserid = inviteUserid;
	}

	public Integer getInviteUserid(){
		return this.inviteUserid;
	}

	public void setIsSave(Integer isSave){
		this.isSave = isSave;
	}

	public Integer getIsSave(){
		return this.isSave;
	}

	public void setHeadPortrait(String headPortrait){
		this.headPortrait = headPortrait;
	}

	public String getHeadPortrait(){
		return this.headPortrait;
	}

	public void setIdcardImgZ(String idcardImgZ){
		this.idcardImgZ = idcardImgZ;
	}

	public String getIdcardImgZ(){
		return this.idcardImgZ;
	}

	public void setIdcardImgF(String idcardImgF){
		this.idcardImgF = idcardImgF;
	}

	public String getIdcardImgF(){
		return this.idcardImgF;
	}

	public void setCustomerType(Integer customerType){
		this.customerType = customerType;
	}

	public Integer getCustomerType(){
		return this.customerType;
	}

	public void setAmountMin(Integer amountMin){
		this.amountMin = amountMin;
	}

	public Integer getAmountMin(){
		return this.amountMin;
	}

	public void setAmountMax(Integer amountMax){
		this.amountMax = amountMax;
	}

	public Integer getAmountMax(){
		return this.amountMax;
	}

	public void setAmountAvailable(Integer amountAvailable){
		this.amountAvailable = amountAvailable;
	}

	public Integer getAmountAvailable(){
		return this.amountAvailable;
	}

	public void setEquipmentNumber(String equipmentNumber){
		this.equipmentNumber = equipmentNumber;
	}

	public String getEquipmentNumber(){
		return this.equipmentNumber;
	}

	public void setZmScore(BigDecimal zmScore){
		this.zmScore = zmScore;
	}

	public BigDecimal getZmScore(){
		return this.zmScore;
	}

	public void setZmScoreTime(Date zmScoreTime){
		this.zmScoreTime = zmScoreTime;
	}

	public Date getZmScoreTime(){
		return this.zmScoreTime;
	}

	public void setZmIndustyBlack(Integer zmIndustyBlack){
		this.zmIndustyBlack = zmIndustyBlack;
	}

	public Integer getZmIndustyBlack(){
		return this.zmIndustyBlack;
	}

	public void setZmOver(Integer zmOver){
		this.zmOver = zmOver;
	}

	public Integer getZmOver(){
		return this.zmOver;
	}

	public void setZmNoPayOver(Integer zmNoPayOver){
		this.zmNoPayOver = zmNoPayOver;
	}

	public Integer getZmNoPayOver(){
		return this.zmNoPayOver;
	}

	public void setZmIndustyTime(Date zmIndustyTime){
		this.zmIndustyTime = zmIndustyTime;
	}

	public Date getZmIndustyTime(){
		return this.zmIndustyTime;
	}

	public void setZmStatus(Integer zmStatus){
		this.zmStatus = zmStatus;
	}

	public Integer getZmStatus(){
		return this.zmStatus;
	}

	public void setMyHb(BigDecimal myHb){
		this.myHb = myHb;
	}

	public BigDecimal getMyHb(){
		return this.myHb;
	}

	public void setMyHbTime(Date myHbTime){
		this.myHbTime = myHbTime;
	}

	public Date getMyHbTime(){
		return this.myHbTime;
	}

	public void setJxlToken(String jxlToken){
		this.jxlToken = jxlToken;
	}

	public String getJxlToken(){
		return this.jxlToken;
	}

	public void setJxlTokenTime(Date jxlTokenTime){
		this.jxlTokenTime = jxlTokenTime;
	}

	public Date getJxlTokenTime(){
		return this.jxlTokenTime;
	}

	public void setJxlStatus(Integer jxlStatus){
		this.jxlStatus = jxlStatus;
	}

	public Integer getJxlStatus(){
		return this.jxlStatus;
	}

	public void setJxlDetail(String jxlDetail){
		this.jxlDetail = jxlDetail;
	}

	public String getJxlDetail(){
		return this.jxlDetail;
	}

	public void setJxlDetailTime(Date jxlDetailTime){
		this.jxlDetailTime = jxlDetailTime;
	}

	public Date getJxlDetailTime(){
		return this.jxlDetailTime;
	}

	public void setJxlZjDkNum(Integer jxlZjDkNum){
		this.jxlZjDkNum = jxlZjDkNum;
	}

	public Integer getJxlZjDkNum(){
		return this.jxlZjDkNum;
	}

	public void setJxlBjDkNum(Integer jxlBjDkNum){
		this.jxlBjDkNum = jxlBjDkNum;
	}

	public Integer getJxlBjDkNum(){
		return this.jxlBjDkNum;
	}

	public void setJxlYjHf(BigDecimal jxlYjHf){
		this.jxlYjHf = jxlYjHf;
	}

	public BigDecimal getJxlYjHf(){
		return this.jxlYjHf;
	}

	public void setJxlLink2Days(Integer jxlLink2Days){
		this.jxlLink2Days = jxlLink2Days;
	}

	public Integer getJxlLink2Days(){
		return this.jxlLink2Days;
	}

	public void setJxlLink1Days(Integer jxlLink1Days){
		this.jxlLink1Days = jxlLink1Days;
	}

	public Integer getJxlLink1Days(){
		return this.jxlLink1Days;
	}

	public void setJxlLink2Num(Integer jxlLink2Num){
		this.jxlLink2Num = jxlLink2Num;
	}

	public Integer getJxlLink2Num(){
		return this.jxlLink2Num;
	}

	public void setJxlLink1Num(Integer jxlLink1Num){
		this.jxlLink1Num = jxlLink1Num;
	}

	public Integer getJxlLink1Num(){
		return this.jxlLink1Num;
	}

	public void setJxlLink2Order(Integer jxlLink2Order){
		this.jxlLink2Order = jxlLink2Order;
	}

	public Integer getJxlLink2Order(){
		return this.jxlLink2Order;
	}

	public void setJxlLink1Order(Integer jxlLink1Order){
		this.jxlLink1Order = jxlLink1Order;
	}

	public Integer getJxlLink1Order(){
		return this.jxlLink1Order;
	}

	public void setJxlGjTs(Integer jxlGjTs){
		this.jxlGjTs = jxlGjTs;
	}

	public Integer getJxlGjTs(){
		return this.jxlGjTs;
	}

	public void setJxlHtPhoneNum(Integer jxlHtPhoneNum){
		this.jxlHtPhoneNum = jxlHtPhoneNum;
	}

	public Integer getJxlHtPhoneNum(){
		return this.jxlHtPhoneNum;
	}

	public void setJxlAmthNum(Integer jxlAmthNum){
		this.jxlAmthNum = jxlAmthNum;
	}

	public Integer getJxlAmthNum(){
		return this.jxlAmthNum;
	}

	public void setJxlPhoneRegDays(Integer jxlPhoneRegDays){
		this.jxlPhoneRegDays = jxlPhoneRegDays;
	}

	public Integer getJxlPhoneRegDays(){
		return this.jxlPhoneRegDays;
	}

	public void setJxlTime(Date jxlTime){
		this.jxlTime = jxlTime;
	}

	public Date getJxlTime(){
		return this.jxlTime;
	}

	public void setUserContactSize(Integer userContactSize){
		this.userContactSize = userContactSize;
	}

	public Integer getUserContactSize(){
		return this.userContactSize;
	}

	public void setHistoryOverNum(Integer historyOverNum){
		this.historyOverNum = historyOverNum;
	}

	public Integer getHistoryOverNum(){
		return this.historyOverNum;
	}

	public void setLastOverDays(Integer lastOverDays){
		this.lastOverDays = lastOverDays;
	}

	public Integer getLastOverDays(){
		return this.lastOverDays;
	}

	public void setCsjy(Integer csjy){
		this.csjy = csjy;
	}

	public Integer getCsjy(){
		return this.csjy;
	}

	public void setUserFrom(Integer userFrom){
		this.userFrom = userFrom;
	}

	public Integer getUserFrom(){
		return this.userFrom;
	}

}