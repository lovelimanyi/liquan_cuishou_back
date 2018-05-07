package com.info.back.service;

import com.info.back.dao.IPaginationDao;
import com.info.back.dao.IPersonStatisticsDao;
import com.info.back.utils.DateKitUtils;
import com.info.constant.Constant;
import com.info.web.pojo.AuditCenter;
import com.info.web.pojo.PersonStatistics;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;
import org.apache.commons.lang3.StringUtils;
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
        HashMap<String, Object> paramss = DateKitUtils.setDefaultDate(beginTime,endTime);

        List<PersonStatistics> list = personStatisticsDao.getPersonStatistics(paramss);

        List<PersonStatistics> list2 = personStatisticsDao.getCompanyStatistics(paramss);

        //保存至数据库

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(sdf.format(new Date()));
            for (PersonStatistics personStatistics : list){
                if(StringUtils.isNotBlank(endTime)){
                    personStatistics.setCreateDate(DateUtil.getDateTimeFormat(endTime,"yyyy-MM-dd"));
                }else {
                    personStatistics.setCreateDate(date);
                }
                personStatisticsDao.insert(personStatistics);
            }
            for (PersonStatistics personStatistics : list2){
                if(StringUtils.isNotBlank(endTime)){
                    personStatistics.setCreateDate(DateUtil.getDateTimeFormat(endTime,"yyyy-MM-dd"));
                }else {
                    personStatistics.setCreateDate(date);
                }
                personStatisticsDao.insertCompanyStatistics(personStatistics);
            }
        } catch (ParseException e) {
            e.printStackTrace();
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
