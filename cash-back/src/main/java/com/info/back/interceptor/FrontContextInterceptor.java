package com.info.back.interceptor;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.info.back.service.BackInfoUserService;
import com.info.constant.Constant;
import com.info.web.pojo.User;

public class FrontContextInterceptor extends HandlerInterceptorAdapter {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private BackInfoUserService backZbUserService;
	private String loginUrl = "/gotoLogin";

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler)
			throws ServletException {
		if (null == request.getSession().getAttribute(Constant.FRONT_USER)) {
			if (request.getHeader("x-requested-with") != null
					&& request.getHeader("x-requested-with").equalsIgnoreCase(
							"XMLHttpRequest")) {// 如果是ajax请求响应头会有，x-requested-with；
				response.setHeader("statusCode", "301");// 在响应头设置session状态
				response.setHeader("path", request.getContextPath());// 在响应头设置session状态
				response.setHeader("Referer", getLoginUrl(request));
				return false;
			}
			request.setAttribute("message", "未登录！");
			try {
				response.sendRedirect(getLoginUrl(request));
			} catch (IOException e) {
				logger.error("preHandle error ", e);
			}
			return false;
		} else {
			// 黑名单用户直接强制退出登录；用户的登陆密码与session不一致强制退出登陆
			User sessionUser = (User) request.getSession().getAttribute(
					Constant.FRONT_USER);
			User currentUser = backZbUserService.findById(sessionUser.getId());
			boolean flag = false;
			String msg = "";
			if (User.STATUS_BLACK.intValue() == currentUser.getStatus()
					.intValue()) {
				msg = "黑名单用户不能登录！";
				flag = true;
			} else if (!sessionUser.getUserPassword().equals(
					currentUser.getUserPassword())) {
				flag = true;
				msg = "密码被修改，强制退出！";
			}
			if (flag) {
				try {
					request.getSession().removeAttribute(Constant.FRONT_USER);
					if (request.getHeader("x-requested-with") != null
							&& request.getHeader("x-requested-with")
									.equalsIgnoreCase("XMLHttpRequest")) {
						response.setHeader("Referer", getLoginUrl(request));
						response.setHeader("msg", java.net.URLEncoder.encode(
								msg, "utf-8"));
						response.setHeader("statusCode", "301");
					} else {
						response.sendRedirect(request.getContextPath()
								+ "/gotoLogin?message="
								+ java.net.URLEncoder.encode(msg, "utf-8"));
					}
				} catch (Exception e) {
					logger.error(" judege user error", e);
				}
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private String getLoginUrl(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		String ctx = request.getContextPath();
		String requestUrl = request.getRequestURL().toString();
		if (request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase(
						"XMLHttpRequest")) {
			requestUrl = request.getHeader("Referer");
		}
		if (loginUrl.startsWith("/")) {
			if (!StringUtils.isBlank(ctx)) {
				buff.append(ctx);
			}
		}
		int i = 0, start = 0, count = 3;
		while (i < count && start != -1) {
			start = requestUrl.indexOf('/', start + 1);
			i++;

		}
		buff.append(loginUrl).append("?");
		String retUrl = requestUrl.substring(start);
		if (StringUtils.isNotBlank(retUrl)) {
			Map<String, String[]> params = request.getParameterMap();
			if (params != null && params.size() > 0) {
				int index = retUrl.indexOf("?");
				if (index >= 0) {
					retUrl = retUrl.substring(0, index);
				}
				retUrl += "?";
				for (String key : params.keySet()) {
					Object value = params.get(key);
					Object v = null;
					if ((value.getClass().isArray())
							&& (((Object[]) value).length > 0)) {
						if (((Object[]) value).length > 1) {
							v = ((Object[]) value);
						} else {
							v = ((Object[]) value)[0];
						}
					} else {
						v = value;
					}
					retUrl += key + "=" + v + "&";
				}
				retUrl = retUrl.substring(0, retUrl.length() - 1);
			}
		}

		buff.append("returnUrl").append("=").append(retUrl);
		return buff.toString();
	}
}