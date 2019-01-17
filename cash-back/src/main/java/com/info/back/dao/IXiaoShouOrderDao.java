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

}
