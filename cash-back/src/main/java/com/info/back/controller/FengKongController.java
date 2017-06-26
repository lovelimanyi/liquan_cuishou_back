package com.info.back.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.info.web.pojo.BackUser;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.result.JsonResult;
import com.info.back.service.IFengKongService;
import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.constant.Constant;
import com.info.web.pojo.FengKong;
import com.info.web.util.PageConfig;

@Controller
@RequestMapping("fengKong/")
public class FengKongController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(FengKongController.class);

	@Autowired
	private IFengKongService fengKongService;
	
	@RequestMapping("getFengKongPage")
	public String toFengKong(HttpServletRequest request,HttpServletResponse response,Model model){
		try{
			HashMap<String,Object> params = getParametersO(request);
			params.put("noAdmin", Constant.ADMINISTRATOR_ID);
			PageConfig<FengKong> pageConfig = fengKongService.findPage(params);
			model.addAttribute("pm", pageConfig);
			model.addAttribute("params", params);
		} catch (Exception e) {
			logger.error("getFengKongPage error", e);
		}
		return "mycollectionorder/fengKongList";
	}
	@RequestMapping("saveUpdateFengKong")
	public String saveUpdateFengKong(FengKong fengKong,HttpServletRequest request,HttpServletResponse response,Model model){
		HashMap<String, Object> params = getParametersO(request);
		String url = null;
		String erroMsg = null;
		try{
			//更新页面转跳
			if("toJsp".equals(params.get("type"))){
				if(params.containsKey("id")){
					int id = Integer.valueOf(params.get("id").toString());
					fengKong = fengKongService.getFengKongById(id);
					model.addAttribute("fengKong", fengKong);
				}
				url = "mycollectionorder/saveUpdateFengKong";
			}else{
				//更新或者添加操作(type非toJsp)
				JsonResult result=new JsonResult("-1","操作失败");
				if(fengKong.getId() != null){
					result = fengKongService.updateFengKong(fengKong);
				}else{
					result = fengKongService.insert(fengKong);
				}
				SpringUtils.renderDwzResult(response, "0".equals(result.getCode()), result.getMsg(),
						DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
								.toString());
			}
		} catch (Exception e) {
			logger.error("saveUpdateFengKong异常", e);
		}
		model.addAttribute(MESSAGE, erroMsg);
		model.addAttribute("params", params);
		return url;
	}
}
