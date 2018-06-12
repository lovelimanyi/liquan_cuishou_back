package com.info.back.service;

import com.info.web.pojo.RecoveryRate;
import com.info.web.pojo.TrackStatistics;
import com.info.web.util.PageConfig;

import java.util.HashMap;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/6/6 0006下午 03:50
 */
public interface IStatisticsService {
    void doTrackStatistics();

    PageConfig<TrackStatistics> findPage(HashMap<String, Object> params);

    PageConfig<TrackStatistics> findCompanyPage(HashMap<String, Object> params);

    void doRecoveryStatistics();

    PageConfig<RecoveryRate> findRecoveryPage(HashMap<String, Object> params);
}
