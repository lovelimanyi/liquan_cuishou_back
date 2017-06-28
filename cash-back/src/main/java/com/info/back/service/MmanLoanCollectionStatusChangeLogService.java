package com.info.back.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IMmanLoanCollectionStatusChangeLogDao;
import com.info.back.dao.IPaginationDao;
import com.info.back.utils.IdGen;
import com.info.constant.Constant;
import com.info.web.pojo.MmanLoanCollectionStatusChangeLog;
import com.info.web.util.PageConfig;

@Service
public class MmanLoanCollectionStatusChangeLogService implements
		IMmanLoanCollectionStatusChangeLogService {
	
	@Autowired
	private IMmanLoanCollectionStatusChangeLogDao mmanLoanCollectionStatusChangeLogDao;
	
	@Autowired
	private IPaginationDao paginationDao;
	
	
	public PageConfig<MmanLoanCollectionStatusChangeLog> findPage(
			HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "MmanLoanCollectionStatusChangeLog");
		PageConfig<MmanLoanCollectionStatusChangeLog> pageConfig = new PageConfig<MmanLoanCollectionStatusChangeLog>();
		pageConfig = paginationDao.findPage("findAll", "findAllCount", params,null);
		return pageConfig;
	}


	public void insert(MmanLoanCollectionStatusChangeLog mmanLoanCollectionStatusChangeLog) {
		mmanLoanCollectionStatusChangeLog.setId(IdGen.uuid());
		mmanLoanCollectionStatusChangeLog.setCreateDate(new Date());
		mmanLoanCollectionStatusChangeLogDao.insert(mmanLoanCollectionStatusChangeLog);
		
	}


	@Override
	public List<MmanLoanCollectionStatusChangeLog> findListLog(String orderId) {
		return mmanLoanCollectionStatusChangeLogDao.findListLog(orderId);
	}

	@Override
	public int getAllCount(HashMap<String, Object> params) {
		return mmanLoanCollectionStatusChangeLogDao.findAllCount(params);
	}
	

}
