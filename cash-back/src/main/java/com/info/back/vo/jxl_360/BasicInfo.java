package com.info.back.vo.jxl_360;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2017/8/16 0016下午 02:58
 */

public class BasicInfo {
    //手机号
    private String phone;
    //运营商编码
    private String operator;
    //运营商中文
    private String operator_zh;
    //号码归属地
    private String phone_location;
    //身份证验证
    private Integer id_card_check;
    //姓名验证
    private Integer name_check;
    //身份证号码
    private String id_card;
    //真实姓名
    private String real_name;
    //每月平均消费
    private float ave_monthly_consumption;
    //当前账户余额
    private float current_balance;
    //注册时间
    private String reg_time;
    //是否联系过紧急联系人1
    private Integer if_call_emergency1;
    //是否联系过紧急联系人2
    private Integer if_call_emergency2;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator_zh() {
        return operator_zh;
    }

    public void setOperator_zh(String operator_zh) {
        this.operator_zh = operator_zh;
    }

    public String getPhone_location() {
        return phone_location;
    }

    public void setPhone_location(String phone_location) {
        this.phone_location = phone_location;
    }

    public Integer getId_card_check() {
        return id_card_check;
    }

    public void setId_card_check(Integer id_card_check) {
        this.id_card_check = id_card_check;
    }

    public Integer getName_check() {
        return name_check;
    }

    public void setName_check(Integer name_check) {
        this.name_check = name_check;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public float getAve_monthly_consumption() {
        return ave_monthly_consumption;
    }

    public void setAve_monthly_consumption(float ave_monthly_consumption) {
        this.ave_monthly_consumption = ave_monthly_consumption;
    }

    public float getCurrent_balance() {
        return current_balance;
    }

    public void setCurrent_balance(float current_balance) {
        this.current_balance = current_balance;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public Integer getIf_call_emergency1() {
        return if_call_emergency1;
    }

    public void setIf_call_emergency1(Integer if_call_emergency1) {
        this.if_call_emergency1 = if_call_emergency1;
    }

    public Integer getIf_call_emergency2() {
        return if_call_emergency2;
    }

    public void setIf_call_emergency2(Integer if_call_emergency2) {
        this.if_call_emergency2 = if_call_emergency2;
    }
}
