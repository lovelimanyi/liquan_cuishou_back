package com.info.web.synchronization;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.info.back.service.TaskJobMiddleService;
import com.info.constant.Constant;
import com.info.web.synchronization.service.IDataService;
import com.info.web.util.ConfigConstant;


public class DataListener implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}
	

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext());
		arg0.getServletContext().setAttribute(Constant.BACK_URL, ConfigConstant.getConstant(Constant.BACK_URL));
		
		/*final IDataService dataService = (IDataService)ctx.getBean("dataService");
		final TaskJobMiddleService taskJobMiddleService = (TaskJobMiddleService)ctx.getBean("taskJobMiddleService");
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				dataService.syncDate(taskJobMiddleService);
			}
		});
		System.out.println("******************START-SYNC************************");
//		thread.start();
		System.out.println("******************END-SUCCESS***********************");*/
	
	}

}
