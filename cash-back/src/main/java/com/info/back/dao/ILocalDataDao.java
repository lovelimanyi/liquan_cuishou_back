package com.info.back.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.info.web.pojo.AuditCenter;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.CreditLoanPay;
import com.info.web.pojo.CreditLoanPayDetail;
import com.info.web.pojo.CreditLoanPaySum;
import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.pojo.MmanLoanCollectionStatusChangeLog;
import com.info.web.pojo.MmanUserLoan;
import com.info.web.pojo.MmanUserRela;
import com.info.web.pojo.SysUserBankCard;
@Repository
public interface ILocalDataDao {
	
	/**
	 * 验证是否重复插入
	 * @param map
	 * @return
	 */
	public int checkLoan(HashMap<String,String> map);
	/**
	 * 验证用户是否存在
	 * @param map
	 * @return
	 */
	public int checkUserInfo(HashMap<String,String> map);
	/**
	 * 验证用户联系人是否存在
	 * @param map
	 * @return
	 */
	public int checkUserRela(HashMap<String,String> map);
	/**
	 * 保存借款表
	 * @param mmanUserLoan
	 */
	public void saveMmanUserLoan(MmanUserLoan mmanUserLoan);
	/**
	 * 更新借款表
	 * @param mmanUserLoan
	 */
	public void updateMmanUserLoan(MmanUserLoan mmanUserLoan);
	/**
	 * 保存还款表
	 * @param creditLoanPay
	 */
	public void saveCreditLoanPay(CreditLoanPay creditLoanPay);
	/**
	 * 更新还款表
	 * @param creditLoanPay
	 */
	public void updateCreditLoanPay(CreditLoanPay creditLoanPay);
	/**
	 * 删除还款详情表
	 * @param creditLoanPayDetail
	 */
	public void delCreditLoanPayDetail(HashMap<String,String> map);
	/**
	 * 从查询还款详情表
	 * @param creditLoanPayDetail
	 */
	public List<String> selectCreditLoanPayDetail(HashMap<String,String> map);
	/**
	 * 查询order
	 * @param map
	 * @return
	 */
	public HashMap<String,Object> selectOrderByDetail(HashMap<String,String> map);
	/**
	 * 保存还款详情表
	 * @param creditLoanPayDetail
	 */
	public void saveCreditLoanPayDetail(CreditLoanPayDetail creditLoanPayDetail);
	/**
	 * 保存用户信息
	 * @param userInfo
	 */
	public void saveMmanUserInfo(HashMap<String,Object> userInfo);
	/**
	 * 保存用户联系人
	 * @param mmanUserRela
	 */
	public void saveMmanUserRela(MmanUserRela mmanUserRela);
	/**
	 * 保存银行卡信息
	 * @param ysUserBankCard
	 */
	public void saveSysUserBankCard(SysUserBankCard ysUserBankCard);
	/**
	 * 更新银行卡
	 * @param ysUserBankCard
	 */
	public void updateSysUserBankCard(SysUserBankCard ysUserBankCard);
	/**
	 * 更新order状态
	 * @param userInfo
	 */
	public void updateOrderStatus(HashMap<String,Object> map);
	/**
	 * 更新代扣记录
	 */
	public void updateWithHold(HashMap<String,String> map);
	/**
	 * 保存流转日志
	 * @param loanChangeLog
	 */
	public void saveLoanChangeLog(MmanLoanCollectionStatusChangeLog loanChangeLog);
	/**
	 * 查询订单状态
	 * @param map
	 * @return
	 */
	public MmanLoanCollectionOrder selectLoanOrder(HashMap<String,Object> map);
	/**
	 * 查询催收员
	 * @param map
	 * @return
	 */
	public BackUser selectBackUser(HashMap<String,Object> map);
	/**
	 * 计算当前payId的还款详情实收本金总额和实收罚息总额
	 * @param payId
	 * @return
	 */
	public CreditLoanPaySum sumRealMoneyAndPenlty(String payId);
	/**
	 * 获取当前还款详情表count
	 * @param payId
	 * @return
	 */
	public int getDetailCount(String payId);
	/**
	 * 根据payId获取审核信息 3-24
	 * @param payId
	 * @return
	 */
	public AuditCenter getAuditCenterByPayId(String payId);
	
	public String checkLoanStatus(HashMap<String, String> map);

	void deleteOrderAndOther(String loanId);

    void saveMmanUserInfo(Map<String, Object> userInfo);

//	boolean checkDianXiaoOrder(String loanId);
	Integer checkDianXiaoOrder(String loanId);

	void updateDianXiaoOrderStatus(String loanId);



}
