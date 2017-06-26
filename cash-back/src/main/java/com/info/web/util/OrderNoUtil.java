package com.info.web.util;

import java.util.UUID;

public class OrderNoUtil {
	public static OrderNoUtil orderNoUtil;

	public static OrderNoUtil getInstance() {
		if (orderNoUtil == null) {
			orderNoUtil = new OrderNoUtil();
		}
		return orderNoUtil;
	}

	/**
	 * 获得UUID
	 * 
	 * @return
	 */
	public String getUUID() {
		return UUID.randomUUID().toString();
	}
}
