package com.info.back.service;

import com.info.web.pojo.CollectionWithholdingRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ICollectionWithholdingRecordService {
	/**
	 * 修改催收代扣状态
	 * @param 
	 * @return
	 */
	 boolean updateStatusFail();

	 List<CollectionWithholdingRecord> findTowHoursList();

	/**
	 * 分期记录 时间段内是否可代扣
	 */
	 int updateWithholdStatus(Map<String, Object> map);

	/**
	 * 分期记录 时间段内是否逾期
	 */
	 void updateOverdueStatus();

    int getById(String uuid);
}
