package com.info.back.dao;

import com.info.web.pojo.InstallmentPayRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInstallmentPayRecordDao {

    public void insert(InstallmentPayRecord installmentPayRecord);

    public List<InstallmentPayRecord> findInstallmentList(String id);

    public InstallmentPayRecord findInstallmentById(String id);

    public void updateInstallmentStatusById(String id);
}