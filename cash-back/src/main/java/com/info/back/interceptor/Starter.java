package com.info.back.interceptor;

import javax.servlet.ServletContext;

/**
 * 
 * 类描述：缓存接口类 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午08:15:05 <br>
 * 
 * @version
 * 
 */
public interface Starter {
	public void init(ServletContext ctx);
}
