package com.info.back.task;

import com.info.back.service.IOperationRecordService;
import com.info.back.utils.BackConstant;
import com.info.web.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

/**
 * @author Administrator
 * @Description: 清除操作记录数据定时任务
 * @CreateTime 2017-11-02 上午 10:38
 **/

@Component
public class TaskCleacUpOperationRecordData {

    private static Logger logger = Logger.getLogger(TaskCleacUpOperationRecordData.class);

    @Autowired
    private IOperationRecordService operationRecordService;

    public void cleacUpOperationRecordData(){

        Date today = new Date();
        Date date = DateUtil.addDay(today, -BackConstant.OPERATION_RECORD_RETAIN_DAYS);
        try {
            HashMap<String,Object> map = new HashMap<>(2);
            logger.info("开始清除操作记录数据，执行时间：" + today.toLocaleString());
            map.put("endDate",date);
            int count = operationRecordService.deleteByDate(map);
            logger.info("清除数据完成，本次共删除数据 " + count + " 条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
