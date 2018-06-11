package com.info.back.service;

import com.info.back.dao.IPaginationDao;
import com.info.back.dao.IStatisticsDao;
import com.info.back.utils.DateKitUtils;
import com.info.constant.Constant;
import com.info.web.pojo.TrackStatistics;
import com.info.web.util.PageConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/6/6 0006下午 03:51
 */
@Service
@Transactional
public class StatisticsService implements IStatisticsService {
    private static Logger logger = LoggerFactory.getLogger(StatisticsService.class);

    @Autowired
    IStatisticsDao statisticsDao;
    @Autowired
    private IPaginationDao paginationDao;

    @Override
    public void doTrackStatistics() {
        //1.先删除之前统计
        HashMap<String, Object> delParam = DateKitUtils.delTrackDate();
        statisticsDao.delTrackStatistics(delParam);
        //2.进行统计
        List<TrackStatistics>  trackPersonList = statisticsDao.doTrackStatistics();
        System.out.println(trackPersonList);
        //3.将统计数据保存至数据库
        statisticsDao.insertTrackStatistics(trackPersonList);
    }

    @Override
    public PageConfig<TrackStatistics> findPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "Statistics");
        return paginationDao.findPage("findAll", "findAllCount", params, null);
}

    @Override
    public PageConfig<TrackStatistics> findCompanyPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "Statistics");
        return paginationDao.findPage("findAllCompany", "findAllCompanyCount", params, null);
    }
}
