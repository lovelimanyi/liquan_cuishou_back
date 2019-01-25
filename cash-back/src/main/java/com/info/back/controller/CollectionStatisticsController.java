package com.info.back.controller;

import com.alibaba.druid.sql.ast.statement.SQLIfStatement;
import com.info.back.service.*;
import com.info.back.utils.BackConstant;
import com.info.back.utils.ExcelUtil;
import com.info.constant.Constant;
import com.info.web.pojo.*;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.java2d.pipe.SpanIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("statistics/")
public class CollectionStatisticsController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(CollectionStatisticsController.class);

	@Autowired
	private ICollectionStatisticsService collectionStatisticsService;

	@Autowired
	private ICountCollectionAssessmentService countCollectionAssessmentService;
	@Autowired
	private ICountCollectionManageService countCollectionManageService;
	@Autowired
	private ISysDictService sysDictService;
	@Autowired
	private IMmanLoanCollectionCompanyService mmanLoanCollectionCompanyService;
	@Autowired
	private IBackUserCompanyPermissionService backUserCompanyPermissionService;
	@Autowired
	private IPersonStatisticsService personStatisticsService;
	@Autowired
	private IBigAmountStatisticsService bigAmountStatisticsService;


	/**
	 * 业务量统计_金额
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("businessMoney")
	public String countByMoney(HttpServletRequest request,HttpServletResponse response, Model model) {
		try {
			HashMap<String, Object> params = getParametersO(request);
			if(StringUtils.isEmpty(params.get("begDate")) && StringUtils.isEmpty(params.get("endDate"))){
				params.put("endDate", DateUtil.getDateForDayBefor(1, "yyyy-MM-dd"));
				params.put("begDate", DateUtil.getDateForDayBefor(7, "yyyy-MM-dd"));
			}
			CollectionStatistics cs = collectionStatisticsService.countPrincipalNew(params);
			List<Object> line = collectionStatisticsService.countBySevenDay(params);
//			StatisticsDistribute bjfb = collectionStatisticsService.countByDistribute(params);
			List<StatisticsDistribute> bjfbList = collectionStatisticsService.countByDistributeNew(params);
			model.addAttribute("bjzj", cs);
			model.addAttribute("line", line);
//			model.addAttribute("bjfb", bjfb);
			model.addAttribute("bjfbList", bjfbList);
			model.addAttribute("type","line");
			model.addAttribute("params", params);// 用于搜索框保留值

		} catch (Exception e) {
			logger.error("countByMoney error", e);
		}
		return "statistics/business";
	}
	/**
	 * 业务量统计
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("businessOrder")
	public String countByOrder(HttpServletRequest request,HttpServletResponse response, Model model) {
		try {
			HashMap<String, Object> params = getParametersO(request);
			if(StringUtils.isEmpty(params.get("begDate")) && StringUtils.isEmpty(params.get("endDate"))){
				params.put("endDate", DateUtil.getDateForDayBefor(1, "yyyy-MM-dd"));
				params.put("begDate", DateUtil.getDateForDayBefor(7, "yyyy-MM-dd"));
			}
			CollectionStatisticsOrder cs = collectionStatisticsService.countPrincipalOrder(params);
			List<Object> line = collectionStatisticsService.countOrderBySevenDay(params);
			model.addAttribute("bjzj", cs);
			model.addAttribute("line", line);
			model.addAttribute("type","line");

			model.addAttribute("params", params);// 用于搜索框保留值

		} catch (Exception e) {
			logger.error("countByOrder error", e);
		}
		return "statistics/business_order";
	}
	/**
	 * 考核统计
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/countAssessmentPage")
	public String getCountCollectionAssessmentPage(HttpServletRequest request,
												   HttpServletResponse response, Model model) {
		String returnUrl = "statistics/assessmentCountList";
		try {
			HashMap<String, Object> params = getParametersO(request);
			BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
			setDefaultDate(params);
			// 统计考核-公司  默认S1
//			if("GS".equals(params.get("type"))){
//				params.put("groupId",3);
//				params.put("orderGroupId",3);
//			}
			params.put("groupId",null);
			params.put("orderGroupId",null);
			if(!StringUtils.isEmpty(params.get("groupType"))){
				params.put("groupId",String.valueOf(params.get("groupType")).substring(0).substring(0,1));
				params.put("orderGroupId",String.valueOf(params.get("groupType")).substring(1));
			}
			if("CJ".equals(params.get("type"))){
				returnUrl = "statistics/collectionCountList";
				params.put("orderGroupId",null);
			}
			// 如果用户是催收员,只能看自己的相关统计
			if(backUser.getRoleId().equals(BackConstant.COLLECTION_ROLE_ID.toString())){
				params.put("personId", backUser.getUuid());
				params.put("roleId", backUser.getRoleId());
				params.put("personName",backUser.getUserName());
			}
			// 如果用户是委外经理,只能看自己公司的相关统计
//			if(backUser.getRoleId().equals(BackConstant.OUTSOURCE_MANAGER_ROLE_ID.toString())){
//				params.put("companyId",backUser.getCompanyId());
//			}

			PageConfig<CountCollectionAssessment> pageConfig = countCollectionAssessmentService.findPage(params);

			// 查询出当前用户可以查看的公司
			if(!"10001".equals(backUser.getRoleId()) && !"10021".equals(backUser.getRoleId())){
				List<BackUserCompanyPermissions> companies = backUserCompanyPermissionService.getPermissionSCompaniesByUser(backUser.getId());
				List<String> companyIds = new ArrayList<>();
				for (BackUserCompanyPermissions company:companies) {
					companyIds.add(company.getCompanyId());
				}

				List<CountCollectionAssessment> items = pageConfig.getItems();
				if(items != null && items.size() > 0){
					for (int i = 1; i < items.size(); i ++) {
						if(!companyIds.contains(items.get(i).getCompanyId())){
							items.get(i).setCompanyTitle("* * * * * * * * 委外公司");
						}
					}
				}
			}

			List<SysDict> dict =sysDictService.findDictByType("xjx_overdue_level");
			// 所有公司
			List<MmanLoanCollectionCompany> companys =mmanLoanCollectionCompanyService.findCompanyByUserId(backUser);
			List<MmanLoanCollectionCompany> coms = new ArrayList<>(); // 存放登录角色权限内可以查看的公司
			coms = getAccessCompanies(backUser, companys, coms);
			model.addAttribute("groupNameTypeMap", BackConstant.groupNameTypeMap);
			model.addAttribute("groupLevel", String.valueOf(params.get("groupType")));
			model.addAttribute("pm", pageConfig);
			model.addAttribute("dict", dict);
			model.addAttribute("company",coms);
			model.addAttribute("params", params);// 用于搜索框保留值
		} catch (Exception e) {
			logger.error("getCountCollectionAssessmentPage error", e);
		}

		return returnUrl;
	}
	/**
	 * 考核统计导出
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/reportAssessment")
	public void reportAssessment(HttpServletResponse response,
								 HttpServletRequest request, Model model) {
		HashMap<String, Object> params = getParametersO(request);
		try {
			BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);

			// 如果用户是催收员,只能看自己的相关统计
			if(backUser.getRoleId().equals(BackConstant.COLLECTION_ROLE_ID.toString())){
				params.put("personId", backUser.getUuid());
				params.put("roleId", backUser.getRoleId());
				params.put("personName",backUser.getUserName());
			}
			// 如果用户是委外经理,只能看自己公司的相关统计
			if(backUser.getRoleId().equals(BackConstant.OUTSOURCE_MANAGER_ROLE_ID.toString())){
				params.put("companyId",backUser.getCompanyId());
			}

			if(BackConstant.COLLECTION_ROLE_ID.equals(backUser.getRoleId())){
				params.put("personId", backUser.getUuid());
			}
			params.put("groupId",null);
			params.put("orderGroupId",null);
			if(!StringUtils.isEmpty(params.get("groupType"))){
				params.put("groupId",String.valueOf(params.get("groupType")).substring(0).substring(0,1));
				params.put("orderGroupId",String.valueOf(params.get("groupType")).substring(1));
			}
			params.put("method", "C");
			int size = 50000;
			int total = 0;
			params.put(Constant.PAGE_SIZE, size);
			int totalPageNum = countCollectionAssessmentService.findAllCount(params);
			if (totalPageNum > 0) {
				if (totalPageNum % size > 0) {
					total = totalPageNum / size + 1;
				} else {
					total = totalPageNum / size;
				}
			}
			String title = "考核统计-每日";
			String[] titles = {  "日期", "催收公司", "催收组", "订单组", "催收员", "本金金额",
					"已还本金", "未还本金", "本金摧回率", "迁徙率", "滞纳金金额", "已还滞纳金",
					"未还滞纳金", "滞纳金摧回率","订单量","已还订单量","订单还款率" }; // "已操作订单","风控标记单量",
			if("LJ".equals(params.get("type"))){
				titles[0] = "序号";
				title = "考核统计-累计";
			} else if ("GS".equals(params.get("type"))){
				titles[0] = "序号";
				title = "考核统计-公司";
			} else if ("CJ".equals(params.get("type"))){
				titles = new String[]{"日期", "催收公司", "催收组",  "催收员", "待催收订单","已催收订单", "催记率"};
				title = "催收记录统计";
			}
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			ExcelUtil.setFileDownloadHeader(request, response, title+".xls");
			response.setContentType("application/msexcel");// 定义输出类型
			SXSSFWorkbook workbook = new SXSSFWorkbook(10000);

			int j=1;
			for (int i = 1; i <= total; i++) {
				params.put(Constant.CURRENT_PAGE, i);
				PageConfig<CountCollectionAssessment> pm = countCollectionAssessmentService.findPage(params);
				List<CountCollectionAssessment> list = pm.getItems();
				List<Object[]> contents = new ArrayList<Object[]>();
				for (CountCollectionAssessment r : list) {
					String[] conList = new String[titles.length];
					if("LJ".equals(params.get("type")) || "GS".equals(params.get("type"))){
						conList[0] = j+"";
					}else{
						conList[0] = DateUtil.getDateFormat(r.getCountDate(),"yyyy-MM-dd");
					}
					if ("CJ".equals(params.get("type"))){
						conList[1] = r.getCompanyTitle();
						conList[2] = r.getGroupName();
						conList[3] = r.getPersonName();
						conList[4] = r.getUndoneOrderNum().toString();
						conList[5] = r.getDoneOrderNum().toString();
						conList[6] = r.getOrderRate() + "%";
					} else {
						conList[1] = r.getCompanyTitle();
						conList[2] = r.getGroupName();
						conList[3] = r.getGroupOrderName();
						conList[4] = r.getPersonName();
						conList[5] = r.getLoanMoney().toString();
						conList[6] = r.getRepaymentMoney().toString();
						conList[7] = r.getNotYetRepaymentMoney().toString();
						conList[8] = r.getRepaymentReta() + "%";
						conList[9] = r.getMigrateRate() == null ? "--" : r.getMigrateRate().compareTo(new BigDecimal(-1)) == 0 ? "--" : r.getMigrateRate() + "%";
						conList[10] = r.getPenalty().toString();
						conList[11] = r.getRepaymentPenalty().toString();
						conList[12] = r.getNotRepaymentPenalty().toString();
						conList[13] = r.getPenaltyRepaymentReta() + "%";
						conList[14] = r.getOrderTotal().toString();
//					conList[15] = r.getDisposeOrderNum().toString();
//					conList[16] = r.getRiskOrderNum().toString();
						conList[15] = r.getRepaymentOrderNum().toString();
						conList[16] = r.getRepaymentOrderRate() + "%";
					}
					contents.add(conList);
					j++;
				}
				ExcelUtil.buildExcel(workbook, title, titles, contents, i, pm.getTotalPageNum(), os);
			}
		} catch (Exception e) {
			logger.error("考核统计-每日导出excel失败", e);
		}
	}

	/**
	 * 管理跟踪统计
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/countManagePage")
	public String getCountCollectionManagePage(HttpServletRequest request,HttpServletResponse response, Model model) {
		HashMap<String, Object> params = getParametersO(request);
		try {
			BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
			setDefaultDate(params);

			// 如果用户是催收员,只能看自己的相关统计
			if(backUser.getRoleId().equals(BackConstant.COLLECTION_ROLE_ID.toString())){
				params.put("personId", backUser.getUuid());
				params.put("roleId", backUser.getRoleId());
				params.put("personName",backUser.getUserName());
			}
			// 如果用户是委外经理,只能看自己公司的相关统计
			if(backUser.getRoleId().equals(BackConstant.OUTSOURCE_MANAGER_ROLE_ID.toString())){
				params.put("companyId",backUser.getCompanyId());
			}

			// 所有公司
			List<MmanLoanCollectionCompany> companys =mmanLoanCollectionCompanyService.findCompanyByUserId(backUser);
			List<MmanLoanCollectionCompany> coms = new ArrayList<>(); // 存放登录角色权限内可以查看的公司
			coms = getAccessCompanies(backUser, companys, coms);  // 获取到用户权限内可以查看的公司

			PageConfig<CountCollectionManage> pageConfig = countCollectionManageService.findPage(params);
			List<SysDict> dict =sysDictService.findDictByType("xjx_overdue_level");
			List<MmanLoanCollectionCompany> company =mmanLoanCollectionCompanyService.findCompanyByUserId(backUser);
			model.addAttribute("pm", pageConfig);
			model.addAttribute("dict", dict);
			model.addAttribute("company",coms);
		} catch (Exception e) {
			logger.error("getCountCollectionManagePage error", e);
		}
		model.addAttribute("params", params);// 用于搜索框保留值
		return "statistics/manageCountList";
	}



	/**
	 * 管理统计导出
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/reportManage")
	public void reportManage(HttpServletResponse response,HttpServletRequest request, Model model) {
		HashMap<String, Object> params = getParametersO(request);
		try {
			BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);

			// 如果用户是催收员,只能看自己的相关统计
			if(backUser.getRoleId().equals(BackConstant.COLLECTION_ROLE_ID.toString())){
				params.put("personId", backUser.getUuid());
				params.put("roleId", backUser.getRoleId());
				params.put("personName",backUser.getUserName());
			}
			// 如果用户是委外经理,只能看自己公司的相关统计
			if(backUser.getRoleId().equals(BackConstant.OUTSOURCE_MANAGER_ROLE_ID.toString())){
				params.put("companyId",backUser.getCompanyId());
			}

			if(BackConstant.COLLECTION_ROLE_ID.equals(backUser.getRoleId())){
				params.put("personId", backUser.getUuid());
			}
			params.put("method", "C");
			int size = 50000;
			int total = 0;
			params.put(Constant.PAGE_SIZE, size);
			int totalPageNum = countCollectionManageService.findAllCount(params);
			if (totalPageNum > 0) {
				if (totalPageNum % size > 0) {
					total = totalPageNum / size + 1;
				} else {
					total = totalPageNum / size;
				}
			}
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			ExcelUtil.setFileDownloadHeader(request, response, "管理跟踪统计.xls");
			response.setContentType("application/msexcel");// 定义输出类型
			SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
			String[] titles = {  "日期", "催收公司", "催收组", "订单组", "催收员", "本金金额",
					"已还本金", "未还本金", "本金摧回率", "迁徙率", "滞纳金金额", "已还滞纳金",
					"未还滞纳金", "滞纳金摧回率","订单量","已还订单量","订单还款率" }; // ,"已操作订单"
			for (int i = 1; i <= total; i++) {
				params.put(Constant.CURRENT_PAGE, i);
				PageConfig<CountCollectionManage> pm = countCollectionManageService.findPage(params);
				List<CountCollectionManage> list = pm.getItems();
				List<Object[]> contents = new ArrayList<Object[]>();
				for (CountCollectionManage r : list) {
					String[] conList = new String[titles.length];
					if(r.getCountDate() != null){
						conList[0] = DateUtil.getDateFormat(r.getCountDate(),"yyyy-MM-dd");
					}
					conList[1] = r.getCompanyTitle();
					conList[2] = r.getGroupName();
					conList[3] = r.getGroupOrderName();
					conList[4] = r.getPersonName();
					conList[5] = r.getLoanMoney().toString();
					conList[6] = r.getRepaymentMoney().toString();
					conList[7] = r.getNotYetRepaymentMoney().toString();
					conList[8] = r.getRepaymentReta()+"%";
					conList[9] = r.getMigrateRate()+"%";
					conList[10] = r.getPenalty().toString();
					conList[11] = r.getRepaymentPenalty().toString();
					conList[12] = r.getNotRepaymentPenalty().toString();
					conList[13] = r.getPenaltyRepaymentReta()+"%";
					conList[14] = r.getOrderTotal().toString();
//					conList[15] = r.getDisposeOrderNum().toString();
					conList[15] = r.getRepaymentOrderNum().toString();
					conList[16] = r.getRepaymentOrderRate()+"%";
					contents.add(conList);
				}
				ExcelUtil.buildExcel(workbook, "管理跟踪统计", titles, contents, i, pm.getTotalPageNum(), os);
			}
		} catch (Exception e) {
			logger.error("管理跟踪统计导出excel失败", e);
		}
	}

	@RequestMapping(value = { "/countCashBusinessPage", "/countLoanPage" })
	public String getCountCashBusinessPage(HttpServletRequest request,HttpServletResponse response, Model model) {
		HashMap<String, Object> params = getParametersO(request);
		try {
			BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
			if(StringUtils.isEmpty(params.get("begDate")) && StringUtils.isEmpty(params.get("endDate"))){
				params.put("endDate", DateUtil.getDateForDayBefor(1, "yyyy-MM-dd"));
				params.put("begDate", DateUtil.getDateForDayBefor(1, "yyyy-MM-dd"));
			}
			if(BackConstant.COLLECTION_ROLE_ID.equals(backUser.getRoleId())){
				params.put("personId", backUser.getUuid());
			}
			PageConfig<CountCashBusiness> pageConfig = countCollectionManageService.getCountCashBusinessPage(params);
			model.addAttribute("pm", pageConfig);
		} catch (Exception e) {
			logger.error("getCountCashBusinessPage error", e);
		}
		model.addAttribute("params", params);// 用于搜索框保留值
		String requestUrl = request.getRequestURL().toString();
		String servletUrl = requestUrl.substring(requestUrl.lastIndexOf("/"),requestUrl.length());
		if (servletUrl.contains("countLoanPage")){
			return "statistics/countLoanPage";
		}else {
			return "statistics/cashBusiness";
		}

	}

	/**
	 * 根据用户（委外经理）的权限获取用户权限内的公司
	 * @param backUser 当前登录用户
	 * @param companys 所有公司
	 * @param coms  登录用户权限内的公司
	 * @return
	 */
	private List<MmanLoanCollectionCompany> getAccessCompanies(BackUser backUser, List<MmanLoanCollectionCompany> companys, List<MmanLoanCollectionCompany> coms) {
		if(BackConstant.OUTSOURCE_MANAGER_ROLE_ID.toString().equals(backUser.getRoleId())){
			List<String> list = new ArrayList<>();
			List<BackUserCompanyPermissions> permissionSCompaniesByUser = backUserCompanyPermissionService.getPermissionSCompaniesByUser(backUser.getId());
			for (BackUserCompanyPermissions com:permissionSCompaniesByUser) {
				list.add(com.getCompanyId());
			}

			for (MmanLoanCollectionCompany company:companys) {
				if(list.contains(company.getId())){
					coms.add(company);
				}
			}
		}else if(BackConstant.COLLECTION_ROLE_ID.toString().equals(backUser.getRoleId())){
			// 催收员只能看到自己公司
			coms.add(mmanLoanCollectionCompanyService.getCompanyById(backUser.getCompanyId()));
		}else{
			coms = companys;
		}
		return coms;
	}

	/**
	 * 根据传递进来的参数判定，如果当日是1号,展示上个月的1号到最后一天 ; 不是的话展示当月1号到当天
	 * @param params 开始时间和结束时间
	 */
	private void setDefaultDate(HashMap<String, Object> params) {
		if(StringUtils.isEmpty(params.get("begDate")) && StringUtils.isEmpty(params.get("endDate"))){
			//如果当日是1号,展示上个月的1号到最后一天 ; 不是的话展示当月1号到当天
			if(DateUtil.getDayFirst().equals(new Date())){
				params.put("begDate", DateUtil.getDateFormat(DateUtil.getNextMon(-1),"yyyy-MM-dd"));
				params.put("endDate", DateUtil.getDateForDayBefor(1, "yyyy-MM-dd"));
			}else{
				params.put("begDate", DateUtil.getDateFormat(DateUtil.getDayFirst(),"yyyy-MM-dd"));
				params.put("endDate", DateUtil.getDateForDayBefor(0, "yyyy-MM-dd"));
			}
		}
	}
	/**
	 * 小额统计
	 * @param
	 */
	@RequestMapping("smallAmountStatistics")
	public String smallAmountStatistics(@RequestParam(value = "Flag") String Flag,HttpServletRequest request,Model model){
		String url = "";
		try {
			HashMap<String, Object> params = getParametersO(request);
			if (StringUtils.isEmpty(params.get("orderBy"))){
				params.put("orderBy","groupLevel");
			}
			BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
			if(StringUtils.isEmpty(params.get("createDate")) && StringUtils.isEmpty(params.get("createDate"))){
				params.put("createDate",DateUtil.getDateFormat(new Date(),"yyyy-MM-dd"));
			}
			//设置登录账户可查看的公司权限
			PageConfig<PersonStatistics> pageConfig = new PageConfig<>();

			PageConfig<PersonNewStatistics> pageConfigNew = new PageConfig<>();

			//如果Flag 为person则查看个人统计，如果为 company则查看公司统计
			if ("person".equals(Flag)){
				handleCompanyPermission(backUser,params,model);
				url = "statistics/personStatistics";
				pageConfig = personStatisticsService.findPage(params);
			}else if("company".equals(Flag)){
				url = "statistics/companyStatistics";
				pageConfig = personStatisticsService.findCompanyPage(params);
				List<String> companyIds = getCompanyIds(backUser);
				if(pageConfig.getItems() != null && pageConfig.getItems().size() > 0){
					for (int i = 0; i < pageConfig.getItems().size(); i ++) {
						if(!companyIds.contains(pageConfig.getItems().get(i).getCompanyId())){
							pageConfig.getItems().get(i).setCompanyName("* * * * * * * * ");
						}
					}
				}
				handleCompanyPermission(backUser,params,model);
			}else if ("other".equals(Flag)){
				url = "statistics/companyStatisticsOther";
				pageConfig = personStatisticsService.findCompanyOtherPage(params);
			}else if ("personNew".equals(Flag)){
				handleCompanyPermission(backUser,params,model);
				url = "statistics/personNewPage";
				pageConfigNew = personStatisticsService.personNewPage(params);
			}else if("companyNew".equals(Flag)){
				url = "statistics/companyNewPage";
				pageConfigNew = personStatisticsService.companyNewPage(params);
				List<String> companyIds = getCompanyIds(backUser);
				if(pageConfigNew.getItems() != null && pageConfigNew.getItems().size() > 0){
					for (int i = 0; i < pageConfigNew.getItems().size(); i ++) {
						if(!companyIds.contains(pageConfigNew.getItems().get(i).getCompanyId())){
							pageConfigNew.getItems().get(i).setCompanyName("* * * * * * * * ");
						}
					}
				}
				handleCompanyPermission(backUser,params,model);
			}
			model.addAttribute("groupLevelMap", BackConstant.smallGroupNameMap);
			model.addAttribute("groupLevel", String.valueOf(params.get("groupLevel")));
			model.addAttribute("dictMap",BackConstant.groupNameMap);
			model.addAttribute("list",pageConfig.getItems());
			model.addAttribute("listNew",pageConfigNew.getItems());
			model.addAttribute("pm", pageConfig);
			model.addAttribute("params", params);// 用于搜索框保留值
			model.addAttribute("merchantNoMap",BackConstant.merchantNoMap);

		}catch ( Exception e){
			e.printStackTrace();
		}
		return url;
	}
	/**
	 * 小额统计导出
	 * @param
	 */
	@RequestMapping("/reportExcel")
	public void reportExport(HttpServletResponse response, HttpServletRequest request, Model model) {
		try {

			String title = "";
			String[] titles = null; // "已操作订单","风控标记单量",

		HashMap<String, Object> params = getParametersO(request);
		if (StringUtils.isEmpty(params.get("orderBy"))){
			params.put("orderBy","groupLevel");
		}
		BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
		if(StringUtils.isEmpty(params.get("createDate")) && StringUtils.isEmpty(params.get("createDate"))){
			params.put("createDate",DateUtil.getDateFormat(new Date(),"yyyy-MM-dd"));
		}
		List<PersonNewStatistics> list = new ArrayList<>();
		String Flag = params.get("Flag").toString();
        if ("personNew".equals(Flag)){
			handleCompanyPermission(backUser,params,model);
			list = personStatisticsService.personNewList(params);
			title = "小额个人统计";
			titles= new String[] {  "统计日期", "催收公司", "催收组", "入催本金",
					"入催订单数", "当日完成单数", "当日完成金额", "完成单数", "完成本金",
					"完成订单滞纳金", "实收滞纳金","不考核滞纳金","本金完成率","滞纳金完成率","催收员姓名" };

		}else if("companyNew".equals(Flag)){
			list = personStatisticsService.companyNewList(params);
			List<String> companyIds = getCompanyIds(backUser);
				for (int i = 0; i < list.size(); i ++) {
					if(!companyIds.contains(list.get(i).getCompanyId())){
						list.get(i).setCompanyName("* * * * * * * * ");
					}
				}
			handleCompanyPermission(backUser,params,model);
			title = "小额公司统计";
			titles= new String[] {  "统计日期", "催收公司", "催收组", "入催本金",
					"入催订单数", "当日完成单数", "当日完成金额", "完成单数", "完成本金",
					"完成订单滞纳金", "实收滞纳金","不考核滞纳金","本金完成率","滞纳金完成率" };
		}
		int count = list.size();
		int size = 50000;
		int totalSheetNum = 0;
		params.put(Constant.PAGE_SIZE, size);
		if (count > 0) {
			if (count % size > 0) {
				totalSheetNum = count / size + 1;
			} else {
				totalSheetNum = count / size;
			}
		}

		OutputStream os = response.getOutputStream();

		response.reset();// 清空输出流
		ExcelUtil.setFileDownloadHeader(request, response, title+".xlsx");
		response.setContentType("application/msexcel");// 定义输出类型
		SXSSFWorkbook workbook = new SXSSFWorkbook(10000);


		for (int i = 1;i<=totalSheetNum;i++){
			List<Object[]> contents = new ArrayList<Object[]>();
			for (PersonNewStatistics report : list){
				String[] conList = new String[titles.length];
				conList[0] = DateUtil.getDateFormat(report.getCreateDate(),"yyyy-MM-dd");
				if ("personNew".equals(Flag)){
					MmanLoanCollectionCompany company = mmanLoanCollectionCompanyService.getCompanyById(report.getCompanyId());
					conList[1] = company.getTitle();
					conList[14] = report.getBackUserName();
				}else if("companyNew".equals(Flag)){
					conList[1] = report.getCompanyName();
				}
				conList[2] = BackConstant.groupNameMap.get(report.getGroupLevel());
				conList[3] = report.getTotalPrincipal().toString();
//				conList[4] = report.getTotalPenalty().toString();
				conList[4] = String.valueOf(report.getTotalOrderCount());
				conList[5] = String.valueOf(report.getTodayDoneCount());
				conList[6] = report.getTodayDoneMoney().toString();
				conList[7] = String.valueOf(report.getDoneOrderCount());
				conList[8] = report.getDonePrincipal().toString();
				conList[9] = report.getDonePenalty().toString();
				conList[10] = report.getRealgetPenalty().toString();
				conList[11] = report.getNoCheckPenalty().toString();
				conList[12] = report.getCleanPrincipalProbability().toString()+"%";
				conList[13] = report.getCleanPenaltyProbability().toString()+"%";

				contents.add(conList);

			}
			ExcelUtil.buildExcel(workbook, title, titles, contents, i, totalSheetNum, os);
		}

		} catch (IOException e) {
			e.printStackTrace();
		}



	}





	/**
	 * 大额统计
	 * @param
	 */
	@RequestMapping("bigAmountStatistics")
	public String bigAmountStatistics(@RequestParam(value = "Flag") String Flag, HttpServletRequest request,Model model){
		String url = "";
		try {
			HashMap<String, Object> params = getParametersO(request);
			BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
			if(StringUtils.isEmpty(params.get("createDate")) && StringUtils.isEmpty(params.get("createDate"))){
				params.put("createDate",DateUtil.getDateFormat(new Date(),"yyyy-MM-dd"));
			}
			PageConfig<BigAmountStatistics>  pageConfig = new PageConfig<>();

			//如果Flag 为person则查看个人统计，如果为 company则查看公司统计
			if ("person".equals(Flag)){
				handleCompanyPermission(backUser,params,model);
				url = "statistics/bigPersonStatistics";
				pageConfig = bigAmountStatisticsService.findPage(params);
			}else if("company".equals(Flag)){
				url = "statistics/bigCompanyStatistics";
				pageConfig = bigAmountStatisticsService.findCompanyPage(params);

				List<String> companyIds = getCompanyIds(backUser);
				if(pageConfig.getItems() != null && pageConfig.getItems().size() > 0){
					for (int i = 0; i < pageConfig.getItems().size(); i ++) {
						if(!companyIds.contains(pageConfig.getItems().get(i).getCompanyId())){
							pageConfig.getItems().get(i).setCompanyName("* * * * * * * * ");
						}
					}
				}
				handleCompanyPermission(backUser,params,model);
			}
			model.addAttribute("groupLevelMap", BackConstant.bigGroupNameMap);
			model.addAttribute("groupLevel", String.valueOf(params.get("groupLevel")));
			model.addAttribute("dictMap",BackConstant.groupNameMap);
			model.addAttribute("list",pageConfig.getItems());
			model.addAttribute("pm", pageConfig);
			model.addAttribute("params", params);// 用于搜索框保留值
		}catch ( Exception e){
			e.printStackTrace();
		}
		return url;
	}

	private List<String> getCompanyIds(BackUser backUser) {
		List<MmanLoanCollectionCompany> companys =mmanLoanCollectionCompanyService.selectCompanyList();
		List<MmanLoanCollectionCompany> coms = new ArrayList<>(); // 存放登录角色权限内可以查看的公司
		coms = getAccessCompanies(backUser, companys, coms);
		List<String> companyIds = new ArrayList<>();
		if (coms != null && coms.size()>=1) {
			for (MmanLoanCollectionCompany company : coms) {
				companyIds.add(company.getId());
			}
		}
		return companyIds;
	}

	public void handleCompanyPermission(BackUser backUser ,HashMap<String, Object> params ,Model model){
		if(StringUtils.isEmpty(params.get("createDate")) && StringUtils.isEmpty(params.get("createDate"))){
			params.put("createDate",DateUtil.getDateFormat(new Date(),"yyyy-MM-dd"));
		}
		// 如果用户是催收员,只能看自己的相关统计
		if(backUser.getRoleId().equals(BackConstant.COLLECTION_ROLE_ID.toString())){
			params.put("backUserId",backUser.getId());
			params.put("uuid", backUser.getUuid());
			params.put("roleId", backUser.getRoleId());
			params.put("backUserName",backUser.getUserName());
			MmanLoanCollectionCompany company = mmanLoanCollectionCompanyService.getCompanyById(backUser.getCompanyId());
			model.addAttribute("companyName",company.getTitle());
		}else {
			// 所有公司--系统管理员可以查看所有公司
			List<MmanLoanCollectionCompany> companys =mmanLoanCollectionCompanyService.selectCompanyList();
			List<MmanLoanCollectionCompany> coms = new ArrayList<>(); // 存放登录角色权限内可以查看的公司
			coms = getAccessCompanies(backUser, companys, coms);
			if (coms != null && coms.size()>=1){
				List<String> companyIds = new ArrayList<>();
				for (MmanLoanCollectionCompany company:coms) {
					companyIds.add(company.getId());
				}
				params.put("companyIds",companyIds);
				model.addAttribute("company",coms);
			}

		}
	}


	/**
	 * 小额统计-个人    小额统计-公司
	 * 大额统计-个人    大额统计-公司
	 * @param beginTime
	 * @param endTime
	 */

	@RequestMapping("doStatistics")
	public void doStatistics(String beginTime,String endTime){
		personStatisticsService.doStatistics(beginTime,endTime);
		personStatisticsService.doNewStatistics(beginTime,endTime);
//		bigAmountStatisticsService.doStatistics(beginTime,endTime);
	}





}
