package com.info.config;

import com.info.web.util.PropertiesLoader;

/**
 * Created by Administrator on 2017/8/31 0031.
 */
public class MqConstant {
    private static PropertiesLoader propertiesLoader = new PropertiesLoader("mq.properties");
    public final static String MQ_URL = propertiesLoader.getProperty("brokerUrl");
    public final static String MQ_QUEUE_TABLE_NAME = propertiesLoader.getProperty("queueTableName");
    public final static String MQ_QUEUE_NAME = propertiesLoader.getProperty("queueName");
    public final static Integer MQ_QUEUE_MAX_COUNT = propertiesLoader.getInteger("queueMaxCount");
    public final static Integer MQ_IS_CREATETABLE = propertiesLoader.getInteger("isCreateTable");
}
