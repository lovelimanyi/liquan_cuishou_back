package com.info.back.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IBackModuleDao;
import com.info.back.dao.IBackRoleDao;
import com.info.back.dao.IBackRoleModuleDao;
import com.info.web.pojo.BackRoleModule;

@Service
public class BackRoleModuleService implements IBackRoleModuleService {
	@Autowired
	private IBackRoleModuleDao backRoleModuleDao;

	@Override
	public List<BackRoleModule> findAll(HashMap<String, Object> params) {
		return backRoleModuleDao.findAll(params);
	}

	@Override
	public void insertModuleRole(HashMap<String, Object> params) {
		backRoleModuleDao.insertModuleRole(params);
	}

}
