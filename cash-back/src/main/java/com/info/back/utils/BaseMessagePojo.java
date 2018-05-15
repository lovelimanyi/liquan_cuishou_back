package com.info.back.utils;


/**
 * Created by Zach on 2017/5/11.
 */
public class BaseMessagePojo {

    private String msgContent;

    private String msgSource;

    private String msgBussinessId;

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgSource() {
        return msgSource;
    }

    public void setMsgSource(String msgSource) {
        this.msgSource = msgSource;
    }

    public String getMsgBussinessId() {
        return msgBussinessId;
    }

    public void setMsgBussinessId(String msgBussinessId) {
        this.msgBussinessId = msgBussinessId;
    }

}
