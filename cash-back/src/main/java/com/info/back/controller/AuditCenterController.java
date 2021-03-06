package com.info.back.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.info.back.dao.IAuditCenterDao;
import com.info.back.dao.IMmanLoanCollectionOrderDao;
import com.info.back.result.JsonResult;
import com.info.back.service.IAuditCenterService;
import com.info.back.service.ICollectionCompanyService;
import com.info.back.service.IMmanLoanCollectionCompanyService;
import com.info.back.service.ISysDictService;
import com.info.back.utils.*;
import com.info.constant.Constant;
import com.info.web.pojo.AuditCenter;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.MmanLoanCollectionCompany;
import com.info.web.pojo.SysDict;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 信息审核
 * @author xieyaling
 * @date 2017-02-24
 */
@Controller
@RequestMapping("auditCenter/")
public class AuditCenterController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(AuditCenterController.class);
	@Autowired
	private IAuditCenterService auditCenterService;
	@Autowired
	private ISysDictService sysDictService;
	@Autowired
	private ICollectionCompanyService CollectionCompanyService;
	// 公司
	@Autowired
	private IMmanLoanCollectionCompanyService mmanLoanCollectionCompanyService;
	@Autowired
	private IAuditCenterDao auditCenterDao;
	@Autowired
	private IMmanLoanCollectionOrderDao manLoanCollectionOrderDao;
	/**
	 * 查询信息审核列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("getAuditCenterPage")
	public String getCompanyPage(HttpServletRequest request,HttpServletResponse response, Model model) {
		try {
			HashMap<String, Object> params = getParametersO(request);
			//add by 170308 审核列表默认申请中状态
			if(!params.containsKey("status") && params.containsKey("_")){
				params.put("status","0");
				params.put("pageNum",1);
			}
			BackUser backUser=this.loginAdminUser(request);
			params.put("noAdmin", Constant.ADMINISTRATOR_ID);
			if(!"1".equals(backUser.getCompanyId())){
				params.put("companyId",backUser.getCompanyId());
			}

			// 查询公司列表
			MmanLoanCollectionCompany mmanLoanCollectionCompany = new MmanLoanCollectionCompany();
			List<MmanLoanCollectionCompany> ListMmanLoanCollectionCompany = mmanLoanCollectionCompanyService.getList(mmanLoanCollectionCompany);
			model.addAttribute("ListMmanLoanCollectionCompany",ListMmanLoanCollectionCompany);
			PageConfig<AuditCenter> pageConfig = auditCenterService.findPage(params);
			model.addAttribute("pm", pageConfig);
			model.addAttribute("params", params);// 用于搜索框保留值
			model.addAttribute("userGropLeval", backUser.getRoleId());
			if(params.get("beginTime") == null && params.get("endTime") == null){
				params.put("beginTime", DateUtil.getDateFormat("yyyy-MM-dd"));
				params.put("endTime", DateUtil.getDateFormat("yyyy-MM-dd"));

			}
			//审核类型
			List<SysDict> levellist = sysDictService.getStatus("xjx_auditcenter_type ");
			model.addAttribute("levellist", levellist);// 用于搜索框保留值
			HashMap<String, String> levelMap = BackConstant.orderState(levellist);
			model.addAttribute("typeMap", levelMap);
		} catch (Exception e) {
			logger.error("getCompanyPage error", e);
		}
		return "auditCenter/auditCenterList";
	}



	@RequestMapping("addAuditCenter")
	public String addAuditCenter(HttpServletRequest request,HttpServletResponse response,Model model){
		String erroMsg = null;
		JsonResult result=new JsonResult("-1","操作失败");
		Map<String, String> params =this.getParameters(request);
		try {
			BackUser backUser=this.loginAdminUser(request);
			params.put("operationUserId", backUser.getId().toString());
			result=auditCenterService.svueAuditCenter(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringUtils.renderDwzResult(response, "0".equals(result.getCode()),result.getMsg(),DwzResult.CALLBACK_CLOSECURRENT, "");
		model.addAttribute("params", params);
		return null;
	}

	@RequestMapping("toUpdateAuditCenter")
	public String toUpdateAuditCenter(HttpServletRequest request,HttpServletResponse response,Model model){
		Map<String, String> params =this.getParameters(request);
		model.addAttribute("params", params);
		return "auditCenter/updateAudit";
	}



	@RequestMapping("updateAuditCenter")
	public String updateAuditCenter(HttpServletRequest request,HttpServletResponse response,Model model){
		JsonResult result=new JsonResult("-1","操作失败");
		Map<String, String> params =this.getParameters(request);
		try {
			result=auditCenterService.updateAuditCenter(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringUtils.renderDwzResult(response, "0".equals(result.getCode()),result.getMsg(),DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
		model.addAttribute("params", params);
		return null;
	}
	@RequestMapping("toUpdateAuditCenterStatus")
	public String toUpdateAuditCenterStatus(HttpServletRequest request,HttpServletResponse response,Model model){
		Map<String, String> params =this.getParameters(request);
		model.addAttribute("params", params);
		return "auditCenter/statusUpdate";
	}

	@RequestMapping("updateAuditCenterStatus")
	public String updateAuditCenterStatus(HttpServletRequest request,HttpServletResponse response,Model model) {
		String erroMsg = null;
		JsonResult result = new JsonResult("-1", "操作失败");
		Map<String, String> params = this.getParameters(request);
		try {
			result = auditCenterService.updateAuditCenter(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringUtils.renderDwzResult(response, "0".equals(result.getCode()), result.getMsg(), DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
		model.addAttribute("params", params);
		return null;
	}
	@RequestMapping("fingAllPageList")
	public String fingAllPageList(HttpServletRequest request,HttpServletResponse response, Model model) {
		try {
			HashMap<String, Object> params = getParametersO(request);
			params.put("noAdmin", Constant.ADMINISTRATOR_ID);
			PageConfig<AuditCenter> pageConfig = auditCenterService.findAllPage(params);

			if(pageConfig != null && pageConfig.getItems().size() > 0){
				for (AuditCenter auditCenter:pageConfig.getItems()) {
					String phoneNumber = auditCenter.getLoanUserPhone();
					if("2".equals(auditCenter.getStatus())){
						auditCenter.setLoanUserPhone(MaskCodeUtil.getMaskCode(phoneNumber));
					}
				}
			}

			model.addAttribute("pm", pageConfig);
			model.addAttribute("params", params);// 用于搜索框保留值
		} catch (Exception e) {
			logger.error("getCompanyPage error", e);
		}
		return "mycollectionorder/getorderPage";
	}

	/**
	 * 异步处理（减免回调）
	 */
	@RequestMapping(value = "reduction-callback-big",method = RequestMethod.POST,consumes = "application/json")
	@ResponseBody
	public MQResponse updateReductionResult(@RequestBody HashMap<String, Object> map) {
		String msg = "";
		try {
			JSONObject obj = JSONObject.parseObject(JSON.toJSONString(map));
			logger.info("接收到减免回调请求参数(大额)： " +obj.get("repaymentId").toString()+ JSON.toJSONString(obj));
			String uuid = (String) obj.get("uuid");
			boolean result = (boolean) obj.get("result");
			msg = obj.get("msg").toString();
			Integer reductionMoney = (Integer) obj.get("reductionMoney");
			HashMap<String, Object> mapParamm = new HashMap<>();
			mapParamm.put("id", uuid);
			// status: 2 通过  5通过不计考核  -1减免失败
			if(result){
				mapParamm.put("auditId", uuid);
				mapParamm.put("status", obj.get("status"));
				mapParamm.put("orderStatus",Constant.STATUS_OVERDUE_FOUR);
				mapParamm.put("reductionMoney",new BigDecimal(reductionMoney).divide(new BigDecimal(100)));
			}else {
				mapParamm.put("status", "-1");
				mapParamm.put("orderStatus",Constant.STATUS_OVERDUE_ONE);
				mapParamm.put("reductionMoney",0);
			}
			auditCenterDao.updateAuditStatus(mapParamm);
		    AuditCenter auditCenter = auditCenterDao.findAuditId(uuid);
			mapParamm.put("orderId",auditCenter.getOrderid());
			manLoanCollectionOrderDao.updateReductionOrder(mapParamm);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("减免回调异常"+msg);
			return new MQResponse(MQResponse.Code.ERROR) ;
		}
		return new MQResponse();
	}



}
