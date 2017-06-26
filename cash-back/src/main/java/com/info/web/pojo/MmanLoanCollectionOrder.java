package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
  催收订单表
*/
public class MmanLoanCollectionOrder {
	// 
	private String id;
	// 催收订单id
	private String orderId;
	// 借款用户ID
	private String userId;
	// 借款ID
	private String loanId;
	// 还款ID
	private String payId;
	// 派单人
	private String dispatchName;
	// 派单时间
	private Date dispatchTime;
	// 上一催收员ID
	private String lastCollectionUserId;
	// 当前催收员ID
	private String currentCollectionUserId;
	// 当前逾期等级（即逾期字典分组）
	private String currentOverdueLevel;
	// 逾期天数
	private Integer overdueDays;
	// s1审批人ID
	private String m1ApproveId;
	// s1审批人操作状态（1，已操作过，0或null未操作过）
	private String m1OperateStatus;
	// s2审批人ID
	private String m2ApproveId;
	// s2审批人操作状态（1，已操作过，0或null未操作过）
	private String m2OperateStatus;
	// m1-m2审批人ID
	private String m3ApproveId;
	// m1-m2审批人操作状态（1，已操作过，0或null未操作过）
	private String m3OperateStatus;
	// m2-m3审批人ID
	private String m4ApproveId;
	// m2-m3审批人操作状态（1，已操作过，0或null未操作过）
	private String m4OperateStatus;
	// m3+审批人ID
	private String m5ApproveId;
	// m3+审批人操作状态（1，已操作过，0或null未操作过）
	private String m5OperateStatus;
	// m6审批人ID
	private String m6ApproveId;
	// 催收状态
	private String status;
	// 承诺还款时间
	private Date promiseRepaymentTime;
	// 最后催收时间
	private Date lastCollectionTime;
	// 创建时间
	private Date createDate;
	// 更新时间
	private Date updateDate;
	// 操作人
	private String operatorName;
	// 备注
	private String remark;
	// 委外催收人id
	private String outsidePersonId;
	// 委外前催收状态
	private String beforeStatus;
	// 委外机构ID
	private String outsideCompanyId;
	// 聚立信息报告审核 0 初始 1 申请 2同意 ，3拒绝
	private String jxlStatus;
	// 是否进行续借出催 1启用 0不启用
	private String renewStatus;
	// 聚信立 报告申请时间
	private Date jxlApplyTime;
	// 聚信立报告审核时间
	private Date jxAuditIme;
	// 催收建议状态 1申请 2 同意 3拒绝
	private String csstatus;

	//减免滞纳金金额
	private BigDecimal reductionMoney;

	public BigDecimal getReductionMoney() {
		return reductionMoney;
	}

	public void setReductionMoney(BigDecimal reductionMoney) {
		this.reductionMoney = reductionMoney;
	}

	//用户信息
	private MmanUserInfo mmanUserInfo;
	
	//用户借款表
	private MmanUserLoan mmanUserLoan;
	
	//用户关系表
	private List<MmanUserRela> mmanUserRela;
	
	//当前催收员
	private MmanLoanCollectionPerson mmanLoanCollectionPerson;
	
	//上一个催收员
	private MmanLoanCollectionPerson mmanLoanCollectionPerson1;
	
	//催收记录表
	private List<MmanLoanCollectionRecord> mmanLoanCollectionRecord;
	
	//流转日志
	private List<MmanLoanCollectionStatusChangeLog> mmanLoanCollectionStatusChangeLog;
	
	//还款信息
	private CreditLoanPay creditLoanPay;
	
	//还款详情
	private List<CreditLoanPayDetail> creditLoanPayDetail;
	
	private String loanUserName;
	
	private String loanUserPhone;
	
	private BigDecimal realMoney;
	private String s1Flag;

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

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setLoanId(String loanId){
		this.loanId = loanId;
	}

	public String getLoanId(){
		return this.loanId;
	}

	public void setPayId(String payId){
		this.payId = payId;
	}

	public String getPayId(){
		return this.payId;
	}

	public void setDispatchName(String dispatchName){
		this.dispatchName = dispatchName;
	}

	public String getDispatchName(){
		return this.dispatchName;
	}

	public void setDispatchTime(Date dispatchTime){
		this.dispatchTime = dispatchTime;
	}

