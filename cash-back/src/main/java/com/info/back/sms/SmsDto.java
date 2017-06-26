package com.info.back.sms;

public class SmsDto {
	
	private String app_key;		//在短信用户后台获取或者与管理员联系
	private String time_stamp;	//格式为yyyyMMddHHmmss
	private String nonce_str;	//短信平台用户名
	private String sign;		
	
	private String batch_num;	//批次号,不能重复
	private String mission_num;	//任务号
	private String dest_id;		//接收手机
	private String content;		//短信内容
	private String sms_type;	//短信类型 verify_code:验证码  notice:通知  advert:营销类
	
	public String getApp_key() {
		return app_key;
	}
	public void setApp_key(String appKey) {
		app_key = appKey;
	}
	public String getTime_stamp() {
		return time_stamp;
	}
	public void setTime_stamp(String timeStamp) {
		time_stamp = timeStamp;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonceStr) {
		nonce_str = nonceStr;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getBatch_num() {
		return batch_num;
	}
	public void setBatch_num(String batchNum) {
		batch_num = batchNum;
	}
	public String getMission_num() {
		return mission_num;
	}
	public void setMission_num(String missionNum) {
		mission_num = missionNum;
	}
	public String getDest_id() {
		return dest_id;
	}
	public void setDest_id(String destId) {
		dest_id = destId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSms_type() {
		return sms_type;
	}
	public void setSms_type(String smsType) {
		sms_type = smsType;
	}
	@Override
	public String toString() {
		return "SmsDto [app_key=" + app_key + ", batch_num=" + batch_num
				+ ", content=" + content + ", dest_id=" + dest_id
				+ ", mission_num=" + mission_num + ", nonce_str=" + nonce_str
				+ ", sign=" + sign + ", sms_type=" + sms_type + ", time_stamp="
				+ time_stamp + "]";
	}

}
