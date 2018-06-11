package com.info.back.utils;

import com.info.web.util.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/5/3 0003下午 01:20
 */

public class DateKitUtils {
    public static HashMap<String, Object> defaultDate(String beginTime, String endTime) {
        HashMap<String, Object> params = new HashMap<>();
        if (StringUtils.isNoneBlank(beginTime)&& StringUtils.isNotBlank(endTime)){
            params.put("beginTime",beginTime);
            params.put("endTime",endTime);
        }else {
            String firstDate =  DateUtil.getDateFormat(DateUtil.getDayFirst(),"yyyy-MM-dd 00:00:00");
            params.put("beginTime",firstDate);
            String nowTime = DateUtil.getDateFormat(new Date(),"yyyy-MM-dd HH:45:00");
            params.put("endTime",nowTime);
        }
        return params;
    }





    public static HashMap<String, Object> setDefaultDate(String beginTime, String endTime) {
        HashMap<String, Object> params = new HashMap<>();
        if (StringUtils.isNoneBlank(beginTime)&& StringUtils.isNotBlank(endTime)){
            params.put("beginTime",beginTime);
            params.put("endTime",endTime);
        }else {
            //如果当日是1号,展示上个月的1号到最后一天 ; 不是的话展示当月1号到当天
            if(DateUtil.getDayFirst().equals(new Date())){
                params.put("beginTime", DateUtil.getDateFormat(DateUtil.getNextMon(-1),"yyyy-MM-dd"));
                params.put("endTime", DateUtil.getDateForDayBefor(1, "yyyy-MM-dd"));
            }else{
                params.put("beginTime", DateUtil.getDateFormat(DateUtil.getDayFirst(),"yyyy-MM-dd"));
                params.put("endTime", DateUtil.getDateForDayBefor(0, "yyyy-MM-dd"));
            }

        }
        return params;


    }

    public static HashMap<String,Object> delDate() {
        HashMap<String, Object> params = new HashMap<>();
        String todayDate =  DateUtil.getDateFormat(new Date(),"yyyy-MM-dd 00:00:00");
        params.put("beginTime",todayDate);
        String nowTime = DateUtil.getDateFormat(new Date(),"yyyy-MM-dd HH:45:00");
        params.put("endTime",nowTime);
        return params;
    }


    public static HashMap<String,Object> delTrackDate() {
        HashMap<String, Object> params = new HashMap<>();
        String firstDate =  DateUtil.getDateFormat(DateUtil.getDayFirst(),"yyyy-MM-dd");
        params.put("beginTime",firstDate);
        String lastDate = DateUtil.getDateFormat(DateUtil.getDayLast(),"yyyy-MM-dd");
        params.put("endTime",lastDate);
        return params;
    }

    public static void handleSelectDate(HashMap<String, Object> params) {
        if (null != params.get("dispatchTime")){
            String dispatchTime = (String) params.get("dispatchTime");//起始派单日期
            String dispatchEndTime ="";
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(DateUtil.getDateTimeFormat(dispatchTime,"yyyy-MM-dd"));
            int month = cal1.get(Calendar.MONTH)+1;

            if ( null == params.get("dispatchEndTime")){ //如果最后派单日期为空，则默认为起始派单日当月的最后一天
                dispatchEndTime = DateUtil.getLastDayOfMonth(cal1.get(Calendar.YEAR),month);
            }else { //如果最后派单日期不为空，则为选择日期
                dispatchEndTime = (String) params.get("dispatchEndTime");
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(DateUtil.getDateTimeFormat(dispatchEndTime,"yyyy-MM-dd"));
                int month2 = cal2.get(Calendar.MONTH)+1; //获取所选日期的月份
                if (month != month2){ //如果起始日期和最后日期不在一个月，则最后日期为起始日期当月的最后一天
                    dispatchEndTime = DateUtil.getLastDayOfMonth(cal1.get(Calendar.YEAR),month);
                }
            }

            params.put("dispatchTime",dispatchTime);
            params.put("dispatchEndTime",dispatchEndTime);
            params.put("endTime",DateUtil.getLastDayOfMonth(cal1.get(Calendar.YEAR),month)); //统计截止日

        }else{ //如果未选择任何值，则设置默认值
            params.put("dispatchTime",DateUtil.getDateFormat(DateUtil.getDayFirst(),"yyyy-MM-dd"));
            params.put("dispatchEndTime",DateUtil.getDateFormat(new Date(),"yyyy-MM-dd"));
            params.put("endTime",DateUtil.getDateFormat(DateUtil.getDayLast(),"yyyy-MM-dd"));//统计截止日
            System.out.println("ss");
        }
    }
}
