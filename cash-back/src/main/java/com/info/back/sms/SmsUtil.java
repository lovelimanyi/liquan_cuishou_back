package com.info.back.sms;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.info.back.utils.SysCacheUtils;
import com.info.constant.Constant;
import com.info.web.pojo.BackConfigParams;
import com.info.web.util.DateUtil;
import com.info.web.util.HttpUtil;
import com.info.web.util.encrypt.MD5coding;

public class SmsUtil {
	private static Logger logger = LoggerFactory.getLogger(SmsUtil.class);
	private static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";// xml

	// 头文件

	/**
	 * 排序
	 * 
	 * @param xmlDto
	 * @return
	 */
	private static String sortString(XmlDto xmlDto) {
		if (null != xmlDto) {
			StringBuffer sb = new StringBuffer();
			sb.append("app_key=" + xmlDto.getHead().getApp_key()).append(
					"&batch_num=" + xmlDto.getBody().getBatch_num()).append(
					"&content=" + xmlDto.getBody().getContent());
			List<Dest> list = xmlDto.getBody().getDests().getDest();
			StringBuffer sb0 = new StringBuffer();
			for (Dest d : list) {
				sb0.append("&dest_id=" + d.getDest_id());
			}
			for (Dest d : list) {
				sb0.append("&mission_num=" + d.getMission_num());
			}
			sb.append(sb0).append(
					"&nonce_str=" + xmlDto.getHead().getNonce_str()).append(
					"&sms_type=" + xmlDto.getBody().getSms_type()).append(
					"&time_stamp=" + xmlDto.getHead().getTime_stamp());

			return sb.toString();
		}
		return null;
	}

	/**
	 * 传入手机号码不合法或者未知异常返回-500<br>
	 * 0是正常,其他结果是短信商其它错误，具体参考接口文档<br>
	 * 
	 * @param phone
	 *            Object类型，可以是字符串、数组或者list
	 * @param content
	 * @return
	 * @throws RemoteException
	 */
	public static int sendSms(Object phone, String content, String type,
			String sign, String userName) {
		int result = -500;
		/**
		 * 短信商需要的格式一般是英文逗号隔开的字符串或者数组
		 */
		String[] phones = null;
		String phoneString = null;
		try {
			if (phone instanceof String) {
				phoneString = String.valueOf(phone).replaceAll("，", ",");
				int lastIndex = phoneString.length() - 1;
				if (",".equals(phoneString.indexOf(lastIndex))) {
					phoneString = phoneString.substring(0, lastIndex);
				}
				phones = phoneString.split(",");
			} else if (phone instanceof String[]) {
				phones = (String[]) phone;
			} else if (phone instanceof List) {
				List<String> list = (List<String>) phone;
				phones = (String[]) list.toArray(new String[list.size()]);
			}
			if (phones != null) {
				/**
				 * 此处根据具体短信商选择不同包下的SendSms.sendSms方法，如果该短信商发送成功结果码不为0需要在底层手动让其等0
				 */
				List<Dest> list = new ArrayList<Dest>();
				int i = 1;
				for (String tel : phones) {
					Dest dest = new Dest();
					dest.setDest_id(tel);
					dest.setMission_num(String.valueOf(i));
					i++;
					list.add(dest);
				}
				/** 组装XML */
				HashMap<String, String> map = SysCacheUtils
						.getConfigParams(BackConfigParams.SMS);
				String appsecret = "&app_secret=" + map.get("app_secret");
				XmlDto xmlDto = new XmlDto();
				Head head = new Head();
				head.setApp_key(map.get("app_key"));
				// 如果用户名为空就使用默认用户名
				if (StringUtils.isBlank(userName)) {
					userName = map.get("nonce_str");
				}
				head.setNonce_str(userName);
				head.setSign("");
				head.setTime_stamp(DateUtil.getDateFormat("yyyyMMddHHmmss"));
				String keys = DateUtil.getDateFormat("yyyyMMddHHmmss");
				Dests ds = new Dests();
				ds.setDest(list);
				Body body = new Body();
				body.setBatch_num(userName + keys);
				body.setDests(ds);
				// 如果签名为空就使用默认签名
				if (StringUtils.isBlank(sign)) {
					sign = map.get("sign_pre");
				}
				body.setContent(sign + content);
				body.setSms_type(type);
				xmlDto.setHead(head);
				xmlDto.setBody(body);

				String smsXml = sortString(xmlDto) + appsecret;
				logger.info("md5-befor:" + smsXml);
				String smsSign = "";

				smsSign = new MD5coding().getInstance().MD5(
						String.valueOf(smsXml));
				logger.info("md5-after:" + smsSign);
				xmlDto.getHead().setSign(smsSign);

				String stringXml = XmlUtil.bulidMessage(xmlDto);
				stringXml = stringXml.replaceAll("<content>",
						"<content><![CDATA[").replaceAll("</content>",
						"]]></content>");
				String requestXml = XML_HEAD + stringXml;
				logger.info("requestXml " + requestXml);
				String result2 = HttpUtil.getInstance().doPost(
						map.get("sms_send_url"), requestXml);
				logger.info("sendSms-result:" + result);
				if (null != result2) {
					Result heads = new Result();
					for (String s : heads.getOutString()) {
						result2 = result2.replace(s, "");
					}
				} else {
					logger.info("sendsms-result-null");
				}
				Result r = (Result) XmlUtil.readMessage(Result.class, result2);
				if (null != r) {
					if ("000000".equals(r.getError_code())) {
						result = 0;
					} else {
						result = Integer.valueOf(r.getError_code());
						if (result == 0) {
							result = -501;
						}
						logger.info("sendsms return " + r.getError_code());
					}
				} else {
					logger.info("sendsms-result-xml-null");
				}
			} else {
				logger.debug("send code error,phone is null");
			}

		} catch (Exception e) {
			logger.error("phones：" + phones + "phone:" + phone + ",error ", e);
		}
		return result;
	}
}
