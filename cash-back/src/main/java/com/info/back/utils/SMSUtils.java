package com.info.back.utils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

/** 
* @ClassName: SMSSender 
* @Description: 现金侠发送 短信
* @author   duj
* @date 2016-7-19 下午03:51:34 
*  
*/ 

  
public class SMSUtils {
    private static Logger logger = Logger.getLogger(SMSUtils.class);
    
    
    
    //发送传递的参数
    private static String sendurl = "http://101.201.238.246/MessageTransferWebAppJs/servlet/messageTransferServiceServletByXml";
    private static String cmd = "sendMessage";
    private static String userName = "jinhuiweijinhy";
    private static String passWord = "888888";
    private static String mobilePhone = "";
    private static String body = "";

    /**
     * 
     * @param mobile 手机号码
     * @param content 短信内容
     * @return "0" 为成功
     */
   /* public String sendSms(String mobile, String content){
    	String sendUrl = null;
    	 try {// 否则发到手机乱码
    		 sendUrl = sendurl + "?cmd=" + cmd + "&userName=" + userName + "&passWord="+ passWord 
             + "&mobilePhone=" + mobile + "&body="+URLEncoder.encode(content, "UTF-8");
    		 logger.info("url:" + sendUrl);
         } catch (UnsupportedEncodingException uee) {
             logger.error(uee.toString());
         }
    	 logger.info("短信内容为------------->:" + content);
         return getUrl(sendUrl);
    }*/
    //云峰短信接口
    public String sendSms(String mobiles, String msg) {
        String str="";
        String app_key =SmsYunFengUtil.APPKEY;//秘钥
        String  app_secret =SmsYunFengUtil.APPSECRET;//应用秘钥
        String nonce_str =SmsYunFengUtil.USER;//用户名
        String dest_id = mobiles;//终端手机号
        String title = SmsYunFengUtil.TITLE;//签名
        //现在msg签名不需要自己写 切割一下
        msg= msg.substring(5);
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

    public String getRandomNumberByNum(int num) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < num; i++) {  
            long randomNum = Math.round(Math.floor(Math.random() * 10.0D));  
            sb.append(randomNum);  
        }  
        return sb.toString();  
    }  

    
    
    /** 
     * @Title: getUrl 
     * @Description: 获取结果
     * @param urlString
     * @return resultCode “0”为成功
     */ 
        
    public String getUrl(String urlString) {
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            conn.setReadTimeout(15000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            for (String line = null; (line = reader.readLine()) != null;) {
                sb.append(line + "\n");
            }
            reader.close();
        } catch (Exception e) {
            logger.error(e.toString());
        }
        String resultCode = "";
        try {
        	String result = URLDecoder.decode(sb.toString(), "UTF-8");
        	resultCode = XMLUtils.formatXmlGetResultCode(result);
        	logger.info("smsResultCode:" + resultCode);
        } catch (Exception e) {
        	logger.error(e.toString());
        }

        return resultCode;
    }
    
    /***
     * 测试地址
     * @throws Exception 
     * 
     * ***/
    public static void main(String[] args) throws Exception {
    	SMSUtils smsUtils = new SMSUtils();
        String message = "【现金侠】您好，您的验证码是"+smsUtils.getRandomNumberByNum(6)+"，30分钟有效，转发无效。";
        System.out.println(message);
        String phone = "15021280239";
        String r = new SMSUtils().sendSms(phone, message);
        System.out.println(r);
    }

}
