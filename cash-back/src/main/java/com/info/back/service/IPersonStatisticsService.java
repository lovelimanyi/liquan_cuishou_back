package com.info.back.service;

import com.info.web.pojo.PersonStatistics;
import com.info.web.util.PageConfig;

import java.util.HashMap;
import java.util.List;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/4/19 0019上午 10:02
 */

public interface IPersonStatisticsService {
    List<PersonStatistics> getPersonStatistics(HashMap<String, Object> params);
    void doStatistics(String beginTime,String endTime);

    PageConfig<PersonStatistics> findPage(HashMap<String, Object> params);

    PageConfig<PersonStatistics> findCompanyPage(HashMap<String, Object> params);

    PageConfig<PersonStatistics> findCompanyOtherPage(HashMap<String, Object> params);
}
