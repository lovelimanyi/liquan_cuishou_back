package com.info.back.dao;

import com.info.web.pojo.RecoveryRate;

import java.util.List;

/**
 * 类描述：催回率统计DAO
 * 创建人：yyf
 * 创建时间：2018/6/12 0012上午 11:18
 */

public interface IRecoveryStatisticsDao {


    List<RecoveryRate> doRecoveryStatistics();

    void insertRecoveryStatistics(List<RecoveryRate> list);

    void delRecoveryStatistics();
}
