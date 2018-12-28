package com.info.back.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

	@Override
	public int deleteLogByOrderId(String orderId) {
		return mmanLoanCollectionStatusChangeLogDao.deleteLogByOrderId(orderId);
	}

	@Override
	public void insertMmanLoanCollectionStatusChangeLog(String orderId, String beforeStatus, String afterStatus, String type, String operatorName, String remark, String companyId, String currentCollectionUserId, String currentCollectionUserLevel, String currentCollectionOrderLevel) {
		MmanLoanCollectionStatusChangeLog changeLog = new MmanLoanCollectionStatusChangeLog();
		changeLog.setId(IdGen.uuid());
		changeLog.setLoanCollectionOrderId(orderId);
		if (StringUtils.isNotBlank(beforeStatus)){
			changeLog.setBeforeStatus(beforeStatus);
		}
		changeLog.setAfterStatus(afterStatus);//订单状态 ：0:待催收 1:催收中  4:还款完成
		changeLog.setType(type);// 操作类型 1:入催  2:逾期升级  3:转单  4：还款完成
		changeLog.setCreateDate(new Date());
		changeLog.setOperatorName(operatorName);
		changeLog.setRemark(remark);
		changeLog.setCompanyId(companyId);
		changeLog.setCurrentCollectionUserId(currentCollectionUserId);
		changeLog.setCurrentCollectionUserLevel(currentCollectionUserLevel);
		changeLog.setCurrentCollectionOrderLevel(currentCollectionOrderLevel);
		mmanLoanCollectionStatusChangeLogDao.insert(changeLog);
	}


}
