package com.info.back.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sun.awt.image.PixelConverter.Bgrx;

import com.info.back.service.IMessageCenterService;
import com.info.back.service.ISmsLogService;
import com.info.back.service.ISmsUserService;
import com.info.back.sms.SmsUtil;
import com.info.back.sms.zt.SmsUtilZt;
import com.info.back.utils.DwzResult;
import com.info.back.utils.ExcelUtil;
import com.info.back.utils.SpringUtils;
import com.info.back.utils.SysCacheUtils;
import com.info.constant.Constant;
import com.info.web.pojo.BackConfigParams;
import com.info.web.pojo.SmsLog;
import com.info.web.pojo.SmsUser;
import com.info.web.util.PageConfig;

@Controller
@RequestMapping("msg/")
public class MessageCenterController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IMessageCenterService backMessageCenterService;
	@Autowired
	private ISmsUserService smsUserService;
	@Autowired
	private ISmsLogService smsLogService;

	/**
	 * 获取消息列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param messageCenter
	 * @return
	 */
	@RequestMapping("findList")
	public String findList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		HashMap<String, Object> params = getParametersO(request);
		try {
			if (!params.containsKey("noticeTypeId")
					|| params.get("noticeTypeId") == null) {
				params.put("noticeTypeAll", "all");
			}
			model.addAttribute("pm", backMessageCenterService.findPage(params));
			model.addAttribute("params", params);
		} catch (Exception e) {
			logger.error("params error", e);
			model.addAttribute(MESSAGE, "服务器异常，请稍后重试！");
		}
		return "msg/list";
	}

	/**
	 * 删除消息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("delete")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		boolean flag = false;
		HashMap<String, Object> params = this.getParametersO(request);
		try {
			backMessageCenterService.delete(params);
			flag = true;
		} catch (Exception e) {
			logger.error("删除消息出错：" + params, e);
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "删除成功" : "删除失败",
				DwzResult.CALLBACK_CLOSECURRENTDIALOG, params.get("parentId")
						.toString());
	}

	@RequestMapping("sendGroup")
	public String sendGroup(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String errorMsg = null;
		String url = null;
		String parentId = request.getParameter("parentId");
		try {
			String type = request.getParameter("type");
			if ("update".equals(type)) {
				boolean flag = false;
				HashMap<String, Object> params = this.getParametersO(request);
				Integer begin = Integer.valueOf(String.valueOf(params
						.get("rowBegin")));
				params.put("rowBegin", begin - 1);
				List<String> phones = smsUserService.findPhones(params);
				String content = String.valueOf(params.get("smsContent"));
				String key = request.getParameter("sign");
				BackConfigParams backConfigParams2 = null;
				for (BackConfigParams backConfigParams : SysCacheUtils
						.getListConfigParams(BackConfigParams.SIGN)) {
					if (backConfigParams.getSysKey().equals(key)) {
						backConfigParams2 = backConfigParams;
						break;
					}
				}
				if (phones != null && phones.size() > 0) {
					String way = "云峰";
					Integer length = phones.size();
					Integer batchSize = 500;
					String name = backConfigParams2.getSysValue();
					if (length <= batchSize) {
						int result = SmsUtil.sendSms(phones, content,
								Constant.ADVERT, backConfigParams2
										.getSysValue(), backConfigParams2
										.getSysValueBig());
						if (result == 0) {
							smsLogService.insert(new SmsLog(name + "第" + begin
									+ "行到" + (length + begin) + "已发送", way));
							flag = true;
						} else {
							// errorMsg = "第" + begin + "条到" + length
							// + "条群发失败,错误码" + result + "，程序终止！";
							errorMsg = "部分发送失败，请查看发送记录！";
						}
					} else {
						Integer remainder = length % batchSize;
						Integer divide = length / batchSize;
						List<String> list2 = null;
						for (int i = 0; i < divide; i++) {
							list2 = phones.subList(i * batchSize, (i + 1)
									* batchSize);
							int result = SmsUtil.sendSms(list2, content,
									Constant.ADVERT, backConfigParams2
											.getSysValue(), backConfigParams2
											.getSysValueBig());
							if (result == 0) {
								int b = i * batchSize + begin;
								smsLogService.insert(new SmsLog(name + content
										+ "第" + b + "行到" + (b + batchSize)
										+ "已发送", way));
								flag = true;
							} else {
								// errorMsg = i * batchSize + "到" + (i + 1)
								// * batchSize + "条群发失败,错误码" + result
								// + "，程序终止！";
								errorMsg = "部分发送失败，请查看发送记录！";
								break;
							}
						}
						if (remainder > 0) {
							list2 = phones.subList(divide * batchSize, length);
							int result = SmsUtil.sendSms(list2, content,
									Constant.ADVERT, backConfigParams2
											.getSysValue(), backConfigParams2
											.getSysValueBig());
							if (result == 0) {
								int b = divide * batchSize + begin;
								smsLogService.insert(new SmsLog(name + content
										+ "第" + b + "行到" + (length + begin)
										+ "已发送", way));
								flag = true;
							} else {
								// errorMsg = divide * batchSize + "到" + length
								// + "条群发失败,错误码" + result + "，程序终止！";
								errorMsg = "部分发送失败，请查看发送记录！";
							}

						}
					}
				}
				// if (result == 0) {
				// List<Integer> ids = smsUserService.findIds(params);
				// params.clear();
				// params.put("list", ids);
				// params.put("smsContent", content);
				// smsUserService.batchInsert(params);
				// flag = true;
				// } else {
				// errorMsg = "错误码" + result;
				// }
				SpringUtils.renderDwzResult(response, flag, flag ? "操作成功"
						: "操作失败：" + errorMsg, DwzResult.CALLBACK_CLOSECURRENT,
						parentId);
			} else {
				model.addAttribute("max", smsUserService.findAllCount(this
						.getParametersO(request)));
				url = "msg/sendGroup";
			}
		} catch (Exception e) {
			if (url == null) {
				errorMsg = "服务器异常，请稍后重试！";
				SpringUtils.renderDwzResult(response, false, "操作失败,原因："
						+ errorMsg, DwzResult.CALLBACK_CLOSECURRENT, parentId);
			}
			logger.error("sendGroup error", e);
		}
		model.addAttribute("parentId", parentId);
		model.addAttribute(MESSAGE, errorMsg);
		return url;
	}

	@RequestMapping("sendGroupZt")
	public String sendGroupZt(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String errorMsg = null;
		String url = null;
		String parentId = request.getParameter("parentId");
		try {
			String way = "助通";
			String type = request.getParameter("type");
			if ("update".equals(type)) {
				boolean flag = false;
				HashMap<String, Object> params = this.getParametersO(request);
				Integer begin = Integer.valueOf(String.valueOf(params
						.get("rowBegin"))) - 1;
				params.put("rowBegin", begin);
				Integer size = Integer.valueOf(String.valueOf(params
						.get("rowEnd")));
				if (size > 0) {
					Integer batchSize = 2000;
					String content = String.valueOf(params.get("smsContent"));
					String key = String.valueOf(params.get("sign"));
					HashMap<String, String> map = SysCacheUtils
							.getConfigParams(BackConfigParams.SMS_ZT);
					String name = map.get("zt_sign" + key);
					if (size <= batchSize) {
						String phonesString = smsUserService
								.findStringPhones(params);
						if (StringUtils.isNotBlank(phonesString)) {
							int result = SmsUtilZt.sendSms(phonesString,
									content, key);
							if (result == 0) {
								smsLogService.insert(new SmsLog(name + content
										+ "第" + (begin + 1) + "行到"
										+ (begin + size + 1) + "已发送", way));
								flag = true;
							} else {
								// errorMsg = "第" + params.get("rowBegin") +
								// "条到"
								// + size + "条群发失败,错误码" + result
								// + "，程序终止！";
								errorMsg = "部分发送失败，请查看发送记录！";
							}
						}
					} else {
						Integer remainder = size % batchSize;
						Integer divide = size / batchSize;
						for (int i = 0; i < divide; i++) {
							Integer b = i * batchSize + begin;
							params.put("rowBegin", b);
							params.put("rowEnd", batchSize);
							String phonesString = smsUserService
									.findStringPhones(params);
							if (StringUtils.isNotBlank(phonesString)) {
								int result = SmsUtilZt.sendSms(phonesString,
										content, key);
								if (result == 0) {
									smsLogService
											.insert(new SmsLog(name + content
													+ "第" + (b + 1) + "行到"
													+ (b + batchSize + 1)
													+ "已发送", way));
									flag = true;
								} else {
									errorMsg = "部分发送失败，请查看发送记录！";
									// errorMsg = i * batchSize + "到" + (i + 1)
									// * batchSize + "条群发失败,错误码" + result
									// + "，程序终止！";
									break;
								}
							}
						}
						if (remainder > 0) {
							Integer b = divide * batchSize + begin;
							params.put("rowBegin", divide * batchSize + begin);
							params.put("rowEnd", remainder);
							String phonesString = smsUserService
									.findStringPhones(params);
							if (StringUtils.isNotBlank(phonesString)) {
								int result = SmsUtilZt.sendSms(phonesString,
										content, key);
								if (result == 0) {
									smsLogService.insert(new SmsLog(name
											+ content + "第" + (b + 2) + "行到"
											+ (begin + size + 1) + "已发送", way));
									flag = true;
								} else {
									errorMsg = "部分发送失败，请查看发送记录！";
									// errorMsg = divide * batchSize + "到" +
									// size
									// + "条群发失败,错误码" + result + "，程序终止！";
								}
							}

						}
					}
				}
				SpringUtils.renderDwzResult(response, flag, flag ? "操作成功"
						: "操作失败：" + errorMsg, DwzResult.CALLBACK_CLOSECURRENT,
						parentId);
			} else {
				model.addAttribute("max", smsUserService.findAllCount(this
						.getParametersO(request)));
				url = "msg/sendGroupZt";
			}
		} catch (Exception e) {
			if (url == null) {
				errorMsg = "服务器异常，请稍后重试！";
				SpringUtils.renderDwzResult(response, false, "操作失败,原因："
						+ errorMsg, DwzResult.CALLBACK_CLOSECURRENT, parentId);
			}
			logger.error("sendGroupZt error", e);
		}
		model.addAttribute("parentId", parentId);
		model.addAttribute(MESSAGE, errorMsg);
		return url;
	}

	/**
	 * 获取消息列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param messageCenter
	 * @return
	 */
	@RequestMapping("findGroupList")
	public String findGroupList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		HashMap<String, Object> params = getParametersO(request);
		try {
			model.addAttribute("pm", smsUserService.findPage2(params));
			model.addAttribute("params", params);
		} catch (Exception e) {
			logger.error("params error", e);
			model.addAttribute(MESSAGE, "服务器异常，请稍后重试！");
		}
		return "msg/smsList";
	}

	/**
	 * 获取待发用户列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param messageCenter
	 * @return
	 */
	@RequestMapping("findSmsUserList")
	public String findSmsUserList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		HashMap<String, Object> params = getParametersO(request);
		try {
			model.addAttribute("pm", smsUserService.findPage(params));
			model.addAttribute("params", params);
		} catch (Exception e) {
			logger.error("params error", e);
			model.addAttribute(MESSAGE, "服务器异常，请稍后重试！");
		}
		return "msg/smsUserList";
	}

	/**
	 * 获取发送日志
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param messageCenter
	 * @return
	 */
	@RequestMapping("findSmsLog")
	public String findSmsLog(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		HashMap<String, Object> params = getParametersO(request);
		try {
			model.addAttribute("pm", smsLogService.findPage(params));
			model.addAttribute("params", params);
		} catch (Exception e) {
			logger.error("findSmsLog error", e);
			model.addAttribute(MESSAGE, "服务器异常，请稍后重试！");
		}
		return "msg/smsLog";
	}

	/**
	 * 导出Excel
	 */
	@RequestMapping("toExcel")
	public void toExcel(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> params = this.getParametersO(request);
		try {
			if (params.containsKey("bg") && params.containsKey("ed")) {
				SpringUtils.renderDwzResult(response, false, "开始行和结束行均不能为空",
						DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
								.toString());
			} else {
				Integer bg = Integer.valueOf(String.valueOf(params.get("bg"))) - 1;
				Integer ed = Integer.valueOf(String.valueOf(params.get("ed")))
						- bg;
				if (bg < 0 || ed > 1000) {
					SpringUtils.renderDwzResult(response, false,
							"操作失败,开始行不能小于0，且最多导出1000条数据",
							DwzResult.CALLBACK_CLOSECURRENT, params.get(
									"parentId").toString());
				} else {
					params.put("bg", bg);
					params.put("ed", ed);
					OutputStream os = response.getOutputStream();
					response.reset();// 清空输出流
					ExcelUtil.setFileDownloadHeader(request, response,
							"用户列表.xls");
					response.setContentType("application/msexcel");// 定义输出类型
					SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
					String[] titles = { "ID", "姓名", "手机号码" };
					List<Object[]> contents = new ArrayList<Object[]>();
					List<SmsUser> userList = smsUserService
							.findPartList(params);
					for (SmsUser smsUser : userList) {
						Object[] conList = new Object[titles.length];
						conList[0] = smsUser.getId();
						conList[1] = smsUser.getUserName();
						conList[2] = smsUser.getUserPhone();
						contents.add(conList);
					}
					ExcelUtil.buildExcel(workbook, "用户列表", titles, contents, 1,
							1, os);
				}
			}
		} catch (Exception e) {
			logger.error("toExcel error ", e);
			SpringUtils.renderDwzResult(response, false, "未知异常，请稍后重试！",
					DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
							.toString());
		}

	}

	/**
	 * 导出Excel
	 */
	@RequestMapping("toExcel2")
	public void toExcel2(HttpServletRequest request,
			HttpServletResponse response) {
		HashMap<String, Object> params = this.getParametersO(request);
		try {
			int pageSize = 50000;
			params.put(Constant.PAGE_SIZE, pageSize + "");
			PageConfig<SmsUser> pm = new PageConfig<SmsUser>();
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			ExcelUtil.setFileDownloadHeader(request, response, "用户列表.xls");
			response.setContentType("application/msexcel");// 定义输出类型
			SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
			String[] titles = { "ID", "姓名", "手机号码" };
			for (int i = 1; i <= pm.getTotalPageNum(); i++) {
				params.put(Constant.CURRENT_PAGE, String.valueOf(i));
				pm = smsUserService.findPage(params);
				List<SmsUser> userList = pm.getItems();
				List<Object[]> contents = new ArrayList<Object[]>();
				for (SmsUser smsUser : userList) {
					Object[] conList = new Object[titles.length];
					conList[0] = smsUser.getId();
					conList[1] = smsUser.getUserName();
					conList[2] = smsUser.getUserPhone();
					contents.add(conList);
				}
				ExcelUtil.buildExcel(workbook, "用户列表", titles, contents, i, pm
						.getTotalPageNum(), os);
			}
		} catch (Exception e) {
			logger.error("导出excel失败", e);
		}

	}

}
