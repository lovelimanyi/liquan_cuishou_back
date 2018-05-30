package com.info.back.dao;

import com.info.web.pojo.PersonStatistics;

import java.util.HashMap;
import java.util.List;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/4/19 0019上午 10:43
 */
public interface IPersonStatisticsDao {
    List<PersonStatistics> getPersonStatistics(HashMap<String, Object> params);

    void insert(PersonStatistics personStatistics);

    List<PersonStatistics> getPersonStatisticsList(HashMap<String, Object> params);

    List<PersonStatistics> getCompanyStatistics(HashMap<String, Object> paramss);

    void insertCompanyStatistics(PersonStatistics personStatistics);

    void delPersonStatistics(HashMap<String, Object> delParam);

    void delCompanyStatistics(HashMap<String, Object> delParam);
}
