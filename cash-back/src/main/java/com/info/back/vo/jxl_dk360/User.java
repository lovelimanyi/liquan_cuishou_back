package com.info.back.vo.jxl_dk360;

/**
 * 类描述：用户运营商基础信息
 * 创建人：yyf
 * 创建时间：2017/10/30 0030下午 02:51
 */

public class User {
    private String star_level; //用户星级
    private String phone; //电话号码
    private String user_source;// 号码类型
    private String phone_status;//客户状态
    private String reg_time;//入网时间
    private String id_card;//身份证号
    private String package_name;//套餐名称
    private String score;//用户积分
    private String contact_phone;//联系人号码
    private String real_name;//真实姓名
    private String addr;//注册该号码所填写的地址
    private String phone_remain;//当前余额
    private String authentication;//用户实名状态

    public String getStar_level() {
        return star_level;
    }

    public void setStar_level(String star_level) {
        this.star_level = star_level;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser_source() {
        return user_source;
    }

    public void setUser_source(String user_source) {
        this.user_source = user_source;
    }

    public String getPhone_status() {
        return phone_status;
    }

    public void setPhone_status(String phone_status) {
        this.phone_status = phone_status;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhone_remain() {
        return phone_remain;
    }

    public void setPhone_remain(String phone_remain) {
        this.phone_remain = phone_remain;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }
}
