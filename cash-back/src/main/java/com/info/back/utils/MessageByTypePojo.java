package com.info.back.utils;



/**
 * Created by Zach on 2017/5/11.
 */
public class MessageByTypePojo extends MessagePojo {

    private String msgTemplateCode;

    private String values;

    private String telephone;

    private String merchantNo;

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getMsgTemplateCode() {
        return msgTemplateCode;
    }

    public void setMsgTemplateCode(String msgTemplateCode) {
        this.msgTemplateCode = msgTemplateCode;
    }
}
