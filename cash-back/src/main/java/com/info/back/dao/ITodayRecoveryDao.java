package com.info.back.dao;

import com.info.web.pojo.TodayRecovery;

import java.util.HashMap;
import java.util.List;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/6/15 0015下午 01:50
 */
public interface ITodayRecoveryDao {
    void delTodayStatistics(HashMap<String, Object> delParam);

    List<TodayRecovery> doTodayPersonStatistics();

    void insertTodayPersonStatistics(List<TodayRecovery> list);

    List<TodayRecovery> doTodayCompanyStatistics();

    void insertTodayCompanyStatistics(List<TodayRecovery> list);
}
