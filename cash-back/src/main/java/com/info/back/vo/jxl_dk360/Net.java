package com.info.back.vo.jxl_dk360;

/**
 * 类描述：流量值记录
 * 创建人：yyf
 * 创建时间：2017/10/30 0030下午 02:51
 */

public class Net {
    private String fee; //通信费
    private String net_type;//网络类型(4g,3g,2g)
    private String net_way;//上网方式(CMNET等)
    private String preferential_fee;//优惠项/套餐优惠
    private String start_time;//起始时间(精确到秒)
    private String total_time;//总时长
    private String total_traffic;//总流量
    private String trade_addr;//通信地址

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getNet_type() {
        return net_type;
    }

    public void setNet_type(String net_type) {
        this.net_type = net_type;
    }

    public String getNet_way() {
        return net_way;
    }

    public void setNet_way(String net_way) {
        this.net_way = net_way;
    }

    public String getPreferential_fee() {
        return preferential_fee;
    }

    public void setPreferential_fee(String preferential_fee) {
        this.preferential_fee = preferential_fee;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getTotal_time() {
        return total_time;
    }

    public void setTotal_time(String total_time) {
        this.total_time = total_time;
    }

    public String getTotal_traffic() {
        return total_traffic;
    }

    public void setTotal_traffic(String total_traffic) {
        this.total_traffic = total_traffic;
    }

    public String getTrade_addr() {
        return trade_addr;
    }

    public void setTrade_addr(String trade_addr) {
        this.trade_addr = trade_addr;
    }
}
