package com.info.web.synchronization;

import java.util.List;

import com.info.back.dao.ILocalDataDao;
import com.info.web.synchronization.dao.IDataDao;

public class SendRenewalManage {

	private List<String> renewalIdList;
	private IDataDao dataDao;
	private ILocalDataDao localDataDao;
	
	
	public SendRenewalManage(List<String> renewalIdList, IDataDao dataDao,ILocalDataDao localDataDao) {
		this.renewalIdList = renewalIdList;
		this.dataDao = dataDao;
		this.localDataDao = localDataDao;
	}


	public void send(){
		if(null==renewalIdList || renewalIdList.size()==0){
			return ;
		}
//		ThreadPoolRenewal pool = ThreadPoolRenewal.getInstance();
		ThreadPoolRenewal pool = ThreadPoolRenewal.getInstance();
		for(String repaymentId : renewalIdList){
			OperaRenewalDataThread operaRenewalDataThread = new OperaRenewalDataThread(repaymentId,dataDao,localDataDao);
			pool.execute(operaRenewalDataThread);
			try {
				//可以根据实际情况做下发送速度控制
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

