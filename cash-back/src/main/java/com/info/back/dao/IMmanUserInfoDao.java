package com.info.back.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.ContactInfo;
import com.info.web.pojo.MmanUserInfo;

@Repository
public interface IMmanUserInfoDao {

    MmanUserInfo get(String id);

    MmanUserInfo getUserInfoById(String id);


    MmanUserInfo getxjxuser(Long id);


    int saveNotNull(MmanUserInfo mmanUserInfo);

    MmanUserInfo findJxlDetail(Map<String, Object> map);


    List<ContactInfo> getContactInfo(String phoneNum);

    String getAddressByIDNumber(String idNumber);
}
