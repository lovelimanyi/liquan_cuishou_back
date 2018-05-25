package com.info.back.mail;


import com.info.web.util.PropertiesLoader;

import java.util.Arrays;
import java.util.List;

public class MailCenterConstant {

    private static PropertiesLoader propertiesLoader = new PropertiesLoader("mail.properties");
    public final static String PROTOCOL =propertiesLoader.getProperty("PROTOCOL");
    // 设置发件人使用的SMTP服务器、用户名、密码
    public final static String SMTP_SERVER =propertiesLoader.getProperty("SMTP_SERVER");
    public final static String FROM_ADDRESS =propertiesLoader.getProperty("FROM_ADDRESS");
    public final static String USER =propertiesLoader.getProperty("USER");
    public final static String PWD =propertiesLoader.getProperty("PWD");



   //派单结果发送
   public final static String SEND_RESULE_MAIL =  propertiesLoader.getProperty("SEND_RESULE_MAIL");

    //3日无催收记录告警
     public final static String BEYOND_WARN =  propertiesLoader.getProperty("BEYOND_WARN");



}
