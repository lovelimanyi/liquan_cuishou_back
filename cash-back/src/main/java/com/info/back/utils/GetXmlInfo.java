package com.info.back.utils;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.info.web.util.DateUtil;

public class GetXmlInfo {
	public String getXmlInfo(String app_key,String app_secret,String nonce_str,
			String dest_id,String title,String content) {
		 	
		//时间戳
		String time_stamp=DateUtil.getDateFormat(new Date(), "yyyyMMddHHmmss");	
		//批次号  以后改成自动的
		String batch_num=new GetBatch_num().getBatch_num(nonce_str,time_stamp);		
		//手机号，可以多个，
		String[] dest_id_array=dest_id.split(",");
		//手机号对应个number
		String mission_num[] = new String [dest_id_array.length];
	
		//短信类别   两种方案1：写逻辑做判断
		//			  2：用户自己填写
		String sms_type="verify_code";	
		//为MD5加密使用
    	String dest_id_md5="";
    	String mission_num_md5="";	
		//拼接XML
	    StringBuilder sb = new StringBuilder();
	    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	    sb.append("<xml>");
	    sb.append("<head>");
	    sb.append("<app_key>"+app_key+"</app_key>");
	    sb.append("<time_stamp>"+time_stamp+"</time_stamp>");
	    sb.append("<nonce_str>"+nonce_str+"</nonce_str>");
	    
	    for(int i=0;i<dest_id_array.length;i++){
	    	//0 代表前面补充0, 8代表长度为8,d 代表参数为正数型       
	    	String str = String.format("%08d", i); 
	    	mission_num[i]="straycat"+str;
	    	dest_id_md5=dest_id_md5+"&dest_id="+dest_id_array[i];
	    	mission_num_md5=mission_num_md5+"&mission_num="+mission_num[i];
	    }
	    //计算sign
	    String md="app_key="+app_key+"&batch_num="+batch_num+"&content="+"【"+title+"】"
	    		+content+dest_id_md5+mission_num_md5+"&nonce_str="+nonce_str+"&sms_type="+
	    		sms_type+"&time_stamp="+time_stamp+"&app_secret="+app_secret;
	

	    String sign=new MD5Util().MD5(md);
//	    System.out.println(sign);
	    sb.append("<sign>"+sign+"</sign>");
	    sb.append("</head>");
	    sb.append("<body>");
	    sb.append("<dests>");
	    //
	    for(int i=0;i<dest_id_array.length;i++){
			sb.append("<dest>");
		    sb.append("<mission_num>"+mission_num[i]+"</mission_num>");
		    sb.append("<dest_id>"+dest_id_array[i]+"</dest_id>");
		    sb.append("</dest>");
		}   
	    sb.append("</dests>");
	    sb.append("<batch_num>"+batch_num+"</batch_num>");
	    sb.append("<sms_type>"+sms_type+"</sms_type>");
	    sb.append("<content>"+"【"+title+"】"+content+"</content>");
	    sb.append("</body>");
	    sb.append("</xml>");
	    return sb.toString();
	}
}

