package com.info.back.service;

import java.util.HashMap;

import com.info.web.pojo.BackMessageCenter;
import com.info.web.util.PageConfig;

public interface IMessageCenterService {

	/**
	 * 根据Id查看
	 * 
	 * @param id
	 * @return
	 */
	public BackMessageCenter findById(Integer id);

	/**
	 * 分页查询
	 * 
	 * @param params
	 *            receiveUserId收件人主键ID<br>
	 *            noticeTypeId消息类型：短信、站内信、邮件 <br>
	 *            noticeTypeAll查询全部消息类型传入非空值 messageStatus 消息状态：已读未读等<br>
	 *            messageAddress 收信地址：邮件地址、手机号码等<br>
	 *            messageTitle消息标题
	 * 
	 * @return
	 */
	public PageConfig<BackMessageCenter> findPage(HashMap<String, Object> params);

	/**
	 * 删除
	 * 
	 * @param params
	 *            ids要删除的主键ID集合<br>
	 *            receiveUserId 收信人
	 * @return
	 */
	public int delete(HashMap<String, Object> params);

	public int update(BackMessageCenter backMessageCenter);

	/**
	 * 发送站内信、短信、邮件总的调度方法
	 * 
	 * @param center
	 *            如果没有特别需要，sendUserId可以为空<br>
	 *            receiveUser和receiveUserId两者不能同时为空，receiveUser为空时，
	 *            会根据receiveUserId查询数据库得到user对象 ，否则，取receiveUser<br>
	 * @param noticeCode
	 *            根据此code查询数据库是否发送短信、站内信、邮件等
	 * @return
	 */
	public boolean send(BackMessageCenter center, String noticeCode);

	/**
	 * 发送邮件
	 * 
	 * @param center
	 *            如果没有特别需要，sendUserId可以为空<br>
	 *            receiveUser和receiveUserId两者不能同时为空，receiveUser为空时，
	 *            会根据receiveUserId查询数据库得到user对象 ，否则，取receiveUser<br>
	 * @param noticeCode
	 * @return
	 */
	boolean sendEmail(BackMessageCenter center, String noticeCode);
	/**
	 * 
	 * @param params
	 *            receiveUserId收件人主键ID <br>
	 *            noticeTypeId消息类型：短信、站内信、邮件<br>
	 *            noticeTypeAll查询全部消息类型传入非空值<br>
	 *            messageStatus 消息状态：已读未读等<br>
	 *            messageAddress 收信地址：邮件地址、手机号码等<br>
	 *            messageTitle消息标题<br>
	 * @return
	 */
	public int findParamsCount(HashMap<String, Object> params);
}
