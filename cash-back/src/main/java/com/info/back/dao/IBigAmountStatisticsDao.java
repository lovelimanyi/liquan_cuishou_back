package com.info.back.dao;

import com.info.web.pojo.BigAmountStatistics;

import java.util.HashMap;
import java.util.List;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/5/2 0002下午 02:04
 */
public interface IBigAmountStatisticsDao {
    List<BigAmountStatistics> getBigAmountPersonStatistics(HashMap<String, Object> paramss);

    void insertPersonStatistics(BigAmountStatistics bigAmountStatistics);

    List<BigAmountStatistics> getBigAmountCompanyStatistics(HashMap<String, Object> paramss);

    void insertCompanyStatistics(BigAmountStatistics bigAmountStatistics);

    void delBigAmountPersonStatistics(HashMap<String, Object> delParam);

    void delBigAmountCompanyStatistics(HashMap<String, Object> delParam);
}
