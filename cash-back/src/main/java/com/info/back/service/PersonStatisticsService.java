package com.info.back.service;

import com.info.back.dao.IPaginationDao;
import com.info.back.dao.IPersonStatisticsDao;
import com.info.constant.Constant;
import com.info.web.pojo.AuditCenter;
import com.info.web.pojo.PersonStatistics;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;
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
    public void doStatistics() {
        HashMap<String, Object> paramss = new HashMap<>();
        setDefaultDate(paramss);
        List<PersonStatistics> list = personStatisticsDao.getPersonStatistics(paramss);

        List<PersonStatistics> list2 = personStatisticsDao.getCompanyStatistics(paramss);

        //保存至数据库

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(sdf.format(new Date()));
            for (PersonStatistics personStatistics : list){
                personStatistics.setCreateDate(date);
                personStatisticsDao.insert(personStatistics);
            }
            for (PersonStatistics personStatistics : list2){
                personStatistics.setCreateDate(date);
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


    private void setDefaultDate(HashMap<String, Object> params) {
            //如果当日是1号,展示上个月的1号到最后一天 ; 不是的话展示当月1号到当天
            if(DateUtil.getDayFirst().equals(new Date())){
                params.put("beginTime", DateUtil.getDateFormat(DateUtil.getNextMon(-1),"yyyy-MM-dd"));
                params.put("endTime", DateUtil.getDateForDayBefor(1, "yyyy-MM-dd"));
            }else{
                params.put("beginTime", DateUtil.getDateFormat(DateUtil.getDayFirst(),"yyyy-MM-dd"));
                params.put("endTime", DateUtil.getDateForDayBefor(0, "yyyy-MM-dd"));
            }
    }
}
