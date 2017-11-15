package com.info.back.vo.jxl_rs;

/**
 * 类描述：短信详情类
 * 创建人：yyf
 * 创建时间：2017/11/15 0015上午 10:29
 */

public class SmsDetailList {
    private String dialType;//
    private String bizName;//
    private String time;//
    private String location;//
    private String peerOperator;//
    private String peerNumber;//
    private String isSms;//
    private String messageLen;//
    private String fee;//

    public String getDialType() {
        return dialType;
    }

    public void setDialType(String dialType) {
        this.dialType = dialType;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPeerOperator() {
        return peerOperator;
    }

    public void setPeerOperator(String peerOperator) {
        this.peerOperator = peerOperator;
    }

    public String getPeerNumber() {
        return peerNumber;
    }

    public void setPeerNumber(String peerNumber) {
        this.peerNumber = peerNumber;
    }

    public String getIsSms() {
        return isSms;
    }

    public void setIsSms(String isSms) {
        this.isSms = isSms;
    }

    public String getMessageLen() {
        return messageLen;
    }

    public void setMessageLen(String messageLen) {
        this.messageLen = messageLen;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
