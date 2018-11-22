package com.info.web.synchronization.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.info.back.service.IMmanLoanCollectionOrderService;
import com.info.config.PayContents;
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
	private static final String LOCK_FLAG = "1";
	
	/**
	 * 同步逾期数据
	 */
	public void syncOverdueDate(){
		loger.error("定时处理逾期数据..."+new Date());
		try {
//			loger.info("获取所有的redis数据");
				try{
					List<String> overdueList = JedisDataClient.getAllValuesByPattern(Constant.TYPE_OVERDUE_+"*_"+PayContents.MERCHANT_NUMBER);
					if(null!=overdueList && 0<overdueList.size()){
						loger.info("处理逾期数据");
						dataForOverdue(overdueList);//处理逾期
					}
				}catch(Exception e){
					loger.info("overdueList get exception..");
					e.printStackTrace();
				}
//			Thread.sleep(1000*60*200);//频率间隔
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 处理逾期数据
	 * @return
	 */
	@Transactional
	public void dataForOverdue(List<String> list){
		if(null!=list && 0<list.size()){
			try{
//				loger.info("处理逾期数据");
				SendOverdueManage dendOverdueManage = new SendOverdueManage(list,this.dataDao,this.localDataDao,this.orderService);
				dendOverdueManage.send();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 获取所有的redis数据
	 */
	public HashMap<String,List<String>> getRedisAllData(){
		HashMap<String,List<String>> hashMap = new HashMap<String,List<String>>();
		List<String> overdueList = new ArrayList<String>();//存放逾期
			
		try {
			List<String> keyList = JedisDataClient.getAllKeys();
			if(null!=keyList && 0<keyList.size()){
				for(String string : keyList){
					if(StringUtils.isNotBlank(string)){
						loger.error("redis-key:"+string);
						loger.error("config-MERCHANT_NUMBER:"+PayContents.MERCHANT_NUMBER.toString());
						if(JedisDataClient.exists(string)){
							//根据配置的商户号进行筛选--只筛选当前商户号的
								if(string.startsWith(Constant.TYPE_OVERDUE_) && string.endsWith(PayContents.MERCHANT_NUMBER.toString())){

									loger.error("redis-key-payId:"+string.replace(Constant.TYPE_OVERDUE_, "").replace(PayContents.MERCHANT_NUMBER.toString(),"").replace("_",""));
									overdueList.add(string.replace(Constant.TYPE_OVERDUE_, "").replace(PayContents.MERCHANT_NUMBER.toString(),"").replace("_",""));
								}
//							}
						}
					}
				}
			}
			hashMap.put(Constant.TYPE_OVERDUE, overdueList);
			return hashMap;
		} catch (Exception e) {
			loger.error("getRedisAllData-exception..."+new Date());
			e.printStackTrace();
			return null;
		}
	}
	

}
