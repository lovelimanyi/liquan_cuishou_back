package com.info.back.utils;

import com.info.web.util.DateUtil;
import org.apache.commons.lang3.StringUtils;

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
}
