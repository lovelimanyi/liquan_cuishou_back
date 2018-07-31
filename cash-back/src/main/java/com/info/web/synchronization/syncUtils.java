package com.info.web.synchronization;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.info.back.utils.BackConstant;
import com.info.back.utils.IdGen;
import com.info.back.vo.jxl.ContactList;
import com.info.web.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.info.back.dao.ILocalDataDao;
import com.info.config.PayContents;
import com.info.constant.Constant;
import com.info.web.util.DateUtil;
import com.info.web.util.HttpUtil;
import com.info.web.util.encrypt.AESUtil;
import com.info.web.util.encrypt.MD5coding;

public class syncUtils {
	
	private static Logger loger = Logger.getLogger(syncUtils.class);
	
	/**
	 * 检验是否存在减免状态并做出相应减免
	 */
	public static void checkReduction(HashMap<String,Object> repaymentMap, ILocalDataDao localDataDao){
		String payId = String.valueOf(repaymentMap.get("id"));
		int receivableMoney = Integer.parseInt(String.valueOf(repaymentMap.get("repayment_amount")));//应还金额
		int realMoney= Integer.parseInt(String.valueOf(repaymentMap.get("repaymented_amount")));//已还金额
		
		try {
			loger.info("start-reduction-payId:"+payId);
			AuditCenter auditCenter = localDataDao.getAuditCenterByPayId(payId);
			
			if("3".equals(auditCenter.getType()) && ("2".equals(auditCenter.getStatus()) || "5".equals(auditCenter.getStatus()))){
				int reductionMoney = auditCenter.getReductionMoney().intValue()*100;//减免金额
				if(realMoney >= receivableMoney-reductionMoney && realMoney < receivableMoney){
					reductionMoney = receivableMoney-realMoney;
					
					String sign = MD5coding.MD5(AESUtil.encrypt(auditCenter.getLoanUserId()+auditCenter.getPayId()+reductionMoney+auditCenter.getId(),PayContents.XJX_WITHHOLDING_NOTIFY_KEY));
                    //2、发起请求
                    String withholdPostUrl=PayContents.XJX_JIANMIAN_URL+"/"+auditCenter.getLoanUserId()+"/"+auditCenter.getPayId()+"/"+reductionMoney+"/"+auditCenter.getId()+"/"+sign;

                    String xjxWithholdingStr = HttpUtil.getHttpMess(withholdPostUrl, "", "POST", "UTF-8");
					
                   loger.info(xjxWithholdingStr);
                    
                   loger.info("payId:"+payId+"需要减免的金额："+reductionMoney);
				}else if(realMoney == receivableMoney){
					 loger.info("payId:"+payId+"钱已还完，无法减免");
				}else{
					 loger.info("未到减免资格，无法减免");
				}
				loger.info("end-reduction-payId:"+payId+"redcutionMoney="+reductionMoney);
			}
			
		}catch (Exception e){
			loger.error("未申请减免payId:"+payId);
		}
	}
	/**
	 * 减免罚息    如果还款类型为6，还款状态2.则更新还款表的 减免滞纳金金额
	 * @param repayDetail 从库还款详情
	 * @param id 还款id
	 */
	public static void ReductionMoney(HashMap<String, Object> repayDetail,String id, ILocalDataDao localDataDao) {
		int repaymentType = Integer.valueOf(String.valueOf(repayDetail.get("repayment_type")));
		int status = Integer.valueOf(String.valueOf(repayDetail.get("status")));
		if(repaymentType==6 && status==2){
			CreditLoanPay creditLoanPay = new CreditLoanPay();
			int reductionMoney =Integer.parseInt(String.valueOf(repayDetail.get("true_repayment_money")));//还款金额，从库中传过来
			creditLoanPay.setId(id);
			creditLoanPay.setReductionMoney(new BigDecimal(reductionMoney / 100.00));
			localDataDao.updateCreditLoanPay(creditLoanPay);
		}
	}


