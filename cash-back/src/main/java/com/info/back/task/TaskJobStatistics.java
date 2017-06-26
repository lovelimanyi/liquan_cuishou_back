package com.info.back.task;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.info.back.service.ICountCollectionAssessmentService;
import com.info.back.service.ICountCollectionManageService;
import com.info.web.util.DateUtil;
/**
 * 统计定时
 * @author Administrator
 *
 */
public class TaskJobStatistics {
	
	protected Logger logger = LoggerFactory.getLogger(TaskJobStatistics.class);
	@Autowired
	private ICountCollectionAssessmentService countCollectionAssessmentService;
	
	@Autowired
	private ICountCollectionManageService countCollectionManageService;
	
	public void callProcedure(){
		try {
			Date date = new Date();
			HashMap<String,Object> params = new HashMap<String,Object>();
            params.put("currDate", DateUtil.getBeforeOrAfter(date,-1));
            params.put("begDate", DateUtil.getDateFormat(DateUtil.getBeforeOrAfter(date,-1),"yyyy-MM-dd"));
            params.put("endDate", DateUtil.getDateFormat("yyyy-MM-dd"));
            params.put("isFirstDay", date.getDate());   //获取当前日
			// 考核统计
			countCollectionAssessmentService.countCallAssessment(params);
//			countCollectionAssessmentService.callMGroupAssessment(params);
			// 管理统计
			countCollectionManageService.countCallManage(params);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("call count procedure error:", e);
		}
	}

	/**
	 * 催记统计
	 */
	public void callCountCollection(){
		logger.warn("=====================CJ-=====================");
		Date date = new Date();
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("begDate", DateUtil.getDateFormat(DateUtil.getBeforeOrAfter(date,-1),"yyyy-MM-dd"));
		countCollectionAssessmentService.countCallOrder(params);
	}

	public static void main(String[] args) throws ParseException {
		Date date = new Date();
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("currDate", DateUtil.getBeforeOrAfter(date,-1));
		params.put("begDate", DateUtil.getDateFormat(DateUtil.getBeforeOrAfter(date,-1),"yyyy-MM-dd"));
		params.put("endDate", DateUtil.getDateFormat("yyyy-MM-dd"));
		params.put("isFirstDay", date.getDate());   //获取当前日

//		System.out.println(params.get("currDate"));
		System.out.println("begDate :" + params.get("begDate"));
		System.out.println(params.get("endDate"));
//		System.out.println(params.get("isFirstDay"));
		System.out.println(DateUtil.getDateFormat(DateUtil.getDayFirst(),"yyyy-MM-dd"));

		int count = DateUtil.daysBetween(DateUtil.getDayFirst(),new Date());
//		System.out.println("天数："+count);
		for (int i = count; i>=0 ; i--){
//			System.out.println(DateUtil.getBeforeOrAfter(date,-i));
		}
	}


}

