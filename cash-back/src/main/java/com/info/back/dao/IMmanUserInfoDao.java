package com.info.back.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.ContactInfo;
import com.info.web.pojo.MmanUserInfo;

@Repository
public interface IMmanUserInfoDao {

    public MmanUserInfo get(String id);


    public MmanUserInfo getxjxuser(Long id);


    public int saveNotNull(MmanUserInfo mmanUserInfo);

    public MmanUserInfo findJxlDetail(Map<String, Object> map);


    public List<ContactInfo> getContactInfo(String phoneNum);

}
