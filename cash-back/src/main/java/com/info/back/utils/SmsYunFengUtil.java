package com.info.back.utils;

/**
 * 发短信常量类
 * @author wayne
 */
public class SmsYunFengUtil {
	
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("sms.properties");
	/**
	 * 发送短信的请求地址
	 */
	public static final String APIURL = propertiesLoader.getProperty("apiurl");
	/**
	 * 用户账号
	 */
	public static final String USER = propertiesLoader.getProperty("nonce_str");
	
	/**
	 * 密钥
	 */
	public static final String APPKEY = propertiesLoader.getProperty("appkey");
	
	/**
	 * 应用密钥
	 */
	public static final String APPSECRET = propertiesLoader.getProperty("appSecret");
	
	/**
	 * 签名
	 */
	public static final String TITLE = "现金侠";
	/**
	 * 发送成功code
	 */
	public static final String FLAG = propertiesLoader.getProperty("flag");
	
}
