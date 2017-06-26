package com.info.back.task;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.info.back.dao.IMmanLoanCollectionOrderDao;
import com.info.web.pojo.AuditCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.info.back.dao.IAuditCenterDao;
import com.info.web.util.DateUtil;
import org.springframework.util.CollectionUtils;

public class TaskJianmianStatus {
	protected Logger logger = LoggerFactory.getLogger(TaskJianmianStatus.class);
	@Autowired
	private IMmanLoanCollectionOrderDao iMmanLoanCollectionOrderDao;
	@Autowired
	private IAuditCenterDao auditCenterDao;
	public void updateStatus() {
		try {
			logger.error(" 减免定时............." + DateUtil.getDateFormat(new Date(), "yyyy-MM-dd hh:mm:ss"));
			List<AuditCenter> auditCenterlist = auditCenterDao.findgetList();
			logger.info(" select list===========" + auditCenterlist.size());
			for (int i = 0; i < auditCenterlist.size(); i++) {
				AuditCenter auditCenter = auditCenterlist.get(i);
				logger.info("order  auditCenter===========" + auditCenter);
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("loanId", auditCenter.getLoanId());
				map1.put("status", "4");
				auditCenterDao.updateSysStatus(map1);
				HashMap<String, String> ordermap = new HashMap<String, String>();
				ordermap.put("loanId", auditCenter.getLoanId());
				ordermap.put("status", auditCenter.getOrderStatus());
				ordermap.put("reductionMoney", "0");
				iMmanLoanCollectionOrderDao.sveUpdateNotNull(ordermap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TaskJianmianStatus  updateStatus error:", e);
		}
		/**
		 * 减免特殊处理
		 */
	    try {
			logger.error(" 减免特殊订单处理............." + DateUtil.getDateFormat(new Date(), "yyyy-MM-dd hh:mm:ss"));
			List<AuditCenter> list = auditCenterDao.selectGetList();
			logger.info(" AuditCenter  list===========" + list.size());
			if(!CollectionUtils.isEmpty(list)){
				for (int j = 0; j < list.size(); j++) {
					AuditCenter auditCenter = list.get(j);
					logger.info("order  auditCenter===========" + auditCenter);
					if(auditCenter!=null){
						Map<String, String> map1 = new HashMap<String, String>();
						map1.put("loanId", auditCenter.getLoanId());
						map1.put("status", "4");
						auditCenterDao.updateSysStatus(map1);
					}else {
						logger.info("order  auditCenter===========" + auditCenter);
					}
				}
			}
	    }catch (Exception e) {
			logger.info("TaskJianmianStatus  auditCenter===========" + e);
	   }
	}
}

