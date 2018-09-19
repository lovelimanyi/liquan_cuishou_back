package com.info.back.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.info.back.dao.IChannelSwitchingDao;
import com.info.back.dao.IMmanUserInfoDao;
import com.info.back.utils.HttpUtils;
import com.info.back.vo.JxlResponse;
import com.info.back.vo.jxl.*;
import com.info.back.vo.jxl2.JxlUserReport;
import com.info.back.vo.jxl_360.Rong360Report;
import com.info.back.vo.jxl_dk360.Dk360Report;
import com.info.back.vo.jxl_jdq.JdqReport;
import com.info.back.vo.jxl_jlm.JlmReport;
import com.info.back.vo.jxl_lf.LfReport;
import com.info.back.vo.jxl_rs.RsReport;
import com.info.config.PayContents;
import com.info.constant.Constant;
import com.info.web.pojo.ContactInfo;
import com.info.web.pojo.EcommerceInfo;
import com.info.web.pojo.MmanUserInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.info.back.controller.BaseController.MESSAGE;

@Service
public class MmanUserInfoService implements IMmanUserInfoService {

    @Autowired
    private IMmanUserInfoDao mmanUserInfoDao;

    @Override
    public MmanUserInfo getUserInfoById(String id) {
        return mmanUserInfoDao.get(id);
    }

    @Override
    public MmanUserInfo getxjxuser(Long id) {
        return mmanUserInfoDao.getxjxuser(id);
    }

    @Override
    public int saveNotNull(MmanUserInfo mmanUserInfo) {
        return mmanUserInfoDao.saveNotNull(mmanUserInfo);
    }


    @Override
    public String getAddressByIDNumber(String idNumber) {
        return mmanUserInfoDao.getAddressByIDNumber(idNumber);
    }

    @Override
    public List<ContactInfo> getContactInfo(String phoneNum) {
        return mmanUserInfoDao.getContactInfo(phoneNum);
    }

    @Autowired
    private IChannelSwitchingDao channelSwitchingDao;

