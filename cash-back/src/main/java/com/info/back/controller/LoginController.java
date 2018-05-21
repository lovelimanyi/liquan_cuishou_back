package com.info.back.controller;

import com.info.back.service.IBackUserService;
import com.info.back.service.ICompanyIpAddressService;
import com.info.back.service.IUserAccountWhiteListService;
import com.info.back.utils.BackConstant;
import com.info.back.utils.RequestUtils;
import com.info.back.utils.ServiceResult;
import com.info.back.utils.SmsSendUtil;
import com.info.back.utils.SpringUtils;
import com.info.constant.Constant;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.CompanyIpAddressDto;
import com.info.web.util.JedisDataClient;
import com.info.web.util.encrypt.AESUtil;
import com.info.web.util.encrypt.MD5coding;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

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
    public final static String SMS_SEND_IN_ONE_MINUTE = "SMS_SEND_IN_ONE_MINUTE_";// Redis某个手机号1分钟内已发送验证码key前缀
    public final static int INFECTIVE_SMS_TIME = 300;// 短信默认有效时间300秒
    @Autowired
    private IBackUserService backUserService;

    @Autowired
    private ICompanyIpAddressService companyIpAddressService;

    @Autowired
    private IUserAccountWhiteListService userAccountWhiteListService;

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
    public String login(HttpServletRequest request, Model model) {
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

    @RequestMapping(value = "/sendSmsBack", method = RequestMethod.POST)
    public void sendSmsBack(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> params = this.getParametersO(request);
        ServiceResult serviceResult = new ServiceResult("500", "未知异常");
        try {
            if (validateSubmit(request, response)) {
                params.put("status", BackUser.STATUS_USE);
                BackUser backUser = backUserService.findOneUser(params);

                if (backUser != null) {
                    Object tmpPwd = params.get("userPassword");
                    if (tmpPwd != null) {
                        AESUtil aesEncrypt = new AESUtil();

                        if (backUser.getUserPassword().equals(MD5coding.getInstance().code(String.valueOf(params.get("userPassword"))))) {
                            String userPhone = backUser.getUserMobile();
                            String hasSendOneMinKey = SMS_SEND_IN_ONE_MINUTE + userPhone;
                            if (JedisDataClient.get(hasSendOneMinKey) != null) {
                                serviceResult.setMsg("请一分钟后再尝试发送");
                            } else {
                                String rand = String.valueOf(Math.random()).substring(2).substring(0, 6);// 6位固定长度
                                if (SmsSendUtil.sendSmsNew(userPhone, rand)) {
                                    JedisDataClient.set(hasSendOneMinKey, userPhone, 60);
                                    // 存入redis
                                    JedisDataClient.set(BackConstant.SMS_REGISTER_PREFIX + userPhone, rand, INFECTIVE_SMS_TIME);
                                    logger.info("调用短信接口成功！手机号 " + userPhone + "  本次验证码为 " + rand);
//                                    JedisDataClient.setex(SMS_REGISTER_PREFIX + userPhone, INFECTIVE_SMS_TIME, rand);
                                    serviceResult = new ServiceResult(ServiceResult.SUCCESS, "发送成功！");
                                } else {
                                    serviceResult.setMsg("短信发送失败,请联系技术！");
                                }
                            }

                        } else {
                            serviceResult.setMsg("登录密码错误！");
                        }
                    } else {
                        serviceResult.setMsg("密码不能为空！");
                    }
                } else {
                    serviceResult.setMsg("该用户不存在！");
                }
            } else {
                serviceResult.setMsg("图形验证码错误");
            }
        } catch (Exception e) {
            logger.error("sendSmsBack error params=" + params, e);
        }

        SpringUtils.renderJson(response, serviceResult);
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submit(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        String errMsg;
        try {
            if (CollectionUtils.isEmpty(BackConstant.userAccountWhiteListList)) {
                userAccountWhiteListService.updateUserAccountWhiteList();
            }

            if (!checkIpAccess(request, params)) {
                errMsg = "非法ip,请联系管理员。";
                model.addAttribute(MESSAGE, errMsg);
                return "login";
            }

//
//            if (!validateSubmit(request, response)) {
//                errMsg = "验证码错误";
//                model.addAttribute(MESSAGE, errMsg);
//                return "login";
//            }
            params.put("status", BackUser.STATUS_USE);
            BackUser backUser = backUserService.findOneUser(params);
            if (backUser == null || BackConstant.BACK_USER_STATUS.equals(backUser.getUserStatus().toString())) {
                errMsg = "该用户不存在！";
                model.addAttribute(MESSAGE, errMsg);
                return "login";
            }

            if (!backUser.getUserPassword().equals(MD5coding.getInstance().code(String.valueOf(params.get("userPassword"))))) {
                errMsg = "密码错误！";
                model.addAttribute(MESSAGE, errMsg);
                return "login";
            }

            String key = BackConstant.SMS_REGISTER_PREFIX + backUser.getUserMobile();
            String smsCode = params.get("smsCode") + "";
            String code = "0000";
            // 666666 ，测试环境下登录注掉下边这行，取消上边一行的注释
//            String code = JedisDataClient.get(key);
            if (code != null) {
                if (code.equals(smsCode)) {
                } else {
                    errMsg = "短信验证码错误！";
                    model.addAttribute(MESSAGE, errMsg);
                    return "login";
                }
            } else {
                errMsg = "验证码失效或不存在！";
                model.addAttribute(MESSAGE, errMsg);
                return "login";
            }

            request.getSession(true).setAttribute(Constant.BACK_USER, backUser);
            request.getSession(true).setMaxInactiveInterval(3600);
        } catch (Exception e) {
            errMsg = "服务器异常，稍后重试！";
            model.addAttribute(MESSAGE, errMsg);
            logger.error("post login error params=" + params, e);
        }
        return "redirect:indexBack";
    }

    /**
     * 检验请求的ip是否在允许的范围内
     *
     * @param request
     * @return
     */
    private boolean checkIpAccess(HttpServletRequest request, HashMap<String, Object> param) throws Exception {
        String userAccount = param.get("userAccount") == null ? "" : param.get("userAccount").toString();
        if (BackConstant.userAccountWhiteListList.contains(userAccount)) {
            return true;
        }
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
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constant.BACK_USER);
        return "redirect:login";
    }
}
