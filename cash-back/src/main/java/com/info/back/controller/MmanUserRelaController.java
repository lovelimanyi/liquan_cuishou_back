package com.info.back.controller;

import com.info.back.service.IMmanLoanCollectionOrderService;
import com.info.back.service.IMmanUserInfoService;
import com.info.back.service.IMmanUserRelaService;
import com.info.back.utils.BackConstant;
import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.pojo.MmanUserInfo;
import com.info.web.pojo.MmanUserRela;
import com.info.web.util.PageConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("mmanUserRela/")
public class MmanUserRelaController extends BaseController {
	private static Logger logger = Logger.getLogger(MmanUserRelaController.class);

	@Autowired
	private IMmanUserRelaService mmanUserRelaService;
	@Autowired
	private IMmanUserInfoService mmanUserInfoService;

	@Autowired
	private IMmanLoanCollectionOrderService mmanLoanCollectionOrderService;

	@RequestMapping("getMmanUserRelaPage")
	public String getMmanUserRelaPage(HttpServletRequest request,HttpServletResponse response, Model model){
		HashMap<String, Object> params = this.getParametersO(request);
		String orderId = params.get("id").toString();
		String erroMsg = null;
		try {
			MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderById(orderId);
			if(order != null){
				params.put("userId", order.getUserId());
				params.put("userId", order.getUserId());
				int overdueDays = order.getOverdueDays();//逾期天数
				params.put("overdueDays",overdueDays);
				if(!BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS.equals(order.getStatus())){
					if(overdueDays <= 0){
//						erroMsg = "逾期2天方可申请查看通讯录";
//						params.put("num",10);
//						List<MmanUserRela> list = mmanUserRelaService.getList(params);
//						model.addAttribute("list", list);
					}
					// 暂时调整，大于3天可以查看所有的通讯录
//					else if(overdueDays >= 2 && overdueDays <= 3){
//						params.put("num",2);
//						List<MmanUserRela> list = mmanUserRelaService.getList(params);
//						model.addAttribute("list", list);
//					}
//					else if(overdueDays >= 4 && overdueDays <= 5){
//						params.put("num",5);
//						List<MmanUserRela> list = mmanUserRelaService.getList(params);
//						model.addAttribute("list", list);
//					}else if(overdueDays >= 6 && overdueDays <= 10){
//						params.put("num",15);
//						List<MmanUserRela> list = mmanUserRelaService.getList(params);
//						model.addAttribute("list", list);
//					}
					else{
						MmanUserInfo userInfo = mmanUserInfoService.getUserInfoById(order.getUserId());


						PageConfig<MmanUserRela> pageConfig = mmanUserRelaService.findPage(params);
						List<MmanUserRela> mmanUserRelaList = pageConfig.getItems();
						MmanUserRela mmanUserRelaOne = new MmanUserRela();
						mmanUserRelaOne.setUserId(order.getUserId());
						mmanUserRelaOne.setRealName(userInfo.getRealname());
						mmanUserRelaOne.setContactsKey(userInfo.getFristContactRelation().toString());
						mmanUserRelaOne.setRelaKey(userInfo.getFristContactRelation().toString());
						mmanUserRelaOne.setInfoName(userInfo.getFirstContactName());
						mmanUserRelaOne.setInfoValue(userInfo.getFirstContactPhone());
						MmanUserRela mmanUserRelaTwo = new MmanUserRela();


						mmanUserRelaTwo.setRealName(userInfo.getRealname());
						mmanUserRelaTwo.setUserId(order.getUserId());
						mmanUserRelaTwo.setContactsKey(userInfo.getSecondContactRelation().toString());
						mmanUserRelaTwo.setRelaKey(userInfo.getSecondContactRelation().toString());
						mmanUserRelaTwo.setInfoName(userInfo.getSecondContactName());
						mmanUserRelaTwo.setInfoValue(userInfo.getSecondContactPhone());
						mmanUserRelaList.add(0,mmanUserRelaOne);
						mmanUserRelaList.add(1,mmanUserRelaTwo);

						model.addAttribute("pm", pageConfig);
					}
				}else {
					erroMsg = "催收成功订单不允许查看通讯录！";
				}
			}else {
				logger.error("借款人联系人异常，请核实，借款id: " + orderId);
			}

			//备用查看通讯录规则
//			if(overdueDays <= 1){
//				erroMsg = "逾期2天方可申请查看通讯录";
//			}else if(overdueDays >= 2 && overdueDays <= 3){
//				params.put("num",2);
//				List<MmanUserRela> list = mmanUserRelaService.getList(params);
//				model.addAttribute("list", list);
//			}else{
//				params.put("num",3);
//				List<MmanUserRela> list = mmanUserRelaService.getList(params);
//				model.addAttribute("list", list);
//			}

		}catch (Exception e) {
			logger.error("getMmanUserRelaPage error", e);
		}
		model.addAttribute("orderId", orderId);
		model.addAttribute("params", params);// 用于搜索框保留值
		model.addAttribute(MESSAGE, erroMsg);
		return "mycollectionorder/mmanUserRelaList";
	}


	@RequestMapping("getMmanUserRelaCountPage")
	public String getMmanUserRelaCountPage(HttpServletRequest request,HttpServletResponse response, Model model){
		//String erroMsg = null;
		try {
			HashMap<String, Object> params = this.getParametersO(request);
			String orderId = params.get("id").toString();
			MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderById(orderId);
			params.put("userId", order.getUserId());
			List<MmanUserRela> list = mmanUserRelaService.getList(params);
			model.addAttribute("list", list);
			PageConfig<MmanUserRela> page = mmanUserRelaService.findAllPage(params);
			model.addAttribute("pm", page);
			model.addAttribute("params", params);
		} catch (Exception e) {
			logger.error("params error", e);
			model.addAttribute(MESSAGE, "服务器异常，请稍后重试！");
		}
		return "mycollectionorder/relaCountPage";
	}

}
