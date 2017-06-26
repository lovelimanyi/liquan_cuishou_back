package com.info.back.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.interceptor.IndexInit;
import com.info.back.service.IBackModuleService;
import com.info.back.utils.SpringUtils;
import com.info.web.pojo.BackModule;
import com.info.web.pojo.BackUser;

/**
 * 
 * 类描述：首页类 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午02:57:46 <br>
 * 
 * @version
 * 
 */
@Controller
public class IndexController extends BaseController {
	private static Logger logger = Logger.getLogger(IndexController.class);
	@Autowired
	private IBackModuleService backModuleService;

	@RequestMapping("indexBack")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			BackUser backUser = loginAdminUser(request);
			if (backUser == null) {
				return "login";
			}
			params.put("userId", backUser.getId());
			params.put("parentId", "0");
			// 获得顶级菜单
			List<BackModule> menuModuleList = backModuleService
					.findAllModule(params);
			if (menuModuleList != null && menuModuleList.size() > 0) {
				int moduleId = menuModuleList.get(0).getId();
				params.put("parentId", moduleId);
				// 获得某个顶级菜单的子菜单（二级菜单）
				List<BackModule> subMenu = backModuleService
						.findAllModule(params);
				for (BackModule backModule : subMenu) {
					params.put("parentId", backModule.getId());
					// 获得某个二级菜单的子菜单（三级菜单）
					List<BackModule> thirdMenu = backModuleService
							.findAllModule(params);
					backModule.setModuleList(thirdMenu);
				}
				model.addAttribute("subMenu", subMenu);
			}
			model.addAttribute("menuModuleList", menuModuleList);
		} catch (Exception e) {
			logger.error("back index error ", e);
		}
		return "index";
	}

	@RequestMapping("subMenu")
	public String subMenu(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		try {
			BackUser backUser = loginAdminUser(request);
			params.put("userId", backUser.getId());
			params.put("parentId", params.get("myId"));
			// 获得某个顶级菜单的子菜单（二级菜单）
			List<BackModule> subMenu = backModuleService.findAllModule(params);
			for (BackModule backModule : subMenu) {
				params.put("parentId", backModule.getId());
				// 获得某个二级菜单的子菜单（三级菜单）
				List<BackModule> thirdMenu = backModuleService
						.findAllModule(params);
				backModule.setModuleList(thirdMenu);
			}
			model.addAttribute("subMenu", subMenu);
		} catch (Exception e) {
			logger.error("展示权限树失败，异常信息:", e);
		}
		return "subMenu";
	}

	@RequestMapping("rightSubList")
	public String rightSubList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("parentId", request.getParameter("parentId"));
			params.put("myId", request.getParameter("myId"));
			BackUser backUser = loginAdminUser(request);
			params.put("userId", backUser.getId());
			// 获得当前登录用户的myId下的子权限
			List<BackModule> rightSubList = backModuleService
					.findAllModule(params);
			model.addAttribute("rightSubList", rightSubList);
		} catch (Exception e) {
			logger.error("rightSubList error ", e);
			model.addAttribute(MESSAGE, "权限查询异常！");
		}
		return "rightSubList";
	}

	/**
	 * 更新系统缓存<br>
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param sysConfig
	 * @return
	 */
	@RequestMapping("updateCache")
	public void updateCache(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		boolean succ = false;
		try {
			BackUser backUser = loginAdminUser(request);
			if (backUser != null) {
//				new IndexInit().updateCache();
				succ = true;

			}
		} catch (Exception e) {
			logger.error("updateCache error ", e);
			succ = false;
		}
		SpringUtils.renderDwzResult(response, succ, succ ? "刷新缓存成功" : "刷新缓存失败");

	}
}
