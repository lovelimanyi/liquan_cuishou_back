package com.info.back.vo.jxl_360;

import java.util.List;

/**
 * 类描述：融360聚信立总VO
 * 创建人：yyf
 * 创建时间：2017/8/16 0016下午 02:53
 */

public class Rong360Report {
    //订单编号
//    private String order_no;
//    //下载地址
//    private String download_url;
//    //报告头部信息
//    private HeadInfo head_info;
    //用户输入信息
    private InputInfo input_info;
    //用户基本信息
    private BasicInfo basic_info;
//    //危险性分析
//    private RiskAnalysis risk_analysis;
//    //用户画像：用户通话分析
//    private UserPortrait user_portrait;
    //紧急联系人分析
    private List<EmergencyAnalysis> emergency_analysis;
//    //联系人所在地区分析
//    private AreaAnalysis area_analysis;
//    //特殊号码种类分析
//    private SpecialCate special_cate;
//    //运营商按月消费分析
//    private MonthlyConsumption monthly_consumption;
//    //外地呼叫记录分析（用户非归属地通话）
//    private TripAnalysis trip_analysis;
    //通话记录:通话号码分析
    private List<CallLog> call_log;


    public InputInfo getInput_info() {
        return input_info;
    }

    public void setInput_info(InputInfo input_info) {
        this.input_info = input_info;
    }

    public BasicInfo getBasic_info() {
        return basic_info;
    }

    public void setBasic_info(BasicInfo basic_info) {
        this.basic_info = basic_info;
    }

    public List<EmergencyAnalysis> getEmergency_analysis() {
        return emergency_analysis;
    }

    public void setEmergency_analysis(List<EmergencyAnalysis> emergency_analysis) {
        this.emergency_analysis = emergency_analysis;
    }

    public List<CallLog> getCall_log() {
        return call_log;
    }

    public void setCall_log(List<CallLog> call_log) {
        this.call_log = call_log;
    }
}
