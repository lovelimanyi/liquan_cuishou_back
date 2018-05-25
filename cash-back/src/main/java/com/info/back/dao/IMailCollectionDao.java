package com.info.back.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IMailCollectionDao {

    List<Map<String,Object>> selectSendResult();


    List<Map<String,Object>> selectBeyondWarn();
}
