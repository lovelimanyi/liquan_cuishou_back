package com.info.back.vo.jxl_jdq;

/**
 * 类描述：借了吗  通讯记录类
 * 创建人：yyf
 * 创建时间：2017/8/3 0002上午 11:55
 */

public class Calls {
    //开始时间
    private String start_time;
    //更新时间
    private String update_time;
    //通话时间
    private String use_time;
    //话费
    private String subtotal;
    //地点
    private String place;
    //呼叫方式
    private String call_type;
    //类型  主叫，被叫
    private String init_type;
    //其他联系电话
    private String other_cell_phone;
    //手机号
    private String cell_phone;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getUse_time() {
        return use_time;
    }

    public void setUse_time(String use_time) {
        this.use_time = use_time;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCall_type() {
        return call_type;
    }

    public void setCall_type(String call_type) {
        this.call_type = call_type;
    }

    public String getInit_type() {
        return init_type;
    }

    public void setInit_type(String init_type) {
        this.init_type = init_type;
    }

    public String getOther_cell_phone() {
        return other_cell_phone;
    }

    public void setOther_cell_phone(String other_cell_phone) {
        this.other_cell_phone = other_cell_phone;
    }

    public String getCell_phone() {
        return cell_phone;
    }

    public void setCell_phone(String cell_phone) {
        this.cell_phone = cell_phone;
    }
}
