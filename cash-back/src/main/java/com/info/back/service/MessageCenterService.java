package com.info.back.service;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IMessageCenterDao;
import com.info.back.dao.IPaginationDao;
import com.info.back.sms.SmsUtil;
import com.info.back.smtp.MailSendTool;
import com.info.back.utils.RequestUtils;
import com.info.back.utils.ServiceResult;
import com.info.constant.Constant;
import com.info.web.pojo.BackMessageCenter;
import com.info.web.pojo.BackNotice;
import com.info.web.pojo.User;
import com.info.web.util.PageConfig;

@Service
public class MessageCenterService implements IMessageCenterService {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private IMessageCenterDao backMessageCenterDao;
	@Autowired
	private IBackNoticeService backNoticeService;
	@Autowired
	private IPaginationDao paginationDao;
	@Autowired
	private IBackInfoUserService backZbUserService;

	@Override
	public int delete(HashMap<String, Object> params) {
		return backMessageCenterDao.delete(params);
	}

	@Override
	public BackMessageCenter findById(Integer id) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<BackMessageCenter> list = backMessageCenterDao.findParams(params);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	@Override
	public PageConfig<BackMessageCenter> findPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "MessageCenter");
		return paginationDao.findPage("findParams", "findParamsCount", params,
				null);
	}

	@Override
	public boolean send(BackMessageCenter center, String noticeCode) {
		boolean flag = false;
		try {
			if (center.getSendUserId() == null) {
				center.setSendUserId(Constant.ADMINISTRATOR_ID);
			}
			if (StringUtils.isBlank(center.getMessageSendIp())) {
				center.setMessageSendIp(RequestUtils.getIpAddr());
			}
			User receiveUser = null;
			if (center.getReceiveUser() != null) {
				receiveUser = center.getReceiveUser();
			} else {
				receiveUser = backZbUserService.findById(center
						.getReceiveUserId());
				center.setReceiveUser(receiveUser);
			}
			String code = noticeCode.toLowerCase();
			BackNotice notice = backNoticeService.findByCode(code);
			if (notice != null) {
				if (BackNotice.EMAIL_REQUIRED_SELECTED.intValue() == notice
						.getEmail().intValue()) {
					try {
						this.sendEmail(center, code);
					} catch (Exception e) {
						logger.error("sendEmail error center=" + center
								+ ",code=" + code, e);
					}
				}
				if (BackNotice.MESSAGE_REQUIRED_SELECTED.intValue() == notice
						.getMessage().intValue()) {
					try {
						this.sendMessage(center, code);
					} catch (Exception e) {
						logger.error("sendMessage error center=" + center
								+ ",code=" + code, e);
					}
				}
				if (BackNotice.PHONE_REQUIRED_SELECTED.intValue() == notice
						.getPhone().intValue()) {
					try {
						this.sendSms(center, code);
					} catch (Exception e) {
						logger.error("sendSms error center=" + center
								+ ",code=" + code, e);
					}
				}
			}
			flag = true;
		} catch (Exception e) {
			logger.error("send error:" + noticeCode + ";" + center, e);
		}
		return flag;
	}

	@Override
	public boolean sendEmail(BackMessageCenter center, String noticeCode) {
		User receiveUser = null;
		if (center.getReceiveUser() != null) {
			receiveUser = center.getReceiveUser();
		} else {
			receiveUser = backZbUserService.findById(center.getReceiveUserId());
		}
		if (center.getSendUserId() == null) {
			center.setSendUserId(Constant.ADMINISTRATOR_ID);
		}
		center.setMessageAddress(receiveUser.getUserEmail());
		ServiceResult serviceResult = MailSendTool.getInstance().sendEmail(
				center.getMessageTitle(), center.getMessageContent(),
				center.getMessageAddress());
		if (serviceResult.isSuccessed()) {
			center.setMessageStatus(BackMessageCenter.STATUS_SUCCESS);
		} else {
			center.setMessageStatus(BackMessageCenter.STATUS_FAILD);
		}
		center.setNoticeTypeId(BackMessageCenter.EMAIL);
		backMessageCenterDao.insert(center);
		return true;
	}

	public boolean sendMessage(BackMessageCenter center, String noticeCode) {
		User receiveUser = null;
		if (center.getReceiveUser() != null) {
			receiveUser = center.getReceiveUser();
		} else {
			receiveUser = backZbUserService.findById(center.getReceiveUserId());
		}
		center.setMessageAddress(receiveUser.getUserAccount());
		center.setMessageStatus(BackMessageCenter.STATUS_UNREAD);
		center.setNoticeTypeId(BackMessageCenter.MESSAGE);
		backMessageCenterDao.insert(center);
		return true;

	}

	public boolean sendSms(BackMessageCenter center, String noticeCode) {
		User receiveUser = null;
		if (center.getReceiveUser() != null) {
			receiveUser = center.getReceiveUser();
		} else {
			receiveUser = backZbUserService.findById(center.getReceiveUserId());
		}
		center.setMessageAddress(receiveUser.getUserTelephone());
		int ret = SmsUtil.sendSms(center.getMessageAddress(), center
				.getMessageContent(),Constant.NOTICE,null,null);// 发送短信
		// 发送成功为0，其他都为失败
		if (ret == 0) {
			center.setMessageStatus(BackMessageCenter.STATUS_SUCCESS);
		} else {
			center.setMessageStatus(BackMessageCenter.STATUS_FAILD);
		}
		center.setNoticeTypeId(BackMessageCenter.SMS);
		backMessageCenterDao.insert(center);
		return true;
	}

	@Override
	public int update(BackMessageCenter backMessageCenter) {
		return backMessageCenterDao.update(backMessageCenter);
	}

	@Override
	public int findParamsCount(HashMap<String, Object> params) {
		return backMessageCenterDao.findParamsCount(params);
	}

}
