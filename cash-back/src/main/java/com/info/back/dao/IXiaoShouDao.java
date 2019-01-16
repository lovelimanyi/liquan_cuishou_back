package com.info.back.dao;

import java.util.List;
import java.util.Map;

public interface IXiaoShouDao {
    Integer importExcel(List<Map<String, Object>> paramList);
}
