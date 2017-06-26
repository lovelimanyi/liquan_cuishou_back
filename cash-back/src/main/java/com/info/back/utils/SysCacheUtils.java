package com.info.back.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;

import com.info.constant.Constant;
import com.info.web.pojo.BackConfigParams;

/**
 * 提供读取缓存的方法
 * 
 * @author Liutq
 * 
 */
public class SysCacheUtils {

	private static ServletContext servletContext = null;

	/**
	 * 获取系统参数
	 * 
	 * @return 2014-7-16 fx
	 */
	public static LinkedHashMap<String, String> getConfigParams(String type) {
		return (LinkedHashMap<String, String>) getServletContext().getAttribute(type);
	}

	/**
	 * 获取系统参数
	 * 
	 * @return 2014-7-16 fx
	 */
	public static List<BackConfigParams> getListConfigParams(String type) {
		type = type + Constant.SYS_CONFIG_LIST;
		return (List<BackConfigParams>) getServletContext().getAttribute(type);
	}

	public static ServletContext getServletContext() {
		if (servletContext == null) {
			servletContext = ContextLoader.getCurrentWebApplicationContext()
					.getServletContext();
		}
		return servletContext;
	}

}
