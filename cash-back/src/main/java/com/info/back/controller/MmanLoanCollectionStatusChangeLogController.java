package com.info.back.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.dao.IBackUserCompanyPermissionsDao;
import com.info.back.dao.IBackUserDao;
import com.info.back.service.IBackUserService;
import com.info.back.service.IMmanLoanCollectionStatusChangeLogService;
import com.info.back.service.ISysDictService;
import com.info.back.utils.BackConstant;
import com.info.constant.Constant;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.BackUserCompanyPermissions;
import com.info.web.pojo.MmanLoanCollectionStatusChangeLog;
import com.info.web.pojo.SysDict;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;

@Controller
@RequestMapping("/collectionRecordStatusChangeLog")
public class MmanLoanCollectionStatusChangeLogController extends BaseController {
	private static Logger logger = Logger.getLogger(IndexController.class);
	@Autowired
	private IMmanLoanCollectionStatusChangeLogService mmanLoanCollectionStatusChangeLogService;
	@Autowired
	private IBackUserDao backUserDao;
	@Autowired
	private IBackUserService backUserService;
	@Autowired
	private ISysDictService sysDictService;

	@RequestMapping("/getMmanLoanCollectionStatusChangeLog")
	public String getView(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			HashMap<String, Object> params = getParametersO(request);
			BackUser backUser = (BackUser) request.getSession().getAttribute(
					Constant.BACK_USER);
			List<BackUserCompanyPermissions> CompanyPermissionsList = backUserService
					.findCompanyPermissions(backUser.getId());
			if (CompanyPermissionsList != null
					&& CompanyPermissionsList.size() > 0) {// 指定公司的订单
				params.put("CompanyPermissionsList", CompanyPermissionsList);
			}
			// 催收员可以看自己的
			if (backUser.getRoleId() != null
					&& BackConstant.COLLECTION_ROLE_ID.toString().equals(
							backUser.getRoleId())) {
				params.put("userRoleId", backUser.getUuid());
			}
//			if (StringUtils.isEmpty(params.get("type"))) {
//				params.put("type", "2");
//			}
			if ("0".equals(params.get("type"))) {
				params.put("type", "");
			}
			// 默认查看近一周的信息
			if(StringUtils.isEmpty(params.get("beginTime")) && StringUtils.isEmpty(params.get("endTime"))){
				params.put("beginTime",DateUtil.getDateFormat(DateUtil.getDayFirst(),"yyyy-MM-dd"));
//				params.put("beginTime",DateUtil.getDateFormat(DateUtil.getBeforeOrAfter(new Date(),-7),"yyyy-MM-dd"));
				params.put("endTime", DateUtil.getDateFormat("yyyy-MM-dd"));
			}
			// // 用户是经理或者催收主管可以查看全部催收流转日志
			// if (backUser.getRoleId() != null
			// && (BackConstant.COLLECTION_MANAGE_ROLE_ID.toString()
			// .equals(backUser.getRoleId()) || BackConstant.MANAGER_ROLE_ID
			// .toString().equals(backUser.getRoleId()))) {
			// params.put("noAdmin", Constant.ADMINISTRATOR_ID);
			// }
			// // 委外经理看自己公司的
			// if (backUser.getRoleId() != null
			// && BackConstant.OUTSOURCE_MANAGER_ROLE_ID.toString().equals(
			// backUser.getRoleId())) {
			// params.put("outSourceCompanyId", backUserDao
			// .getCompanyId(backUser.getId()));
			// }
			PageConfig<MmanLoanCollectionStatusChangeLog> pageConfig = mmanLoanCollectionStatusChangeLogService
					.findPage(params);
			model.addAttribute("pm", pageConfig);
			model.addAttribute("params", params);// 用于搜索框保留值
			// 催收组(逾期等级)
			List<SysDict> groupist = sysDictService
					.getStatus("collection_group");
			Map<String, String> dictMap = BackConstant.orderState(groupist);
			model.addAttribute("dictMap", dictMap);
			// 订单状态
			List<SysDict> orderStatusList = sysDictService
					.getStatus("xjx_collection_order_state");
			Map<String, String> orderStatusMap = BackConstant.orderState(orderStatusList);
			model.addAttribute("orderStatusMap", orderStatusMap);
			// 催收状态流转类型
			List<SysDict> collectionStatusMoveTypeList = sysDictService
					.getStatus("xjx_collection_status_move_type");
			Map<String, String> collectionStatusMoveTypeMap = BackConstant.orderState(collectionStatusMoveTypeList);
			model.addAttribute("collectionStatusMoveTypeMap", collectionStatusMoveTypeMap);
			// List<SysDict> orderlist =
			// sysDictService.getStatus("collection_group");
			// HashMap<String, String> orderMap =
			// BackConstant.orderState(orderlist);
			// model.addAttribute("currentCollectionOrderLevel", orderlist);
			// model.addAttribute("dictMap", orderMap);
		} catch (Exception e) {
			logger.error("getMmanLoanCollectionStatusChangeLog error", e);
		}
		return "mmanLoanCollectionStatusChangeLog/mmanLoanCollectionStatusChangeLogList";
	}
}
