package com.info.back.service;

import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.CountCollectionAssessment;
import com.info.web.util.PageConfig;

public interface ICountCollectionAssessmentService {

    /**
     * 考勤记录分页
     *
     * @param params
     * @return
     */
    PageConfig<CountCollectionAssessment> findPage(
            HashMap<String, Object> params);

    /**
     * 查询出所有满足条件的记录
     *
     * @param params
     * @return
     */
    List<CountCollectionAssessment> findAll(HashMap<String, Object> params);

    Integer findAllCount(HashMap<String, Object> params);

    /**
     * 根据主键查询一条考勤记录
     *
     * @return 查询到的考勤记录对象
     */
    CountCollectionAssessment getOne(HashMap<String, Object> params);

    /**
     * 执行存储过程
     *
     * @param params
     */
    void countCallAssessment(HashMap<String, Object> params);

    void callMGroupAssessment(HashMap<String, Object> params);

    void countCallOrder(HashMap<String, Object> params);

    void deleteAssessmentList(HashMap<String,Object> params);

    void deleteCountCollectionOrder(HashMap<String,Object> params);

}
