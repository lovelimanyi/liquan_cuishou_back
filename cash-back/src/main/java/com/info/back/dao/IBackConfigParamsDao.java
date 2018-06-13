package com.info.back.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.BackConfigParams;

@Repository
public interface IBackConfigParamsDao {
    /**
     * @param params sysType参数分类
     * @return
     */
    List<BackConfigParams> findParams(HashMap<String, Object> params);

    /**
     * 更新
     *
     * @param list
     * @return
     */
    int updateValue(List<BackConfigParams> list);

}
