package com.info.web.synchronization;

import java.util.List;

import com.info.back.dao.ILocalDataDao;
/**
 * 代扣
 * @author Administrator
 *
 */
public class SendWithManage {
	private List<String> withHoldList;
	private ILocalDataDao localDataDao;
	
	
	public SendWithManage(List<String> withHoldList, ILocalDataDao localDataDao) {
		this.withHoldList = withHoldList;
		this.localDataDao = localDataDao;
	}


	public void send(){
		if(null==withHoldList || withHoldList.size()==0){
			return ;
		}
//		ThreadPoolWith pool = ThreadPoolWith.getInstance();
		ThreadPoolRenewal pool = ThreadPoolRenewal.getInstance();
		for(String withId : withHoldList){
			OperaWithDataThread operaDataThread = new OperaWithDataThread(withId,localDataDao);
			pool.execute(operaDataThread);
			try {
				//可以根据实际情况做下发送速度控制
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

