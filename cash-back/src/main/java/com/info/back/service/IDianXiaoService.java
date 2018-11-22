package com.info.back.service;

import com.info.back.result.JsonResult;
import com.info.web.pojo.DianXiaoOrder;
import com.info.web.util.PageConfig;

import java.util.HashMap;
import java.util.List;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/11/14 0014下午 10:41
 */
public interface IDianXiaoService {
    PageConfig<DianXiaoOrder> getDianXiaoPage(HashMap<String, Object> params);

    DianXiaoOrder getDianXiaoOrder(HashMap<String, Object> params);

    JsonResult updateDianXiaoOrder(HashMap<String, Object> params);

    List<DianXiaoOrder> getDianXiaoOrderList(HashMap<String, Object> params);

    int getDianxiaoOrderCount(HashMap<String, Object> params);

    boolean dispatchDianXiaoOrder(HashMap<String, Object> borrowOrder);


    int getDianXiaoOrderByLoanId(String loanId);
}
