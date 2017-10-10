package com.info.back.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.service.IBackRoleService;
import com.info.back.service.IBackUserService;
import com.info.back.service.ICollectionCompanyService;
import com.info.back.utils.BackConstant;
import com.info.back.utils.DwzResult;
import com.info.back.utils.IdGen;
import com.info.back.utils.SpringUtils;
import com.info.back.utils.TreeUtil;
import com.info.constant.Constant;
import com.info.web.pojo.BackTree;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.BackUserCompanyPermissions;
import com.info.web.pojo.MmanLoanCollectionCompany;
import com.info.web.util.PageConfig;
import com.info.web.util.encrypt.MD5coding;

@Controller
@RequestMapping("user/")
public class UserController extends BaseController {

    private static Logger logger = LoggerFactory
            .getLogger(UserController.class);

    @Autowired
    private IBackUserService backUserService;
    @Autowired
    private IBackRoleService backRoleService;
    @Autowired
    private ICollectionCompanyService collectionCompanyService;

    @RequestMapping("getUserPage")
    public String getUserPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            params.put("noAdmin", Constant.ADMINISTRATOR_ID);
            params.put("roleId", BackConstant.COLLECTION_ROLE_ID);
            PageConfig<BackUser> pageConfig = backUserService.findPage(params);
            model.addAttribute("pm", pageConfig);
            model.addAttribute("params", params);// 用于搜索框保留值

        } catch (Exception e) {
            logger.error("getUserPage error", e);
        }
        return "user/userList";
    }


    /**
     * xiangxiaoyan 跳转到添加页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("saveUpdateUser")
    public String saveUpdateUser(HttpServletRequest request,
                                 HttpServletResponse response, Model model, BackUser backUser) {
        HashMap<String, Object> params = this.getParametersO(request);
        String url = null;
        String erroMsg = null;
        try {
            if ("toJsp".equals(String.valueOf(params.get("type")))) {
                List<BackUserCompanyPermissions> CompanyPermissionsList = null;
                if (params.containsKey("id")) {
                    // 更新的页面跳转
                    backUser = backUserService.findOneUser(params);
                    CompanyPermissionsList = backUserService.findCompanyPermissions(Integer.parseInt(params.get("id").toString()));
                    model.addAttribute("backUser", backUser);
                }
                List<MmanLoanCollectionCompany> companyList = collectionCompanyService.selectCompanyList();
                String outGroupHtml = TreeUtil.getCompanyStringWidthCheckBoxOne(companyList, CompanyPermissionsList, "dataComapnyIDs");
                model.addAttribute("outGroupHtml", outGroupHtml);
                model.addAttribute("companyList", companyList);
                url = "user/saveUpdateUser";
            } else {
                String[] dataComapnyIDs = request.getParameterValues("dataComapnyIDs");
                backUser.setDataComapnyIDs(dataComapnyIDs);
                // 更新或者添加操作
                if (backUser.getId() != null) {
                    backUserService.updateById(backUser);
                } else {
                    backUser.setAddIp(this.getIpAddr(request));
                    backUser.setUserPassword(MD5coding.getInstance().code(String.valueOf(params.get("userPassword"))));
                    backUser.setUuid(IdGen.uuid());
                    backUserService.insert(backUser);
                }
                SpringUtils.renderDwzResult(response, true, "操作成功",
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
     * 删除角色
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping("deleteUser")
    public void deleteUser(HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        boolean flag = false;
        try {
            Integer id = Integer.parseInt(params.get("id").toString());
            backUserService.deleteById(id);
            flag = true;
        } catch (Exception e) {
            logger.error("deleteRole error", e);
        }
        SpringUtils.renderDwzResult(response, flag, flag ? "删除用户成功" : "删除用户失败",
                DwzResult.CALLBACK_CLOSECURRENTDIALOG, params.get("parentId")
                        .toString());
    }

    /**
     * 用戶授权
     *
     * @param request
     * @param response
     * @param model
     * @return
     */

    @RequestMapping("saveUserRole")
    public String saveUserRole(HttpServletRequest request,
                               HttpServletResponse response, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        String url = null;
        String erroMsg = null;
        try {
            BackUser backUser = this.loginAdminUser(request);
            if ("toJsp".equals(String.valueOf(params.get("type")))) {
                params.put("userId", backUser.getId());
                List<BackTree> userAll = backRoleService.findRoleTree(params);
                Integer id = Integer.valueOf(params.get("id").toString());
                params.put("userId", id);
                List<BackTree> haveList = backRoleService.findRoleTree(params);
                String outGroupHtml = TreeUtil.getTreeStringWidthCheckBoxOne(
                        userAll, haveList, "roleIds");
                model.addAttribute("outGroupHtml", outGroupHtml);
                params.put("url", "user/saveUserRole");
                params.put("ajaxType", "dialogAjaxDone");
                url = "showRolesRight";
            } else {
                String[] roleIds = request.getParameterValues("roleIds");
                if (roleIds.length > 1) {
                    SpringUtils.renderDwzResult(response, false, "用户授权失败，一个用户不能设置多个角色",
                            DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
                                    .toString());
                    return null;
                }
                url = null;
                params.put("roleIds", request.getParameterValues("roleIds"));
                backUserService.addUserRole(params);
                SpringUtils.renderDwzResult(response, true, "用户授权成功",
                        DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
                                .toString());
            }
        } catch (Exception e) {
            if (url == null) {
                SpringUtils.renderDwzResult(response, false, "操作失败",
                        DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
                                .toString());
            } else {
                erroMsg = "服务器异常，请稍后重试！";
            }
            logger.error("用户授权失败，异常信息:", e);
        }
        model.addAttribute("params", params);
        model.addAttribute(MESSAGE, erroMsg);
        return url;
    }

    /**
     * 修改密码
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("updateUserPassword")
    public String updateUserPassword(HttpServletRequest request,
                                     HttpServletResponse response, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        String target = "";
        boolean bool = false;
        String errorMsg = "";
        try {
            if ("toJsp".equals(String.valueOf(params.get("type")))) {
                model.addAttribute("id", params.get("id"));
                target = "user/updateUserPwd";
            } else {
                BackUser backUser = null;
                if (null == params.get("id")) {
                    backUser = this.loginAdminUser(request);
                } else {
                    HashMap<String, Object> paraUserMap = new HashMap<String, Object>();
                    paraUserMap.put("id", String.valueOf(params.get("id")));
                    backUser = backUserService.findOneUser(paraUserMap);
                }
                Integer id = null == params.get("id") ? backUser.getId() : Integer.parseInt(params.get("id").toString());

                String originalNewPassword = params.get("newPassword").toString();
                String newPassword = MD5coding.getInstance().code(originalNewPassword);
                // 只针对指定账号开放修改密码权限
                if(!BackConstant.managers.contains(backUser.getUserAccount())){
                    errorMsg = "您无权进行该操作,请联系管理员!";
                }else {
                    if (backUser.getUserPassword().equals(newPassword)) {
                        errorMsg = "新密码和原密码不能相同!";
                    } else if (!originalNewPassword.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,16}")) {
                        errorMsg = "密码必须包含大写字母、小写字母、数字三项并且长度在6-16位之间!";
                    } else {
                        // 修改密码
                        BackUser backUser2 = new BackUser();
                        backUser2.setId(id);
                        backUser2.setUserPassword(newPassword);
                        backUserService.updatePwdById(backUser2);
                        bool = true;
                    }
                }
                SpringUtils.renderDwzResult(response, bool, bool ? "操作成功"
                        : "操作失败，" + errorMsg, DwzResult.CALLBACK_CLOSECURRENT);
                target = null;
            }
        } catch (Exception e) {
            logger.error("updateUserPassWord error ", e);
        }
        return target;
    }
}
