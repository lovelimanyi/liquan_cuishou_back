package com.info.web.synchronization;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.info.config.PayContents;
import com.info.web.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.info.back.dao.ILocalDataDao;
import com.info.constant.Constant;
import com.info.web.synchronization.dao.IDataDao;
import com.info.web.util.DateUtil;
/**
 * 处理续期数据
 * @author gaoyuhai
 *
 */
public class OperaRenewalDataThread implements Runnable {

	private static Logger loger = Logger.getLogger(OperaRenewalDataThread.class);
	private String repaymentId;
	private IDataDao dataDao;
	private ILocalDataDao localDataDao;

	public OperaRenewalDataThread(String repaymentId, IDataDao dataDao,ILocalDataDao localDataDao) {
		this.repaymentId = repaymentId;
		this.dataDao = dataDao;
		this.localDataDao = localDataDao;
	}

	public OperaRenewalDataThread() {
	}

	@Override
	public void run() {
		if(StringUtils.isNotBlank(repaymentId)){
			loger.error("sync-OperaRenewalDataThread:"+repaymentId);
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("ID", repaymentId);
			HashMap<String,Object> repayment = this.dataDao.getAssetRepayment(map);
			loger.info("repayment="+repayment);
			if(null!=repayment){
				try{
					String loanId = String.valueOf(repayment.get("asset_order_id"));//还款id
					//删除对应的借款表，订单表，还款表，还款详情表，催收记录表，催收流转日志表
					localDataDao.deleteOrderAndOther(loanId);
					RedisUtil.delRedisKey2(Constant.TYPE_REPAY_ +repaymentId+"*");
					}catch(Exception e0) {
					e0.printStackTrace();
				}
			}
		}
	}
}
