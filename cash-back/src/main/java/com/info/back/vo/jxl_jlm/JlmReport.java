package com.info.back.vo.jxl_jlm;

import java.util.List;

/**
 * 类描述：借了吗  聚信立报告总VO
 * 创建人：yyf
 * 创建时间：2017/8/3 0003上午 11:55
 */

public class JlmReport {
    private String realName;
    private String gender;
    private String idNumber;
    private Integer age;
    //通讯记录列表
    private List<Calls> calls;
    //话费信息列表
    private List<Transactions> transactions;
    //基本信息
    private Basic basic;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Calls> getCalls() {
        return calls;
    }

    public void setCalls(List<Calls> calls) {
        this.calls = calls;
    }

    public List<Transactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transactions> transactions) {
        this.transactions = transactions;
    }

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }
}
