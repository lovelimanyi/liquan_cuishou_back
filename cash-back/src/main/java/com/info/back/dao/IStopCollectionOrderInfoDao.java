package com.info.back.dao;

import com.info.web.pojo.StopCollectionOrderInfo;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/9/7 0007.
 */
@Component
public interface IStopCollectionOrderInfoDao {

    int insert(StopCollectionOrderInfo order);
}
