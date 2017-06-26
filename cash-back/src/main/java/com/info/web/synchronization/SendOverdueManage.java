package com.info.web.synchronization;

import java.util.List;

import com.info.back.dao.ILocalDataDao;
import com.info.back.service.TaskJobMiddleService;
import com.info.web.synchronization.dao.IDataDao;

public class SendOverdueManage {

	private List<String> repaymentIdList;
	private IDataDao dataDao;
	private ILocalDataDao localDataDao;
	private TaskJobMiddleService taskJobMiddleService;
	
	
	public SendOverdueManage(List<String> repaymentIdList, IDataDao dataDao,ILocalDataDao localDataDao,TaskJobMiddleService taskJobMiddleService) {
		this.repaymentIdList = repaymentIdList;
		this.dataDao = dataDao;
		this.localDataDao = localDataDao;
		this.taskJobMiddleService = taskJobMiddleService;
	}


	public void send(){
		if(null==repaymentIdList || repaymentIdList.size()==0){
			return ;
		}
		ThreadPoolOverdue pool = ThreadPoolOverdue.getInstance();
		for(String repaymentId : repaymentIdList){
			OperaOverdueDataThread operaDataThread = new OperaOverdueDataThread(repaymentId,dataDao,localDataDao,taskJobMiddleService);
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

