package com.info.back.vo.jxl;

/**
 * 联系人通话相关信息
 * @author duj
 *
 * @version 2016-12-24
 */
public class ContactList {
	private Integer contact_noon;
	private String phone_num_loc;
	private Integer contact_3m;
	private Integer contact_1m;
	private Integer contact_1w;
	private String p_relation;
	private String phone_num;
	private String contact_name;
	private Integer call_in_cnt;
	private Integer call_out_cnt;
	private float call_out_len;
	private Integer contact_holiday;
	private String needs_type;
	private Integer contact_weekday;
	private Integer contact_afternoon;
	private float call_len;
	private Integer contact_early_morning;
	private Integer contact_night;
	private Integer contact_3m_plus;
	private Integer call_cnt;
	private float call_in_len;
	private boolean contact_all_day;
	private Integer contact_morning;
	private Integer contact_weekend;

	public Integer getContact_noon() {
		return contact_noon;
	}

	public void setContact_noon(Integer contactNoon) {
		contact_noon = contactNoon;
	}

	public String getPhone_num_loc() {
		return phone_num_loc;
	}

	public void setPhone_num_loc(String phoneNumLoc) {
		phone_num_loc = phoneNumLoc;
	}

	public Integer getContact_3m() {
		return contact_3m;
	}

	public void setContact_3m(Integer contact_3m) {
		this.contact_3m = contact_3m;
	}

	public Integer getContact_1m() {
		return contact_1m;
	}

	public void setContact_1m(Integer contact_1m) {
		this.contact_1m = contact_1m;
	}

	public Integer getContact_1w() {
		return contact_1w;
	}

	public void setContact_1w(Integer contact_1w) {
		this.contact_1w = contact_1w;
	}

	public String getP_relation() {
		return p_relation;
	}

	public void setP_relation(String pRelation) {
		p_relation = pRelation;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phoneNum) {
		phone_num = phoneNum;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contactName) {
		contact_name = contactName;
	}

	public Integer getCall_in_cnt() {
		return call_in_cnt;
	}

	public void setCall_in_cnt(Integer callInCnt) {
		call_in_cnt = callInCnt;
	}

	public Integer getCall_out_cnt() {
		return call_out_cnt;
	}

	public void setCall_out_cnt(Integer callOutCnt) {
		call_out_cnt = callOutCnt;
	}

	public float getCall_out_len() {
		return call_out_len;
	}

	public void setCall_out_len(float callOutLen) {
		call_out_len = callOutLen;
	}

	public Integer getContact_holiday() {
		return contact_holiday;
	}

	public void setContact_holiday(Integer contactHoliday) {
		contact_holiday = contactHoliday;
	}

	public String getNeeds_type() {
		return needs_type;
	}

	public void setNeeds_type(String needsType) {
		needs_type = needsType;
	}

	public Integer getContact_weekday() {
		return contact_weekday;
	}

	public void setContact_weekday(Integer contactWeekday) {
		contact_weekday = contactWeekday;
	}

	public Integer getContact_afternoon() {
		return contact_afternoon;
	}

	public void setContact_afternoon(Integer contactAfternoon) {
		contact_afternoon = contactAfternoon;
	}

	public float getCall_len() {
		return call_len;
	}

	public void setCall_len(float callLen) {
		call_len = callLen;
	}

	public Integer getContact_early_morning() {
		return contact_early_morning;
	}

	public void setContact_early_morning(Integer contactEarlyMorning) {
		contact_early_morning = contactEarlyMorning;
	}

	public Integer getContact_night() {
		return contact_night;
	}

	public void setContact_night(Integer contactNight) {
		contact_night = contactNight;
	}

	public Integer getContact_3m_plus() {
		return contact_3m_plus;
	}

	public void setContact_3m_plus(Integer contact_3mPlus) {
		contact_3m_plus = contact_3mPlus;
	}

	public Integer getCall_cnt() {
		return call_cnt;
	}

	public void setCall_cnt(Integer callCnt) {
		call_cnt = callCnt;
	}

	public float getCall_in_len() {
		return call_in_len;
	}

	public void setCall_in_len(float callInLen) {
		call_in_len = callInLen;
	}

	public boolean isContact_all_day() {
		return contact_all_day;
	}

	public void setContact_all_day(boolean contactAllDay) {
		contact_all_day = contactAllDay;
	}

	public Integer getContact_morning() {
		return contact_morning;
	}

	public void setContact_morning(Integer contactMorning) {
		contact_morning = contactMorning;
	}

	public Integer getContact_weekend() {
		return contact_weekend;
	}

	public void setContact_weekend(Integer contactWeekend) {
		contact_weekend = contactWeekend;
	}

	@Override
	public String toString() {
		return "ContactList [call_cnt=" + call_cnt + ", call_in_cnt="
				+ call_in_cnt + ", call_in_len=" + call_in_len + ", call_len="
				+ call_len + ", call_out_cnt=" + call_out_cnt
				+ ", call_out_len=" + call_out_len + ", contact_1m="
				+ contact_1m + ", contact_1w=" + contact_1w + ", contact_3m="
				+ contact_3m + ", contact_3m_plus=" + contact_3m_plus
				+ ", contact_afternoon=" + contact_afternoon
				+ ", contact_all_day=" + contact_all_day
				+ ", contact_early_morning=" + contact_early_morning
				+ ", contact_holiday=" + contact_holiday + ", contact_morning="
				+ contact_morning + ", contact_name=" + contact_name
				+ ", contact_night=" + contact_night + ", contact_noon="
				+ contact_noon + ", contact_weekday=" + contact_weekday
				+ ", contact_weekend=" + contact_weekend + ", needs_type="
				+ needs_type + ", p_relation=" + p_relation + ", phone_num="
				+ phone_num + ", phone_num_loc=" + phone_num_loc + "]";
	}

}
