package com.info.vo.bigAmount;

/**
 * 类描述：还款详情参数
 * 创建人：yyf
 * 创建时间：2017/11/16 0016上午 11:04
 */

public class RepaymentDetail {
    private String createDate;
    private String id;
    private String payId;
    private String returnType;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
