package com.info.back.utils;

import com.info.config.MqConstant;
import com.xjx.mqclient.service.MqClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by Administrator on 2017/8/31 0031.
 */
@Component
public class MqClientUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(MqClientUtils.class);
    @Autowired
    @Qualifier("dataSourcecs")
    private DataSource dataSource;

    @Bean
    public MqClient mqClient() {
        MqClient mq = null;
        try {
            mq = new MqClient(MqConstant.MQ_URL, dataSource, MqConstant.MQ_QUEUE_TABLE_NAME, MqConstant.MQ_QUEUE_NAME, MqConstant.MQ_QUEUE_MAX_COUNT,
                    MqConstant.MQ_IS_CREATETABLE);
            mq.start();
        } catch (Exception e) {
            LOGGER.error("mq启动初始化异常",e);
        }
        return mq;
    }
}
