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
import com.info.back.service.ISysDictService;
import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.SysDict;
import com.info.web.util.PageConfig;
/**
 * 数据字典Controller
 * @author 
 * @date 2017-02-16
 */
@Controller
@RequestMapping("sysDict/")
public class SysDictController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(SysDictController.class);
	@Autowired
	private ISysDictService sysDictService;
	
	/**
	 * 查询数据字典列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("getSysDictPage")
	public String getSysDictPage(HttpServletRequest request,HttpServletResponse response, Model model){
		
		
		try {
			HashMap<String, Object> params = getParametersO(request);
			PageConfig<SysDict> pageConfig = sysDictService.findPage(params);
			model.addAttribute("pm", pageConfig);
			model.addAttribute("params", params);
		} catch (Exception e) {
			logger.error("params error"+ e);
			model.addAttribute(MESSAGE, "服务器异常，请稍后重试！");
		}
		return "sysDict/sysDictList";
	}
	/**
	 * 刪除
	 */
	@RequestMapping("deleteSysDict")
	public void deleteSysDict(HttpServletRequest request,HttpServletResponse response, Model model){

		Map<String, String> params =this.getParameters(request);
		JsonResult result=new JsonResult("-1","删除数据字典失败");
		try {
			String id=params.get("id")==null?"":params.get("id");
			if(StringUtils.isNotBlank(id)){
				result=sysDictService.deleteSysDict(id);
			}else{
				result.setMsg("参数错误");
			}
		} catch (Exception e) {
			logger.error("deleteSysDict error", e);
		}
		SpringUtils.renderDwzResult(response, "0".equals(result.getCode()), result.getMsg(),
				DwzResult.CALLBACK_CLOSECURRENTDIALOG, params.get("parentId").toString());
	}
	
	/**
	 * 添加
	 */
	@RequestMapping("addSysDict")
	public String addSysDict(HttpServletRequest request,HttpServletResponse response, Model model){
		HashMap<String, Object> params = getParametersO(request);
		model.addAttribute("params", params);// 用于搜索框保留值
		return "sysDict/addSysDict";
	}
	/**
	 * 保存
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("saveSysDict")
	public String saveSysDict(HttpServletRequest request,HttpServletResponse response, Model model){
		String url = null;
		String erroMsg = null;
		JsonResult result=new JsonResult("-1","添加数据字典失败");
		Map<String, String> params =this.getParameters(request);
		BackUser backUser = loginAdminUser(request);
		params.put("createBy", backUser.getUserAccount());
		try{
			result= sysDictService.saveSysDict(params);
		}catch(Exception e){
			logger.error("saveSysDict error", e);
		}
		SpringUtils.renderDwzResult(response, "0".equals(result.getCode()),result.getMsg(),DwzResult.CALLBACK_CLOSECURRENT, params.get("theParentId").toString());
		model.addAttribute(MESSAGE, erroMsg);
		model.addAttribute("params", params);
		return url;
	}
	@RequestMapping("updateSysDict")
	public String updateSysDict(HttpServletRequest request,HttpServletResponse response, Model model){
		HashMap<String, Object> params = getParametersO(request);
		String id=request.getParameter("id")==null?"":request.getParameter("id");
		SysDict sysDict = sysDictService.get(id);
		model.addAttribute("params", params);// 用于搜索框保留值
		model.addAttribute("sysDict",sysDict);
		return "sysDict/updateSysDict";
	}
	@RequestMapping("updateDateSysDict")
	public void updateDateSysDict(HttpServletRequest request,HttpServletResponse response, Model model){
		JsonResult result = new JsonResult("-1","修改数据字典失败");
		Map<String, String> params =this.getParameters(request);
		BackUser backUser = loginAdminUser(request);
		params.put("updateBy", backUser.getUserAccount());
		try{
			result= sysDictService.updateSysDict(params);
		}catch(Exception e){
			logger.error("saveSysDict error", e);
		}
		SpringUtils.renderDwzResult(response, "0".equals(result.getCode()),result.getMsg(),DwzResult.CALLBACK_CLOSECURRENT, params.get("theParentId").toString());
	}
}
