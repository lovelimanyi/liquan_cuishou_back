package com.info.back.service;

import com.info.back.dao.*;
import com.info.back.utils.BackConstant;
import com.info.back.utils.IdGen;
import com.info.constant.Constant;
import com.info.web.pojo.*;
import com.info.web.synchronization.dao.IDataDao;
import com.info.web.synchronization.syncUtils;
import com.info.web.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/12/29 0029上午 11:54
 */
@Service
public class DataTransferToCuiShouService {

    private static Logger logger = Logger.getLogger(DataTransferToCuiShouService.class);
    private static final String TXLC_MERCHANT_NUMBER = "1004";
    //  易秒借款
    private static final String YMJK_MERCHANT_NUMBER = "1005";



    @Autowired
    IBackUserDao backUserDao;
    @Autowired
    private IDataDao dataDao;
    @Autowired
    private ILocalDataDao localDataDao;
    @Autowired
    private IMmanLoanCollectionOrderDao manLoanCollectionOrderDao;
    @Autowired
    private ICreditLoanPayService payService;
    @Autowired
    private IMmanUserLoanDao  loanDao;
    @Autowired
    private IMmanLoanCollectionOrderService orderService;
    @Autowired
    private IMmanLoanCollectionStatusChangeLogDao statusChangeLogDao;
    @Autowired
    private IMmanUserInfoService infoService;

