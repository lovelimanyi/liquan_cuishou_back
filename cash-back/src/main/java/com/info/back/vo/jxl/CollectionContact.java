package com.info.back.vo.jxl;

import java.util.List;
/**
 * 联系人名单
 * @author yyf
 *
 * @version
 */
public class CollectionContact {
    //联系人姓名
    private String contact_name;
    //最早出现时间
    private String begin_date;
    //最晚出现时间
    private String end_date;
    //电商送货总数
    private Integer total_count;
    //电商送货总金额
    private float total_amount;
    //呼叫信息统计
    private List<ContactDetails> contact_details;

    public String getContact_name() {
        return contact_name;
    }
    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }
    public String getBegin_date() {
        return begin_date;
    }
    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }
    public String getEnd_date() {
        return end_date;
    }
    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
    public Integer getTotal_count() {
        return total_count;
    }
    public void setTotal_count(Integer total_count) {
        this.total_count = total_count;
    }
    public float getTotal_amount() {
        return total_amount;
    }
    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }
    public List<ContactDetails> getContact_details() {
        return contact_details;
    }
    public void setContact_details(List<ContactDetails> contact_details) {
        this.contact_details = contact_details;
    }


}
