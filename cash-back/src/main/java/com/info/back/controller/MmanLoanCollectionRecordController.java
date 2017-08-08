package com.info.back.controller;

import com.info.back.service.IBackUserService;
import com.info.back.service.IFengKongService;
import com.info.back.service.IMmanLoanCollectionRecordService;
import com.info.back.service.ISysDictService;
import com.info.back.utils.BackConstant;
import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.constant.Constant;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.BackUserCompanyPermissions;
import com.info.web.pojo.MmanLoanCollectionRecord;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 催收记录控制层
 *
 * @author hxj
 *
 */


@Controller
@RequestMapping("/collectionRecord")
public class MmanLoanCollectionRecordController extends BaseController {
	private static Logger logger = Logger.getLogger(IndexController.class);
	@Autowired
	private IMmanLoanCollectionRecordService mmanLoanCollectionRecordService;
	@Autowired
	private ISysDictService sysDictService;
	@Autowired
	private IBackUserService backUserService;
	@Autowired
	private IFengKongService fengKongService;


	@RequestMapping("/getMmanLoanCollectionRecord")
	public String getView(HttpServletRequest request,Model model) {
		try {
			HashMap<String, Object> params = getParametersO(request);
			BackUser backUser = (BackUser) request.getSession().getAttribute(
					Constant.BACK_USER);
			List<BackUserCompanyPermissions> CompanyPermissionsList=backUserService.findCompanyPermissions(backUser.getId());
			if(CompanyPermissionsList!=null&&CompanyPermissionsList.size()>0){//指定公司的订单
				params.put("CompanyPermissionsList", CompanyPermissionsList);
			}
//			if(backUser.getGroupLevel() == null){
//				params.put("overdueLevel", "3");
//			}
			// 催收员可以看自己的
			if (backUser.getRoleId() != null
					&& BackConstant.COLLECTION_ROLE_ID.toString().equals(
					backUser.getRoleId())) {// 如果是催收员只能看自己的订单
				params.put("userRoleId", backUser.getUuid());
			}

//			if(params.get("collectionStatus") == null){
//				params.put("collectionStatus", "0");
//			}

			if ("-1".equals(params.get("collectionStatus"))) {
				params.put("collectionStatus", "");
			}

			// 默认查看近一周的信息
			if(params.get("collectionDateBegin") == null && params.get("collectionDateEnd") == null){
				params.put("collectionDateBegin",DateUtil.getDateFormat(DateUtil.getBeforeOrAfter(new Date(),-6),"yyyy-MM-dd"));
				params.put("collectionDateEnd", DateUtil.getDateFormat(new Date(),"yyyy-MM-dd"));
			}
//			if(StringUtils.isEmpty(String.valueOf(params.get("overdueLevel"))) || params.get("overdueLevel") == null){
//				params.put("overdueLevel", "0");
//			}
//			// 用户是经理或者催收主管可以查看全部催收流转日志
//			if (backUser.getRoleId() != null
//					&& (BackConstant.COLLECTION_MANAGE_ROLE_ID.toString()
//							.equals(backUser.getRoleId()) || BackConstant.MANAGER_ROLE_ID
//							.toString().equals(backUser.getRoleId()))) {
//				params.put("noAdmin", Constant.ADMINISTRATOR_ID);
//			}
//			// 委外经理看自己公司的
//			if (backUser.getRoleId() != null
//					&& BackConstant.OUTSOURCE_MANAGER_ROLE_ID.toString().equals(
//							backUser.getRoleId())) {
//				params.put("outSourceCompanyId", backUserDao
//						.getCompanyId(backUser.getId()));
//			}

			PageConfig<MmanLoanCollectionRecord> pageConfig = mmanLoanCollectionRecordService
					.findPage(params);
			model.addAttribute("pm", pageConfig);
//			model.addAttribute("record", 1);
			model.addAttribute("params", params);// 用于搜索框保留值
			// 查询所有的催收状态
			model.addAttribute("status",
					sysDictService.getStatus("xjx_collection_order_state"));
			// 查询所有的施压等级
//			model.addAttribute("stressLevel",
//					sysDictService.getStatus("xjx_stress_level"));
			// 查询所有的催收类型
			model.addAttribute("collectionType",
					sysDictService.getStatus("xjx_collection_type"));
			model.addAttribute("fengKongLabels",fengKongService.getFengKongList());  // 风控标签
			model.addAttribute("labels",BackConstant.fengKongLabels);  // 用于前台页面风控标签下拉框（选中效果）
		} catch (Exception e) {
			logger.error("getMmanLoanCollectionRecordPage error", e);
		}
		return "mmanLoanCollectionRecord/mmanLoanCollectionRecordList";
	}

	@RequestMapping("/saveOrUpdateCollectionRecord")
	public String insert(HttpServletRequest request,
						 HttpServletResponse response, MmanLoanCollectionRecord record,
						 Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		String url = null;
		String erroMsg = null;
		try {
			if ("toJsp".equals(String.valueOf(params.get("type")))) {
				if (params.containsKey("id")) {
					// 更新的页面跳转
					record = mmanLoanCollectionRecordService.getOne(params);
					model.addAttribute("collectionRecord", record);
				}
				url = "mmanLoanCollectionRecord/saveOrUpdateCollectionRecord";
			} else {
				// 更新或者添加操作
				if (record.getId() != null) {
					mmanLoanCollectionRecordService.update(record);
				} else {
					mmanLoanCollectionRecordService.insert(record);
				}
				SpringUtils.renderDwzResult(response, true, "操作成功",
						DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
								.toString());
			}

		} catch (Exception e) {
			logger.error("saveOrUpdateCollectionRecord error", e);
		}
		model.addAttribute(MESSAGE, erroMsg);
		model.addAttribute("params", params);
		return url;
	}
}
