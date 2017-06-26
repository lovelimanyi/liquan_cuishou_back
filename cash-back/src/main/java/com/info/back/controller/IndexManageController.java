package com.info.back.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.service.BackModuleService;
import com.info.back.service.IBackIndexService;
import com.info.back.utils.DwzResult;
import com.info.back.utils.ServiceResult;
import com.info.back.utils.SpringUtils;
import com.info.constant.Constant;
import com.info.web.pojo.BackModule;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.IndexPage;
import com.info.web.pojo.index.Banner;
import com.info.web.pojo.index.Cv;
import com.info.web.pojo.index.HotBorrow;
import com.info.web.pojo.index.Link;
import com.info.web.pojo.index.Notice;
import com.info.web.util.JSONUtil;
import com.info.web.util.JedisDataClient;
import com.info.web.util.PageConfig;

/**
 * 
 * 类描述： 前台首页维护 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-7-1 下午03:10:38 <br>
 * 
 * @version
 * 
 */
@Controller
@RequestMapping("indexManage/")
public class IndexManageController extends BaseController {
	private static Logger logger = LoggerFactory
			.getLogger(IndexManageController.class);
	@Autowired
	private IBackIndexService backIndexService;

	/**
	 * 跳转到首页某个部分的管理页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("findList")
	public String findList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String url = null;
		String errorMsg = null;
		HashMap<String, Object> params = this.getParametersO(request);
		try {
			if (params.containsKey("type")) {
				String type = String.valueOf(params.get("type"));
				if (StringUtils.isNotBlank(type)) {
					IndexPage index = this.backIndexService.searchIndex();// 查询数据库
					if ("banner".equals(type)) {
						List<Banner> bannerList = JSONUtil.jsonArrayToBean(
								index.getIndexBanner(), Banner.class);// bannerList
						model.addAttribute("bannerList", bannerList);
						url = "index/bannerList";
					} else if ("notice".equals(type)) {
						List<Notice> noticeList = JSONUtil.jsonArrayToBean(
								index.getIndexNotice(), Notice.class);// 公告
						model.addAttribute("noticeString", index
								.getIndexNotice().replaceAll("\"", "\'"));
						model.addAttribute("noticeList", noticeList);
						url = "index/noticeList";
					} else if ("cv".equals(type)) {
						Cv cv = JSONUtil.jsonToBean(index.getIndexCv(),
								Cv.class);// 公司简介
						model.addAttribute("cv", cv);
						url = "index/saveUpdateCv";
					} else if ("updateCv".equals(type)) {

					} else if ("link".equals(type)) {
						List<Link> linkList = JSONUtil.jsonArrayToBean(index
								.getIndexLink(), Link.class);// 友情链接
						model.addAttribute("linkString", index.getIndexLink()
								.replaceAll("\"", "\'"));
						model.addAttribute("linkList", linkList);
						url = "index/linkList";
					} else if ("".equals(type)) {

					} else if ("".equals(type)) {

					}
				} else {
					errorMsg = "参数非法！";
				}
			} else {
				errorMsg = "参数非法！";
			}
		} catch (Exception e) {
			logger.error("findList error ", e);
		}
		model.addAttribute("params", params);
		model.addAttribute(MESSAGE, errorMsg);
		return url;
	}

	/**
	 * 保存banner
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param banner
	 * @return
	 */
	@RequestMapping("saveUpdateBanner")
	public String saveUpdateBanner(HttpServletRequest request,
			HttpServletResponse response, Model model, Banner banner) {
		HashMap<String, Object> params = this.getParametersO(request);
		String url = null;
		String erroMsg = null;
		try {
			if ("toJsp".equals(String.valueOf(params.get("type")))) {
				url = "index/saveUpdateBanner";
			} else {
				IndexPage index = this.backIndexService.searchIndex();// 查询数据库
				List<Banner> list = JSONUtil.jsonArrayToBean(index
						.getIndexBanner(), Banner.class);
				list.add(banner);
				IndexPage zbIndex = new IndexPage();
				zbIndex.setIndexBanner(JSONUtil.beanListToJson(list));
				backIndexService.update(zbIndex);
				SpringUtils.renderDwzResult(response, true, "操作成功",
						DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
								.toString());
			}

		} catch (Exception e) {
			erroMsg = "服务器异常，请稍后重试！";
			logger.error("saveUpdateBanner error ", e);
		}
		model.addAttribute("params", params);
		model.addAttribute(MESSAGE, erroMsg);
		return url;
	}

