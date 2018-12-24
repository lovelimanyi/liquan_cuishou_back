package com.info.web.synchronization.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.info.back.service.IMmanLoanCollectionOrderService;
import com.info.config.PayContents;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.back.dao.ILocalDataDao;
import com.info.back.service.TaskJobMiddleService;
import com.info.constant.Constant;
import com.info.web.synchronization.SendOverdueManage;
import com.info.web.synchronization.dao.IDataDao;
import com.info.web.util.JedisDataClient;
/**
 * 定时跑逾期
 * @author Administrator
 *
 */
@Service
public class DataSyncService {
	
	private static Logger loger = Logger.getLogger(DataSyncService.class);
	
	@Autowired
	private IDataDao dataDao;
	@Autowired
	private ILocalDataDao localDataDao;
	@Autowired
	private IMmanLoanCollectionOrderService orderService;

	/**
	 * 同步逾期数据
	 */
	public void syncOverdueDate(){
		loger.info("获取所有overdue的redis数据");
		List<String> overdueValueList = new ArrayList<>();
		try {
			overdueValueList = JedisDataClient.getAllValuesByPattern(Constant.TYPE_OVERDUE_ + "*");
			if (CollectionUtils.isNotEmpty(overdueValueList)) {
				loger.info("处理逾期数据");
				dataForOverdue(overdueValueList);//处理逾期
			}

		} catch (Exception e) {
			loger.error("getRedisValue-exception", e);
			return;
		}
	}
	
	/**
	 * 处理逾期数据
	 * @return
	 */
	public void dataForOverdue(List<String> list){
		try{
			SendOverdueManage sendOverdueManage = new SendOverdueManage(list,this.dataDao,this.localDataDao,this.orderService);
			sendOverdueManage.send();
		}catch(Exception e){
			loger.error("dataForOverdue-exception", e);
		}
	}

}