    /**
     * 解析聚信立报告
     */
    @Override
    public String handleJxl(Model model, String userId) {
        MmanUserInfo userInfo = mmanUserInfoDao.get(userId);
        String phone = userInfo.getUserPhone();
        String ossUrl = channelSwitchingDao.getChannelValue("jxl_oss_url").getChannelValue();
        String url = ossUrl + phone;
        String returnUrl = "";
//        String phone = "18612567487";
//        String url = "http://apigateway.cee10a53e8937498ab6c068afee5df20a.cn-hangzhou.alicontainer.com/api/storage/v1/report/"+phone+"?subtype=rs_detail";

        try {
            String jxlDetail = userInfo.getJxlDetail();
            JxlResponse jxlResponse = HttpUtils.get(url, null);
            if (jxlResponse != null) {
                String jxlType = jxlResponse.getJxlType();
                String result = jxlResponse.getJxlData();
                JSONObject jsonDetail = JSONObject.parseObject(result);
                if (jxlType.equals(Constant.JLM_DEATIL) || jxlType.equals(Constant.FQGJ_DETAIL)) {
                    JlmReport jlmReport = JSONObject.toJavaObject(jsonDetail, JlmReport.class);
                    model.addAttribute("calls", jlmReport.getCalls());
                    model.addAttribute("transactions", jlmReport.getTransactions());
                    model.addAttribute("basic", jlmReport.getBasic());
                    //返回 借了吗或分期管家聚信立报告页面
                    returnUrl = "mycollectionorder/jlmReport";
                } else if (jxlType.equals(Constant.R360_DETAIL)) {
                    Rong360Report rong360Report = JSONObject.toJavaObject(jsonDetail, Rong360Report.class);
                    //返回 融360聚信立报告页面
                    model.addAttribute("inputInfo", rong360Report.getInput_info());
                    model.addAttribute("basicInfo", rong360Report.getBasic_info());
                    model.addAttribute("emergencyAnalysis", rong360Report.getEmergency_analysis());
                    model.addAttribute("callLog", rong360Report.getCall_log());
                    returnUrl = "mycollectionorder/rong360Report";
                } else if (jxlType.equals(Constant.JXL_DETAIL)) {
                    handleCashmanJxl(result, model);
                    returnUrl = "mycollectionorder/jxlReport";
                } else if (jxlType.equals(Constant.JDQ_DETAIL)) {
                    JdqReport jdqReport = JSONObject.toJavaObject(jsonDetail, JdqReport.class);
                    model.addAttribute("calls", jdqReport.getCalls());
                    model.addAttribute("transactions", jdqReport.getTransactions());
                    model.addAttribute("basic", jdqReport.getBasic());
                    model.addAttribute("smses", jdqReport.getSmses());
                    model.addAttribute("datasource", jdqReport.getDatasource());
                    //返回 借点钱聚信立报告页面
                    returnUrl = "mycollectionorder/jdqReport";
                } else if (jxlType.equals(Constant.DK360_DETAIL)) {
                    Dk360Report dk360Report = JSONObject.toJavaObject(jsonDetail, Dk360Report.class);
                    model.addAttribute("user", dk360Report.getUser());
                    model.addAttribute("teleData", dk360Report.getTel().getTeldata());
                    returnUrl = "mycollectionorder/dk360Report";
                } else if (jxlType.equals(Constant.RS_DETAIL)) {
                    RsReport rsReport = JSONObject.toJavaObject(jsonDetail, RsReport.class);
                    model.addAttribute("basicInfo", rsReport.getBasicInfo());
                    model.addAttribute("billSummary", rsReport.getBillSummaryList());
                    model.addAttribute("callDetail", rsReport.getCallDetailList());
                    model.addAttribute("smsDetail", rsReport.getSmsDetailList());
                    returnUrl = "mycollectionorder/rsReport";
                } else if (jxlType.equals(Constant.LF_DETAIL)) {
                    LfReport lfReport = JSONObject.toJavaObject(jsonDetail, LfReport.class);
                    model.addAttribute("basicInfo", lfReport.getCarrier_mobile_basic());
                    model.addAttribute("call", lfReport.getCarrier_mobile_voice_call());
                    returnUrl = "mycollectionorder/lfReport";
                } else {
                    //其他情况暂时先返回原始聚信立报告页面
                    returnUrl = localJXLReport(jxlDetail, model, userInfo);
                }
            } else {
                returnUrl = localJXLReport(jxlDetail, model, userInfo);
            }
        } catch (Exception e) {
            model.addAttribute(MESSAGE, "聚信立请求超时！");
            returnUrl = "mycollectionorder/jxlReport";
        }
        model.addAttribute("name", userInfo.getRealname());
        model.addAttribute("gender", userInfo.getUserSex());
        model.addAttribute("idNumber", userInfo.getIdNumber());
        model.addAttribute("age", userInfo.getUserAge());
        return returnUrl;
    }

    @Override
    public void updateUserPhonesByUserId(MmanUserInfo info) {
        mmanUserInfoDao.updateUserPhonesByUserId(info);
    }

    @Override
    public List<EcommerceInfo> getEconmerceInfo(HashMap<String, Object> map) {
        List<EcommerceInfo> list = new ArrayList<>(4);
        String url = PayContents.GXB_ECOMMERCE_INFOS;
        String phone = map.get("phone") == null ? null : map.get("phone").toString();
        String userId = map.get("userId") == null ? null : map.get("userId").toString();
        if (StringUtils.isNotEmpty(phone)) {
            url += "?phone=" + phone;
        } else if (StringUtils.isNotEmpty(userId)) {
            url += "?userId=" + userId;
        } else {
            throw new RuntimeException("获取用户电商信息参数缺失");
        }
        String res = HttpUtils.doGet(url, null);
        JSONObject jsonObject = JSONObject.parseObject(res);
        String data = jsonObject.getString("data");
        if (data == null) {
            String newUrl = PayContents.GXB_ECOMMERCE_INFOS + "?userId=" + userId;
            String result = HttpUtils.doGet(newUrl, null);
            JSONObject jsonResult = JSONObject.parseObject(result);
            String jsonData = jsonResult.getString("data");
            if (jsonData == null) {
                return null;
            } else {
                data = jsonData;
            }
        }
        String authResultData = JSONObject.parseObject(data).getString("authResult");
        String reportSummaryData = JSONObject.parseObject(authResultData).getString("reportSummary");
        String taobaoAddressListData = JSONObject.parseObject(reportSummaryData).getString("taobaoAddressList");
        if (StringUtils.isEmpty(taobaoAddressListData)) {
            return null;
        }
        JSONArray objects = JSONObject.parseArray(taobaoAddressListData);
        for (int i = 0; i < objects.size(); i++) {
            EcommerceInfo info = JSONObject.parseObject(objects.getString(i), EcommerceInfo.class);
            list.add(info);
        }
        return list;
    }


