package com.info.back.controller;

import java.io.OutputStream;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.info.back.utils.ExcelUtil;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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

	/**
	 * 流转日志导出
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/execlToStatusChangeLog")
	public void reportManage(HttpServletResponse response,HttpServletRequest request, Model model) {
		HashMap<String, Object> params = getParametersO(request);
		System.out.println("map==========="+params.size());
		try {
			BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
			List<BackUserCompanyPermissions> CompanyPermissionsList=backUserService.findCompanyPermissions(backUser.getId());
			if(CompanyPermissionsList!=null){
				params.put("CompanyPermissionsList", CompanyPermissionsList);
			}
			if(backUser.getRoleId()!=null&&BackConstant.COLLECTION_ROLE_ID.toString().equals(backUser.getRoleId())){
				params.put("roleUserId", backUser.getId());
			}
			int size = 50000;
			int total = 0;
			params.put(Constant.PAGE_SIZE, size);
			int totalPageNum = mmanLoanCollectionStatusChangeLogService.getAllCount(params);
			if (totalPageNum > 0) {
				if (totalPageNum % size > 0) {
					total = totalPageNum / size + 1;
				} else {
					total = totalPageNum / size;
				}
			}
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			ExcelUtil.setFileDownloadHeader(request, response, "催收流转日志.xlsx");
			response.setContentType("application/msexcel");// 定义输出类型
			SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
			String[] titles = {  "序号", "借款编号", "操作前状态", "操作后状态","操作类型","催收组","当前催收员","订单组","创建时间","操作人","操作备注"};
			List<SysDict> dictlist=sysDictService.getStatus("collection_group");
			HashMap<String, String> dictMap=BackConstant.orderState(dictlist);
			List<SysDict> statulist=sysDictService.getStatus("xjx_collection_order_state");
			HashMap<String, String> StatuMap=BackConstant.orderState(statulist);
			for (int i = 1; i <= total; i++) {
				params.put(Constant.CURRENT_PAGE, i);
				PageConfig<MmanLoanCollectionStatusChangeLog> pm = mmanLoanCollectionStatusChangeLogService.findPage(params);
				List<MmanLoanCollectionStatusChangeLog> list = pm.getItems();
				System.out.println("list>>>>>>>>="+list.size());
				List<Object[]> contents = new ArrayList<Object[]>();
				for (MmanLoanCollectionStatusChangeLog r : list) {
					String[] conList = new String[titles.length];
					conList[0] = String.valueOf(i);
					conList[1] = r.getLoanCollectionOrderId();
					conList[2] = dictMap.get(r.getBeforeStatus() + "");
					conList[3] = dictMap.get(r.getAfterStatus() + "");
					conList[4] = StatuMap.get(r.getType());
					conList[5] = dictMap.get(r.getCurrentCollectionUserLevel() + "");
					conList[6] = r.getCurrentCollectionUserId() == null ? "" : r.getCurrentCollectionUserId();
					conList[7] = dictMap.get(r.getCurrentCollectionOrderLevel()+"");
					conList[8] = r.getCreateDate() == null ? "" : DateUtil.getDateFormat(r.getCreateDate(),"yyyy-MM-dd HH:mm:ss");
					conList[9] = r.getOperatorName() == null ? "" : r.getOperatorName();
					conList[10] = r.getRemark() == null ? "" : r.getRemark();
					contents.add(conList);
				}
				ExcelUtil.buildExcel(workbook, "催收流转日志", titles, contents, i, total, os);
			}
		} catch (Exception e) {
			logger.error("催收流转日志导出excel失败", e);
		}
	}
}
