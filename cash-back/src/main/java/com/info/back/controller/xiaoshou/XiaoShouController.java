package com.info.back.controller.xiaoshou;

import com.info.back.controller.BaseController;
import com.info.back.dao.IMmanLoanCollectionCompanyDao;
import com.info.back.dao.IXiaoShouOrderDao;
import com.info.back.result.JsonResult;
import com.info.back.service.DistributeXiaoShouOrderService;
import com.info.back.service.IXiaoShouService;
import com.info.back.utils.BackConstant;
import com.info.back.utils.DwzResult;
import com.info.back.utils.ExcelUtil;
import com.info.back.utils.SpringUtils;
import com.info.constant.Constant;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.XiaoShouOrder;
import com.info.web.synchronization.dao.IDataDao;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.info.back.utils.BackConstant.SALER_ROLE_ID;

@Controller
@RequestMapping("xiaoShou/")
public class XiaoShouController  extends BaseController {

    private static Logger logger = Logger.getLogger(XiaoShouController.class);

    @Autowired
    IXiaoShouService xiaoShouService;
    @Autowired
    IXiaoShouOrderDao xiaoShouOrderDao;
    @Autowired
    IMmanLoanCollectionCompanyDao mmanLoanCollectionCompanyDao;
    @Autowired
    DistributeXiaoShouOrderService distributeXiaoShouOrderService;
    @Autowired
    IDataDao dataDao;
    private static Map<Integer,String> loanOrderStatusMap = new HashedMap();
    private static Map<Integer,String> userIntentionMap = new HashedMap();//用户意向：1有意向；2无意向；3 未接通
    static {
        loanOrderStatusMap.put(0,"无在接订单");
        loanOrderStatusMap.put(1,"有在接订单");

        userIntentionMap.put(1,"有意向");
        userIntentionMap.put(2,"无意向");
        userIntentionMap.put(3,"未接通");
    }

