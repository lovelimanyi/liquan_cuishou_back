package com.info.back.vo.jxl_rs;

/**账单类
 * 类描述：
 * 创建人：yyf
 * 创建时间：2017/11/15 0015上午 10:47
 */

public class BillSummaryList {
    private String voicCallFee;//通话费用
    private String totalFee;//总费用
    private String vasFee;//增值服务费
    private String dataFee;//数据流量费
    private String discount;//减免费用
    private String basicFee;//
    private String billDate;//账单时间
    private String actualFee;//月实际费用
    private String smsFee;//短信费用

    public String getVoicCallFee() {
        return voicCallFee;
    }

    public void setVoicCallFee(String voicCallFee) {
        this.voicCallFee = voicCallFee;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getVasFee() {
        return vasFee;
    }

    public void setVasFee(String vasFee) {
        this.vasFee = vasFee;
    }

    public String getDataFee() {
        return dataFee;
    }

    public void setDataFee(String dataFee) {
        this.dataFee = dataFee;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getBasicFee() {
        return basicFee;
    }

    public void setBasicFee(String basicFee) {
        this.basicFee = basicFee;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getActualFee() {
        return actualFee;
    }

    public void setActualFee(String actualFee) {
        this.actualFee = actualFee;
    }

    public String getSmsFee() {
        return smsFee;
    }

    public void setSmsFee(String smsFee) {
        this.smsFee = smsFee;
    }
}
