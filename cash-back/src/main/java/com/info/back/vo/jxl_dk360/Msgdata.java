package com.info.back.vo.jxl_dk360;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2017/10/30 0030下午 02:52
 */

public class Msgdata {
    private String trade_way;//短信状态（发送：1；接收：2；未识别状态：3 ）
    private String business_name;//业务类型
    private String send_time;//发送时间
    private String fee;//费用
    private String trade_type;//类型1：短信 2：彩信 3：其他
    private String receiver_phone;//对方号码
    private String trade_addr;//短信地址

    public String getTrade_way() {
        return trade_way;
    }

    public void setTrade_way(String trade_way) {
        this.trade_way = trade_way;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public String getTrade_addr() {
        return trade_addr;
    }

    public void setTrade_addr(String trade_addr) {
        this.trade_addr = trade_addr;
    }
}
