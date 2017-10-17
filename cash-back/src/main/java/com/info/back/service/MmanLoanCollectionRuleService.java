package com.info.back.service;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IMmanLoanCollectionRuleDao;
import com.info.back.dao.IPaginationDao;
import com.info.back.result.JsonResult;
import com.info.constant.Constant;
import com.info.web.pojo.MmanLoanCollectionRule;
import com.info.web.util.PageConfig;

@Service
public class MmanLoanCollectionRuleService implements IMmanLoanCollectionRuleService {
	
	@Autowired
	private IPaginationDao<MmanLoanCollectionRule> paginationDao;
	@Autowired
	private IMmanLoanCollectionRuleDao mmanLoanCollectionRuleDao;
	
	private static Logger logger = Logger.getLogger(MmanLoanCollectionRuleService.class);
	@Override
	public PageConfig<MmanLoanCollectionRule> findPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "MmanLoanCollectionRule");
		return paginationDao.findPage("findAll", "findAllCount", params,null);
	}
	@Override
	public MmanLoanCollectionRule getCollectionRuleById(String id) {
		return mmanLoanCollectionRuleDao.getCollectionRuleById(id);
	}
	@Override
	public JsonResult updateCollectionRule(MmanLoanCollectionRule collectionRule) {
		JsonResult result=new JsonResult("-1","修改规则失败");
		try{
			if(mmanLoanCollectionRuleDao.update(collectionRule)>0){
				result.setCode("0");
				result.setMsg("修改规则成功");
			}
		}catch(Exception e){
			logger.error("MmanLoanCollectionRuleService update", e);
		}
		return result;
	}

}
