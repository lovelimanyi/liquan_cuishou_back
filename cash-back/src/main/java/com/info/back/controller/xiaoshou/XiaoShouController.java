package com.info.back.controller.xiaoshou;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.info.back.controller.BaseController;
import com.info.back.dao.IMmanLoanCollectionCompanyDao;
import com.info.back.dao.IXiaoShouOrderDao;
import com.info.back.result.JsonResult;
import com.info.back.service.DistributeXiaoShouOrderService;
import com.info.back.service.IXiaoShouService;
import com.info.back.utils.*;
import com.info.config.PayContents;
import com.info.constant.Constant;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.XiaoShouOrder;
import com.info.web.synchronization.dao.IDataDao;
import com.info.web.util.DateUtil;
import com.info.web.util.HttpUtil;
import com.info.web.util.PageConfig;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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
        String url = "xiaoshou/importUserInfo";
        if ("ymgj".equals(String.valueOf(params.get("orderFrom")))){
            url = "xiaoshouYoumi/importUserInfo";
        }
        return url;
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
            Integer count = xiaoShouService.importExcel(multipartFile,BackConstant.ORDER_FROM_XJX);
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

    @RequestMapping(value = "importExcelFromYoumi", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String uploadExcelFromYoumi(HttpServletRequest request, HttpServletResponse response, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        JsonResult result = new JsonResult("-1", "导入失败！");
        try {
            logger.info("开始导入excel");
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile multipartFile = multipartRequest.getFile("file");
//            String filename = file.getOriginalFilename();
            Integer count = xiaoShouService.importExcel(multipartFile,BackConstant.ORDER_FROM_YMGJ);
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
            HashMap<String,Object> params = getParametersO(request);
            String orderFrom = String.valueOf(params.get("orderFrom"));
            distributeXiaoShouOrderService.handleXiaoShouOrder(orderFrom);
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
        String url = "xiaoshou/allXiaoShouOrder";
        HashMap<String, Object> params = this.getParametersO(request);
        String orderFrom = String.valueOf(params.get("orderFrom"));
        Map<String,String> saleCompanyMap = getSaleCompanyMap();

        PageConfig<XiaoShouOrder> page = null;
        if (BackConstant.ORDER_FROM_YMGJ.equals(orderFrom)){
            page = xiaoShouService.findAllUserPageFromYoumi(params);
            page = handleOrderInfoFromYoumi(page);
            url = "xiaoshouYoumi/allXiaoShouOrder";
        }else {
            page = xiaoShouService.findAllUserPage(params);
            page = handleLoanOrderStatus(page);
        }

        model.addAttribute("page",page);
        model.addAttribute("params",params);
        model.addAttribute("merchantNoMap", BackConstant.merchantNoMap);
        model.addAttribute("saleCompanyMap", saleCompanyMap);
        model.addAttribute("userIntentionMap", userIntentionMap);
        return url;
    }

    private PageConfig<XiaoShouOrder> handleLoanOrderStatus(PageConfig<XiaoShouOrder> page) {
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

    private PageConfig<XiaoShouOrder> handleOrderInfoFromYoumi(PageConfig<XiaoShouOrder> page) {
        List<XiaoShouOrder> items = page.getItems();
        if(null != items && items.size()>0){
            List<Long> userIdList = new ArrayList<>();
            for(XiaoShouOrder xiaoShouOrder : items){
                //todo_lmy 请求第三方
//                HashMap<String,String> map = new HashMap();
//                map.put("userId",xiaoShouOrder.getUserId());
//                map.put("merchantNo",xiaoShouOrder.getMerchantNo());
//                HashMap<String,String> borrowOrder = dataDao.getBorrowOrderOnBorrowing2(map);
//                if (null != borrowOrder && borrowOrder.size() > 0){
//                    xiaoShouOrder.setLoanOrderStatus(1);
//                }else {
//                    xiaoShouOrder.setLoanOrderStatus(0);
//                }
                userIdList.add(Long.valueOf(xiaoShouOrder.getUserId()));
            }
            logger.info("调用有米渠道接口，获取用户信息，参数：" + JSONObject.toJSONString(userIdList));
            try{
                //通过userId、商户号查询在借状态借款单；
//                String returnInfo = HttpUtil.getInstance().doPost2(PayContents.GET_YMGJ_USER_INFOS, JSON.toJSONString(userIdList));
//                String returnInfo = HttpUtil.doPost3(PayContents.GET_YMGJ_USER_INFOS, JSON.toJSONString(userIdList));

                RestTemplate restTemplate = new RestTemplate();
                String returnInfo = RestTemplateUtil.postJsonForEntity(restTemplate,PayContents.GET_YMGJ_USER_INFOS,userIdList,String.class);
//                Map<String, Object> o = (Map<String, Object>) JSONObject.parse(returnInfo);
                List<Map<String, Object>> o = (List<Map<String, Object>>) JSONObject.parse(returnInfo);
                Map<String,Map<String,Object>> mapResult = new HashMap<>();
                for(Map<String,Object> map :o){
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        if(null != entry.getKey() && StringUtils.isNotEmpty(entry.getKey())){
                            mapResult.put(entry.getKey(),(Map<String, Object>)entry.getValue());
                        }
                    }
                }

                for(XiaoShouOrder xiaoShouOrder : items){
                    String mobile = null;
                    String currentStatus = null;
                    String merchantNo = null;
                    if(null != mapResult.get(xiaoShouOrder.getUserId()) && mapResult.get(xiaoShouOrder.getUserId()).size()>0){
                        mobile = String.valueOf(mapResult.get(xiaoShouOrder.getUserId()).get("mobile"));
                        currentStatus = String.valueOf(mapResult.get(xiaoShouOrder.getUserId()).get("currentStatus"));
                        merchantNo = String.valueOf(mapResult.get(xiaoShouOrder.getUserId()).get("merchantNo"));
                    }
                    xiaoShouOrder.setMobile(mobile);
                    xiaoShouOrder.setCurrentStatus(currentStatus);
                    xiaoShouOrder.setMerchantNo(merchantNo);
                }
            }catch (Exception e){
                e.printStackTrace();
                logger.error("调用有米渠道接口，获取用户信息，处理异常，，，");
            }
        }
        page.setItems(items);
        return page;
    }

    @RequestMapping("getMyXiaoShouOrder")
    public String getMyXiaoShouOrder(HttpServletRequest request, Model model) {
        String url = "xiaoshou/myXiaoShouOrder";
        HashMap<String, Object> params = this.getParametersO(request);
        BackUser backUser = (BackUser) request.getSession().getAttribute(
                Constant.BACK_USER);
        if (String.valueOf(SALER_ROLE_ID).equals(backUser.getRoleId())){
            params.put("currentCollectionUserId",backUser.getUuid());
        }
        String orderFrom = String.valueOf(params.get("orderFrom"));
        PageConfig<XiaoShouOrder> page = null;
        if(BackConstant.ORDER_FROM_YMGJ.equals(orderFrom)){
            page = xiaoShouService.findAllUserPageFromYoumi(params);
            handleOrderInfoFromYoumi(page);
            url = "xiaoshouYoumi/myXiaoShouOrder";
        }else {
            page = xiaoShouService.findAllUserPage(params);
            handleLoanOrderStatus(page);
        }
        Map<String,String> saleCompanyMap = getSaleCompanyMap();

        model.addAttribute("page",page);
        model.addAttribute("params",params);
        model.addAttribute("merchantNoMap", BackConstant.merchantNoMap);
        model.addAttribute("saleCompanyMap", saleCompanyMap);
        model.addAttribute("userIntentionMap", userIntentionMap);
        return url;
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
            String orderFrom = String.valueOf(params.get("orderFrom"));
            int totalPageNum = 0;
            if(BackConstant.ORDER_FROM_YMGJ.equals(orderFrom)){
                totalPageNum = xiaoShouOrderDao.findAllUserCountFromYmgj(params);
            }else {
                totalPageNum = xiaoShouOrderDao.findAllUserCount(params);
            }

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
                PageConfig<XiaoShouOrder> pm = new PageConfig<>();
                if(BackConstant.ORDER_FROM_YMGJ.equals(orderFrom)){
                    pm = xiaoShouService.findAllUserPageFromYoumi(params);
                    pm = handleOrderInfoFromYoumi(pm);
                }else {
                    pm = xiaoShouService.findAllUserPage(params);
                    pm = handleLoanOrderStatus(pm);
                }

                List<XiaoShouOrder> list = pm.getItems();
                List<Object[]> contents = new ArrayList<Object[]>();
                for (XiaoShouOrder r : list) {
                    String[] conList = new String[titles.length];
                    conList[0] = r.getBatchId() == null ? "" : String.valueOf(r.getBatchId());
                    conList[1] = r.getCompanyId() == null ? "" : saleCompanyMap.get(r.getCompanyId());
                    conList[2] = r.getCurrentCollectionUserName() == null ? "" : r.getCurrentCollectionUserName();
                    if(BackConstant.ORDER_FROM_YMGJ.equals(orderFrom)){
                        conList[3] = r.getMerchantNo() == null ? "" : String.valueOf(r.getMerchantNo());
                    }else {
                        conList[3] = r.getMerchantNo() == null ? "" : merchantNoMap.get(r.getMerchantNo());
                    }
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
        String url = "xiaoshou/userMobile";
        HashMap<String, Object> params = this.getParametersO(request);
        String orderFrom = String.valueOf(params.get("orderFrom"));
        if(BackConstant.ORDER_FROM_YMGJ.equals(orderFrom)){
            url = "xiaoshouYoumi/userMobile";
            String mobile = String.valueOf(params.get("mobile"));//todo_lmy 调取第三方获取手机号
            model.addAttribute("mobile",mobile);
        }else {
            String mobile = String.valueOf(params.get("mobile"));
            model.addAttribute("mobile",mobile);
        }
        return url;
    }

    /**
     * 跳转至添加备注页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("addRemarkPage")
    public String addRemarkPage(HttpServletRequest request, Model model) {
        String url = "xiaoshou/addRemark";
        HashMap<String, Object> params = this.getParametersO(request);
        String orderFrom = String.valueOf(params.get("orderFrom"));
        if(BackConstant.ORDER_FROM_YMGJ.equals(orderFrom)){
            url = "xiaoshouYoumi/addRemark";
        }
        String id = String.valueOf(params.get("id"));
        String remark = String.valueOf(params.get("remark"));
        model.addAttribute("id",id);
        model.addAttribute("params",params);
        return url;
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

        int resultTemp = 0;
        String orderFrom = String.valueOf(params.get("orderFrom"));
        if(BackConstant.ORDER_FROM_YMGJ.equals(orderFrom)){
            resultTemp = xiaoShouOrderDao.updateRemarkFromYmgj(xiaoShouOrder);
        }else {
            resultTemp = xiaoShouOrderDao.updateRemark(xiaoShouOrder);
        }
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
        String url = "xiaoshou/updateUserIntention";
        HashMap<String, Object> params = this.getParametersO(request);
        String orderFrom = String.valueOf(params.get("orderFrom"));
        if(BackConstant.ORDER_FROM_YMGJ.equals(orderFrom)){
            url = "xiaoshouYoumi/updateUserIntention";
        }
        String id = String.valueOf(params.get("id"));
        String remark = String.valueOf(params.get("userIntention"));
        model.addAttribute("id",id);
        model.addAttribute("params",params);
        model.addAttribute("userIntentionMap", userIntentionMap);
        return url;
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
        String orderFrom = String.valueOf(params.get("orderFrom"));
        int resultTemp = 0;
        if(BackConstant.ORDER_FROM_YMGJ.equals(orderFrom)){
            resultTemp = xiaoShouOrderDao.updateUserIntentionFromYmgj(xiaoShouOrder);
        }else {
            resultTemp = xiaoShouOrderDao.updateUserIntention(xiaoShouOrder);
        }

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
