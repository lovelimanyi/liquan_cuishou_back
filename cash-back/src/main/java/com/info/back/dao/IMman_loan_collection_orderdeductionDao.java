package com.info.back.dao;

import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.InstallmentPayRecord;
import com.info.web.pojo.Mman_loan_collection_orderdeduction;

public interface IMman_loan_collection_orderdeductionDao {
/**
 *    减免
 * @param record
 * @return
 */
    public  int insertSelective(Mman_loan_collection_orderdeduction record);
    
    /**
     * 根据借款ID 查询单条
     * 
     */
    public List<Mman_loan_collection_orderdeduction> findAllList(String id);
   
}
