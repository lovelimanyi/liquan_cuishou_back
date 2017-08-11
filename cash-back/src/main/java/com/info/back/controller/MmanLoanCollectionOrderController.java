package com.info.back.controller;

import com.info.back.service.IBackUserService;
import com.info.back.service.IMmanLoanCollectionCompanyService;
import com.info.back.service.IMmanLoanCollectionOrderService;
import com.info.back.service.ISysDictService;
import com.info.back.utils.BackConstant;
import com.info.back.utils.ExcelUtil;
import com.info.constant.Constant;
import com.info.web.pojo.*;
import com.info.web.util.CompareUtils;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/mmanLoanCollectionOrder")
public class MmanLoanCollectionOrderController extends BaseController{
	private static Logger logger = Logger.getLogger(MmanLoanCollectionOrderController.class);
	@Autowired
	private IMmanLoanCollectionOrderService mmanLoanCollectionOrderService;
	@Autowired
	private IMmanLoanCollectionCompanyService mmanLoanCollectionCompanyService;
	@Autowired
	private IBackUserService backUserService;
	@Autowired
	private ISysDictService sysDictService;
	@RequestMapping("/technologyOrderList")
	public String technologyOrderList(HttpServletRequest request,HttpServletResponse response,Model model){
		try {
			HashMap<String, Object> params = getParametersO(request);
			BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
			//查询公司列表
			MmanLoanCollectionCompany mmanLoanCollectionCompany = new MmanLoanCollectionCompany();
			PageConfig<OrderBaseResult> pageConfig = mmanLoanCollectionOrderService.getPage(params);
			model.addAttribute("pm", pageConfig);
			model.addAttribute("params", params);
			model.addAttribute("ListMmanLoanCollectionCompany", mmanLoanCollectionCompanyService.getList(mmanLoanCollectionCompany));
			model.addAttribute("dictMap",BackConstant.groupNameMap);
		} catch (Exception e) {
			logger.error("getMmanLoanCollectionOrder error", e);
		}
		return "order/technologyOrderList";
	}
	
	@RequestMapping("/getMmanLoanCollectionOrderPage")
	public String getPage(HttpServletRequest request,HttpServletResponse response,Model model){
		try {
			HashMap<String, Object> params = getParametersO(request);
			BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
			if(backUser != null){
//				logger.info("backUser is not null");
				List<BackUserCompanyPermissions> CompanyPermissionsList=backUserService.findCompanyPermissions(backUser.getId());
				if(CompanyPermissionsList!=null&&CompanyPermissionsList.size() > 0){//指定公司的订单
					params.put("CompanyPermissionsList", CompanyPermissionsList);
				}
				//若组没有 ，则默认查询S1 组
//			if(null==params.get("collectionGroup") || StringUtils.isBlank(String.valueOf(params.get("collectionGroup")))){
//				params.put("collectionGroup", "3");
//			}
				//查询公司列表
				MmanLoanCollectionCompany mmanLoanCollectionCompany = new MmanLoanCollectionCompany();
				PageConfig<OrderBaseResult> pageConfig = mmanLoanCollectionOrderService.getPage(params);
				model.addAttribute("pm", pageConfig);
				model.addAttribute("params", params);
				model.addAttribute("ListMmanLoanCollectionCompany", mmanLoanCollectionCompanyService.getList(mmanLoanCollectionCompany));
				model.addAttribute("dictMap",BackConstant.groupNameMap);
			}

		} catch (Exception e) {
			logger.error("getMmanLoanCollectionOrder error", e);
		}
		return "order/orderList";
	}
	
	/**
     * 总订单导出
     * @param request
     * @param response
     * @param model
     * @return
     */
	@RequestMapping("/execlTotalOder")
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
			int totalPageNum = mmanLoanCollectionOrderService.findAllCount(params);
			if (totalPageNum > 0) {
				if (totalPageNum % size > 0) {
					total = totalPageNum / size + 1;
				} else {
					total = totalPageNum / size;
				}
			}
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			ExcelUtil.setFileDownloadHeader(request, response, "催收总订单.xlsx");
			response.setContentType("application/msexcel");// 定义输出类型
			SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
			String[] titles = {  "借款编号", "催收公司", "催收组", "借款人姓名","借款人身份证","借款手机号","借款金额","逾期天数",
					"滞纳金","催收状态","应还时间","已还金额","最新还款时间","最后催收时间","承诺还款时间","派单时间","当前催收员","上一催收员","减免滞纳金",};
			List<SysDict> dictlist=sysDictService.getStatus("collection_group");
			HashMap<String, String> dictMap=BackConstant.orderState(dictlist);
			List<SysDict> statulist=sysDictService.getStatus("xjx_collection_order_state");
			HashMap<String, String> StatuMap=BackConstant.orderState(statulist);
			for (int i = 1; i <= total; i++) {
				params.put(Constant.CURRENT_PAGE, i);
				PageConfig<OrderBaseResult> pm = mmanLoanCollectionOrderService.getPage(params);
				List<OrderBaseResult> list = pm.getItems();
				System.out.println("list>>>>>>>>="+list.size());
				List<Object[]> contents = new ArrayList<Object[]>();
				for (OrderBaseResult r : list) {
					String[] conList = new String[titles.length];
					conList[0]=r.getLoanId();
					conList[1]=r.getCompanyTile();
					conList[2]=dictMap.get(r.getCollectionGroup()+"");
					conList[3]=r.getRealName();
					conList[4]=r.getIdCard();
					conList[5]=r.getPhoneNumber();
					conList[6]=r.getLoanMoney()==null?"0":r.getLoanMoney()+"";
					conList[7]=r.getOverdueDays()+"";
					conList[8]=r.getLoanPenlty()+"";
					conList[9]=StatuMap.get(r.getCollectionStatus());
					conList[10]=r.getLoanEndTime()==null?"":DateUtil.getDateFormat(r.getLoanEndTime(), "yyyy-MM-dd HH:mm:ss");
					conList[11]=r.getReturnMoney()==null?"0":r.getReturnMoney()+"";
					if(r.getReturnMoney()!=null &&CompareUtils.greaterThanZero(r.getReturnMoney())){
						conList[12]=r.getCurrentReturnDay()==null?"":DateUtil.getDateFormat(r.getCurrentReturnDay(), "yyyy-MM-dd HH:mm:ss");
					}else{
						conList[12]="";
					}
					conList[13]=r.getLastCollectionTime()==null?"":DateUtil.getDateFormat(r.getLastCollectionTime(), "yyyy-MM-dd HH:mm:ss");
					conList[14]=r.getPromiseRepaymentTime()==null?"":DateUtil.getDateFormat(r.getPromiseRepaymentTime(), "yyyy-MM-dd HH:mm:ss");
					conList[15]=r.getDispatchTime()==null?"":DateUtil.getDateFormat(r.getDispatchTime(), "yyyy-MM-dd HH:mm:ss");
					conList[16]=r.getCurrUserName()==null?"":r.getCurrUserName();
					conList[17]=r.getLastUserName()==null?"":r.getLastUserName();
					conList[18]= String.valueOf(r.getReductionMoney()==null?"":r.getReductionMoney());
					contents.add(conList);
				}
				ExcelUtil.buildExcel(workbook, "管理跟踪统计", titles, contents, i, total, os);
			}
		} catch (Exception e) {
			logger.error("催收总订单导出excel失败", e);
		}
	}
	
}
