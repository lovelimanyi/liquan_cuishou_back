package com.info.back.controller;

import com.alibaba.fastjson.JSON;
import com.info.back.result.JsonResult;
import com.info.back.service.IBackUserCompanyPermissionService;
import com.info.back.service.ICollectionCompanyService;
import com.info.back.service.ICollectionService;
import com.info.back.utils.BackConstant;
import com.info.back.utils.DwzResult;
import com.info.back.utils.IdGen;
import com.info.back.utils.SpringUtils;
import com.info.constant.Constant;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.BackUserCompanyPermissions;
import com.info.web.pojo.Collection;
import com.info.web.pojo.MmanLoanCollectionCompany;
import com.info.web.util.JedisDataClient;
import com.info.web.util.PageConfig;
import com.info.web.util.encrypt.MD5coding;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 催收员管理Controller
 *
 * @author xieyaling
 * @date 2017-02-10
 */
@Controller
@RequestMapping("collection/")
public class CollectionController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(CollectionController.class);
    @Autowired
    private ICollectionService collectionService;
    @Autowired
    private ICollectionCompanyService collectionCompanyService;
    @Autowired
    private IBackUserCompanyPermissionService backUserCompanyPermissionService;

    /**
     * 查询催收员列表
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("getCollectionPage")
    public String getCompanyPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
            HashMap<String, Object> params = getParametersO(request);
            params.put("noAdmin", Constant.ADMINISTRATOR_ID);
            params.put("realId", BackConstant.COLLECTION_ROLE_ID);
            if ("10022".equals(backUser.getRoleId())) {
                List<String> list = new ArrayList<>();
                List<BackUserCompanyPermissions> permissionSCompaniesByUser = backUserCompanyPermissionService.getPermissionSCompaniesByUser(backUser.getId());
                for (BackUserCompanyPermissions company : permissionSCompaniesByUser) {
                    list.add(company.getCompanyId());
                }
                params.put("companyIds", list);
            }
            PageConfig<Collection> pageConfig = collectionService.findPage(params);
            model.addAttribute("pm", pageConfig);
            model.addAttribute("params", params);// 用于搜索框保留值

            String verifyCodePermission = collectionService.verifyCodeAccess(request) ? "1" : "0";
            model.addAttribute("verifyCodePermission", verifyCodePermission);

            List<MmanLoanCollectionCompany> companyList = collectionCompanyService.getCompanyList(params);
            model.addAttribute("groupNameMap", BackConstant.groupNameMap);
            model.addAttribute("companyList", companyList);
            model.addAttribute("groupStatusMap", BackConstant.groupStatusMap);
        } catch (Exception e) {
            logger.error("getCollectionPage error", e);
        }
        return "collection/collectionList";
    }


    /**
     * xiangxiaoyan 跳转到添加页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("saveUpdateCollection")
    public String saveUpdateUser(HttpServletRequest request, HttpServletResponse response, Model model, Collection collection) {
        HashMap<String, Object> params = this.getParametersO(request);
        String url = null;
        String erroMsg = null;
        try {
            if ("toJsp".equals(String.valueOf(params.get("type")))) {
                if (params.containsKey("id")) {
                    // 更新的页面跳转
                    collection = collectionService.findOneCollection(Integer.parseInt(params.get("id").toString()));
                    model.addAttribute("collection", collection);
                }
                List<MmanLoanCollectionCompany> companyList = collectionCompanyService.selectCompanyList();
                model.addAttribute("groupNameMap", BackConstant.groupNameMap);
                model.addAttribute("companyList", companyList);
                url = "collection/SUCollection";
            } else {
                JsonResult result = new JsonResult("-1", "操作失败");
                // 更新或者添加操作
                if (collection.getId() != null) {
                    result = collectionService.updateById(collection);
                } else {
                    collection.setAddIp(this.getIpAddr(request));
                    collection.setUserPassword(MD5coding.getInstance().code(String.valueOf(params.get("userPassword"))));
                    collection.setUuid(IdGen.uuid());
                    collection.setRoleId(BackConstant.COLLECTION_ROLE_ID.toString());
                    result = collectionService.insert(collection);
                }
                SpringUtils.renderDwzResult(response, "0".equals(result.getCode()), result.getMsg(),
                        DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
                                .toString());
            }
        } catch (Exception e) {
            if (url == null) {
                erroMsg = "服务器异常，请稍后重试！";
                if (e.getLocalizedMessage().indexOf("UK_user_account") >= 0) {
                    erroMsg = "用户名重复！";
                } else if (e.getLocalizedMessage().indexOf("UK_user_name") >= 0) {
                    erroMsg = "真实姓名不能重复";
                }
                SpringUtils.renderDwzResult(response, false, "操作失败,原因："
                        + erroMsg, DwzResult.CALLBACK_CLOSECURRENT, params.get(
                        "parentId").toString());
            }
            logger.error("添加权限信息失败，异常信息:", e);
        }
        model.addAttribute(MESSAGE, erroMsg);
        model.addAttribute("params", params);
        return url;
    }

    /**
     * 删除
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping("deleteCollection")
    public void deleteCollection(HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, String> params = this.getParameters(request);
        JsonResult result = new JsonResult("-1", "删除催收员失败");
        try {
            String id = params.get("id") == null ? "" : params.get("id");
            if (StringUtils.isNotBlank(id)) {
                result = collectionService.deleteCollection(Integer.parseInt(id));
            } else {
                result.setMsg("参数错误");
            }
        } catch (Exception e) {
            logger.error("deleteCompany error", e);
        }
        SpringUtils.renderDwzResult(response, "0".equals(result.getCode()), result.getMsg(),
                DwzResult.CALLBACK_CLOSECURRENTDIALOG, params.get("parentId").toString());
    }

    @RequestMapping("/checkCollections")
    @ResponseBody
    public String getCollectionByUserName(HttpServletRequest request) {
        String s = null;
        try {
            Map<String, String> params = this.getParameters(request);
            String userName = params.get("userName");
            Collection user = collectionService.getCollectionByUserName(userName);
            HashMap<String, Object> res = new HashMap<>();
            if (user != null) {
                res.put("code", "200");
            }
            s = JSON.toJSONString(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    @RequestMapping("/checkUserAccount")
    @ResponseBody
    public String getCollectionByUserAccount(HttpServletRequest request) {
        String s = null;
        try {
            Map<String, String> params = this.getParameters(request);
            String userAccount = params.get("userAccount");
            Collection user = collectionService.getCollectionByUserAccount(userAccount);
            HashMap<String, Object> res = new HashMap<>();
            if (user != null) {
                res.put("code", "200");
            }
            s = JSON.toJSONString(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 获取手机号验证码
     *
     * @param phone
     * @param model
     * @return
     */
    @RequestMapping("/getVerifyCode")
    public String getVerifyCode(@Param("phone") String phone, Model model, HttpServletRequest request) {
        try {
            if (collectionService.verifyCodeAccess(request)) {
                String code = JedisDataClient.get(BackConstant.SMS_REGISTER_PREFIX + phone);
                if (StringUtils.isEmpty(code)) {
                    model.addAttribute("msg", "暂无该手机号验证码，请重新发送后查询。");
                }
                model.addAttribute("phoneNumber", phone);
                model.addAttribute("code", code);
            } else {
                model.addAttribute("msg", "暂无该手机号验证码，请重新发送后查询。");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "collection/verifyCodePage";
    }
}
