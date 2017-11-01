package com.info.back.service;

import com.info.back.dao.IOperationRecordDao;
import com.info.back.dao.IPaginationDao;
import com.info.constant.Constant;
import com.info.web.pojo.OperationRecord;
import com.info.web.util.PageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 * @Description: OperationRecordService实现
 * @CreateTime 2017-10-30 上午 10:29
 **/

@Service
public class OperationRecordService implements IOperationRecordService {

    @Autowired
    private IOperationRecordDao operationRecordDao;

    @Autowired
    private IPaginationDao paginationDao;

    @Override
    public int deleteByDate(HashMap<String, Object> map) {
        return operationRecordDao.deleteByDate(map);
    }

    @Override
    public int insert(OperationRecord operationRecord) {
        return operationRecordDao.insert(operationRecord);
    }

    @Override
    public PageConfig<OperationRecord> getList(HashMap<String, Object> map) {
        map.put(Constant.NAME_SPACE, "OperationRecord");
        return paginationDao.findPage("findAll","findAllCount",map,null);
    }

    @Override
    public Integer findAllCount(HashMap<String, Object> map) {
        return operationRecordDao.findAllCount(map);
    }

    @Override
    public List<OperationRecord> findAll(HashMap<String, Object> map) {
        return operationRecordDao.findAll(map);
    }
}
