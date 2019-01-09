package com.info.web.synchronization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.info.back.dao.ILocalDataDao;
import com.info.back.service.IMmanLoanCollectionOrderService;
import com.info.back.utils.BackConstant;
import com.info.back.utils.IdGen;
import com.info.config.PayContents;
import com.info.constant.Constant;
import com.info.web.pojo.CreditLoanPay;
import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.pojo.MmanUserLoan;
import com.info.web.synchronization.dao.IDataDao;
import com.info.web.util.DateUtil;
import com.info.web.util.HttpUtil;
import org.apache.commons.collections.CollectionUtils;
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

	private static Logger logger = Logger.getLogger(OperaOverdueDataThread.class);
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
			logger.error("sync-OperaOverdueDataThread:"+payId);
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("ID", payId);//还款id
			//还款信息--app端
			HashMap<String,Object> repayment = this.dataDao.getAssetRepayment(map);
			logger.info("sync-repayment:"+repayment);
			String loanId = String.valueOf(repayment.get("asset_order_id"));//借款id
			String userId  = String.valueOf(repayment.get("user_id"));    	//用户id
			map.put("ORDER_ID", loanId);
			map.put("USER_ID", userId);
			String repaymentMoney =String.valueOf(repayment.get("repayment_amount"));
			String repaymentedMoney = String.valueOf(repayment.get("repaymented_amount"));

			if (null != repayment &&(!repaymentMoney.equals(repaymentedMoney))) {
				try {
					HashMap<String, Object> borrowOrder = null;					//借款信息--app端
					List<HashMap<String, Object>> repaymentDetailList = null;	//还款详情信息--app端
					Map<String, Object> userInfo = null;					//用户信息--app端
					Map<String, Object> cardInfo = null;					//银行卡--app端
					List<Map<String, Object>> userContactsList = null;		//用户联系人--app端

					borrowOrder = this.dataDao.getAssetBorrowOrder(map);
					logger.info("sync-borrowOrder:"+borrowOrder);
					repaymentDetailList = this.dataDao.getAssetRepaymentDetail(map);
					if (checkLoan(loanId)) {
						try{
							Map<String, String> map2 = new HashMap();
							map2.put("userId",borrowOrder.get("user_id").toString());
							map2.put("merchantNumber","cjxjx");//默认小额推逾期，商户号都是cjxjx；如之后有其他商户渠道，则需修改
							String returnInfo = HttpUtil.getInstance().doPost2(PayContents.XJX_GET_USERINFOS, JSON.toJSONString(map2));
//							logger.error("调用vip查询用户信息："+returnInfo);
							Map<String, Object> o = (Map<String, Object>) JSONObject.parse(returnInfo);
							if(o != null && "00".equals(String.valueOf(o.get("code")))){
								Map<String,Object> data = (Map<String, Object>) o.get("data");
								userInfo = (Map<String, Object>) data.get("user");
								cardInfo = ((List<Map<String, Object>>) data.get("userCardInfoList")).get(0);
								userContactsList = (List<Map<String, Object>>) data.get("userContactsList");
							}
						}catch (Exception e){
							logger.error("调用cashman获取用户信息出错：", e);
							return;
						}

						if (null != userInfo && null != borrowOrder&& null != cardInfo) {
							String realname = borrowOrder.get("realname").toString();//借款用户真实姓名
							String userPhone = borrowOrder.get("user_phone").toString();//借款用户手机号
							String idNumber = borrowOrder.get("id_number").toString();//借款用户身份证号码
							logger.info("handel-data-to-cuishou-LoanId："+loanId);
							int payCount = localDataDao.checkPay(payId);
							if (payCount < 1){
								//保存还款表
								saveCreditLoanPay(repayment);
								logger.info("保存还款表-payId："+payId);
							}
							if (CollectionUtils.isNotEmpty(repaymentDetailList)){
								//保存还款详情表
								logger.info("保存还款详情表-payId："+payId);
								syncUtils.saveFirstPayDetail(localDataDao,repayment,payId, repaymentDetailList);
							}
							int orderCount = localDataDao.checkOrder(loanId);
							if (orderCount < 1){
								logger.info("处理订单表-loanId："+loanId+"orderCount:"+orderCount);
								MmanLoanCollectionOrder order = new MmanLoanCollectionOrder();
								order.setId(IdGen.uuid());
								order.setLoanId(loanId);
								order.setPayId(payId);
								order.setOrderId(String.valueOf(borrowOrder.get("out_trade_no")));
								order.setUserId(userId);//借款用户id
								order.setOverdueDays(Integer.valueOf(repayment.get("late_day").toString()));
								order.setStatus(BackConstant.XJX_COLLECTION_ORDER_STATE_WAIT);//订单状态 默认为“待催收”
								order.setRealMoney(new BigDecimal(Integer.parseInt(String.valueOf(repayment.get("repaymented_amount")))).divide(new BigDecimal(100)));//已还金额
								order.setDispatchName("系统");
								order.setDispatchTime(new Date());
								order.setCurrentOverdueLevel(BackConstant.XJX_OVERDUE_LEVEL_S1);//逾期等级 默认为S1
								order.setCreateDate(new Date());
								order.setOperatorName("系统");
								order.setRemark("系统派单");
								order.setLoanUserName(realname);
								order.setLoanUserPhone(userPhone);
								order.setIdNumber(idNumber);
								//分单逻辑
								logger.error("start-distributeOrder-loanId:"+loanId);
								Boolean result = this.orderService.distributeOrder(order,borrowOrder.get("merchant_number").toString());
								if (result){
									logger.error("end-distributeOrder-loanId-result:"+loanId+result);
								}else {
									return;
								}
							}
							if (checkLoan(loanId)){
								//保存用户借款表
								logger.info("保存用户借款表-start:");
								saveMmanUserLoan(borrowOrder,repayment);
								logger.info("saveMmanUserLoan-end:");
								RedisUtil.delRedisKey2(Constant.TYPE_OVERDUE_ +payId+"*");
							}

							//保存用户信息表--联系人表--银行卡
							syncUtils.saveUserInfo(localDataDao,payId,userId,userInfo,userContactsList,cardInfo);
						}








//						this.orderService.dispatchOrderNew(loanId,userInfo.get("idNumber").toString(),Constant.SMALL);
//						this.taskJobMiddleService.dispatchforLoanId(loanId,userInfo.get("id_number").toString(),Constant.SMALL);
//						if (repaymentDetailList != null && repaymentDetailList.size()>0){
//							logger.info("未逾期部分还款:"+loanId);
//							syncUtils.updateMmanLoanCollectionOrder(localDataDao,loanId,repayment,Constant.STATUS_OVERDUE_ONE);
//						}
					} else {
						logger.info("更新操作-loanId:"+loanId);
						if (null != borrowOrder && null != repaymentDetailList) {
							//更新用户借款表
							logger.info("更新用户借款表");
							syncUtils.updateMmanUserLoan(localDataDao,loanId, repayment,Constant.STATUS_OVERDUE_FOUR);
							//更新还款表
							syncUtils.updateCreditLoanPay(localDataDao,payId,repayment);
							//保存还款详情表
							syncUtils.saveCreditLoanPayDetail(localDataDao,repayment,payId, repaymentDetailList);
							//更新订单表
							syncUtils.updateMmanLoanCollectionOrder(localDataDao,loanId,repayment,Constant.STATUS_OVERDUE_ONE);
						}
//						//验证是否减免
//						syncUtils.checkReduction(repayment,localDataDao);
						RedisUtil.delRedisKey2(Constant.TYPE_OVERDUE_ +payId+"*");
					}
				} catch (Exception e0) {
					logger.error("OperaOverdueDataThread-异常-loanId"+loanId);
					e0.printStackTrace();
				}
			}else{
				RedisUtil.delRedisKey2(Constant.TYPE_OVERDUE_ +payId+"*");
			}
		}
	}

	/**
	 * 保存用户借款表
	 * @param repaymentMap  还款信息
	 * @param borrowOrder  借款信息
	 * */
	public  void saveMmanUserLoan(HashMap<String,Object> borrowOrder,HashMap<String,Object> repaymentMap){
		logger.info("start-saveMmanUserLoan:"+String.valueOf(borrowOrder.get("id")));
		MmanUserLoan mmanUserLoan = new MmanUserLoan();
		mmanUserLoan.setId(String.valueOf(borrowOrder.get("id")));
		mmanUserLoan.setUserId(String.valueOf(borrowOrder.get("user_id")));
		mmanUserLoan.setLoanPyId(String.valueOf(borrowOrder.get("out_trade_no")));//第三方订单号
		mmanUserLoan.setLoanMoney(new BigDecimal(Integer.parseInt(String.valueOf(borrowOrder.get("money_amount")))/100.00));
		mmanUserLoan.setLoanRate(String.valueOf(borrowOrder.get("apr")));
		mmanUserLoan.setPaidMoney(new BigDecimal(Integer.parseInt(String.valueOf(borrowOrder.get("paid_money")))/100.00));//服务费+本金


//		if (TXLC_MERCHANT_NUMBER.equals(String.valueOf(borrowOrder.get("merchant_number"))) || YMJK_MERCHANT_NUMBER.equals(String.valueOf(borrowOrder.get("merchant_number")))){
//			mmanUserLoan.setPaidMoney(new BigDecimal(0));
//		}else {
//			mmanUserLoan.setPaidMoney(new BigDecimal(Integer.parseInt(String.valueOf(borrowOrder.get("paid_money")))/100.00));//服务费+本金
//		}


		mmanUserLoan.setLoanPenalty(new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("plan_late_fee")))/100.00));
		mmanUserLoan.setServiceCharge(new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("repayment_interest")))/100.00));//服务费
		mmanUserLoan.setLoanPenaltyRate(String.valueOf(repaymentMap.get("late_fee_apr")));
		mmanUserLoan.setLoanEndTime(DateUtil.getDateTimeFormat(String.valueOf(repaymentMap.get("repayment_time")), "yyyy-MM-dd HH:mm:ss"));
		mmanUserLoan.setLoanStartTime(DateUtil.getDateTimeFormat(String.valueOf(repaymentMap.get("credit_repayment_time")), "yyyy-MM-dd HH:mm:ss"));
		mmanUserLoan.setUpdateTime(new Date());
		mmanUserLoan.setLoanStatus(Constant.STATUS_OVERDUE_FOUR);//4：逾期
		mmanUserLoan.setCreateTime(new Date());
		mmanUserLoan.setDelFlag("0");//0正常1：删除
		mmanUserLoan.setCustomerType(Integer.valueOf(borrowOrder.get("customer_type") == null ? "0" : borrowOrder.get("customer_type").toString()));   // 标识新老用户 0 新用户  1 老用户
		mmanUserLoan.setBorrowingType(Constant.SMALL);
		mmanUserLoan.setMerchantNo(String.valueOf(borrowOrder.get("merchant_number")));
		mmanUserLoan.setRepayChannel(Integer.parseInt(String.valueOf(repaymentMap.get("repay_channel"))));
		//TODO 优化渠道来源
		mmanUserLoan.setChannelFrom(borrowOrder.get("order_from").toString());
		this.localDataDao.saveMmanUserLoan(mmanUserLoan);
		logger.info("end-saveMmanUserLoan:"+String.valueOf(borrowOrder.get("id")));
	}
	/**
	 * 保存用户还款表
	 * @param repaymentMap  还款信息
	 * */
	private void saveCreditLoanPay(HashMap<String,Object> repaymentMap){
		logger.info("start-saveCreditLoanPay:"+String.valueOf(repaymentMap.get("id")));
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
			logger.info("end-saveCreditLoanPay:"+String.valueOf(repaymentMap.get("id")));
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
