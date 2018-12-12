package com.info.back.service;

import com.info.back.dao.IPaginationDao;
import com.info.back.dao.IPersonStatisticsDao;
import com.info.back.test.TestAutoDispatch;
import com.info.back.utils.DateKitUtils;
import com.info.constant.Constant;
import com.info.web.pojo.AuditCenter;
import com.info.web.pojo.PersonStatistics;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/4/19 0019上午 10:03
 */
@Service
@Transactional
public class PersonStatisticsService implements IPersonStatisticsService {
    private static Logger logger = Logger.getLogger(PersonStatisticsService.class);
    @Autowired
    private IPersonStatisticsDao personStatisticsDao;

    @Autowired
    private IPaginationDao paginationDao;

    @Override
    public List<PersonStatistics> getPersonStatistics(HashMap<String, Object> params) {
        List<PersonStatistics> list = personStatisticsDao.getPersonStatisticsList(params);


        return list;
    }

    @Override
    public void doStatistics(String beginTime,String endTime) {
        //先删除本日的统计
        HashMap<String, Object> delParam = DateKitUtils.delDate();
        personStatisticsDao.delPersonStatistics(delParam);
        personStatisticsDao.delCompanyStatistics(delParam);

        //进行统计
        HashMap<String, Object> paramss = DateKitUtils.defaultDate(beginTime,endTime);
        List<PersonStatistics> list = personStatisticsDao.getPersonStatistics(paramss);
        List<PersonStatistics> list2 = personStatisticsDao.getCompanyStatistics(paramss);
        if (CollectionUtils.isNotEmpty(list)&&CollectionUtils.isNotEmpty(list2)){
            //将统计数据保存至数据库
            try {
                String endTimes = paramss.get("endTime").toString();
                for (PersonStatistics personStatistics : list){
                    if(StringUtils.isNotBlank(endTime)){
                        personStatistics.setCreateDate(DateUtil.getDateTimeFormat(endTime,"yyyy-MM-dd HH:mm:ss"));
                    }else {
                        personStatistics.setCreateDate(DateUtil.getDateTimeFormat(endTimes,"yyyy-MM-dd HH:mm:ss"));
                    }
                    personStatisticsDao.insert(personStatistics);
                }
                for (PersonStatistics personStatistics : list2){
                    if(StringUtils.isNotBlank(endTime)){
                        personStatistics.setCreateDate(DateUtil.getDateTimeFormat(endTime,"yyyy-MM-dd HH:mm:ss"));
                    }else {
                        personStatistics.setCreateDate(DateUtil.getDateTimeFormat(endTimes,"yyyy-MM-dd HH:mm:ss"));
                    }
                    personStatisticsDao.insertCompanyStatistics(personStatistics);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {
            logger.info("无数据");
        }

    }

    @Override
    public PageConfig<PersonStatistics> findPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "PersonStatistics");
        PageConfig<PersonStatistics> pageConfig = new PageConfig<PersonStatistics>();
        pageConfig = paginationDao.findPage("findAll", "findAllCount", params, null);
        return pageConfig;
    }

    @Override
    public PageConfig<PersonStatistics> findCompanyPage(HashMap<String, Object> params) {

        params.put(Constant.NAME_SPACE, "PersonStatistics");
        PageConfig<PersonStatistics> pageConfig = new PageConfig<PersonStatistics>();
        pageConfig = paginationDao.findPage("findCompanyAll", "findCompanyAllCount", params, null);
        return pageConfig;
    }

}
