package com.info.web.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * JSON工具类
 */
public class JSONUtil {

	/**
	 * javabean转map
	 * 
	 * @param javaBean
	 * @return
	 */
	public static Map<String, String> beanToMap(Object javaBean) {
		Map<String, String> result = new HashMap<String, String>();
		Method[] methods = javaBean.getClass().getDeclaredMethods();

		for (Method method : methods) {
			try {
				if (method.getName().startsWith("get")) {
					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);

					Object value = method.invoke(javaBean, (Object[]) null);
					result.put(field, null == value ? "" : value.toString());
				}
			} catch (Exception e) {
			}
		}

		return result;
	}

	/**
	 * json对象转换成Map
	 * 
	 * @param jsonObject
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> jsonToMap(JSONObject jsonObject) {
		Map<String, String> result = new HashMap<String, String>();
		Iterator<String> iterator = jsonObject.keys();
		String key = null;
		String value = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			value = jsonObject.getString(key);
			result.put(key, value);
		}
		return result;
	}

	/**
	 * JSON 对象字符格式转换成java对象
	 * 
	 * @param jsonString
	 * @param beanCalss
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToBean(String jsonString, Class<T> beanClass) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		T bean = (T) JSONObject.toBean(jsonObject, beanClass);
		return bean;
	}

	/**
	 * java对象转换成json字符串
	 * 
	 * @param bean
	 * @return
	 */
	public static String beanToJson(Object bean) {
		JSONObject json = JSONObject.fromObject(bean);
		return json.toString();
	}

	/**
	 * java对象List集合转换成json字符串
	 * 
	 * @param beans
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	public static String beanListToJson(List beans) {
		StringBuffer rest = new StringBuffer();
		rest.append("[");
		int size = beans.size();
		for (int i = 0; i < size; i++) {
			rest.append(beanToJson(beans.get(i)) + ((i < size - 1) ? "," : ""));
		}
		rest.append("]");
		return rest.toString();
	}

	/**
	 * String 转List<T>
	 * 
	 * @param <T>
	 * @param jsonArray
	 * @param beanClsss
	 * @return
	 */
	public static <T> List<T> jsonArrayToBean(String jsonArray,
			Class<T> beanClsss) {
		JSONArray jsonArr = JSONArray.fromObject(jsonArray);
		List<T> beanList = new ArrayList<T>();
		for (int i = 0; i < jsonArr.size(); i++) {
			T bean = jsonToBean(String.valueOf(jsonArr.get(i)), beanClsss);
			beanList.add(bean);
		}
		return beanList;
	}
}
