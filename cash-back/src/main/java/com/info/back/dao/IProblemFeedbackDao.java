package com.info.back.dao;

import com.info.web.pojo.ProblemFeedback;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/22 0022.
 */
@Repository
public interface IProblemFeedbackDao {

     int insert(ProblemFeedback problemFeedback);

     int updateStatus(Map<String,Object> map);

     String getStatusById(String id);
}
