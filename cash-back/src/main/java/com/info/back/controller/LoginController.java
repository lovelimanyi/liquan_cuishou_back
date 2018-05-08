package com.info.back.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.info.back.service.ICompanyIpAddressService;
import com.info.back.utils.BackConstant;
import com.info.back.utils.RequestUtils;
import com.info.web.pojo.CompanyIpAddressDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.info.constant.Constant;
import com.info.back.service.IBackUserService;
import com.info.web.pojo.BackUser;
import com.info.web.util.JedisDataClient;
import com.info.web.util.encrypt.MD5coding;

/**
 * 类描述：后台登录类 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午02:57:46 <br>
 */
@Controller
public class LoginController extends BaseController {
    public static final String PROCESS_URL = "processUrl";
    public static final String RETURN_URL = "returnUrl";
    private static Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    private IBackUserService backUserService;

    @Autowired
    private ICompanyIpAddressService companyIpAddressService;

    /**
     * 获得地址
     *
     * @param processUrl
     * @param returnUrl
     * @param
     * @return
     */
    private String getView(String processUrl, String returnUrl) {
        if (!StringUtils.isBlank(processUrl)) {
            StringBuilder sb = new StringBuilder("redirect:");
            sb.append(processUrl);
            if (!StringUtils.isBlank(returnUrl)) {
                sb.append("?").append(RETURN_URL).append("=").append(returnUrl);
            }
            return sb.toString();
        } else if (!StringUtils.isBlank(returnUrl)) {
            StringBuilder sb = new StringBuilder("redirect:");
            sb.append(returnUrl);

            return sb.toString();
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request,
                        HttpServletResponse response, Model model) {

        try {
            String processUrl = request.getParameter(PROCESS_URL);
            String returnUrl = request.getParameter(RETURN_URL);
            String message = request.getParameter(MESSAGE);
            BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
            if (backUser != null) {
                String view = getView(processUrl, returnUrl);
                if (view != null) {
                    return view;
                } else {

                    return "index";
                }
            }
            if (!StringUtils.isBlank(processUrl)) {
                model.addAttribute(PROCESS_URL, processUrl);
            }
            if (!StringUtils.isBlank(returnUrl)) {
                model.addAttribute(RETURN_URL, returnUrl);
            }
            if (!StringUtils.isBlank(message)) {
                model.addAttribute(MESSAGE, message);
            }
        } catch (Exception e) {
            logger.error("back login error ", e);
        }
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submit(HttpServletRequest request,
                         HttpServletResponse response, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        String errMsg = null;
        try {
            if (checkIpAccess(request)) {
                if (validateSubmit(request, response)) {
                    params.put("status", BackUser.STATUS_USE);
                    BackUser backUser = backUserService.findOneUser(params);
                    if (backUser != null && !BackConstant.BACK_USER_STATUS.equals(backUser.getUserStatus().toString())) {
                        if (backUser.getUserPassword().equals(MD5coding.getInstance().code(String.valueOf(params.get("userPassword"))))) {
                            request.getSession(true).setAttribute(Constant.BACK_USER, backUser);
                            request.getSession(true).setMaxInactiveInterval(1800);
                        } else {
                            errMsg = "密码错误！";
                        }
                    } else {
                        errMsg = "该用户不存在！";
                    }
                } else {
                    errMsg = "验证码错误";
                }
            } else {
                errMsg = "非法ip,请联系管理员。";
            }
        } catch (Exception e) {
            errMsg = "服务器异常，稍后重试！";
            logger.error("post login error params=" + params, e);
        }
        if (errMsg != null) {
            model.addAttribute(MESSAGE, errMsg);
            return "login";
        } else {
            return "redirect:indexBack";
        }
    }

    /**
     * 检验请求的ip是否在允许的范围内
     *
     * @param request
     * @return
     */
    private boolean checkIpAccess(HttpServletRequest request) throws Exception {
        List<String> orayIps = JedisDataClient.getList("orayIps", 0, -1);
        if (CollectionUtils.isEmpty(orayIps)) {
            List<CompanyIpAddressDto> companyIps = companyIpAddressService.getAllIps();
            for (CompanyIpAddressDto companyIpAddress : companyIps) {
                String CompanyIps = companyIpAddress.getIpAddress();
                if (StringUtils.isNotEmpty(CompanyIps)) {
                    orayIps.add(CompanyIps);
                }
            }
            JedisDataClient.setList("orayIps", orayIps);
        }

        String clientIp = RequestUtils.getClientIpAddr(request);
        if (clientIp == null || orayIps == null) {
            request.getSession().removeAttribute(Constant.BACK_USER);
        }
        boolean loginFlag = false;
        if (orayIps.contains(clientIp)) {
            loginFlag = true; // 在花生壳范围内可以登录
        }
        return loginFlag;
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute(Constant.BACK_USER);
        return "redirect:login";
    }
}
