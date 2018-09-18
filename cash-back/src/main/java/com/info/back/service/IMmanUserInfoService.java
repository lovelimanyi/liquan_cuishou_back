package com.info.back.service;

import com.info.web.pojo.ContactInfo;
import com.info.web.pojo.EcommerceInfo;
import com.info.web.pojo.MmanUserInfo;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;

public interface IMmanUserInfoService {
	
	/**
	 * 根据主键查询用户信息
	 * @param
	 * @return
	 */
	MmanUserInfo getUserInfoById(String id);
	
	
	MmanUserInfo  getxjxuser(Long id);
	
	
	int saveNotNull(MmanUserInfo mmanUserInfo);
	

	/**
	 * 根据电话号码(借款人对应联系人的)查询对应借款人信息
	 * @param phoneNum
	 * @return
	 */
	List<ContactInfo> getContactInfo(String phoneNum);

	/**
	 * 根据身份证号码前6位获取地址
	 * @param idNumber
	 * @return
	 */
    String getAddressByIDNumber(String idNumber);

    String handleJxl(Model model, String userId);

	MmanUserInfo getUserInfoAccordId(String id);

	void updateUserPhonesByUserId(MmanUserInfo info);

	/**
	 * 获取用户电商交易信息
	 * @param map
	 * @return
	 */
    List<EcommerceInfo> getEconmerceInfo(HashMap<String, Object> map);
}

