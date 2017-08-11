package com.info.back.vo.jxl_jdq;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2017/8/3 0003下午 04:14
 */

public class Smses {
    //开始时间
    private String start_time;
    //更新时间
    private String update_time;
    //话费
    private String subtotal;
    //地点
    private String place;
    //类型
    private String init_type;
    //对方联系电话
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
