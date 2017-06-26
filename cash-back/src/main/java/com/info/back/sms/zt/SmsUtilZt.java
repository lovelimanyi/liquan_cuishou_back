package com.info.back.sms.zt;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.info.back.sms.SmsUtil;
import com.info.back.utils.SysCacheUtils;
import com.info.web.pojo.BackConfigParams;

public class SmsUtilZt {
	static Logger logger = LoggerFactory.getLogger(SmsUtilZt.class);

	/**
	 * -500为未知异常<br>
	 * 0是成功，对应厂商的1<br>
	 * 999对应厂商0，是发送失败<br>
	 * 其他事厂商返回的结果码
	 * 
	 * @param phone
	 * @param content
	 * @return
	 */
	public static int sendSms(String phone, String content, String key) {
		String result = "-500";
		try {
			HashMap<String, String> map = SysCacheUtils
					.getConfigParams(BackConfigParams.SMS_ZT);
			content = map.get("zt_sign" + key) + content;
			logger.info("SmsUtilZt sendSms phone=" + phone + ",content="
					+ content);
			String url = map.get("zt_url");
			String username = map.get("zt_user_name" + key);
			String password = map.get("zt_pwd" + key);
			String productid = map.get("zt_product_id" + key);
			String xh = "";// 没有
			String tkey = TimeUtil.getNowTime("yyyyMMddHHmmss");
			content = URLEncoder.encode(content, "utf-8");
			String param = "url=" + url + "&username=" + username
					+ "&password="
					+ MD5Gen.getMD5(MD5Gen.getMD5(password) + tkey) + "&tkey="
					+ tkey + "&mobile=" + phone + "&content=" + content
					+ "&productid=" + productid + "&xh" + xh;
			logger.info("SmsUtilZt sendSms url=" + url + ",param=" + param);
			String ret = HttpRequest.sendPost(url, param);
			logger.info("SmsUtilZt sendSms ret=" + ret);
			String[] array = ret.split(",");
			if ("1".equals(array[0])) {
				result = "0";
			} else if ("0".equals(array[0])) {
				result = "999";
			} else {
				result = array[0];
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("SmsUtilZt sendSms error", e);
		}
		return Integer.valueOf(result);
	}
}
