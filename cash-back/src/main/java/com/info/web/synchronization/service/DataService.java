package com.info.web.synchronization.service;

import com.info.back.dao.ILocalDataDao;
import com.info.config.PayContents;
import com.info.constant.Constant;
import com.info.web.synchronization.SendRepayManage;
import com.info.web.synchronization.SendWithManage;
import com.info.web.synchronization.dao.IDataDao;
import com.info.web.util.JedisDataClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DataService{

	private static Logger logger = Logger.getLogger(DataService.class);
	@Autowired
	private IDataDao dataDao;
	@Autowired
	private ILocalDataDao localDataDao;

	
	/**
	 * 全部还款数据同步
	 */
	public void syncDate(){
			try {
				logger.info("获取所有repay的redis数据");
					try{
						List<String> repayList = JedisDataClient.getAllValuesByPattern(Constant.TYPE_REPAY_+"*");
						if(null!=repayList && 0<repayList.size()){
							logger.info("处理还款数据");
							dataForRepay(repayList);//处理还款
						}
					}catch(Exception e){
						logger.info("repayList get exception..");
						e.printStackTrace();
					}
					try{
						List<String> withList = JedisDataClient.getAllValuesByPattern(Constant.TYPE_WITHHOLD_+"*_"+PayContents.MERCHANT_NUMBER);
						if(null!=withList && 0<withList.size()){
							logger.info("处理代扣数据");
							dataForWithHold(withList);//处理还款
						}
					}catch(Exception e){
						logger.info("withList get exception..");
						e.printStackTrace();
					}
			} catch (Exception e) {
				e.printStackTrace();
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
				logger.info("处理还款数据");
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
				logger.info("处理代扣数据");
				SendWithManage sendWithManage = new SendWithManage(list,this.localDataDao);
				sendWithManage.send();
					
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
}
