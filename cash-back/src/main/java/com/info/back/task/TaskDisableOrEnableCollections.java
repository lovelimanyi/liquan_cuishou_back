package com.info.back.task;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.info.back.service.IBackUserService;

/**
 * @Author Administrator
 * @Description: 启用催收员
 * @Params
 * @Return 
 * @CreateTime 2017/11/2 0002 上午 11:29
 */
public class TaskDisableOrEnableCollections {
	protected Logger logger = Logger.getLogger(TaskDisableOrEnableCollections.class);
	
	@Autowired
	private IBackUserService backUserService;

	public void disableCollections() {
		logger.error("start   >>>>>  禁用催收员定时 执行时间：" + new Date().toLocaleString());
		try {
			backUserService.disableCollections();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("禁用催收员出错:" + e);
		}
		logger.error("end   >>>>>  禁用催收员定时 执行时间："+ new Date().toLocaleString());
	}
	
	public void enableCollections() {
		logger.error("start   >>>>>  启用催收员定时 执行时间：" + new Date().toLocaleString());
		try {
			backUserService.enableCollections();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("启用催收员出错:" + e);
		}
		logger.error("end   >>>>>  启用催收员定时 执行时间："+ new Date().toLocaleString());
	}
}
