package com.info.back.utils;

/**
 * 流浪猫发短信接口
 * @author wayne
 * 
 */
public class YunFengSendSmsUtil {
	/**
	 * 云峰短信接口
	 * @param :mobiles手机号可以多个。之间用逗号隔开，
	 * @return :返回值0表示发送成功，返回1表示发送失败
	 */
	public String sendSms(String mobiles, String msg) {
		String str="";
		String app_key =SmsYunFengUtil.APPKEY;//秘钥
		String  app_secret =SmsYunFengUtil.APPSECRET;//应用秘钥
		String nonce_str =SmsYunFengUtil.USER;//用户名
		String dest_id = mobiles;//终端手机号
		String title = SmsYunFengUtil.TITLE;//签名
		String content =msg;//短信内容
		String URL = SmsYunFengUtil.APIURL;
       try {
    	   HttpProtocolHandler httpProto = HttpProtocolHandler.getInstance();
    	   str = httpProto.getPost(app_key, app_secret, nonce_str, dest_id, title, content,URL);
	  } catch (Exception e) {
			e.printStackTrace();
	  }       
		return str;
	}
	public static void main(String[] args) throws Exception {
		SMSUtils smsUtils = new SMSUtils();
		String message = "【现金侠】您好，您的验证码是"+smsUtils.getRandomNumberByNum(6)+"，30分钟有效，转发无效。";
		System.out.println(message);
		String phone = "13061966998";
//		String r = new YunFengSendSmsUtil().sendSms(phone, message);
//		System.out.println(message.substring(5));
	}
}