/**
 * Copyright &copy; 2012-2014  All rights reserved.
 */
package com.info.back.utils;


import java.io.Serializable;
import java.security.SecureRandom;
import java.util.UUID;

import org.activiti.engine.impl.cfg.IdGenerator;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * 
 * @version 2013-01-15
 */
@Service
@Lazy(false)
public class IdGen{

	private static SecureRandom random = new SecureRandom();
	
	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static void main(String[] args) {
		System.out.println(IdGen.uuid());
	}

}
