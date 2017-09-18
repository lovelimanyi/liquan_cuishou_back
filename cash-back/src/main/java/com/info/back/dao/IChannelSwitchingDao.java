package com.info.back.dao;

import com.info.web.pojo.ChannelSwitching;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/9/15 0015.
 */
@Repository
public interface IChannelSwitchingDao {
    /**
     * 更具渠道描述查询对应的渠道值
     * @param description
     * @return
     */
    ChannelSwitching getChannelValue(String description);
}
