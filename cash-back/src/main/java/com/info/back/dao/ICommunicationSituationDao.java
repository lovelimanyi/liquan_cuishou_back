package com.info.back.dao;

import com.info.web.pojo.CommunicationSituation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类描述：用户dao层 <br>
 * 创建人：hxj
 * 创建时间：2016-6-28 下午01:53:41 <br>
 */
@Repository
public interface ICommunicationSituationDao {

    List<CommunicationSituation> getLableList();

}
