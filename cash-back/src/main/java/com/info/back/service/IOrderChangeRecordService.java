package com.info.back.service;

import com.info.web.pojo.OrderChangeRecord;
import org.springframework.stereotype.Repository;

/**
 * 订单流转数据记录
 */
@Repository
public interface IOrderChangeRecordService {

	/**
	 * 插入对象
	 * 
	 * @param record
	 */
	void insert(OrderChangeRecord record);

}
