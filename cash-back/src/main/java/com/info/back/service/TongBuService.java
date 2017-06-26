package com.info.back.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.ITongBuDao;
import com.info.web.pojo.CreditLoanPay;
import com.info.web.pojo.CreditLoanPayDetail;
import com.info.web.pojo.MmanUserInfo;
import com.info.web.pojo.MmanUserLoan;
import com.info.web.pojo.MmanUserRela;
import com.info.web.pojo.SysUserBankCard;
import com.info.web.util.DataSourceContextHolder;

@Service
public class TongBuService implements ITongBuService{
	
	//查询xjx的库
	@Autowired
	private ITongBuDao tongBuDao;
	
	//插入用户信息
	@Autowired
	private IMmanUserInfoService mmanUserInfoService;
	//插入用户银行卡
	@Autowired
	private ISysUserBankCardService sysUserBankCardService;
	//用户联系人信息
	@Autowired
	private IMmanUserRelaService mmanUserRelaService;
	//插入还款表
	@Autowired
	private ICreditLoanPayService creditLoanPayService;
	//插入还款详情
	@Autowired
	private ICreditLoanPayDetailService creditLoanPayDetailService;
	//插入借款表
	@Autowired
	private IMmanUserLoanService mmanUserLoanService;
	
	
	/**
	 *  逾期推送
		repayId
		type:OVERDUE
		还款推送
		repayId
		type:REPAY
	 */
	

