package com.info.back.dao;

import com.info.web.pojo.ProblemFeedback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/22 0022.
 */
public interface IProblemFeedbackDao {

     int insert(ProblemFeedback problemFeedback);

     int updateStatus(Map<String,Object> map);

     String getStatusById(String id);
}
