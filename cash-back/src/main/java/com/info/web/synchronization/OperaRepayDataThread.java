package com.info.web.synchronization;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.info.back.dao.ILocalDataDao;
import com.info.back.utils.BackConstant;
import com.info.back.utils.IdGen;
import com.info.constant.Constant;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.CreditLoanPay;
import com.info.web.pojo.CreditLoanPayDetail;
import com.info.web.pojo.CreditLoanPaySum;
import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.pojo.MmanLoanCollectionStatusChangeLog;
import com.info.web.pojo.MmanUserLoan;
import com.info.web.synchronization.dao.IDataDao;
import com.info.web.util.DateUtil;
/**
 * 还款
 * @author Administrator
 *
 */
public class OperaRepayDataThread implements Runnable {

    private static Logger loger = Logger.getLogger(OperaRepayDataThread.class);
    private String payId;
    private IDataDao dataDao;
    private ILocalDataDao localDataDao;

    public OperaRepayDataThread(String payId, IDataDao dataDao,
                                ILocalDataDao localDataDao) {
        this.payId = payId;
        this.dataDao = dataDao;
        this.localDataDao = localDataDao;
    }

    public OperaRepayDataThread() {
    }

    @Override
    public void run() {
        if(StringUtils.isNotBlank(payId)){
            loger.error("sync-OperaRepayDataThread:"+payId);
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("ID", payId);//还款id
            //获取app端还款信息
            HashMap<String,Object> repayment = this.dataDao.getAssetRepayment(map);
            loger.error("repayment:"+repayment);
            //获取app端还款详情信息
            List<HashMap<String, Object>> repaymentDetailList = this.dataDao.getAssetRepaymentDetail(map);
            int repaymentMoney = Integer.parseInt(String.valueOf(repayment.get("repayment_amount")));
            int repaymentedMoney = Integer.parseInt(String.valueOf(repayment.get("repaymented_amount")));
            loger.error("repaymentMoney===="+repaymentMoney+"repaymentedMoney==="+repaymentedMoney);
            //只有应还金额=已还金额 且 还款详情列表不为空才执行逻辑
            if(null!=repayment && (repaymentMoney==repaymentedMoney)&& repaymentDetailList.size()>0) {
                try {
                    String loanId = String.valueOf(repayment.get("asset_order_id"));//借款id
                    map.put("ORDER_ID", loanId);//还款id
                    HashMap<String, Object> borrowOrder = null;

                    if (!checkLoanStatus(loanId, payId)) {
                        //获取app端借款信息
                        borrowOrder = this.dataDao.getAssetBorrowOrder(map);

                        if (null != borrowOrder && null != repaymentDetailList) {
                            //更新借款表
                            syncUtils.updateMmanUserLoan(localDataDao, loanId, repayment, Constant.STATUS_OVERDUE_FIVE);
                            //更新还款表
                            syncUtils.updateCreditLoanPay(localDataDao, payId, repayment);
                            //保存还款详情表
                            syncUtils.saveCreditLoanPayDetail(localDataDao, repayment, payId, repaymentDetailList);
                            //更新订单表-保存催收流转日志
                            loger.error("start-updateOrderAndLog-loanId:" + loanId);
                            loger.error("startDate-updateOrderAndLog" + DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
                            updateOrderAndLog(loanId, repayment);
                            loger.error("end-updateOrderAndLog-loanId:" + loanId);

                        }
                        RedisUtil.delRedisKey(Constant.TYPE_REPAY_ + payId);
                    }
                } catch (Exception e0) {
                    e0.printStackTrace();
                }
            }
        }
    }

    private void updateOrderAndLog(String loanId,HashMap<String, Object> repaymentMap){
        MmanLoanCollectionOrder order = null;
        //更新订单
        order = syncUtils.updateMmanLoanCollectionOrder(localDataDao,loanId,repaymentMap,Constant.STATUS_OVERDUE_FOUR);

        BackUser backUser = null;
        if(null!=order){
            String backUserId = order.getCurrentCollectionUserId();
            HashMap<String,Object> umap = new HashMap<String,Object>();
            umap.put("ID", backUserId);
            backUser = this.localDataDao.selectBackUser(umap);
        }
        if(null!=backUser){
            loger.error("start-saveLoanChangeLog-loanId"+loanId);
            loger.error("startDate-saveLoanChangeLog-loanId"+DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
            MmanLoanCollectionStatusChangeLog loanChangeLog = new MmanLoanCollectionStatusChangeLog();
            loanChangeLog.setId(IdGen.uuid());
            loanChangeLog.setLoanCollectionOrderId(order.getOrderId());
            loanChangeLog.setBeforeStatus(order.getStatus());
            loanChangeLog.setAfterStatus(Constant.STATUS_OVERDUE_FOUR);
            loanChangeLog.setType(Constant.STATUS_OVERDUE_FIVE);//催收完成
            loanChangeLog.setOperatorName(Constant.OPERATOR_NAME);
            loanChangeLog.setRemark(Constant.PAY_MENT_SUCCESS+backUser.getUserName());
            loanChangeLog.setCompanyId(backUser.getCompanyId());
            loanChangeLog.setCurrentCollectionUserId(backUser.getUuid());
            loanChangeLog.setCurrentCollectionUserLevel(backUser.getGroupLevel());
            if(StringUtils.isNotBlank(order.getS1Flag())){
                loanChangeLog.setCurrentCollectionOrderLevel(BackConstant.XJX_OVERDUE_LEVEL_S1);
            }else{
                loanChangeLog.setCurrentCollectionOrderLevel(order.getCurrentOverdueLevel());
            }
            loanChangeLog.setCreateDate(new Date());
            this.localDataDao.saveLoanChangeLog(loanChangeLog);
            loger.error("end-saveLoanChangeLog-loanId"+loanId);
            loger.error("endDate-saveLoanChangeLog-loanId"+DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
        }

    }

    /**
     * 验证是否重已经还款完成
     *
     * @return true 已经还款完成  false未还款完成
     */
    public boolean checkLoanStatus(String id,String payId){
        if(StringUtils.isNotBlank(id)){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("ID", id);
            String userLoan = null;
            userLoan = this.localDataDao.checkLoanStatus(map);
            if (userLoan.equals("5")){
                //如果还款完成，删除redis中的TYPE_REPAY
                RedisUtil.delRedisKey(Constant.TYPE_REPAY_+payId);
                return true;
            }else if(userLoan == null){
                //如果没有该订单，不做处理
                return true;
            }else {
                return  false;
            }
        }
        return true;
    }

}
