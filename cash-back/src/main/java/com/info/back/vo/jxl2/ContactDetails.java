package com.info.back.vo.jxl2;
/**
 * 呼叫信息
 * @author yyf
 *
 * @version 
 */
public class ContactDetails {
	//电话号码
	private String phone_num;
	//号码归属地
	private String phone_num_loc;
	//呼叫次数
	private Integer call_cnt;
	//呼叫时长
	private Integer call_len;
	//呼出次数
	private Integer call_out_cnt;
	//呼入次数
	private Integer call_in_cnt;
	//短信条数
	private Integer sms_cnt;
	//最早沟通时间
	private String trans_start;
	//最晚沟通时间
	private String trans_end;
	
	
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getPhone_num_loc() {
		return phone_num_loc;
	}
	public void setPhone_num_loc(String phone_num_loc) {
		this.phone_num_loc = phone_num_loc;
	}
	public Integer getCall_cnt() {
		return call_cnt;
	}
	public void setCall_cnt(Integer call_cnt) {
		this.call_cnt = call_cnt;
	}
	public Integer getCall_len() {
		return call_len;
	}
	public void setCall_len(Integer call_len) {
		this.call_len = call_len;
	}
	public Integer getCall_out_cnt() {
		return call_out_cnt;
	}
	public void setCall_out_cnt(Integer call_out_cnt) {
		this.call_out_cnt = call_out_cnt;
	}
	public Integer getCall_in_cnt() {
		return call_in_cnt;
	}
	public void setCall_in_cnt(Integer call_in_cnt) {
		this.call_in_cnt = call_in_cnt;
	}
	public Integer getSms_cnt() {
		return sms_cnt;
	}
	public void setSms_cnt(Integer sms_cnt) {
		this.sms_cnt = sms_cnt;
	}
	public String getTrans_start() {
		return trans_start;
	}
	public void setTrans_start(String trans_start) {
		this.trans_start = trans_start;
	}
	public String getTrans_end() {
		return trans_end;
	}
	public void setTrans_end(String trans_end) {
		this.trans_end = trans_end;
	}
	
}
