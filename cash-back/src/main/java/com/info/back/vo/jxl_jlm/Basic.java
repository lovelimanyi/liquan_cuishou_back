package com.info.back.vo.jxl_jlm;

/**
 * 类描述：借了吗  基本信息类
 * 创建人：yyf
 * 创建时间：2017/8/3 0003上午 11:44
 */

public class Basic {
    //入网时间
    private String reg_time;
    //身份证
    private String idcard;
    //姓名
    private String real_name;
    //手机号
    private String cell_phone;

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getCell_phone() {
        return cell_phone;
    }

    public void setCell_phone(String cell_phone) {
        this.cell_phone = cell_phone;
    }
}
