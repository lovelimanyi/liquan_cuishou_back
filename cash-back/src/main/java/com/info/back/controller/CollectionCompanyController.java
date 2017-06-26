package com.info.back.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.result.JsonResult;
import com.info.back.service.ICollectionCompanyService;
import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.constant.Constant;
import com.info.web.pojo.MmanLoanCollectionCompany;
import com.info.web.util.PageConfig;
/**
 * 公司服Controller
 * @author xieyaling
 * @date 2017-02-10
 */
@Controller
@RequestMapping("company/")
public class CollectionCompanyController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(CollectionCompanyController.class);
	@Autowired
	private ICollectionCompanyService CollectionCompanyService;
	/**
	 * 查询公司列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("getCompanyPage")
	public String getCompanyPage(HttpServletRequest request,HttpServletResponse response, Model model) {
		try {
			HashMap<String, Object> params = getParametersO(request);
			params.put("noAdmin", Constant.ADMINISTRATOR_ID);
			PageConfig<MmanLoanCollectionCompany> pageConfig = CollectionCompanyService.findPage(params);
			model.addAttribute("pm", pageConfig);
			model.addAttribute("params", params);// 用于搜索框保留值
		} catch (Exception e) {
			logger.error("getCompanyPage error", e);
		}
		return "company/companyList";
	}
	/**
	 * 添加
	 */
	@RequestMapping("addCompany")
	public String addCompany(HttpServletRequest request,HttpServletResponse response, Model model){
		HashMap<String, Object> params = getParametersO(request);
		model.addAttribute("params", params);// 用于搜索框保留值
		return "company/addCompany";
	}
	/**
	 * 保存
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("saveCompany")
	public String saveCompany(HttpServletRequest request,HttpServletResponse response, Model model){
		String url = null;
		String erroMsg = null;
		MmanLoanCollectionCompany mmanLoanCollectionCompany=new MmanLoanCollectionCompany();
		JsonResult result=new JsonResult("-1","添加公司失败");
		Map<String, String> params =this.getParameters(request);
		try{
			result=CollectionCompanyService.saveCompany(params);
		}catch(Exception e){
			logger.error("saveCompany error", e);
		}
		SpringUtils.renderDwzResult(response, "0".equals(result.getCode()),result.getMsg(),DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
		model.addAttribute(MESSAGE, erroMsg);
		model.addAttribute("params", params);
		return url;
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("updateCompany")
	public String updateCompany(HttpServletRequest request,HttpServletResponse response, Model model){
		HashMap<String, Object> params = getParametersO(request);
		String id=request.getParameter("id")==null?"":request.getParameter("id");
		MmanLoanCollectionCompany Company=CollectionCompanyService.get(id);
		model.addAttribute("params", params);// 用于搜索框保留值
		model.addAttribute("company",Company);
		return "company/updateCompany";
	}
	/**
	 * 修改
	 */
	@RequestMapping("updateDateCompany")
	public String updateDateCompany(HttpServletRequest request,HttpServletResponse response, Model model){
		String url = null;
		String erroMsg = null;
		MmanLoanCollectionCompany mmanLoanCollectionCompany=new MmanLoanCollectionCompany();
		JsonResult result=new JsonResult("-1","修改公司信息失败");
		Map<String, String> params =this.getParameters(request);
		try{
			result=CollectionCompanyService.updateCompany(params);
		}catch(Exception e){
			logger.error("updateDateCompany error", e);
		}
		SpringUtils.renderDwzResult(response, "0".equals(result.getCode()),result.getMsg(),
				DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
						.toString());
		model.addAttribute(MESSAGE, erroMsg);
		model.addAttribute("params", params);
		return url;
	}
	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("deleteCompany")
	public void deleteCompany(HttpServletRequest request,HttpServletResponse response, Model model) {
		Map<String, String> params =this.getParameters(request);
		JsonResult result=new JsonResult("-1","删除公司失败");
		try {
			String compayId=params.get("id")==null?"":params.get("id");
			if(StringUtils.isNotBlank(compayId)){
				result=CollectionCompanyService.deleteCompany(compayId);
			}else{
				result.setMsg("参数错误");
			}
		} catch (Exception e) {
			logger.error("deleteCompany error", e);
		}
		SpringUtils.renderDwzResult(response, "0".equals(result.getCode()), result.getMsg(),
				DwzResult.CALLBACK_CLOSECURRENTDIALOG, params.get("parentId").toString());
	}
	
}
