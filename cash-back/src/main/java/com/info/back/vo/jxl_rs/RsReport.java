package com.info.back.vo.jxl_rs;

import java.util.List;

/**
 * 类描述：榕树聚信立基本类
 * 创建人：yyf
 * 创建时间：2017/11/15 0015上午 09:47
 */

public class RsReport {
    private BasicInfo basicInfo;
    private List<CallDetailList> callDetailList;
    private List<SmsDetailList> smsDetailList;
    private List<BillSummaryList> billSummaryList;

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public List<CallDetailList> getCallDetailList() {
        return callDetailList;
    }

    public void setCallDetailList(List<CallDetailList> callDetailList) {
        this.callDetailList = callDetailList;
    }

    public List<SmsDetailList> getSmsDetailList() {
        return smsDetailList;
    }

    public void setSmsDetailList(List<SmsDetailList> smsDetailList) {
        this.smsDetailList = smsDetailList;
    }

    public List<BillSummaryList> getBillSummaryList() {
        return billSummaryList;
    }

    public void setBillSummaryList(List<BillSummaryList> billSummaryList) {
        this.billSummaryList = billSummaryList;
    }
}
