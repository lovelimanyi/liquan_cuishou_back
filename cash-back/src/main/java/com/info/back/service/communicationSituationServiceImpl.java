package com.info.back.service;

import com.info.back.dao.ICommunicationSituationDao;
import com.info.back.utils.BackConstant;
import com.info.web.pojo.CommunicationSituation;
import com.info.web.util.JedisDataClient;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @Description:
 * @CreateTime 2018-06-07 下午 5:33
 **/

@Service
public class communicationSituationServiceImpl implements ICommunicationSituationService {

    private final String  COMMUNICATION_LABEL_KEY = "communicationLabels";

    @Autowired
    private ICommunicationSituationDao situationDao;

    @Override
    public List<CommunicationSituation> getLableList() {
        List<CommunicationSituation> list;
        list = JedisDataClient.getList(BackConstant.REDIS_KEY_PREFIX, COMMUNICATION_LABEL_KEY);
        if (CollectionUtils.isEmpty(list)) {
            list = situationDao.getLableList();
            JedisDataClient.setList(BackConstant.REDIS_KEY_PREFIX, COMMUNICATION_LABEL_KEY, list, 60 * 60 * 12);
        }
        return list;
    }
}
