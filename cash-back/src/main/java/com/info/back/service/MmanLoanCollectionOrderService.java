package com.info.back.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.back.dao.*;
import com.info.back.utils.BackConstant;
import com.info.web.pojo.*;

import com.info.web.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.info.web.util.PageConfig;
import com.info.back.result.JsonResult;
import com.info.back.utils.IdGen;
import com.info.constant.Constant;

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

    @Override
    public List<MmanLoanCollectionOrder> getOrderList(MmanLoanCollectionOrder mmanLoanCollectionOrder) {
        return mmanLoanCollectionOrderDao.getOrderList(mmanLoanCollectionOrder);
    }

    @Override
    public PageConfig<MmanLoanCollectionOrder> findPage(HashMap<String, Object> params) {
        PageConfig<MmanLoanCollectionOrder> page = new PageConfig<MmanLoanCollectionOrder>();
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
                logger.info("toReductionPage, id = " + params.get("id").toString() + ", order = " + order);
                if (order != null) {
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
                    if (realMoney.intValue() >= loanMoney.intValue()) {
                        deductibleMoney = receivableMoney.subtract(realMoney);
                    } else {
                        deductibleMoney = new BigDecimal(0);
                    }
                    params.put("receivableMoney", receivableMoney);//应还金额
                    params.put("loanMoney", loanMoney);//本金
                    params.put("loanPenalty", loanPenalty);//滞纳金
                    params.put("realMoney", realMoney);//已还金额
                    params.put("deductibleMoney", deductibleMoney);//可减免金额
                    params.put("loanId", order.getLoanId());//借款id
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
    public void updateOrderInfo(String loanId) {

        MmanUserLoan loan = mmanUserLoanDao.get(loanId);
        if (loan == null) {
            logger.error("更新逾期订单出错，借款对象为空，借款id: " + loanId);
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
        if(BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS.equals(order.getStatus())){
            logger.error("更新逾期订单出错，该订单已还款完成，请核实，借款id: " + loanId);
            return;
        }

        BigDecimal loanMoney = loan.getLoanMoney(); // 借款本金
        if (BackConstant.CREDITLOANAPPLY_OVERDUE.equals(loan.getLoanStatus())) {
            // 计算去逾期天数
            int overdueDays = getOverdueDays(loan);
            // 计算罚息
            BigDecimal pmoney = getLoanPenalty(loan, loanMoney, overdueDays);

            // 如果滞纳金超过本金金额  则滞纳金金额等于本金且不再增加
            if (pmoney.compareTo(loanMoney) >= 0) {
                pmoney = loanMoney;
            }

            //对于逾期了多次还款的状况，还够本金则不算新罚息，计算到上次罚息值即可（如借1000，罚息120，上次还了1100，本次补足剩余20即可，罚息依然是120）
                        /*if ((loanMoney.subtract(payedMoney)).compareTo(BigDecimal.valueOf(0.00)) <= 0) {
                            pmoney = null==mmanUserLoanOri.getLoanPenalty()?new BigDecimal("0"):mmanUserLoanOri.getLoanPenalty();
						}*/
//                        loanMoney = loanMoney.add(pmoney).setScale(2, BigDecimal.ROUND_HALF_UP);  // 应还总额

            // 外层循环处理时间随着数据量增长会越来越长，防止更新滞纳金（罚息）时覆盖更新已还款的数据

            MmanUserLoan mmanUserLoanForUpdate = new MmanUserLoan();
            mmanUserLoanForUpdate.setId(loan.getId());
            mmanUserLoanForUpdate.setLoanPenalty(pmoney);  // 更新借款表滞纳金
            mmanUserLoanDao.updateMmanUserLoan(mmanUserLoanForUpdate);

            // 更新还款表中剩余应还滞纳金
            BigDecimal znj = pmoney.subtract(pay.getRealgetInterest());  // 剩余应还滞纳金 = 订单滞纳金 - 实收罚息
            CreditLoanPay loanPay = new CreditLoanPay();
            loanPay.setId(pay.getId());
            loanPay.setReceivableInterest(znj);  //  剩余应还罚息
            loanPay.setReceivableMoney(loan.getLoanMoney().add(pmoney)); // 应还总额
            creditLoanPayDao.updateCreditLoanPay(loanPay);

            // 更新订单表信息
            MmanLoanCollectionOrder collectionOrder = new MmanLoanCollectionOrder();
            collectionOrder.setLoanId(loan.getId());
            collectionOrder.setOverdueDays(overdueDays);
            manLoanCollectionOrderDao.updateOrderOverdueDays(collectionOrder);

        }
    }

    /**
     * 计算订单当前的逾期天数
     *
     * @param mmanUserLoanOri
     * @return
     */
    private int getOverdueDays(MmanUserLoan mmanUserLoanOri) {
        int pday = 0;
        try {
            pday = DateUtil.daysBetween(mmanUserLoanOri.getLoanEndTime(), new Date());
        } catch (ParseException e) {
            logger.error("parse failed", e);
        }
        return pday;
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
        BigDecimal pRate = new BigDecimal(Integer.parseInt(loan.getLoanPenaltyRate())).divide(new BigDecimal(10000));
//        BigDecimal pRate = new BigDecimal((Double.parseDouble(mmanUserLoanOri.getLoanPenaltyRate()) / 10000));//罚息率
        BigDecimal paidMoney = loan.getPaidMoney();  // 借款本金和服务费之和
//        BigDecimal loanMoney = mmanUserLoanOri.getLoanMoney();
        BigDecimal pmoney = null;
        if (loan.getBorrowingType().equals(Constant.BIG)) {
            pmoney = loanMoney.multiply(new BigDecimal(pday)).multiply(pRate).setScale(2, BigDecimal.ROUND_HALF_UP);
            return pmoney;
        }

        // 计算订单罚息
        try {
            if (paidMoney != null && paidMoney.compareTo(BigDecimal.ZERO) > 0) {
                pmoney = (paidMoney.multiply(pRate).multiply(new BigDecimal(pday))).setScale(2, BigDecimal.ROUND_HALF_UP);//逾期金额(部分还款算全罚息  服务费算罚息)
            } else {
                pmoney = (loanMoney.multiply(pRate).multiply(new BigDecimal(pday))).setScale(2, BigDecimal.ROUND_HALF_UP);//逾期金额（部分还款算全罚息  服务费不算罚息)
            }
        } catch (Exception e) {
            logger.error("calculate Penalty error！ loanid =" + loan.getId());
        }
        return pmoney;
    }
}
