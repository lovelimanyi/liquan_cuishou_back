package com.info.back.controller.xiaoshou;

import com.info.back.controller.BaseController;
import com.info.back.dao.IMmanLoanCollectionCompanyDao;
import com.info.back.result.JsonResult;
import com.info.back.service.IXiaoShouService;
import com.info.back.utils.BackConstant;
import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.constant.Constant;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.XiaoShouOrder;
import com.info.web.util.PageConfig;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    IMmanLoanCollectionCompanyDao mmanLoanCollectionCompanyDao;

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
        List<Map<String,String>> saleCompanyList = mmanLoanCollectionCompanyDao.getAllSaleCompany();
        Map<String,String> saleCompanyMap = getSaleCompanyMap(saleCompanyList);

        model.addAttribute("page",page);
        model.addAttribute("params",params);
        model.addAttribute("merchantNoMap", BackConstant.merchantNoMap);
        model.addAttribute("saleCompanyMap", saleCompanyMap);
        return "xiaoshou/allXiaoShouOrder";
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
        List<Map<String,String>> saleCompanyList = mmanLoanCollectionCompanyDao.getAllSaleCompany();
        Map<String,String> saleCompanyMap = getSaleCompanyMap(saleCompanyList);

        model.addAttribute("page",page);
        model.addAttribute("params",params);
        model.addAttribute("merchantNoMap", BackConstant.merchantNoMap);
        return "xiaoshou/myXiaoShouOrder";
    }

    private Map<String,String> getSaleCompanyMap(List<Map<String, String>> saleCompanyList) {
        Map<String,String> map = new HashedMap();
        for (Map<String, String> mapTemp:saleCompanyList) {
            map.put(mapTemp.get("id"),mapTemp.get("title"));
        }
        return map;
    }


}
