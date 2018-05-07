package com.info.back.service;

import com.info.web.pojo.BigAmountStatistics;
import com.info.web.util.PageConfig;

import java.util.HashMap;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/5/2 0002下午 01:57
 */
public interface IBigAmountStatisticsService {
    PageConfig<BigAmountStatistics> findPage(HashMap<String, Object> params);

    void doStatistics(String beginTime,String endTime);

    PageConfig<BigAmountStatistics> findCompanyPage(HashMap<String, Object> params);
}
