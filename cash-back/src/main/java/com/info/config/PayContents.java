package com.info.config;

import com.info.web.util.PropertiesLoader;

/**
 * 现金侠对接地址常量
 */
public class PayContents {
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("config.properties");
    public static final String XJX_REIMBURSEMENT_STATUS_URL = propertiesLoader.getProperty("XJX.reimbursementstatus.url");	//现金侠复查订单状态
    public static final String XJX_PUSH_STATUS_URL = propertiesLoader.getProperty("XJX.push.status_url");	//触发现金侠推送接口
    public static final String XJX_WITHHOLDING_NOTIFY_KEY=propertiesLoader.getProperty("XJX.withholding.key");	//触发现金侠代扣KEY
    public static final String XJX_WITHHOLDING_NOTIFY_URL = propertiesLoader.getProperty("XJX.withholding.url");	//触发现金侠代扣
    public static final String XJX_DOMAINNAME_URL=propertiesLoader.getProperty("XJX.domainname.url");	//现金侠域名
    public static final String COLLECTION_ADVISE_UPDATE_URL=propertiesLoader.getProperty("collection_advise_update_url");//现金侠风控催收建议推送地址
    public static final String XJX_JIANMIAN_URL=propertiesLoader.getProperty("XJX.jianmian.url");//现金侠减免推送地址
    public static final String JXL_HBASE_SERVER_URL = propertiesLoader.getProperty("jxl.hbase.server.rul");//rong360聚信立报告地址
    public static final String XJX_SMS_SEND_URL_NEW = propertiesLoader.getProperty("XJX.sms_send_url_new");//短信调用地址
    public static final String XJX_GET_PHONES = propertiesLoader.getProperty("XJX.GET.PHONES");//获取共债手机号地址

    public static final String JXL_OSS_SERVER_URL = propertiesLoader.getProperty("jxl.oss.server.rul");//oss地址
    //获取商品名称
    public static final String PRODUCT_NAME_URL = propertiesLoader.getProperty("productName.rul");//获取商品名地址
    public static final String XJX_GET_USERINFOS = propertiesLoader.getProperty("XJX.GET.userInfos");//获取cashman库中用户个人用户信息、银行卡、联系人等信息
    public static final String GXB_ECOMMERCE_INFOS = propertiesLoader.getProperty("XJX.ecommerce.info");// gxb电商报告获取地址
    // Basic authentication信息
    public static final String AUTHENTICATE_USERNAME = propertiesLoader.getProperty("XJX.authenticate.username");
    public static final String AUTHENTICATE_PASSWORD = propertiesLoader.getProperty("XJX.authenticate.password");
    public static final String XJX_HEADER_NAME = propertiesLoader.getProperty("XJX.header.name");
    public static final String XJX_HEADER_VALUE = propertiesLoader.getProperty("XJX.header.value");
    public static final String QRCODE_URL=propertiesLoader.getProperty("qr.code");//还款二维码地址
    // 商户号
    public static final String MERCHANT_NUMBER = propertiesLoader.getProperty("merchant.number");
}