	/**
	 * 更新用户借款表
	 * @param repaymentMap 还款信息
	 * @param status 借款状态 4 逾期，5还款完成
	 * */
	public static void updateMmanUserLoan(ILocalDataDao localDataDao,String loanId,HashMap<String,Object> repaymentMap,String status){
		MmanUserLoan mmanUserLoan = new MmanUserLoan();
		mmanUserLoan.setId(loanId);//借款id
		mmanUserLoan.setLoanPenalty(new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("plan_late_fee")))/100.00));//滞纳金
		mmanUserLoan.setLoanStatus(status);//借款状态
		mmanUserLoan.setUpdateTime(new Date());
//		if(BackConstant.XJX_LOAN_STATUS_RETURN_SUCCESS.equals(status)){
//			mmanUserLoan.setCustomerType(BackConstant.CUSTOMER_TYPE_OLD); // 还款完成,用户变成老用户
//		}
		localDataDao.updateMmanUserLoan(mmanUserLoan);
	}
	/**
	 * 更新用户还款表
	 * @param repaymentMap 还款信息
	 * */
	public static void updateCreditLoanPay(ILocalDataDao localDataDao,String payId,HashMap<String,Object> repaymentMap){
		CreditLoanPay creditLoanPay = new CreditLoanPay();
		creditLoanPay.setId(payId);//还款id
		creditLoanPay.setReceivableMoney(new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("repayment_amount")))/100.00));//应还金额
		creditLoanPay.setRealMoney(new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("repaymented_amount")))/100.00));//实收（本金+服务费）
		creditLoanPay.setStatus(getPayStatus(String.valueOf(repaymentMap.get("status")))); //订单等级组s1,s2等
		creditLoanPay.setUpdateDate(new Date());
		creditLoanPay = syncUtils.operaRealPenlty(repaymentMap,creditLoanPay);//剩余应还(本金+手续费),剩余应还罚息,实收(本金+手续费),实收罚息
		localDataDao.updateCreditLoanPay(creditLoanPay);
	}
	/**
	 * 获取还款订单等级getPayStatus
	 * @param status
	 * @return  还款状态（3，4，5，6，7对应S1，S2，M1-M2，M2-M3，M3+对应1-10,11-30（1），1个月-2个月，2个月-3个月，3个月+）
	 *			public static final int CREDITLOANPAY_OVERDUEA = 3;// 逾期1到10天S1
	 *			public static final int CREDITLOANPAY_OVERDUEB = 4;// 逾期11到30天S2
	 *			public static final int CREDITLOANPAY_OVERDUEC = 5;// 逾期31到60天M1-M2
	 *			public static final int CREDITLOANPAY_OVERDUED = 6;// 逾期61到90天M2-M3
	 *			public static final int CREDITLOANPAY_OVERDUEE = 7;// 逾期大于90天 M3+
	 *			public static final int CREDITLOANPAY_OVERDUE_UNCOMPLETE = 8;// 续期（该状态催收员不能操作）
	 *			public static final int CREDITLOANPAY_COMPLETE = 2;// 已还款
	 */
	public static int getPayStatus(String status) {
		if (Constant.STATUS_HKZ.equals(status)) {
			return Constant.CREDITLOANPAY_OVERDUE_UNCOMPLETE;
		} else if (Constant.STATUS_YYQ.equals(status) || Constant.STATUS_YHZ.equals(status)) {
			return Constant.CREDITLOANPAY_OVERDUEA;
		} else {
			return Constant.CREDITLOANPAY_COMPLETE;
		}
	}
	/**
	 * 更新订单表
	 * @param loanId  借款id
	 * @param repaymentMap 还款信息
	 * @param status 订单状态 1 催收中，4 还款完成
	 * */
	public static MmanLoanCollectionOrder updateMmanLoanCollectionOrder(ILocalDataDao localDataDao,String loanId,HashMap<String, Object> repaymentMap,String status){
		loger.error("start-updateMmanLoanCollectionOrder-loanId"+loanId);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("ORDER_ID", loanId);
		map.put("USER_ID", repaymentMap.get("user_id"));
		MmanLoanCollectionOrder order = localDataDao.selectLoanOrder(map);
		BigDecimal realMoney = new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("repaymented_amount")))/100.00);
		map.put("REAL_MONEY", realMoney);
		map.put("STATUS",status);
		map.put("updateDate",new Date());
		localDataDao.updateOrderStatus(map);
		loger.error("end-updateMmanLoanCollectionOrder-loanId"+loanId);
		loger.error("end-updateMmanLoanCollectionOrder-order"+order);
		return order;
	}

	/**
	 * 保存用户还款详情表-----（未逾期部分还款本金）不保存当前催收员
	 * */

	public static void saveFirstPayDetail(ILocalDataDao localDataDao,HashMap<String, Object> repayment,String payId,List<HashMap<String,Object>> repaymentDetailList){
		loger.error("未逾期部分还款start-saveCreditLoanPayDetail-payId =" + payId);
		loger.error("repaymentDetailList="+repaymentDetailList);

		List<String> idList = null;
		if(null!=repaymentDetailList && 0<repaymentDetailList.size()){
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("PAY_ID", payId);
			idList = localDataDao.selectCreditLoanPayDetail(map);//查询目前插入的还款记录
		}
		for(int i=0;i<repaymentDetailList.size();i++){
			HashMap<String,Object> repayDetail = repaymentDetailList.get(i);
			String detailId = String.valueOf(repayDetail.get("id"));
			if(checkDetailId(idList, detailId)){
				CreditLoanPayDetail creditLoanPayDetail = new CreditLoanPayDetail();

				creditLoanPayDetail.setId(detailId);
				creditLoanPayDetail.setPayId(payId);
				creditLoanPayDetail.setCreateDate(DateUtil.getDateTimeFormat(String.valueOf(repayDetail.get("created_at")), "yyyy-MM-dd HH:mm:ss"));
				creditLoanPayDetail.setUpdateDate(new Date());
				creditLoanPayDetail.setReturnType(String.valueOf(repayDetail.get("repayment_type")));
				creditLoanPayDetail.setRemark("未逾期部分还款");
				creditLoanPayDetail = syncUtils.operaRealPenltyDetail(repayment, repayDetail, payId,creditLoanPayDetail,localDataDao);
				localDataDao.saveCreditLoanPayDetail(creditLoanPayDetail);
				loger.error("未逾期部分还款end-saveCreditLoanPayDetail-payId =" + payId);
				loger.error("endDate-saveCreditLoanPayDetail:"+DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
				loger.error("creditLoanPayDetail="+creditLoanPayDetail);
			}
		}
	}




	/**
	 * 保存用户还款详情表
	 *
	 *
	 * */
	public static void saveCreditLoanPayDetail(ILocalDataDao localDataDao,HashMap<String, Object> repayment,String payId,List<HashMap<String,Object>> repaymentDetailList){
		loger.error("start-saveCreditLoanPayDetail-payId =" + payId);
		loger.error("startDate-saveCreditLoanPayDetail:"+DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
		loger.error("repaymentDetailList="+repaymentDetailList);

		List<String> idList = null;
		if(null!=repaymentDetailList && 0<repaymentDetailList.size()){
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("PAY_ID", payId);
			idList = localDataDao.selectCreditLoanPayDetail(map);//查询目前插入的还款记录
		}
		CreditLoanPayDetail creditLoanPayDetail =null;
		for(int i=0;i<repaymentDetailList.size();i++){
			HashMap<String,Object> repayDetail = repaymentDetailList.get(i);
			String detailId = String.valueOf(repayDetail.get("id"));
			if(checkDetailId(idList, detailId)){
				//减免罚息    如果还款类型为6，还款状态2.则更新还款表的 减免金额
				syncUtils.ReductionMoney(repayDetail,payId,localDataDao);

				creditLoanPayDetail = new CreditLoanPayDetail();
				creditLoanPayDetail.setId(detailId);
				creditLoanPayDetail.setPayId(payId);
				creditLoanPayDetail.setCreateDate(DateUtil.getDateTimeFormat(String.valueOf(repayDetail.get("created_at")), "yyyy-MM-dd HH:mm:ss"));
				creditLoanPayDetail.setUpdateDate(new Date());
				creditLoanPayDetail.setReturnType(String.valueOf(repayDetail.get("repayment_type")));
				creditLoanPayDetail.setRemark(String.valueOf(repayDetail.get("remark")));
				HashMap<String,String> resultMap = checkOrderByS1(repayDetail,localDataDao);
				if(null!=resultMap){
					String flag = null;
					try{
						flag = resultMap.get("sFlag");
					}catch(Exception e){

					}
					if(StringUtils.isNotBlank(flag)){
						creditLoanPayDetail.setS1Flag(Constant.S_FLAG);
					}
					creditLoanPayDetail.setCurrentCollectionUserId(resultMap.get("currentUserId"));
				}
				creditLoanPayDetail = syncUtils.operaRealPenltyDetail(repayment, repayDetail, payId,creditLoanPayDetail,localDataDao);
				localDataDao.saveCreditLoanPayDetail(creditLoanPayDetail);
				loger.error("end-saveCreditLoanPayDetail-payId =" + payId);
				loger.error("endDate-saveCreditLoanPayDetail:"+DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
				loger.error("creditLoanPayDetail="+creditLoanPayDetail);
			}
		}
	}
	/**
	 * 验证当前还款详情是否已存在
	 * @param idList
	 * @param detailId
	 * @return false 存在，  true 不存在
	 */
	public static boolean checkDetailId(List<String> idList,String detailId){
		if(null!=idList && 0<idList.size()){
			for(int i=0;i<idList.size();i++){
				if(detailId.equals(idList.get(i))){
					return false;
				}
			}
			return true;
		}else{
			return true;
		}
	}

	/**
	 * 验证 纪录2-11号S1组订单到S2人手上的标志
	 * @param repayDetail
	 */
	public static HashMap<String,String> checkOrderByS1(HashMap<String,Object> repayDetail,ILocalDataDao localDataDao){
		String payId = String.valueOf(repayDetail.get("asset_repayment_id"));//还款id
		String orderId = String.valueOf(repayDetail.get("asset_order_id"));//借款id
		String createdDate = String.valueOf(repayDetail.get("created_at"));
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("PAY_ID", payId);
		map.put("ORDER_ID", orderId);
		HashMap<String,Object> orderMap = localDataDao.selectOrderByDetail(map);//查询ourder
		if(null!=orderMap){
			HashMap<String,String> resultMap = new HashMap<String,String>();
			try{
				String currentUserId = String.valueOf(orderMap.get("current_collection_user_id"));
				String sFlag = String.valueOf(orderMap.get("s1_flag"));
				int overdueDays = Integer.parseInt(String.valueOf(orderMap.get("overdueDays")));
				if(StringUtils.isNotBlank(sFlag)){
					if(sFlag.equals(Constant.S_FLAG)){
						boolean bool = operaSflag(createdDate,overdueDays);
						if(bool){
							resultMap.put("sFlag", Constant.S_FLAG);
							resultMap.put("currentUserId", currentUserId);
							return resultMap;
						}
					}
				}
				resultMap.put("currentUserId", currentUserId);
			}catch(Exception e){
				e.printStackTrace();
			}
			return resultMap;
		}
		return null;
	}
	/**
	 * 计算是否在s1里
	 * @param createdDate
	 * @param overdueDays
	 */
	public static boolean operaSflag(String createdDate, int overdueDays) {
		int days = DateUtil.getDateFormats(createdDate,"yyyy-MM-dd HH:mm:ss");//还款时间减去当前时间
		int sDay = overdueDays-days;//逾期的天数-还款延迟天数
		if(sDay<=10){
			return true;
		}
		return false;
	}
	/**
	 * 计算还款
	 * @param repaymentMap 从库还款
	 * @param creditLoanPay 主库还款
	 */
	public static CreditLoanPay operaRealPenlty(HashMap<String,Object> repaymentMap,CreditLoanPay creditLoanPay){
		int repaymentAmount = Integer.parseInt(String.valueOf(repaymentMap.get("repayment_amount")));//总还款金额
		int planLateFee = Integer.parseInt(String.valueOf(repaymentMap.get("plan_late_fee")));//滞纳金
		int repaymentedAmount = Integer.parseInt(String.valueOf(repaymentMap.get("repaymented_amount")));//已还款金额

		int receivablePrinciple = repaymentAmount - planLateFee;// 应还(本金+手续费)

		// 实收的利息 = 已还金额 - 应还(本金+手续费)
		int realPenlty = repaymentedAmount - receivablePrinciple;

		if(realPenlty <= 0){
			creditLoanPay.setReceivablePrinciple(new BigDecimal((receivablePrinciple - repaymentedAmount) / 100.00));//剩余(本金+手续费)
			creditLoanPay.setReceivableInterest(new BigDecimal(planLateFee/100.00));//剩余应还罚息
			creditLoanPay.setRealgetPrinciple(new BigDecimal(repaymentedAmount/100.00));//实收(本金+手续费)
			creditLoanPay.setRealgetInterest(new BigDecimal(0));//实收罚息
		}else{
			creditLoanPay.setReceivablePrinciple(new BigDecimal(0));//剩余应还(本金+手续费)
			creditLoanPay.setReceivableInterest(new BigDecimal((repaymentAmount-repaymentedAmount)/100.00));//剩余应还罚息
			creditLoanPay.setRealgetPrinciple(new BigDecimal((repaymentedAmount-realPenlty)/100.00));//实收(本金+手续费)
			creditLoanPay.setRealgetInterest(new BigDecimal(realPenlty/100.00));//实收罚息
		}
		return creditLoanPay;
	}
	/**
	 * 计算还款详情
	 *
	 */
	public static CreditLoanPayDetail operaRealPenltyDetail(HashMap<String,Object> repaymentMap,HashMap<String,Object> repayDetail,String payId,CreditLoanPayDetail creditLoanPayDetail,ILocalDataDao localDataDao){
		int repaymentAmount =  Integer.parseInt(String.valueOf(repaymentMap.get("repayment_amount")));//总还款金额
		int planLateFee = Integer.parseInt(String.valueOf(repaymentMap.get("plan_late_fee")));//滞纳金
//		int repaymentedAmount = Integer.parseInt(String.valueOf(repaymentMap.get("repaymented_amount")));//已还款金额

		int receivablePrinciple = repaymentAmount - planLateFee;// （应还本金+服务费）

		int trueRepaymentMoney = Integer.parseInt(String.valueOf(repayDetail.get("true_repayment_money")));//还款金额，从库中传过来

		int detailCount = localDataDao.getDetailCount(payId);//还款详情表中payId还款条数

		if(detailCount == 0){
			if(trueRepaymentMoney >= receivablePrinciple){
				creditLoanPayDetail.setRealMoney(new BigDecimal(receivablePrinciple/100.00));//实收本金+服务费
				int realPenlty = trueRepaymentMoney-receivablePrinciple;//实收罚息
				creditLoanPayDetail.setRealPenlty(new BigDecimal(realPenlty/100.00));
				creditLoanPayDetail.setRealPrinciple(new BigDecimal(0));
				int realInterest = planLateFee-realPenlty;
				creditLoanPayDetail.setRealInterest(new BigDecimal(realInterest/100.00));
			}else{
				creditLoanPayDetail.setRealMoney(new BigDecimal(trueRepaymentMoney/100.00));
				creditLoanPayDetail.setRealPenlty(new BigDecimal(0));
				creditLoanPayDetail.setRealPrinciple(new BigDecimal((receivablePrinciple-trueRepaymentMoney)/100.00));
				creditLoanPayDetail.setRealInterest(new BigDecimal(planLateFee/100.00));
			}

		}else{
			CreditLoanPaySum creditLoanPaySum = localDataDao.sumRealMoneyAndPenlty(payId);
			BigDecimal sumRealMoneyBig = creditLoanPaySum.getSumRealMoney().setScale(2,   BigDecimal.ROUND_HALF_UP);
			BigDecimal sumRealPenltyBig = creditLoanPaySum.getSumRealPenlty().setScale(2,   BigDecimal.ROUND_HALF_UP);
			BigDecimal big100 = new BigDecimal(100);

			int sumRealMoney = sumRealMoneyBig.multiply(big100).intValue();;//总实收本金
			int sumRealPenlty = sumRealPenltyBig.multiply(big100).intValue();//总实收罚息
			if(sumRealMoney >= receivablePrinciple){
				creditLoanPayDetail.setRealMoney(new BigDecimal(0));
				creditLoanPayDetail.setRealPenlty(new BigDecimal(trueRepaymentMoney/100.00));
				creditLoanPayDetail.setRealPrinciple(new BigDecimal(0));
				creditLoanPayDetail.setRealInterest(new BigDecimal((planLateFee-sumRealPenlty-trueRepaymentMoney)/100.00));
			}else{

				if((sumRealMoney+trueRepaymentMoney)<receivablePrinciple){
					creditLoanPayDetail.setRealMoney(new BigDecimal(trueRepaymentMoney/100.00));
					creditLoanPayDetail.setRealPenlty(new BigDecimal(0));
					int realPrinciple = receivablePrinciple-(sumRealMoney+trueRepaymentMoney);//剩余应还本金
					creditLoanPayDetail.setRealPrinciple(new BigDecimal(realPrinciple/100.00));
					creditLoanPayDetail.setRealInterest(new BigDecimal(planLateFee/100.00));
				}else{
					int realPenlty = sumRealMoney+trueRepaymentMoney-receivablePrinciple;//实收罚息
					creditLoanPayDetail.setRealMoney(new BigDecimal((trueRepaymentMoney-realPenlty)/100.00));
					creditLoanPayDetail.setRealPenlty(new BigDecimal(realPenlty/100.00));
					creditLoanPayDetail.setRealPrinciple(new BigDecimal(0));
					creditLoanPayDetail.setRealInterest(new BigDecimal((planLateFee-realPenlty)/100.00));
				}
			}

		}
		return creditLoanPayDetail;
	}


	public static void saveUserInfo(ILocalDataDao localDataDao,String payId,String userId,HashMap<String, Object> userInfo,List<HashMap<String, Object>> userContactsList,HashMap<String, Object> cardInfo){
		payId = payId;
		if(checkUserInfo(localDataDao,userId)) {//如果借款人信息不存在
			//保存用户信息
			userInfo.put("user_from", 0);
			localDataDao.saveMmanUserInfo(userInfo);
			//保存用户联系人
			saveMmanUserRela(payId,userInfo, userContactsList,localDataDao);
			//保存银行卡
			saveUpdateSysUserBankCard(userId,localDataDao,cardInfo, IdGen.uuid());
		}else{//借款人信息存在
			if(checkUserRela(localDataDao,userId)){//通讯录不存在
				//保存用户联系人
				saveMmanUserRela(payId,userInfo, userContactsList,localDataDao);
			}
			//更新银行卡
			saveUpdateSysUserBankCard(userId,localDataDao,cardInfo,null);
		}
	}
	//保存用户联系人
	private static void saveMmanUserRela(String payId,HashMap<String,Object> userInfo,List<HashMap<String,Object>> userContactsList,ILocalDataDao localDataDao){
		List<ContactList> contactList = null;
		try{
			contactList = JxlJsonUtil.operaJxlDetail(String.valueOf(userInfo.get("jxl_detail")));
		}catch(Exception e){
			loger.error("解析聚信立异常-payId"+payId);
		}
		MmanUserRela mmanUserRela = null;
		//保存第一联系人
		saveMmanUserRelas("firstContact",mmanUserRela,userInfo,userContactsList,contactList,localDataDao);
		//保存第二联系人
		saveMmanUserRelas("secondContact",mmanUserRela,userInfo,userContactsList,contactList,localDataDao);
		//保存其他联系人
		saveMmanUserRelas("otherContact",mmanUserRela,userInfo,userContactsList,contactList,localDataDao);
	}

	private static void saveMmanUserRelas(String flag,MmanUserRela mmanUserRela, HashMap<String, Object> userInfo, List<HashMap<String, Object>> userContactsList, List<ContactList> contactList,ILocalDataDao localDataDao) {
		mmanUserRela = new MmanUserRela();
		String phoneNmuber = null;
		mmanUserRela.setUserId(String.valueOf(userInfo.get("id")));
		mmanUserRela.setDelFlag("0");
		if (flag.equals("firstContact")){//第一联系人
			mmanUserRela.setId(IdGen.uuid());
			phoneNmuber = String.valueOf(userInfo.get("firstContactPhone"));
			mmanUserRela.setContactsKey("1");
			mmanUserRela.setInfoName(String.valueOf(userInfo.get("firstContactName")));
			mmanUserRela.setInfoValue(phoneNmuber);
			mmanUserRela.setRelaKey(String.valueOf(userInfo.get("fristContactRelation")));
			//保存第一联系人
			saveUserRael(localDataDao,contactList,mmanUserRela,phoneNmuber);
		}else if (flag.equals("secondContact")){//第二联系人
			mmanUserRela.setId(IdGen.uuid());
			phoneNmuber = String.valueOf(userInfo.get("secondContactPhone"));
			mmanUserRela.setContactsKey("2");
			mmanUserRela.setInfoName(String.valueOf(userInfo.get("secondContactName")));
			mmanUserRela.setInfoValue(phoneNmuber);
			mmanUserRela.setRelaKey(String.valueOf(userInfo.get("secondContactRelation")));
			//保存第二联系人
			saveUserRael(localDataDao,contactList,mmanUserRela,phoneNmuber);
		}else {//其他联系人
			for(int i=0;i<userContactsList.size();i++){
				mmanUserRela.setId(IdGen.uuid());
				HashMap<String,Object> userRela = userContactsList.get(i);
				phoneNmuber = String.valueOf(userRela.get("contactPhone"));
				mmanUserRela.setInfoName(String.valueOf(userRela.get("contactName")));
				mmanUserRela.setInfoValue(phoneNmuber);
				//保存其他联系人
				saveUserRael(localDataDao,contactList,mmanUserRela,phoneNmuber);
			}
		}
	}
	/**
	 * 设置联系人属性 --保存联系人
	 * @param contactList
	 * @param mmanUserRela
	 * @param phoneNmuber
	 */
	public static void saveUserRael(ILocalDataDao localDataDao,List<ContactList> contactList, MmanUserRela mmanUserRela,String phoneNmuber) {
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
			localDataDao.saveMmanUserRela(mmanUserRela);
		}catch (Exception e){
			loger.error("联系人信息错误：payId"+"错误值："+phoneNmuber,e);
		}
	}
	// 保存-更新用户银行卡
	private static void saveUpdateSysUserBankCard(String userId,ILocalDataDao localDataDao,HashMap<String,Object> cardInfo,String uuid){
		if (cardInfo != null){
			SysUserBankCard bankCard = new SysUserBankCard();
			bankCard.setUserId(String.valueOf(cardInfo.get("userId")));
			bankCard.setBankCard(String.valueOf(cardInfo.get("card_no")));
			bankCard.setDepositBank(String.valueOf(cardInfo.get("bankName")));
			bankCard.setBankInstitutionNo(String.valueOf(cardInfo.get("bank_id")));
			bankCard.setName(String.valueOf(cardInfo.get("openName")));
			bankCard.setMobile(String.valueOf(cardInfo.get("phone")));
			if (cardInfo.get("bank_address") != null){
				bankCard.setCityName(String.valueOf(cardInfo.get("bank_address")));
			}else {
				bankCard.setCityName("");
			}
			if (uuid != null && !uuid.equals("")){
				bankCard.setId(IdGen.uuid());
				localDataDao.saveSysUserBankCard(bankCard);
			}else{
				localDataDao.updateSysUserBankCard(bankCard);
			}
		}else {
			loger.error("银行卡信息为空：userId="+userId);
		}

	}

	/**
	 * 验证用户是否存在
	 */
	public static boolean checkUserInfo(ILocalDataDao localDataDao,String userId){
		if(StringUtils.isNotBlank(userId)){
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("ID", userId);
			int count = localDataDao.checkUserInfo(map);
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
	public static boolean checkUserRela(ILocalDataDao localDataDao,String userId){
		if(StringUtils.isNotBlank(userId)){
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("ID", userId);
			int count = localDataDao.checkUserRela(map);
			if(count>0){
				return false;
			}
			return true;//不存在
		}
		return false;
	}

	/**
	 * 还款完成更新订单表和催收流转日志
	 */

	public static void updateOrderAndLog(String loanId,HashMap<String, Object> repaymentMap,ILocalDataDao localDataDao,String payId){
		MmanLoanCollectionOrder order = null;
		//更新订单
		order = syncUtils.updateMmanLoanCollectionOrder(localDataDao,loanId,repaymentMap,Constant.STATUS_OVERDUE_FOUR);

		BackUser backUser = null;
		if(null!=order){
			String backUserId = order.getCurrentCollectionUserId();
			HashMap<String,Object> umap = new HashMap<String,Object>();
			umap.put("ID", backUserId);
			backUser = localDataDao.selectBackUser(umap);
		}
		if(null!=backUser){
			loger.error("start-saveLoanChangeLog-loanId"+loanId);
			loger.error("startDate-saveLoanChangeLog-loanId"+DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
			MmanLoanCollectionStatusChangeLog loanChangeLog = new MmanLoanCollectionStatusChangeLog();
			loanChangeLog.setId(IdGen.uuid());
			loanChangeLog.setLoanCollectionOrderId(order.getOrderId());
			loanChangeLog.setBeforeStatus(order.getStatus());
			loanChangeLog.setAfterStatus(Constant.STATUS_OVERDUE_FOUR);
			loanChangeLog.setType(Constant.STATUS_OVERDUE_FIVE);//催收完成
			loanChangeLog.setOperatorName(Constant.OPERATOR_NAME);
			loanChangeLog.setRemark(Constant.PAY_MENT_SUCCESS+backUser.getUserName());
			loanChangeLog.setCompanyId(backUser.getCompanyId());
			loanChangeLog.setCurrentCollectionUserId(backUser.getUuid());
			loanChangeLog.setCurrentCollectionUserLevel(backUser.getGroupLevel());
			if(StringUtils.isNotBlank(order.getS1Flag())){
				loanChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_S1);
			}else{
				loanChangeLog.setCurrentCollectionOrderLevel(order.getCurrentOverdueLevel());
			}
			loanChangeLog.setCreateDate(new Date());
			localDataDao.saveLoanChangeLog(loanChangeLog);
			loger.error("end-saveLoanChangeLog-loanId"+loanId);
			loger.error("endDate-saveLoanChangeLog-loanId"+DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
		}

	}

}