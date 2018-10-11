package com.info.web.synchronization;

import java.util.HashMap;

import com.info.config.PayContents;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.info.back.dao.ILocalDataDao;
import com.info.constant.Constant;
/**
 * 处理代扣数据
 * @author gaoyuhai
 *
 */
public class OperaWithDataThread implements Runnable {

	private static Logger loger = Logger.getLogger(OperaWithDataThread.class);
	private String withId;
	private ILocalDataDao localDataDao;

	public OperaWithDataThread(String withId,ILocalDataDao localDataDao) {
		this.withId = withId;
		this.localDataDao = localDataDao;
	}

	public OperaWithDataThread() {
	}

	@Override
	public void run() {
		if (StringUtils.isNotBlank(withId)) {
			loger.error("sync-OperaWithDataThread:"+withId);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("ID", withId);
			this.localDataDao.updateWithHold(map);
			RedisUtil.delRedisKey(Constant.TYPE_WITHHOLD_ + withId+"_"+ PayContents.MERCHANT_NUMBER.toString());
		}else{
			RedisUtil.delRedisKey(Constant.TYPE_WITHHOLD_ + withId+"_"+ PayContents.MERCHANT_NUMBER.toString());
		}
	}

}
