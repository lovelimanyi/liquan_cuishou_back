package com.info.back.task;

import com.info.back.dao.IAuditCenterDao;
import com.info.back.dao.IMmanLoanCollectionOrderDao;
import com.info.constant.Constant;
import com.info.web.pojo.AuditCenter;
import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskJianmianStatus {
	protected Logger logger = LoggerFactory.getLogger(TaskJianmianStatus.class);
	@Autowired
	private IMmanLoanCollectionOrderDao mmanLoanCollectionOrderDao;
	@Autowired
	private IAuditCenterDao auditCenterDao;
	public void updateStatus() {
		try {
			logger.info("auditStatusInvalid" + DateUtil.getDateFormat(new Date(), "yyyy-MM-dd hh:mm:ss"));

			Map<String, String> auditParams = new HashMap<>();
			auditParams.put("type", Constant.AUDIT_TYPE_REDUCTION);
			auditParams.put("status",Constant.AUDIT_CHECKING);

			List<AuditCenter> auditCenterList = auditCenterDao.getAuditCheckingList(auditParams);
			logger.error("auditStatusInvalidListSize:"+auditCenterList.size());
			HashMap<String, Object> map = new HashMap<>();
			AuditCenter auditCenter = null;
			MmanLoanCollectionOrder order = null;
			for (int i= 0;i<auditCenterList.size(); i++){
				auditCenter = auditCenterList.get(i);
				//更新审核status至4失效
				map.put("auditId", auditCenter.getId());
				map.put("status", Constant.AUDIT_INVALID);
				auditCenterDao.updateAuditStatus(map);
				//更新订单status： 如果订单此时未还款完成，则将订单置为减免前的状态。如果已还款完成，不更新订单stats
				order = mmanLoanCollectionOrderDao.getOrderById(auditCenter.getOrderid());
				if (!order.getStatus().equals(Constant.STATUS_OVERDUE_FOUR)){//订单此时未还款完成
					map.put("orderId",auditCenter.getOrderid());
					map.put("orderStatus",auditCenter.getOrderStatus());
					mmanLoanCollectionOrderDao.updateReductionOrder(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TaskJianmianStatus  updateStatus error:", e);
		}
	}
}

