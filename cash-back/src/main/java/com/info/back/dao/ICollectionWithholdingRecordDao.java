package com.info.back.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.CollectionWithholdingRecord;
@Repository
public interface ICollectionWithholdingRecordDao {

	 void insert(CollectionWithholdingRecord withholdingRecord);

	 List<CollectionWithholdingRecord> findOrderList(String id);

	 int updateStatusFail();

	 List<CollectionWithholdingRecord> findTowHoursList();

	 int findCurrDayWithhold(HashMap<String, String> dayMap);

	 int updateWithholdStatus(Map<String, Object> map);

	 void updateOverdueStatus();

	/**
	 * 根据催收员id查询其最新一条代扣记录
	 * @return
	 */
	CollectionWithholdingRecord getLatestWithholdRecord(String operationUserId);
}
