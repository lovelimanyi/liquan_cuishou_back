package com.info.back.service;

import com.info.back.dao.IOrderChangeRecordDao;
import com.info.web.pojo.OrderChangeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @Description:
 * @CreateTime 2018-05-17 下午 3:26
 **/

@Service
public class OrderChangeRecordService implements IOrderChangeRecordService {

    @Autowired
    private IOrderChangeRecordDao recordDao;

    @Override
    public void insert(OrderChangeRecord record) {
        recordDao.insert(record);
    }
}