    public String dataTransferToCuiShou2(String backUserName,String payIds,String disTime) {
        BackUser collectionUser = backUserDao.getUserByName(backUserName);
        for (String payId : payIds.split(",")) {

            HashMap<String,String> map = new HashMap<String,String>();
            map.put("ID", payId);//还款id
            HashMap<String,Object> repayment = this.dataDao.getAssetRepayment(map);
            CreditLoanPay pay = payService.get(payId);
            if (null == pay){
                saveCreditLoanPay(repayment);
            }
             pay = payService.get(payId);
            MmanLoanCollectionOrder order = new MmanLoanCollectionOrder();
            order.setId(IdGen.uuid());
            order.setLoanId(pay.getLoanId());
            MmanUserLoan loan = loanDao.get(pay.getLoanId());
            MmanUserInfo userInfo =infoService.getUserInfoById(loan.getUserId());

            order.setPayId(payId);
            order.setOrderId(String.valueOf(loan.getLoanPyId()));
            order.setUserId(loan.getUserId());//借款用户id
            order.setOverdueDays(1);
            order.setStatus("1");//订单状态 默认为“待催收”
            order.setRealMoney(new BigDecimal(Integer.parseInt(String.valueOf(repayment.get("repaymented_amount")))).divide(new BigDecimal(100)));//已还金额
            order.setDispatchName("系统");
            order.setDispatchTime(DateUtil.getDateTimeFormat(String.valueOf(disTime), "yyyy-MM-dd HH:mm:ss"));
            order.setCurrentOverdueLevel(BackConstant.XJX_OVERDUE_LEVEL_S1);//逾期等级 默认为S1
            order.setCreateDate(DateUtil.getDateTimeFormat(String.valueOf(disTime), "yyyy-MM-dd HH:mm:ss"));
            order.setUpdateDate(DateUtil.getDateTimeFormat(String.valueOf(disTime), "yyyy-MM-dd HH:mm:ss"));
            order.setOperatorName("系统");
            order.setRemark("系统派单");
            order.setLoanUserName(userInfo.getRealname());
            order.setLoanUserPhone(userInfo.getUserPhone());
            order.setIdNumber(userInfo.getIdNumber());
            order.setCurrentCollectionUserId(collectionUser.getUuid());
            order.setOutsideCompanyId(collectionUser.getCompanyId());
            manLoanCollectionOrderDao.insertCollectionOrder(order);

            MmanLoanCollectionStatusChangeLog changeLog = new MmanLoanCollectionStatusChangeLog();
            changeLog.setId(IdGen.uuid());
            changeLog.setLoanCollectionOrderId(loan.getLoanPyId());
            changeLog.setAfterStatus(BackConstant.XJX_COLLECTION_ORDER_STATE_WAIT);//订单状态 ：0:待催收 1:催收中  4:还款完成
            changeLog.setType("1");// 操作类型 1:入催  2:逾期升级  3:转单  4：还款完成
            changeLog.setCreateDate(DateUtil.getDateTimeFormat(String.valueOf(disTime), "yyyy-MM-dd HH:mm:ss"));
            changeLog.setOperatorName("系统");
            changeLog.setRemark("系统派单，催收员:" + collectionUser.getUserName());
            changeLog.setCompanyId(collectionUser.getCompanyId());
            changeLog.setCurrentCollectionUserId(collectionUser.getUuid());
            changeLog.setCurrentCollectionUserLevel(collectionUser.getGroupLevel());
            changeLog.setCurrentCollectionOrderLevel(collectionUser.getGroupLevel());
            statusChangeLogDao.insert(changeLog);


            List<HashMap<String, Object>> repaymentDetailList = this.dataDao.getAssetRepaymentDetail(map);

            if (CollectionUtils.isNotEmpty(repaymentDetailList)){
                syncUtils.saveFirstPayDetail(localDataDao,repayment,payId, repaymentDetailList);
            }

        }




        return "";
    }
    private void saveCreditLoanPay(HashMap<String,Object> repaymentMap){
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
        }catch (Exception e){
            e.printStackTrace();
        }

    }











    public String dataTransferToCuiShou(String backUserName,String payIds,String disTime) {


        BackUser collectionUser = backUserDao.getUserByName(backUserName);
        if (collectionUser == null) {
            return "该催收员不存在或该催收员已被禁用!";
        }
        for (String payId : payIds.split(",")) {
            try {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("ID", payId);//还款id
                //还款信息--app端
                HashMap<String, Object> repayment = dataDao.getAssetRepayment(map);
                logger.info("sync-borrowOrders:" + repayment);
                String loanId = String.valueOf(repayment.get("asset_order_id"));//借款id
                String userId = String.valueOf(repayment.get("user_id"));        //用户id
                map.put("ORDER_ID", loanId);
                map.put("USER_ID", userId);
                String repaymentMoney = String.valueOf(repayment.get("repayment_amount"));
                String repaymentedMoney = String.valueOf(repayment.get("repaymented_amount"));
                logger.info("sync-repaymentMoney:" + payId + ":" + repaymentMoney);
                logger.info("sync-repaymentedMoney:" + payId + ":" + repaymentedMoney);
                if (null != repayment) {
                    HashMap<String, Object> borrowOrder = null;					//借款信息--app端
                    List<HashMap<String, Object>> repaymentDetailList = null;	//还款详情信息--app端
                    Map<String, Object> userInfo = null;					//用户信息--app端
                    Map<String, Object> cardInfo = null;					//银行卡--app端
                    List<Map<String, Object>> userContactsList = null;		//用户联系人--app端

                    borrowOrder = this.dataDao.getAssetBorrowOrder(map);
                    logger.info("sync-borrowOrder:"+borrowOrder);
                    repaymentDetailList = this.dataDao.getAssetRepaymentDetail(map);
                    logger.info("sync-repaymentDetailList:"+repaymentDetailList);
                    logger.info("开始:"+borrowOrder);
//                    try{
//                        Map<String, String> map2 = new HashMap();
//                        map2.put("userId",borrowOrder.get("user_id").toString());
//                        map2.put("merchantNumber","cjxjx");//默认小额推逾期，商户号都是cjxjx；如之后有其他商户渠道，则需修改
//                        String returnInfo = HttpUtil.getInstance().doPost2(PayContents.XJX_GET_USERINFOS, JSON.toJSONString(map2));
////							loger.error("调用vip查询用户信息："+returnInfo);
//                        Map<String, Object> o = (Map<String, Object>) JSONObject.parse(returnInfo);
//                        if(o != null && "00".equals(String.valueOf(o.get("code")))){
//                            Map<String,Object> data = (Map<String, Object>) o.get("data");
//                            userInfo = (Map<String, Object>) data.get("user");
//                            cardInfo = ((List<Map<String, Object>>) data.get("userCardInfoList")).get(0);
//                            userContactsList = (List<Map<String, Object>>) data.get("userContactsList");
//                        }
//                    }catch (Exception e){
//                        logger.error("调用cashman获取用户信息出错：" + e);
//                        e.printStackTrace();
//                        return "fail";
//                    }
                    logger.info("loanId true:"+loanId);
                    if (null != userInfo && null != borrowOrder&& null != cardInfo&& null != repaymentDetailList) {
                        //保存用户借款表
                        logger.info("保存用户借款表 start:");
                        saveMmanUserLoan(borrowOrder,repayment,userInfo,disTime);
                        logger.info("saveMmanUserLoan end:");
                        //保存还款表
                        saveCreditLoanPay(repayment,disTime);
                        logger.info("保存还款表");
                        //保存还款详情表
                        saveFirstPayDetail2(localDataDao,repayment,payId, repaymentDetailList,collectionUser,disTime);
                        logger.info("保存还款详情表");
                        //保存用户信息表--联系人表--银行卡
                        syncUtils.saveUserInfo(localDataDao,payId,userId,userInfo,userContactsList,cardInfo);
                    }
                    MmanLoanCollectionOrder order = new MmanLoanCollectionOrder();
                    order.setId(IdGen.uuid());
                    order.setLoanId(loanId);
                    order.setPayId(payId);
                    order.setOrderId(String.valueOf(borrowOrder.get("out_trade_no")));
                    order.setUserId(userId);//借款用户id
                    order.setOverdueDays(Integer.valueOf(repayment.get("late_day").toString()));
                    order.setStatus("1");//订单状态 默认为“待催收”
                    order.setRealMoney(new BigDecimal(Integer.parseInt(String.valueOf(repayment.get("repaymented_amount")))).divide(new BigDecimal(100)));//已还金额
                    order.setDispatchName("系统");
                    order.setDispatchTime(DateUtil.getDateTimeFormat(String.valueOf(disTime), "yyyy-MM-dd HH:mm:ss"));
                    order.setCurrentOverdueLevel(BackConstant.XJX_OVERDUE_LEVEL_S1);//逾期等级 默认为S1
                    order.setCreateDate(DateUtil.getDateTimeFormat(String.valueOf(disTime), "yyyy-MM-dd HH:mm:ss"));
                    order.setUpdateDate(DateUtil.getDateTimeFormat(String.valueOf(disTime), "yyyy-MM-dd HH:mm:ss"));
                    order.setOperatorName("系统");
                    order.setRemark("系统派单");
                    order.setLoanUserName(userInfo.get("realname").toString());
                    order.setLoanUserPhone(userInfo.get("userPhone").toString());
                    order.setIdNumber(userInfo.get("idNumber").toString());
                    order.setCurrentCollectionUserId(collectionUser.getUuid());
                    order.setOutsideCompanyId(collectionUser.getCompanyId());
                    manLoanCollectionOrderDao.insertCollectionOrder(order);

                    MmanLoanCollectionStatusChangeLog changeLog = new MmanLoanCollectionStatusChangeLog();
                    changeLog.setId(IdGen.uuid());
                    changeLog.setLoanCollectionOrderId(String.valueOf(borrowOrder.get("out_trade_no")));
                    changeLog.setAfterStatus(BackConstant.XJX_COLLECTION_ORDER_STATE_WAIT);//订单状态 ：0:待催收 1:催收中  4:还款完成
                    changeLog.setType("1");// 操作类型 1:入催  2:逾期升级  3:转单  4：还款完成
                    changeLog.setCreateDate(DateUtil.getDateTimeFormat(String.valueOf(disTime), "yyyy-MM-dd HH:mm:ss"));
                    changeLog.setOperatorName("系统");
                    changeLog.setRemark("系统派单，催收员:" + collectionUser.getUserName());
                    changeLog.setCompanyId(collectionUser.getCompanyId());
                    changeLog.setCurrentCollectionUserId(collectionUser.getUuid());
                    changeLog.setCurrentCollectionUserLevel(collectionUser.getGroupLevel());
                    changeLog.setCurrentCollectionOrderLevel(collectionUser.getGroupLevel());
                    statusChangeLogDao.insert(changeLog);

//
//                    if (repaymentDetailList != null && repaymentDetailList.size()>0){
//                        logger.info("未逾期部分还款:"+loanId);
//                        syncUtils.updateMmanLoanCollectionOrder(localDataDao,loanId,repayment,Constant.STATUS_OVERDUE_ONE);
//                    }
                }

            }catch (Exception E){

            }



        }
        return "success";
    }

    public  void saveMmanUserLoan(HashMap<String,Object> borrowOrder,HashMap<String,Object> repaymentMap,Map<String,Object> userInfo,String disTime){
        logger.info("sync-borrowOrder:"+borrowOrder);
        MmanUserLoan mmanUserLoan = new MmanUserLoan();
        mmanUserLoan.setId(String.valueOf(borrowOrder.get("id")));
        mmanUserLoan.setUserId(String.valueOf(borrowOrder.get("user_id")));
        mmanUserLoan.setLoanPyId(String.valueOf(borrowOrder.get("out_trade_no")));//第三方订单号
        mmanUserLoan.setLoanMoney(new BigDecimal(Integer.parseInt(String.valueOf(borrowOrder.get("money_amount")))/100.00));
        mmanUserLoan.setLoanRate(String.valueOf(borrowOrder.get("apr")));
        if (TXLC_MERCHANT_NUMBER.equals(String.valueOf(borrowOrder.get("merchant_number"))) || YMJK_MERCHANT_NUMBER.equals(String.valueOf(borrowOrder.get("merchant_number")))){
            mmanUserLoan.setPaidMoney(new BigDecimal(0));
        }else {
            mmanUserLoan.setPaidMoney(new BigDecimal(Integer.parseInt(String.valueOf(borrowOrder.get("paid_money")))/100.00));//服务费+本金
        }


        mmanUserLoan.setLoanPenalty(new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("plan_late_fee")))/100.00));
        mmanUserLoan.setServiceCharge(new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("repayment_interest")))/100.00));//服务费
        mmanUserLoan.setLoanPenaltyRate(String.valueOf(repaymentMap.get("late_fee_apr")));
        mmanUserLoan.setLoanEndTime(DateUtil.getDateTimeFormat(String.valueOf(repaymentMap.get("repayment_time")), "yyyy-MM-dd HH:mm:ss"));
        mmanUserLoan.setLoanStartTime(DateUtil.getDateTimeFormat(String.valueOf(repaymentMap.get("credit_repayment_time")), "yyyy-MM-dd HH:mm:ss"));
        mmanUserLoan.setUpdateTime(DateUtil.getDateTimeFormat(String.valueOf(disTime), "yyyy-MM-dd HH:mm:ss"));
        mmanUserLoan.setLoanStatus(Constant.STATUS_OVERDUE_FOUR);//4：逾期
        mmanUserLoan.setCreateTime(DateUtil.getDateTimeFormat(String.valueOf(disTime), "yyyy-MM-dd HH:mm:ss"));
        mmanUserLoan.setDelFlag("0");//0正常1：删除
        mmanUserLoan.setCustomerType(Integer.valueOf(userInfo.get("customer_type") == null ? "0" : userInfo.get("customer_type").toString()));   // 标识新老用户 0 新用户  1 老用户
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
    private void saveCreditLoanPay(HashMap<String,Object> repaymentMap,String disTime){
        logger.info("start-saveCreditLoanPay:"+String.valueOf(repaymentMap.get("id")));
        try{
            CreditLoanPay creditLoanPay = new CreditLoanPay();
            creditLoanPay.setId(String.valueOf(repaymentMap.get("id")));
            creditLoanPay.setLoanId(String.valueOf(repaymentMap.get("asset_order_id")));
            creditLoanPay.setCreateDate(DateUtil.getDateTimeFormat(String.valueOf(disTime), "yyyy-MM-dd HH:mm:ss"));
            creditLoanPay.setReceivableStartdate(DateUtil.getDateTimeFormat(String.valueOf(repaymentMap.get("credit_repayment_time")), "yyyy-MM-dd HH:mm:ss"));
            creditLoanPay.setReceivableDate(DateUtil.getDateTimeFormat(String.valueOf(repaymentMap.get("repayment_time")), "yyyy-MM-dd HH:mm:ss"));//应还时间
            creditLoanPay.setReceivableMoney(new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("repayment_amount")))/100.00));//应还金额
            creditLoanPay.setRealMoney(new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("repaymented_amount")))/100.00));//实收(本金+服务费)
            creditLoanPay.setStatus(syncUtils.getPayStatus(String.valueOf(repaymentMap.get("status")))); //还款状态
            creditLoanPay.setUpdateDate(DateUtil.getDateTimeFormat(String.valueOf(disTime), "yyyy-MM-dd HH:mm:ss"));
            creditLoanPay = syncUtils.operaRealPenlty(repaymentMap,creditLoanPay);
            this.localDataDao.saveCreditLoanPay(creditLoanPay);
            logger.info("end-saveCreditLoanPay:"+String.valueOf(repaymentMap.get("id")));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void saveFirstPayDetail2(ILocalDataDao localDataDao,HashMap<String, Object> repayment,String payId,List<HashMap<String,Object>> repaymentDetailList,BackUser collectionUser,String disTime){
        logger.error("部分还款start-saveCreditLoanPayDetail-payId =" + payId);
        logger.error("repaymentDetailList="+repaymentDetailList);

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
                creditLoanPayDetail.setUpdateDate(DateUtil.getDateTimeFormat(String.valueOf(repayDetail.get("created_at")), "yyyy-MM-dd HH:mm:ss"));
                creditLoanPayDetail.setReturnType(String.valueOf(repayDetail.get("repayment_type")));
                creditLoanPayDetail.setRemark(String.valueOf(repayDetail.get("remark")));
                creditLoanPayDetail.setCurrentCollectionUserId(collectionUser.getUuid());
                creditLoanPayDetail = syncUtils.operaRealPenltyDetail(repayment, repayDetail, payId,creditLoanPayDetail,localDataDao);
                localDataDao.saveCreditLoanPayDetail(creditLoanPayDetail);
                logger.error("部分还款end-saveCreditLoanPayDetail-payId =" + payId);
                logger.error("endDate-saveCreditLoanPayDetail:"+DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
                logger.error("creditLoanPayDetail="+creditLoanPayDetail);
            }
        }
    }
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
}
