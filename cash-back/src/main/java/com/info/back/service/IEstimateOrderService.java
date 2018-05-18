package com.info.back.service;

import com.info.web.pojo.EstimateOrder;
import com.info.web.util.PageConfig;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public interface IEstimateOrderService {

    Map<String, Object> getEstimateOrderList(HashMap<String, Object> params);

    void pullEstimateOrder(String pullDate);
}
