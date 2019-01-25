package com.info.back.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.info.back.dao.*;
import com.info.back.result.JsonResult;
import com.info.back.utils.BackConstant;
import com.info.back.utils.IdGen;
import com.info.constant.Constant;
import com.info.web.pojo.*;
import com.info.web.util.DateUtil;
import com.info.web.util.JedisDataClient;
import com.info.web.util.PageConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@Service
public class MmanLoanCollectionOrderService implements IMmanLoanCollectionOrderService {
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
    @Autowired
    private IBackUserDao backUserDao;
    @Autowired
    private ISysAlertMsgDao sysAlertMsgDao;
    @Autowired
    private IMmanLoanCollectionRecordService mmanLoanCollectionRecordService;
    @Autowired
    private IMmanLoanCollectionStatusChangeLogDao statusChangeLogDao;
    @Autowired
    private IChannelSwitchingDao channelSwitchingDao;
    @Autowired
    private IOrderChangeRecordService orderChangeRecordService;
    @Autowired
    private IMmanLoanCollectionCompanyDao mmanLoanCollectionCompanyDao;
    @Autowired
    private IMerchantInfoDao merchantInfoDao;
    @Autowired
    private IRepayChannelConfigDao repayChannelConfigDao;
    @Autowired
    private IMmanLoanCollectionStatusChangeLogService  changeLogService;
    @Autowired
    private IAlertMsgService sysAlertMsgService;

    private static final String MERCHANT_INFO_REDIS_KEY = "merchants";

    private static final String REPAY_CHANNEL_REDIS_KEY = "repayChannel";

    @Override
    public List<String> getOverdueOrder() {
        return mmanLoanCollectionOrderDao.getOverdueOrder();
    }

    /**
     * @param loanId
     */
    @Override
    public void updateTotalOverdueDays(String loanId) {
        MmanUserLoan mmanUserLoan = mmanUserLoanDao.get(loanId);
        int totalOverdueDays = getTotalOverdueDays(mmanUserLoan);
        Map<String, String> map = new HashMap<>();
        map.put("loanId", loanId);
        map.put("totalOverdueDays", String.valueOf(totalOverdueDays));
        mmanLoanCollectionOrderDao.upTotalOverdueDays(map);
    }

    @Override
    public List<MmanLoanCollectionOrder> getOrderList(MmanLoanCollectionOrder mmanLoanCollectionOrder) {
        return mmanLoanCollectionOrderDao.getOrderList(mmanLoanCollectionOrder);
    }

    @Override
    public PageConfig<MmanLoanCollectionOrder> findPage(HashMap<String, Object> params) {
        PageConfig<MmanLoanCollectionOrder> page;
        page = orderPaginationDao.findPage("getOrderPage", "getOrderPageCount", params, null);
        return page;
    }

    @Override
    public List<MmanLoanCollectionOrder> findList(MmanLoanCollectionOrder queryManLoanCollectionOrder) {
        return manLoanCollectionOrderDao.findList(queryManLoanCollectionOrder);
    }

    @Override
    public void saveMmanLoanCollectionOrder(MmanLoanCollectionOrder order) {

        if (order != null && StringUtils.isNotBlank(order.getUserId())) {
            MmanUserInfo mmanUserInfo = mmanUserInfoDao.get(order.getUserId());
            if (null != mmanUserInfo) {
                order.setLoanUserName(mmanUserInfo.getRealname());
                order.setLoanUserPhone(mmanUserInfo.getUserPhone());
            }
        }


        if (StringUtils.isBlank(order.getId())) {
            order.setId(IdGen.uuid());
            order.setCreateDate(new Date());
            order.setUpdateDate(new Date());
            manLoanCollectionOrderDao.insertCollectionOrder(order);
        } else {
            order.setUpdateDate(new Date());
            manLoanCollectionOrderDao.updateCollectionOrder(order);
        }

    }

