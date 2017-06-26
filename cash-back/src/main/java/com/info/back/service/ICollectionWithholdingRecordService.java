package com.info.back.service;

import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.CollectionWithholdingRecord;

public interface ICollectionWithholdingRecordService {
	/**
	 * 修改催收代扣状态
	 * @param 
	 * @return
	 */
	public boolean updateStatusFail();

	public List<CollectionWithholdingRecord> findTowHoursList();

	/**
	 * 分期记录 时间段内是否可代扣
	 */
	public void updateWithholdStatus();

	/**
	 * 分期记录 时间段内是否逾期
	 */
	public void updateOverdueStatus();
}
