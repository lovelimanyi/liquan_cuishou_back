package com.info.web.synchronization;

import java.util.List;

import com.info.back.dao.ILocalDataDao;
import com.info.web.synchronization.dao.IDataDao;

public class SendRepayManage {

	private List<String> repayIdList;
	private IDataDao dataDao;
	private ILocalDataDao localDataDao;
	
	
	public SendRepayManage(List<String> repayIdList, IDataDao dataDao,ILocalDataDao localDataDao) {
		this.repayIdList = repayIdList;
		this.dataDao = dataDao;
		this.localDataDao = localDataDao;
	}


	public void send(){
		if(null==repayIdList || repayIdList.size()==0){
			return ;
		}
//		ThreadPoolRepay pool = ThreadPoolRepay.getInstance();
		ThreadPoolRenewal pool = ThreadPoolRenewal.getInstance();
		for(String repaymentId : repayIdList){
			OperaRepayDataThread operaRepayDataThread = new OperaRepayDataThread(repaymentId,dataDao,localDataDao);
			pool.execute(operaRepayDataThread);
			try {
				//可以根据实际情况做下发送速度控制
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

