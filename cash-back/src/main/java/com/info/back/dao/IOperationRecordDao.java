package com.info.back.dao;

import com.info.web.pojo.OperationRecord;
import com.info.web.util.PageConfig;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @Description:操作记录DAO层
 * @Author Administrator
 * @CreateTime 2017-10-27 下午 6:17
 **/

@Repository
public interface IOperationRecordDao {

	int deleteByDate(HashMap<String, Object> map);

	int insert(OperationRecord operationRecord);

	PageConfig<OperationRecord> getList(HashMap<String, Object> map);

	Integer findAllCount(HashMap<String, Object> map);

	List<OperationRecord> findAll(HashMap<String, Object> map);
}
