package com.info.back.vo.jxl_360;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2017/8/16 0016下午 03:44
 */

public class EmergencyAnalysis {
    //联系人号码
    private String phone;
    //联系人姓名
    private String name;
    //首次联系时间
    private String first_contact_date;
    //最后联系时间
    private String last_contact_date;
    //最近半年通话时长
    private Integer last_6_talk_seconds;
    //最近半年主叫次数
    private Integer last_6_call_cnt;
    //最近半年被叫次数
    private Integer last_6_called_cnt;
    //最近半年短信总数
    private Integer last_6_msg_cnt;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getLast_6_talk_seconds() {
        return last_6_talk_seconds;
    }

    public void setLast_6_talk_seconds(Integer last_6_talk_seconds) {
        this.last_6_talk_seconds = last_6_talk_seconds;
    }

    public Integer getLast_6_call_cnt() {
        return last_6_call_cnt;
    }

    public void setLast_6_call_cnt(Integer last_6_call_cnt) {
        this.last_6_call_cnt = last_6_call_cnt;
    }

    public Integer getLast_6_called_cnt() {
        return last_6_called_cnt;
    }

    public void setLast_6_called_cnt(Integer last_6_called_cnt) {
        this.last_6_called_cnt = last_6_called_cnt;
    }

    public Integer getLast_6_msg_cnt() {
        return last_6_msg_cnt;
    }

    public void setLast_6_msg_cnt(Integer last_6_msg_cnt) {
        this.last_6_msg_cnt = last_6_msg_cnt;
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
}
