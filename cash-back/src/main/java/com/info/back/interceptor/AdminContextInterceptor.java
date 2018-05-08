package com.info.back.interceptor;

import com.info.back.service.IBackModuleService;
import com.info.back.service.IBackUserService;
import com.info.back.service.ICollectionCompanyService;
import com.info.back.service.ICompanyIpAddressService;
import com.info.back.utils.RequestUtils;
import com.info.constant.Constant;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.CompanyIpAddressDto;
import com.info.web.pojo.MmanLoanCollectionCompany;
import com.info.web.util.JedisDataClient;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：后台拦截器，包括session验证和权限信息 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-27 下午05:38:34 <br>
 */
public class AdminContextInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = Logger.getLogger(AdminContextInterceptor.class);

    @Autowired
    IBackUserService backUserService;
    @Autowired
    IBackModuleService backModuleService;
    @Autowired
    private ICompanyIpAddressService companyIpAddressService;

    @SuppressWarnings("unused")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            BackUser user = null;

            // 正常状态
            user = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
            String uri = getURI(request);

            // 不在验证的范围内
            if (exclude(uri)) {
                return true;
            }

            //ip限制
            if (!this.getLoginFlag(request)) {
                request.getSession().removeAttribute(Constant.BACK_USER);
                return false;
            }

            // 用户为null跳转到登录页面
            if (user == null) {
                if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))// 如果是ajax请求响应头会有，x-requested-with；
                {
                    response.setHeader("statusCode", "301");// 在响应头设置session状态
                    return false;
                }
                response.sendRedirect(getLoginUrl(request));
                return false;
            }

            // 用户的登陆密码与session不一致强制退出登陆
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("id", user.getId());
            BackUser currentUser = backUserService.findOneUser(params);
            if (!user.getUserPassword().equals(currentUser.getUserPassword())) {
                request.getSession().removeAttribute(
                        Constant.BACK_USER);
                if (request.getHeader("x-requested-with") != null
                        && request.getHeader("x-requested-with")
                        .equalsIgnoreCase("XMLHttpRequest"))// 如果是ajax请求响应头会有，x-requested-with；

                {
                    response.setHeader("statusCode", "301");// 在响应头设置session状态
                    return false;
                }
                response.sendRedirect(getLoginUrl(request));
                return false;
            }
//			if (currentUser.getUserStatus().intValue() != BackUser.STATUS_USE
//					.intValue()) {
//				request.setAttribute("message", "用户已被删除");
//				response.sendError(HttpServletResponse.SC_FORBIDDEN);
//				return false;
//			}
            if (Constant.ADMINISTRATOR_ID.intValue() != user.getId().intValue()) {
                String u = null;
                int i;
                i = uri.indexOf("/");
                if (i == -1) {
                    throw new RuntimeException("uri must start width '/':"
                            + uri);
                }
                u = uri.substring(i + 1);
                params.put("moduleUrl", u);
                int count = backModuleService.findModuleByUrl(params);
                if (count > 0) {
                    return true;
                } else {
//					response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return true;
                }

            } else {
                return true;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("admin interrupt error", e);
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler, ModelAndView mav)
            throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

    private String getLoginUrl(HttpServletRequest request) {
        StringBuilder buff = new StringBuilder();
        if (StringUtils.isNotEmpty(loginUrl)) {
            if (loginUrl.startsWith("/")) {
                String ctx = request.getContextPath();
                if (!StringUtils.isBlank(ctx)) {
                    buff.append(ctx);
                }
            }
        }
        buff.append(loginUrl).append("?");
        buff.append("returnUrl").append("=").append(returnUrl);
        if (!StringUtils.isBlank(processUrl)) {
            buff.append("&").append("processUrl").append("=").append(
                    getProcessUrl(request));
        }
        return buff.toString();
    }

    private String getProcessUrl(HttpServletRequest request) {
        StringBuilder buff = new StringBuilder();
        if (StringUtils.isNotEmpty(loginUrl)) {
            if (loginUrl.startsWith("/")) {
                String ctx = request.getContextPath();
                if (!StringUtils.isBlank(ctx)) {
                    buff.append(ctx);
                }
            }
        }
        buff.append(processUrl);
        return buff.toString();
    }

    /**
     * 判断该请求是否是允许不经过session验证的路径
     *
     * @param uri
     * @return
     */
    private boolean exclude(String uri) {
        if (excludeUrls != null) {
            for (String exc : excludeUrls) {
                if (exc.equals(uri)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获得第二个路径分隔符的位置
     *
     * @param request
     * @throws IllegalStateException 访问路径错误，没有二(三)个'/'
     */
    private static String getURI(HttpServletRequest request)
            throws IllegalStateException {
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        String ctxPath = helper.getOriginatingContextPath(request);
        int start = 0, i = 0, count = 1;
        if (!StringUtils.isBlank(ctxPath)) {
            count++;
        }
        while (i < count && start != -1) {
            start = uri.indexOf('/', start + 1);
            i++;
        }
        if (start <= 0) {
            throw new IllegalStateException(
                    "admin access path not like '/back/...' pattern: " + uri);
        }
        return uri.substring(start);
    }

    /**
     * 判断请求过来的ip是否在允许的范围内
     *
     * @param request
     * @return
     * @throws Exception
     */
    public boolean getLoginFlag(HttpServletRequest request) throws Exception {
//        clientIp = "180.175.163.201";
        List<String> orayIps = JedisDataClient.getList("orayIps", 0, -1);
        if (CollectionUtils.isEmpty(orayIps)) {
            List<CompanyIpAddressDto> companyIps = companyIpAddressService.listAll();
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


    private String[] excludeUrls;

    private String loginUrl;
    private String processUrl;
    private String returnUrl;

    public String[] getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getProcessUrl() {
        return processUrl;
    }

    public void setProcessUrl(String processUrl) {
        this.processUrl = processUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}