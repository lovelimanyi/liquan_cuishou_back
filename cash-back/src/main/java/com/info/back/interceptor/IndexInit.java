package com.info.back.interceptor;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;


public class IndexInit implements ServletContextListener {

	private static Logger loger = Logger.getLogger(IndexInit.class);


	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
//		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext());
//		final IDataService dataService = (IDataService)ctx.getBean("dataService");
//		Thread thread = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				dataService.syncDate();
//			}
//		});
//		loger.info("******************START-SYNC************************");
//		thread.start();
//		loger.info("******************END-SUCCESS***********************");
	}

}
