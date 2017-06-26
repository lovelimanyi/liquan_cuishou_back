package com.info.constant;

/**
 * 
 * 类描述：redi缓存常量类 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-7-1 下午09:28:54 <br>
 * 
 * @version
 * 
 */
public class RedisContants {
	/** banner缓存标识 */
	public static final String BANNER_FLAG = "BANNER_FLAG";
	/** 趋势图中管理每个趋势图key的缓存对应的key，使用时需要后缀年月日加以区别 */
	public static final String CHART_KEYS = "CHART_KEYS_";
	/** 每个趋势图缓存对应的key，使用时需要后缀年月日和borrowId加以区别 */
	public static final String CHART_BORROW_RATE = "CHART_BORROW_RATE_";
	/** 趋势图中管理每个趋势图第一出现null值的缓存对应的key，使用时需要后缀年月日加以区别 */
	public static final String CHART_END_KEYS = "CHART_END_KEYS_";
	/** 每个趋势图上一次更新时第一次出现null（时间大于当前时间的）的时间缓存key，使用时需要后缀年月日和borrowId加以区别 */
	public static final String CHART_BORROW_RATE_END = "CHART_BORROW_RATE_END_";
	/** 每个标的日趋势图缓存对应的key，使用时需要后缀borrowId加以区别 */
	public static final String CHART_BORROW_DAY_RATE = "CHART_BORROW_DAY_RATE_";
	/** 资产包缓存标识，使用时后缀borrowId */
	public static final String ASSETS_PACKAGE = "ASSETS_PACKAGE_";
}
