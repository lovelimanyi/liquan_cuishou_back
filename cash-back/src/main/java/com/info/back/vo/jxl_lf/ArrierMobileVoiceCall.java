package com.info.back.vo.jxl_lf;

import java.util.List;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2017/11/24 0024上午 11:42
 */

public class ArrierMobileVoiceCall {
    private List<CallItems> call_items;
    private String bill_month;
    private String total_size;

    public List<CallItems> getCall_items() {
        return call_items;
    }

    public void setCall_items(List<CallItems> call_items) {
        this.call_items = call_items;
    }

    public String getBill_month() {
        return bill_month;
    }

    public void setBill_month(String bill_month) {
        this.bill_month = bill_month;
    }

    public String getTotal_size() {
        return total_size;
    }

    public void setTotal_size(String total_size) {
        this.total_size = total_size;
    }
}
