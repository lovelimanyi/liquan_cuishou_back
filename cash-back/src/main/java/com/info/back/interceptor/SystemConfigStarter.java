package com.info.back.interceptor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.info.back.service.BackConfigParamsService;
import com.info.back.service.IBackConfigParamsService;
import com.info.constant.Constant;
import com.info.web.pojo.BackConfigParams;
import com.info.web.pojo.BackMessageCenter;
import com.info.web.pojo.BackNotice;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.User;
import com.info.web.util.ConfigConstant;

/**
 * 
 * 类描述：系统缓存配置 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午08:16:03 <br>
 * 
 * @version
 * 
 */
public class SystemConfigStarter implements Starter {

	private static Logger logger = LoggerFactory
			.getLogger(SystemConfigStarter.class);
	private IBackConfigParamsService backConfigParamsService;

	/**
	 * 获取spring注入的bean对象
	 * 
	 * @param ctx
	 */
	private void initBeans(ServletContext ctx) {

		WebApplicationContext springContext = WebApplicationContextUtils
				.getWebApplicationContext(ctx);
		backConfigParamsService = (BackConfigParamsService) springContext
				.getBean("backConfigParamsService");
	}

	@Override
	public void init(ServletContext ctx) {
		initBeans(ctx); // 初始化spring bean

		String key = null;
		LinkedHashMap<String, String> map = null;
		List<BackConfigParams> parmasList = null;
		List<BackConfigParams> list = backConfigParamsService.findParams(null);
		for (int i = 0; i < list.size(); i++) {
			BackConfigParams cfg = list.get(i);
			if (!cfg.getSysType().equals(key)) {
				if (key != null) {
					ctx.setAttribute(key, map);
					ctx
							.setAttribute(key + Constant.SYS_CONFIG_LIST,
									parmasList);
				}
				map = new LinkedHashMap<String, String>();
				parmasList = new ArrayList<BackConfigParams>();
				key = cfg.getSysType();
				ctx.setAttribute(key, key);
				ctx.setAttribute(key + Constant.SYS_CONFIG_LIST, parmasList);
			}
			map.put(cfg.getSysKey(), cfg.getSysValueAuto());
			parmasList.add(cfg);
			if (i == list.size() - 1) {
				ctx.setAttribute(key, map);
				ctx.setAttribute(key + Constant.SYS_CONFIG_LIST, parmasList);
			}
		}

		ctx.setAttribute(Constant.DEFAULT_PWD, ConfigConstant
				.getConstant(Constant.DEFAULT_PWD));
		ctx.setAttribute(Constant.BACK_URL, ConfigConstant
				.getConstant(Constant.BACK_URL));
		// 用户状态
		ctx.setAttribute("BACKUSER_STATUS_USE", BackUser.STATUS_USE);
		ctx.setAttribute("BACKUSER_STATUS_DELETE", BackUser.STATUS_DELETE);
		ctx.setAttribute("BACKUSER_ALL_STATUS", BackUser.ALL_STATUS);
		ctx.setAttribute("User_ALL_STATUS", User.ALL_STATUS);
		ctx.setAttribute("User_ALL_NAME_STATUS", User.NAME_STATUS);
		ctx.setAttribute("User_ALL_PHONE_STATUS", User.PHONE_STATUS);
		ctx.setAttribute("User_ALL_EMAIL_STATUS", User.EMAIL_STATUS);
		ctx.setAttribute("User_ALL_IMAGE_STATUS", User.IMAGE_STATUS);
		ctx.setAttribute("User_ALL_CARD_TYPE", User.CARD_TYPE);
		ctx.setAttribute("ALL_NOTICE_STATUS", BackNotice.ALL_NOTICE_STATUS);
		ctx.setAttribute("ALL_MESSAGE", BackNotice.ALL_MESSAGE);
		ctx.setAttribute("ALL_EMAIL", BackNotice.ALL_EMAIL);
		ctx.setAttribute("ALL_PHONE", BackNotice.ALL_PHONE);
		ctx.setAttribute("NOTICE_TYPE", BackMessageCenter.NOTICE_TYPE);
		ctx.setAttribute("SMS_REGISTER", Constant.SMS_REGISTER);
		ctx.setAttribute("SMS_DEL_BANK", Constant.SMS_DEL_BANK);
		ctx.setAttribute("SMS_SET_PAY", Constant.SMS_SET_PAY);
		ctx.setAttribute("SMS_SET_PHONE_OLD", Constant.SMS_SET_PHONE_OLD);
		ctx.setAttribute("SMS_SET_PHONE_NEW", Constant.SMS_SET_PHONE_NEW);
		logger.debug("------------load SystemConfig end-------------");
	}
}
