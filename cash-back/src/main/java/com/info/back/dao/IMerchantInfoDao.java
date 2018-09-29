package com.info.back.dao;

import com.info.web.pojo.MerchantInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author Administrator
 * @CreateTime 2018-05-28 上午 11:17
 **/

@Repository
public interface IMerchantInfoDao {

    List<MerchantInfo> getAll();
}
