package com.info.web.pojo;

import java.io.Serializable;

/**
 * @author Administrator
 * @Description: 商户信息
 * @CreateTime 2018-05-28 上午 11:12
 **/
public class MerchantInfo implements Serializable {

    private String merchantId;

    private String merchantValue;

    private String merchantName;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantValue() {
        return merchantValue;
    }

    public void setMerchantValue(String merchantValue) {
        this.merchantValue = merchantValue;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
