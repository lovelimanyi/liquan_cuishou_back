package com.info.back.vo.jxl;

import java.util.List;
/**
 * 电商地址分析
 * @author yyf
 *
 * @version
 */
public class DeliverAddress {
    //收货地址
    private String address;
    //经度
    private float lng;
    //纬度
    private float lat;
    //地址类型
    private String predict_addr_type;
    //开始送货时间
    private String begin_date;
    //结束送货时间
    private String end_date;
    //总送货金额
    private float total_amount;
    //总送货次数
    private Integer total_count;
    //收货人列表
    private List<Receiver> receiver;

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public float getLng() {
        return lng;
    }
    public void setLng(float lng) {
        this.lng = lng;
    }
    public float getLat() {
        return lat;
    }
    public void setLat(float lat) {
        this.lat = lat;
    }
    public String getPredict_addr_type() {
        return predict_addr_type;
    }
    public void setPredict_addr_type(String predict_addr_type) {
        this.predict_addr_type = predict_addr_type;
    }
    public String getBegin_date() {
        return begin_date;
    }
    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }
    public String getEnd_date() {
        return end_date;
    }
    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
    public float getTotal_amount() {
        return total_amount;
    }
    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }
    public Integer getTotal_count() {
        return total_count;
    }
    public void setTotal_count(Integer total_count) {
        this.total_count = total_count;
    }
    public List<Receiver> getReceiver() {
        return receiver;
    }
    public void setReceiver(List<Receiver> receiver) {
        this.receiver = receiver;
    }




}
