package com.info.back.service;

import com.alibaba.fastjson.JSONObject;
import com.info.back.dao.IMmanUserInfoDao;
import com.info.back.utils.WebClient;
import com.info.back.vo.Base64;
import com.info.back.vo.GzipUtil;
import com.info.back.vo.jxl.*;
import com.info.back.vo.jxl2.JxlUserReport;
import com.info.back.vo.jxl_360.Rong360Report;
import com.info.back.vo.jxl_jdq.JdqReport;
import com.info.back.vo.jxl_jlm.JlmReport;
import com.info.config.PayContents;
import com.info.web.pojo.ContactInfo;
import com.info.web.pojo.MmanUserInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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


    /**
     * 解析聚信立报告
     */
    @Override
    public String handleJxl(Model model, String userId) {
        MmanUserInfo userInfo = mmanUserInfoDao.get(userId);
        String returnUrl = "";
        if (userInfo != null){
            String jxlDetail = userInfo.getJxlDetail();
            //如果jxlDetail不为空，则从数据库中查出来解析，如果为空，则从Hbase中查出来解析
            if (StringUtils.isNotBlank(jxlDetail)){ //原始现金侠的聚信立报告-分为两种：从数据库中查出来解析
                if (jxlDetail.startsWith("{\"report\"")){ //原始现金侠第二种聚信立报告
                    JxlUserReport jxl2= JSONObject.parseObject(jxlDetail,JxlUserReport.class);
                    com.info.back.vo.jxl2.Report report = jxl2.getReport();
                    addModelAttribute(model,report, jxl2.getUser_info_check().getCheck_black_info(),jxl2.getEbusiness_expense(),
                            jxl2.getDeliver_address(),jxl2.getBehavior_check(),jxl2.getCollection_contact(),
                            jxl2.getContact_list(),jxl2.getContact_region(),jxl2.getCell_behavior(),jxl2.getMain_service(),
                            jxl2.getTrip_info(),jxl2.getUser_info_check().getCheck_search_info());
                }else { //原始现金侠第一种聚信立报告
                    UserReport jxl1 = JSONObject.parseObject(jxlDetail, UserReport.class);
                    if(jxl1.getReport_data() != null){
                        addModelAttribute(model,jxl1.getReport_data().getReport(),jxl1.getReport_data().getUser_info_check().getCheck_black_info(),
                                jxl1.getReport_data().getEbusiness_expense(),jxl1.getReport_data().getDeliver_address(),
                                jxl1.getReport_data().getBehavior_check(),jxl1.getReport_data().getCollection_contact(),
                                jxl1.getReport_data().getContact_list(),jxl1.getReport_data().getContact_region(),
                                jxl1.getReport_data().getCell_behavior(),jxl1.getReport_data().getMain_service(),
                                jxl1.getReport_data().getTrip_info(),jxl1.getReport_data().getUser_info_check().getCheck_search_info());
                    }
                }
                //返回 现金侠原始聚信立报告页面
                returnUrl = "mycollectionorder/jxlReport";
            }else { //从Hbase中查出来解析： 融360聚信立；借了吗聚信立；借点钱聚信立；分期管家聚信立
                JSONObject json = new JSONObject();
                json.put("userid", userId);
                String data = json.toString();
                try {
                    String result = WebClient.getInstance().postJsonData(PayContents.JXL_HBASE_SERVER_URL,  data,null);
                    json = JSONObject.parseObject(result);
                    String type = json.getString("type");
                    String jsonString = json.getString("json");
                    if (StringUtils.isBlank(jsonString)){
                        model.addAttribute(MESSAGE, "聚信立报告为空");
                        returnUrl = "mycollectionorder/jxlReport";
                        return returnUrl;
                    }
                    if ("3".equals(type)){
                        jsonString = GzipUtil.uncompress(Base64.decode(jsonString),"UTF-8");
                    }
                    JSONObject jsonDetail = JSONObject.parseObject(jsonString);
                    if ("4".equals(type)){ //融360聚信立
                        Rong360Report rong360Report = JSONObject.toJavaObject(jsonDetail,Rong360Report.class);
                        //返回 融360聚信立报告页面
                        model.addAttribute("inputInfo",rong360Report.getInput_info());
                        model.addAttribute("basicInfo",rong360Report.getBasic_info());
                        model.addAttribute("emergencyAnalysis",rong360Report.getEmergency_analysis());
                        model.addAttribute("callLog",rong360Report.getCall_log());
                        returnUrl = "mycollectionorder/rong360Report";
                    }else if ("6".equals(type) || "5".equals(type)){ //借了吗，分期管家聚信立
                        JlmReport jlmReport = JSONObject.toJavaObject(jsonDetail,JlmReport.class);
                        model.addAttribute("calls",jlmReport.getCalls());
                        model.addAttribute("transactions",jlmReport.getTransactions());
                        model.addAttribute("basic",jlmReport.getBasic());
                        //返回 借了吗或分期管家聚信立报告页面
                        returnUrl = "mycollectionorder/jlmReport";
                    }else if ("3".equals(type)){ //借点钱聚信立
                        JdqReport jdqReport = JSONObject.toJavaObject(jsonDetail,JdqReport.class);
                        model.addAttribute("calls",jdqReport.getCalls());
                        model.addAttribute("transactions",jdqReport.getTransactions());
                        model.addAttribute("basic",jdqReport.getBasic());
                        model.addAttribute("smses",jdqReport.getSmses());
                        model.addAttribute("datasource",jdqReport.getDatasource());
                        //返回 借点钱聚信立报告页面
                        returnUrl = "mycollectionorder/jdqReport";
                    }else {
                        //其他情况暂时先返回原始聚信立报告页面
                        returnUrl = "mycollectionorder/jxlReport";
                    }
                }catch (Exception e){
                    model.addAttribute(MESSAGE, "Hbase聚信立请求超时！");
                    returnUrl = "mycollectionorder/jxlReport";
                }
            }
            model.addAttribute("name",userInfo.getRealname());
            model.addAttribute("gender",userInfo.getUserSex());
            model.addAttribute("idNumber",userInfo.getIdNumber());
            model.addAttribute("age",userInfo.getUserAge());
        }
        return returnUrl;
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
