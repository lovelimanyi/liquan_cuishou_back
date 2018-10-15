package com.info.web.synchronization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.info.back.dao.ILocalDataDao;
import com.info.back.service.IMmanLoanCollectionOrderService;
import com.info.back.service.TaskJobMiddleService;
import com.info.back.utils.IdGen;
import com.info.back.vo.jxl.ContactList;
import com.info.config.PayContents;
import com.info.constant.Constant;
import com.info.web.pojo.*;
import com.info.web.synchronization.dao.IDataDao;
import com.info.web.util.DateUtil;
import com.info.web.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private IMmanLoanCollectionOrderService orderService;

	public OperaOverdueDataThread(String payId, IDataDao dataDao,ILocalDataDao localDataDao,IMmanLoanCollectionOrderService orderService) {
		this.payId = payId;
		this.dataDao = dataDao;
		this.localDataDao = localDataDao;
		this.orderService = orderService;
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
			loger.info("sync-borrowOrders:"+repayment);
			String loanId = String.valueOf(repayment.get("asset_order_id"));//借款id
			String userId  = String.valueOf(repayment.get("user_id"));    	//用户id
			map.put("ORDER_ID", loanId);
			map.put("USER_ID", userId);
			String repaymentMoney =String.valueOf(repayment.get("repayment_amount"));
			String repaymentedMoney = String.valueOf(repayment.get("repaymented_amount"));
			loger.info("sync-repaymentMoney:"+payId+":"+repaymentMoney);
            loger.info("sync-repaymentedMoney:"+payId+":"+repaymentedMoney);
			if (null != repayment &&(!repaymentMoney.equals(repaymentedMoney))) {
				try {
					HashMap<String, Object> borrowOrder = null;					//借款信息--app端
					List<HashMap<String, Object>> repaymentDetailList = null;	//还款详情信息--app端
					Map<String, Object> userInfo = null;					//用户信息--app端
					Map<String, Object> cardInfo = null;					//银行卡--app端
					List<Map<String, Object>> userContactsList = null;		//用户联系人--app端

					borrowOrder = this.dataDao.getAssetBorrowOrder(map);
					loger.info("sync-borrowOrder:"+borrowOrder);
					repaymentDetailList = this.dataDao.getAssetRepaymentDetail(map);
					loger.info("sync-repaymentDetailList:"+repaymentDetailList);
					loger.info("开始:"+borrowOrder);
					if (checkLoan(loanId)) {
//						userInfo = this.dataDao.getUserInfo(map);
//						cardInfo = this.dataDao.getUserCardInfo(map);
//						userContactsList = this.dataDao.getUserContacts(map);
						try{
							Map<String, String> map2 = new HashMap();
							map2.put("userId",borrowOrder.get("user_id").toString());
							map2.put("merchantNumber","cjxjx");//默认小额推逾期，商户号都是cjxjx；如之后有其他商户渠道，则需修改
							String returnInfo = HttpUtil.getInstance().doPost2(PayContents.XJX_GET_USERINFOS, JSON.toJSONString(map2));
//							loger.error("调用vip查询用户信息："+returnInfo);
							Map<String, Object> o = (Map<String, Object>) JSONObject.parse(returnInfo);
							if(o != null && "00".equals(String.valueOf(o.get("code")))){
								Map<String,Object> data = (Map<String, Object>) o.get("data");
								userInfo = (Map<String, Object>) data.get("user");
								cardInfo = ((List<Map<String, Object>>) data.get("userCardInfoList")).get(0);
								userContactsList = (List<Map<String, Object>>) data.get("userContactsList");
							}
						}catch (Exception e){
							loger.error("调用cashman获取用户信息出错：" + e);
							e.printStackTrace();
							return;
						}


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
							syncUtils.saveFirstPayDetail(localDataDao,repayment,payId, repaymentDetailList);
							loger.info("保存还款详情表");
							//保存用户信息表--联系人表--银行卡
							syncUtils.saveUserInfo(localDataDao,payId,userId,userInfo,userContactsList,cardInfo);
						}
						this.orderService.dispatchOrderNew(loanId,userInfo.get("idNumber").toString(),Constant.SMALL);
//						this.taskJobMiddleService.dispatchforLoanId(loanId,userInfo.get("id_number").toString(),Constant.SMALL);
                        if (repaymentDetailList != null && repaymentDetailList.size()>0){
                            loger.info("未逾期部分还款:"+loanId);
							syncUtils.updateMmanLoanCollectionOrder(localDataDao,loanId,repayment,Constant.STATUS_OVERDUE_ONE);
						}
						RedisUtil.delRedisKey(Constant.TYPE_OVERDUE_ + payId+"_"+PayContents.MERCHANT_NUMBER.toString());
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
						RedisUtil.delRedisKey(Constant.TYPE_OVERDUE_ + payId+"_"+PayContents.MERCHANT_NUMBER.toString());
					}
				} catch (Exception e0) {
					loger.error("OperaOverdueDataThread-异常-loanId"+loanId);
					e0.printStackTrace();
				}
			}else{
				RedisUtil.delRedisKey(Constant.TYPE_OVERDUE_ + payId+"_"+PayContents.MERCHANT_NUMBER.toString());
			}
		}
	}

	/**
	 * 保存用户借款表
	 * @param repaymentMap  还款信息
	 * @param borrowOrder  借款信息
	 * */
	public  void saveMmanUserLoan(HashMap<String,Object> borrowOrder,HashMap<String,Object> repaymentMap,Map<String,Object> userInfo){
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
		mmanUserLoan.setBorrowingType(Constant.SMALL);
		mmanUserLoan.setMerchantNo(String.valueOf(borrowOrder.get("merchant_number")));
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
