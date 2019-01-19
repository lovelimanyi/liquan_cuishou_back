package com.info.back.dao;

import com.info.web.pojo.XiaoShouOrder;
import com.info.web.util.PageConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IXiaoShouOrderDao {
    Integer importExcel(List<Map<String, Object>> paramList);
    PageConfig<XiaoShouOrder> findAllUser(HashMap<String, Object> params);
    Integer findAllUserCount(HashMap<String, Object> params);

    Long getMaxBatchId();

    void setBatchId(Long BatchId);
    List<XiaoShouOrder> getXiaoShouOrder();

    void insertXiaoShouOrder(XiaoShouOrder order);

    void delXiaoShouInfo(Long id);

    int updateRemark(XiaoShouOrder xiaoShouOrder);

    int updateUserIntention(XiaoShouOrder xiaoShouOrder);
}
