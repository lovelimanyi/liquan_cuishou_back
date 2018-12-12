package com.info.back.service;

import com.info.back.dao.IPaginationDao;
import com.info.back.dao.IRecoveryStatisticsDao;
import com.info.back.dao.IStatisticsDao;
import com.info.back.dao.ITodayRecoveryDao;
import com.info.back.utils.DateKitUtils;
import com.info.constant.Constant;
import com.info.web.pojo.RecoveryRate;
import com.info.web.pojo.TodayRecovery;
import com.info.web.pojo.TrackStatistics;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述： 时间段累计统计，催回率统计service
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
    @Autowired
    IRecoveryStatisticsDao recoveryStatisticsDao;
    @Autowired
    ITodayRecoveryDao todayRecoveryDao;

    @Override
    public void doTrackStatistics() {
        //1.先删除之前统计
        HashMap<String, Object> delParam = DateKitUtils.delTrackDate();
        statisticsDao.delTrackStatistics(delParam);
        //2.进行统计
        List<TrackStatistics>  trackPersonList = statisticsDao.doTrackStatistics();
        if (CollectionUtils.isNotEmpty(trackPersonList)){
            //3.将统计数据保存至数据库
            statisticsDao.insertTrackStatistics(trackPersonList);
        }else {
            logger.info("无数据");
        }

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

    @Override
    public void doRecoveryStatistics() {
        //1.先删除之前统计
        recoveryStatisticsDao.delRecoveryStatistics();
        //2.进行统计
        List<RecoveryRate> recoveryRateList = recoveryStatisticsDao.doRecoveryStatistics();
        if (CollectionUtils.isNotEmpty(recoveryRateList)){
            //3.将统计数据保存至数据库
            recoveryStatisticsDao.insertRecoveryStatistics(recoveryRateList);
        }else {
            logger.info("无数据");
        }


    }

    @Override
    public PageConfig<RecoveryRate> findRecoveryPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "RecoveryStatistics");
        PageConfig<RecoveryRate> pageConfig = paginationDao.findPage("findAll", "findAllCount", params, null);

        List<RecoveryRate> recoveryRateList = pageConfig.getItems();
        for(RecoveryRate recoveryRate : recoveryRateList){
            try {
                int days = DateUtil.daysBetween(recoveryRate.getDispatchTime(),new Date()) + 1;
                if (days <= 1){
                    recoveryRate.setTwoDays("---");
                    recoveryRate.setThreeDays("---");
                    recoveryRate.setFourDays("---");
                    recoveryRate.setFiveDays("---");
                    recoveryRate.setSixDays("---");
                    recoveryRate.setSevenDays("---");
                    recoveryRate.setEightTOTen("---");
                    recoveryRate.setToThirty("---");
                    recoveryRate.setToSixty("---");
                    recoveryRate.setToNinety("---");
                    recoveryRate.setToHundredEight("---");
                    recoveryRate.setOverHundredEight("---");
                }else if (days == 2){
                    recoveryRate.setThreeDays("---");
                    recoveryRate.setFourDays("---");
                    recoveryRate.setFiveDays("---");
                    recoveryRate.setSixDays("---");
                    recoveryRate.setSevenDays("---");
                    recoveryRate.setEightTOTen("---");
                    recoveryRate.setToThirty("---");
                    recoveryRate.setToSixty("---");
                    recoveryRate.setToNinety("---");
                    recoveryRate.setToHundredEight("---");
                    recoveryRate.setOverHundredEight("---");
                }else if (days == 3){
                    recoveryRate.setFourDays("---");
                    recoveryRate.setFiveDays("---");
                    recoveryRate.setSixDays("---");
                    recoveryRate.setSevenDays("---");
                    recoveryRate.setEightTOTen("---");
                    recoveryRate.setToThirty("---");
                    recoveryRate.setToSixty("---");
                    recoveryRate.setToNinety("---");
                    recoveryRate.setToHundredEight("---");
                    recoveryRate.setOverHundredEight("---");
                }else if (days == 4){
                    recoveryRate.setFiveDays("---");
                    recoveryRate.setSixDays("---");
                    recoveryRate.setSevenDays("---");
                    recoveryRate.setEightTOTen("---");
                    recoveryRate.setToThirty("---");
                    recoveryRate.setToSixty("---");
                    recoveryRate.setToNinety("---");
                    recoveryRate.setToHundredEight("---");
                    recoveryRate.setOverHundredEight("---");
                }else if (days == 5){
                    recoveryRate.setSixDays("---");
                    recoveryRate.setSevenDays("---");
                    recoveryRate.setEightTOTen("---");
                    recoveryRate.setToThirty("---");
                    recoveryRate.setToSixty("---");
                    recoveryRate.setToNinety("---");
                    recoveryRate.setToHundredEight("---");
                    recoveryRate.setOverHundredEight("---");
                }else if (days == 6){
                    recoveryRate.setSevenDays("---");
                    recoveryRate.setEightTOTen("---");
                    recoveryRate.setToThirty("---");
                    recoveryRate.setToSixty("---");
                    recoveryRate.setToNinety("---");
                    recoveryRate.setToHundredEight("---");
                    recoveryRate.setOverHundredEight("---");
                }else if (days == 7){
                    recoveryRate.setEightTOTen("---");
                    recoveryRate.setToThirty("---");
                    recoveryRate.setToSixty("---");
                    recoveryRate.setToNinety("---");
                    recoveryRate.setToHundredEight("---");
                    recoveryRate.setOverHundredEight("---");
                }else if (days >= 8 && days <= 10){
                    recoveryRate.setToThirty("---");
                    recoveryRate.setToSixty("---");
                    recoveryRate.setToNinety("---");
                    recoveryRate.setToHundredEight("---");
                    recoveryRate.setOverHundredEight("---");
                }else if (days >= 11 && days <= 30){
                    recoveryRate.setToSixty("---");
                    recoveryRate.setToNinety("---");
                    recoveryRate.setToHundredEight("---");
                    recoveryRate.setOverHundredEight("---");
                }else if (days >= 31 && days <= 60){
                    recoveryRate.setToNinety("---");
                    recoveryRate.setToHundredEight("---");
                    recoveryRate.setOverHundredEight("---");
                }else if (days >= 61 && days <= 90){
                    recoveryRate.setToHundredEight("---");
                    recoveryRate.setOverHundredEight("---");
                }else if (days >= 90 && days <= 180){
                    recoveryRate.setOverHundredEight("---");
                }

            }catch (Exception e ){
                e.printStackTrace();
            }
        }


        return pageConfig;
    }

    @Override
    public PageConfig<TodayRecovery> findTodayPersonPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "TodayRecovery");
        return paginationDao.findPage("findAllPerson", "findAllCountPerson", params, null);
    }

    @Override
    public PageConfig<TodayRecovery> findTodayCompanyPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "TodayRecovery");
        return paginationDao.findPage("findAllCompany", "findAllCountCompany", params, null);
    }

    @Override
    public void doTodayStatistics() {
        //1.先删除之前统计
        HashMap<String, Object> delParam = DateKitUtils.delTodayStatisticsDate();
        todayRecoveryDao.delTodayStatistics(delParam);
        //2.进行统计
        List<TodayRecovery> recoveryRateList =  todayRecoveryDao.doTodayPersonStatistics(); //个人
        List<TodayRecovery> recoveryRateList2 =  todayRecoveryDao.doTodayCompanyStatistics();//公司
        if (CollectionUtils.isNotEmpty(recoveryRateList)&&CollectionUtils.isNotEmpty(recoveryRateList2)){
            //3.将统计数据保存至数据库
            todayRecoveryDao.insertTodayPersonStatistics(recoveryRateList);
            todayRecoveryDao.insertTodayCompanyStatistics(recoveryRateList2);
        }else {
            logger.info("无数据");
        }


    }
}
