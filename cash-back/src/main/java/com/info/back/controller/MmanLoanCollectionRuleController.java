package com.info.back.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.result.JsonResult;
import com.info.back.service.ICollectionCompanyService;
import com.info.back.service.IMmanLoanCollectionRuleService;
import com.info.back.utils.BackConstant;
import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.web.pojo.MmanLoanCollectionCompany;
import com.info.web.pojo.MmanLoanCollectionRule;
import com.info.web.util.PageConfig;

@Controller
@RequestMapping("collectionRule/")
public class MmanLoanCollectionRuleController extends BaseController{
	private static Logger logger = Logger.getLogger(MmanLoanCollectionRuleController.class);
	
	@Autowired
	private IMmanLoanCollectionRuleService mmanLoanCollectionRuleService;
	@Autowired
	private ICollectionCompanyService collectionCompanyService;
	
	@RequestMapping("getMmanLoanCollectionRulePage")
	public String getCollectionRulePage(HttpServletRequest request,HttpServletResponse response, Model model){
		try {
			HashMap<String, Object> params = getParametersO(request);
			
			PageConfig<MmanLoanCollectionRule> pageConfig = mmanLoanCollectionRuleService.findPage(params);
			model.addAttribute("pm", pageConfig);
			model.addAttribute("params", params);// 用于搜索框保留值
			
			List<MmanLoanCollectionCompany> companyList=collectionCompanyService.selectCompanyList();
			model.addAttribute("companyList",companyList);
			
			model.addAttribute("groupNameMap",BackConstant.groupNameMap);//用户组
			
			
		}catch (Exception e) {
			logger.error("getCollectionRulePage error", e);
		}
		return "mmanLoanCollectionRule/mmanLoanCollectionRuleList";
	}
	@RequestMapping("updateMmanLoanCollectionRulePage")
	public String updateMmanLoanCollectionRulePage(HttpServletRequest request,HttpServletResponse response, Model model,MmanLoanCollectionRule collectionRule){
		HashMap<String, Object> params = this.getParametersO(request);
		String url = null;
		String erroMsg = null;
		try{
			
			List<MmanLoanCollectionCompany> companyList=collectionCompanyService.selectCompanyList();
			model.addAttribute("companyList",companyList);
			
			model.addAttribute("groupNameMap",BackConstant.groupNameMap);//用户组
			
			//更新页面转跳
			if("toJsp".equals(params.get("type"))){
				if(params.containsKey("id")){
					collectionRule = mmanLoanCollectionRuleService.getCollectionRuleById(params.get("id").toString());
					model.addAttribute("collectionRule", collectionRule);
				}
				url = "mmanLoanCollectionRule/updateMmanLoanCollectionRule";
			}else{
				//更新或者添加操作(type非toJsp)
				JsonResult result=new JsonResult("-1","操作失败");
				if(StringUtils.isNotBlank(collectionRule.getId())){
					result = mmanLoanCollectionRuleService.updateCollectionRule(collectionRule);
				}else{
//					result = mmanLoanCollectionRuleService.insert(collectionRule);
				}
				SpringUtils.renderDwzResult(response, "0".equals(result.getCode()), result.getMsg(),
						DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
								.toString());
			}
		} catch (Exception e) {
			if (url == null) {
				erroMsg = "服务器异常，请稍后重试！";
				if (e.getLocalizedMessage().indexOf("UK_user_account") >= 0) {
					erroMsg = "用户名重复！";
				}
				SpringUtils.renderDwzResult(response, false, "操作失败,原因："
						+ erroMsg, DwzResult.CALLBACK_CLOSECURRENT, params.get(
						"parentId").toString());
			}
			logger.error("添加权限信息失败，异常信息:", e);
		}
		model.addAttribute(MESSAGE, erroMsg);
		model.addAttribute("params", params);
		return url;
	}
	
}
