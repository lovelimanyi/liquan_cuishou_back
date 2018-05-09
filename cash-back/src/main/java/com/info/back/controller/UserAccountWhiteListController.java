package com.info.back.controller;

import com.info.back.result.JsonResult;
import com.info.back.service.ICompanyIpAddressService;
import com.info.back.service.IMmanLoanCollectionCompanyService;
import com.info.back.service.IUserAccountWhiteListService;
import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.web.pojo.CompanyIpAddressDto;
import com.info.web.pojo.MmanLoanCollectionCompany;
import com.info.web.pojo.UserAccountWhiteList;
import com.info.web.util.JedisDataClient;
import com.info.web.util.PageConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @Description:
 * @CreateTime 2018-05-07 下午 4:16
 **/

@RequestMapping("/userAccountWhiteList")
@Controller
public class UserAccountWhiteListController extends BaseController {

    private static Logger logger = Logger.getLogger(IndexController.class);

    @Autowired
    private IUserAccountWhiteListService userAccountWhiteListService;


    @RequestMapping("/list")
    public String list(HttpServletRequest request, Model model) {
        try {
            HashMap<String, Object> params = this.getParametersO(request);
            PageConfig<UserAccountWhiteList> page = userAccountWhiteListService.listAll(params);
            model.addAttribute("page", page);
            model.addAttribute("params", params);
        } catch (Exception e) {
            logger.error("company-ip page error!");
            e.printStackTrace();
        }
        return "company/userAccountWhiteListPage";
    }


    @RequestMapping("/saveWhiteList")
    public void save(HttpServletRequest request, HttpServletResponse response) {
        JsonResult result = null;
        HashMap<String, Object> params = this.getParametersO(request);
        try {

            // 新增
            result = userAccountWhiteListService.save(params);

        } catch (Exception e) {
            logger.error("company-ip page error!");
            e.printStackTrace();
        }
        SpringUtils.renderDwzResult(response, "0".equals(result.getCode()), result.getMsg(),
                DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
    }


    /**
     * 添加
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/addCompanyIp")
    public String addCompanyIp(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        try {
            model.addAttribute("params", params);// 用于搜索框保留值
        } catch (Exception e) {
            logger.error("addUserAccountWhiteList page error!");
            e.printStackTrace();
        }
        return "company/addUserAccountWhiteList";
    }


    /**
     * 删除
     *
     * @param request
     * @param response
     */
    @RequestMapping("/deleteUserAccountWhiteList")
    public void deleteCollection(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = this.getParameters(request);
        JsonResult result = new JsonResult("-1", "删除公司ip白名单失败");
        try {
            String id = params.get("id") == null ? "" : params.get("id");
            if (StringUtils.isNotBlank(id)) {
                result = userAccountWhiteListService.deleteById(Integer.parseInt(id));
            } else {
                result.setMsg("参数错误");
            }

        } catch (Exception e) {
            logger.error("deleteCompanyIpAddress error", e);
        }
        SpringUtils.renderDwzResult(response, "0".equals(result.getCode()), result.getMsg(),
                DwzResult.CALLBACK_CLOSECURRENTDIALOG, params.get("parentId").toString());
    }


}
