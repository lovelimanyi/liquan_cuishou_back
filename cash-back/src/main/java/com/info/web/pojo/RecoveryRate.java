package com.info.web.pojo;

import java.util.Date;

/**
 * 类描述：催回率实体类
 * 创建人：yyf
 * 创建时间：2018/6/12 0012上午 10:49
 */

public class RecoveryRate {
    private Long id;
    private Date dispatchTime;
    private String borrowingType;
    private String entryRate;
    private String unentryRate;
    private String oneDay;
    private String twoDays;
    private String threeDays;
    private String fourDays;
    private String fiveDays;
    private String sixDays;
    private String sevenDays;
    private String eightTOTen;
    private String toThirty;
    private String toSixty;
    private String toNinety;
    private String toHundredEight;
    private String overHundredEight;
    private Date createDate;
    private String merchantNo;


    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(Date dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public String getBorrowingType() {
        return borrowingType;
    }

    public void setBorrowingType(String borrowingType) {
        this.borrowingType = borrowingType;
    }

    public String getEntryRate() {
        return entryRate;
    }

    public void setEntryRate(String entryRate) {
        this.entryRate = entryRate;
    }

    public String getUnentryRate() {
        return unentryRate;
    }

    public void setUnentryRate(String unentryRate) {
        this.unentryRate = unentryRate;
    }

    public String getOneDay() {
        return oneDay;
    }

    public void setOneDay(String oneDay) {
        this.oneDay = oneDay;
    }

    public String getTwoDays() {
        return twoDays;
    }

    public void setTwoDays(String twoDays) {
        this.twoDays = twoDays;
    }

    public String getThreeDays() {
        return threeDays;
    }

    public void setThreeDays(String threeDays) {
        this.threeDays = threeDays;
    }

    public String getFourDays() {
        return fourDays;
    }

    public void setFourDays(String fourDays) {
        this.fourDays = fourDays;
    }

    public String getFiveDays() {
        return fiveDays;
    }

    public void setFiveDays(String fiveDays) {
        this.fiveDays = fiveDays;
    }

    public String getSixDays() {
        return sixDays;
    }

    public void setSixDays(String sixDays) {
        this.sixDays = sixDays;
    }

    public String getSevenDays() {
        return sevenDays;
    }

    public void setSevenDays(String sevenDays) {
        this.sevenDays = sevenDays;
    }

    public String getEightTOTen() {
        return eightTOTen;
    }

    public void setEightTOTen(String eightTOTen) {
        this.eightTOTen = eightTOTen;
    }

    public String getToThirty() {
        return toThirty;
    }

    public void setToThirty(String toThirty) {
        this.toThirty = toThirty;
    }

    public String getToSixty() {
        return toSixty;
    }

    public void setToSixty(String toSixty) {
        this.toSixty = toSixty;
    }

    public String getToNinety() {
        return toNinety;
    }

    public void setToNinety(String toNinety) {
        this.toNinety = toNinety;
    }

    public String getToHundredEight() {
        return toHundredEight;
    }

    public void setToHundredEight(String toHundredEight) {
        this.toHundredEight = toHundredEight;
    }

    public String getOverHundredEight() {
        return overHundredEight;
    }

    public void setOverHundredEight(String overHundredEight) {
        this.overHundredEight = overHundredEight;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
