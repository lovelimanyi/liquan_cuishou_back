package com.info.back.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.service.ISmsService;
import com.info.constant.Constant;
import com.info.web.pojo.InfoSms;
import com.info.web.pojo.TemplateSms;
import com.info.web.util.PageConfig;

/**
 * 短信模板Controller
 * @author yangyaofei
 * @date 2017-02-17
 */
@Controller
@RequestMapping("infoMsg/")
public class SmsController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SmsController.class);
	
	@Autowired
	private ISmsService smsService;
	
	@RequestMapping("findList")
	public String getSmsPage(HttpServletRequest request,HttpServletResponse response, Model model){
		try{
			HashMap<String,Object> params = getParametersO(request);
			params.put("noAdmin", Constant.ADMINISTRATOR_ID);
			PageConfig<InfoSms> pageConfig = smsService.findPage(params);
			model.addAttribute("pm", pageConfig);
			model.addAttribute("params", params);
		} catch (Exception e) {
			logger.error("getSmsPage error", e);
		}
		return "infoSms/infoSmsList";
	}
}
