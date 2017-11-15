package com.info.back.vo.jxl_dk360;

/**
 * 类描述：充值记录
 * 创建人：yyf
 * 创建时间：2017/10/30 0030下午 02:50
 */

public class Recharge {
    private String fee; //金额
    private String recharge_time;//充值时间(精确到秒)
    private String recharge_way ;//充值方式

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getRecharge_time() {
        return recharge_time;
    }

    public void setRecharge_time(String recharge_time) {
        this.recharge_time = recharge_time;
    }

    public String getRecharge_way() {
        return recharge_way;
    }

    public void setRecharge_way(String recharge_way) {
        this.recharge_way = recharge_way;
    }
}
