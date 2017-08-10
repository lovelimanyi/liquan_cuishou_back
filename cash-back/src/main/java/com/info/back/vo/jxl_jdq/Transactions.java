package com.info.back.vo.jxl_jdq;

/**
 * 类描述：借了吗  话费信息类
 * 创建人：yyf
 * 创建时间：2017/8/3 0003上午 11:04
 */

public class Transactions {
    //剩余金额
    private String total_amt;
    //更新时间
    private String update_time;
    //剩余金额
    private String pay_amt;
    //套餐及固定费用（元）
    private String plan_amt;
    //统计时间
    private String bill_cycle;
    //本人手机号
    private String cell_phone;

    public String getTotal_amt() {
        return total_amt;
    }

    public void setTotal_amt(String total_amt) {
        this.total_amt = total_amt;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getPay_amt() {
        return pay_amt;
    }

    public void setPay_amt(String pay_amt) {
        this.pay_amt = pay_amt;
    }

    public String getPlan_amt() {
        return plan_amt;
    }

    public void setPlan_amt(String plan_amt) {
        this.plan_amt = plan_amt;
    }

    public String getBill_cycle() {
        return bill_cycle;
    }

    public void setBill_cycle(String bill_cycle) {
        this.bill_cycle = bill_cycle;
    }

    public String getCell_phone() {
        return cell_phone;
    }

    public void setCell_phone(String cell_phone) {
        this.cell_phone = cell_phone;
    }
}
