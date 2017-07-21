package com.info.back.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.info.back.utils.WebClient;
import com.info.back.vo.jxl2.JxlUserReport;
import com.info.config.PayContents;
import com.info.web.pojo.MmanUserInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.service.IMmanLoanCollectionOrderService;
import com.info.back.service.IMmanUserInfoService;
import com.info.back.utils.BackConstant;
import com.info.back.vo.jxl.UserReport;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.MmanLoanCollectionOrder;

import static com.info.config.PayContents.RONG360_REPORT_URL;

@Controller
@RequestMapping("/mmanUserInfo")
public class MmanUserInfoController extends BaseController{
	private static Logger logger = Logger.getLogger(MmanUserInfoController.class);
	@Autowired
	private IMmanUserInfoService mmanUserInfoService;
	@Autowired
	private IMmanLoanCollectionOrderService mmanLoanCollectionOrderService;
    /**
     * 聚信立报告
     * @param request
     * @param response
     * @param model
     * @return
     */
	@RequestMapping("/jxlReport")
	public String getPage(HttpServletRequest request,HttpServletResponse response,Model model){
		String erroMsg = null;
		String url=null;
		try {
			HashMap<String, Object> params = getParametersO(request);
			if(StringUtils.isNotBlank(params.get("id")+"")){
				MmanLoanCollectionOrder mmanLoanCollectionOrderOri = mmanLoanCollectionOrderService.getOrderById(params.get("id").toString());
				if(mmanLoanCollectionOrderOri != null){
                    int overdueDay = mmanLoanCollectionOrderOri.getOverdueDays();
                    BackUser backUser=this.loginAdminUser(request);
                    if((BackConstant.COLLECTION_ROLE_ID.toString().equals(backUser.getRoleId()) && "2".equals(mmanLoanCollectionOrderOri.getJxlStatus()))|| !BackConstant.COLLECTION_ROLE_ID.toString().equals(backUser.getRoleId())){
                        String returnString = handleJxl(model, params, mmanLoanCollectionOrderOri);
                        if(StringUtils.isNotBlank(returnString)){
                            if ("exception".equals(returnString)){
                                erroMsg="聚信立报告请求超时";
                            }else {
                                model.addAttribute("rong360Url", url);
                            }
                            url = "mycollectionorder/rong360Report";
                        }else {
                            url="mycollectionorder/jxlReport";
                        }

                    }else {
                        if (overdueDay < 25){
                            erroMsg = "逾期25天以上订单才能查看聚信里报告！";
                            url="mycollectionorder/jxlReport";
                        }else if (overdueDay>30){
                            handleJxl(model, params, mmanLoanCollectionOrderOri);
                            url="mycollectionorder/jxlReport";
                        }else {
                            url="mycollectionorder/tapplyJxlRepor";
                        }
                    }
                }else{
				    logger.error("该订单异常，请核实，订单号：" + params.get("id"));
                }
			}
			model.addAttribute("params", params);
		} catch (Exception e) {
			logger.error("getMmanLoanCollectionOrder error", e);
		}
		model.addAttribute(MESSAGE, erroMsg);
		return url;
	}

	private String  handleJxl(Model model, HashMap<String, Object> params, MmanLoanCollectionOrder mmanLoanCollectionOrderOri) {
		params.put("loanUserId", mmanLoanCollectionOrderOri.getUserId());
		MmanUserInfo userInfo = mmanUserInfoService.getUserInfoById(mmanLoanCollectionOrderOri.getUserId());
		if(userInfo != null){
            String detail = userInfo.getJxlDetail();
            if(StringUtils.isNotBlank(detail)){
                if(detail.startsWith("{\"report\"")){
                    JxlUserReport usr = mmanUserInfoService.parseJxlDetail(params);
                    model.addAttribute("name",usr.getRealName());
                    model.addAttribute("gender",usr.getGender());
                    model.addAttribute("idNumber",usr.getIdNumber());
                    model.addAttribute("age",usr.getAge());
                    model.addAttribute("report",usr.getReport());
                    model.addAttribute("check_black_info",usr.getUser_info_check().getCheck_black_info());
                    model.addAttribute("ebusiness_expense",usr.getEbusiness_expense());
                    model.addAttribute("deliver_address",usr.getDeliver_address());
                    model.addAttribute("behavior_check",usr.getBehavior_check());
                    model.addAttribute("collection_contact",usr.getCollection_contact());
                    model.addAttribute("contact_list",usr.getContact_list());
                    model.addAttribute("contact_region",usr.getContact_region());
                    model.addAttribute("cell_behavior",usr.getCell_behavior());
                    model.addAttribute("main_service",usr.getMain_service());
                    model.addAttribute("trip_info",usr.getTrip_info());
                    model.addAttribute("check_search_info",usr.getUser_info_check().getCheck_search_info());
                    return null;
                }else{
                    UserReport ur = mmanUserInfoService.findJxlDetail(params);
                    model.addAttribute("name", ur.getRealName());
                    model.addAttribute("gender", ur.getGender());
                    model.addAttribute("idNumber", ur.getIdNumber());
                    model.addAttribute("age",ur.getAge());
                    if(ur.getReport_data() != null){
                        model.addAttribute("report", ur.getReport_data().getReport());
                        model.addAttribute("check_black_info", ur.getReport_data().getUser_info_check().getCheck_black_info());
                        model.addAttribute("ebusiness_expense", ur.getReport_data().getEbusiness_expense());
                        model.addAttribute("deliver_address", ur.getReport_data().getDeliver_address());
                        model.addAttribute("behavior_check", ur.getReport_data().getBehavior_check());
                        model.addAttribute("collection_contact", ur.getReport_data().getCollection_contact());
                        model.addAttribute("contact_list", ur.getReport_data().getContact_list());
                        model.addAttribute("contact_region", ur.getReport_data().getContact_region());
                        model.addAttribute("cell_behavior", ur.getReport_data().getCell_behavior());
                        model.addAttribute("main_service", ur.getReport_data().getMain_service());
                        model.addAttribute("trip_info", ur.getReport_data().getTrip_info());
                        model.addAttribute("check_search_info", ur.getReport_data().getUser_info_check().getCheck_search_info());
                    }else {
                        logger.error("ur.getReport_data() is null,userId : " + userInfo.getId());
                    }
                    return null;
                }
            }else{
                String userId = userInfo.getId();
                JSONObject json = new JSONObject();
                json.put("userid", userId);

                String data = json.toString();
                //192.168.5.37:8085   192.168.6.66:8888
                String returnString = null;
                try {
                    String result = WebClient.getInstance().postJsonData(PayContents.RONG360_REPORT_URL,  data,null);
//                    logger.info("result:"+result);
                    json = JSONObject.parseObject(result);
                    String type = json.getString("type");

                    if ("1".equals(type)){
                        String josnString = json.getString("json");
                        JSONObject josnDetail = JSONObject.parseObject(josnString);
                        returnString = josnDetail.getString("downloadUrl");
                    }
                    return returnString;
                }catch (Exception e){
                    e.printStackTrace();
                    return "exception";
                }
            }
        }
       return null;
	}

}
