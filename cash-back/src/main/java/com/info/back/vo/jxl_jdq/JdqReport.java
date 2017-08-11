package com.info.back.vo.jxl_jdq;

import com.info.back.vo.jxl_jlm.*;
import com.info.back.vo.jxl_jlm.Basic;
import com.info.back.vo.jxl_jlm.Calls;
import com.info.back.vo.jxl_jlm.Transactions;

import java.util.List;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2017/8/3 0003下午 03:39
 */

public class JdqReport {
    private String realName;
    private String gender;
    private String idNumber;
    private Integer age;
    //数据来源
    private String datasource;
    //版本号
    private String version;
    //token
    private String token;
    private String juid;
    private List<Nets> nets;
    //短信费用列表
    private List<Smses> smses;

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

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getJuid() {
        return juid;
    }

    public void setJuid(String juid) {
        this.juid = juid;
    }

    public List<Nets> getNets() {
        return nets;
    }

    public void setNets(List<Nets> nets) {
        this.nets = nets;
    }

    public List<Smses> getSmses() {
        return smses;
    }

    public void setSmses(List<Smses> smses) {
        this.smses = smses;
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