    @Override
    public PageConfig<OrderBaseResult> getPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "MmanLoanCollectionOrder");
        PageConfig<OrderBaseResult> page;
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
        String collectionRealName = params.get("collectionRealName") == null ? null : params.get("collectionRealName").toString();
        record.setCurrentCollectionUserName(this.getCorrectParam(collectionRealName));
        record.setCurrentCollectionUserName(params.get("collectionRealName") == null ? null : params.get("collectionRealName").toString());
        record.setCollectionStatus(params.get("status") == null ? null : params.get("status").toString());
        record.setFollowUpGrad(params.get("topImportant") == null ? null : params.get("topImportant").toString());
        String loanUserName = params.get("loanRealName") == null ? null : params.get("loanRealName").toString();
        record.setLoanUserName(this.getCorrectParam(loanUserName));
        String loanUserPhone = params.get("loanUserPhone") == null ? null : params.get("loanUserPhone").toString();
        record.setLoanUserPhone(this.getCorrectParam(loanUserPhone));
        String currentUserAccount = params.get("currentUserAccount") == null ? null : params.get("currentUserAccount").toString();
        record.setOperateUserAccount(this.getCorrectParam(currentUserAccount));
        record.setLoanId(params.get("loanId") == null ? null : params.get("loanId").toString());
        record.setOperateTime(new Date());
        operationRecordService.insert(record);
    }

    /**
     * @param params
     * @return
     * @Description 处理后台传入数据，防止脏数据超出限制
     */
    private String getCorrectParam(String params) {
        if (StringUtils.isNotEmpty(params) && params.length() > 50) {
            params = params.substring(0, 49);
        }
        return params;
    }

    @Override
    public PageConfig<OrderBaseResult> getCollectionUserPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "MmanLoanCollectionOrder");
        PageConfig<OrderBaseResult> page;
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
        JsonResult result = new JsonResult("-1", "标记订单重要程度失败");
        if (StringUtils.isNotBlank(params.get("id") + "") && StringUtils.isNotBlank(params.get("topLevel") + "")) {
            int count = manLoanCollectionOrderDao.updateTopOrder(params);
            if (count > 0) {
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
        PageConfig<OrderBaseResult> page;
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
                logger.debug("toReductionPage, id = " + params.get("id").toString() + ", order = " + order);
                if (order != null) {
                    CreditLoanPay pay = creditLoanPayDao.findByLoanId(order.getLoanId());
                    MmanUserLoan loan = mmanUserLoanDao.get(order.getLoanId());

                    //应还金额
                    BigDecimal receivableMoney = pay.getReceivableMoney();
                    // 借款利息
                    BigDecimal accrual = loan.getAccrual() == null ? BigDecimal.ZERO : loan.getAccrual();
                    //本金--后期变为 本金+服务费（大额为本金+利息）
                    BigDecimal loanMoney;
                    if (loan.getPaidMoney() != null && loan.getPaidMoney().compareTo(BigDecimal.ZERO) == 1) {
                        loanMoney = loan.getPaidMoney().add(accrual);
                    } else {
                        loanMoney = loan.getLoanMoney().add(accrual);
                    }
                    // 滞纳金
                    BigDecimal loanPenalty = loan.getLoanPenalty();
                    // 已还金额
                    BigDecimal realMoney = pay.getRealMoney();
                    // 可减免金额
                    BigDecimal deductibleMoney;
                    if (realMoney.compareTo(loanMoney) == 1 || realMoney.compareTo(loanMoney) == 0) {
                        deductibleMoney = receivableMoney.subtract(realMoney);
                    } else {
                        deductibleMoney = new BigDecimal(0);
                    }
//                    if (Constant.BIG.equals(loan.getBorrowingType())) {
//                        params.put("type", Constant.BIG);
//                    }
                    params.put("accrual", accrual);
                    params.put("receivableMoney", receivableMoney);//应还金额
                    params.put("loanMoney", loanMoney.subtract(accrual));//本金
                    params.put("loanPenalty", loanPenalty);//滞纳金
                    params.put("realMoney", realMoney);//已还金额
                    params.put("deductibleMoney", deductibleMoney);//可减免金额
                    params.put("loanId", order.getLoanId());//借款id
                    params.put("type", loan.getBorrowingType());
                } else {
                    logger.info("toReductionPage,order is null,请核实id = " + params.get("id"));
                }
            }
        } catch (Exception e) {
            logger.error("MmanLoanCollectionOrderService-toReductionPage", e);
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

    @Override
    public void updateOverdueDays(MmanLoanCollectionOrder order) {
        manLoanCollectionOrderDao.updateOverdueDays(order);

    }

    @Override
    public void dealwithBigOrderUpgrade(String loanId) {
        try {
            MmanUserLoan loan = mmanUserLoanDao.get(loanId);
            if (loan == null) {
                logger.error("处理逾期订单升级出错(借款对象为空)，借款id: " + loanId);
                return;
            }

            List<MmanLoanCollectionOrder> orderList = new ArrayList<>();
            List<MmanLoanCollectionPerson> personList = new ArrayList<>();
            MmanLoanCollectionOrder order = new MmanLoanCollectionOrder();
            order.setLoanId(loanId);
            List<MmanLoanCollectionOrder> orders = mmanLoanCollectionOrderDao.findList(order);
            if (CollectionUtils.isEmpty(orders)) {
                logger.error("逾期升级出错，订单为空，借款id: " + loanId);
                return;
            }
            order = orders.get(0);
            if (BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS.equals(order.getStatus())) {
                logger.error("逾期升级出错，订单已还款完成，借款id: " + loanId);
                return;
            }
            if (BackConstant.XJX_COLLECTION_ORDER_STATE_STOP.equals(order.getStatus())) {
                logger.error("逾期升级出错，订单已停催，借款id: " + loanId);
                return;
            }

            // 计算订单逾期天数
            int dayCount = getTotalOverdueDays(loan);

            if (dayCount >= 31 && dayCount <= 60) {
                this.dispatchOrderToFM2(order, orderList, personList);
            } else if (dayCount >= 61 && dayCount <= 180) {
                this.dispatchOrderToFM3(order, orderList, personList);
            } else if (dayCount >= 181) {
                this.dispatchOrderToFM6(order, orderList, personList);
            }
            mmanLoanCollectionRecordService.assignCollectionOrderToRelatedGroup(orderList, personList, new Date());
            if (!CollectionUtils.isEmpty(personList) && !CollectionUtils.isEmpty(orderList)) {
                saveRecord(personList, orderList);
            }
        } catch (Exception e) {
            logger.error("处理订单逾期升级出错，借款id: " + loanId);
            e.printStackTrace();
        }
    }

    @Override
    public void updateProductName(MmanLoanCollectionOrder order) {
        manLoanCollectionOrderDao.updateProductName(order);


    }

    /**
     * 记录订单流转状态数据
     *
     * @param personList
     * @param list
     */
    private void saveRecord(List<MmanLoanCollectionPerson> personList, List<MmanLoanCollectionOrder> list) {
        OrderChangeRecord record = new OrderChangeRecord();
        record.setId(IdGen.uuid());
        record.setLoanId(list.get(0).getLoanId());
        record.setCreateDate(new Date());
        record.setCurrentUserId(list.get(0).getCurrentCollectionUserId());
        record.setNextUserId(personList.get(0).getUserId());
        CreditLoanPay pay = creditLoanPayDao.findByLoanId(list.get(0).getLoanId());
        record.setRealgetAccrual(pay.getRealgetAccrual());
        record.setRealgetPrinciple(pay.getRealgetPrinciple());
        record.setRemainAccrual(pay.getRemainAccrual());
        record.setRemainPrinciple(pay.getReceivablePrinciple());
        orderChangeRecordService.insert(record);
    }

    /**
     * 大额订单逾期天数大于30天时升级到F-M2组
     *
     * @param order
     * @param orderList
     * @param personList
     */
    private void dispatchOrderToFM2(MmanLoanCollectionOrder order, List<MmanLoanCollectionOrder> orderList, List<MmanLoanCollectionPerson> personList) {
        if (!BackConstant.XJX_OVERDUE_LEVEL_F_M2.equals(order.getCurrentOverdueLevel())) {
            this.setCommonVariables(order);
            orderList.add(order);

            if (isBeforePeroidCollectionCompleted(order.getLoanId())) {
                Map<String, String> personMap = new HashMap<>();
                personMap.put("beginDispatchTime", DateUtil.getDateFormat("yyyy-MM-dd 00:00:00"));
                personMap.put("endDispatchTime", DateUtil.getDateFormat((DateUtil.getBeforeOrAfter(new Date(), 1)), "yyyy-MM-dd HH:mm:ss"));
                personMap.put("groupLevel", BackConstant.XJX_OVERDUE_LEVEL_F_M2);
                personMap.put("userStatus", BackConstant.ON);
                List<MmanLoanCollectionPerson> persons = backUserDao.findUnCompleteCollectionOrderByCurrentUnCompleteCountListByMap(personMap);
                if (CollectionUtils.isEmpty(persons)) {
                    this.inserWarnMsg();
                    return;
                }
                personList.addAll(persons);
            } else {
                personList.addAll(setPersonList(order.getLoanId()));
            }
        }
    }

    /**
     * 大额订单逾期天数大于60天时升级到F-M3组
     *
     * @param order
     * @param orderList
     * @param personList
     */
    private void dispatchOrderToFM3(MmanLoanCollectionOrder order, List<MmanLoanCollectionOrder> orderList, List<MmanLoanCollectionPerson> personList) {
        if (!BackConstant.XJX_OVERDUE_LEVEL_F_M3.equals(order.getCurrentOverdueLevel())) {
            this.setCommonVariables(order);
            orderList.add(order);

            if (isBeforePeroidCollectionCompleted(order.getLoanId())) {
                Map<String, String> personMap = new HashMap<>();
                personMap.put("beginDispatchTime", DateUtil.getDateFormat("yyyy-MM-dd 00:00:00"));
                personMap.put("endDispatchTime", DateUtil.getDateFormat((DateUtil.getBeforeOrAfter(new Date(), 1)), "yyyy-MM-dd HH:mm:ss"));
                personMap.put("groupLevel", BackConstant.XJX_OVERDUE_LEVEL_F_M3);
                personMap.put("userStatus", BackConstant.ON);
                List<MmanLoanCollectionPerson> persons = backUserDao.findUnCompleteCollectionOrderByCurrentUnCompleteCountListByMap(personMap);
                if (CollectionUtils.isEmpty(persons)) {
                    this.inserWarnMsg();
                    return;
                }
                personList.addAll(persons);
            } else {
                personList.addAll(setPersonList(order.getLoanId()));
            }
        }
    }

    /**
     * 大额订单逾期天数大于180天时升级到F-M6组
     *
     * @param order
     * @param orderList
     * @param personList
     */
    private void dispatchOrderToFM6(MmanLoanCollectionOrder order, List<MmanLoanCollectionOrder> orderList, List<MmanLoanCollectionPerson> personList) {
        if (!BackConstant.XJX_OVERDUE_LEVEL_F_M6.equals(order.getCurrentOverdueLevel())) {
            this.setCommonVariables(order);
            orderList.add(order);

            if (isBeforePeroidCollectionCompleted(order.getLoanId())) {
                Map<String, String> personMap = new HashMap<>();
                personMap.put("beginDispatchTime", DateUtil.getDateFormat("yyyy-MM-dd 00:00:00"));
                personMap.put("endDispatchTime", DateUtil.getDateFormat((DateUtil.getBeforeOrAfter(new Date(), 1)), "yyyy-MM-dd HH:mm:ss"));
                personMap.put("groupLevel", BackConstant.XJX_OVERDUE_LEVEL_F_M6);
                personMap.put("userStatus", BackConstant.ON);
                List<MmanLoanCollectionPerson> persons = backUserDao.findUnCompleteCollectionOrderByCurrentUnCompleteCountListByMap(personMap);
                if (CollectionUtils.isEmpty(persons)) {
                    this.inserWarnMsg();
                    return;
                }
                personList.addAll(persons);
            } else {
                personList.addAll(setPersonList(order.getLoanId()));
            }
        }
    }

    /**
     * 计算给定日期之间相差的天数
     *
     * @param loanEndTime
     * @return
     */
    private int getDaysCount(Date loanEndTime, Date date) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("计算逾期天数出错！");
        }
        if (loanEndTime != null || date != null) {
            long time = date.getTime() - loanEndTime.getTime();
            long betweentDays = time / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(betweentDays));
        }
        return 0;
    }


    @Override
    public void updateOrderInfo(String loanId) {

        MmanUserLoan loan = mmanUserLoanDao.get(loanId);
        if (loan == null) {
            logger.error("更新逾期订单出错，借款对象为空，借款id: " + loanId);
            return;
        }
        if (loan.getTermNumber() != null) {
            logger.error("更新逾期订单出错，更新逾期订单只处理原来小额来源的订单，借款id: " + loanId);
            return;
        }
        CreditLoanPay pay = creditLoanPayDao.findByLoanId(loanId);
        if (pay == null) {
            logger.error("更新逾期订单出错，还款对象为空，借款id: " + loanId);
            return;
        }
        MmanLoanCollectionOrder order = getOrderByLoanId(loanId);
        if (order == null) {
            logger.error("更新逾期订单出错，订单对象为空，借款id: " + loanId);
            return;
        }
        if (BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS.equals(order.getStatus())) {
            logger.error("更新逾期订单出错，该订单已还款完成，请核实，借款id: " + loanId);
            return;
        }

        if (BackConstant.XJX_COLLECTION_ORDER_STATE_STOP.equals(order.getStatus())) {
            logger.error("更新逾期订单出错，该订单已停催，请核实，借款id: " + loanId);
            return;
        }

        BigDecimal loanMoney = loan.getLoanMoney(); // 借款本金
        if (BackConstant.CREDITLOANAPPLY_OVERDUE.equals(loan.getLoanStatus())) {
            // 计算逾期天数
            int overdueDays = getOverdueDays(loan);
            // 计算罚息
            BigDecimal pmoney = getLoanPenalty(loan, loanMoney, overdueDays);

            // 如果滞纳金超过本金金额  则滞纳金金额等于本金且不再增加
            if (pmoney != null) {
                if (pmoney.compareTo(loanMoney) >= 0) {
                    pmoney = loanMoney;
                }
            }

            //对于逾期了多次还款的状况，还够本金则不算新罚息，计算到上次罚息值即可（如借1000，罚息120，上次还了1100，本次补足剩余20即可，罚息依然是120）
                        /*if ((loanMoney.subtract(payedMoney)).compareTo(BigDecimal.valueOf(0.00)) <= 0) {
                            pmoney = null==mmanUserLoanOri.getLoanPenalty()?new BigDecimal("0"):mmanUserLoanOri.getLoanPenalty();
						}*/
//                        loanMoney = loanMoney.add(pmoney).setScale(2, BigDecimal.ROUND_HALF_UP);  // 应还总额

            MmanUserLoan mmanUserLoanForUpdate = new MmanUserLoan();
            mmanUserLoanForUpdate.setId(loan.getId());
            mmanUserLoanForUpdate.setLoanPenalty(pmoney);  // 更新借款表滞纳金
            mmanUserLoanDao.updateMmanUserLoan(mmanUserLoanForUpdate);

            // 更新还款表中剩余应还滞纳金
            BigDecimal znj = pmoney.subtract(pay.getRealgetInterest());  // 剩余应还滞纳金 = 订单滞纳金 - 实收罚息
            CreditLoanPay loanPay = new CreditLoanPay();
            loanPay.setId(pay.getId());
            loanPay.setReceivableInterest(znj);  //  剩余应还罚息
            BigDecimal serviceCharge = BigDecimal.ZERO;
            if (BigDecimal.ZERO.compareTo(loan.getPaidMoney()) < 0) {
                serviceCharge = loan.getServiceCharge();
            }
            loanPay.setReceivableMoney(loan.getLoanMoney().add(pmoney).add(serviceCharge)); // 应还总额
            creditLoanPayDao.updateCreditLoanPay(loanPay);

            // 更新订单表信息
            MmanLoanCollectionOrder collectionOrder = new MmanLoanCollectionOrder();
            collectionOrder.setLoanId(loan.getId());
            collectionOrder.setOverdueDays(overdueDays);
            manLoanCollectionOrderDao.updateOrderOverdueDays(collectionOrder);
        }
    }

    /**
     * 根据loanId判断上一期是否催收完成：
     * 大额第一期，则返回ture；大额非第一期，则判断上期是否进催收、催收完成；小额，则返回true
     * lmy
     *
     * @param loanId
     * @return
     */
    public boolean isBeforePeroidCollectionCompleted(String loanId) {
        MmanUserLoan mmanUserLoan = mmanUserLoanDao.get(loanId);
        String type = mmanUserLoan.getBorrowingType();
        if (Constant.SMALL.equals(type)) {
            return true;
        }
        if (Constant.BIG.equals(type)) {
            if (loanId.endsWith("-1")) {//大额第1期，返回true
                return true;
            } else {//大额不是第1期
                String beforeStr = StringUtils.substringBefore(loanId, "-");
                String afterStr = StringUtils.substringAfter(loanId, "-");
                String beforePeroidStr = (Integer.parseInt(afterStr) - 1) + "";
                String beforePeroidLoanId = beforeStr + "-" + beforePeroidStr;//上一期订单loanId
                MmanLoanCollectionOrder beforePeroidCollectionOrder = mmanLoanCollectionOrderDao.getOrderByLoanId(beforePeroidLoanId);
                if (beforePeroidCollectionOrder != null) {
                    if ("4".equals(beforePeroidCollectionOrder.getStatus()) || "-1".equals(beforePeroidCollectionOrder.getStatus())) {//上一期催收完成
                        return true;
                    } else {
                        return false;
                    }
                } else {//上一期未进催收系统
                    return true;
                }
            }
        }
//        if(Constant.FEN.equals(type)){}  分期商城订单，判断上一期是否催收完成
        return true;
    }


    /**
     * 查询大额分期订单对应各期借款单，最早进入催收系统的一期(不包括催收完成、停催)
     *
     * @return
     */
    public MmanUserLoan getFirstMmanUserLoan(MmanUserLoan mmanUserLoanOri) {
        MmanUserLoan mmanUserLoan;
        try {
            if (Constant.BIG.equals(mmanUserLoanOri.getBorrowingType())) {
                String beforeStr = StringUtils.substringBefore(mmanUserLoanOri.getId(), "-") + "-";
                String afterStr = StringUtils.substringAfter(mmanUserLoanOri.getId(), "-");
                int afterInt = Integer.parseInt(afterStr);
                String loanId;
                for (int i = 1; i <= afterInt; i++) {
                    loanId = beforeStr + String.valueOf(i);
                    mmanUserLoan = mmanUserLoanDao.get(loanId);
                    if (mmanUserLoan != null && !("5".equals(mmanUserLoan.getLoanStatus()) || "-1".equals(mmanUserLoan.getLoanStatus()))) {//最早进入催收系统的一期(不包括催收完成、停催)
                        return mmanUserLoan;
                    }
                }
            }
//            if(Constant.FEN.equals(mmanUserLoanOri.getBorrowingType())){ } 分期商城订单；
        } catch (Exception e) {
            logger.error("查询大额分期订单对应各期借款单，最早进入催收系统的一期。出错：", e);
        }
        return mmanUserLoanOri;
    }

    /**
     * 计算订单当前的逾期天数
     *
     * @param mmanUserLoanOri
     * @return
     */
    public int getOverdueDays(MmanUserLoan mmanUserLoanOri) {
        int pday = 0;
        try {
            pday = DateUtil.daysBetween(mmanUserLoanOri.getLoanEndTime(), new Date());
        } catch (ParseException e) {
            logger.error("parse failed", e);
        }
        return pday;
    }

    /**
     * 计算订单当前的逾期天数。大额计算逾期天数，跟之前几期是否催收完成相关
     * lmy
     *
     * @param mmanUserLoanOri
     * @return
     */
    private int getTotalOverdueDays(MmanUserLoan mmanUserLoanOri) {
        int pday = 0;
        if (Constant.SMALL.equals(mmanUserLoanOri.getBorrowingType())) {
            try {
                pday = DateUtil.daysBetween(mmanUserLoanOri.getLoanEndTime(), new Date());
            } catch (ParseException e) {
                logger.error("parse failed", e);
            }
        }
        if (Constant.BIG.equals(mmanUserLoanOri.getBorrowingType())) {
            try {
                if (isBeforePeroidCollectionCompleted(mmanUserLoanOri.getId())) {
                    pday = DateUtil.daysBetween(mmanUserLoanOri.getLoanEndTime(), new Date());
                } else {
                    pday = DateUtil.daysBetween(getFirstMmanUserLoan(mmanUserLoanOri).getLoanEndTime(), new Date());
                }
            } catch (ParseException e) {
                logger.error("parse failed", e);
            }
        }
//        if (Constant.FEN.equals(mmanUserLoanOri.getBorrowingType())) { } 分期商城订单
        return pday;
    }

    /**
     * 大额订单，如果上期未催收完成，设置本期催收员列表
     *
     * @param loanId
     * @return
     */
    private List<MmanLoanCollectionPerson> setPersonList(String loanId) {
        List<MmanLoanCollectionPerson> personList = new ArrayList<>();
        MmanUserLoan mmanUserLoan = mmanUserLoanDao.get(loanId);
        String firstLoadId = getFirstMmanUserLoan(mmanUserLoan).getId();
        MmanLoanCollectionOrder mmanLoanCollectionOrder = mmanLoanCollectionOrderDao.getCollectionOrderByLoanId(firstLoadId);
        String backUserUuid = mmanLoanCollectionOrder.getCurrentCollectionUserId();
        BackUser backUser = backUserDao.getBackUserByUuid(backUserUuid);
        MmanLoanCollectionCompany mmanLoanCollectionCompany = mmanLoanCollectionCompanyDao.getCompanyById(backUser.getCompanyId());
        MmanLoanCollectionPerson mmanLoanCollectionPerson = new MmanLoanCollectionPerson();
        mmanLoanCollectionPerson.setId(backUser.getId().toString());
        mmanLoanCollectionPerson.setUserId(backUser.getUuid());
        mmanLoanCollectionPerson.setLoginName(backUser.getUserAccount());
        mmanLoanCollectionPerson.setUsername(backUser.getUserName());
        mmanLoanCollectionPerson.setGroupLevel(backUser.getGroupLevel());
        mmanLoanCollectionPerson.setCompanyId(backUser.getCompanyId());
        mmanLoanCollectionPerson.setCompanyName(mmanLoanCollectionCompany.getTitle());
        mmanLoanCollectionPerson.setUserStatus(backUser.getUserStatus().toString());
        mmanLoanCollectionPerson.setRealName(backUser.getUserAccount());
        personList.add(mmanLoanCollectionPerson);
        return personList;
    }

    /**
     * @Description 派新订单逻辑
     * @param loanId 借款id
     * @param idNumber 身份证号
     * @param type 借款类型（1 大额   2 小额）
     */
    @Override
    public void dispatchOrderNew(String loanId, String idNumber, String type) {
        try {
            logger.info("处理派单开始，借款id: " + loanId);
            MmanUserLoan mmanUserLoan = mmanUserLoanDao.get(loanId);
            if (mmanUserLoan == null) {
                return;
            }
            CreditLoanPay creditLoanPay = creditLoanPayDao.findByLoanId(loanId);
            if (creditLoanPay == null) {
                return;
            }
            List<MmanLoanCollectionOrder> orderList = new ArrayList<>();
            List<MmanLoanCollectionPerson> personList;
            Date now = new Date();
            String sysName = "系统";
            String sysRemark = "系统派单";
            MmanLoanCollectionOrder order = new MmanLoanCollectionOrder();
            order.setLoanId(mmanUserLoan.getId());
            order.setOrderId(mmanUserLoan.getLoanPyId());
            order.setUserId(mmanUserLoan.getUserId());
            order.setOverdueDays(getOverdueDays(mmanUserLoan));
            order.setTotalOverdueDays(getTotalOverdueDays(mmanUserLoan));
            order.setPayId(creditLoanPay.getId());
            order.setDispatchName(sysName);
            order.setDispatchTime(now);
            order.setOperatorName(sysName);
            order.setRemark(sysRemark);
            order.setJxlStatus(BackConstant.XJX_JXL_STATUS_REFUSE);
            order.setIdNumber(idNumber);  // 借款人身份证号
            orderList.add(order);

            // 区分大小额分别处理
            Map<String, String> personMap = new HashMap<>();
            if (Constant.BIG.equals(type)) {
                if (isBeforePeroidCollectionCompleted(loanId)) {
                    personMap.put("beginDispatchTime", DateUtil.getDateFormat("yyyy-MM-dd 00:00:00"));
                    personMap.put("endDispatchTime", DateUtil.getDateFormat((DateUtil.getBeforeOrAfter(new Date(), 1)), "yyyy-MM-dd HH:mm:ss"));
                    personMap.put("groupLevel", BackConstant.XJX_OVERDUE_LEVEL_F_M1);
                    personMap.put("userStatus", BackConstant.ON);
                    personList = backUserDao.findUnCompleteCollectionOrderByCurrentUnCompleteCountListByMap(personMap);
                } else {
                    personList = setPersonList(loanId);
                }

                if (CollectionUtils.isEmpty(personList)) {
                    SysAlertMsg alertMsg = new SysAlertMsg();
                    alertMsg.setId(IdGen.uuid());
                    alertMsg.setTitle("分配催收任务失败");
                    alertMsg.setContent("所有公司F-M1组查无可用催收人,请及时添加或启用该组催收员。");
                    alertMsg.setDealStatus(BackConstant.OFF);
                    alertMsg.setStatus(BackConstant.OFF);
                    alertMsg.setType(SysAlertMsg.TYPE_COMMON);
                    sysAlertMsgDao.insert(alertMsg);
                    logger.warn("所有公司F-M1组查无可用催收人...");
                    return;
                }
            } else {
                personMap.put("beginDispatchTime", DateUtil.getDateFormat("yyyy-MM-dd 00:00:00"));
                personMap.put("endDispatchTime", DateUtil.getDateFormat((DateUtil.getBeforeOrAfter(new Date(), 1)), "yyyy-MM-dd HH:mm:ss"));
                Calendar clrNow = Calendar.getInstance();
                int dayNow = clrNow.get(Calendar.DAY_OF_MONTH);
//                if (dayNow == 1) {
//                    personMap.put("groupLevel", BackConstant.XJX_OVERDUE_LEVEL_M1_M2);
//                    personMap.put("userStatus", BackConstant.ON);
//                    personList = backUserDao.findUnCompleteCollectionOrderByCurrentUnCompleteCountListByMap(personMap);
//                    if (CollectionUtils.isEmpty(personList)) {
//                        inserWarnMsg();
//                        return;
//                    }
//                } else {
                    /*
                    Calendar cal = Calendar.getInstance();
                    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 2);
                    Date date = cal.getTime();
                    personMap.put("beginDispatchTime", DateUtil.getDateFormat(date, "yyyy-MM-dd 00:00:00"));
                    personMap.put("endDispatchTime", DateUtil.getDateFormat((DateUtil.getBeforeOrAfter(new Date(), 1)), "yyyy-MM-dd HH:mm:ss"));
                    */
                    personMap.put("groupLevel", BackConstant.XJX_OVERDUE_LEVEL_S1);
                    personMap.put("userStatus", BackConstant.ON);
                    personList = backUserDao.findUnCompleteCollectionOrderByCurrentUnCompleteCountListByMap(personMap);
                    if (CollectionUtils.isEmpty(personList)) {
                        SysAlertMsg alertMsg = new SysAlertMsg();
                        alertMsg.setId(IdGen.uuid());
                        alertMsg.setTitle("分配催收任务失败");
                        alertMsg.setContent("所有公司S1、S2组查无可用催收人,请及时添加或启用该组催收员。");
                        alertMsg.setDealStatus(BackConstant.OFF);
                        alertMsg.setStatus(BackConstant.OFF);
                        alertMsg.setType(SysAlertMsg.TYPE_COMMON);
                        sysAlertMsgDao.insert(alertMsg);
                        logger.warn("所有公司S1、S2组查无可用催收人...");
                        return;
                    }
//                }
            }
            mmanLoanCollectionRecordService.assignCollectionOrderToRelatedGroup(orderList, personList, now);
            logger.info("处理派单完成，借款id: " + loanId);
        } catch (Exception e) {
            logger.error("新订单入催派单出错，借款id: " + loanId);
            e.printStackTrace();
        }

    }
    /**
     * 逾期订单升级 --节后上线TODO
     *
     * @param loanId
     */
    @Override
    public void orderUpgrade(String loanId){
        MmanLoanCollectionOrder order = new MmanLoanCollectionOrder();
        order.setLoanId(loanId);
        MmanUserLoan loan = mmanUserLoanDao.get(loanId);
        if (loan == null) {
            logger.error("逾期升级出错-loan表无该订单-loanId: " + loanId);
            return;
        }
        List<MmanLoanCollectionOrder> orders = mmanLoanCollectionOrderDao.findList(order);
        order = orders.get(0);
        if (BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS.equals(order.getStatus())) {
            logger.error("逾期升级出错-该订单已还款完成-loanId: " + loanId);
            return;
        }
        if (BackConstant.XJX_COLLECTION_ORDER_STATE_STOP.equals(order.getStatus())) {
            logger.error("逾期升级出错-该定单已停催-loanId: " + loanId);
            return;
        }


        int overdueDays = order.getOverdueDays();
        if (10 <= overdueDays && overdueDays < 30) {
            //订单由S1组 升级至 S2组
            handleOrderUpgrade(order, BackConstant.XJX_OVERDUE_LEVEL_S2);
        }
        if (30 <= overdueDays) {
            //订单由S2组 升级至 M1-M2组
            handleOrderUpgrade(order, BackConstant.XJX_OVERDUE_LEVEL_M1_M2);
        }
    }

    private void handleOrderUpgrade(MmanLoanCollectionOrder order, String groupLevel) {
        Map<String,Object> param = new HashMap();
        param.put("groupLevel",groupLevel);
        CollectionBackUser collectionBackUser = backUserDao.getOneCollectionBackUserGroupByOrderCount(param);
        if (null != collectionBackUser){
            //订单升级
            setCommonVariables(order);//设置逾期升级默认参数
            order.setCurrentCollectionUserId(collectionBackUser.getUuid());//设置当前催收员
            order.setOutsideCompanyId(collectionBackUser.getCompanyId());
            order.setCurrentOverdueLevel(groupLevel);
            order.setStatus("0");
            //更新订单
            manLoanCollectionOrderDao.updateUpgradeOrder(order);
            //添加催收流转日志
            changeLogService.insertMmanLoanCollectionStatusChangeLog(
                    order.getOrderId(),
                    order.getStatus(),
                    BackConstant.XJX_COLLECTION_ORDER_STATE_WAIT,
                    BackConstant.XJX_COLLECTION_STATUS_MOVE_TYPE_CONVERT,
                    "系统",
                    "逾期升级,当前催收员:" + collectionBackUser.getUserName(),
                    collectionBackUser.getCompanyId(),
                    collectionBackUser.getUuid(),
                    groupLevel,
                    groupLevel);
        }else {
            logger.error("fail-逾期升级至失败"+BackConstant.groupNameMap.get(groupLevel)+"组无可用催收员-loanId:"+order.getLoanId());
            SysAlertMsg alertMsg = new SysAlertMsg();
            alertMsg.setTitle("逾期升级至"+BackConstant.groupNameMap.get(groupLevel)+"失败");
            alertMsg.setContent(BackConstant.groupNameMap.get(groupLevel)+"组无可用催收员-loanId:"+order.getLoanId());
            alertMsg.setDealStatus(BackConstant.OFF);
            alertMsg.setStatus(BackConstant.OFF);
            alertMsg.setType(SysAlertMsg.TYPE_COMMON);
            sysAlertMsgService.insert(alertMsg);

        }

    }