	/**
	 * 删除banner
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("deleteBanner")
	public void deleteBanner(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		String erroMsg = null;
		boolean flag = false;
		try {
			IndexPage zindex = this.backIndexService.searchIndex();// 查询数据库
			List<Banner> list = JSONUtil.jsonArrayToBean(zindex
					.getIndexBanner(), Banner.class);
			list.remove(Integer.valueOf(String.valueOf(params.get("id")))
					.intValue());
			IndexPage zbIndex = new IndexPage();
			zbIndex.setIndexBanner(JSONUtil.beanListToJson(list));
			backIndexService.update(zbIndex);
			flag = true;

		} catch (Exception e) {
			erroMsg = "服务器异常，请稍后重试！";
			logger.error("deleteBanner error ", e);
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败，"
				+ erroMsg, DwzResult.CALLBACK_CLOSECURRENTDIALOG, params.get(
				"parentId").toString());

	}

	/**
	 * 保存公告
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param banner
	 * @return
	 */
	@RequestMapping("saveNotice")
	public String saveNotice(HttpServletRequest request,
			HttpServletResponse response, Model model, Notice notice) {
		HashMap<String, Object> params = this.getParametersO(request);
		String url = null;
		String erroMsg = null;
		try {
			if ("toJsp".equals(String.valueOf(params.get("type")))) {
				if (params.get("noticeString") != null) {
					model.addAttribute("noticeString", params
							.get("noticeString"));
				}
				params.put("url", "saveNotice");
				url = "index/saveUpdateNotice";
			} else {
				IndexPage zindex = this.backIndexService.searchIndex();// 查询数据库
				List<Notice> list = JSONUtil.jsonArrayToBean(zindex
						.getIndexNotice(), Notice.class);
				list.add(notice);
				IndexPage zbIndex = new IndexPage();
				zbIndex.setIndexNotice(JSONUtil.beanListToJson(list));
				backIndexService.update(zbIndex);
				SpringUtils.renderDwzResult(response, true, "操作成功",
						DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
								.toString());
			}

		} catch (Exception e) {
			erroMsg = "服务器异常，请稍后重试！";
			logger.error("saveNotice error ", e);
		}
		model.addAttribute("params", params);
		model.addAttribute(MESSAGE, erroMsg);
		return url;
	}

	/**
	 * 更新公告
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param banner
	 * @return
	 */
	@RequestMapping("updateNotice")
	public String updateNotice(HttpServletRequest request,
			HttpServletResponse response, Model model, Notice notice) {
		HashMap<String, Object> params = this.getParametersO(request);
		String url = null;
		String erroMsg = null;
		try {
			if ("toJsp".equals(String.valueOf(params.get("type")))) {
				IndexPage zindex = this.backIndexService.searchIndex();// 查询数据库
				List<Notice> list = JSONUtil.jsonArrayToBean(zindex
						.getIndexNotice(), Notice.class);
				model.addAttribute("notice", list.get(Integer.valueOf(String
						.valueOf(params.get("id")))));
				params.put("url", "updateNotice");
				url = "index/saveUpdateNotice";
			} else {
				IndexPage zindex = this.backIndexService.searchIndex();// 查询数据库
				List<Notice> list = JSONUtil.jsonArrayToBean(zindex
						.getIndexNotice(), Notice.class);
				int index = Integer.valueOf(String.valueOf(params.get("id")));
				list.set(index, notice);
				IndexPage zbIndex = new IndexPage();
				zbIndex.setIndexNotice(JSONUtil.beanListToJson(list));
				backIndexService.update(zbIndex);

				SpringUtils.renderDwzResult(response, true, "操作成功",
						DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
								.toString());
			}

		} catch (Exception e) {
			erroMsg = "服务器异常，请稍后重试！";
			logger.error("updateNotice error ", e);
		}
		model.addAttribute("params", params);
		model.addAttribute(MESSAGE, erroMsg);
		return url;
	}

