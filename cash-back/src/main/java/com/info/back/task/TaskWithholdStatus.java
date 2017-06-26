package com.info.back.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.info.back.service.ICollectionWithholdingRecordService;
import com.info.web.pojo.CollectionWithholdingRecord;

public class TaskWithholdStatus {
	protected Logger logger = LoggerFactory.getLogger(TaskWithholdStatus.class);
	@Autowired
	private ICollectionWithholdingRecordService collectionWithholdingRecordService;
	public void updateStatus(){
		try {
			//查询两小时前申请中的数据   what???????
			List<CollectionWithholdingRecord> recordList=collectionWithholdingRecordService.findTowHoursList();
			if(recordList!=null && recordList.size() > 0){
				collectionWithholdingRecordService.updateStatusFail();
			}else{
				logger.error("暂时不没有需要处理的代扣记录");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TaskWithholdStatus updateStatus error:", e);
		}
	}
}
