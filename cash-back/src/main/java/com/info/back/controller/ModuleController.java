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
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.service.IBackModuleService;
import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.back.utils.TreeUtil;
import com.info.web.pojo.BackModule;
import com.info.web.pojo.BackTree;
import com.info.web.pojo.BackUser;
import com.info.web.util.PageConfig;

@Controller
@RequestMapping("module/")
public class ModuleController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(ModuleController.class);

	@Autowired
	private IBackModuleService backModuleService;

	/**
	 * 根据某个树节点查询子节点，菜单管理树
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("findModuleList")
	public String findModuleList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		try {
			// 得到UserId查询对应的权限
			BackUser backUser = loginAdminUser(request);
			params.put("userId", backUser.getId());
			if (!params.containsKey("moduleId")) {
				List<BackTree> treeModels = backModuleService
						.findModuleTree(params);
				String url = "module/findModuleList?myId=" + params.get("myId")
						+ "&moduleId=";
				String ModuleList = TreeUtil.getTreeModelStrings(url,
						"moduleBox", 0, 0, treeModels);
				ModuleList = "<a href=\"module/findModuleList?myId="
						+ params.get("myId")
						+ "&moduleId=0\" target=\"ajax\" rel=\"moduleBox\" >根目录</a>"
						+ ModuleList.replaceAll("<ul></ul>", "");
				model.addAttribute("ModuleList", ModuleList);
			} else {
				params.put("parentId", params.get("moduleId"));
				PageConfig<BackModule> pageConfig = backModuleService
						.findPage(params);
				model.addAttribute("pm", pageConfig);
			}
			model.addAttribute("params", params);
		} catch (Exception e) {
			logger.error("展示用户权限树失败，异常信息:", e);
		}
		return "module/listModuleSearch";
	}

	/**
	 * 显示权限树<br>
	 * 菜单管理选择父级菜单的树
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("showRightList")
	public String showRightList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		String errorMsg = null;
		try {
			// 得到UserId查询对应的权限
			BackUser backUser = loginAdminUser(request);
			params.put("userId", backUser.getId());
			List<BackTree> treeModels = backModuleService
					.findModuleTree(params);
			String outGroupHtml = TreeUtil.getCallBackTreeString(0, 0,
					treeModels);
			model.addAttribute("outGroupHtml", outGroupHtml);
		} catch (Exception e) {
			errorMsg = "服务器异常，请稍后重试！";
			logger.error("展示权限树失败，异常信息:", e);
		}
		model.addAttribute("errorMsg", errorMsg);
		return "showRight";
	}

	/**
	 * 添加或者更新权限
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param backModule
	 * @return
	 */
	@RequestMapping("saveUpdateModule")
	public String saveUpdateModule(HttpServletRequest request,
			HttpServletResponse response, Model model, BackModule backModule) {
		HashMap<String, Object> params = this.getParametersO(request);
		String url = null;
		String erroMsg = null;
		try {
			if ("toJsp".equals(String.valueOf(params.get("type")))) {
				if (params.containsKey("id")) {
					// 更新的页面跳转
					backModule = backModuleService.findById(Integer
							.valueOf(String.valueOf(params.get("id"))));
					BackModule parentModule = backModuleService
							.findById(backModule.getModuleParentId());
					model.addAttribute("backModule", backModule);
					model.addAttribute("parentModule", parentModule);
				}
				url = "module/saveUpdateModule";
			} else {
				if (params.containsKey("district.id")) {
					backModule.setModuleParentId(Integer.parseInt(params.get(
							"district.id").toString()));
				} else {
					backModule.setModuleParentId(0);
				}
				// 更新或者添加操作
				if (backModule.getId() != null) {
					backModule.setModuleView(1);
					backModuleService.updateById(backModule);
				} else {
					backModuleService.insert(backModule);
				}
				SpringUtils.renderDwzResult(response, true, "操作成功",
						DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
								.toString());
			}
		} catch (Exception e) {
			if (url == null) {
				SpringUtils.renderDwzResult(response, false, "操作失败",
						DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
								.toString());
			} else {
				erroMsg = "服务器异常，请稍后重试！";
			}
			logger.error("添加权限信息失败，异常信息:", e);
		}
		model.addAttribute(MESSAGE, erroMsg);
		model.addAttribute("params", params);
		return url;
	}

	@RequestMapping("deleteModule")
	public void deleteModule(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		boolean flag = false;
		try {
			Integer id = Integer.parseInt(params.get("id").toString());
			backModuleService.deleteById(id);
			flag = true;
		} catch (Exception e) {
			logger.error("删除权限信息失败，异常信息:", e);
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "删除权限成功" : "删除权限失败",
				DwzResult.CALLBACK_CLOSECURRENTDIALOG, params.get("parentId")
						.toString());
	}
}
