package com.info.back.vo.jxl_rs;

/**通话详情类
 * 类描述：
 * 创建人：yyf
 * 创建时间：2017/11/15 0015上午 10:28
 */

public class CallDetailList {
    private String commType;//
    private String fee;//总费用
    private String peerNumber;//对方号码
    private String dialType;//业务类型
    private String locationType;//地点类型
    private String location;//通话地点
    private String time;//通话时间
    private String durationSec;//通话时长
    private String peerOperator;//对方运营商
    private String dialTypeNum;//

    public String getCommType() {
        return commType;
    }

    public void setCommType(String commType) {
        this.commType = commType;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getPeerNumber() {
        return peerNumber;
    }

    public void setPeerNumber(String peerNumber) {
        this.peerNumber = peerNumber;
    }

    public String getDialType() {
        return dialType;
    }

    public void setDialType(String dialType) {
        this.dialType = dialType;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDurationSec() {
        return durationSec;
    }

    public void setDurationSec(String durationSec) {
        this.durationSec = durationSec;
    }

    public String getPeerOperator() {
        return peerOperator;
    }

    public void setPeerOperator(String peerOperator) {
        this.peerOperator = peerOperator;
    }

    public String getDialTypeNum() {
        return dialTypeNum;
    }

    public void setDialTypeNum(String dialTypeNum) {
        this.dialTypeNum = dialTypeNum;
    }
}
