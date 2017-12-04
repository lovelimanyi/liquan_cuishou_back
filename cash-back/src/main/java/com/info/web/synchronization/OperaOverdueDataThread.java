package com.info.web.synchronization;

import com.info.back.dao.ILocalDataDao;
import com.info.back.service.TaskJobMiddleService;
import com.info.back.utils.IdGen;
import com.info.back.vo.jxl.ContactList;
import com.info.constant.Constant;
import com.info.web.pojo.*;
import com.info.web.synchronization.dao.IDataDao;
import com.info.web.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
/**
 * 处理逾期数据
 * @author gaoyuhai
 *
 */
public class OperaOverdueDataThread implements Runnable {

	private static Logger loger = Logger.getLogger(OperaOverdueDataThread.class);
	private String payId;
	private IDataDao dataDao;
	private ILocalDataDao localDataDao;
	private TaskJobMiddleService taskJobMiddleService;

	public OperaOverdueDataThread(String payId, IDataDao dataDao,ILocalDataDao localDataDao,TaskJobMiddleService taskJobMiddleService) {
		this.payId = payId;
		this.dataDao = dataDao;
		this.localDataDao = localDataDao;
		this.taskJobMiddleService = taskJobMiddleService;
	}

	public OperaOverdueDataThread() {
	}

	@Override
	public void run() {
		if (StringUtils.isNotBlank(payId)) {
			loger.error("sync-OperaOverdueDataThread:"+payId);
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("ID", payId);//还款id
			//还款信息--app端
			HashMap<String,Object> repayment = this.dataDao.getAssetRepayment(map);
//			loger.info("sync-borrowOrders:"+repayment);
			String loanId = String.valueOf(repayment.get("asset_order_id"));//借款id
			String userId  = String.valueOf(repayment.get("user_id"));    	//用户id
			map.put("ORDER_ID", loanId);
			map.put("USER_ID", userId);
			String repaymentMoney =String.valueOf(repayment.get("repayment_amount"));
			String repaymentedMoney = String.valueOf(repayment.get("repaymented_amount"));
			loger.error("repaymentMoney===="+repaymentMoney+"repaymentedMoney==="+repaymentedMoney);
			if (null != repayment &&(!repaymentMoney.equals(repaymentedMoney))) {
				loger.error("第二次repaymentMoney===="+repaymentMoney+"repaymentedMoney==="+repaymentedMoney);
				try {
					HashMap<String, Object> borrowOrder = null;					//借款信息--app端
					List<HashMap<String, Object>> repaymentDetailList = null;	//还款详情信息--app端
					HashMap<String, Object> userInfo = null;					//用户信息--app端
					HashMap<String, Object> cardInfo = null;					//银行卡--app端
					List<HashMap<String, Object>> userContactsList = null;		//用户联系人--app端

					borrowOrder = this.dataDao.getAssetBorrowOrder(map);
					loger.info("sync-borrowOrder:"+borrowOrder);
					repaymentDetailList = this.dataDao.getAssetRepaymentDetail(map);
					loger.info("sync-repaymentDetailList:"+repaymentDetailList);
					loger.info("开始:"+borrowOrder);
					if (checkLoan(loanId)) {
						userInfo = this.dataDao.getUserInfo(map);
						cardInfo = this.dataDao.getUserCardInfo(map);
						userContactsList = this.dataDao.getUserContacts(map);
						loger.info("loanId true:"+loanId);
						if (null != userInfo && null != borrowOrder&& null != cardInfo&& null != repaymentDetailList) {
							//保存用户借款表
							loger.info("保存用户借款表 start:");
							saveMmanUserLoan(borrowOrder,repayment,userInfo);
							loger.info("saveMmanUserLoan end:");
							//保存还款表
							saveCreditLoanPay(repayment);
							loger.info("保存还款表");
							//保存还款详情表
							syncUtils.saveCreditLoanPayDetail(localDataDao,repayment,payId, repaymentDetailList);
							loger.info("保存还款详情表");
							//保存用户信息表--联系人表--银行卡
							saveUserInfo(userId,userInfo,userContactsList,cardInfo);
						}
						this.taskJobMiddleService.dispatchforLoanId(loanId,userInfo.get("id_number").toString());
						RedisUtil.delRedisKey(Constant.TYPE_OVERDUE_ + payId);
					} else {
						loger.info("loanId:"+loanId);
						if (null != borrowOrder && null != repaymentDetailList) {
							//更新用户借款表
							loger.info("更新用户借款表");
							syncUtils.updateMmanUserLoan(localDataDao,loanId, repayment,Constant.STATUS_OVERDUE_FOUR);
							//更新还款表
							loger.info("更新还款表");
							syncUtils.updateCreditLoanPay(localDataDao,payId,repayment);
							//保存还款详情表
							loger.info("保存还款详情表");
							syncUtils.saveCreditLoanPayDetail(localDataDao,repayment,payId, repaymentDetailList);
							loger.info("更新订单表");
							//更新订单表
							syncUtils.updateMmanLoanCollectionOrder(localDataDao,loanId,repayment,Constant.STATUS_OVERDUE_ONE);
						}
//						//验证是否减免
//						syncUtils.checkReduction(repayment,localDataDao);
						RedisUtil.delRedisKey(Constant.TYPE_OVERDUE_ + payId);
					}
				} catch (Exception e0) {
					loger.error("OperaOverdueDataThread-异常-loanId"+loanId);
					e0.printStackTrace();
				}
			}else{
                loger.error("del-key===="+payId);
				RedisUtil.delRedisKey(Constant.TYPE_OVERDUE_ + payId);
			}
		}
	}

