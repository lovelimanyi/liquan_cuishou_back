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
            String repaymentMoney =String.valueOf(repayment.get("repayment_amount"));
            String repaymentedMoney = String.valueOf(repayment.get("repaymented_amount"));
            //只有应还金额=已还金额 且 还款详情列表不为空才执行逻辑
            if(null!=repayment && (repaymentMoney.equals(repaymentedMoney))&& repaymentDetailList.size()>0) {
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
                            syncUtils.updateOrderAndLog(loanId,repayment,localDataDao,payId);
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
            if (userLoan == null){
                // 如果没有该订单，不做处理
                RedisUtil.delRedisKey(Constant.TYPE_REPAY_+payId);
                return true;
            }else if(userLoan.equals("5")){
                // 如果还款完成，删除redis中的TYPE_REPAY
                RedisUtil.delRedisKey(Constant.TYPE_REPAY_+payId);
                return true;
            }else {
                return  false;
            }
        }
        return true;
    }

}
