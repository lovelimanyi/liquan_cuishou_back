package com.info.web.synchronization;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.info.back.utils.BackConstant;
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
		mmanUserLoan.setLoanPenalty(new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("plan_late_fee")))/100));//滞纳金
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

}
