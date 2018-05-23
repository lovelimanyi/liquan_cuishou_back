package com.info.back.service;

import com.info.back.dao.IMailCollectionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MailCollectionService implements IMailCollectionService{

    @Autowired
    private IMailCollectionDao mailCollectionDao;

    @Override
    public List<Map<String, Object>> selectSendResult() {
        return mailCollectionDao.selectSendResult();
    }

    @Override
    public List<Map<String, Object>> selectBeyondWarn() {
        return mailCollectionDao.selectBeyondWarn();
    }
}
