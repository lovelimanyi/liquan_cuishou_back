package com.info.back.service;

import java.util.List;
import java.util.Map;

import com.info.back.vo.jxl.UserReport;
import com.info.back.vo.jxl2.JxlUserReport;
import com.info.web.pojo.ContactInfo;
import com.info.web.pojo.MmanUserInfo;

public interface IMmanUserInfoService {
	
	/**
	 * 根据主键查询用户信息
	 * @param mmanUserInfo
	 * @return
	 */
	public MmanUserInfo getUserInfoById(String id);
	
	
	public MmanUserInfo  getxjxuser(Long id);
	
	
	public int saveNotNull(MmanUserInfo mmanUserInfo);
	
	public UserReport findJxlDetail(Map<String,Object> map);

	/**
	 * 根据电话号码(借款人对应联系人的)查询对应借款人信息
	 * @param phoneNum
	 * @return
	 */
	public List<ContactInfo> getContactInfo(String phoneNum);

	public JxlUserReport parseJxlDetail(Map<String,Object> map);
	/**
	 * 根据身份证号码前6位获取地址
	 * @param idNumber
	 * @return
	 */
    String getAddressByIDNumber(String idNumber);
}

