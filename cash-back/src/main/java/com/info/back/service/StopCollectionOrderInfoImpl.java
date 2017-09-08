package com.info.back.service;

import com.info.back.dao.IStopCollectionOrderInfoDao;
import com.info.web.pojo.StopCollectionOrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/9/7 0007.
 */
@Service
public class StopCollectionOrderInfoImpl implements IStopCollectionOrderInfoService {

    @Autowired
    private IStopCollectionOrderInfoDao stopCollectionOrderInfoDao;

    @Override
    public int save(StopCollectionOrderInfo info) {
        return stopCollectionOrderInfoDao.insert(info);
    }
}
