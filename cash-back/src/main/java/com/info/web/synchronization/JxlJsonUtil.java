package com.info.web.synchronization;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.info.back.vo.jxl.ContactList;

public class JxlJsonUtil {
	/**
	 * 解析报告
	 * @param jxlDetail
	 * @return
	 */
	public static List<ContactList> operaJxlDetail(String jxlDetail) {
		if(StringUtils.isNotBlank(jxlDetail)){
			List<ContactList> contactList = new ArrayList<ContactList>();
			try{
				JSONObject jxlDetailObject = JSONObject.parseObject(jxlDetail);
				JSONObject jsl0 = JSONObject.parseObject(String.valueOf(jxlDetailObject.get("report_data")));
				JSONArray conarr = null;
				if (jsl0 == null){
					conarr = jxlDetailObject.getJSONArray("contact_list");
				}else{
					conarr = jsl0.getJSONArray("contact_list");
				}

				for(int i=0;i<conarr.size();i++){
					JSONObject jobj = conarr.getJSONObject(i);
					ContactList contact = JSONObject.toJavaObject(jobj, ContactList.class);
					contactList.add(contact);
				}
				return contactList;
			}catch(Exception e){
				return null;
			}
		}
		return null;
	}

	public static String getCallLen(Object callLen){
		String callL = String.valueOf(callLen);
		if(StringUtils.isNotBlank(callL)){
			if(callL.contains(".")){
				callL = callL.substring(0, callL.indexOf(".")+2);
				return callL;
			}
		}
		return "0";
	}
	public static void main(String[] args) {
		
		System.out.println(getCallLen("1.9"));
	}
}
