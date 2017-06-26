package com.info.back.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.interceptor.IndexInit;
import com.info.back.service.IBackConfigParamsService;
import com.info.back.utils.SpringUtils;
import com.info.web.pojo.BackConfigParams;
import com.info.web.pojo.BackConfigParamsVo;

@Controller
@RequestMapping("cofigParams/")
public class ConfigParamController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(ConfigParamController.class);

	@Autowired
	IBackConfigParamsService backConfigParamsService;

	/**
	 * 进入系统配置编辑页面<br>
	 * 传入不同的type可以配置多个类型的页面
	 */
	@RequestMapping("enterCfg/{sysType}")
	public String enterCfgEdit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@PathVariable("sysType") String sysType, BackConfigParamsVo vo) {
		String url = null;
		String erroMsg = null;
		HashMap<String, Object> params = getParametersO(request);
		params.put("sysType", sysType);
		try {
			if ("toJsp".equals(String.valueOf(params.get("type")))) {
				List<BackConfigParams> list = backConfigParamsService
						.findParams(params);
				model.addAttribute("cfgList", list);
				url = "sys/configParam";
			} else {
				backConfigParamsService.updateValue(vo.getList(), sysType);
//				new IndexInit().updateCache();
				SpringUtils.renderDwzResult(response, true, "操作成功");
			}
		} catch (Exception e) {
			if (url == null) {
				SpringUtils.renderDwzResult(response, false, "操作失败");
			} else {
				erroMsg = "服务器异常，请稍后重试！";
			}
			logger.error("enterCfgEdit error params=" + params, e);
		}
		model.addAttribute(MESSAGE, erroMsg);
		model.addAttribute("params", params);
		return url;
	}
}
