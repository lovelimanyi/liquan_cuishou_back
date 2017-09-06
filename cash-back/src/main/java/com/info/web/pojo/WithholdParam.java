package com.info.web.pojo;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/9/5 0005.
 */
// 代扣参数传递
public class WithholdParam {
    private String userId; // 用户id
    private String repaymentId; //还款id
    private BigDecimal money; // 扣款金额
    private String uuid;
    private String sign;

    public String getRepaymentId() {
        return repaymentId;
    }

    public void setRepaymentId(String repaymentId) {
        this.repaymentId = repaymentId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
