package com.info.back.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.CollectionWithholdingRecord;
@Repository
public interface ICollectionWithholdingRecordDao {

	public void insert(CollectionWithholdingRecord withholdingRecord);

	public List<CollectionWithholdingRecord> findOrderList(String id);

	public int updateStatusFail();

	public List<CollectionWithholdingRecord> findTowHoursList();

	public int findCurrDayWithhold(HashMap<String, String> dayMap);

	public void updateWithholdStatus();

	public void updateOverdueStatus();

}