    /**
     * 导入客户信息页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("toImportUserInfoPage")
    public String toImportUserInfoPage(HttpServletRequest request, HttpServletResponse response,Model model) {
        HashMap<String, Object> params = getParametersO(request);
        model.addAttribute("params", params);
        return "xiaoshou/importUserInfo";
    }


    /**
     * excel 导入
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "importExcel", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String uploadExcel(HttpServletRequest request, HttpServletResponse response, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        JsonResult result = new JsonResult("-1", "导入失败！");
        try {
            logger.info("开始导入excel");
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile multipartFile = multipartRequest.getFile("file");
//            String filename = file.getOriginalFilename();
            Integer count = xiaoShouService.importExcel(multipartFile);
            result.setCode("0");
            result.setMsg("导入成功！");
            model.addAttribute("params", params);
        } catch (Exception e) {
            logger.error("导入订单出错");
            result.setCode("-1");
            result.setMsg("导入失败！");
            e.printStackTrace();
        }
        SpringUtils.renderDwzResult(response, "0".equals(result.getCode()),
                result.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
                null);
        return null;
    }

    @ResponseBody
    @RequestMapping("dispatcherOrder")
    public String dispatcherOrder(HttpServletRequest request, HttpServletResponse response, Model model){
        JsonResult result = new JsonResult("-1", "分单失败！");
        try{

            distributeXiaoShouOrderService.handleXiaoShouOrder();
            result.setCode("0");
            result.setMsg("已完成分单！");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("分单处理异常！");
        }
        SpringUtils.renderDwzResult(response, "0".equals(result.getCode()),
                result.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
                null);
        return null;
    }


    @RequestMapping("getAllXiaoShouOrder")
    public String getAllXiaoShouOrder(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        PageConfig<XiaoShouOrder> page = xiaoShouService.findAllUserPage(params);
        page = handleLoanOrderStatus2(page);
        Map<String,String> saleCompanyMap = getSaleCompanyMap();

        model.addAttribute("page",page);
        model.addAttribute("params",params);
        model.addAttribute("merchantNoMap", BackConstant.merchantNoMap);
        model.addAttribute("saleCompanyMap", saleCompanyMap);
        model.addAttribute("userIntentionMap", userIntentionMap);
        return "xiaoshou/allXiaoShouOrder";
    }

    private PageConfig<XiaoShouOrder> handleLoanOrderStatus2(PageConfig<XiaoShouOrder> page) {
        List<XiaoShouOrder> items = page.getItems();
        if(null != items && items.size()>0){
            for(XiaoShouOrder xiaoShouOrder : items){
                HashMap<String,String> map = new HashMap();
                map.put("userId",xiaoShouOrder.getUserId());
                map.put("merchantNo",xiaoShouOrder.getMerchantNo());
                HashMap<String,String> borrowOrder = dataDao.getBorrowOrderOnBorrowing2(map);//通过userId、商户号查询在借状态借款单；
                if (null != borrowOrder && borrowOrder.size() > 0){
                    xiaoShouOrder.setLoanOrderStatus(1);
                }else {
                    xiaoShouOrder.setLoanOrderStatus(0);
                }
            }
        }
        page.setItems(items);
        return page;
    }

    @RequestMapping("getMyXiaoShouOrder")
    public String getMyXiaoShouOrder(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        BackUser backUser = (BackUser) request.getSession().getAttribute(
                Constant.BACK_USER);
        if (String.valueOf(SALER_ROLE_ID).equals(backUser.getRoleId())){
            params.put("currentCollectionUserId",backUser.getUuid());
        }
        PageConfig<XiaoShouOrder> page = xiaoShouService.findAllUserPage(params);
        handleLoanOrderStatus2(page);
        Map<String,String> saleCompanyMap = getSaleCompanyMap();

        model.addAttribute("page",page);
        model.addAttribute("params",params);
        model.addAttribute("merchantNoMap", BackConstant.merchantNoMap);
        model.addAttribute("userIntentionMap", userIntentionMap);
        return "xiaoshou/myXiaoShouOrder";
    }

    private Map<String,String> getSaleCompanyMap() {
        List<Map<String,String>> saleCompanyList = mmanLoanCollectionCompanyDao.getAllSaleCompany();
        Map<String,String> map = new HashedMap();
        for (Map<String, String> mapTemp:saleCompanyList) {
            map.put(mapTemp.get("id"),mapTemp.get("title"));
        }
        return map;
    }

    /**
     * 客户总列表导出
     * @param response
     * @param request
     * @param
     */
    @RequestMapping("/toExcel")
    public void toExcel(HttpServletResponse response, HttpServletRequest request) {
        HashMap<String, Object> params = getParametersO(request);
        logger.info("客户总列表导出开始......");
        Map<String,String> saleCompanyMap = getSaleCompanyMap();
        Map<String,String> merchantNoMap = BackConstant.merchantNoMap;
        try {
            int size = 50000;
            int total = 0;
            params.put(Constant.PAGE_SIZE, size);
            int totalPageNum = xiaoShouOrderDao.findAllUserCount(params);
            if (totalPageNum > 0) {
                if (totalPageNum % size > 0) {
                    total = totalPageNum / size + 1;
                } else {
                    total = totalPageNum / size;
                }
            }
            OutputStream os = response.getOutputStream();
            response.reset();// 清空输出流
            ExcelUtil.setFileDownloadHeader(request, response, "客户总列表.xlsx");
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
            String[] titles = {"批次", "销售公司", "坐席", "商户号", "User ID", "客户姓名", "注册时间", "当前状态", "用户意向", "备注", "分单时间"};
            for (int i = 1; i <= total; i++) {
                params.put(Constant.CURRENT_PAGE, i);
                PageConfig<XiaoShouOrder> pm = xiaoShouService.findAllUserPage(params);
                pm = handleLoanOrderStatus2(pm);
                List<XiaoShouOrder> list = pm.getItems();
                List<Object[]> contents = new ArrayList<Object[]>();
                for (XiaoShouOrder r : list) {
                    String[] conList = new String[titles.length];
                    conList[0] = r.getBatchId() == null ? "" : String.valueOf(r.getBatchId());
                    conList[1] = r.getCompanyId() == null ? "" : saleCompanyMap.get(r.getCompanyId());
                    conList[2] = r.getCurrentCollectionUserName() == null ? "" : r.getCurrentCollectionUserName();
                    conList[3] = r.getMerchantNo() == null ? "" : merchantNoMap.get(r.getMerchantNo());
                    conList[4] = r.getUserId() == null ? "" : r.getUserId();
                    conList[5] = r.getUserName() == null ? "" : r.getUserName();
                    conList[6] = r.getRegisterTime() == null ? "" : DateUtil.getDateFormat(r.getRegisterTime(), "yyyy-MM-dd HH:mm:ss");
                    if (r.getLoanOrderStatus() == null){
                        conList[7] = "";
                    }else{
                        conList[7] = loanOrderStatusMap.get(r.getLoanOrderStatus());
                    }
                    if (r.getUserIntention() == null){
                        conList[8] = "";
                    }else{
                        conList[8] = userIntentionMap.get(r.getUserIntention());
                    }
                    conList[9] = r.getRemark() == null ? "" : r.getRemark();
                    conList[10] = r.getDispatcherTime() == null ? "" : DateUtil.getDateFormat(r.getDispatcherTime(), "yyyy-MM-dd HH:mm:ss");
                    contents.add(conList);
                }
                ExcelUtil.buildExcel(workbook, "客户总列表", titles, contents, i, total, os);
            }
        } catch (Exception e) {
            logger.error("客户总列表导出excel失败", e);
        }
    }


