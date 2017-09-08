package com.info.back.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.ICollectionWithholdingRecordDao;
import com.info.web.pojo.CollectionWithholdingRecord;

@Service
public class CollectionWithholdingRecordService implements ICollectionWithholdingRecordService {
    @Autowired
    private ICollectionWithholdingRecordDao collectionWithholdingRecordDao;

    @Override
    public boolean updateStatusFail() {
        return collectionWithholdingRecordDao.updateStatusFail() > 0;
    }

    @Override
    public List<CollectionWithholdingRecord> findTowHoursList() {
        return collectionWithholdingRecordDao.findTowHoursList();
    }

    @Override
    public int updateWithholdStatus(Map<String,Object> map) {
       return collectionWithholdingRecordDao.updateWithholdStatus(map);
    }

    @Override
    public void updateOverdueStatus() {
        collectionWithholdingRecordDao.updateOverdueStatus();
    }
}
