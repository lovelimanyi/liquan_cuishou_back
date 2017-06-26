package com.info.back.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.MmanLoanCollectionRule;

@Repository
public interface IMmanLoanCollectionRuleDao {
	
	public List<MmanLoanCollectionRule> findList(MmanLoanCollectionRule mmanLoanCollectionRule);

	public MmanLoanCollectionRule getCollectionRuleById(String id);

	public int update(MmanLoanCollectionRule collectionRule);

	public void insert(MmanLoanCollectionRule mmanLoanCollectionRule);

	public Integer findCompanyGoupOnline(HashMap<String, String> params);
}
