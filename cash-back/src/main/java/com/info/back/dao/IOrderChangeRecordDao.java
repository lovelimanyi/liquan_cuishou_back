package com.info.back.dao;

import com.info.web.pojo.BackRole;
import com.info.web.pojo.BackTree;
import com.info.web.pojo.OrderChangeRecord;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * 订单流转数据记录
 */
@Repository
public interface IOrderChangeRecordDao {

	/**
	 * 插入对象
	 * 
	 * @param record
	 */
	void insert(OrderChangeRecord record);

}
