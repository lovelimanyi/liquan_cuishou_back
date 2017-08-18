package com.info.back.vo.jxl_360;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2017/8/16 0016下午 02:55
 */

public class HeadInfo {
    //报告编号
    private String search_id;
    //报告生成时间
    private String report_time;
    //用户类型:1:实名认证;2:非实名认证;
    private Integer user_type;

    public String getSearch_id() {
        return search_id;
    }

    public void setSearch_id(String search_id) {
        this.search_id = search_id;
    }

    public String getReport_time() {
        return report_time;
    }

    public void setReport_time(String report_time) {
        this.report_time = report_time;
    }

    public Integer getUser_type() {
        return user_type;
    }

    public void setUser_type(Integer user_type) {
        this.user_type = user_type;
    }
}