	public Date getDispatchTime(){
		return this.dispatchTime;
	}

	public void setLastCollectionUserId(String lastCollectionUserId){
		this.lastCollectionUserId = lastCollectionUserId;
	}

	public String getLastCollectionUserId(){
		return this.lastCollectionUserId;
	}

	public void setCurrentCollectionUserId(String currentCollectionUserId){
		this.currentCollectionUserId = currentCollectionUserId;
	}

	public String getCurrentCollectionUserId(){
		return this.currentCollectionUserId;
	}

	public void setCurrentOverdueLevel(String currentOverdueLevel){
		this.currentOverdueLevel = currentOverdueLevel;
	}

	public String getCurrentOverdueLevel(){
		return this.currentOverdueLevel;
	}

	public void setOverdueDays(Integer overdueDays){
		this.overdueDays = overdueDays;
	}

	public Integer getOverdueDays(){
		return this.overdueDays;
	}

	public void setM1ApproveId(String m1ApproveId){
		this.m1ApproveId = m1ApproveId;
	}

	public String getM1ApproveId(){
		return this.m1ApproveId;
	}

	public void setM1OperateStatus(String m1OperateStatus){
		this.m1OperateStatus = m1OperateStatus;
	}

	public String getM1OperateStatus(){
		return this.m1OperateStatus;
	}

	public void setM2ApproveId(String m2ApproveId){
		this.m2ApproveId = m2ApproveId;
	}

	public String getM2ApproveId(){
		return this.m2ApproveId;
	}

	public void setM2OperateStatus(String m2OperateStatus){
		this.m2OperateStatus = m2OperateStatus;
	}

	public String getM2OperateStatus(){
		return this.m2OperateStatus;
	}

	public void setM3ApproveId(String m3ApproveId){
		this.m3ApproveId = m3ApproveId;
	}

	public String getM3ApproveId(){
		return this.m3ApproveId;
	}

	public void setM3OperateStatus(String m3OperateStatus){
		this.m3OperateStatus = m3OperateStatus;
	}

	public String getM3OperateStatus(){
		return this.m3OperateStatus;
	}

	public void setM4ApproveId(String m4ApproveId){
		this.m4ApproveId = m4ApproveId;
	}

	public String getM4ApproveId(){
		return this.m4ApproveId;
	}

	public void setM4OperateStatus(String m4OperateStatus){
		this.m4OperateStatus = m4OperateStatus;
	}

	public String getM4OperateStatus(){
		return this.m4OperateStatus;
	}

	public void setM5ApproveId(String m5ApproveId){
		this.m5ApproveId = m5ApproveId;
	}

	public String getM5ApproveId(){
		return this.m5ApproveId;
	}

	public void setM5OperateStatus(String m5OperateStatus){
		this.m5OperateStatus = m5OperateStatus;
	}

	public String getM5OperateStatus(){
		return this.m5OperateStatus;
	}

	public void setM6ApproveId(String m6ApproveId){
		this.m6ApproveId = m6ApproveId;
	}

	public String getM6ApproveId(){
		return this.m6ApproveId;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return this.status;
	}

	public void setPromiseRepaymentTime(Date promiseRepaymentTime){
		this.promiseRepaymentTime = promiseRepaymentTime;
	}

	public Date getPromiseRepaymentTime(){
		return this.promiseRepaymentTime;
	}

	public void setLastCollectionTime(Date lastCollectionTime){
		this.lastCollectionTime = lastCollectionTime;
	}

