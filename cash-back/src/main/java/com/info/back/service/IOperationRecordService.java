package com.info.back.service;

import com.info.web.pojo.OperationRecord;
import com.info.web.util.PageConfig;

import java.util.HashMap;
import java.util.List;

/**
 * @Description:操作记录service接口
 * @Author Administrator
 * @CreateTime 2017-10-30 上午 10:28
 **/
public interface IOperationRecordService {

	int deleteByDate(HashMap<String, Object> map);

	int insert(OperationRecord operationRecord);

	PageConfig<OperationRecord> getList(HashMap<String, Object> map);

	Integer findAllCount(HashMap<String, Object> map);

	List<OperationRecord> findAll(HashMap<String, Object> map);
}
