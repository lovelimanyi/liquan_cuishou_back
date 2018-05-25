package com.info.back.service;

import java.util.List;
import java.util.Map;

public interface IMailCollectionService {

    List<Map<String,Object>>  selectSendResult();

    List<Map<String,Object>> selectBeyondWarn();
}