//    @Override
//    public void orderUpgrade1(String loanId) {
//        try {
//            MmanUserLoan loan = mmanUserLoanDao.get(loanId);
//            if (loan == null) {
//                logger.error("处理逾期订单升级出错(借款对象为空)，借款id: " + loanId);
//                return;
//            }
//            Calendar clrLoanEnd = Calendar.getInstance();
//            clrLoanEnd.setTime(loan.getLoanEndTime());
//            int yearLoanEnd = clrLoanEnd.get(Calendar.YEAR);
//            int monthLoanEnd = clrLoanEnd.get(Calendar.MONTH) + 1;
//            Calendar calendar = Calendar.getInstance();
//            int yearNow = calendar.get(Calendar.YEAR);
//            int monthNow = calendar.get(Calendar.MONTH) + 1;
//            int dayNow = calendar.get(Calendar.DAY_OF_MONTH);
//            int monthDiff = (yearNow * 12 + monthNow) - (yearLoanEnd * 12 + monthLoanEnd);
//
//            List<MmanLoanCollectionOrder> orderList = new ArrayList<>();
//            List<MmanLoanCollectionPerson> personList = new ArrayList<>();
//            MmanLoanCollectionOrder order = new MmanLoanCollectionOrder();
//            order.setLoanId(loanId);
//            List<MmanLoanCollectionOrder> orders = mmanLoanCollectionOrderDao.findList(order);
//            if (CollectionUtils.isEmpty(orders)) {
//                logger.error("逾期升级出错，订单为空，借款id: " + loanId);
//                return;
//            }
//            order = orders.get(0);
//            if (BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS.equals(order.getStatus())) {
//                logger.error("逾期升级出错，订单已还款完成，借款id: " + loanId);
//                return;
//            }
//
//            if (BackConstant.XJX_COLLECTION_ORDER_STATE_STOP.equals(order.getStatus())) {
//                logger.error("逾期升级出错，订单已停催，借款id: " + loanId);
//                return;
//            }
//
//            if (dayNow == 1) {
//                // 跨越次数为0不处理
//                if (monthDiff == 0) {
//                    return;
//                }
//                if (monthDiff == 1) {
//                    this.dispatchOrderToM1(order, orderList, personList);
//                } else if (monthDiff == 2) {
//                    this.dispatchOrderToM2(order, orderList, personList);
//                } else if (monthDiff >= 3 && monthDiff < 6) {
//                    this.dispatchOrderToM3(order, orderList, personList);
//                } else if (monthDiff >= 6) {
//                    this.dispatchOrderToM6(order, orderList, personList);
//                }
//            } else if (dayNow >= 12) {
//                // 默认逾期升级天数（用一月内,s1组逾期10天，第二天升级至S2组)
//                int orderUpgradeDay = getOrderUpgradeDay();
//                this.upGrageS1OrdersToS2Users(order, orderList, personList, loan, orderUpgradeDay);
//            }
//            mmanLoanCollectionRecordService.assignCollectionOrderToRelatedGroup(orderList, personList, new Date());
//        } catch (Exception e) {
//            logger.error("处理订单逾期升级出错，借款id: " + loanId);
//            e.printStackTrace();
//        }
//    }


    /**
     * 非1号时，S1组订单逾期天数达到10天升级为S2
     *
     * @param order
     * @param orderList
     * @param personList
     */
    private void upGrageS1OrdersToS2Users(MmanLoanCollectionOrder order, List<MmanLoanCollectionOrder> orderList, List<MmanLoanCollectionPerson> personList,
                                          MmanUserLoan loan, int orderUpgradeDay) {
        if (isMeetTheConditions(getOverdueDays(loan), orderUpgradeDay, order)) {
            this.setCommonVariables(order);
            orderList.add(order);

            Map<String, String> personMap = new HashMap<>();
            personMap.put("beginDispatchTime", DateUtil.getDateFormat("yyyy-MM-dd 00:00:00"));
            personMap.put("endDispatchTime", DateUtil.getDateFormat((DateUtil.getBeforeOrAfter(new Date(), 1)), "yyyy-MM-dd HH:mm:ss"));
            personMap.put("groupLevel", BackConstant.XJX_OVERDUE_LEVEL_S2);
            personMap.put("userStatus", BackConstant.ON);

            List<MmanLoanCollectionPerson> persons = backUserDao.findUnCompleteCollectionOrderByCurrentUnCompleteCountListByMap(personMap);
            if (CollectionUtils.isEmpty(persons)) {
                SysAlertMsg alertMsg = new SysAlertMsg();
                alertMsg.setId(IdGen.uuid());
                alertMsg.setTitle("分配催收任务失败");
                alertMsg.setContent("所有公司S2组查无可用催收人,请及时添加或启用该组催收员。");
                alertMsg.setDealStatus(BackConstant.OFF);
                alertMsg.setStatus(BackConstant.OFF);
                alertMsg.setType(SysAlertMsg.TYPE_COMMON);
                sysAlertMsgDao.insert(alertMsg);
                logger.warn("所有公司S2组查无可用催收人...");
            }
            personList.addAll(persons);
        }
    }

    /**
     * 跨月一次的订单分到M1-M2组
     *
     * @param order
     * @param orderList
     * @param personList
     */
    private void dispatchOrderToM1(MmanLoanCollectionOrder order, List<MmanLoanCollectionOrder> orderList, List<MmanLoanCollectionPerson> personList) {
        if (!BackConstant.XJX_OVERDUE_LEVEL_M1_M2.equals(order.getCurrentOverdueLevel())) {
            this.setCommonVariables(order);
            orderList.add(order);

            Map<String, String> personMap = new HashMap<>();
            personMap.put("beginDispatchTime", DateUtil.getDateFormat("yyyy-MM-dd 00:00:00"));
            personMap.put("endDispatchTime", DateUtil.getDateFormat((DateUtil.getBeforeOrAfter(new Date(), 1)), "yyyy-MM-dd HH:mm:ss"));
            personMap.put("groupLevel", BackConstant.XJX_OVERDUE_LEVEL_M1_M2);
            personMap.put("userStatus", BackConstant.ON);
            List<MmanLoanCollectionPerson> persons = backUserDao.findUnCompleteCollectionOrderByCurrentUnCompleteCountListByMap(personMap);
            if (CollectionUtils.isEmpty(persons)) {
                this.inserWarnMsg();
                return;
            }
            personList.addAll(persons);
        }
    }

    /**
     * 跨月两次的订单分到M2-M3组
     *
     * @param order
     * @param orderList
     * @param personList
     */
    private void dispatchOrderToM2(MmanLoanCollectionOrder order, List<MmanLoanCollectionOrder> orderList, List<MmanLoanCollectionPerson> personList) {
        if (!BackConstant.XJX_OVERDUE_LEVEL_M2_M3.equals(order.getCurrentOverdueLevel())) {
            this.setCommonVariables(order);
            orderList.add(order);

            Map<String, String> personMap = new HashMap<>();
            personMap.put("beginDispatchTime", DateUtil.getDateFormat("yyyy-MM-dd 00:00:00"));
            personMap.put("endDispatchTime", DateUtil.getDateFormat((DateUtil.getBeforeOrAfter(new Date(), 1)), "yyyy-MM-dd HH:mm:ss"));
            personMap.put("groupLevel", BackConstant.XJX_OVERDUE_LEVEL_M2_M3);
            personMap.put("userStatus", BackConstant.ON);

            List<MmanLoanCollectionPerson> persons = backUserDao.findUnCompleteCollectionOrderByCurrentUnCompleteCountListByMap(personMap);
            if (CollectionUtils.isEmpty(persons)) {
                SysAlertMsg alertMsg = new SysAlertMsg();
                alertMsg.setId(IdGen.uuid());
                alertMsg.setTitle("分配催收任务失败");
                alertMsg.setContent("所有公司M2-M3组查无可用催收人,请及时添加或启用该组催收员。");
                alertMsg.setDealStatus(BackConstant.OFF);
                alertMsg.setStatus(BackConstant.OFF);
                alertMsg.setType(SysAlertMsg.TYPE_COMMON);
                sysAlertMsgDao.insert(alertMsg);
                logger.warn("所有公司M2-M3组查无可用催收人...");
                return;
            }
            personList.addAll(persons);
        }
    }

    /**
     * 跨月3-5次的订单分到M3+组
     *
     * @param order
     * @param orderList
     * @param personList
     */
    private void dispatchOrderToM3(MmanLoanCollectionOrder order, List<MmanLoanCollectionOrder> orderList, List<MmanLoanCollectionPerson> personList) {
        if (!BackConstant.XJX_OVERDUE_LEVEL_M3P.equals(order.getCurrentOverdueLevel())) {
            this.setCommonVariables(order);
            orderList.add(order);

            Map<String, String> personMap = new HashMap<>();
            personMap.put("beginDispatchTime", DateUtil.getDateFormat("yyyy-MM-dd 00:00:00"));
            personMap.put("endDispatchTime", DateUtil.getDateFormat((DateUtil.getBeforeOrAfter(new Date(), 1)), "yyyy-MM-dd HH:mm:ss"));
            personMap.put("groupLevel", BackConstant.XJX_OVERDUE_LEVEL_M3P);
            personMap.put("userStatus", BackConstant.ON);

            List<MmanLoanCollectionPerson> persons = backUserDao.findUnCompleteCollectionOrderByCurrentUnCompleteCountListByMap(personMap);
            if (CollectionUtils.isEmpty(persons)) {
                SysAlertMsg alertMsg = new SysAlertMsg();
                alertMsg.setId(IdGen.uuid());
                alertMsg.setTitle("分配催收任务失败");
                alertMsg.setContent("所有公司M3+组查无可用催收人,请及时添加或启用该组催收员。");
                alertMsg.setDealStatus(BackConstant.OFF);
                alertMsg.setStatus(BackConstant.OFF);
                alertMsg.setType(SysAlertMsg.TYPE_COMMON);
                sysAlertMsgDao.insert(alertMsg);
                logger.warn("所有M3+组查无可用催收人...");
                return;
            }
            personList.addAll(persons);
        }
    }

    /**
     * 跨月6次及以上的订单分到M3+组
     *
     * @param order
     * @param orderList
     * @param personList
     */
    private void dispatchOrderToM6(MmanLoanCollectionOrder order, List<MmanLoanCollectionOrder> orderList, List<MmanLoanCollectionPerson> personList) {
        if (!BackConstant.XJX_OVERDUE_LEVEL_M6P.equals(order.getCurrentOverdueLevel())) {
            this.setCommonVariables(order);
            orderList.add(order);

            Map<String, String> personMap = new HashMap<>();
            personMap.put("beginDispatchTime", DateUtil.getDateFormat("yyyy-MM-dd 00:00:00"));
            personMap.put("endDispatchTime", DateUtil.getDateFormat((DateUtil.getBeforeOrAfter(new Date(), 1)), "yyyy-MM-dd HH:mm:ss"));
            personMap.put("groupLevel", BackConstant.XJX_OVERDUE_LEVEL_M6P);
            personMap.put("userStatus", BackConstant.ON);

            List<MmanLoanCollectionPerson> persons = backUserDao.findUnCompleteCollectionOrderByCurrentUnCompleteCountListByMap(personMap);
            if (CollectionUtils.isEmpty(persons)) {
                SysAlertMsg alertMsg = new SysAlertMsg();
                alertMsg.setId(IdGen.uuid());
                alertMsg.setTitle("分配催收任务失败");
                alertMsg.setContent("所有公司M6+组查无可用催收人,请及时添加或启用该组催收员。");
                alertMsg.setDealStatus(BackConstant.OFF);
                alertMsg.setStatus(BackConstant.OFF);
                alertMsg.setType(SysAlertMsg.TYPE_COMMON);
                sysAlertMsgDao.insert(alertMsg);
                logger.warn("所有M6+组查无可用催收人...");
                return;
            }
            personList.addAll(persons);
        }
    }

    /**
     * M1-M2组满足条件催收员为空时，插入提示消息
     */
    private void inserWarnMsg() {
        SysAlertMsg alertMsg = new SysAlertMsg();
        alertMsg.setId(IdGen.uuid());
        alertMsg.setTitle("分配催收任务失败");
        alertMsg.setContent("所有M1-M2组查无可用催收人,请及时添加或启用该组催收员。");
        alertMsg.setDealStatus(BackConstant.OFF);
        alertMsg.setStatus(BackConstant.OFF);
        alertMsg.setType(SysAlertMsg.TYPE_COMMON);
        sysAlertMsgDao.insert(alertMsg);
        logger.warn("所有M1-M2组查无可用催收人...");
    }

    /**
     * 设置派单时定单对象的通用参数
     *
     * @param order
     */
    private void setCommonVariables(MmanLoanCollectionOrder order) {
        String sysName = "系统";
        String sysPromoteRemark = "逾期升级，系统重新派单";
        order.setDispatchName(sysName);
        order.setDispatchTime(new Date());
        order.setUpdateDate(new Date());
        order.setS1Flag(null);   // 订单逾期升级  S1flg置为null
        order.setOperatorName(sysName);
        order.setRemark(sysPromoteRemark);
        order.setLastCollectionUserId(order.getCurrentCollectionUserId());//上一催收员
        //更新聚信立报告申请审核状态为初始状态，下一催收员要看需要重新申请
//        order.setJxlStatus(BackConstant.XJX_JXL_STATUS_REFUSE);
    }

    /**
     * 计算订单罚息
     *
     * @param loan
     * @param loanMoney
     * @param pday
     * @return
     */
    private BigDecimal getLoanPenalty(MmanUserLoan loan, BigDecimal loanMoney, int pday) {
        BigDecimal firstDayRate = new BigDecimal(0.05);
        BigDecimal pRate = new BigDecimal(Integer.parseInt(loan.getLoanPenaltyRate())).divide(new BigDecimal(10000));
        BigDecimal paidMoney = loan.getPaidMoney();  // 借款本金和服务费之和
        BigDecimal pmoney = null;
//        if (loan.getBorrowingType().equals(Constant.BIG)) {
//            pmoney = loanMoney.multiply(new BigDecimal(pday)).multiply(pRate).setScale(2, BigDecimal.ROUND_HALF_UP);
//            return pmoney;
//        }

        // 计算订单罚息
        try {
            // 应还时间
            long loanEndTime = loan.getLoanEndTime().getTime();
            // 最近一次订单计费方式更改截止时间
            long newestOrderTime = DateUtils.parseDate("2018-09-02", "yyyy-MM-dd").getTime();
            // 最近一次订单计费方式更改截止时间
            long secondOrderTime = DateUtils.parseDate("2018-03-01", "yyyy-MM-dd").getTime();

            if (loanEndTime >= newestOrderTime) {
                // 按固定逾期费率收取（砍头息滞纳金计算方式）
                pmoney = getPayMoney(loanMoney, pday, pRate, paidMoney, pmoney);
            } else if (loanEndTime >= secondOrderTime) {
                // 按(本金)日费率：首日（5%）、之后每日按千分之6收取
                pmoney = (loanMoney.multiply(firstDayRate).add(loanMoney.multiply(pRate).multiply(new BigDecimal(pday - 1)))).setScale(2, BigDecimal.ROUND_HALF_UP);
            } else {
                pmoney = getPayMoney(loanMoney, pday, pRate, paidMoney, pmoney);

            }
        } catch (Exception e) {
            logger.error("calculate Penalty error！ loanId =" + loan.getId());
        }
        return pmoney;
    }

    // 根据paidMoney是否为空来判断计算滞纳金的方式
    private BigDecimal getPayMoney(BigDecimal loanMoney, int pday, BigDecimal pRate, BigDecimal paidMoney, BigDecimal pmoney) {
        if (paidMoney != null && paidMoney.compareTo(BigDecimal.ZERO) > 0) {
            // 砍头息滞纳金计算方式
            pmoney = (paidMoney.multiply(pRate).multiply(new BigDecimal(pday))).setScale(2, BigDecimal.ROUND_HALF_UP);//逾期金额(部分还款算全罚息  服务费算罚息)
        } else {
            pmoney = (loanMoney.multiply(pRate).multiply(new BigDecimal(pday))).setScale(2, BigDecimal.ROUND_HALF_UP);//逾期金额（部分还款算全罚息  服务费不算罚息)
        }
        return pmoney;
    }

    /**
     * 判断订单是否满足逾期升级条件（S1--->S2）
     * 1.订单当前逾期等级是否是S1或S2
     * 2.订单逾期天数是否满足逾期天数升级条件
     * 3.检查日志，判断订单本次是否已经处理（升级）
     * 4.当前订单的状态不是续期或者催收成功
     *
     * @param pday
     * @param orderUpgradeDay
     * @param order
     * @return
     */
    private boolean isMeetTheConditions(int pday, int orderUpgradeDay, MmanLoanCollectionOrder order) {
        boolean currentOverdueCondition = BackConstant.XJX_OVERDUE_LEVEL_S1.equals(order.getCurrentOverdueLevel());
        boolean isGreaterThanRuleDays = pday >= orderUpgradeDay;
        boolean hasLog = checkLog(order.getOrderId());
        return currentOverdueCondition && isGreaterThanRuleDays && !hasLog;
    }

    /**
     * 根据派单订单id,查询是否有系统逾期升级至S2的转派日志
     *
     * @param loanCollectionOrderId
     * @return
     */
    public boolean checkLog(String loanCollectionOrderId) {
        HashMap<String, String> paraMap = new HashMap<>();
        paraMap.put("collectionOrderId", loanCollectionOrderId);
        int count = null == statusChangeLogDao.findSystemUpToS2Log(paraMap) ? 0 : statusChangeLogDao.findSystemUpToS2Log(paraMap).intValue();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取当前规则下订单逾期升级的天数
     *
     * @param
     * @return
     * @throws Exception
     */
    private int getOrderUpgradeDay() throws Exception {
        int orderUpgradeDay;
        if (StringUtils.isNotEmpty(JedisDataClient.get("OVERDUE_UPGRADE_DAY"))) {
            orderUpgradeDay = Integer.valueOf(JedisDataClient.get("OVERDUE_UPGRADE_DAY"));
        } else {
            ChannelSwitching orderOverdueRule = channelSwitchingDao.getChannelValue("order_overdue_rule");
            String channelValue = orderOverdueRule.getChannelValue();
            orderUpgradeDay = Integer.valueOf(channelValue) + 10;
            JedisDataClient.set("OVERDUE_UPGRADE_DAY", String.valueOf(orderUpgradeDay), 60 * 60 * 6);
        }
        return orderUpgradeDay;
    }

    @Override
    public Map<String, String> getMerchantMap() {
        Map<String, String> result = new HashMap<>(4);
        List<MerchantInfo> list = getAllMerchants();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(list)) {
            for (MerchantInfo merchantInfo : list) {
                String merchantId = merchantInfo.getMerchantId();
                String merchantName = merchantInfo.getMerchantName();
                if (StringUtils.isNotEmpty(merchantId) && StringUtils.isNotEmpty(merchantName)) {
                    result.put(merchantId, merchantName);
                }
            }
        }
        return result;
    }

    @Override
    public Map<Integer, String> getRepayChannelMap() {
        Map<Integer, String> result = new HashMap<>(4);
        List<RepayChannelConfig> list = getAllRepayChannel();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(list)) {
            for (RepayChannelConfig repayChannelConfig : list) {
                Integer repayChannel = repayChannelConfig.getRepayChannel();
                String repayChannelName = repayChannelConfig.getRepayChannelName();
                if (null != repayChannel && StringUtils.isNotEmpty(repayChannelName)) {
                    result.put(repayChannel, repayChannelName);
                }
            }
        }
        return result;
    }

    /**
     * 获取所有的放款主体信息
     *
     * @return
     */
    private List<RepayChannelConfig> getAllRepayChannel() {
        try {
            List<RepayChannelConfig> repayChannelList = JedisDataClient.getList(BackConstant.REDIS_KEY_PREFIX, REPAY_CHANNEL_REDIS_KEY);
            if (org.apache.commons.collections.CollectionUtils.isEmpty(repayChannelList)) {
                repayChannelList = repayChannelConfigDao.getAll();
                JedisDataClient.setList(BackConstant.REDIS_KEY_PREFIX, REPAY_CHANNEL_REDIS_KEY, repayChannelList, 10 * 60);
            }
            return repayChannelList;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取商户信息失败...");
        }
        return null;
    }

    /**
     * 获取所有的商户信息
     *
     * @return
     */
    private List<MerchantInfo> getAllMerchants() {
        try {
            List<MerchantInfo> merchantList = JedisDataClient.getList(BackConstant.REDIS_KEY_PREFIX, MERCHANT_INFO_REDIS_KEY);
            if (org.apache.commons.collections.CollectionUtils.isEmpty(merchantList)) {
                merchantList = merchantInfoDao.getAll();
                JedisDataClient.setList(BackConstant.REDIS_KEY_PREFIX, MERCHANT_INFO_REDIS_KEY, merchantList, 10 * 60);
            }
            return merchantList;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取商户信息失败...");
        }
        return null;
    }
    /**
     * 最新首逾派单逻辑
     */
    @Override
    public Boolean distributeOrder(MmanLoanCollectionOrder order, String merchantNumber) {
        JSONObject user = null;
        try {
            String backUser = JedisDataClient.lpop(BackConstant.DISTRIBUTE_BACK_USER);
            JedisDataClient.rpush(BackConstant.DISTRIBUTE_BACK_USER,backUser);
            user = JSON.parseObject(backUser);
            String currentCollectionUserId = user.get("uuid").toString();
            String backUserName =  user.get("userName").toString();
            String companyId = user.get("companyId").toString();
            order.setCurrentCollectionUserId(currentCollectionUserId);
            order.setOutsideCompanyId(companyId);
            manLoanCollectionOrderDao.insertCollectionOrder(order);
            logger.error("distributeOrder-end-loanId="+order.getLoanId());
            //添加订单催收流转日志
            insertMmanLoanCollectionStatusChangeLog(
                    order.getOrderId(),
                    BackConstant.XJX_COLLECTION_ORDER_STATE_WAIT,
                    "1",
                    "系统",
                    "系统派单，催收员:" + backUserName,
                    companyId,
                    currentCollectionUserId,
                    BackConstant.XJX_OVERDUE_LEVEL_S1,
                    BackConstant.XJX_OVERDUE_LEVEL_S1);
            return true;
        } catch (Exception e) {
            logger.error("distributeOrder-exception-loanId="+order.getLoanId(),e);
            return false;
        }
    }
    private void insertMmanLoanCollectionStatusChangeLog(String orderId,String afterStatus, String type, String operatorName, String remark, String companyId, String currentCollectionUserId, String currentCollectionUserLevel, String currentCollectionOrderLevel) {
        MmanLoanCollectionStatusChangeLog changeLog = new MmanLoanCollectionStatusChangeLog();
        changeLog.setId(IdGen.uuid());
        changeLog.setLoanCollectionOrderId(orderId);
        changeLog.setAfterStatus(afterStatus);//订单状态 ：0:待催收 1:催收中  4:还款完成
        changeLog.setType(type);// 操作类型 1:入催  2:逾期升级  3:转单  4：还款完成
        changeLog.setCreateDate(new Date());
        changeLog.setOperatorName(operatorName);
        changeLog.setRemark(remark);
        changeLog.setCompanyId(companyId);
        changeLog.setCurrentCollectionUserId(currentCollectionUserId);
        changeLog.setCurrentCollectionUserLevel(currentCollectionUserLevel);
        changeLog.setCurrentCollectionOrderLevel(currentCollectionOrderLevel);
        statusChangeLogDao.insert(changeLog);

    }

    /**
     *  查询派单时间是上个月且未还款完成的订单。
     * */
    @Override
    public List<MmanLoanCollectionOrder> getLastMonthOrder(String getLastMonthOrder) {
        return manLoanCollectionOrderDao.getLastMonthOrder(getLastMonthOrder);
    }

    /**
     * 处理需要转到下月的订单
     * 每月1号将上个月转过来的订单，添加流转日志和定单的虚拟派单时间（1号当天逾期升级的订单除外）
     * */
    @Override
    public void handleLastMonthOrder(MmanLoanCollectionOrder order) {
        manLoanCollectionOrderDao.updateVirtualDispathTime(order.getLoanId());

        insertMmanLoanCollectionStatusChangeLog(
                order.getOrderId(),
                "1",
                "1",
                "系统",
                "上月未完成订单流转",
                order.getOutsideCompanyId(),
                order.getCurrentCollectionUserId(),
                order.getCurrentOverdueLevel(),
                order.getCurrentOverdueLevel());

        logger.info("handleLastMonthOrder-loanId="+order.getLoanId());


    }
}
