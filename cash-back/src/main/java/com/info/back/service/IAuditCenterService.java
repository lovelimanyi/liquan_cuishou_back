package com.info.back.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.back.result.JsonResult;
import com.info.web.pojo.AuditCenter;
import com.info.web.util.PageConfig;

import javax.servlet.http.HttpServletRequest;

public interface IAuditCenterService {
	/**
	 * 添加审核申请
	 * @param params
	 * @return
	 */
	public JsonResult svueAuditCenter(Map<String, String> params);
	/**
	 * 查询审核列表
	 * @param params
	 * @return
	 */
	public PageConfig<AuditCenter> findPage(HashMap<String, Object> params);
	
	public JsonResult updateAuditCenter(Map<String, String> params);
	/**
	 * 
	 * 减免申请
	 * @param params
	 * @return
	 */
	public  JsonResult saveorderdeduction(Map<String, Object> params) throws ParseException;
	
	public PageConfig<AuditCenter> findAllPage(HashMap<String, Object> params);

	public void updateSysStatus(Map<String, String> params);

	public int findAuditStatus(Map<String, Object> params);
	/**
	 *
	 * 获取审核中的减免
	 * @author yyf
	 * @param params
	 */
	int getAuditChecking(HashMap<String, Object> params);
	/**
	 *
	 * 保存减免申请至审核表
	 * @author yyf
	 * @param params
	 * @param request
	 */
	JsonResult saveAuditReduction(HashMap<String, Object> params,HttpServletRequest request);
}
