package com.info.back.dao;

import com.info.web.pojo.UserAccountWhiteList;

import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 * @Description:
 * @CreateTime 2018-05-09 上午 11:24
 **/
public interface IUserAccountWhiteListDao {

    List<UserAccountWhiteList> listAll(HashMap<String,Object> param);

    Integer listAllCount(HashMap<String,Object> param);

    int deleteById(Integer id);

    int insert(UserAccountWhiteList whiteList);
}
