package com.info.back.vo.jxl_360;

/**
 * 类描述：通话记录类
 * 创建人：yyf
 * 创建时间：2017/8/16 0016下午 04:03
 */

public class CallLog {
    //号码
    private String phone;
    //号码归属地
    private String phone_location;
    //互联网标识
    private String phone_info;
    //类别标签
    private String phone_label;
    //首次联系时间
    private String first_contact_date;
    //最后联系时间
    private String last_contact_date;
    //通话时长
    private Integer talk_seconds;
    //通话次数
    private Integer talk_cnt;
    //主叫时长
    private Integer call_seconds;
    //主叫次数
    private Integer call_cnt;
    //被叫时长
    private Integer called_seconds;
    //被叫次数
    private Integer called_cnt;
    //短信总数
    private Integer msg_cnt;
    //发送短信数
    private Integer send_cnt;
    //接收短信数
    private Integer receive_cnt;
    //未识别状态短信数
    private Integer unknown_cnt;
    //近一周联系次数
    private Integer contact_1w;
    //近一个月联系次数
    private Integer contact_1m;
    //近三个月联系次数
    private Integer contact_3m;
    //凌晨联系次数
    private Integer contact_early_morning;
    //早晨联系次数
    private Integer contact_morning;
    //上午联系次数
    private Integer contact_noon;
    //下午联系次数
    private Integer contact_afternoon;
    //夜晚联系次数
    private Integer contact_eveing;
    //深夜联系次数
    private Integer contact_night;
    //工作日联系次数
    private Integer contact_weekday;
    //周末联系次数
    private Integer contact_weekend;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone_location() {
        return phone_location;
    }

    public void setPhone_location(String phone_location) {
        this.phone_location = phone_location;
    }

    public String getPhone_info() {
        return phone_info;
    }

    public void setPhone_info(String phone_info) {
        this.phone_info = phone_info;
    }

    public String getPhone_label() {
        return phone_label;
    }

    public void setPhone_label(String phone_label) {
        this.phone_label = phone_label;
    }

    public String getFirst_contact_date() {
        return first_contact_date;
    }

    public void setFirst_contact_date(String first_contact_date) {
        this.first_contact_date = first_contact_date;
    }

    public String getLast_contact_date() {
        return last_contact_date;
    }

    public void setLast_contact_date(String last_contact_date) {
        this.last_contact_date = last_contact_date;
    }

    public Integer getTalk_seconds() {
        return talk_seconds;
    }

    public void setTalk_seconds(Integer talk_seconds) {
        this.talk_seconds = talk_seconds;
    }

    public Integer getTalk_cnt() {
        return talk_cnt;
    }

    public void setTalk_cnt(Integer talk_cnt) {
        this.talk_cnt = talk_cnt;
    }

    public Integer getCall_seconds() {
        return call_seconds;
    }

    public void setCall_seconds(Integer call_seconds) {
        this.call_seconds = call_seconds;
    }

    public Integer getCall_cnt() {
        return call_cnt;
    }

    public void setCall_cnt(Integer call_cnt) {
        this.call_cnt = call_cnt;
    }

    public Integer getCalled_seconds() {
        return called_seconds;
    }

    public void setCalled_seconds(Integer called_seconds) {
        this.called_seconds = called_seconds;
    }

    public Integer getCalled_cnt() {
        return called_cnt;
    }

    public void setCalled_cnt(Integer called_cnt) {
        this.called_cnt = called_cnt;
    }

    public Integer getMsg_cnt() {
        return msg_cnt;
    }

    public void setMsg_cnt(Integer msg_cnt) {
        this.msg_cnt = msg_cnt;
    }

    public Integer getSend_cnt() {
        return send_cnt;
    }

    public void setSend_cnt(Integer send_cnt) {
        this.send_cnt = send_cnt;
    }

    public Integer getReceive_cnt() {
        return receive_cnt;
    }

    public void setReceive_cnt(Integer receive_cnt) {
        this.receive_cnt = receive_cnt;
    }

    public Integer getUnknown_cnt() {
        return unknown_cnt;
    }

    public void setUnknown_cnt(Integer unknown_cnt) {
        this.unknown_cnt = unknown_cnt;
    }

    public Integer getContact_1w() {
        return contact_1w;
    }

    public void setContact_1w(Integer contact_1w) {
        this.contact_1w = contact_1w;
    }

    public Integer getContact_1m() {
        return contact_1m;
    }

    public void setContact_1m(Integer contact_1m) {
        this.contact_1m = contact_1m;
    }

    public Integer getContact_3m() {
        return contact_3m;
    }

    public void setContact_3m(Integer contact_3m) {
        this.contact_3m = contact_3m;
    }

    public Integer getContact_early_morning() {
        return contact_early_morning;
    }

    public void setContact_early_morning(Integer contact_early_morning) {
        this.contact_early_morning = contact_early_morning;
    }

    public Integer getContact_morning() {
        return contact_morning;
    }

    public void setContact_morning(Integer contact_morning) {
        this.contact_morning = contact_morning;
    }

    public Integer getContact_noon() {
        return contact_noon;
    }

    public void setContact_noon(Integer contact_noon) {
        this.contact_noon = contact_noon;
    }

    public Integer getContact_afternoon() {
        return contact_afternoon;
    }

    public void setContact_afternoon(Integer contact_afternoon) {
        this.contact_afternoon = contact_afternoon;
    }

    public Integer getContact_eveing() {
        return contact_eveing;
    }

    public void setContact_eveing(Integer contact_eveing) {
        this.contact_eveing = contact_eveing;
    }

    public Integer getContact_night() {
        return contact_night;
    }

    public void setContact_night(Integer contact_night) {
        this.contact_night = contact_night;
    }

    public Integer getContact_weekday() {
        return contact_weekday;
    }

    public void setContact_weekday(Integer contact_weekday) {
        this.contact_weekday = contact_weekday;
    }

    public Integer getContact_weekend() {
        return contact_weekend;
    }

    public void setContact_weekend(Integer contact_weekend) {
        this.contact_weekend = contact_weekend;
    }
}
