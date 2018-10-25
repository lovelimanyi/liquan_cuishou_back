package com.info.back.dao;


import com.info.web.pojo.RepayChannelConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRepayChannelConfigDao {

    List<RepayChannelConfig> getAll();
}