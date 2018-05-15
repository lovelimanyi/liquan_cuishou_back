package com.info.back.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.info.back.utils.SysCacheUtils;
import com.info.web.pojo.BackConfigParams;
import org.springframework.stereotype.Component;

import static com.info.config.PayContents.XJX_SMS_SEND_URL_NEW;

@Component
public class SmsSendUtil {
	private static Logger loger = Logger.getLogger(SmsSendUtil.class);


	/**
	 * 调用api短信接口
	 * @param telephone
	 * @param sms
	 * @return
	 */
	public static boolean sendSmsNew(String telephone,String sms){
		loger.info("sendSms:"+telephone+"   sms="+sms);
//		HashMap<String, String> map = SysCacheUtils.getConfigParams(BackConfigParams.SMS);
//		map = SysCacheUtils.getConfigParams(BackConfigParams.SMS_ZT);
		String url=XJX_SMS_SEND_URL_NEW;

		 //单条发送短信接口
        HttpPost httppost = new HttpPost(url);
        httppost.addHeader("Content-Type", "application/json");
        MessageByTypePojo messageByTypePojo = new MessageByTypePojo(); 	
        messageByTypePojo.setTelephone(telephone);
        messageByTypePojo.setMsgSource("xianjinxia_back");//系统名称
        messageByTypePojo.setMsgBussinessId("login_"+telephone);//业务唯一标识
        messageByTypePojo.setMsgTemplateCode("notification-8");//验证码模板编号
        messageByTypePojo.setValues(sms);
       
        HttpEntity entity = null;
        try {
			entity = new ByteArrayEntity(JSONObject.toJSONString(messageByTypePojo)
			        .getBytes("UTF-8"));
			  httppost.setEntity(entity);
		        CloseableHttpClient httpclient = HttpClients.createDefault();
		        CloseableHttpResponse response;
				try {
					response = httpclient.execute(httppost);
					  if (200 == response.getStatusLine().getStatusCode()) {
				            HttpEntity entity2 = response.getEntity();
				            String result = EntityUtils.toString(entity2);
				            if(StringUtils.isNotBlank(result)){
								JSONObject  json= JSONObject.parseObject(result);
								String code=json.getString("code");
								if("0".equals(code)){
									return true;
								}
								
							}
				        } else {
				        	loger.info("request throw an error !");
				        }
				} catch (ClientProtocolException e) {
					
					loger.error("request throw an error !",e);
				} catch (IOException e) {
					
					loger.error("request throw an error !",e);
				}
		      
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
      
		return  false;
	}
	
}
