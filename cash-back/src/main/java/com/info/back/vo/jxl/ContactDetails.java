package com.info.back.vo.jxl;

/**
 * 通话详情
 * @author duj
 *
 * @version 2016-12-24
 */
public class ContactDetails {
	private Integer sms_cnt;
	private String phone_num;
	private String phone_num_loc;
	private String trans_start;
	private Integer call_in_cnt;
	private Integer call_cnt;
	private Integer call_len;
	private Integer call_out_cnt;
	private String trans_end;

	public Integer getSms_cnt() {
		return sms_cnt;
	}

	public void setSms_cnt(Integer smsCnt) {
		sms_cnt = smsCnt;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phoneNum) {
		phone_num = phoneNum;
	}

	public String getPhone_num_loc() {
		return phone_num_loc;
	}

	public void setPhone_num_loc(String phoneNumLoc) {
		phone_num_loc = phoneNumLoc;
	}

	public String getTrans_start() {
		return trans_start;
	}

	public void setTrans_start(String transStart) {
		trans_start = transStart;
	}

	public Integer getCall_in_cnt() {
		return call_in_cnt;
	}

	public void setCall_in_cnt(Integer callInCnt) {
		call_in_cnt = callInCnt;
	}

	public Integer getCall_cnt() {
		return call_cnt;
	}

	public void setCall_cnt(Integer callCnt) {
		call_cnt = callCnt;
	}

	public Integer getCall_len() {
		return call_len;
	}

	public void setCall_len(Integer callLen) {
		call_len = callLen;
	}

	public Integer getCall_out_cnt() {
		return call_out_cnt;
	}

	public void setCall_out_cnt(Integer callOutCnt) {
		call_out_cnt = callOutCnt;
	}

	public String getTrans_end() {
		return trans_end;
	}

	public void setTrans_end(String transEnd) {
		trans_end = transEnd;
	}

}
