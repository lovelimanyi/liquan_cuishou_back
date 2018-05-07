package com.info.back.task;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.info.back.dao.IPersonStatisticsDao;
import com.info.back.service.IBigAmountStatisticsService;
import com.info.back.service.IPersonStatisticsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.info.back.service.ICountCollectionAssessmentService;
import com.info.back.service.ICountCollectionManageService;
import com.info.web.util.DateUtil;
import org.springframework.stereotype.Component;

/**
 * 统计定时
 *
 * @author Administrator
 */
@Component
public class TaskJobStatistics {

    protected Logger logger = Logger.getLogger(TaskJobStatistics.class);
    @Autowired
    private ICountCollectionAssessmentService countCollectionAssessmentService;

    @Autowired
    private ICountCollectionManageService countCollectionManageService;

    @Autowired
    private IPersonStatisticsService personStatisticsService;

    @Autowired
    private IBigAmountStatisticsService bigAmountStatisticsService;

    public void callProcedure() {
        try {
            Date date = new Date();
            Calendar today = Calendar.getInstance();
            int currentHour = today.get(Calendar.HOUR_OF_DAY);
            int day = today.get(Calendar.DAY_OF_MONTH);
            HashMap<String, Object> params = new HashMap<String, Object>();
            // 为减轻服务器(数据库压力),每月1号统计暂停执行
            if (currentHour < 6) {
                // 第一次统计前一天的数据
                params.put("currDate", DateUtil.getBeforeOrAfter(date, -1));
                params.put("begDate", DateUtil.getDateFormat(DateUtil.getBeforeOrAfter(date, -1), "yyyy-MM-dd"));
                params.put("endDate", DateUtil.getDateFormat("yyyy-MM-dd"));
            } else {
                // 第二次开始统计当天的数据
                if (day != 1) {
                    params.put("currDate", DateUtil.getDateFormat("yyyy-MM-dd"));
                    params.put("begDate", DateUtil.getDateFormat("yyyy-MM-dd"));
                    params.put("endDate", DateUtil.getDateFormat(DateUtil.getBeforeOrAfter(date, 1), "yyyy-MM-dd"));
                } else {
                    logger.info("1号统计暂停执行......" + DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
                    return;
                }
            }

            // 考核统计
            countCollectionAssessmentService.countCallAssessment(params);
            logger.info("考核统计执行完成,完成时间 :" + DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
            // 管理统计
            countCollectionManageService.countCallManage(params);
            logger.info("管理统计执行完成,完成时间 :" + DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("call count procedure error:", e);
        }
    }

    /**
     * 催记统计
     */
    public void callCountCollection() {
        logger.info("催记统计开始.....");
        Date date = new Date();
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("begDate", DateUtil.getDateFormat(DateUtil.getBeforeOrAfter(date,-1),"yyyy-MM-dd"));
        countCollectionAssessmentService.countCallOrder(params);
        logger.info("催记统计执行完成,完成时间 :" + DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    //个人统计
    public void personAndCompanyStatistics(){
        logger.info("个人-公司统计开始.....");
        String beginTime = null;
        String endTime = null;
        personStatisticsService.doStatistics(beginTime,endTime);
        logger.info("个人-公司统计执行完成,完成时间 :" + DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
        logger.info("大额统计开始.....");
        bigAmountStatisticsService.doStatistics(beginTime,endTime);
        logger.info("大额统计执行完成,完成时间 :" + DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
    }


    public static void main(String[] args) throws ParseException {
        Date date = new Date();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("currDate", DateUtil.getBeforeOrAfter(date, -1));
        params.put("begDate", DateUtil.getDateFormat(DateUtil.getBeforeOrAfter(date, -1), "yyyy-MM-dd"));
        params.put("endDate", DateUtil.getDateFormat("yyyy-MM-dd"));
        params.put("isFirstDay", date.getDate());   //获取当前日

//		System.out.println(params.get("currDate"));
        System.out.println("begDate :" + params.get("begDate"));
        System.out.println(params.get("endDate"));
//		System.out.println(params.get("isFirstDay"));
        System.out.println(DateUtil.getDateFormat(DateUtil.getDayFirst(), "yyyy-MM-dd"));

        int count = DateUtil.daysBetween(DateUtil.getDayFirst(), new Date());
//		System.out.println("天数："+count);
        for (int i = count; i >= 0; i--) {
//			System.out.println(DateUtil.getBeforeOrAfter(date,-i));
        }
    }


}

