package com.info.back.controller;


import com.info.back.result.JsonResult;
import com.info.back.service.IMmanLoanCollectionCompanyService;
import com.info.back.service.IProblemFeedbackService;
import com.info.back.utils.BackConstant;
import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.web.pojo.*;

import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;


/**
 * Created by Administrator on 2017/5/22 0022.
 */
@Controller
@RequestMapping("problemFeedback")
public class ProblemFeedbackController extends BaseController{
    private static Logger logger = LoggerFactory
            .getLogger(NoticeController.class);
    @Autowired
    private IProblemFeedbackService problemFeedbackService;
    @Autowired
    private IMmanLoanCollectionCompanyService mmanLoanCollectionCompanyService;

    /**
     * 问题反馈列表
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("list")
    public String getProblemFeedbackPage(HttpServletRequest request,Model model){
        try{
            HashMap<String, Object> params = getParametersO(request);
            // 默认查看月初到当天时间段内的问题反馈
//            if(params.get("beginTime") == null && params.get("endTime") == null){
//                params.put("beginTime", DateUtil.getDateFormat(DateUtil.getDayFirst(),"yyyy-MM-dd"));
//                params.put("endTime", DateUtil.getDateFormat("yyyy-MM-dd"));
//            }
            BackUser backUser=this.loginAdminUser(request);

            // 超级管理员、客服、技术、财务可以看到所有问题，催收员只能看到电催类问题
            if(!"10001".equals(backUser.getRoleId()) && !"10024".equals(backUser.getRoleId()) && !"10023".equals(backUser.getRoleId()) && !"10025".equals(backUser.getRoleId())){
                if("10021".equals(backUser.getRoleId())){
                    params.put("type","1");   // 电催类 --- 1   催收员查看
                }
            }

            PageConfig<ProblemFeedback> page = problemFeedbackService.getPage(params);
            model.addAttribute("page",page);
            model.addAttribute("backUserRolename", BackConstant.backUserRolename);  // 角色名称
            model.addAttribute("problemFeedBackType", BackConstant.problemFeedBackType);  // 问题类型
            model.addAttribute("problemFeedBackStatus", BackConstant.problemFeedBackStatus);  // 问题状态
            model.addAttribute("MmanLoanCollectionCompanys", mmanLoanCollectionCompanyService.getList(new MmanLoanCollectionCompany()));  // 催收公司列表
            model.addAttribute("params", params);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return  "problemFeedback/list";
    }

    /**
     * 跳转到新增问题反馈的页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("addProblemFeedback")
    public String addFeedbackPage(HttpServletRequest request, Model model){
        HashMap<String, Object> params = getParametersO(request);
        model.addAttribute("params", params);
        model.addAttribute("problemFeedBackType", BackConstant.problemFeedBackType);  // 问题类型
        return  "problemFeedback/addProblemFeedback";
    }

    /**
     * 新增问题反馈
     * @param response
     * @param request
     */
    @RequestMapping("saveProblemFeedback")
    public void saveFeedbackPage(HttpServletResponse response,HttpServletRequest request){
        BackUser backUser=this.loginAdminUser(request);
        HashMap<String,Object> params = this.getParametersO(request);
        JsonResult result=new JsonResult("-1","添加问题反馈失败！");
        params.put("backUser",backUser);
        params.put("content",params.get("content"));
        params.put("type",params.get("type"));
        params.put("userPhone",params.get("loanUserPhone"));
        params.put("loanEndTime",params.get("loanEndTime"));
        try{
            result = problemFeedbackService.saveProblemFeedback(params);
        }catch (Exception e){
            logger.error("saveProblemFeedback err!");
        }
        SpringUtils.renderDwzResult(response, "0".equals(result.getCode()),result.getMsg(), DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
    }

    /**
     * 更新问题反馈状态
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping("updateStatus")
    public void updateStatus(HttpServletRequest request,HttpServletResponse response,Model model){
        HashMap<String, Object> params = getParametersO(request);
        JsonResult result = new JsonResult("-1","更新问题反馈失败！");
        BackUser backUser=this.loginAdminUser(request);
        HashMap<String,Object> map = new HashMap<>();

        String problemId = params.get("id").toString();
        map.put("status","1");
        map.put("id",problemId);
        map.put("uuid",backUser.getUuid());
        try{
            result = problemFeedbackService.updateStatus(map);
        }catch (Exception e){
            logger.error("updateProblemFeedbackStatus error!");
        }
        SpringUtils.renderDwzResult(response, "0".equals(result.getCode()),result.getMsg(),DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
        model.addAttribute("params", params);
    }

    /**
     * 跳转标记、处理问题页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("sloveProblem")
    public String sloveProblem(HttpServletRequest request,Model model) {
        HashMap<String, Object> params = getParametersO(request);
        params.put("id",params.get("id"));
        model.addAttribute("params", params);
        return "problemFeedback/sloveProblem";
    }
}
