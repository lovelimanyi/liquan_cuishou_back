package com.info.back.dao;

import com.info.web.pojo.MerchantInfo;

import java.util.List;

/**
 * @Description:
 * @Author Administrator
 * @CreateTime 2018-05-28 上午 11:17
 **/
public interface IMerchantInfoDao {

    List<MerchantInfo> getAll();
}