    @RequestMapping("getUserMobile")
    public String getUserMobile(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        String mobile = String.valueOf(params.get("mobile"));
        model.addAttribute("mobile",mobile);
        return "xiaoshou/userMobile";
    }

    /**
     * 跳转至添加备注页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("addRemarkPage")
    public String addRemarkPage(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        String id = String.valueOf(params.get("id"));
        String remark = String.valueOf(params.get("remark"));
        model.addAttribute("id",id);
        model.addAttribute("params",params);
        return "xiaoshou/addRemark";
    }

    @RequestMapping("addRemark")
    public void addRemark(HttpServletRequest request, HttpServletResponse response,Model model) {
        JsonResult result = new JsonResult("-1", "添加/修改备注失败");
        HashMap<String, Object> params = this.getParametersO(request);
        Long id = Long.valueOf(String.valueOf(params.get("id")));
        String remark = String.valueOf(params.get("remark"));
        XiaoShouOrder xiaoShouOrder = new XiaoShouOrder();
        xiaoShouOrder.setId(id);
        xiaoShouOrder.setRemark(remark);
        int resultTemp = xiaoShouOrderDao.updateRemark(xiaoShouOrder);
        if (1 == resultTemp){
            result.setCode("0");
            result.setMsg("已修改备注");
        }
        model.addAttribute("id",id);
        model.addAttribute("params",params);
        //返回原页面
        SpringUtils.renderDwzResult(response, "0".equals(result.getCode()),
                result.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
                params.get("parentId").toString());
    }

    /**
     * 跳转至修改用户意向页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("updateUserIntentionPage")
    public String updateUserIntentionPage(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        String id = String.valueOf(params.get("id"));
        String remark = String.valueOf(params.get("userIntention"));
        model.addAttribute("id",id);
        model.addAttribute("params",params);
        model.addAttribute("userIntentionMap", userIntentionMap);
        return "xiaoshou/updateUserIntention";
    }

    @RequestMapping("updateUserIntention")
    public void updateUserIntention(HttpServletRequest request, HttpServletResponse response,Model model) {
        JsonResult result = new JsonResult("-1", "修改用户意向失败");
        HashMap<String, Object> params = this.getParametersO(request);
        Long id = Long.valueOf(String.valueOf(params.get("id")));
        Integer userIntention = Integer.valueOf(String.valueOf(params.get("userIntention")));
        XiaoShouOrder xiaoShouOrder = new XiaoShouOrder();
        xiaoShouOrder.setId(id);
        xiaoShouOrder.setUserIntention(userIntention);
        int resultTemp = xiaoShouOrderDao.updateUserIntention(xiaoShouOrder);
        if (1 == resultTemp){
            result.setCode("0");
            result.setMsg("已修改用户意向");
        }
        model.addAttribute("id",id);
        model.addAttribute("params",params);
        //返回原页面
        SpringUtils.renderDwzResult(response, "0".equals(result.getCode()),
                result.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
                params.get("parentId").toString());
    }



}
