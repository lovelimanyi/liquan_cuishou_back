package com.info.back.dao;

import com.info.web.pojo.TrackStatistics;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/6/6 0006下午 03:53
 */
@Repository
public interface IStatisticsDao{


    List<TrackStatistics> doTrackStatistics();

    void insertTrackStatistics(List<TrackStatistics> list);

    void delTrackStatistics(HashMap<String, Object> delParam);
}
