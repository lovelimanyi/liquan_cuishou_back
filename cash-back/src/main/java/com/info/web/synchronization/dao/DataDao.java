package com.info.web.synchronization.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class DataDao extends BaseDao implements IDataDao {

    @Override
    public HashMap<String, Object> getAssetBorrowOrder(HashMap<String, String> map) {
        return getSqlSessionTemplates().selectOne("getAssetBorrowOrder", map);
    }

    @Override
    public HashMap<String, Object> getAssetRepayment(HashMap<String, String> map) {
        return getSqlSessionTemplates().selectOne("getAssetRepayment", map);
    }

    @Override
    public List<HashMap<String, Object>> getAssetRepaymentDetail(HashMap<String, String> map) {
        return getSqlSessionTemplates().selectList("getAssetRepaymentDetail", map);
    }

    @Override
    public HashMap<String, Object> getUserCardInfo(HashMap<String, String> map) {
        return getSqlSessionTemplates().selectOne("getUserCardInfo", map);
    }

    @Override
    public List<HashMap<String, Object>> getUserContacts(HashMap<String, String> map) {
        return getSqlSessionTemplates().selectList("getUserContacts", map);
    }

    @Override
    public HashMap<String, Object> getUserInfo(HashMap<String, String> map) {
        return getSqlSessionTemplates().selectOne("getUserInfo", map);
    }

    @Override
    public List<HashMap<String, Object>> getEstimateOrder(HashMap<String,Object> map) {
        return getSqlSessionTemplates().selectList("getEstimateOrder", map);
    }

    @Override
    public HashMap<String, Object> getDianXiaoOrder(HashMap<String, String> map) {
        return getSqlSessionTemplates().selectOne("getDianXiaoOrder", map);
    }

    @Override
    public String getMerchantNumberByLoanId(String loanId) {
        return getMerchantNumberByLoanId(loanId);
    }

}
