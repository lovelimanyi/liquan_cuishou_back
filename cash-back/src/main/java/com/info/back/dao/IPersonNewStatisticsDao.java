package com.info.back.dao;

import com.info.web.pojo.PersonNewStatistics;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2019/1/23 0023下午 02:00
 */
@Repository
public interface IPersonNewStatisticsDao {

    List<PersonNewStatistics> personNewAll(HashMap<String, Object> params);

    List<PersonNewStatistics> companyNewAll(HashMap<String, Object> params);

    void delNewPersonStatistics(HashMap<String, Object> delParam);

    List<PersonNewStatistics> getPersonNewStatistics(HashMap<String, Object> paramss);

    void insertPersonNewStatistics(PersonNewStatistics personNewStatistics);
}