	public Date getLastCollectionTime(){
		return this.lastCollectionTime;
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

	public void setOperatorName(String operatorName){
		this.operatorName = operatorName;
	}

	public String getOperatorName(){
		return this.operatorName;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setOutsidePersonId(String outsidePersonId){
		this.outsidePersonId = outsidePersonId;
	}

	public String getOutsidePersonId(){
		return this.outsidePersonId;
	}

	public void setBeforeStatus(String beforeStatus){
		this.beforeStatus = beforeStatus;
	}

	public String getBeforeStatus(){
		return this.beforeStatus;
	}

	public void setOutsideCompanyId(String outsideCompanyId){
		this.outsideCompanyId = outsideCompanyId;
	}

	public String getOutsideCompanyId(){
		return this.outsideCompanyId;
	}

	public void setJxlStatus(String jxlStatus){
		this.jxlStatus = jxlStatus;
	}

	public String getJxlStatus(){
		return this.jxlStatus;
	}

	public void setRenewStatus(String renewStatus){
		this.renewStatus = renewStatus;
	}

	public String getRenewStatus(){
		return this.renewStatus;
	}

	public void setJxlApplyTime(Date jxlApplyTime){
		this.jxlApplyTime = jxlApplyTime;
	}

	public Date getJxlApplyTime(){
		return this.jxlApplyTime;
	}

	public void setJxAuditIme(Date jxAuditIme){
		this.jxAuditIme = jxAuditIme;
	}

	public Date getJxAuditIme(){
		return this.jxAuditIme;
	}

	public void setCsstatus(String csstatus){
		this.csstatus = csstatus;
	}

	public String getCsstatus(){
		return this.csstatus;
	}

	public MmanUserInfo getMmanUserInfo() {
		return mmanUserInfo;
	}

	public void setMmanUserInfo(MmanUserInfo mmanUserInfo) {
		this.mmanUserInfo = mmanUserInfo;
	}

	public MmanUserLoan getMmanUserLoan() {
		return mmanUserLoan;
	}

	public void setMmanUserLoan(MmanUserLoan mmanUserLoan) {
		this.mmanUserLoan = mmanUserLoan;
	}

	public List<MmanUserRela> getMmanUserRela() {
		return mmanUserRela;
	}

	public void setMmanUserRela(List<MmanUserRela> mmanUserRela) {
		this.mmanUserRela = mmanUserRela;
	}

	public MmanLoanCollectionPerson getMmanLoanCollectionPerson() {
		return mmanLoanCollectionPerson;
	}

	public void setMmanLoanCollectionPerson(MmanLoanCollectionPerson mmanLoanCollectionPerson) {
		this.mmanLoanCollectionPerson = mmanLoanCollectionPerson;
	}

	public MmanLoanCollectionPerson getMmanLoanCollectionPerson1() {
		return mmanLoanCollectionPerson1;
	}

	public void setMmanLoanCollectionPerson1(MmanLoanCollectionPerson mmanLoanCollectionPerson1) {
		this.mmanLoanCollectionPerson1 = mmanLoanCollectionPerson1;
	}

	public List<MmanLoanCollectionRecord> getMmanLoanCollectionRecord() {
		return mmanLoanCollectionRecord;
	}

	public void setMmanLoanCollectionRecord(List<MmanLoanCollectionRecord> mmanLoanCollectionRecord) {
		this.mmanLoanCollectionRecord = mmanLoanCollectionRecord;
	}

	public List<MmanLoanCollectionStatusChangeLog> getMmanLoanCollectionStatusChangeLog() {
		return mmanLoanCollectionStatusChangeLog;
	}

	public void setMmanLoanCollectionStatusChangeLog(
			List<MmanLoanCollectionStatusChangeLog> mmanLoanCollectionStatusChangeLog) {
		this.mmanLoanCollectionStatusChangeLog = mmanLoanCollectionStatusChangeLog;
	}

	public CreditLoanPay getCreditLoanPay() {
		return creditLoanPay;
	}

	public void setCreditLoanPay(CreditLoanPay creditLoanPay) {
		this.creditLoanPay = creditLoanPay;
	}

	public List<CreditLoanPayDetail> getCreditLoanPayDetail() {
		return creditLoanPayDetail;
	}

	public void setCreditLoanPayDetail(List<CreditLoanPayDetail> creditLoanPayDetail) {
		this.creditLoanPayDetail = creditLoanPayDetail;
	}

	public String getS1Flag() {
		return s1Flag;
	}

	public void setS1Flag(String s1Flag) {
		this.s1Flag = s1Flag;
	}

	public String getLoanUserName() {
		return loanUserName;
	}

	public void setLoanUserName(String loanUserName) {
		this.loanUserName = loanUserName;
	}

	public String getLoanUserPhone() {
		return loanUserPhone;
	}

	public void setLoanUserPhone(String loanUserPhone) {
		this.loanUserPhone = loanUserPhone;
	}

	public BigDecimal getRealMoney() {
		return realMoney;
	}

	public void setRealMoney(BigDecimal realMoney) {
		this.realMoney = realMoney;
	}

	
}