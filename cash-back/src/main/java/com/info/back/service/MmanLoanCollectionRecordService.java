package com.info.back.service;

import com.info.back.dao.*;
import com.info.back.result.JsonResult;
import com.info.back.utils.BackConstant;
import com.info.back.utils.DecimalFormatUtil;
import com.info.back.utils.IdGen;
import com.info.config.PayContents;
import com.info.constant.Constant;
import com.info.web.pojo.*;
import com.info.web.util.*;
import com.info.web.util.encrypt.AESUtil;
import com.info.web.util.encrypt.MD5coding;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MmanLoanCollectionRecordService implements IMmanLoanCollectionRecordService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IMmanLoanCollectionRecordDao mmanLoanCollectionRecordDao;
	
	@Autowired
	private IPaginationDao paginationDao;
	
	@Autowired
	private IMmanLoanCollectionRuleDao mmanLoanCollectionRuleDao;
	
	@Autowired
	private IAlertMsgService sysAlertMsgService;
	
	@Autowired
	private ICreditLoanPayService creditLoanPayService;
	
	
	@Autowired
	private IMmanLoanCollectionOrderService mmanLoanCollectionOrderService;
	
	@Autowired
	private IBackUserDao backUserDao;
	
	@Autowired
	private IMmanLoanCollectionStatusChangeLogDao mmanLoanCollectionStatusChangeLogDao;
	@Autowired
	private IMmanLoanCollectionOrderDao mmanLoanCollectionOrderDao;
	
	@Autowired
	private IMmanUserInfoDao mmanUserInfoDao;
	@Autowired
	private ICollectionWithholdingRecordDao collectionWithholdingRecordDao;
	@Autowired
	private IInstallmentPayRecordDao iInstallmentPayRecordDao;
	@Autowired
	private IMmanUserRelaDao mmanUserRelaDao;
	
	public void assignCollectionOrderToRelatedGroup(
			List<MmanLoanCollectionOrder> mmanLoanCollectionOrderList,
			List<MmanLoanCollectionPerson> mmanLoanCollectionPersonList, Date date) {
		
		if (null!=mmanLoanCollectionOrderList && mmanLoanCollectionOrderList.size()>0) {
			
			//2.1 查询当前组中所有非禁用的催收员，按照截止到当前手里未处理的订单数升序排序(前面已查)，并查出他们组每人每天单数上限(上限规则中公司+组唯一)，取出有效催收员
			List<MmanLoanCollectionRule> allRuleList = mmanLoanCollectionRuleDao.findList(new MmanLoanCollectionRule());
			HashMap<String, Integer> allRuleLimitCountMap = new HashMap<String, Integer>();
			if (null!=allRuleList && allRuleList.size()>0) {
				for (MmanLoanCollectionRule ruleOri : allRuleList) {
					allRuleLimitCountMap.put(ruleOri.getCompanyId() + "_" + ruleOri.getCollectionGroup(), ruleOri.getEveryLimit());
				}
			}
			logger.info("allRuleLimitCountMap:"+allRuleLimitCountMap);
			
			//开始分配前,先筛选出有效催收员(手里单子未超出上限的催收员),查询并设置每个催收员今日派到手里的订单数(包括已完成的)
			String currentCompanyGroup = "";//当前公司_组
			List<MmanLoanCollectionPerson> effectiveCollectionPersonList = new ArrayList<MmanLoanCollectionPerson>();
			for (MmanLoanCollectionPerson person : mmanLoanCollectionPersonList) {
				Integer todayAssignedCount = backUserDao.findTodayAssignedCount(person);//查询当前催收员今日派到手里的订单数(包括已完成的)
				person.setTodayAssignedCount(todayAssignedCount);
				Integer limitCount = allRuleLimitCountMap.get(person.getCompanyId() + "_" + person.getGroupLevel());//当前催收组每人每天上限
				currentCompanyGroup = person.getCompanyName() + "_" + BackConstant.groupNameMap.get(person.getGroupLevel());
				if (limitCount == null) {
					limitCount = 0;
				}
				if (todayAssignedCount.intValue() < limitCount.intValue()) {
					effectiveCollectionPersonList.add(person);
				}
			}
			
			//2.2 采用多次均匀涂抹法（将待分配订单数按排好序的催收员，依次分配，最后一次内层循环会优先分配给手里待处理单子少的）派单(最多循环次数：ceilAvgCount * effectivePersonCount)
			if (null==effectiveCollectionPersonList || effectiveCollectionPersonList.isEmpty()) {
				SysAlertMsg alertMsg = new SysAlertMsg();
				alertMsg.setTitle("分配催收任务失败");
				alertMsg.setContent("当前" + currentCompanyGroup + "组所有催收员催收规则上限不足，请抓紧调整！");
				alertMsg.setDealStatus(BackConstant.OFF);
				alertMsg.setStatus(BackConstant.OFF);
				alertMsg.setType(SysAlertMsg.TYPE_COMMON);
				sysAlertMsgService.insert(alertMsg);
				logger.error("当前" + currentCompanyGroup + "组所有催收员催收规则上限不足，请抓紧调整...");
			}else {
				
				int orderCount = mmanLoanCollectionOrderList.size();//待分配订单数
				int effectivePersonCount = effectiveCollectionPersonList.size();//当前可用催收员数
				int ceilAvgCount = new BigDecimal(orderCount).divide(new BigDecimal(effectivePersonCount), 0, BigDecimal.ROUND_CEILING).intValue();//平均订单数向上取整数
				
				int i = 0;//外层循环次数（ceilAvgCount）
				int j = 0;//已分配的订单数（最大为orderCount）
				while (i < ceilAvgCount) {
					for (int t = 0; t < effectivePersonCount; t++) {
						MmanLoanCollectionPerson effectivePerson = effectiveCollectionPersonList.get(t);//当前催收员
						
						//这里再实时查询当前催收员今日派到手里的订单数(包括已完成的)，防止每天第一次派单会超过上限，因为这个时候effectivePerson.getTodayAssignedCount().intValue()一直是0
						Integer todayAssignedCount = backUserDao.findTodayAssignedCount(effectivePerson);
						Integer limitCount = allRuleLimitCountMap.get(effectivePerson.getCompanyId() + "_" + effectivePerson.getGroupLevel());//当前催收组每人每天上限
						if (limitCount == null) {
							limitCount = 0;
						}
						if (todayAssignedCount.intValue() < limitCount.intValue()) {//可以分配
							if (j < orderCount) {
								MmanLoanCollectionOrder order = mmanLoanCollectionOrderList.get(j);
								try {
									//派单方法：添加或更新催收订单、添加催收流转日志并更新还款状态
									addOrUpdateOrderAndAddStatusChangeLogAndUpdatePayStatus(effectivePerson, order, date);
								} catch (Exception e) {
									logger.error("分配当前催收任务出错，订单ID：" + order.getOrderId(), e);
								}
								j++;
							}else {//全部派单完成
								return;
							}
						}
					}
					i++;
				}
				
				//最终订单数未分配完成，给一个通知
				if (j < orderCount) {
					SysAlertMsg alertMsg = new SysAlertMsg();
					alertMsg.setTitle("分配催收任务失败");
					alertMsg.setContent("当前" + currentCompanyGroup + "组，本次派单后出现催收规则上限不足，剩余" + (orderCount - j) + "单未派送，请及时调整。");
					alertMsg.setDealStatus(BackConstant.OFF);
					alertMsg.setStatus(BackConstant.OFF);
					alertMsg.setType(SysAlertMsg.TYPE_COMMON);
					sysAlertMsgService.insert(alertMsg);
					logger.error("当前" + currentCompanyGroup + "组，本次派单后出现催收规则上限不足，剩余" + (orderCount - j) + "单未派送，请及时调整...");
				}
			}
		}
	}

	public PageConfig<MmanLoanCollectionRecord> findPage(
			HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "MmanLoanCollectionRecord");
		PageConfig<MmanLoanCollectionRecord> pageConfig = new PageConfig<MmanLoanCollectionRecord>();
		pageConfig = paginationDao.findPage("findAll", "findAllCount", params,
				null);
		return pageConfig;
	}
	
	
	private void addOrUpdateOrderAndAddStatusChangeLogAndUpdatePayStatus(MmanLoanCollectionPerson person,
			MmanLoanCollectionOrder mmanLoanCollectionOrder, Date date) {
		
		//添加催收流转日志
		MmanLoanCollectionStatusChangeLog mmanLoanCollectionStatusChangeLog = new MmanLoanCollectionStatusChangeLog();
		mmanLoanCollectionStatusChangeLog.setId(IdGen.uuid());
		mmanLoanCollectionStatusChangeLog.setLoanCollectionOrderId(mmanLoanCollectionOrder.getOrderId());
		mmanLoanCollectionStatusChangeLog.setOperatorName("系统");
		mmanLoanCollectionStatusChangeLog.setCreateDate(date);
		
		
		mmanLoanCollectionOrder.setCurrentCollectionUserId(person.getUserId());
		mmanLoanCollectionOrder.setCurrentOverdueLevel(person.getGroupLevel());
		
		if("S1".equals(mmanLoanCollectionOrder.getS1Flag())){//说明是S1,S2平分过来的单子
			if("4".equals(person.getGroupLevel())){
				mmanLoanCollectionOrder.setS1Flag("S1"); // 说明S1组的订单流转到了S2人员手上
			}else{
				mmanLoanCollectionOrder.setS1Flag(null); //  s1组订单分到s1组催收员手上，去掉s1_flag标志（和其他情况一样，订单组和催收组一致）
			}
		}
		
		if (StringUtils.isBlank(mmanLoanCollectionOrder.getId())) {
			mmanLoanCollectionStatusChangeLog.setType(BackConstant.XJX_COLLECTION_STATUS_MOVE_TYPE_IN);//入催
			mmanLoanCollectionStatusChangeLog.setRemark("系统派单，催收人：" + person.getUsername() + "，手机：" + person.getPhone());
			mmanLoanCollectionStatusChangeLog.setCurrentCollectionUserId(person.getUserId());
		} else {
			mmanLoanCollectionStatusChangeLog.setBeforeStatus(mmanLoanCollectionOrder.getStatus());
			mmanLoanCollectionStatusChangeLog.setType(BackConstant.XJX_COLLECTION_STATUS_MOVE_TYPE_CONVERT);//逾期等级转换
			mmanLoanCollectionStatusChangeLog.setRemark("逾期升级，系统重新派单,当前催收人：" + person.getUsername() + "，手机：" + person.getPhone());
			mmanLoanCollectionStatusChangeLog.setCurrentCollectionUserId(person.getUserId());
		}
		
		//添加或更新催收订单
		//催收公司和状态这里统一设置或统一重置（升级的单子无论原来什么状态，这里都会重置！），根据当前分配到的催收员所在公司而定，状态为本公司待催收，委外公司委外中
		mmanLoanCollectionStatusChangeLog.setCurrentCollectionUserLevel(person.getGroupLevel());
		mmanLoanCollectionOrder.setOutsideCompanyId(person.getCompanyId());
		if ("1".equals(mmanLoanCollectionOrder.getOutsideCompanyId())) {
			mmanLoanCollectionOrder.setStatus(BackConstant.XJX_COLLECTION_ORDER_STATE_WAIT);
		} else {
			mmanLoanCollectionOrder.setStatus(BackConstant.XJX_COLLECTION_ORDER_STATE_OUTSIDE);
		}
		
		
		
		if (BackConstant.XJX_OVERDUE_LEVEL_S1.equals(person.getGroupLevel())) {
			mmanLoanCollectionOrder.setM1ApproveId(person.getUserId());
			mmanLoanCollectionOrder.setM1OperateStatus(BackConstant.OFF);
			mmanLoanCollectionStatusChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_S1);
		}else if (BackConstant.XJX_OVERDUE_LEVEL_S2.equals(person.getGroupLevel())) {
			mmanLoanCollectionOrder.setM2ApproveId(person.getUserId());
			mmanLoanCollectionOrder.setM2OperateStatus(BackConstant.OFF);
			mmanLoanCollectionStatusChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_S2);
			if("S1".equals( mmanLoanCollectionOrder.getS1Flag()) && mmanLoanCollectionOrder.getOverdueDays()<=10){
				mmanLoanCollectionOrder.setM2ApproveId(null);
				mmanLoanCollectionOrder.setM1ApproveId(person.getUserId());
				mmanLoanCollectionOrder.setM1OperateStatus(BackConstant.OFF);
				mmanLoanCollectionStatusChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_S1);
			}
			
		}else if (BackConstant.XJX_OVERDUE_LEVEL_M1_M2.equals(person.getGroupLevel())) {
			mmanLoanCollectionOrder.setM3ApproveId(person.getUserId());
			mmanLoanCollectionOrder.setM3OperateStatus(BackConstant.OFF);
			
			mmanLoanCollectionStatusChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_M1_M2);
			
		}else if (BackConstant.XJX_OVERDUE_LEVEL_M2_M3.equals(person.getGroupLevel())) {
			mmanLoanCollectionOrder.setM4ApproveId(person.getUserId());
			mmanLoanCollectionOrder.setM4OperateStatus(BackConstant.OFF);
			
			mmanLoanCollectionStatusChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_M2_M3);
		}else {
			mmanLoanCollectionOrder.setM5ApproveId(person.getUserId());
			mmanLoanCollectionOrder.setM5OperateStatus(BackConstant.OFF);
			
			mmanLoanCollectionStatusChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_M3P);
		}
		
		mmanLoanCollectionStatusChangeLog.setRemark(mmanLoanCollectionStatusChangeLog.getRemark()+",催收组："+BackConstant.groupNameMap.get(mmanLoanCollectionStatusChangeLog.getCurrentCollectionOrderLevel()));
		mmanLoanCollectionStatusChangeLog.setAfterStatus(mmanLoanCollectionOrder.getStatus());
		mmanLoanCollectionStatusChangeLog.setCompanyId(mmanLoanCollectionOrder.getOutsideCompanyId());
		
		mmanLoanCollectionStatusChangeLogDao.insert(mmanLoanCollectionStatusChangeLog);
		
		//更新还款状态
		CreditLoanPay creditLoanPay = creditLoanPayService.findByLoanId(mmanLoanCollectionOrder.getLoanId());
		creditLoanPay.setStatus(Integer.parseInt(person.getGroupLevel()));
		creditLoanPayService.save(creditLoanPay);
		
		mmanLoanCollectionOrderService.saveMmanLoanCollectionOrder(mmanLoanCollectionOrder);
	}

	public List<MmanLoanCollectionRecord> findAll(HashMap<String, Object> params) {
		return mmanLoanCollectionRecordDao.findAll(params);
	}

	@Override
	public void insert(MmanLoanCollectionRecord record) {
		mmanLoanCollectionRecordDao.insert(record);
	}

	@Override
	public void update(MmanLoanCollectionRecord record) {
		mmanLoanCollectionRecordDao.update(record);

	}

	@Override
	public MmanLoanCollectionRecord getOne(HashMap<String, Object> params) {
		List<MmanLoanCollectionRecord> list = this.findAll(params);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<MmanLoanCollectionRecord> findListRecord(String OrderId) {
		return mmanLoanCollectionRecordDao.findListRecord(OrderId);
	}

	@Override
	public JsonResult saveCollection(Map<String, String> params, BackUser user) {
		JsonResult  result=new JsonResult("0","添加成功");
		if(user!=null){
			//更新我的催收订单
			Date now = new Date();
			MmanLoanCollectionOrder mmanLoanCollectionOrderOri = mmanLoanCollectionOrderService.getOrderById(params.get("id").toString());

			MmanLoanCollectionOrder mmanLoanCollectionOrder = new MmanLoanCollectionOrder();
			if (mmanLoanCollectionOrderOri != null && !BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS.equals(mmanLoanCollectionOrderOri.getStatus())) {
				if (params.get("repaymentTime")== null || params.get("repaymentTime")== "") {//不填承诺还款时间为催收中
					mmanLoanCollectionOrder.setStatus(BackConstant.XJX_COLLECTION_ORDER_STATE_ING);
					mmanLoanCollectionOrder.setPromiseRepaymentTime(null);
				}else {
					mmanLoanCollectionOrder.setStatus(BackConstant.XJX_COLLECTION_ORDER_STATE_PROMISE);
					mmanLoanCollectionOrder.setPromiseRepaymentTime(DateUtil.formatDate(params.get("repaymentTime"),"yyyy-MM-dd"));
				}
			}
			mmanLoanCollectionOrder.setLastCollectionTime(now);
			mmanLoanCollectionOrder.setOperatorName(StringUtils.isNotBlank(user.getUserName()) ? user.getUserName(): "");
			//根据等级设置当前催收员某等级操作状态，1代表操作过催收单
			if (BackConstant.XJX_OVERDUE_LEVEL_S1.equals(user.getGroupLevel())) {
				mmanLoanCollectionOrder.setM1OperateStatus(BackConstant.ON);
			} else if (BackConstant.XJX_OVERDUE_LEVEL_S2.equals(user.getGroupLevel())) {
				if("S1".equals(mmanLoanCollectionOrderOri.getS1Flag())&&mmanLoanCollectionOrderOri.getOverdueDays()<=10){
					mmanLoanCollectionOrder.setM1OperateStatus(BackConstant.ON);
				}else{
					mmanLoanCollectionOrder.setM2OperateStatus(BackConstant.ON);
				}
			} else if (BackConstant.XJX_OVERDUE_LEVEL_M1_M2
					.equals(user.getGroupLevel())) {
				mmanLoanCollectionOrder.setM3OperateStatus(BackConstant.ON);
			} else if (BackConstant.XJX_OVERDUE_LEVEL_M2_M3
					.equals(user.getGroupLevel())) {
				mmanLoanCollectionOrder.setM4OperateStatus(BackConstant.ON);
			} else {
				mmanLoanCollectionOrder.setM5OperateStatus(BackConstant.ON);
			}
			mmanLoanCollectionOrder.setS1Flag(mmanLoanCollectionOrderOri.getS1Flag());
			mmanLoanCollectionOrder.setUpdateDate(now);
			mmanLoanCollectionOrder.setId(mmanLoanCollectionOrderOri.getId());
			mmanLoanCollectionOrderService.updateRecord(mmanLoanCollectionOrder);
			MmanLoanCollectionRecord mmanLoanCollectionRecord =new MmanLoanCollectionRecord();
			//添加催收记录
			mmanLoanCollectionRecord.setStressLevel(params.get("stressLevel"));
			mmanLoanCollectionRecord.setCollectionType(params.get("collectionType"));
			mmanLoanCollectionRecord.setContent(params.get("content"));
			mmanLoanCollectionRecord.setRemark(params.get("remark"));
			mmanLoanCollectionRecord.setContactType(params.get("contactType")==null?"":params.get("contactType"));
			mmanLoanCollectionRecord.setContactName(params.get("contactName"));
			mmanLoanCollectionRecord.setRelation(params.get("relation"));
			mmanLoanCollectionRecord.setContactPhone(params.get("contactPhone"));
			mmanLoanCollectionRecord.setCollectionDate(now);
			mmanLoanCollectionRecord.setOrderId(mmanLoanCollectionOrder.getId());
			mmanLoanCollectionRecord.setCollectionId(user.getUuid());
			mmanLoanCollectionRecord.setUserId(mmanLoanCollectionOrderOri.getUserId());
			
			mmanLoanCollectionRecord.setOrderState(mmanLoanCollectionOrderOri.getStatus());

			mmanLoanCollectionRecord.setId(IdGen.uuid());
//			mmanLoanCollectionRecord.setId(params.get("recordId"));
			mmanLoanCollectionRecord.setCreateDate(now);
			mmanLoanCollectionRecord.setUpdateDate(now);
			mmanLoanCollectionRecordDao.insert(mmanLoanCollectionRecord);
		}else{
			result.setCode("-1");
			result.setMsg("登录失效,请重新登录");
		}
		return result;
	}

    /**
     * 转派
     */
	@Override
	public JsonResult batchDispatch(BackUser user,MmanLoanCollectionOrder mmanLoanCollectionOrder){
		JsonResult  result=new JsonResult("-1","转派失败，未知异常");
		HashMap<String, String> resutMap=new HashMap<String,String>();
		//更新我的催收订单
		String currentCollectionUserId = mmanLoanCollectionOrder.getCurrentCollectionUserId();
		String ids = mmanLoanCollectionOrder.getId();
		String[] orderIds = ids.split(",");
		if(orderIds != null && orderIds.length > 0){
			int successCount=0;
			
			for (String orderId : orderIds) {
					MmanLoanCollectionOrder mmanLoanCollectionOrderOri = mmanLoanCollectionOrderService.getOrderById(orderId);//原始催收订单
					//Date nowDateTime=new Date();
					//Date orderDateTime=mmanLoanCollectionOrderOri.getDispatchTime();
					//String nowDateStr=DateUtil.getDateFormat(nowDateTime,"yyyy-MM-dd");
					//String orderDateStr=DateUtil.getDateFormat(orderDateTime, "yyyy-MM-dd");
					//Date nowDate=DateUtil.formatDate(nowDateStr, "yyyy-MM-dd");
					//Date orderDate=DateUtil.formatDate(orderDateStr, "yyyy-MM-dd");
					try {
						/*if(DateUtil.daysBetween(orderDate,nowDate)>=1){*/
							if(!"4".equals(mmanLoanCollectionOrderOri.getStatus())){
								if(!currentCollectionUserId.equals(mmanLoanCollectionOrderOri.getCurrentCollectionUserId())){
									HashMap<String, String> params=new HashMap<String, String>();
									params.put("currentCollectionUserId",currentCollectionUserId);
									params.put("orderId", orderId);
									int countSinge=mmanLoanCollectionStatusChangeLogDao.findOrderSingle(params);//判断催收员是否转派过改单
									if(countSinge<=0){
										BackUser buc = backUserDao.getBackUserByUuid(currentCollectionUserId);//当前催收人
										params.put("companyId", buc.getCompanyId());
										params.put("grouplevel", buc.getGroupLevel());
										Integer limitCount = mmanLoanCollectionRuleDao.findCompanyGoupOnline(params);//查询该公催收每日订单上线
										MmanLoanCollectionPerson person=new MmanLoanCollectionPerson();
										person.setId(buc.getId()+"");
										Integer todayOrder=backUserDao.findTodayAssignedCount(person);//查询当前催收员今日派到手里的订单数(包括已完成的)
										boolean S2topS1=false;
										if("S1".equals(mmanLoanCollectionOrderOri.getS1Flag())&&"3".equals(buc.getGroupLevel())&& mmanLoanCollectionOrderOri.getOverdueDays()<=10){
											S2topS1=true;

										}
										if(limitCount>todayOrder){
											BackUser bu = backUserDao.getBackUserByUuid(mmanLoanCollectionOrderOri.getCurrentCollectionUserId());//原始催收人
											if(bu.getGroupLevel().equals(buc.getGroupLevel()) || S2topS1 ){
												MmanLoanCollectionStatusChangeLog mmanLoanCollectionStatusChangeLog = new MmanLoanCollectionStatusChangeLog();
												//催收订单状态
												String beforeStatus = mmanLoanCollectionOrderOri.getStatus();
												if ("1".equals(mmanLoanCollectionOrder.getOutsideCompanyId())) {
													mmanLoanCollectionOrderOri.setStatus(BackConstant.XJX_COLLECTION_ORDER_STATE_WAIT);//公司内部转派待催收
													mmanLoanCollectionStatusChangeLog.setType(BackConstant.XJX_COLLECTION_STATUS_MOVE_TYPE_OTHER);//转单
												}else {
													mmanLoanCollectionOrderOri.setStatus(BackConstant.XJX_COLLECTION_ORDER_STATE_OUTSIDE);//委外
													mmanLoanCollectionStatusChangeLog.setType(BackConstant.XJX_COLLECTION_STATUS_MOVE_TYPE_OUTSIDE);//委外
												}
												//被转派人所在催收组
												//String currentOverdueLevel = mmanLoanCollectionOrderOri.getCurrentOverdueLevel();
												String currentOverdueLevel =  buc.getGroupLevel();
												//转派后要将当前级催收状态初始化
												if (BackConstant.XJX_OVERDUE_LEVEL_S1.equals(currentOverdueLevel)) {
													mmanLoanCollectionOrderOri.setM1ApproveId(currentCollectionUserId);
													mmanLoanCollectionOrderOri.setM1OperateStatus(BackConstant.OFF);
												}else if (BackConstant.XJX_OVERDUE_LEVEL_S2.equals(currentOverdueLevel)) {
													mmanLoanCollectionOrderOri.setM2ApproveId(currentCollectionUserId);
													mmanLoanCollectionOrderOri.setM2OperateStatus(BackConstant.OFF);
												}else if (BackConstant.XJX_OVERDUE_LEVEL_M1_M2.equals(currentOverdueLevel)) {
													mmanLoanCollectionOrderOri.setM3ApproveId(currentCollectionUserId);
													mmanLoanCollectionOrderOri.setM3OperateStatus(BackConstant.OFF);
												}else if (BackConstant.XJX_OVERDUE_LEVEL_M2_M3.equals(currentOverdueLevel)) {
													mmanLoanCollectionOrderOri.setM4ApproveId(currentCollectionUserId);
													mmanLoanCollectionOrderOri.setM4OperateStatus(BackConstant.OFF);
												}else {
													mmanLoanCollectionOrderOri.setM5ApproveId(currentCollectionUserId);
													mmanLoanCollectionOrderOri.setM5OperateStatus(BackConstant.OFF);
												}
												mmanLoanCollectionOrderOri.setCurrentOverdueLevel(currentOverdueLevel);
												mmanLoanCollectionOrderOri.setLastCollectionUserId(mmanLoanCollectionOrderOri.getCurrentCollectionUserId());//上一催收员
												mmanLoanCollectionOrderOri.setCurrentCollectionUserId(currentCollectionUserId);
												mmanLoanCollectionOrderOri.setOutsideCompanyId(mmanLoanCollectionOrder.getOutsideCompanyId());
												mmanLoanCollectionOrderOri.setOperatorName(StringUtils.isNotBlank(user.getUserName()) ? user.getUserName() : "");
												mmanLoanCollectionOrderOri.setDispatchName(StringUtils.isNotBlank(user.getUserName()) ? user.getUserName() : "");
												mmanLoanCollectionOrderOri.setDispatchTime(new Date());
												mmanLoanCollectionOrderOri.setRemark("[" + bu.getUserName() + "]转派给[" + buc.getUserName() + "]");

												//更新聚信立报告申请审核状态为初始状态，下一催收员要看需要重新申请
												mmanLoanCollectionOrderOri.setJxlStatus(BackConstant.XJX_JXL_STATUS_REFUSE);
												Date now = new Date();
												mmanLoanCollectionOrderOri.setUpdateDate(now);
												mmanLoanCollectionOrderDao.updateCollectionOrder(mmanLoanCollectionOrderOri);
												//添加转派记录
												mmanLoanCollectionStatusChangeLog.setLoanCollectionOrderId(mmanLoanCollectionOrderOri.getOrderId());
												mmanLoanCollectionStatusChangeLog.setCompanyId(mmanLoanCollectionOrderOri.getOutsideCompanyId());
												mmanLoanCollectionStatusChangeLog.setBeforeStatus(beforeStatus);
												mmanLoanCollectionStatusChangeLog.setAfterStatus(mmanLoanCollectionOrderOri.getStatus());
												mmanLoanCollectionStatusChangeLog.setOperatorName(StringUtils.isNotBlank(user.getUserName()) ? user.getUserName() : "");
												mmanLoanCollectionStatusChangeLog.setRemark("转单，催收人：" + buc.getUserName() + "，手机：" + buc.getUserMobile());
												mmanLoanCollectionStatusChangeLog.setId(IdGen.uuid());
												mmanLoanCollectionStatusChangeLog.setCreateDate(now);
												mmanLoanCollectionStatusChangeLog.setCurrentCollectionUserId(buc.getUuid());   //订单转派后的催收人
												mmanLoanCollectionStatusChangeLog.setCurrentCollectionOrderLevel(mmanLoanCollectionOrderOri.getCurrentOverdueLevel());
												mmanLoanCollectionStatusChangeLog.setCurrentCollectionUserLevel(buc.getGroupLevel());
												mmanLoanCollectionStatusChangeLogDao.insert(mmanLoanCollectionStatusChangeLog);
												successCount++;
											}else{
												resutMap.put("sameGroup","只能同组之间转派");
											}
										}else{
											resutMap.put("todayOrder","已超过催收员每日上线,");
											break;
										}
									}else{
										resutMap.put("countSinge","订单在当前催收员手上有转派过,");
									}
								}else{
									resutMap.put("backUser","自己不能转给自己");
								}
							}else{
								resutMap.put("orderStatus","催收成功的订单不能转派");
							}
						/*}else{
							resutMap.put("currDateMesg","当天的订单不能转派,");
						}*/

					} catch (Exception e) {
						e.printStackTrace();
					}
			}
			if(successCount==orderIds.length){
				result.setMsg("转派成功");
				result.setCode("0");
			}else{
				StringBuffer resultStr=new StringBuffer();
				if(resutMap.get("currDateMesg")!=null){
					resultStr.append(resutMap.get("currDateMesg"));
				}
				if(resutMap.get("orderStatus")!=null){
					resultStr.append(resutMap.get("orderStatus"));
				}
				if(resutMap.get("countSinge")!=null){
					resultStr.append(resutMap.get("countSinge"));
				}
				if(resutMap.get("backUser")!=null){
					resultStr.append(resutMap.get("backUser"));
				}
				if(resutMap.get("todayOrder")!=null){
					resultStr.append(resutMap.get("todayOrder"));
				}
				if(resutMap.get("sameGroup")!=null){
					resultStr.append(resutMap.get("sameGroup"));
				}
				result.setMsg("总单:"+orderIds.length+"，转派成功："+successCount+"失败原因："+resultStr.toString());
			}
		}else{
			result.setMsg("请选择需要转派的订单");
		}
	return result;
	}

	@Override
	public JsonResult xjxWithholding(Map<String, String> params) {
		MmanLoanCollectionOrder mmanLoanCollectionOrderOri = mmanLoanCollectionOrderService.getOrderById(params.get("id").toString());//原催收订单
		JsonResult reslut=new JsonResult("-1","申请代扣款失败");
		BackUser backUser=new BackUser();
		try {
			if(mmanLoanCollectionOrderOri!=null){
				List<CollectionWithholdingRecord> recordList = collectionWithholdingRecordDao.findOrderList(params.get("id").toString());
				if(!"5".equals(mmanLoanCollectionOrderOri.getStatus())){
					if(CollectionUtils.isEmpty(recordList) || new Date().getTime() > getCreateTimePlus(recordList)){
						CreditLoanPay creditLoanPay = creditLoanPayService.get(mmanLoanCollectionOrderOri.getPayId());
						String payMonery=params.get("payMoney");//扣款金额
						BigDecimal koPayMonery=new BigDecimal(0);
						BigDecimal maxpayMonery=creditLoanPay.getReceivablePrinciple().add(creditLoanPay.getReceivableInterest());
						if(payMonery==null|| "".equals(payMonery) || !CompareUtils.greaterThanZero(new BigDecimal(payMonery))){
							koPayMonery=creditLoanPay.getReceivablePrinciple().add(creditLoanPay.getReceivableInterest());
						}else{
							koPayMonery=new BigDecimal(params.get("payMoney"));
						}
						if(CompareUtils.greaterEquals(maxpayMonery, koPayMonery)){
							HashMap<String, String> dayMap=new HashMap<String, String>();
							dayMap.put("orderId", params.get("id").toString());
							dayMap.put("currDate", DateUtil.getDateFormat(new Date(), "yyyy-MM-dd"));
							//查询当天定单代扣次数
							int count=collectionWithholdingRecordDao.findCurrDayWithhold(dayMap);
							logger.error("当前roleId: " + String.valueOf(params.get("roleId")));
							//超级管理员，催收经理 不受权限控制
							if(Constant.ROLE_ID.equals(String.valueOf(params.get("roleId"))) || "10001".equals(String.valueOf(params.get("roleId")))){
								count = 0;
							}
							logger.error("当前次数count:" + count);
							if(count<5){
								long actualPayMonery=koPayMonery.multiply(new BigDecimal(100)).longValue();
								String uuid=IdGen.uuid();
								String sign = MD5coding.MD5(AESUtil.encrypt(mmanLoanCollectionOrderOri.getUserId()+mmanLoanCollectionOrderOri.getPayId()+actualPayMonery+uuid,PayContents.XJX_WITHHOLDING_NOTIFY_KEY));
								//2、发送请求
								String withholdPostUrl=PayContents.XJX_WITHHOLDING_NOTIFY_URL+"/"+mmanLoanCollectionOrderOri.getUserId()+"/"+mmanLoanCollectionOrderOri.getPayId()+"/"+actualPayMonery+"/"+uuid+"/"+sign;
								logger.error("现金侠代扣请求地址："+withholdPostUrl);
								String xjxWithholdingStr=HttpUtil.getHttpMess(withholdPostUrl, "", "POST", "UTF-8");
								//3、解析响应结果封装到Java Bean
								if(xjxWithholdingStr!=null&&!"".equals(xjxWithholdingStr)){
									JSONObject jos=new JSONObject().fromObject(xjxWithholdingStr);
									if(!"-100".equals(jos.get("code"))){
										CollectionWithholdingRecord  WithholdingRecord=new CollectionWithholdingRecord();
										MmanUserInfo userInfo=mmanUserInfoDao.get(mmanLoanCollectionOrderOri.getUserId());
										WithholdingRecord.setLoanUserId(userInfo.getId());
										WithholdingRecord.setId(uuid);
										WithholdingRecord.setLoanUserName(userInfo.getRealname());
										WithholdingRecord.setLoanUserPhone(userInfo.getUserPhone());
										WithholdingRecord.setOrderId(mmanLoanCollectionOrderOri.getId());
										WithholdingRecord.setCreateDate(new Date());

										WithholdingRecord.setArrearsMoney(DecimalFormatUtil.df2Points.format(creditLoanPay.getReceivableMoney()));
										WithholdingRecord.setHasalsoMoney(creditLoanPay.getRealMoney().toString());
										WithholdingRecord.setOperationUserId(params.get("operationUserId"));
										WithholdingRecord.setDeductionsMoney(payMonery);
										WithholdingRecord.setOrderStatus(mmanLoanCollectionOrderOri.getStatus());
										if("0".equals(jos.get("code")) || "100".equals(jos.get("code"))){
											//扣款成功要更新操作人，由于代扣成功时会有接口更新订单、借款、还款、详情等数据，所以这里千万不能更新mmanLoanCollectionOrderOri，因为这里的订单状态还是原始状态！！！
											MmanLoanCollectionOrder mmanLoanCollectionOrderNow = new MmanLoanCollectionOrder();
											mmanLoanCollectionOrderNow.setId(mmanLoanCollectionOrderOri.getId());
											mmanLoanCollectionOrderNow.setOperatorName(params.get("userName"));
											mmanLoanCollectionOrderNow.setS1Flag(mmanLoanCollectionOrderOri.getS1Flag());
											if(BackConstant.XJX_COLLECTION_ORDER_STATE_WAIT.equals(mmanLoanCollectionOrderOri.getStatus())){
												mmanLoanCollectionOrderNow.setStatus(BackConstant.XJX_COLLECTION_ORDER_STATE_ING);
											}
											mmanLoanCollectionOrderService.updateRecord(mmanLoanCollectionOrderNow);
											if("0".equals(jos.get("code"))){
												WithholdingRecord.setStatus(1);
											}else{
												WithholdingRecord.setStatus(0);
											}
											reslut.setMsg("申请代扣成功");
											reslut.setCode("0");
										}else{
											reslut.setMsg(jos.getString("msg"));
											WithholdingRecord.setStatus(2);
										}

										//添加一条扣款记录
										collectionWithholdingRecordDao.insert(WithholdingRecord);
									}else{
										reslut.setMsg("申请代扣失败,失败编码-100");
									}
									logger.error("现金侠代扣返回："+xjxWithholdingStr);
								}
							}else{
								reslut.setMsg("每笔订单每天代扣次数不能超过三次");
							}
						}else{
							reslut.setMsg("代扣金额不能大于"+creditLoanPay.getReceivablePrinciple().add(creditLoanPay.getReceivableInterest()));
						}
					}else{
						reslut.setMsg("代扣过于频繁,请稍等(间隔时间为5分钟)");
					}
				}else{
					reslut.setMsg("续期订单不允许代扣！！");
				}
			}else{
				reslut.setMsg("该订单不存在");
			}
		} catch (Exception e) {
			logger.error("代扣异常：", e);
		}
		return reslut;
	}

	private long getCreateTimePlus(List<CollectionWithholdingRecord> recordList) {
		long createTime = recordList.get(0).getCreateDate().getTime();//最新一条代扣时间
		return createTime + 5 * 60 * 1000; //新增5分钟
	}

	@Override
	public List<CollectionWithholdingRecord> findWithholdRecord(String id) {
		return collectionWithholdingRecordDao.findOrderList(id);
	}

	@Override
	public JsonResult insertInstallmentPayRecord(List<InstallmentPayInfoVo> list, MmanLoanCollectionOrder mmanLoanCollectionOrderOri) {
		JsonResult jsonResult = new JsonResult();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		List<InstallmentPayRecord> recordsList = new ArrayList<InstallmentPayRecord>();
		for(InstallmentPayInfoVo installmentPayInfoVo : list){
			InstallmentPayRecord installmentPayRecord = new InstallmentPayRecord();
			installmentPayRecord.setId(IdGen.uuid());
			installmentPayRecord.setRepayTime(installmentPayInfoVo.getRepayTime());
			installmentPayRecord.setDateNew(formatter.format(installmentPayInfoVo.getRepayTime()));
			installmentPayRecord.setCreateTime(new Date());
			installmentPayRecord.setRepayMoney(installmentPayInfoVo.getTotalRepay());
			installmentPayRecord.setLoanOrderId(mmanLoanCollectionOrderOri.getId());
			installmentPayRecord.setLoanUserName(mmanLoanCollectionOrderOri.getLoanUserName());
			installmentPayRecord.setLoanUserPhone(mmanLoanCollectionOrderOri.getLoanUserPhone());
			installmentPayRecord.setRepayBatches(installmentPayInfoVo.getInstallmentType()+"还款");
			if(installmentPayInfoVo.getServiceCharge() != null){
				installmentPayRecord.setRepayStatus("0"); //还款成功
				installmentPayRecord.setOperationStatus("1"); //无代扣
			}
			recordsList.add(installmentPayRecord);
			iInstallmentPayRecordDao.insert(installmentPayRecord);
			
		}
		jsonResult.setData(recordsList);
		jsonResult.setCode("0");
		jsonResult.setMsg("分期创建成功");
		return jsonResult;
	}

	@Override
	public List<InstallmentPayRecord> findInstallmentList(String id) {
		return iInstallmentPayRecordDao.findInstallmentList(id);
	}

	/**
	 * 根据主键ID查分期记录
	 * @param params
     */
	@Override
	public JsonResult fqWithholding (Map<String, String> params) {
		JsonResult result = new JsonResult("","");
		InstallmentPayRecord installmentPayRecord = iInstallmentPayRecordDao.findInstallmentById(params.get("installmentId").toString());
		if (installmentPayRecord != null) {
			params.put("payMoney",String.valueOf(installmentPayRecord.getRepayMoney()));
			params.put("id",installmentPayRecord.getLoanOrderId());
			//调用代扣接口
			result = this.xjxWithholding(params);
			logger.info("调用代扣接口返回：{}", JSONUtil.beanToJson(result));
			result.setCode("0");
			if(result != null && "0".equals(result.getCode())){
				iInstallmentPayRecordDao.updateInstallmentStatusById(params.get("installmentId").toString());
			}
		}
		return result;
	}

}
