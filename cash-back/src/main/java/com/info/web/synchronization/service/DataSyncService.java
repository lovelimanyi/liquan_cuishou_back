package com.info.web.synchronization.service;

import com.alibaba.fastjson.JSONObject;
import com.info.back.dao.IBackUserDao;
import com.info.back.dao.ILocalDataDao;
import com.info.back.service.IMmanLoanCollectionOrderService;
import com.info.back.utils.BackConstant;
import com.info.constant.Constant;
import com.info.web.pojo.CollectionBackUser;
import com.info.web.synchronization.SendOverdueManage;
import com.info.web.synchronization.dao.IDataDao;
import com.info.web.util.JedisDataClient;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 定时跑逾期
 * @author Administrator
 *
 */
@Component
public class DataSyncService {
	
	private static Logger logger = Logger.getLogger(DataSyncService.class);
	@Autowired
	private IDataDao dataDao;
	@Autowired
	private ILocalDataDao localDataDao;
	@Autowired
	private IBackUserDao backUserDao;
	@Autowired
	private IMmanLoanCollectionOrderService orderService;

	/**
	 * 首逾数据同步-逾期部分还款同步
	 */
	public void syncOverdueDate() throws Exception {
		//查询当前系统中S1可用催收员，并根据催收员今日派单量排序，缓存至redis中
		Map<String, Object> map = new HashedMap();
		map.put("groupLevel", BackConstant.XJX_OVERDUE_LEVEL_S1);
		String redisKey = BackConstant.DISTRIBUTE_BACK_USER;
		Integer length = JedisDataClient.llen(redisKey);
		if (length <= 0){
			List<CollectionBackUser> backUserList = backUserDao.getBackUserGroupByOrderCount(map);
			if (CollectionUtils.isNotEmpty(backUserList)){
				for (CollectionBackUser backUser : backUserList){
					try {
						JedisDataClient.rpush(redisKey , JSONObject.toJSONString(backUser));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			JedisDataClient.expire(redisKey,60*60);
		}

//		JSONObject backUser = null;
		String backUser = null;
		try {
			backUser = JedisDataClient.lpop(BackConstant.DISTRIBUTE_BACK_USER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(backUser);
//		System.out.println(backUser.get("uuid").toString());
//		System.out.println(backUser.get("companyId").toString());



//		logger.info("获取所有overdue的redis数据");
//		try {
//			List<String> overdueValueList = JedisDataClient.getAllValuesByPattern(Constant.TYPE_OVERDUE_ + "*");
//			if (CollectionUtils.isNotEmpty(overdueValueList)) {
//				logger.info("处理逾期数据");
//				dataForOverdue(overdueValueList);//处理逾期
//			}
//
//		} catch (Exception e) {
//			logger.error("getRedisValue-exception", e);
//			return;
//		}
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
			logger.error("dataForOverdue-exception", e);
		}
	}

}
