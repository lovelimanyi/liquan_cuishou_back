package com.info.web.pojo;

/**
 * @author Administrator
 * @Description: 淘宝交易信息
 * @CreateTime 2018-09-18 上午 11:59
 **/
public class EcommerceInfo {

    /**
     * 收货人姓名
     */
    private String receiveName;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 手机号
     */
    private String telNumber;

    /**
     * 交易订单总数
     */
    private int tradeCount;

    /**
     * 近3月交易订单总数
     */
    private int tradeCountOf3m;

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public int getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(int tradeCount) {
        this.tradeCount = tradeCount;
    }

    public int getTradeCountOf3m() {
        return tradeCountOf3m;
    }

    public void setTradeCountOf3m(int tradeCountOf3m) {
        this.tradeCountOf3m = tradeCountOf3m;
    }
}
