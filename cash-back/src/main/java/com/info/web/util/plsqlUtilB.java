package com.info.web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 * 把数据库取出的字段列(大写)组装成ibatis-sql
 * 
 * @author gaoyuhai
 * 
 */
public class plsqlUtilB {
	public static void main(String[] args) {

		try {
			String str = "returnregist.txt";
			tests(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void tests(String str) throws Exception {
		int i = 0;
		String privincePath = "E:/works/lqbz/b/" + str;
		BufferedReader bufferdReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(
						new File(privincePath))));
		String resultStr = bufferdReader.readLine();

		while (resultStr != null && !"".equals(resultStr)) {
			i++;
			excuJdbc(resultStr, privincePath, str);
			resultStr = bufferdReader.readLine();
		}
		bufferdReader.close();
	}

	public static void writeDateToFile(String filePath, String content)
			throws Exception {
		FileWriter writer = new FileWriter(filePath, true);
		writer.flush();
		writer.write(content);
		writer.close();
	}

	public static void excuJdbc(String string, String path, String fileName)
			throws Exception {
		String xstring = string;
		String pri = "private String ";
		string = string.toLowerCase();
		String[] str = string.split("_");
		if (str.length == 1) {
			writeDateToFile(path + "-select-" + fileName, string + " as "
					+ string + "," + "\r\n");// select
			writeDateToFile(path + "-pojo-" + fileName, pri + string + ";"
					+ "\r\n");// pojo
			writeDateToFile(path + "-save0-" + fileName, xstring + "," + "\r\n");// save
			writeDateToFile(path + "-save1-" + fileName, "#" + string + "#,"
					+ "\r\n");// save
			System.out.println("�?" + string + ">>>>目标=" + str);
		} else {
			String resultStr = str[0];
			for (int i = 1; i < str.length; i++) {
				String sss = str[i].substring(0, 1);
				String s1 = sss.toUpperCase()
						+ str[i].substring(1, str[i].length());
				resultStr = resultStr + s1;
			}
			writeDateToFile(path + "-select-" + fileName, string + " as "
					+ resultStr + "," + "\r\n");
			writeDateToFile(path + "-pojo-" + fileName, pri + resultStr + ";"
					+ "\r\n");
			writeDateToFile(path + "-save0-" + fileName, xstring + "," + "\r\n");// save
			writeDateToFile(path + "-save1-" + fileName, "#" + resultStr + "#,"
					+ "\r\n");// save
			System.out.println("�?" + string + ">>>>目标=" + resultStr);
		}

	}

}
