package com.info.back.controller;

import com.info.back.service.IMmanLoanCollectionOrderService;
import com.info.back.service.IMmanUserInfoService;
import com.info.back.utils.BackConstant;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.MmanLoanCollectionOrder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 类描述：聚信立报告类
 * 创建人：yyf
 * 创建时间：2017/8/4 0004下午 04:13
 */
@Controller
@RequestMapping("/mmanUserInfo")
public class MmanUserInfoController extends BaseController {
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
    public String getPage(HttpServletRequest request, HttpServletResponse response, Model model){
        String url=null;
        try {
            HashMap<String, Object> params = getParametersO(request);
            MmanLoanCollectionOrder order =  null;
            if(StringUtils.isNotBlank(params.get("id")+"")){
               order = mmanLoanCollectionOrderService.getOrderById(params.get("id").toString());
            }
            if(order != null){
                int overdueDay = order.getOverdueDays();
                String userId = order.getUserId();
                BackUser backUser=this.loginAdminUser(request);
                if(!BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS.equals(order.getStatus())){
                    if((BackConstant.COLLECTION_ROLE_ID.toString().equals(backUser.getRoleId()) && "2".equals(order.getJxlStatus()))|| !BackConstant.COLLECTION_ROLE_ID.toString().equals(backUser.getRoleId())){
                        url = mmanUserInfoService.handleJxl(model,userId);
                    }else {
                        if (overdueDay < 25){
                            model.addAttribute(MESSAGE, "逾期25天以上订单才能查看聚信里报告！");
                            url="mycollectionorder/jxlReport";
                        }else if (overdueDay>30){
                            url = mmanUserInfoService.handleJxl(model,userId);
                        }else {
                            url="mycollectionorder/tapplyJxlRepor";
                        }
                    }
                }else {
                    model.addAttribute(MESSAGE, "催收成功订单不允许查看聚信里报告！");
                }
            }else{
                    logger.error("该订单异常，请核实，订单号：" + params.get("id"));
                }
            model.addAttribute("params", params);
        } catch (Exception e) {
            logger.error("jxlException", e);
            e.printStackTrace();
            model.addAttribute(MESSAGE, "聚信立异常");
        }
        return url;
    }

}
