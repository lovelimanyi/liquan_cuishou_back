package com.info.back.service;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IBackInfoUserDao;
import com.info.back.dao.IPaginationDao;
import com.info.constant.Constant;
import com.info.web.pojo.User;
import com.info.web.util.PageConfig;

@Service
public class BackInfoUserService implements IBackInfoUserService {
	@Autowired
	private IBackInfoUserDao backZbUserDao;
	@Autowired
	private IPaginationDao paginationDao;

	@Override
	public PageConfig<User> findPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "BackInfoUser");
		return paginationDao.findPage("findByParams", "findByParamsCount",
				params, null);
	}

	@Override
	public int updateUser(User zbUser) {
		return backZbUserDao.updateUser(zbUser);
	}

	@Override
	public User findById(Integer id) {
		return backZbUserDao.findById(id);
	}

	@Override
	public User findByUserAccount(String userAccount) {
		if (StringUtils.isNotBlank(userAccount)) {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("userAccountEq", userAccount);
			List<User> list = backZbUserDao.findByParams(params);
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public User findByUserTelePhone(String userTelePhone) {
		if (StringUtils.isNotBlank(userTelePhone)) {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("userTelephoneEq", userTelePhone);
			List<User> list = backZbUserDao.findByParams(params);
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	@Override
	public User findByUserEmail(String userEmail) {
		if (StringUtils.isNotBlank(userEmail)) {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("userEmail", userEmail);
			List<User> list = backZbUserDao.findByParams(params);
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	@Override
	public User findByUserMobile(String userMobileEq) {
		if (StringUtils.isNotBlank(userMobileEq)) {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("userMobileEq", userMobileEq);
			List<User> list = backZbUserDao.findByParams(params);
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

}
