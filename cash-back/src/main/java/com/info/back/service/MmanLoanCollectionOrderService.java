package com.info.back.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.back.dao.*;
import com.info.web.pojo.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.info.web.util.PageConfig;
import com.info.back.result.JsonResult;
import com.info.back.utils.IdGen;
import com.info.constant.Constant;

@Service
public class MmanLoanCollectionOrderService implements IMmanLoanCollectionOrderService{
	protected Logger logger = Logger.getLogger(MmanLoanCollectionOrderService.class);
	@Autowired
	private IMmanLoanCollectionOrderDao mmanLoanCollectionOrderDao;
	
	@Autowired
	private IMmanLoanCollectionOrderDao manLoanCollectionOrderDao;
	
	@Autowired
	private IOrderPaginationDao orderPaginationDao;
	
	@Autowired
	private IMmanUserInfoDao mmanUserInfoDao;
    @Autowired
	private ICreditLoanPayDao creditLoanPayDao;
    @Autowired
	private IMmanUserLoanDao mmanUserLoanDao;
	@Autowired
	private IOperationRecordService operationRecordService;
    
	public List<MmanLoanCollectionOrder> getOrderList(MmanLoanCollectionOrder mmanLoanCollectionOrder) {
		return mmanLoanCollectionOrderDao.getOrderList(mmanLoanCollectionOrder);
	}

