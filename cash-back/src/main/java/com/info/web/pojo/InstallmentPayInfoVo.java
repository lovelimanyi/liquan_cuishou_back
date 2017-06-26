package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
public class InstallmentPayInfoVo {

    private String installmentType; //分期类型
    private Date repayTime; //还款时间
    private BigDecimal totalRepay; //还款总计
    private BigDecimal serviceCharge; //服务费
    private BigDecimal stagesOwnMoney; //分期本金
    private BigDecimal stagesOverdueMoney; //分期滞纳金

    public String getInstallmentType() {
        return installmentType;
    }

    public void setInstallmentType(String installmentType) {
        this.installmentType = installmentType;
    }

    public Date getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(Date repayTime) {
        this.repayTime = repayTime;
    }

    public BigDecimal getTotalRepay() {
        return totalRepay;
    }

    public void setTotalRepay(BigDecimal totalRepay) {
        this.totalRepay = totalRepay;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public BigDecimal getStagesOwnMoney() {
        return stagesOwnMoney;
    }

    public void setStagesOwnMoney(BigDecimal stagesOwnMoney) {
        this.stagesOwnMoney = stagesOwnMoney;
    }

    public BigDecimal getStagesOverdueMoney() {
        return stagesOverdueMoney;
    }

    public void setStagesOverdueMoney(BigDecimal stagesOverdueMoney) {
        this.stagesOverdueMoney = stagesOverdueMoney;
    }


    public static void main(String[] args)
    {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_YEAR, 2);
        date = calendar.getTime();
        System.out.println(date);
    }
}
