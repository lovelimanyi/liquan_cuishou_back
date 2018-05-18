package com.info.web.pojo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EstimateOrder {
    public static final Map<Integer,String> ORDER_AGE_MAP = new HashMap<>();
    static{
        ORDER_AGE_MAP.put(3,"S1");
        ORDER_AGE_MAP.put(4,"S2");
        ORDER_AGE_MAP.put(5,"M1-M2");
        ORDER_AGE_MAP.put(6,"M2-M3");
        ORDER_AGE_MAP.put(7,"M3+");
        ORDER_AGE_MAP.put(8,"M6+");
        ORDER_AGE_MAP.put(11,"F-M1");
        ORDER_AGE_MAP.put(12,"F-M2");
        ORDER_AGE_MAP.put(13,"F-M3");
        ORDER_AGE_MAP.put(16,"F-M6");
    }
    private Long id;
    private Date overDate;
    private Integer orderCount;
    private Long amountTotal;
    private Date collectionDate;
    private Long collectionCompanyId;
    private Integer orderAge;
    private Integer estimateOrderCount;
    private Long estimateAmountCount;
    private Date createTime;
    private Byte orderType;//1 大额  2 小额
    private Integer realCollectionOrderCount;
    private Integer oldCollectionRate;
    private Integer oldCollectionAmountRate;

    public Integer getOldCollectionAmountRate() {
        return oldCollectionAmountRate;
    }

    public void setOldCollectionAmountRate(Integer oldCollectionAmountRate) {
        this.oldCollectionAmountRate = oldCollectionAmountRate;
    }

    public Integer getOldCollectionRate() {
        return oldCollectionRate;
    }

    public void setOldCollectionRate(Integer oldCollectionRate) {
        this.oldCollectionRate = oldCollectionRate;
    }

    public Integer getRealCollectionOrderCount() {
        return realCollectionOrderCount;
    }

    public void setRealCollectionOrderCount(Integer realCollectionOrderCount) {
        this.realCollectionOrderCount = realCollectionOrderCount;
    }

    public Byte getOrderType() {
        return orderType;
    }

    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOverDate() {
        return overDate;
    }

    public void setOverDate(Date overDate) {
        this.overDate = overDate;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Long getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(Long amountTotal) {
        this.amountTotal = amountTotal;
    }

    public Date getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }

    public Long getCollectionCompanyId() {
        return collectionCompanyId;
    }

    public void setCollectionCompanyId(Long collectionCompanyId) {
        this.collectionCompanyId = collectionCompanyId;
    }

    public Integer getOrderAge() {
        return orderAge;
    }

    public void setOrderAge(Integer orderAge) {
        this.orderAge = orderAge;
    }

    public Integer getEstimateOrderCount() {
        return estimateOrderCount;
    }

    public void setEstimateOrderCount(Integer estimateOrderCount) {
        this.estimateOrderCount = estimateOrderCount;
    }

    public Long getEstimateAmountCount() {
        return estimateAmountCount;
    }

    public void setEstimateAmountCount(Long estimateAmountCount) {
        this.estimateAmountCount = estimateAmountCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
