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
import com.xjx.mqclient.pojo.MqMessage;
import com.xjx.mqclient.service.MqClient;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class MmanLoanCollectionRecordService implements IMmanLoanCollectionRecordService {
    private static Logger logger = Logger.getLogger(MmanLoanCollectionRecordService.class);

    private final String WITHHOLD_CHANNEL_KEY = "WITHHOLD_CHANNEL";
    // 借款用户直系联系人关系list redis key
    private final String USER_CLOSE_RELATION_KEY = "userCloseRelation";
    // 借款用户其他联系人关系list redis key
    private final String USER_OTHER_RELATION_KEY = "userOtherRelation";

    private final String XJX_CONTACTS_TYPE_IMMEDIATE = "xjx_contacts_type_immediate";

    private final String XJX_CONTACTS_TYPE_OTHER = "xjx_contacts_type_other";

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
    private IChannelSwitchingDao channelSwitchingDao;
    @Autowired
    private IFengKongService fengKongService;
    @Autowired
    private IMmanUserRelaService userRelaService;
    @Autowired
    private ISysDictService dictService;

    @Qualifier("mqClient")
    @Autowired
    MqClient mqClient;

    public void assignCollectionOrderToRelatedGroup(
            List<MmanLoanCollectionOrder> mmanLoanCollectionOrderList,
            List<MmanLoanCollectionPerson> mmanLoanCollectionPersonList, Date date) {

        if (null != mmanLoanCollectionOrderList && mmanLoanCollectionOrderList.size() > 0) {

            //2.1 查询当前组中所有非禁用的催收员，按照截止到当前手里未处理的订单数升序排序(前面已查)，并查出他们组每人每天单数上限(上限规则中公司+组唯一)，取出有效催收员
            List<MmanLoanCollectionRule> allRuleList = mmanLoanCollectionRuleDao.findList(new MmanLoanCollectionRule());
            HashMap<String, Integer> allRuleLimitCountMap = new HashMap<>();
            if (null != allRuleList && allRuleList.size() > 0) {
                for (MmanLoanCollectionRule ruleOri : allRuleList) {
                    allRuleLimitCountMap.put(ruleOri.getCompanyId() + "_" + ruleOri.getCollectionGroup(), ruleOri.getEveryLimit());
                }
            }

            //开始分配前,先筛选出有效催收员(手里单子未超出上限的催收员),查询并设置每个催收员今日派到手里的订单数(包括已完成的)
            String currentCompanyGroup = "";//当前公司_组
            List<MmanLoanCollectionPerson> effectiveCollectionPersonList = new ArrayList<>();
            for (MmanLoanCollectionPerson person : mmanLoanCollectionPersonList) {
                Integer todayAssignedCount = backUserDao.findTodayAssignedCount(person);//查询当前催收员今日派到手里的订单数(包括已完成的)
                person.setTodayAssignedCount(todayAssignedCount);
                String key = person.getCompanyId() + "_" + person.getGroupLevel();
                Integer limitCount = allRuleLimitCountMap.get(key);//当前催收组每人每天上限
                currentCompanyGroup = person.getCompanyName() + "_" + BackConstant.groupNameMap.get(person.getGroupLevel());
                if (limitCount == null) {
                    limitCount = 0;
                }
                if (todayAssignedCount.intValue() < limitCount.intValue()) {
                    effectiveCollectionPersonList.add(person);
                }
            }

            //2.2 采用多次均匀涂抹法（将待分配订单数按排好序的催收员，依次分配，最后一次内层循环会优先分配给手里待处理单子少的）派单(最多循环次数：ceilAvgCount * effectivePersonCount)
            if (null == effectiveCollectionPersonList || effectiveCollectionPersonList.isEmpty()) {
                SysAlertMsg alertMsg = new SysAlertMsg();
                alertMsg.setTitle("分配催收任务失败");
                alertMsg.setContent("当前" + currentCompanyGroup + "组所有催收员催收规则上限不足，请抓紧调整！");
                alertMsg.setDealStatus(BackConstant.OFF);
                alertMsg.setStatus(BackConstant.OFF);
                alertMsg.setType(SysAlertMsg.TYPE_COMMON);
                sysAlertMsgService.insert(alertMsg);
                logger.error("当前" + currentCompanyGroup + "组所有催收员催收规则上限不足，请抓紧调整...");
            } else {

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
                            } else {//全部派单完成
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
        if (params.get("fengKongLabel") != null && !"".equals(params.get("fengKongLabel"))) {
            if (StringUtils.isNotBlank(params.get("fengKongLabel").toString()) && !"".equals(params.get("fengKongLabel").toString())) {
                FengKong fengKongLabel = fengKongService.getFengKongById(Integer.valueOf((String) params.get("fengKongLabel")));
                params.put("fengKongLabel", "%" + fengKongLabel.getFkLabel() + "%");
            }
        }
        PageConfig<MmanLoanCollectionRecord> pageConfig = new PageConfig<MmanLoanCollectionRecord>();
        pageConfig = paginationDao.findPage("findAll", "findAllCount", params,
                null);

        List<FengKong> fengKongList = fengKongService.getFengKongList();
        List<String> list = new ArrayList<>();
        for (FengKong lable : fengKongList) {
            list.add(lable.getId().toString());
        }
        List<MmanLoanCollectionRecord> items = pageConfig.getItems();
        for (int i = 0; i < items.size(); i++)
            if (StringUtils.isNotBlank(items.get(i).getFengKongLabel())) {
                StringBuilder sb = new StringBuilder();
                String[] labels = items.get(i).getFengKongLabel().split(",");
                for (int j = 0; j < labels.length; j++) {
                    if (list.contains(labels[j].toString())) {
                        sb.append(fengKongService.getFengKongById(Integer.valueOf(labels[j])).getFkLabel());
                        if (labels.length > j + 1) {
                            sb.append("，");
                        } else {
                            sb.append("");
                        }
                    }
                }
                items.get(i).setFengKongLabel(sb.toString());
            }

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

        if ("S1".equals(mmanLoanCollectionOrder.getS1Flag())) {//说明是S1,S2平分过来的单子
            if ("4".equals(person.getGroupLevel())) {
                mmanLoanCollectionOrder.setS1Flag("S1"); // 说明S1组的订单流转到了S2人员手上
            } else {
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
        } else if (BackConstant.XJX_OVERDUE_LEVEL_S2.equals(person.getGroupLevel())) {
            mmanLoanCollectionOrder.setM2ApproveId(person.getUserId());
            mmanLoanCollectionOrder.setM2OperateStatus(BackConstant.OFF);
            mmanLoanCollectionStatusChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_S2);
            if ("S1".equals(mmanLoanCollectionOrder.getS1Flag()) && mmanLoanCollectionOrder.getOverdueDays() <= 10) {
                mmanLoanCollectionOrder.setM2ApproveId(null);
                mmanLoanCollectionOrder.setM1ApproveId(person.getUserId());
                mmanLoanCollectionOrder.setM1OperateStatus(BackConstant.OFF);
                mmanLoanCollectionStatusChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_S1);
            }

        } else if (BackConstant.XJX_OVERDUE_LEVEL_M1_M2.equals(person.getGroupLevel())) {
            mmanLoanCollectionOrder.setM3ApproveId(person.getUserId());
            mmanLoanCollectionOrder.setM3OperateStatus(BackConstant.OFF);

            mmanLoanCollectionStatusChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_M1_M2);

        } else if (BackConstant.XJX_OVERDUE_LEVEL_M2_M3.equals(person.getGroupLevel())) {
            mmanLoanCollectionOrder.setM4ApproveId(person.getUserId());
            mmanLoanCollectionOrder.setM4OperateStatus(BackConstant.OFF);

            mmanLoanCollectionStatusChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_M2_M3);
        } else if (BackConstant.XJX_OVERDUE_LEVEL_M3P.equals(person.getGroupLevel())) {
            mmanLoanCollectionOrder.setM5ApproveId(person.getUserId());
            mmanLoanCollectionOrder.setM5OperateStatus(BackConstant.OFF);

            mmanLoanCollectionStatusChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_M3P);
        } else if (BackConstant.XJX_OVERDUE_LEVEL_M6P.equals(person.getGroupLevel())) {
            mmanLoanCollectionOrder.setM5ApproveId(person.getUserId());
            mmanLoanCollectionOrder.setM5OperateStatus(BackConstant.OFF);

            mmanLoanCollectionStatusChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_M6P);
        } else if (BackConstant.XJX_OVERDUE_LEVEL_F_M1.equals(person.getGroupLevel())) {
            mmanLoanCollectionOrder.setM6ApproveId(person.getUserId());
            mmanLoanCollectionOrder.setM6OperateStatus(BackConstant.OFF);

            mmanLoanCollectionStatusChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_F_M1);
        } else if (BackConstant.XJX_OVERDUE_LEVEL_F_M2.equals(person.getGroupLevel())) {
            mmanLoanCollectionOrder.setM6ApproveId(person.getUserId());
            mmanLoanCollectionOrder.setM6OperateStatus(BackConstant.OFF);

            mmanLoanCollectionStatusChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_F_M2);
        } else if (BackConstant.XJX_OVERDUE_LEVEL_F_M3.equals(person.getGroupLevel())) {
            mmanLoanCollectionOrder.setM6ApproveId(person.getUserId());
            mmanLoanCollectionOrder.setM6OperateStatus(BackConstant.OFF);

            mmanLoanCollectionStatusChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_F_M3);
        } else {
            mmanLoanCollectionOrder.setM6ApproveId(person.getUserId());
            mmanLoanCollectionOrder.setM6OperateStatus(BackConstant.OFF);

            mmanLoanCollectionStatusChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_F_M6);
        }

        mmanLoanCollectionStatusChangeLog.setRemark(mmanLoanCollectionStatusChangeLog.getRemark() + ",催收组：" + BackConstant.groupNameMap.get(mmanLoanCollectionStatusChangeLog.getCurrentCollectionOrderLevel()));
        mmanLoanCollectionStatusChangeLog.setAfterStatus(mmanLoanCollectionOrder.getStatus());
        mmanLoanCollectionStatusChangeLog.setCompanyId(mmanLoanCollectionOrder.getOutsideCompanyId());

        mmanLoanCollectionOrderService.saveMmanLoanCollectionOrder(mmanLoanCollectionOrder);

        //更新还款状态
        CreditLoanPay creditLoanPay = creditLoanPayService.findByLoanId(mmanLoanCollectionOrder.getLoanId());
        creditLoanPay.setStatus(Integer.parseInt(person.getGroupLevel()));
        creditLoanPayService.save(creditLoanPay);

        mmanLoanCollectionStatusChangeLogDao.insert(mmanLoanCollectionStatusChangeLog);

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
    public List<MmanLoanCollectionRecord> findListRecord(HashMap<String, Object> map) {
        return mmanLoanCollectionRecordDao.findListRecord(map);
    }

    @Override
    public JsonResult saveCollection(Map<String, String> params, BackUser user) {
        JsonResult result = new JsonResult("0", "添加成功");
        if (user != null) {
            //更新我的催收订单
            Date now = new Date();
            MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderById(params.get("id").toString());

            MmanLoanCollectionOrder mmanLoanCollectionOrder = new MmanLoanCollectionOrder();
            if (order != null && !BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS.equals(order.getStatus())) {
                if (params.get("repaymentTime") == null || params.get("repaymentTime") == "") {//不填承诺还款时间为催收中
                    mmanLoanCollectionOrder.setStatus(BackConstant.XJX_COLLECTION_ORDER_STATE_ING);
                    mmanLoanCollectionOrder.setPromiseRepaymentTime(null);
                } else {
                    mmanLoanCollectionOrder.setStatus(BackConstant.XJX_COLLECTION_ORDER_STATE_PROMISE);
                    mmanLoanCollectionOrder.setPromiseRepaymentTime(DateUtil.formatDate(params.get("repaymentTime"), "yyyy-MM-dd"));
                }
            }
            params.put("currentOverdueLevel", order.getCurrentOverdueLevel());
            params.put("loanId", order.getLoanId());
            mmanLoanCollectionOrder.setLastCollectionTime(now);
            mmanLoanCollectionOrder.setOperatorName(StringUtils.isNotBlank(user.getUserName()) ? user.getUserName() : "");
            //根据等级设置当前催收员某等级操作状态，1代表操作过催收单
            if (BackConstant.XJX_OVERDUE_LEVEL_S1.equals(user.getGroupLevel())) {
                mmanLoanCollectionOrder.setM1OperateStatus(BackConstant.ON);
            } else if (BackConstant.XJX_OVERDUE_LEVEL_S2.equals(user.getGroupLevel())) {
                if ("S1".equals(order.getS1Flag()) && order.getOverdueDays() <= 10) {
                    mmanLoanCollectionOrder.setM1OperateStatus(BackConstant.ON);
                } else {
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
            mmanLoanCollectionOrder.setS1Flag(order.getS1Flag());
            mmanLoanCollectionOrder.setUpdateDate(now);
            mmanLoanCollectionOrder.setId(order.getId());
            mmanLoanCollectionOrderService.updateRecord(mmanLoanCollectionOrder);

            //添加催收记录
            saveCollectionRecord(params, user, now, order, mmanLoanCollectionOrder);
        } else {
            result.setCode("-1");
            result.setMsg("登录失效,请重新登录");
        }
        return result;
    }

    /**
     * 添加催收记录
     *
     * @param params
     * @param user
     * @param now
     * @param mmanLoanCollectionOrderOri
     * @param mmanLoanCollectionOrder
     */
    private void saveCollectionRecord(Map<String, String> params, BackUser user, Date now, MmanLoanCollectionOrder mmanLoanCollectionOrderOri, MmanLoanCollectionOrder mmanLoanCollectionOrder) {
        MmanLoanCollectionRecord record = new MmanLoanCollectionRecord();
        record.setStressLevel(params.get("stressLevel"));
        record.setCollectionType(params.get("collectionType"));
        record.setContent(params.get("content"));
        record.setRemark(params.get("remark"));
        record.setContactType(params.get("contactType") == null ? "" : params.get("contactType"));
        record.setContactName(params.get("contactName"));
        record.setRelation(params.get("relation"));
        record.setContactPhone(params.get("contactPhone"));
        record.setCollectionDate(now);
        record.setOrderId(mmanLoanCollectionOrder.getId());
        record.setCollectionId(user.getUuid());
        record.setUserId(mmanLoanCollectionOrderOri.getUserId());
        record.setLoanId(params.get("loanId"));
        record.setCurrentOverdueLevel(params.get("currentOverdueLevel"));
        record.setOrderState(mmanLoanCollectionOrderOri.getStatus());
        record.setId(params.get("recordId"));
        record.setCreateDate(now);
        record.setUpdateDate(now);
        mmanLoanCollectionRecordDao.insert(record);
    }

    /**
     * 转派
     */
    @Override
    public JsonResult batchDispatch(BackUser user, MmanLoanCollectionOrder mmanLoanCollectionOrder) {
        JsonResult result = new JsonResult("-1", "转派失败，未知异常");
        HashMap<String, String> resutMap = new HashMap<String, String>();
        //更新我的催收订单
        String currentCollectionUserId = mmanLoanCollectionOrder.getCurrentCollectionUserId();
        String ids = mmanLoanCollectionOrder.getId();
        String[] orderIds = ids.split(",");
        if (orderIds != null && orderIds.length > 0) {
            int successCount = 0;

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
                    if (!"4".equals(mmanLoanCollectionOrderOri.getStatus())) {
                        if (!currentCollectionUserId.equals(mmanLoanCollectionOrderOri.getCurrentCollectionUserId())) {
                            HashMap<String, String> params = new HashMap<String, String>();
                            params.put("currentCollectionUserId", currentCollectionUserId);
                            params.put("orderId", orderId);
                            int countSinge = mmanLoanCollectionStatusChangeLogDao.findOrderSingle(params);//判断催收员是否转派过改单
                            if (countSinge <= 0) {
                                BackUser buc = backUserDao.getBackUserByUuid(currentCollectionUserId);//当前催收人
                                params.put("companyId", buc.getCompanyId());
                                params.put("grouplevel", buc.getGroupLevel());
                                Integer limitCount = mmanLoanCollectionRuleDao.findCompanyGoupOnline(params);//查询该公催收每日订单上线
                                MmanLoanCollectionPerson person = new MmanLoanCollectionPerson();
                                person.setId(buc.getId() + "");
                                Integer todayOrder = backUserDao.findTodayAssignedCount(person);//查询当前催收员今日派到手里的订单数(包括已完成的)
                                boolean S2topS1 = false;
                                if ("S1".equals(mmanLoanCollectionOrderOri.getS1Flag()) && "3".equals(buc.getGroupLevel()) && mmanLoanCollectionOrderOri.getOverdueDays() <= 10) {
                                    S2topS1 = true;

                                }
                                if (limitCount > todayOrder) {
                                    BackUser bu = backUserDao.getBackUserByUuid(mmanLoanCollectionOrderOri.getCurrentCollectionUserId());//原始催收人
                                    if (bu.getGroupLevel().equals(buc.getGroupLevel()) || S2topS1) {
                                        MmanLoanCollectionStatusChangeLog mmanLoanCollectionStatusChangeLog = new MmanLoanCollectionStatusChangeLog();
                                        //催收订单状态
                                        String beforeStatus = mmanLoanCollectionOrderOri.getStatus();
                                        if ("1".equals(mmanLoanCollectionOrder.getOutsideCompanyId())) {
                                            mmanLoanCollectionOrderOri.setStatus(BackConstant.XJX_COLLECTION_ORDER_STATE_WAIT);//公司内部转派待催收
                                            mmanLoanCollectionStatusChangeLog.setType(BackConstant.XJX_COLLECTION_STATUS_MOVE_TYPE_OTHER);//转单
                                        } else {
                                            mmanLoanCollectionOrderOri.setStatus(BackConstant.XJX_COLLECTION_ORDER_STATE_OUTSIDE);//委外
                                            mmanLoanCollectionStatusChangeLog.setType(BackConstant.XJX_COLLECTION_STATUS_MOVE_TYPE_OUTSIDE);//委外
                                        }
                                        //被转派人所在催收组
                                        //String currentOverdueLevel = mmanLoanCollectionOrderOri.getCurrentOverdueLevel();
                                        String currentOverdueLevel = buc.getGroupLevel();
                                        //转派后要将当前级催收状态初始化
                                        if (BackConstant.XJX_OVERDUE_LEVEL_S1.equals(currentOverdueLevel)) {
                                            mmanLoanCollectionOrderOri.setM1ApproveId(currentCollectionUserId);
                                            mmanLoanCollectionOrderOri.setM1OperateStatus(BackConstant.OFF);
                                        } else if (BackConstant.XJX_OVERDUE_LEVEL_S2.equals(currentOverdueLevel)) {
                                            mmanLoanCollectionOrderOri.setM2ApproveId(currentCollectionUserId);
                                            mmanLoanCollectionOrderOri.setM2OperateStatus(BackConstant.OFF);
                                        } else if (BackConstant.XJX_OVERDUE_LEVEL_M1_M2.equals(currentOverdueLevel)) {
                                            mmanLoanCollectionOrderOri.setM3ApproveId(currentCollectionUserId);
                                            mmanLoanCollectionOrderOri.setM3OperateStatus(BackConstant.OFF);
                                        } else if (BackConstant.XJX_OVERDUE_LEVEL_M2_M3.equals(currentOverdueLevel)) {
                                            mmanLoanCollectionOrderOri.setM4ApproveId(currentCollectionUserId);
                                            mmanLoanCollectionOrderOri.setM4OperateStatus(BackConstant.OFF);
                                        } else if (BackConstant.XJX_OVERDUE_LEVEL_M3P.equals(currentOverdueLevel)) {
                                            mmanLoanCollectionOrderOri.setM5ApproveId(currentCollectionUserId);
                                            mmanLoanCollectionOrderOri.setM5OperateStatus(BackConstant.OFF);
                                        } else {
                                            mmanLoanCollectionOrderOri.setM6ApproveId(currentCollectionUserId);
                                            mmanLoanCollectionOrderOri.setM6OperateStatus(BackConstant.OFF);
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
                                        if ("S1".equals(mmanLoanCollectionOrderOri.getS1Flag())) {
                                            mmanLoanCollectionStatusChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_S1);
                                        } else {
                                            mmanLoanCollectionStatusChangeLog.setCurrentCollectionOrderLevel(mmanLoanCollectionOrderOri.getCurrentOverdueLevel());
                                        }
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
                                        mmanLoanCollectionStatusChangeLog.setCurrentCollectionUserLevel(buc.getGroupLevel());
                                        mmanLoanCollectionStatusChangeLogDao.insert(mmanLoanCollectionStatusChangeLog);
                                        successCount++;
                                    } else {
                                        resutMap.put("sameGroup", "只能同组之间转派");
                                    }
                                } else {
                                    resutMap.put("todayOrder", "已超过催收员每日上线,");
                                    break;
                                }
                            } else {
                                resutMap.put("countSinge", "订单在当前催收员手上有转派过,");
                            }
                        } else {
                            resutMap.put("backUser", "自己不能转给自己");
                        }
                    } else {
                        resutMap.put("orderStatus", "催收成功的订单不能转派");
                    }
                        /*}else{
                            resutMap.put("currDateMesg","当天的订单不能转派,");
						}*/

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (successCount == orderIds.length) {
                result.setMsg("转派成功");
                result.setCode("0");
            } else {
                StringBuffer resultStr = new StringBuffer();
                if (resutMap.get("currDateMesg") != null) {
                    resultStr.append(resutMap.get("currDateMesg"));
                }
                if (resutMap.get("orderStatus") != null) {
                    resultStr.append(resutMap.get("orderStatus"));
                }
                if (resutMap.get("countSinge") != null) {
                    resultStr.append(resutMap.get("countSinge"));
                }
                if (resutMap.get("backUser") != null) {
                    resultStr.append(resutMap.get("backUser"));
                }
                if (resutMap.get("todayOrder") != null) {
                    resultStr.append(resutMap.get("todayOrder"));
                }
                if (resutMap.get("sameGroup") != null) {
                    resultStr.append(resutMap.get("sameGroup"));
                }
                result.setMsg("总单:" + orderIds.length + "，转派成功：" + successCount + "失败原因：" + resultStr.toString());
            }
        } else {
            result.setMsg("请选择需要转派的订单");
        }
        return result;
    }

    @Override
    @Transactional
    public JsonResult xjxWithholding(Map<String, String> params) {
        MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderById(params.get("id").toString());//原催收订单
        JsonResult reslut = new JsonResult("-1", "申请代扣款失败");
        try {
            if (order == null) {
                reslut.setMsg("该订单不存在");
                return reslut;
            }
            CollectionWithholdingRecord record = collectionWithholdingRecordDao.getLatestWithholdRecord(params.get("operationUserId").toString());
            if (BackConstant.XJX_COLLECTION_ORDER_STATE_PAYING.equals(order.getStatus()) || BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS.equals
                    (order.getStatus())) {
                reslut.setMsg("续期订单或催收完成订单不允许代扣！！");
                return reslut;
            }

            if (!BackConstant.SURPER_MANAGER_ROLE_ID.equals(Integer.valueOf(params.get("roleId"))) && record != null && new Date().getTime() <= getCreateTimePlus(record)) {
                reslut.setMsg("连续2次代扣时间不可小于2分钟，请稍后再试！");
                return reslut;
            }

            CreditLoanPay creditLoanPay = creditLoanPayService.get(order.getPayId());
            String payMonery = params.get("payMoney");//扣款金额
            BigDecimal koPayMonery;
            // 剩余应还利息
            BigDecimal remainAccrual = creditLoanPay.getRemainAccrual() == null ? BigDecimal.ZERO : creditLoanPay.getRemainAccrual();
            BigDecimal maxpayMonery = creditLoanPay.getReceivablePrinciple().add(creditLoanPay.getReceivableInterest()).add(remainAccrual);
            if (payMonery == null || "".equals(payMonery) || !CompareUtils.greaterThanZero(new BigDecimal(payMonery))) {
                koPayMonery = creditLoanPay.getReceivablePrinciple().add(creditLoanPay.getReceivableInterest()).add(remainAccrual);
            } else {
                koPayMonery = new BigDecimal(params.get("payMoney"));
            }
            if (!CompareUtils.greaterEquals(maxpayMonery, koPayMonery)) {
                reslut.setMsg("本次代扣金额不能大于" + maxpayMonery);
                return reslut;
            }
            if (BackConstant.WITHHELD_LOWER_LIMIT_AMOUNT.compareTo(koPayMonery) > 0) {
                reslut.setMsg("代扣金额最少不能低于50元！");
                return reslut;
            }

            HashMap<String, Object> dayMap = new HashMap<>();
            dayMap.put("orderId", params.get("id").toString());
            dayMap.put("currDate", DateUtil.getDateFormat(new Date(), "yyyy-MM-dd"));
            dayMap.put("status", 2);
            //查询当天定单代扣失败次数
            List<CollectionWithholdingRecord> list = collectionWithholdingRecordDao.findCurrDayWithhold(dayMap);
            int count = list.size();
            String currentUserRoleId = String.valueOf(params.get("roleId"));
            //超级管理员，高级经理 不受权限控制
            if (BackConstant.SURPER_MANAGER_ROLE_ID.toString().equals(currentUserRoleId) ||
                    BackConstant.SUPER_MANAGE_ROLE_ID.toString().equals(currentUserRoleId)) {
                count = 0;
            }
            if (count >= 2) {
                String msg = list.get(0).getRemark() == null ? "" : list.get(0).getRemark();
                reslut.setMsg(msg + " 代扣失败，您今日还有1次代扣机会 / 您今日已经无法代扣，请联系委外对接人代扣。");
                return reslut;
            }

            // 判断该笔订单是否有还款中待处理的数据(redis中是否存在对应key)
            String payId = order.getPayId();
            String overdueKeys = JedisDataClient.get(Constant.TYPE_OVERDUE_ + payId);
            String repayKeys = JedisDataClient.get(Constant.TYPE_REPAY_ + payId);
            if (StringUtils.isNotEmpty(overdueKeys) || StringUtils.isNotEmpty(repayKeys)) {
                reslut.setMsg("该用户正在还款处理中,请稍后查看");
                reslut.setCode("-1");
                return reslut;
            }

            long actualPayMonery = koPayMonery.multiply(new BigDecimal(100)).longValue();
            String uuid = IdGen.uuid();
            String sign = MD5coding.MD5(AESUtil.encrypt(order.getUserId() + order.getPayId() + actualPayMonery + uuid, PayContents.XJX_WITHHOLDING_NOTIFY_KEY));

            String withholdChannel = getWithholdChannel();
            // 根据渠道区分代扣请求发送地方
            if (BackConstant.CUISHOU_WITHHOLD_CHANNEL_PAYMENTCENTER.equals(withholdChannel)) {
                logger.info("订单: " + params.get("id").toString() + " 通过支付中心发起代扣...");
                sendMqMsg(params, order, actualPayMonery, uuid, sign);

                //插入一条代扣记录
                saveWithholdRecord(params, order, creditLoanPay, payMonery, uuid);
                reslut.setCode("0");
                reslut.setMsg("代扣请求发送成功，请5分钟后查看处理结果！");
            } else {
                logger.info("订单: " + params.get("id").toString() + " 通过请求现金侠后台发起代扣...");
                //2、发送请求
                String withholdPostUrl = PayContents.XJX_WITHHOLDING_NOTIFY_URL + "/" + order.getUserId() + "/" + order.getPayId() + "/" + actualPayMonery + "/" + uuid + "/" + sign;
                logger.error("现金侠代扣请求地址：" + withholdPostUrl);
                String xjxWithholdingStr = HttpUtil.getHttpMess(withholdPostUrl, "", "POST", "UTF-8");
                //3、解析响应结果封装到Java Bean
                if (xjxWithholdingStr != null && !"".equals(xjxWithholdingStr)) {
                    dealwithJsonResult(params, order, reslut, creditLoanPay, payMonery, uuid, xjxWithholdingStr);
                }
            }
        } catch (Exception e) {
            logger.error("代扣异常：", e);
            e.printStackTrace();
        }
        return reslut;
    }

    /**
     * 发送mq消息
     *
     * @param params
     * @param mmanLoanCollectionOrderOri
     * @param actualPayMonery
     * @param uuid
     * @param sign
     */
    private void sendMqMsg(Map<String, String> params, MmanLoanCollectionOrder mmanLoanCollectionOrderOri, long actualPayMonery, String uuid, String sign) {
        String json = getMessageString(mmanLoanCollectionOrderOri, actualPayMonery, uuid, sign);
        MqMessage msg = new MqMessage();
        // 区分大小额 向不同的队列发送消息
        String type = params.get("borrowingType");
        if (Constant.SMALL.equals(type)) {
            // 小额
            msg.setQueueName(Constant.CUISHOU_WITHHOLD_QUEUE);
            msg.setMessage(json);
            mqClient.sendMessage(msg);
        } else {
            // 大额
            msg.setQueueName(Constant.CUISHOU_WITHHOLD_QUEUE_BIG);
            msg.setMessage(json);
            mqClient.sendMessage(msg);
        }
    }

    /**
     * 插入代扣记录
     *
     * @param params
     * @param mmanLoanCollectionOrderOri
     * @param creditLoanPay
     * @param payMonery
     * @param uuid
     */
    private void saveWithholdRecord(Map<String, String> params, MmanLoanCollectionOrder mmanLoanCollectionOrderOri, CreditLoanPay creditLoanPay, String payMonery, String uuid) {
        CollectionWithholdingRecord WithholdingRecord = new CollectionWithholdingRecord();
        MmanUserInfo userInfo = mmanUserInfoDao.get(mmanLoanCollectionOrderOri.getUserId());
        if (userInfo != null) {
            WithholdingRecord.setLoanUserId(userInfo.getId());
            WithholdingRecord.setLoanUserName(userInfo.getRealname());
            WithholdingRecord.setLoanUserPhone(userInfo.getUserPhone());
        } else {
            logger.error("userInfo is null,userId = " + mmanLoanCollectionOrderOri.getUserId());
        }
        WithholdingRecord.setId(uuid);
        WithholdingRecord.setOrderId(mmanLoanCollectionOrderOri.getId());
        WithholdingRecord.setCreateDate(new Date());
        WithholdingRecord.setArrearsMoney(DecimalFormatUtil.df2Points.format(creditLoanPay.getReceivableMoney())); // 欠款金额
        WithholdingRecord.setHasalsoMoney(creditLoanPay.getRealMoney().toString()); // 当前已还金额
        WithholdingRecord.setOperationUserId(params.get("operationUserId"));
        WithholdingRecord.setDeductionsMoney(payMonery);  // 扣款金额
        WithholdingRecord.setOrderStatus(mmanLoanCollectionOrderOri.getStatus());  // 当前订单状态
        collectionWithholdingRecordDao.insert(WithholdingRecord);
    }

    /**
     * 处理代扣结果
     *
     * @param params
     * @param mmanLoanCollectionOrderOri
     * @param reslut
     * @param creditLoanPay
     * @param payMonery
     * @param uuid
     * @param xjxWithholdingStr
     */
    private void dealwithJsonResult(Map<String, String> params, MmanLoanCollectionOrder mmanLoanCollectionOrderOri, JsonResult reslut, CreditLoanPay creditLoanPay, String payMonery, String uuid, String xjxWithholdingStr) {
        JSONObject jos = new JSONObject().fromObject(xjxWithholdingStr);
        if (!"-100".equals(jos.get("code"))) {
            CollectionWithholdingRecord WithholdingRecord = new CollectionWithholdingRecord();
            MmanUserInfo userInfo = mmanUserInfoDao.get(mmanLoanCollectionOrderOri.getUserId());
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
            if ("0".equals(jos.get("code")) || "100".equals(jos.get("code"))) {
                //扣款成功要更新操作人，由于代扣成功时会有接口更新订单、借款、还款、详情等数据，所以这里千万不能更新mmanLoanCollectionOrderOri，因为这里的订单状态还是原始状态！！！
                MmanLoanCollectionOrder mmanLoanCollectionOrderNow = new MmanLoanCollectionOrder();
                mmanLoanCollectionOrderNow.setId(mmanLoanCollectionOrderOri.getId());
                mmanLoanCollectionOrderNow.setOperatorName(params.get("userName"));
                mmanLoanCollectionOrderNow.setS1Flag(mmanLoanCollectionOrderOri.getS1Flag());
                if (BackConstant.XJX_COLLECTION_ORDER_STATE_WAIT.equals(mmanLoanCollectionOrderOri.getStatus())) {
                    mmanLoanCollectionOrderNow.setStatus(BackConstant.XJX_COLLECTION_ORDER_STATE_ING);
                }
                mmanLoanCollectionOrderService.updateRecord(mmanLoanCollectionOrderNow);
                if ("0".equals(jos.get("code"))) {
                    WithholdingRecord.setStatus(1);
                } else {
                    WithholdingRecord.setStatus(0);
                }
                reslut.setMsg("申请代扣成功");
                reslut.setCode("0");
            } else {
                reslut.setMsg(jos.getString("msg"));
                WithholdingRecord.setStatus(2);
            }
            //添加一条扣款记录
            collectionWithholdingRecordDao.insert(WithholdingRecord);
        } else {
            reslut.setMsg("申请代扣失败,失败编码-100");
        }
        logger.error("现金侠代扣返回：" + xjxWithholdingStr);
    }

    /**
     * 处理要被发送往mq的JSONSTRING
     *
     * @param mmanLoanCollectionOrderOri
     * @param actualPayMonery
     * @param uuid
     * @param sign
     * @return
     */
    private String getMessageString(MmanLoanCollectionOrder mmanLoanCollectionOrderOri, long actualPayMonery, String uuid, String sign) {
        WithholdParam withhold = new WithholdParam();
        withhold.setMoney(BigDecimal.valueOf(actualPayMonery)); // 扣款金额
        String origianlPayId = mmanLoanCollectionOrderOri.getPayId();
        if (StringUtils.isNotEmpty(origianlPayId) && origianlPayId.contains(Constant.SEPARATOR_FOR_ORDER_SOURCE)) {
            String payId = origianlPayId.substring(0, origianlPayId.length() - 2);
            withhold.setRepaymentId(payId); // 还款id
        } else {
            withhold.setRepaymentId(mmanLoanCollectionOrderOri.getPayId()); // 还款id
        }
        withhold.setUuid(uuid); // uuid
        withhold.setUserId(mmanLoanCollectionOrderOri.getUserId()); // 用户id
        withhold.setSign(sign);
        return JSONUtil.beanToJson(withhold);
    }

    private String getWithholdChannel() throws Exception {
        String realKey = BackConstant.REDIS_KEY_PREFIX + WITHHOLD_CHANNEL_KEY;
        String withholdChannel = JedisDataClient.get(realKey);
        if (StringUtils.isBlank(withholdChannel)) {
            withholdChannel = channelSwitchingDao.getChannelValue("cuishou_withhold_channel").getChannelValue();
            JedisDataClient.set(realKey, withholdChannel, 60 * 60);
        }
        return withholdChannel;
    }

    private long getCreateTimePlus(CollectionWithholdingRecord record) {
        long createTime = 0;
        if (record == null) {
            createTime = System.currentTimeMillis();
        } else {
            createTime = record.getCreateDate().getTime();//最新一条代扣时间
        }
        return createTime + 2 * 60 * 1000; //新增2分钟
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
        for (InstallmentPayInfoVo installmentPayInfoVo : list) {
            InstallmentPayRecord installmentPayRecord = new InstallmentPayRecord();
            installmentPayRecord.setId(IdGen.uuid());
            installmentPayRecord.setRepayTime(installmentPayInfoVo.getRepayTime());
            installmentPayRecord.setDateNew(formatter.format(installmentPayInfoVo.getRepayTime()));
            installmentPayRecord.setCreateTime(new Date());
            installmentPayRecord.setRepayMoney(installmentPayInfoVo.getTotalRepay());
            installmentPayRecord.setLoanOrderId(mmanLoanCollectionOrderOri.getId());
            installmentPayRecord.setLoanUserName(mmanLoanCollectionOrderOri.getLoanUserName());
            installmentPayRecord.setLoanUserPhone(mmanLoanCollectionOrderOri.getLoanUserPhone());
            installmentPayRecord.setRepayBatches(installmentPayInfoVo.getInstallmentType() + "还款");
            if (installmentPayInfoVo.getServiceCharge() != null) {
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
     *
     * @param params
     */
    @Override
    public JsonResult fqWithholding(Map<String, String> params) {
        JsonResult result = new JsonResult("", "");
        InstallmentPayRecord installmentPayRecord = iInstallmentPayRecordDao.findInstallmentById(params.get("installmentId").toString());
        if (installmentPayRecord != null) {
            params.put("payMoney", String.valueOf(installmentPayRecord.getRepayMoney()));
            params.put("id", installmentPayRecord.getLoanOrderId());
            // 调用代扣接口
            result = this.xjxWithholding(params);
            result.setCode("0");
            if (result != null && "0".equals(result.getCode())) {
                iInstallmentPayRecordDao.updateInstallmentStatusById(params.get("installmentId").toString());
            }
        }
        return result;
    }

    @Override
    public void saveCollectionRecord(HashMap<String, Object> params, BackUser user) {
        String loanId = params.get("loanId") == null ? null : params.get("loanId").toString();
        MmanLoanCollectionOrder collectionOrder = mmanLoanCollectionOrderDao.getOrderByLoanId(loanId);
        if (BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS.equals(collectionOrder.getStatus())) {
            logger.info("催收完成订单不允许添加催收记录！订单id: " + collectionOrder.getLoanId());
            return;
        }
        String recordId = IdGen.uuid();
        params.put("recordId", recordId);

        //更新我的催收订单
        Date now = new Date();
        MmanLoanCollectionOrder mmanLoanCollectionOrder = new MmanLoanCollectionOrder();
        if (collectionOrder != null && !BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS.equals(collectionOrder.getStatus())) {
            if (params.get("repaymentTime") == null || params.get("repaymentTime") == "") {//不填承诺还款时间为催收中
                mmanLoanCollectionOrder.setStatus(BackConstant.XJX_COLLECTION_ORDER_STATE_ING);
                mmanLoanCollectionOrder.setPromiseRepaymentTime(null);
            } else {
                mmanLoanCollectionOrder.setStatus(BackConstant.XJX_COLLECTION_ORDER_STATE_PROMISE);
                mmanLoanCollectionOrder.setPromiseRepaymentTime(DateUtil.formatDate((String) params.get("repaymentTime"), "yyyy-MM-dd"));
            }
        }
        params.put("currentOverdueLevel", collectionOrder.getCurrentOverdueLevel());
        params.put("loanId", collectionOrder.getLoanId());
        mmanLoanCollectionOrder.setLastCollectionTime(now);
        mmanLoanCollectionOrder.setOperatorName(StringUtils.isNotBlank(user.getUserName()) ? user.getUserName() : "");
        //根据等级设置当前催收员某等级操作状态，1代表操作过催收单
        if (BackConstant.XJX_OVERDUE_LEVEL_S1.equals(user.getGroupLevel())) {
            mmanLoanCollectionOrder.setM1OperateStatus(BackConstant.ON);
        } else if (BackConstant.XJX_OVERDUE_LEVEL_S2.equals(user.getGroupLevel())) {
            if ("S1".equals(collectionOrder.getS1Flag()) && collectionOrder.getOverdueDays() <= 10) {
                mmanLoanCollectionOrder.setM1OperateStatus(BackConstant.ON);
            } else {
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
        mmanLoanCollectionOrder.setS1Flag(collectionOrder.getS1Flag());
        mmanLoanCollectionOrder.setUpdateDate(now);
        mmanLoanCollectionOrder.setId(collectionOrder.getId());
        mmanLoanCollectionOrderService.updateRecord(mmanLoanCollectionOrder);

        MmanLoanCollectionRecord record = setRecordParam(params, user, collectionOrder);
        String userRealId = (params.get("contactId") == null || "undefined".equals(params.get("contactId").toString())) ? null : params.get("contactId").toString();
        String collectionRecordId = params.get("collectionRecordId") == null ? null : params.get("collectionRecordId").toString();
        String selectCallRecordFlag = (params.get("selectCallRecordFlag") == null || "undefined".equals(params.get("selectCallRecordFlag").toString())) ? null : params.get("selectCallRecordFlag").toString();
        if (StringUtils.isNotEmpty(userRealId)) {
            // 查询借款人联系人信息
            MmanUserRela userRela = userRelaService.getUserRealByUserId(userRealId);
            record.setRelation(getcurrentUserRelationWithLoanUser(userRela.getRelaKey(), userRela.getContactsKey()));
            record.setContactName(userRela.getInfoName());
            record.setContactPhone(userRela.getInfoValue());
        } else if (StringUtils.isNotEmpty(collectionRecordId)) {
            // 查询催收记录
            MmanLoanCollectionRecord loanCollectionRecord = mmanLoanCollectionRecordDao.getOneCollectionRecordById(collectionRecordId);
            record.setRelation(loanCollectionRecord.getRelation());
            record.setContactName(loanCollectionRecord.getContactName());
            record.setContactPhone(loanCollectionRecord.getContactPhone());

        }else if(selectCallRecordFlag !=null ){
//            record.setRelation(params.get("callUserName"));
            record.setContactName((String) params.get("callUserName"));
            record.setContactPhone((String) params.get("phoneNumber"));
        } else {
            String isCloseRelation = params.get("isCloseRelation") == null ? null : params.get("isCloseRelation").toString();
            MmanUserInfo userInfo = mmanUserInfoDao.getUserInfoById(params.get("userId") == null ? null : params.get("userId").toString());
            if (StringUtils.isNotEmpty(isCloseRelation)) {
                record.setContactType("1");
                if ("1".equals(isCloseRelation)) {
                    record.setRelation(getcurrentUserRelationWithLoanUser(userInfo.getFristContactRelation().toString(), "1"));
                    record.setContactName(userInfo.getFirstContactName());
                    record.setContactPhone(userInfo.getFirstContactPhone());
                } else if ("2".equals(isCloseRelation)) {
                    record.setRelation(getcurrentUserRelationWithLoanUser(userInfo.getSecondContactRelation().toString(), "1"));
                    record.setContactName(userInfo.getSecondContactName());
                    record.setContactPhone(userInfo.getSecondContactPhone());
                }
            } else {
                record.setRelation("本人");
                record.setContactName(userInfo.getRealname());
                record.setContactPhone(userInfo.getUserName());
            }

        }
        mmanLoanCollectionRecordDao.insert(record);

        String repaymentTime = params.get("repaymentTime") == null ? null : params.get("repaymentTime").toString();
        MmanLoanCollectionOrder order = new MmanLoanCollectionOrder();
        String orderId = params.get("orderId") == null ? null : params.get("orderId").toString();
        order.setId(orderId);
        if (StringUtils.isNotEmpty(repaymentTime)) {
            // 更新承诺还款时间
            order.setPromiseRepaymentTime(DateUtil.getDateTimeFormat(repaymentTime, "yyyy-MM-dd"));
        } else {
            order.setPromiseRepaymentTime(collectionOrder.getPromiseRepaymentTime());
        }
        // 更新最新催收时间
        order.setLastCollectionTime(new Date());
        mmanLoanCollectionOrderDao.updateCollectionOrder(order);

        // 保存催收建议
        CollectionAdvice advice = new CollectionAdvice();
        String fengKongIds = params.get("fengKongIds") == null ? null : params.get("fengKongIds").toString();
        advice.setLoanId(loanId);
        advice.setOrderId(orderId);
        advice.setId(IdGen.uuid());
        advice.setCreateDate(new Date());
        advice.setBackUserId(user.getId());
        advice.setCollectionRecordId(recordId);
        advice.setFengkongIds(fengKongIds);
        advice.setLoanUserName(collectionOrder.getLoanUserName());
        advice.setStatus(params.get("advice").toString());
        advice.setLoanUserPhone(collectionOrder.getLoanUserPhone());
        String fengKongLables = getLables(fengKongIds);
        advice.setFkLabels(fengKongLables);
        advice.setPayId(collectionOrder.getPayId());
        advice.setUserId(collectionOrder.getUserId());
        advice.setUserName(user.getUserName());
        fengKongService.saveAdvice(advice);
    }

    private String getLables(String fengKongIds) {
        if (StringUtils.isEmpty(fengKongIds)) {
            return null;
        }
        String[] ids = fengKongIds.split(",");
        StringBuilder sb = new StringBuilder(16);
        Map<String,Object> fengKongLableMap = fengKongService.getFengKongLableMap();
        for (String fengKongId : ids) {
            sb.append(fengKongLableMap.get(fengKongId)).append(",");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    // 获取当前联系人与借款人的关系
    private String getcurrentUserRelationWithLoanUser(String relaKey, String contactsKey) {
        Map<String, String> closeRelationMap = new HashMap<>();
        Map<String, String> otherRelationMap = new HashMap<>();
        String relation = null;
        try {
            // 直系联系人与借款人关系list
            Map<String, String> closeRelationList = JedisDataClient.getMap(BackConstant.REDIS_KEY_PREFIX, USER_CLOSE_RELATION_KEY);
            // 其他联系人与借款人关系list
            Map<String, String> otherRelationList = JedisDataClient.getMap(BackConstant.REDIS_KEY_PREFIX, USER_OTHER_RELATION_KEY);

            if (MapUtils.isEmpty(closeRelationList)) {
                // 借款人直系联系人与借款的关系
                List<SysDict> dicts = dictService.findDictByType(XJX_CONTACTS_TYPE_IMMEDIATE);
                for (SysDict dict : dicts) {
                    closeRelationMap.put(dict.getValue(), dict.getLabel());
                }
                JedisDataClient.setMap(BackConstant.REDIS_KEY_PREFIX + USER_CLOSE_RELATION_KEY, closeRelationMap);
            }

            if (otherRelationList == null || otherRelationList.size() <= 0) {
                // 借款人其他联系人与借款的关系
                List<SysDict> dicts = dictService.findDictByType(XJX_CONTACTS_TYPE_OTHER);

                for (SysDict dict : dicts) {
                    otherRelationMap.put(dict.getValue(), dict.getLabel());
                }
                JedisDataClient.setMap(BackConstant.REDIS_KEY_PREFIX + USER_OTHER_RELATION_KEY, otherRelationMap);
            }

            if (StringUtils.isNotEmpty(contactsKey)) {
                if (contactsKey.equals("1")) {
                    relation = closeRelationMap.get(relaKey);
                } else {
                    relation = otherRelationMap.get(relaKey);
                }
            }
        } catch (Exception e) {

        }
        return relation;
    }

    // 设置共用参数
    private MmanLoanCollectionRecord setRecordParam(HashMap<String, Object> params, BackUser user, MmanLoanCollectionOrder order) {
        MmanLoanCollectionRecord record = new MmanLoanCollectionRecord();
        record.setId(params.get("recordId").toString());
        record.setOrderId(params.get("orderId") == null ? null : params.get("orderId").toString());
        record.setCollectionDate(new Date());
        record.setContent(params.get("content") == null ? null : params.get("content").toString());
        record.setCollectionType(params.get("collectionMode").toString());
        record.setCommunicationStatus(params.get("communication") == null ? null : params.get("communication").toString());
        record.setCreateDate(new Date());
        record.setUpdateDate(new Date());
        record.setUserId(params.get("userId") == null ? null : params.get("userId").toString());
        record.setCurrentOverdueLevel(order.getCurrentOverdueLevel());
        record.setCollectionGroup(order.getCurrentOverdueLevel());
        record.setOrderState(params.get("orderStatus") == null ? null : params.get("orderStatus").toString());
        record.setCompanyTitle(user.getCompanyTitle());
        record.setLoanId(params.get("loanId") == null ? null : params.get("loanId").toString());
        record.setContactType("1");
        record.setCollectionId(user.getUuid());
        record.setContent(params.get("collectionContent") == null ? null : params.get("collectionContent").toString());
        record.setPromiseRepaymentTime(params.get("repaymentTime") == null ? null : DateUtil.formatDate(params.get("repaymentTime").toString(), "yyyy-MM-dd"));
        return record;
    }
}
