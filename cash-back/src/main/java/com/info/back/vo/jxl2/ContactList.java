package com.info.back.vo.jxl2;
/**
 * 运营商联系人通话详情
 * @author yyf
 *
 * @version 
 */
public class ContactList {
	//号码
	private String phone_num;
	//号码归属地
	private String phone_num_loc;
	//号码标注
	private String contact_name;
	//需求类别
	private String needs_type;
	//通话次数
	private Integer call_cnt;
	//通话时长
	private float call_len;
	//呼出次数
	private Integer call_out_cnt;
	//呼出时间
	private float call_out_len;
	//呼入次数
	private Integer call_in_cnt;
	//呼入时间
	private float call_in_len;
	//关系推测
	private String p_relation;
	//最近一周联系次数
	private Integer contact_1w;
	//最近一月联系次数
	private Integer contact_1m;
	//最近三月联系次数
	private Integer contact_3m;
	//三个月以上联系次数
	private Integer contact_3m_plus;
	//凌晨联系次数
	private Integer contact_early_morning;
	//上午联系次数
	private Integer contact_morning;
	//中午联系次数
	private Integer contact_noon;
	//下午联系次数
	private Integer contact_afternoon;
	//晚上联系次数
	private Integer contact_night;
	//是否全天联系
	private boolean contact_all_day;
	//周中联系次数
	private Integer contact_weekend;
	//周末联系次数
	private Integer contact_weekday;
	//节假日联系次数
	private Integer contact_holiday;
	
	public String getP_relation() {
		return p_relation;
	}
	public void setP_relation(String p_relation) {
		this.p_relation = p_relation;
	}
	public Integer getContact_noon() {
		return contact_noon;
	}
	public void setContact_noon(Integer contact_noon) {
		this.contact_noon = contact_noon;
	}
	public String getPhone_num_loc() {
		return phone_num_loc;
	}
	public void setPhone_num_loc(String phone_num_loc) {
		this.phone_num_loc = phone_num_loc;
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
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public Integer getCall_in_cnt() {
		return call_in_cnt;
	}
	public void setCall_in_cnt(Integer call_in_cnt) {
		this.call_in_cnt = call_in_cnt;
	}
	public Integer getCall_out_cnt() {
		return call_out_cnt;
	}
	public void setCall_out_cnt(Integer call_out_cnt) {
		this.call_out_cnt = call_out_cnt;
	}
	public float getCall_out_len() {
		return call_out_len;
	}
	public void setCall_out_len(float call_out_len) {
		this.call_out_len = call_out_len;
	}
	public Integer getContact_holiday() {
		return contact_holiday;
	}
	public void setContact_holiday(Integer contact_holiday) {
		this.contact_holiday = contact_holiday;
	}
	public String getNeeds_type() {
		return needs_type;
	}
	public void setNeeds_type(String needs_type) {
		this.needs_type = needs_type;
	}
	public Integer getContact_weekday() {
		return contact_weekday;
	}
	public void setContact_weekday(Integer contact_weekday) {
		this.contact_weekday = contact_weekday;
	}
	public Integer getContact_afternoon() {
		return contact_afternoon;
	}
	public void setContact_afternoon(Integer contact_afternoon) {
		this.contact_afternoon = contact_afternoon;
	}
	public float getCall_len() {
		return call_len;
	}
	public void setCall_len(float call_len) {
		this.call_len = call_len;
	}
	public Integer getContact_early_morning() {
		return contact_early_morning;
	}
	public void setContact_early_morning(Integer contact_early_morning) {
		this.contact_early_morning = contact_early_morning;
	}
	public Integer getContact_night() {
		return contact_night;
	}
	public void setContact_night(Integer contact_night) {
		this.contact_night = contact_night;
	}
	public Integer getContact_3m_plus() {
		return contact_3m_plus;
	}
	public void setContact_3m_plus(Integer contact_3m_plus) {
		this.contact_3m_plus = contact_3m_plus;
	}
	public Integer getCall_cnt() {
		return call_cnt;
	}
	public void setCall_cnt(Integer call_cnt) {
		this.call_cnt = call_cnt;
	}
	public float getCall_in_len() {
		return call_in_len;
	}
	public void setCall_in_len(float call_in_len) {
		this.call_in_len = call_in_len;
	}
	public boolean isContact_all_day() {
		return contact_all_day;
	}
	public void setContact_all_day(boolean contact_all_day) {
		this.contact_all_day = contact_all_day;
	}
	public Integer getContact_morning() {
		return contact_morning;
	}
	public void setContact_morning(Integer contact_morning) {
		this.contact_morning = contact_morning;
	}
	public Integer getContact_weekend() {
		return contact_weekend;
	}
	public void setContact_weekend(Integer contact_weekend) {
		this.contact_weekend = contact_weekend;
	}
	
	
}
