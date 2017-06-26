package com.info.back.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.info.constant.Constant;
import com.info.back.utils.RequestUtils;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.User;

public class BaseController {
	public static final String MESSAGE = "message";

	/**
	 * 得到 session
	 * 
	 * @return
	 */
	protected HttpSession getSession(HttpServletRequest request) {
		return request.getSession();
	}

	/**
	 * 获取IP地址
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		return RequestUtils.getIpAddr(request);
	}

	@Autowired
	private ImageCaptchaService captchaService;

	/**
	 * 得到session中的admin user对象
	 */
	public BackUser loginAdminUser(HttpServletRequest request) {
		if (request == null) {
			request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
		}
		BackUser backUser = (BackUser) request.getSession().getAttribute(
				Constant.BACK_USER);
		return backUser;
	}

	/**
	 * 得到session中的admin user对象
	 */
	public User loginFrontUser(HttpServletRequest request) {
		if (request == null) {
			request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
		}
		User zbUser = (User) request.getSession().getAttribute(
				Constant.FRONT_USER);
		return zbUser;
	}

	/**
	 * 验证码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean validateSubmit(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			return captchaService.validateResponseForID(request.getSession(
					false).getId(), request.getParameter("captcha")
					.toLowerCase());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获得request中的参数
	 * 
	 * @param request
	 * @return string object类型的map
	 */
	public HashMap<String, Object> getParametersO(HttpServletRequest request) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		if (request == null) {
			request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
		}
		Map req = request.getParameterMap();
		if ((req != null) && (!req.isEmpty())) {
			Map<String, Object> p = new HashMap<String, Object>();
			Collection keys = req.keySet();
			for (Iterator i = keys.iterator(); i.hasNext();) {
				String key = (String) i.next();
				Object value = req.get(key);
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
				if ((v != null) && ((v instanceof String))) {
					String s = ((String) v).trim();
					if (s.length() > 0) {
						p.put(key, s);
					}
				}
			}
			hashMap.putAll(p);
			// 读取cookie
			hashMap.putAll(ReadCookieMap(request));
		}
		return hashMap;
	}
	
	/**
	 * 得到页面传递的参数封装成map
	 */

	public Map<String, String> getParameters(HttpServletRequest request) {
		if (request == null) {
			request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		}
		Map<String, String> p = new HashMap<String, String>();
		Map req = request.getParameterMap();
		if ((req != null) && (!req.isEmpty())) {
			
			Collection keys = req.keySet();
			for (Iterator i = keys.iterator(); i.hasNext();) {
				String key = (String)i.next();
				Object value = req.get(key);
				Object v = null;
				if ((value.getClass().isArray())
						&& (((Object[]) value).length > 0)) {
					v = ((Object[]) value)[0];
				} else {
					v = value;
				}
				if ((v != null) && ((v instanceof String))&&!"\"\"".equals(v)) {
					String s = (String) v;
					if (s.length() > 0) {
						p.put(key, s);
					}
				}
			}
			//读取cookie
			p.putAll(ReadCookieMap(request));
			return p;
		}
		return p;
	}

	/**
	 * 将cookie封装到Map里面
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, String> ReadCookieMap(HttpServletRequest request) {
		Map<String, String> cookieMap = new HashMap<String, String>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie.getValue());
			}
		}
		return cookieMap;
	}
}
