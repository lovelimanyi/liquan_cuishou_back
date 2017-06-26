package com.info.back.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.AuditCenter;

@Repository
public interface IAuditCenterDao {

	public int saveNotNull(AuditCenter auditCenter);

	public AuditCenter findAuditId(String string);

	public void updateStatus(Map<String, String> params);

	public void updateSysStatus(Map<String, String> params);
	
	public  int saveUpdate(AuditCenter AuditCenter);

	public	List<AuditCenter> findgetList();

	public	List<AuditCenter> selectGetList();

	public  List<AuditCenter> auditBayId(String loanId);

	public int findAuditStatus(Map<String, Object> params);

	void updateAuditStatus(HashMap<String, Object> map);

	public int findAuditStatusByOrderId(Map<String, Object> params);

    int getAuditChecking(HashMap<String, Object> params);


}
