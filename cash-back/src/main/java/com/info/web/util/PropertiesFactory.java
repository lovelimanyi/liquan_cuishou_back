package com.info.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//读取配置文件
public class PropertiesFactory {

	public static String getValue(String key) {
		InputStream input = null;
		Properties properties = new Properties();
		/**
		 * /dbconfig.properties 绝对路径, 取到的文件是classpath下的
		 * resources/dbconfig.properties 相对路径 获取文件流
		 */
		// 获取到classpath下的文件
		try {
			input = Class.forName(PropertiesFactory.class.getName()).getResourceAsStream("/config/activemq.properties");
			properties.load(input);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 获取到package下的文件
		// input =
		// Class.forName(ClassLoaderDemo.class.getName()).getResourceAsStream("resources/dbconfig.properties");
		return properties.getProperty(key);
	}

	//test
	public static void main(String[] args) throws IOException {
		System.out.println(PropertiesFactory.getValue("user"));

	}
}