	/**
	 * 保存用户借款表
	 * @param repaymentMap  还款信息
	 * @param borrowOrder  借款信息
	 * */
	public  void saveMmanUserLoan(HashMap<String,Object> borrowOrder,HashMap<String,Object> repaymentMap,HashMap<String,Object> userInfo){
		loger.info("start-saveMmanUserLoan:"+String.valueOf(borrowOrder.get("id")));
		MmanUserLoan mmanUserLoan = new MmanUserLoan();
		mmanUserLoan.setId(String.valueOf(borrowOrder.get("id")));
		mmanUserLoan.setUserId(String.valueOf(borrowOrder.get("user_id")));
		mmanUserLoan.setLoanPyId(String.valueOf(borrowOrder.get("out_trade_no")));//第三方订单号
		mmanUserLoan.setLoanMoney(new BigDecimal(Integer.parseInt(String.valueOf(borrowOrder.get("money_amount")))/100.00));
		mmanUserLoan.setLoanRate(String.valueOf(borrowOrder.get("apr")));
		mmanUserLoan.setPaidMoney(new BigDecimal(Integer.parseInt(String.valueOf(borrowOrder.get("paid_money")))/100.00));//服务费+本金
		mmanUserLoan.setLoanPenalty(new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("plan_late_fee")))/100.00));
		mmanUserLoan.setServiceCharge(new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("repayment_interest")))/100.00));//服务费
		mmanUserLoan.setLoanPenaltyRate(String.valueOf(repaymentMap.get("late_fee_apr")));
		mmanUserLoan.setLoanEndTime(DateUtil.getDateTimeFormat(String.valueOf(repaymentMap.get("repayment_time")), "yyyy-MM-dd HH:mm:ss"));
		mmanUserLoan.setLoanStartTime(DateUtil.getDateTimeFormat(String.valueOf(repaymentMap.get("credit_repayment_time")), "yyyy-MM-dd HH:mm:ss"));
		mmanUserLoan.setUpdateTime(new Date());
		mmanUserLoan.setLoanStatus(Constant.STATUS_OVERDUE_FOUR);//4：逾期
		mmanUserLoan.setCreateTime(new Date());
		mmanUserLoan.setDelFlag("0");//0正常1：删除
		mmanUserLoan.setCustomerType(Integer.valueOf(userInfo.get("customer_type") == null ? "0" : userInfo.get("customer_type").toString()));   // 标识新老用户 0 新用户  1 老用户
		this.localDataDao.saveMmanUserLoan(mmanUserLoan);
		loger.info("end-saveMmanUserLoan:"+String.valueOf(borrowOrder.get("id")));
	}
	/**
	 * 保存用户还款表
	 * @param repaymentMap  还款信息
	 * */
	private void saveCreditLoanPay(HashMap<String,Object> repaymentMap){
		loger.info("start-saveCreditLoanPay:"+String.valueOf(repaymentMap.get("id")));
		try{
			CreditLoanPay creditLoanPay = new CreditLoanPay();
			creditLoanPay.setId(String.valueOf(repaymentMap.get("id")));
			creditLoanPay.setLoanId(String.valueOf(repaymentMap.get("asset_order_id")));
			creditLoanPay.setCreateDate(DateUtil.getDateTimeFormat(String.valueOf(repaymentMap.get("created_at")), "yyyy-MM-dd HH:mm:ss"));
			creditLoanPay.setReceivableStartdate(DateUtil.getDateTimeFormat(String.valueOf(repaymentMap.get("credit_repayment_time")), "yyyy-MM-dd HH:mm:ss"));
//			System.out.println("=====================================================");
//			System.out.println(repaymentMap.get("credit_repayment_time"));
//			System.out.println(DateUtil.getDateTimeFormat(String.valueOf(repaymentMap.get("repayment_time")), "yyyy-MM-dd HH:mm:ss"));
//			System.out.println("保存还款表 ：" + String.valueOf(repaymentMap.get("credit_repayment_time")));
//			System.out.println("=====================================================");
			creditLoanPay.setReceivableDate(DateUtil.getDateTimeFormat(String.valueOf(repaymentMap.get("repayment_time")), "yyyy-MM-dd HH:mm:ss"));//应还时间
			creditLoanPay.setReceivableMoney(new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("repayment_amount")))/100.00));//应还金额
			creditLoanPay.setRealMoney(new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("repaymented_amount")))/100.00));//实收(本金+服务费)
			creditLoanPay.setStatus(syncUtils.getPayStatus(String.valueOf(repaymentMap.get("status")))); //还款状态
			creditLoanPay.setUpdateDate(new Date());
			creditLoanPay = syncUtils.operaRealPenlty(repaymentMap,creditLoanPay);
			this.localDataDao.saveCreditLoanPay(creditLoanPay);
			loger.info("end-saveCreditLoanPay:"+String.valueOf(repaymentMap.get("id")));
		}catch (Exception e){
			e.printStackTrace();
		}

	}
	/**
	 * 保存用户信息
	 * @param userId  用户id
	 * @param userInfo 用户信息
	 * @param userContactsList 用户联系人
	 * @param cardInfo 银行卡信息
	 * */
	private void saveUserInfo(String userId,HashMap<String, Object> userInfo,List<HashMap<String, Object>> userContactsList,HashMap<String, Object> cardInfo){
		if(checkUserInfo(userId)) {//如果借款人信息不存在
			//保存用户信息
			userInfo.put("user_from", 0);
			this.localDataDao.saveMmanUserInfo(userInfo);
			//保存用户联系人
			saveMmanUserRela(userInfo, userContactsList);
			//保存银行卡
			saveUpdateSysUserBankCard(cardInfo,IdGen.uuid());
		}else{//借款人信息存在
			if(checkUserRela(userId)){//通讯录不存在
				//保存用户联系人
				saveMmanUserRela(userInfo, userContactsList);
			}
			//更新银行卡
			saveUpdateSysUserBankCard(cardInfo,null);
		}
	}
	//保存用户联系人
	private void saveMmanUserRela(HashMap<String,Object> userInfo,List<HashMap<String,Object>> userContactsList){
		List<ContactList> contactList = null;
		try{
			contactList = JxlJsonUtil.operaJxlDetail(String.valueOf(userInfo.get("jxl_detail")));
		}catch(Exception e){
			loger.error("解析聚信立异常-payId"+payId);
		}
		MmanUserRela mmanUserRela = null;
		//保存第一联系人
		saveMmanUserRelas("firstContact",mmanUserRela,userInfo,userContactsList,contactList);
		//保存第二联系人
		saveMmanUserRelas("secondContact",mmanUserRela,userInfo,userContactsList,contactList);
		//保存其他联系人
		saveMmanUserRelas("otherContact",mmanUserRela,userInfo,userContactsList,contactList);
	}

	private void saveMmanUserRelas(String flag,MmanUserRela mmanUserRela, HashMap<String, Object> userInfo, List<HashMap<String, Object>> userContactsList, List<ContactList> contactList) {
		mmanUserRela = new MmanUserRela();
		String phoneNmuber = null;
		mmanUserRela.setUserId(String.valueOf(userInfo.get("id")));
		mmanUserRela.setDelFlag("0");
		if (flag.equals("firstContact")){//第一联系人
			mmanUserRela.setId(IdGen.uuid());
			phoneNmuber = String.valueOf(userInfo.get("first_contact_phone"));
			mmanUserRela.setContactsKey("1");
			mmanUserRela.setInfoName(String.valueOf(userInfo.get("first_contact_name")));
			mmanUserRela.setInfoValue(phoneNmuber);
			mmanUserRela.setRelaKey(String.valueOf(userInfo.get("frist_contact_relation")));
			//保存第一联系人
			saveUserRael(contactList,mmanUserRela,phoneNmuber);
		}else if (flag.equals("secondContact")){//第二联系人
			mmanUserRela.setId(IdGen.uuid());
			phoneNmuber = String.valueOf(userInfo.get("second_contact_phone"));
			mmanUserRela.setContactsKey("2");
			mmanUserRela.setInfoName(String.valueOf(userInfo.get("second_contact_name")));
			mmanUserRela.setInfoValue(phoneNmuber);
			mmanUserRela.setRelaKey(String.valueOf(userInfo.get("second_contact_relation")));
			//保存第二联系人
			saveUserRael(contactList,mmanUserRela,phoneNmuber);
		}else {//其他联系人
			for(int i=0;i<userContactsList.size();i++){
				mmanUserRela.setId(IdGen.uuid());
				HashMap<String,Object> userRela = userContactsList.get(i);
				phoneNmuber = String.valueOf(userRela.get("contact_phone"));
				mmanUserRela.setInfoName(String.valueOf(userRela.get("contact_name")));
				mmanUserRela.setInfoValue(phoneNmuber);
				//保存其他联系人
				saveUserRael(contactList,mmanUserRela,phoneNmuber);
			}
		}
	}
	/**
	 * 设置联系人属性 --保存联系人
	 * @param contactList
	 * @param mmanUserRela
	 * @param phoneNmuber
	 */
	private void saveUserRael(List<ContactList> contactList, MmanUserRela mmanUserRela,String phoneNmuber) {
		if(null!=contactList && 0<contactList.size()) {
			for (int j = 0; j < contactList.size(); j++) {
				ContactList contact = contactList.get(j);
				if (phoneNmuber.equals(contact.getPhone_num().trim())) {
					mmanUserRela.setPhoneNumLoc(contact.getPhone_num_loc());//归属地
					int callcnt = 0;
					try {
						callcnt = Integer.parseInt(String.valueOf(contact.getCall_cnt()));
					} catch (Exception e) {
					}
					mmanUserRela.setCallCnt(callcnt);//联系次数
					int CallOutCnt = 0;
					try {
						CallOutCnt = Integer.parseInt(String.valueOf(contact.getCall_out_cnt()));
					} catch (Exception e) {
					}
					mmanUserRela.setCallOutCnt(CallOutCnt);//主叫次数

					int CallInCnt = 0;
					try {
						CallInCnt = Integer.parseInt(String.valueOf(contact.getCall_in_cnt()));
					} catch (Exception e) {
					}
					mmanUserRela.setCallInCnt(CallInCnt);//被叫次数

					mmanUserRela.setCallLen(JxlJsonUtil.getCallLen(contact.getCall_len()));//联系时间-分
					mmanUserRela.setCallOutLen(JxlJsonUtil.getCallLen(contact.getCall_out_len()));//主叫时间-分
					mmanUserRela.setCallInLen(JxlJsonUtil.getCallLen(contact.getCall_in_len()));//被叫时间-分
					break;
				}
			}
		}
		try {
			this.localDataDao.saveMmanUserRela(mmanUserRela);
		}catch (Exception e){
			loger.error("联系人信息错误：payId"+payId+"错误值："+phoneNmuber,e);
		}
	}
	// 保存-更新用户银行卡
	private void saveUpdateSysUserBankCard(HashMap<String,Object> cardInfo,String uuid){
		SysUserBankCard bankCard = new SysUserBankCard();
		bankCard.setUserId(String.valueOf(cardInfo.get("user_id")));
		bankCard.setBankCard(String.valueOf(cardInfo.get("card_no")));
		bankCard.setDepositBank(String.valueOf(cardInfo.get("bank_name")));
		bankCard.setBankInstitutionNo(String.valueOf(cardInfo.get("bank_id")));
		bankCard.setName(String.valueOf(cardInfo.get("open_name")));
		bankCard.setMobile(String.valueOf(cardInfo.get("phone")));
		bankCard.setCityName(String.valueOf(cardInfo.get("bank_address")));
		if (uuid != null && !uuid.equals("")){
			bankCard.setId(IdGen.uuid());
			this.localDataDao.saveSysUserBankCard(bankCard);
		}else{
			this.localDataDao.updateSysUserBankCard(bankCard);
		}
	}
	/**
	 * 验证订单是否重复入库
	 * @param loanId 根据借款id判断同一个订单是否重复入库
	 */
	public boolean checkLoan(String loanId){
		if(StringUtils.isNotBlank(loanId)){
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("ID", loanId);
			int count = this.localDataDao.checkLoan(map);
			if(count>0){
				return false;
			}
			return true;
		}
		return false;
	}
	/**
	 * 验证用户是否存在
	 */
	public boolean checkUserInfo(String userId){
		if(StringUtils.isNotBlank(userId)){
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("ID", userId);
			int count = this.localDataDao.checkUserInfo(map);
			if(count>0){
				return false;
			}
			return true;//不存在
		}
		return false;
	}
	/**
	 * 验证用户联系人是否存在
	 */
	public boolean checkUserRela(String userId){
		if(StringUtils.isNotBlank(userId)){
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("ID", userId);
			int count = this.localDataDao.checkUserRela(map);
			if(count>0){
				return false;
			}
			return true;//不存在
		}
		return false;
	}
}
