package com.info.back.vo.jxl_dk360;

/**
 * 类描述：月账单记录
 * 创建人：yyf
 * 创建时间：2017/10/30 0030下午 02:50
 */

public class Bill {

    private String month;//鱼粉
    private String call_pay;//当月话费（单位元）
    private String package_fee;//套菜及固定费用
    private String msg_fee;//额外套餐费-短信
    private String tel_fee;//额外套餐费-通话
    private String net_fee;//额外套餐费-流量
    private String addtional_fee;//增值业务费
    private String preferential_fee;//优惠费
    private String generation_fee;//代收费
    private String other_fee;//其它费用
    private String score;//当月积分

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCall_pay() {
        return call_pay;
    }

    public void setCall_pay(String call_pay) {
        this.call_pay = call_pay;
    }

    public String getPackage_fee() {
        return package_fee;
    }

    public void setPackage_fee(String package_fee) {
        this.package_fee = package_fee;
    }

    public String getMsg_fee() {
        return msg_fee;
    }

    public void setMsg_fee(String msg_fee) {
        this.msg_fee = msg_fee;
    }

    public String getTel_fee() {
        return tel_fee;
    }

    public void setTel_fee(String tel_fee) {
        this.tel_fee = tel_fee;
    }

    public String getNet_fee() {
        return net_fee;
    }

    public void setNet_fee(String net_fee) {
        this.net_fee = net_fee;
    }

    public String getAddtional_fee() {
        return addtional_fee;
    }

    public void setAddtional_fee(String addtional_fee) {
        this.addtional_fee = addtional_fee;
    }

    public String getPreferential_fee() {
        return preferential_fee;
    }

    public void setPreferential_fee(String preferential_fee) {
        this.preferential_fee = preferential_fee;
    }

    public String getGeneration_fee() {
        return generation_fee;
    }

    public void setGeneration_fee(String generation_fee) {
        this.generation_fee = generation_fee;
    }

    public String getOther_fee() {
        return other_fee;
    }

    public void setOther_fee(String other_fee) {
        this.other_fee = other_fee;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
