package com.info.web.synchronization.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import com.info.web.synchronization.SendRenewalManage;
import com.info.web.synchronization.SendRepayManage;
import com.info.web.synchronization.SendWithManage;
import com.info.web.synchronization.dao.IDataDao;
import com.info.web.util.JedisDataClient;

@Service
public class DataService implements IDataService {

	private static Logger loger = Logger.getLogger(DataService.class);
	@Autowired
	private IDataDao dataDao;
	@Autowired
	private ILocalDataDao localDataDao;
	private static final String LOCK_FLAG = "1";
//	@Autowired
//	private TaskJobMiddleService taskJobMiddleService;

	
	/**
	 * 同步数据
	 */
	public void syncDate(TaskJobMiddleService taskJobMiddleService){
		/*try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}*/
		
//		loger.info("获取数据 syncDate");
//		while(true){
			try {
				loger.info("获取所有的redis数据");
				HashMap<String,List<String>> hashMap =  getRedisAllData();
				if(null!=hashMap){
//					try{
//						List<String> overdueList = hashMap.get(Constant.TYPE_OVERDUE);
//						if(null!=overdueList && 0<overdueList.size()){
//							loger.info("处理逾期数据");
//							dataForOverdue(overdueList,taskJobMiddleService);//处理逾期
//						}
//					}catch(Exception e){
//						loger.info("overdueList get exception..");
//						e.printStackTrace();
//					}
					try{
						List<String> renewalList = hashMap.get(Constant.TYPE_RENEWAL);
						if(null!=renewalList && 0<renewalList.size()){
							loger.info("处理续期数据");
							dataForRenewal(renewalList);//处理续期
						}
					}catch(Exception e){
						loger.info("renewalList get exception..");
						e.printStackTrace();
					}
					try{
						List<String> repayList = hashMap.get(Constant.TYPE_REPAY);
						if(null!=repayList && 0<repayList.size()){
							loger.info("处理还款数据");
							dataForRepay(repayList);//处理还款
						}
					}catch(Exception e){
						loger.info("repayList get exception..");
						e.printStackTrace();
					}
					try{
						List<String> withList = hashMap.get(Constant.TYPE_WITHHOLD);
						if(null!=withList && 0<withList.size()){
							loger.info("处理代扣数据");
							dataForWithHold(withList);//处理还款
						}
					}catch(Exception e){
						loger.info("withList get exception..");
						e.printStackTrace();
					}
				}
//				Thread.sleep(1000*60*2);//频率间隔
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//	}
	
	/**
	 * 获取所有的redis数据
	 */
	public HashMap<String,List<String>> getRedisAllData(){
		HashMap<String,List<String>> hashMap = new HashMap<String,List<String>>();
//		List<String> overdueList = new ArrayList<String>();//存放逾期
		List<String> renewalList = new ArrayList<String>();//存放续期
		List<String> repayList = new ArrayList<String>();//存放还款
		List<String> withList = new ArrayList<String>();//存放代扣WITHHOLD_
			
		try {
			List<String> keyList = JedisDataClient.getAllKeys();
			if(null!=keyList && 0<keyList.size()){
				for(String string : keyList){
					if(StringUtils.isNotBlank(string)){
						loger.error("redis-key:"+string);
						if(JedisDataClient.exists(string)){
							//根据配置的商户号进行筛选--只筛选当前商户号的
								if(string.startsWith(Constant.TYPE_RENEWAL_)&& string.endsWith(PayContents.MERCHANT_NUMBER.toString())){
									renewalList.add(string.replace(Constant.TYPE_RENEWAL_, "").replace(PayContents.MERCHANT_NUMBER.toString(),"").replace("_",""));
								}
								if(string.startsWith(Constant.TYPE_REPAY_)&& string.endsWith(PayContents.MERCHANT_NUMBER.toString())){
									loger.error("redis-key-payId:"+string.replace(Constant.TYPE_REPAY_, "").replace(PayContents.MERCHANT_NUMBER.toString(),"").replace("_",""));
									repayList.add(string.replace(Constant.TYPE_REPAY_, "").replace(PayContents.MERCHANT_NUMBER.toString(),"").replace("_",""));
								}
								if(string.startsWith(Constant.TYPE_WITHHOLD_)&& string.endsWith(PayContents.MERCHANT_NUMBER.toString())){
									withList.add(string.replace(Constant.TYPE_WITHHOLD_, "").replace(PayContents.MERCHANT_NUMBER.toString(),"").replace("_",""));
								}
//							}
						}
					}
				}
			}
//			hashMap.put(Constant.TYPE_OVERDUE, overdueList);
			hashMap.put(Constant.TYPE_RENEWAL, renewalList);
			hashMap.put(Constant.TYPE_REPAY, repayList);
			hashMap.put(Constant.TYPE_WITHHOLD, withList);
			return hashMap;
		} catch (Exception e) {
			loger.error("getRedisAllData-exception..."+new Date());
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 处理逾期数据
	 * @return
	 */
//	@Transactional
//	public void dataForOverdue(List<String> list,TaskJobMiddleService taskJobMiddleService){
//		if(null!=list && 0<list.size()){
//			try{
//				loger.info("处理逾期数据");
//				SendOverdueManage dendOverdueManage = new SendOverdueManage(list,this.dataDao,this.localDataDao,taskJobMiddleService);
//				dendOverdueManage.send();
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//		
//	}
	/**
	 * 处理续期数据
	 * @return
	 */
	@Transactional
	public void dataForRenewal(List<String> list){
		if(null!=list && 0<list.size()){
			try{
				loger.info(" 处理续期数据");
				SendRenewalManage sendRenewalManage = new SendRenewalManage(list,this.dataDao,this.localDataDao);
				sendRenewalManage.send();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 处理还款数据
	 * @return
	 */
	@Transactional
	private void dataForRepay(List<String> list){
		if(null!=list && 0<list.size()){
			try{
				loger.info("处理还款数据");
				SendRepayManage sendRepayManage = new SendRepayManage(list,this.dataDao,this.localDataDao);
				sendRepayManage.send();
					
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * 处理代扣数据
	 */
	private void dataForWithHold(List<String> list){
		if(null!=list && 0<list.size()){
			try{
				loger.info("处理代扣数据");
				SendWithManage sendWithManage = new SendWithManage(list,this.localDataDao);
				sendWithManage.send();
					
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
}
