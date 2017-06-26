package com.info.back.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.result.JsonResult;
import com.info.back.service.IAlertMsgService;
import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.constant.Constant;
import com.info.web.pojo.SysAlertMsg;
import com.info.web.util.PageConfig;

/**
 * 站内信Controller
 * @author xieyaling
 * @date 2017-02-17 17：04：38
 */
@Controller
@RequestMapping("sysAlertMsg/")
public class SysAlertMsgController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(CollectionCompanyController.class);
	@Autowired
	public IAlertMsgService alertMsgService;
	/**
	 * 查询公司列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("getAlertMsgPage")
	public String getCompanyPage(HttpServletRequest request,HttpServletResponse response, Model model) {
		try {
			HashMap<String, Object> params = getParametersO(request);
			params.put("noAdmin", Constant.ADMINISTRATOR_ID);
			PageConfig<SysAlertMsg> pageConfig = alertMsgService.findPage(params);
			model.addAttribute("pm", pageConfig);
			model.addAttribute("params", params);// 用于搜索框保留值
		} catch (Exception e) {
			logger.error("getAlertMsgPage error", e);
		}
		return "alertmsg/alertmsgList";
	}
	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("tagSolve")
	public void tagSolve(HttpServletRequest request,HttpServletResponse response, Model model) {
		Map<String, String> params =this.getParameters(request);
		JsonResult result=new JsonResult("-1","删除公司失败");
		String url=null;
		try {
			String alertId=params.get("id")==null?"":params.get("id");
			if(StringUtils.isNotBlank(alertId)){
				result=alertMsgService.updateTogAlertMsg(alertId);
			}else{
				result.setMsg("参数错误");
			}
		} catch (Exception e) {
			logger.error("deleteCompany error", e);
			result.setMsg("系异常请稍后重试");
		}
		SpringUtils.renderDwzResult(response, "0".equals(result.getCode()), result.getMsg(),
				DwzResult.CALLBACK_CLOSECURRENTDIALOG, params.get("parentId").toString());
	}
}
