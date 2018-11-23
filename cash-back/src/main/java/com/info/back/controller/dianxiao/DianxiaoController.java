package com.info.back.controller.dianxiao;

import com.info.back.controller.BaseController;
import com.info.back.result.JsonResult;
import com.info.back.service.IDianXiaoService;
import com.info.back.service.IMmanLoanCollectionOrderService;
import com.info.back.service.ISysDictService;
import com.info.back.utils.BackConstant;
import com.info.back.utils.DwzResult;
import com.info.back.utils.ExcelUtil;
import com.info.back.utils.SpringUtils;
import com.info.constant.Constant;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.DianXiaoOrder;
import com.info.web.pojo.OrderBaseResult;
import com.info.web.pojo.SysDict;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.chart.ValueRangeRecord;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：电销controller
 * 创建人：yyf
 * 创建时间：2018/11/14 0014下午 10:30
 */
@Controller
@RequestMapping("/dianxiao")
public class DianxiaoController extends BaseController {
    private static Logger logger = Logger.getLogger(DianxiaoController.class);
    @Autowired
    private IMmanLoanCollectionOrderService mmanLoanCollectionOrderService;
    @Autowired
    private IDianXiaoService dianXiaoService;
    @Autowired
    private ISysDictService sysDictService;

    @RequestMapping(value = "/getMyDianXiaoOrderList")
    public String getOrderList(HttpServletRequest request, Model model){
        String url = "dianxiao/myDianXiaoOrderList";
        try {
            BackUser backUser = loginAdminUser(request);
            HashMap<String, Object> params = getParametersO(request);
            if (BackConstant.DIAN_XIAO_ROLE_ID.equals(backUser.getRoleId())){
                params.put("currentCollectionUserId",backUser.getUuid());
                url = "dianxiao/myDianXiaoOrderList";
            }else {
                url = "dianxiao/totalDianXiaoOrderList";
            }
            params.put("TodayDate", DateUtil.getDateFormat("yyyy-MM-dd"));
            PageConfig<DianXiaoOrder> pageConfig = dianXiaoService.getDianXiaoPage(params);
            model.addAttribute("pm", pageConfig);
            model.addAttribute("params", params);// 用于搜索框保留值
            model.addAttribute("merchantMap", mmanLoanCollectionOrderService.getMerchantMap());
        } catch (Exception e) {
            logger.error("getDianXiaoOrderListError", e);
        }
        return url;
    }
    @RequestMapping(value = "/getTotalDianXiaoOrderList")
    public String getTotalDianXiaoOrderList(HttpServletRequest request, Model model){
        try {
            HashMap<String, Object> params = getParametersO(request);
            PageConfig<DianXiaoOrder> pageConfig = dianXiaoService.getDianXiaoPage(params);
            model.addAttribute("pm", pageConfig);
            model.addAttribute("params", params);// 用于搜索框保留值
            model.addAttribute("merchantMap", mmanLoanCollectionOrderService.getMerchantMap());
        } catch (Exception e) {
            logger.error("getDianXiaoOrderListError", e);
        }
        return "dianxiao/totalDianXiaoOrderList";
    }

    @RequestMapping(value = "/toUpdateDianXiaoOrderPage")
    public String updateDianXiaoOrder(HttpServletRequest request, Model model){
        HashMap<String, Object> params = getParametersO(request);
        try {
            DianXiaoOrder diaoxiaoOrder = dianXiaoService.getDianXiaoOrder(params);
            model.addAttribute("params", params);// 用于搜索框保留值
            model.addAttribute("order",diaoxiaoOrder);
        }catch (Exception e){
            logger.error("toUpdateDianXiaoOrderPage:"+ params.get("id").toString());
        }
        return "dianxiao/updateDianXiaoOrderPage";

    }
    @RequestMapping(value = "/updateDianXiaoOrder",method = RequestMethod.POST)
    public void updateDianXiaoOrder(HttpServletRequest request, HttpServletResponse response){

        JsonResult result = new JsonResult("-1", "电销订单修改失败");
        HashMap<String, Object> params = getParametersO(request);
        try {
            result = dianXiaoService.updateDianXiaoOrder(params);
        }catch (Exception e){
            logger.error("updateDianXiaoOrderError:"+ params.get("id").toString());
        }
        SpringUtils.renderDwzResult(response, "0".equals(result.getCode()),
                result.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
                params.get("parentId").toString());
    }
    @RequestMapping("/execlTotalDianXiaoOder")
    public void reportManage(HttpServletResponse response, HttpServletRequest request, Model model) {

        HashMap<String, Object> params = getParametersO(request);
        int sheetSize = 50000;
        int sheetNo = 0;
        int orderCount = dianXiaoService.getDianxiaoOrderCount(params);
        if (orderCount > 0){
            if (orderCount % sheetSize > 0) {
                sheetNo = orderCount / sheetSize + 1;
            } else {
                sheetNo = orderCount / sheetSize;
            }
        }
        try {
            OutputStream os = response.getOutputStream();
            response.reset();// 清空输出流
            ExcelUtil.setFileDownloadHeader(request, response, "电销总订单.xlsx");
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
            String[] titles = {"借款编号", "借款人", "借款人手机号", "借款时间", "应还时间", "借款金额", "服务费", "还款状态","电催员姓名", "商户号", "客户意向", "备注"};
            Map<String,String> merchantNoMap = mmanLoanCollectionOrderService.getMerchantMap();
            List<SysDict> list = sysDictService.getStatus("repayment_intention");
            HashMap<String, String> intentionMap = BackConstant.orderState(list);
            params.put(Constant.PAGE_SIZE, sheetSize);
            for (int i = 1; i <= sheetNo; i++) {
                params.put(Constant.CURRENT_PAGE, i);
                PageConfig<DianXiaoOrder> pageConfig = dianXiaoService.getDianXiaoPage(params);
                List<DianXiaoOrder> orders = pageConfig.getItems();
                List<Object[]> contents = new ArrayList<>();
                for (DianXiaoOrder order : orders) {
                    String[] conList = new String[titles.length];
                    conList[0] = order.getLoanId();
                    conList[1] = order.getLoanUserName();
                    conList[2] = order.getLoanUserPhone();
                    conList[3] = DateUtil.getDateFormat(order.getLoanStartDate(), "yyyy-MM-dd ");
                    conList[4] = DateUtil.getDateFormat(order.getLoanEndDate(), "yyyy-MM-dd ");
                    conList[5] = order.getLoanMoney().toString();
                    conList[6] = order.getLoanServiceCharge().toString();
                    conList[7] = order.getOrderStatus()== 1? "未还款":"已还款";
                    conList[8] = order.getCurrentCollectionUserName();
                    conList[9]= merchantNoMap.get(order.getMerchantNo());
                    if (order.getRepaymentIntention() != null){
                        conList[10]= intentionMap.get(order.getRepaymentIntention().toString());
                    }else {
                        conList[10] = "";
                    }
                    conList[11]= null == order.getRemark()? "":order.getRemark().toString();
                    contents.add(conList);
                }
                ExcelUtil.buildExcel(workbook, "电销总订单", titles, contents, i, sheetNo, os);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