    @Override
    public MmanUserInfo getUserInfoAccordId(String id) {
        return mmanUserInfoDao.getUserInfoById(id);
    }

    /**
     * 本地数据库聚信立解析
     */
    private String localJXLReport(String jxlDetail, Model model, MmanUserInfo userInfo) {
        String returnUrl = "";
        if (StringUtils.isNotBlank(jxlDetail)) { //原始现金侠的聚信立报告-分为两种：从数据库中查出来解析
            handleCashmanJxl(jxlDetail, model);
            //返回 现金侠原始聚信立报告页面
            model.addAttribute("name", userInfo.getRealname());
            model.addAttribute("gender", userInfo.getUserSex());
            model.addAttribute("idNumber", userInfo.getIdNumber());
            model.addAttribute("age", userInfo.getUserAge());
            returnUrl = "mycollectionorder/jxlReport";
        } else {
            model.addAttribute(MESSAGE, "暂无此客户的聚信立报告！");
            returnUrl = "mycollectionorder/jxlReport";
        }
        return returnUrl;
    }

    /**
     * 现金侠聚信立报告的两种解析
     */
    private void handleCashmanJxl(String jxlDetail, Model model) {
        if (jxlDetail.startsWith("{\"report\"")) { //原始现金侠第二种聚信立报告
            JxlUserReport jxl2 = JSONObject.parseObject(jxlDetail, JxlUserReport.class);
            com.info.back.vo.jxl2.Report report = jxl2.getReport();
            addModelAttribute(model, report, jxl2.getUser_info_check().getCheck_black_info(), jxl2.getEbusiness_expense(),
                    jxl2.getDeliver_address(), jxl2.getBehavior_check(), jxl2.getCollection_contact(),
                    jxl2.getContact_list(), jxl2.getContact_region(), jxl2.getCell_behavior(), jxl2.getMain_service(),
                    jxl2.getTrip_info(), jxl2.getUser_info_check().getCheck_search_info());
        } else { //原始现金侠第一种聚信立报告
            UserReport jxl1 = JSONObject.parseObject(jxlDetail, UserReport.class);
            if (jxl1.getReport_data() != null) {
                addModelAttribute(model, jxl1.getReport_data().getReport(), jxl1.getReport_data().getUser_info_check().getCheck_black_info(),
                        jxl1.getReport_data().getEbusiness_expense(), jxl1.getReport_data().getDeliver_address(),
                        jxl1.getReport_data().getBehavior_check(), jxl1.getReport_data().getCollection_contact(),
                        jxl1.getReport_data().getContact_list(), jxl1.getReport_data().getContact_region(),
                        jxl1.getReport_data().getCell_behavior(), jxl1.getReport_data().getMain_service(),
                        jxl1.getReport_data().getTrip_info(), jxl1.getReport_data().getUser_info_check().getCheck_search_info());
            }
        }
    }

    /**
     * addAttribute 将聚信立解析的字段add到model中
     */
    private void addModelAttribute(Model model, Object report, CheckBlackInfo check_black_info, List<EbusinessExpense> ebusiness_expense, List<DeliverAddress> deliver_address, List<BehaviorCheck> behavior_check, List<CollectionContact> collection_contact, List<ContactList> contact_list, List<ContactRegion> contact_region, List<CellBehavior> cell_behavior, List<MainService> main_service, List<TripInfo> trip_info, CheckSearchInfo check_search_info) {

        model.addAttribute("report", report);
        model.addAttribute("check_black_info", check_black_info);
        model.addAttribute("ebusiness_expense", ebusiness_expense);
        model.addAttribute("deliver_address", deliver_address);
        model.addAttribute("behavior_check", behavior_check);
        model.addAttribute("collection_contact", collection_contact);
        model.addAttribute("contact_list", contact_list);
        model.addAttribute("contact_region", contact_region);
        model.addAttribute("cell_behavior", cell_behavior);
        model.addAttribute("main_service", main_service);
        model.addAttribute("trip_info", trip_info);
        model.addAttribute("check_search_info", check_search_info);
    }

}
