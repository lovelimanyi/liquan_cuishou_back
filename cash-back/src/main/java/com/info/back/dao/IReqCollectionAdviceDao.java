package com.info.back.dao;

import com.info.web.pojo.CollectionAdviceResponse;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/8/3 0003下午 02:49
 */
public interface IReqCollectionAdviceDao {
    CollectionAdviceResponse getCollectionAdvice(String loanId);
}
