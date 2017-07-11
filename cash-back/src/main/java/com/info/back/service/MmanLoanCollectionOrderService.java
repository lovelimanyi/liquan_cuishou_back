package com.info.back.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.back.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.info.web.pojo.CreditLoanPay;
import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.pojo.MmanUserInfo;
import com.info.web.pojo.MmanUserLoan;
import com.info.web.pojo.OrderBaseResult;
import com.info.web.util.CompareUtils;
import com.info.web.util.PageConfig;
import com.info.back.result.JsonResult;
import com.info.back.utils.IdGen;
import com.info.constant.Constant;

@Service
public class MmanLoanCollectionOrderService implements IMmanLoanCollectionOrderService{
	protected Logger logger = LoggerFactory.getLogger(MmanLoanCollectionOrderService.class);
	@Autowired
	private IMmanLoanCollectionOrderDao mmanLoanCollectionOrderDao;
	
	@Autowired
	private IMmanLoanCollectionOrderDao manLoanCollectionOrderDao;
	
	@Autowired
	private IPaginationDao paginationDao;
	
	@Autowired
	private IMmanUserInfoDao mmanUserInfoDao;
	
    @Autowired
    private IMmanLoanCollectionOrderDao collectionOrderDao;
    @Autowired
    private IMmanUserLoanService iMmanUserLoanService;
    @Autowired
    private IMmanUserLoanDao iMmanUserLoanDao;
    @Autowired
	private ICreditLoanPayDao creditLoanPayDao;
    @Autowired
	private IMmanUserLoanDao mmanUserLoanDao;
    
	public List<MmanLoanCollectionOrder> getOrderList(MmanLoanCollectionOrder mmanLoanCollectionOrder) {
		return mmanLoanCollectionOrderDao.getOrderList(mmanLoanCollectionOrder);
	}

	@Override
	public PageConfig<MmanLoanCollectionOrder> findPage(HashMap<String, Object> params) {
		PageConfig<MmanLoanCollectionOrder> page = new PageConfig<MmanLoanCollectionOrder>();
		page = paginationDao.findPage("getOrderPage", "getOrderPageCount", params, null);
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
		page = paginationDao.findPage("getCollectionOrderList", "getCollectionOrderCount", params, null);
		return page;
	}

	@Override
	public PageConfig<OrderBaseResult> getCollectionUserPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "MmanLoanCollectionOrder");
		PageConfig<OrderBaseResult> page = new PageConfig<OrderBaseResult>();
		page = paginationDao.findPage("getCollectionOrderList", "getOrderCount", params, null);
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
		page = paginationDao.findPage("getCollectionMyOrderList", "getCollectionMyOrderCount", params, null);
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

}