	@Override
	public PageConfig<MmanLoanCollectionOrder> findPage(HashMap<String, Object> params) {
		PageConfig<MmanLoanCollectionOrder> page = new PageConfig<MmanLoanCollectionOrder>();
		page = orderPaginationDao.findPage("getOrderPage", "getOrderPageCount", params, null);
		return page;
	}
	public List<MmanLoanCollectionOrder> findList(MmanLoanCollectionOrder queryManLoanCollectionOrder) {
		return manLoanCollectionOrderDao.findList(queryManLoanCollectionOrder);
	}

	
	public void saveMmanLoanCollectionOrder(MmanLoanCollectionOrder order) {
		
		if(order!=null && StringUtils.isNotBlank(order.getUserId())){
			MmanUserInfo mmanUserInfo = mmanUserInfoDao.get(order.getUserId());
			if(null!=mmanUserInfo){
				order.setLoanUserName(mmanUserInfo.getRealname());
				order.setLoanUserPhone(mmanUserInfo.getUserPhone());
			}
		}
		
		
		if(StringUtils.isBlank(order.getId())){
			order.setId(IdGen.uuid());
			order.setCreateDate(new Date());
			order.setUpdateDate(new Date());
			manLoanCollectionOrderDao.insertCollectionOrder(order);
		}else{
			order.setUpdateDate(new Date());
			manLoanCollectionOrderDao.updateCollectionOrder(order);
		}
		
	}
	@Override
	public PageConfig<OrderBaseResult> getPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "MmanLoanCollectionOrder");
		PageConfig<OrderBaseResult> page = new PageConfig<OrderBaseResult>();
		page = orderPaginationDao.findPage("getCollectionOrderList", "getCollectionOrderCount", params, null);
		// 插入操作记录
		insertOperateRecord(params);
		return page;
	}

	private void insertOperateRecord(HashMap<String, Object> params) {
		OperationRecord record = new OperationRecord();
		record.setSource(params.get("source") == null ? null : Integer.valueOf(params.get("source").toString()));   // 来源  总订单
		record.setBeginCollectionTime(params.get("collectionBeginTime") == null ? null : params.get("collectionBeginTime").toString());
		record.setEndCollectionTime(params.get("collectionEndTime") == null ? null : params.get("collectionEndTime").toString());
		record.setBeginDispatchTime(params.get("dispatchBeginTime") == null ? null : params.get("dispatchBeginTime").toString());
		record.setEndDispatchTime(params.get("dispatchEndTime") == null ? null : params.get("dispatchEndTime").toString());
		record.setBeginOverDuedays(params.get("overDueDaysBegin") == null ? null : Integer.valueOf(params.get("overDueDaysBegin").toString()));
		record.setEndOverDuedays(params.get("overDueDaysEnd") == null ? null : Integer.valueOf(params.get("overDueDaysEnd").toString()));
		record.setCollectionCompanyId(params.get("companyId") == null ? null : params.get("companyId").toString());
		record.setCollectionGroup(params.get("collectionGroup") == null ? null : params.get("collectionGroup").toString());
		record.setCurrentCollectionUserName(params.get("collectionRealName") == null ? null : params.get("collectionRealName").toString());
		record.setCollectionStatus(params.get("status") == null ? null : params.get("status").toString());
		record.setFollowUpGrad(params.get("topImportant") == null ? null : params.get("topImportant").toString());
		record.setLoanUserName(params.get("loanRealName") == null ? null : params.get("loanRealName").toString());
		record.setLoanUserPhone(params.get("loanUserPhone") == null ? null : params.get("loanUserPhone").toString());
		record.setOperateUserAccount(params.get("currentUserAccount") == null ? null : params.get("currentUserAccount").toString());
		record.setLoanId(params.get("loanId") == null ? null : params.get("loanId").toString());
		record.setOperateTime(new Date());
		operationRecordService.insert(record);
	}

	@Override
	public PageConfig<OrderBaseResult> getCollectionUserPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "MmanLoanCollectionOrder");
		PageConfig<OrderBaseResult> page = new PageConfig<OrderBaseResult>();
		page = orderPaginationDao.findPage("getCollectionOrderList", "getOrderCount", params, null);
		// 插入操作记录
		insertOperateRecord(params);
		return page;
	}

	@Override
	public int findAllCount(HashMap<String, Object> params) {
		return manLoanCollectionOrderDao.getOrderPageCount(params);
	}

	@Override
	public void updateRecord(MmanLoanCollectionOrder mmanLoanCollectionOrder) {
		manLoanCollectionOrderDao.updateCollectionOrder(mmanLoanCollectionOrder);
	}
	@Override
	public MmanLoanCollectionOrder getOrderById(String id) {
		return manLoanCollectionOrderDao.getOrderById(id);
	}

	@Override
	public MmanLoanCollectionOrder getOrderByUserId(String userId) {
		return manLoanCollectionOrderDao.getOrderByUserId(userId);
	}

	@Override
	public OrderBaseResult getBaseOrderById(String orderId) {
		return manLoanCollectionOrderDao.getBaseOrderById(orderId);
	}

	@Override
	public JsonResult saveTopOrder(Map<String, Object> params) {
		JsonResult result=new JsonResult("-1","标记订单重要程度失败");
		if(StringUtils.isNotBlank(params.get("id")+"")&&StringUtils.isNotBlank(params.get("topLevel")+"")){
			int count=manLoanCollectionOrderDao.updateTopOrder(params);
			if(count>0){
				result.setCode("0");
				result.setMsg("");
			}
		}
		return result;
	}

	@Override
	public MmanLoanCollectionOrder getOrderWithId(String orderId) {
		return manLoanCollectionOrderDao.getOrderWithId(orderId);
	}

	@Override
	public PageConfig<OrderBaseResult> getMyPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "MmanLoanCollectionOrder");
		PageConfig<OrderBaseResult> page = new PageConfig<OrderBaseResult>();
		page = orderPaginationDao.findPage("getCollectionMyOrderList", "getCollectionMyOrderCount", params, null);
		return page;
	}

	@Override
	public int updateJmStatus(MmanLoanCollectionOrder collectionOrder) {
		return manLoanCollectionOrderDao.updateJmStatus(collectionOrder);
	}

	@Override
	public HashMap<String, Object> toReductionPage(HashMap<String, Object> params) {
		try {
			if (StringUtils.isNotBlank(params.get("id").toString())) {
				MmanLoanCollectionOrder order = mmanLoanCollectionOrderDao.getOrderById(params.get("id").toString());
				CreditLoanPay pay = creditLoanPayDao.findByLoanId(order.getLoanId());
				MmanUserLoan loan = mmanUserLoanDao.get(order.getLoanId());
				//应还金额
				BigDecimal receivableMoney = pay.getReceivableMoney();
				//本金--后期变为 本金+服务费
				BigDecimal loanMoney = null;
				if (loan.getPaidMoney() != null && loan.getPaidMoney().intValue() > 0) {
					loanMoney = loan.getPaidMoney();
				} else {
					loanMoney = loan.getLoanMoney();
				}
				//滞纳金
				BigDecimal loanPenalty = loan.getLoanPenalty();
				//已还金额
				BigDecimal realMoney = pay.getRealMoney();
				//可减免金额
				BigDecimal deductibleMoney = null;
				if (realMoney.intValue() >= loanMoney.intValue()){
					deductibleMoney = receivableMoney.subtract(realMoney);
				}else{
					deductibleMoney = new BigDecimal(0);
				}
				params.put("receivableMoney", receivableMoney);//应还金额
				params.put("loanMoney", loanMoney);//本金
				params.put("loanPenalty", loanPenalty);//滞纳金
				params.put("realMoney", realMoney);//已还金额
				params.put("deductibleMoney", deductibleMoney);//可减免金额
				params.put("loanId", order.getLoanId());//借款id
			}
		}catch (Exception e){
			logger.error("MmanLoanCollectionOrderService-toReductionPage",e);
		}
		return params;
	}

	@Override
	public MmanLoanCollectionOrder getOrderByLoanId(String loanId) {
		return manLoanCollectionOrderDao.getOrderByLoanId(loanId);
	}

	@Override
	public OrderInfo getStopOrderInfoById(String id) {
		return manLoanCollectionOrderDao.getStopOrderInfoById(id);
	}

	@Override
	public int deleteOrderInfoAndLoanInfoByloanId(String loanId) {
		return manLoanCollectionOrderDao.deleteOrderInfoAndLoanInfoByloanId(loanId);
	}

	@Override
	public MmanLoanCollectionOrder getOrderloanId(String loanId) {
		return manLoanCollectionOrderDao.getOrderloanId(loanId);
	}

}
