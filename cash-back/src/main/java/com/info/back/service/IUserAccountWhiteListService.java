package com.info.back.service;

import com.info.back.result.JsonResult;
import com.info.web.pojo.UserAccountWhiteList;
import com.info.web.util.PageConfig;

import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 * @Description:
 * @CreateTime 2018-05-09 上午 11:24
 **/
public interface IUserAccountWhiteListService {

    PageConfig<UserAccountWhiteList> listAll(HashMap<String, Object> param);

    JsonResult deleteById(Integer id);

    JsonResult save(HashMap<String, Object> param);

    void updateUserAccountWhiteList();
}
