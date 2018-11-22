package com.info.back.dao;

import com.info.web.pojo.DianXiaoOrder;

import java.util.HashMap;
import java.util.List;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/11/15 0015上午 10:41
 */
public interface IDianXiaoDao {

    DianXiaoOrder getDianXiaoOrder(HashMap<String, Object> params);

    int updateDianXiaoOrder(HashMap<String, Object> params);

    List<DianXiaoOrder> getDianXiaoOrderList(HashMap<String, Object> params);

    int getDianxiaoOrderCount(HashMap<String, Object> params);

    void updateDianXiaoOrderStatus(String loanId);

    void insertDianXiaoOrder(DianXiaoOrder order);

    int getDianXiaoOrderByLoanId(String loanId);
}
