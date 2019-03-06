package com.info.back.dao;

import com.info.web.pojo.XiaoShouOrder;
import com.info.web.util.PageConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IXiaoShouOrderDao {
    Integer importExcel(List<Map<String, Object>> paramList);
    Integer importExcelFromYmgj(List<Map<String, Object>> paramList);



    PageConfig<XiaoShouOrder> findAllUser(HashMap<String, Object> params);
    Integer findAllUserCount(HashMap<String, Object> params);
    Integer findAllUserCountFromYmgj(HashMap<String, Object> params);

    Long getMaxBatchId();
    Long getMaxBatchIdFromYmgj();

    void setBatchId(Long BatchId);
    void setBatchIdFromYmgj(Long maxBatchId);

    List<XiaoShouOrder> getXiaoShouOrder();
    List<XiaoShouOrder> getXiaoShouOrderYmgj();

    void insertXiaoShouOrder(XiaoShouOrder order);
    void insertXiaoShouOrderFromYmgj(XiaoShouOrder order);

    void delXiaoShouInfo(Long id);
    void delXiaoShouInfoFromYmgj(Long id);

    int updateRemark(XiaoShouOrder xiaoShouOrder);
    int updateRemarkFromYmgj(XiaoShouOrder xiaoShouOrder);

    int updateUserIntention(XiaoShouOrder xiaoShouOrder);
    int updateUserIntentionFromYmgj(XiaoShouOrder xiaoShouOrder);

    void deleteAllOrder();
    void deleteAllOrderToYoumi();
}