	@Override
	public void tongbu(String id, String cz) {
		DataSourceContextHolder.setDbType("dataSourcexjx");
		//还款信息
		Map<Object,Object> hkmap = new HashMap<Object,Object>(); 
		hkmap = tongBuDao.getAssetRepayment(id);
		if(hkmap.isEmpty()){
			return;
		}
		
		//还款详情
		List<Map<Object,Object>> assetRepaymentDetailmap = new ArrayList<Map<Object,Object>>();
		assetRepaymentDetailmap = tongBuDao.getAssetRepaymentDetail((java.lang.Long)hkmap.get("id"));
		
		//用户信息信息
		Map<Object,Object> usermap = new HashMap<Object,Object>();
		usermap =  tongBuDao.getUserInfo((java.lang.Long)hkmap.get("user_id"));
		//用户联系人
		List<Map<Object,Object>>  userContactsmap = new ArrayList<Map<Object,Object>>();
		userContactsmap =  tongBuDao.getUserContacts((java.lang.Long)hkmap.get("user_id"));
		//银行卡
		Map<Object,Object> userCardmap = new HashMap<Object,Object>();
		userCardmap = tongBuDao.getuserCardInfo((java.lang.Long)hkmap.get("user_id"));
		
		
		//借款详情
		Map<Object,Object> assetborrowordermap = new HashMap<Object,Object>();
		assetborrowordermap = tongBuDao.getassetborroworder((java.lang.Long)hkmap.get("asset_order_id"));
		
		
		
		
		
		//BorrowOrder bo = borrowOrderService.findOneBorrow(re.getAssetOrderId());  通过借款ID拿到借款详情 (select * FROM asset_borrow_order where id = ?)
		//==assetborrowordermap
		DataSourceContextHolder.setDbType("dataSourcecs");

		/*HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", usermap.get("id"));
		List<UserContacts> userContacts = userContactsService.selectUserContacts(params);  通过用户ID拿到联系人列表  (select * from user_contacts where user_id = ?)*/
		//==userContactsmap
		// 应还本金
		int repayment_amount =  Integer.parseInt(String.valueOf(hkmap.get("repayment_amount")));
		int plan_late_fee = Integer.parseInt(String.valueOf(hkmap.get("plan_late_fee")));
		//int receivablePrinciple = (int)hkmap.get("repayment_amount") - (int)hkmap.get("plan_late_fee");
		int receivablePrinciple =  repayment_amount- plan_late_fee;
		// 实收的利息 = 已还金额 - 应还本金
		int realPenlty = repayment_amount - receivablePrinciple;
	
		int count = creditLoanPayService.FindCount(String.valueOf(assetborrowordermap.get("id")));
		if("OVERDUE".equals(cz)){
			
			if(0==count){
				/**
				 * 插入紧急联系人   用户信息  用户银行卡  联系人列表
				 */
				MmanUserInfo mmanUserInfo = new MmanUserInfo();
				mmanUserInfo = mmanUserInfoService.getxjxuser((java.lang.Long)hkmap.get("user_id"));
				
				mmanUserInfoService.saveNotNull(mmanUserInfo);
				MmanUserRela mmanUserRela = null;
				for(Map<Object,Object> abc : userContactsmap){
					mmanUserRela = new MmanUserRela();
					mmanUserRela.setUserId(String.valueOf(hkmap.get("user_id")));
					mmanUserRela.setContactsKey("");
					mmanUserRela.setRelaKey("");
					mmanUserRela.setInfoName(String.valueOf(abc.get("contact_name")));
					mmanUserRela.setInfoValue(String.valueOf(abc.get("contact_phone")));
					mmanUserRela.setContactsFlag("");
					mmanUserRelaService.saveNotNull(mmanUserRela);
				}
				mmanUserRela = new MmanUserRela();
				mmanUserRela.setUserId(String.valueOf(hkmap.get("user_id")));
				mmanUserRela.setContactsKey("1");
				mmanUserRela.setRelaKey( String.valueOf(hkmap.get("frist_contact_relation")));
				mmanUserRela.setInfoName( String.valueOf(hkmap.get("first_contact_name")));
				mmanUserRela.setInfoValue((String) String.valueOf(hkmap.get("first_contact_phone")));
				mmanUserRela.setContactsFlag("1");
				mmanUserRelaService.saveNotNull(mmanUserRela);
				mmanUserRela = new MmanUserRela();
				mmanUserRela.setUserId(String.valueOf(hkmap.get("user_id")));
				mmanUserRela.setContactsKey("1");
				mmanUserRela.setRelaKey(String.valueOf(hkmap.get("second_contact_relation")));
				mmanUserRela.setInfoName(String.valueOf(hkmap.get("second_contact_name")));
				mmanUserRela.setInfoValue(String.valueOf(hkmap.get("second_contact_phone")));
				mmanUserRela.setContactsFlag("1");
				mmanUserRelaService.saveNotNull(mmanUserRela);
				
				SysUserBankCard sysUserBankCard = new SysUserBankCard();
				sysUserBankCard.setUserId(String.valueOf(userCardmap.get("user_id")));
				sysUserBankCard.setBankCard(String.valueOf(userCardmap.get("card_no")));
				sysUserBankCard.setDepositBank(String.valueOf(userCardmap.get("bank_name")));
				sysUserBankCard.setMobile(String.valueOf(userCardmap.get("phone")));
				sysUserBankCardService.saveNotNull(sysUserBankCard);
				
			}
		
		
		/**
		 * 插入 还款 信息 还款详情  借款信息
		 */
		CreditLoanPay creditLoanPay = new CreditLoanPay();
		
		// 还款信息
				creditLoanPay.setId((String)hkmap.get("id"));
				creditLoanPay.setLoanId(String.valueOf(hkmap.get("asset_order_id")));
				creditLoanPay.setReceivableStartdate((Date)hkmap.get("credit_repayment_time"));
				creditLoanPay.setReceivableDate((Date)hkmap.get("repayment_time"));
				creditLoanPay.setReceivableMoney((BigDecimal)hkmap.get("repayment_amount"));
				creditLoanPay.setRealMoney((BigDecimal)hkmap.get("repaymented_amount"));
				
				if(realPenlty <= 0){
					/*creditLoanPay.put("receivablePrinciple", (receivablePrinciple - re.getRepaymentedAmount()) );
					creditLoanPay.put("receivableInterest", re.getPlanLateFee() / 100.00);
					creditLoanPay.put("realgetPrinciple", re.getRepaymentedAmount() / 100.00);
					creditLoanPay.put("realgetInterest", 0);*/
					
					
					creditLoanPay.setReceivablePrinciple(new BigDecimal((receivablePrinciple-Integer.parseInt(String.valueOf(hkmap.get("repaymented_amount")))/100.00)));
					creditLoanPay.setReceivableInterest(new BigDecimal(Integer.parseInt(String.valueOf(hkmap.get("plan_late_fee")))/100.00));
					creditLoanPay.setRealgetPrinciple(new BigDecimal(Integer.parseInt(String.valueOf(hkmap.get("repaymented_amount")))/100.00));
					creditLoanPay.setRealgetInterest(new BigDecimal(0));
					
				}
				else{
					/*creditLoanPay.put("receivablePrinciple", 0);
					creditLoanPay.put("receivableInterest", (re.getRepaymentAmount() - re.getRepaymentedAmount()) / 100.00);
					creditLoanPay.put("realgetPrinciple", (re.getRepaymentedAmount().intValue() - realPenlty) / 100.00);
					creditLoanPay.put("realgetInterest", realPenlty / 100.00);*/
					
					creditLoanPay.setReceivablePrinciple(new BigDecimal(0));
					int receivableInterest = Integer.parseInt(String.valueOf(hkmap.get("repayment_amount"))) - Integer.parseInt(String.valueOf(hkmap.get("repaymented_amount")));
					creditLoanPay.setReceivableInterest(new BigDecimal(receivableInterest/100.00));
					int realgetPrinciple =Integer.parseInt(String.valueOf(hkmap.get("repaymented_amount"))) - realPenlty;
					creditLoanPay.setRealgetPrinciple(new BigDecimal(realgetPrinciple/100.00));
					creditLoanPay.setRealgetInterest(new BigDecimal(0));
				}
				creditLoanPay.setStatus(Integer.parseInt(String.valueOf(hkmap.get("status")))); 
				creditLoanPay.setCreateDate(new Date());
				creditLoanPayService.saveNotNull(creditLoanPay);
		
		
		CreditLoanPayDetail creditLoanPayDetail =null;
		for(Map<Object,Object> bcds :assetRepaymentDetailmap){
			creditLoanPayDetail = new CreditLoanPayDetail();
			creditLoanPayDetail.setId(String.valueOf(bcds.get("id")));
			creditLoanPayDetail.setPayId(String.valueOf(hkmap.get("asset_order_id")));
			if(realPenlty <= 0){
				/*creditLoanPayDetail.put("realMoney", rd.getTrueRepaymentMoney() );
				creditLoanPayDetail.put("realPenlty", 0);
				creditLoanPayDetail.put("realPrinciple", (receivablePrinciple - re.getRepaymentedAmount()) / 100.00);
				creditLoanPayDetail.put("realInterest", re.getPlanLateFee() / 100.00);*/
				
				creditLoanPayDetail.setRealMoney(new BigDecimal(Integer.parseInt(String.valueOf(bcds.get("true_repayment_money")))/ 100.00));
				creditLoanPayDetail.setRealPenlty(new BigDecimal(0));
				int RealPrinciple = receivablePrinciple - Integer.parseInt(String.valueOf(hkmap.get("repaymented_amount")));
				creditLoanPayDetail.setRealPrinciple(new BigDecimal(RealPrinciple/ 100.00));
				creditLoanPayDetail.setRealInterest(new BigDecimal(Integer.parseInt(String.valueOf(hkmap.get("plan_late_fee")))));
			}
			else{
			/*	creditLoanPayDetail.put("realMoney", (rd.getTrueRepaymentMoney() - realPenlty)  / 100.00);
				creditLoanPayDetail.put("realPenlty", realPenlty / 100.00);
				creditLoanPayDetail.put("realPrinciple", 0);
				creditLoanPayDetail.put("realInterest", (re.getRepaymentAmount() - re.getRepaymentedAmount()) / 100.00);*/
				
				int realMoney = Integer.parseInt(String.valueOf(bcds.get("true_repayment_money"))) - realPenlty;
				creditLoanPayDetail.setRealMoney(new BigDecimal(realMoney/100.00));
				creditLoanPayDetail.setRealPenlty(new BigDecimal(realPenlty/100.00));
				creditLoanPayDetail.setRealPrinciple(new BigDecimal(0));
				int realInterest = Integer.parseInt(String.valueOf(hkmap.get("repayment_amount"))) - Integer.parseInt(String.valueOf(hkmap.get("repaymented_amount")));
				creditLoanPayDetail.setRealInterest(new BigDecimal(realInterest/100.00));
			} 
			creditLoanPayDetail.setCreateDate(new Date());
			/*creditLoanPayDetail.put("returnType", rd.getRepaymentType());
			creditLoanPayDetail.put("remark", rd.getRemark());*/
			creditLoanPayDetail.setReturnType(String.valueOf(assetborrowordermap.get("repayment_type")));
			creditLoanPayDetail.setRemark(String.valueOf(assetborrowordermap.get("remark")));
			
			creditLoanPayDetailService.saveNotNull(creditLoanPayDetail);
		}
		
		MmanUserLoan mmanUserLoan = new MmanUserLoan();
		
		//if(collType == Repayment.REPAY_COLLECTION || collType == Repayment.OVERDUE_COLLECTION || collType == Repayment.RENEWAL_COLLECTION){

			// 借款信息
			//Map<String, Object> mmanUserLoan = new HashMap<String, Object>();
			//mmanUserLoan.put("id", bo.getId());
			/*mmanUserLoan.put("userId",(java.lang.String)hkmap.get("user_id"));
			mmanUserLoan.put("loanPyId", bo.getOutTradeNo());
			mmanUserLoan.put("loanMoney", bo.getMoneyAmount() / 100.00);
			mmanUserLoan.put("loanRate", bo.getApr());
			mmanUserLoan.put("loanPenalty", re.getPlanLateFee() / 100.00);
			mmanUserLoan.put("loanPenaltyRate", bo.getLateFeeApr());
			mmanUserLoan.put("loanStartTime", sdf.format(re.getCreditRepaymentTime()));
			mmanUserLoan.put("loanEndTime", sdf.format(re.getRepaymentTime()));
			mmanUserLoan.put("loanStatus", re.getStatus());*/
			
			mmanUserLoan.setUserId(String.valueOf(hkmap.get("user_id")));
			mmanUserLoan.setLoanPyId(String.valueOf(hkmap.get("asset_order_id")));
			mmanUserLoan.setLoanMoney(new BigDecimal(Integer.parseInt(String.valueOf(assetborrowordermap.get("money_amount")))/100));
			mmanUserLoan.setLoanRate(String.valueOf(assetborrowordermap.get("apr")));
			mmanUserLoan.setLoanPenalty(new BigDecimal(Integer.parseInt(String.valueOf(hkmap.get("money_amount")))/100));
			mmanUserLoan.setLoanPenaltyRate(String.valueOf(hkmap.get("late_fee_apr")));
			mmanUserLoan.setLoanStatus(String.valueOf(hkmap.get("status")));
			mmanUserLoan.setLoanEndTime((Date)hkmap.get("repayment_time"));
			mmanUserLoan.setLoanStartTime((Date)hkmap.get("credit_repayment_time"));
			mmanUserLoan.setCreateTime(new Date());
			mmanUserLoanService.saveNotNull(mmanUserLoan);
		//}
		}
		
		if("REPAY".equals(cz)){
			creditLoanPayDetailService.deleteid(String.valueOf(hkmap.get("id")));
			if("34".equals(String.valueOf(hkmap.get("status")))){
				MmanUserLoan mm = new MmanUserLoan();
				mm.setUpdateTime(new Date());
				mm.setLoanPyId(String.valueOf(hkmap.get("id")));
				mm.setLoanStatus("5");
				mmanUserLoanService.updateNotNull(mm);
				CreditLoanPay creditLoanPay = new CreditLoanPay();
				creditLoanPay.setUpdateDate(new Date());
				creditLoanPay.setStatus(5);
				creditLoanPay.setLoanId(String.valueOf(assetborrowordermap.get("id")));
				creditLoanPayService.updateNotNull(creditLoanPay);
			}
			CreditLoanPayDetail creditLoanPayDetail =null;
			for(Map<Object,Object> bcds :assetRepaymentDetailmap){
				creditLoanPayDetail = new CreditLoanPayDetail();
				creditLoanPayDetail.setId(String.valueOf((String)bcds.get("id")));
				creditLoanPayDetail.setPayId(String.valueOf(hkmap.get("asset_order_id")));
				if(realPenlty <= 0){
					/*creditLoanPayDetail.put("realMoney", rd.getTrueRepaymentMoney() );
					creditLoanPayDetail.put("realPenlty", 0);
					creditLoanPayDetail.put("realPrinciple", (receivablePrinciple - re.getRepaymentedAmount()) / 100.00);
					creditLoanPayDetail.put("realInterest", re.getPlanLateFee() / 100.00);*/
					
					creditLoanPayDetail.setRealMoney(new BigDecimal(Integer.parseInt(String.valueOf(bcds.get("true_repayment_money")))/ 100.00));
					creditLoanPayDetail.setRealPenlty(new BigDecimal(0));
					int RealPrinciple = receivablePrinciple - Integer.parseInt(String.valueOf(hkmap.get("repaymented_amount")));
					creditLoanPayDetail.setRealPrinciple(new BigDecimal(RealPrinciple/ 100.00));
					creditLoanPayDetail.setRealInterest(new BigDecimal(Integer.parseInt(String.valueOf(hkmap.get("plan_late_fee")))));
				}
				else{
				/*	creditLoanPayDetail.put("realMoney", (rd.getTrueRepaymentMoney() - realPenlty)  / 100.00);
					creditLoanPayDetail.put("realPenlty", realPenlty / 100.00);
					creditLoanPayDetail.put("realPrinciple", 0);
					creditLoanPayDetail.put("realInterest", (re.getRepaymentAmount() - re.getRepaymentedAmount()) / 100.00);*/
					
					int realMoney = Integer.parseInt(String.valueOf(bcds.get("true_repayment_money"))) - realPenlty;
					creditLoanPayDetail.setRealMoney(new BigDecimal(realMoney/100.00));
					creditLoanPayDetail.setRealPenlty(new BigDecimal(realPenlty/100.00));
					creditLoanPayDetail.setRealPrinciple(new BigDecimal(0));
					int realInterest = Integer.parseInt(String.valueOf(hkmap.get("repayment_amount"))) - Integer.parseInt(String.valueOf(hkmap.get("repaymented_amount")));
					creditLoanPayDetail.setRealInterest(new BigDecimal(realInterest/100.00));
				} 
				creditLoanPayDetail.setCreateDate(new Date());
				/*creditLoanPayDetail.put("returnType", rd.getRepaymentType());
				creditLoanPayDetail.put("remark", rd.getRemark());*/
				creditLoanPayDetail.setReturnType(String.valueOf(assetborrowordermap.get("repayment_type")));
				creditLoanPayDetail.setRemark(String.valueOf(assetborrowordermap.get("remark")));
				creditLoanPayDetailService.saveNotNull(creditLoanPayDetail);
			}
			
		}
	/*	if(1 == 1){
			// 紧急联系人
			List<Map> mmanUserRelas = new ArrayList<Map>();
			Map<String, String> mmanUserRela = new HashMap<String, String>();
			mmanUserRela.put("id", "");
			mmanUserRela.put("userId", (String)usermap.get("id"));
			mmanUserRela.put("contactsKey", "1");
			mmanUserRela.put("relaKey", (String)usermap.get("fristContactRelation"));
			mmanUserRela.put("infoName", (String)usermap.get("firstContactName"));
			mmanUserRela.put("infoValue", (String)usermap.get("firstContactPhone"));
			mmanUserRela.put("contactsFlag", "1");
			mmanUserRelas.add(mmanUserRela);

			mmanUserRela = new HashMap<String, String>();
			mmanUserRela.put("id", "");
			mmanUserRela.put("userId", (String)usermap.get("id"));
			mmanUserRela.put("contactsKey", "2");
			mmanUserRela.put("relaKey", (String)usermap.get("secondContactRelation"));
			mmanUserRela.put("infoName", (String)usermap.get("secondContactName"));
			mmanUserRela.put("infoValue", (String)usermap.get("secondContactPhone"));
			mmanUserRela.put("contactsFlag", "1");
			mmanUserRelas.add(mmanUserRela);

			for (Map<Object, Object> contacts : userContactsmap) {
				mmanUserRela = new HashMap<String, String>();
				mmanUserRela.put("id", (String)contacts.get("id"));
				mmanUserRela.put("userId", (String)contacts.get("id"));
				mmanUserRela.put("contactsKey", "2");
				mmanUserRela.put("infoName",  (String)contacts.get("contactName"));
				mmanUserRela.put("infoValue", (String)contacts.get("contactPhone"));
				mmanUserRelas.add(mmanUserRela);
			}

			loanMap.put("mmanUserInfo", usermap);
			loanMap.put("mmanUserRelas", mmanUserRelas);*/


		/*	// 银行卡信息
			Map<String, Object> bankCard = new HashMap<String, Object>();
				bankCard.put("id",  (String)userCardmap.get("id"));
				bankCard.put("userId", (String)userCardmap.get("userId"));
				bankCard.put("bankCard", (String)userCardmap.get("cardno"));
				bankCard.put("depositBank", (String)userCardmap.get("bankName"));
				bankCard.put("mobile",(String)userCardmap.get("phone"));

			loanMap.put("bankCard", bankCard);*/
/*
			// 还款详情
			List<Map> creditLoanPayDetails = new ArrayList<Map>();
			//List<RepaymentDetail> rds = this.findDetailsByRepId(hkmap.get("id"));          // (select * from asset_repayment_detail where user_id = ? and `status` in (2,30);)
			Map<String, Object> creditLoanPayDetail;   
			for (Map<Object,Object> rd :assetRepaymentDetailmap) {
				creditLoanPayDetail = new HashMap<String, Object>();
				creditLoanPayDetail.put("id", (String)rd.get("id"));
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("id", hkmap.get("id"));
				creditLoanPayDetail.put("payId", temp);
  
				if(realPenlty <= 0){
					creditLoanPayDetail.put("realMoney", (int)rd.get("trueRepaymentMoney") / 100.00);
					creditLoanPayDetail.put("realPenlty", 0);

					creditLoanPayDetail.put("realPrinciple", (receivablePrinciple - (int)hkmap.get("repaymentedAmount")) / 100.00);
					creditLoanPayDetail.put("realInterest", (int)hkmap.get("planLateFee") / 100.00);
				}
				else{
					creditLoanPayDetail.put("realMoney", ((int)rd.get("TrueRepaymentMoney") - realPenlty)  / 100.00);
					creditLoanPayDetail.put("realPenlty", realPenlty / 100.00);

					creditLoanPayDetail.put("realPrinciple", 0);
					creditLoanPayDetail.put("realInterest", ((int)hkmap.get("repaymentAmount") - (int)hkmap.get("RepaymentedAmount") / 100.00));
				}
				creditLoanPayDetail.put("returnType", rd.get("repaymentType"));
				creditLoanPayDetail.put("remark", rd.get("remark"));

				creditLoanPayDetails.add(creditLoanPayDetail);
			}

			loanMap.put("creditLoanPayDetails", creditLoanPayDetails);*/

		/*//if(collType == Repayment.REPAY_COLLECTION || collType == Repayment.OVERDUE_COLLECTION){

			// 借款信息
			Map<String, Object> mmanUserLoan = new HashMap<String, Object>();
			mmanUserLoan.put("id", assetborrowordermap.get("id"));
			mmanUserLoan.put("userId", usermap.get("id"));
			mmanUserLoan.put("loanPyId", assetborrowordermap.get("OutTradeNo"));
			mmanUserLoan.put("loanMoney", (int)assetborrowordermap.get("MoneyAmount") / 100.00);
			mmanUserLoan.put("loanRate", assetborrowordermap.get("Apr"));
			mmanUserLoan.put("loanPenalty", (int)assetborrowordermap.get("PlanLateFee") / 100.00);
			mmanUserLoan.put("loanPenaltyRate",assetborrowordermap.get("LateFeeApr"));
			mmanUserLoan.put("loanStartTime", hkmap.get("CreditRepaymentTime"));
			mmanUserLoan.put("loanEndTime", hkmap.get("RepaymentTime"));
			mmanUserLoan.put("loanStatus", hkmap.get("status"));

			loanMap.put("mmanUserLoan", mmanUserLoan);
		//}

		// 还款信息
		Map<String, Object> creditLoanPay = new HashMap<String, Object>();
		creditLoanPay.put("id", hkmap.get("id"));
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("id",  assetborrowordermap.get("id"));

		creditLoanPay.put("loanId", temp);
		creditLoanPay.put("receivableStartdate", hkmap.get("CreditRepaymentTime"));
		creditLoanPay.put("receivableDate", hkmap.get("RepaymentTime"));
		creditLoanPay.put("receivableMoney", (int)hkmap.get("RepaymentAmount")/ 100.00);
//		creditLoanPay.put("receivablePrinciple", receivablePrinciple / 100.00);
//		creditLoanPay.put("receivableInterest", re.getPlanLateFee() / 100.00);
		creditLoanPay.put("realMoney", (int)hkmap.get("RepaymentedAmount")/ 100.00);
		if(realPenlty <= 0){
			creditLoanPay.put("receivablePrinciple", (receivablePrinciple - (int)hkmap.get("RepaymentedAmount")) / 100.00);
			creditLoanPay.put("receivableInterest",(int)hkmap.get("PlanLateFee")/ 100.00);
			creditLoanPay.put("realgetPrinciple", (int)hkmap.get("RepaymentedAmount")/ 100.00);
			creditLoanPay.put("realgetInterest", 0);
		}
		else{
			creditLoanPay.put("receivablePrinciple", 0);
			creditLoanPay.put("receivableInterest", ((int)hkmap.get("RepaymentAmount") - (int)hkmap.get("RepaymentedAmount()")) / 100.00);
			creditLoanPay.put("realgetPrinciple", ((int)hkmap.get("RepaymentedAmount") - realPenlty) / 100.00);
			creditLoanPay.put("realgetInterest", realPenlty / 100.00);
		}

		creditLoanPay.put("status", hkmap.get("Status"));



		loanMap.put("creditLoanPay", creditLoanPay);*/

		/*//if(collType == Repayment.BREPAY_COLLECTION || collType == Repayment.REPAY_COLLECTION){
			// 还款详情
			List<Map> creditLoanPayDetails = new ArrayList<Map>();
			Map<String, Object> creditLoanPayDetail;
			creditLoanPayDetail = new HashMap<String, Object>();
			creditLoanPayDetail.put("id",""); //assetRepaymentDetailmap.get("id")
			temp = new HashMap<String, Object>();
			temp.put("id", hkmap.get("id"));
			creditLoanPayDetail.put("payId", temp);

			if(realPenlty <= 0){
				creditLoanPayDetail.put("realMoney", detail.getTrueRepaymentMoney() / 100.00);
				creditLoanPayDetail.put("realPenlty", 0);

				creditLoanPayDetail.put("realPrinciple", (receivablePrinciple - re.getRepaymentedAmount()) / 100.00);
				creditLoanPayDetail.put("realInterest", re.getPlanLateFee() / 100.00);
			}
			else{
				creditLoanPayDetail.put("realMoney", (detail.getTrueRepaymentMoney() - realPenlty)  / 100.00);
				creditLoanPayDetail.put("realPenlty", realPenlty / 100.00);

				creditLoanPayDetail.put("realPrinciple", 0);
				creditLoanPayDetail.put("realInterest", (re.getRepaymentAmount() - re.getRepaymentedAmount()) / 100.00);
			}
			creditLoanPayDetail.put("returnType", detail.getRepaymentType());
			creditLoanPayDetail.put("remark", detail.getRemark());

			creditLoanPayDetails.add(creditLoanPayDetail);

			loanMap.put("creditLoanPayDetails", creditLoanPayDetails);
		//}

		loanList.add(loanMap);

		Map<String, Object> collectionRelevantJson = new HashMap<String, Object>();
		collectionRelevantJson.put("collectionRelevantJson", loanList);

		// 设置参数 可设置多个
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("collectionRelevantJson", JSONObject.fromObject(collectionRelevantJson).toString()));
		try {
			String result = HttpUtil.getInstance().post(CollectionConstant.getCollectionPath(), postParams);
			JSONObject obj = JSONObject.fromObject(result);
			if (obj.getString("code").equals("0")) {
				re.setCollection(Repayment.COLLECTION_YES);
			}
		} catch (Exception e) {
			logger.error("collection error", e);
		}
		return re;
		*/

	}

}