	/**
	 * 删除公告
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("deleteNotice")
	public void deleteNotice(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		String erroMsg = null;
		boolean flag = false;
		try {
			IndexPage zindex = this.backIndexService.searchIndex();// 查询数据库
			List<Notice> list = JSONUtil.jsonArrayToBean(zindex
					.getIndexNotice(), Notice.class);
			int index = Integer.valueOf(String.valueOf(params.get("id")));
			list.remove(index);
			IndexPage zbIndex = new IndexPage();
			zbIndex.setIndexNotice(JSONUtil.beanListToJson(list));
			backIndexService.update(zbIndex);
			flag = true;

		} catch (Exception e) {
			erroMsg = "服务器异常，请稍后重试！";
			logger.error("deleteNotice error ", e);
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败，"
				+ erroMsg, DwzResult.CALLBACK_CLOSECURRENTDIALOG, params.get(
				"parentId").toString());

	}

	/**
	 * 保存更新公司简介
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param Cv
	 * @return
	 */
	@RequestMapping("saveUpdateCv")
	public void saveUpdateCv(HttpServletRequest request,
			HttpServletResponse response, Model model, Cv cv) {
		HashMap<String, Object> params = this.getParametersO(request);
		String erroMsg = null;
		String code = "500";
		try {
			BackUser backUser = loginAdminUser(request);
			if (backUser != null) {
				IndexPage zbIndex = new IndexPage();
				zbIndex.setIndexCv(JSONUtil.beanToJson(cv));
				backIndexService.update(zbIndex);
				code = ServiceResult.SUCCESS;
				erroMsg = "操作成功！";
			} else {
				erroMsg = "请登录后操作！";
			}
		} catch (Exception e) {
			erroMsg = "服务器异常，请稍后重试！";
			logger.error("saveUpdateCv error ", e);
		}
		model.addAttribute("params", params);
		model.addAttribute(MESSAGE, erroMsg);
		SpringUtils.renderJson(response, new DwzResult(code, erroMsg));
	}

	/**
	 * 保存友情链接
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param banner
	 * @return
	 */
	@RequestMapping("saveUpdateLink")
	public String saveUpdateLink(HttpServletRequest request,
			HttpServletResponse response, Model model, Link link) {
		HashMap<String, Object> params = this.getParametersO(request);
		String url = null;
		String erroMsg = null;
		try {
			if ("toJsp".equals(String.valueOf(params.get("type")))) {
				url = "index/saveUpdateLink";
			} else {
				IndexPage index = this.backIndexService.searchIndex();// 查询数据库
				List<Link> list = JSONUtil.jsonArrayToBean(
						index.getIndexLink(), Link.class);
				list.add(link);
				IndexPage zbIndex = new IndexPage();
				zbIndex.setIndexLink(JSONUtil.beanListToJson(list));
				backIndexService.update(zbIndex);
				SpringUtils.renderDwzResult(response, true, "操作成功",
						DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
								.toString());
			}

		} catch (Exception e) {
			erroMsg = "服务器异常，请稍后重试！";
			logger.error("saveUpdateLink error ", e);
		}
		model.addAttribute("params", params);
		model.addAttribute(MESSAGE, erroMsg);
		return url;
	}

	/**
	 * 删除banner
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("deleteLink")
	public void deleteLink(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		String erroMsg = null;
		boolean flag = false;
		try {
			IndexPage zindex = this.backIndexService.searchIndex();// 查询数据库
			List<Link> list = JSONUtil.jsonArrayToBean(zindex.getIndexLink(),
					Link.class);
			list.remove(Integer.valueOf(String.valueOf(params.get("id")))
					.intValue());
			IndexPage zbIndex = new IndexPage();
			zbIndex.setIndexLink(JSONUtil.beanListToJson(list));
			backIndexService.update(zbIndex);
			flag = true;

		} catch (Exception e) {
			erroMsg = "服务器异常，请稍后重试！";
			logger.error("deleteBanner error ", e);
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败，"
				+ erroMsg, DwzResult.CALLBACK_CLOSECURRENTDIALOG, params.get(
				"parentId").toString());

	}
}
