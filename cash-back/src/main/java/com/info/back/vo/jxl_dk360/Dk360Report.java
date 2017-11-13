package com.info.back.vo.jxl_dk360;


import java.util.List;

/**
 * 类描述：360贷款导航聚信立
 * 创建人：yyf
 * 创建时间：2017/10/30 0030下午 02:45
 */

public class Dk360Report {
    private Msg msg;
    private List<Recharge> recharge;
    private List<Bill> bill;
    private Tel tel;
    private List<Net> net;
    private User user;
    private String uptime;

    public Msg getMsg() {
        return msg;
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }

    public List<Recharge> getRecharge() {
        return recharge;
    }

    public void setRecharge(List<Recharge> recharge) {
        this.recharge = recharge;
    }

    public List<Bill> getBill() {
        return bill;
    }

    public void setBill(List<Bill> bill) {
        this.bill = bill;
    }

    public Tel getTel() {
        return tel;
    }

    public void setTel(Tel tel) {
        this.tel = tel;
    }

    public List<Net> getNet() {
        return net;
    }

    public void setNet(List<Net> net) {
        this.net = net;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }
}
