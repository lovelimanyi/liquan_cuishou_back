package com.info.back.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IBackModuleDao;
import com.info.back.dao.IPaginationDao;
import com.info.constant.Constant;
import com.info.web.pojo.BackModule;
import com.info.web.pojo.BackTree;
import com.info.web.util.PageConfig;

@Service
public class BackModuleService implements IBackModuleService {
	@Autowired
	private IBackModuleDao backModuleDao;

	@Autowired
	private IPaginationDao paginationDao;

	@Override
	public List<BackModule> findAllModule(HashMap<String, Object> params) {
		if (params.containsKey("userId")
				&& Constant.ADMINISTRATOR_ID.intValue() == Integer.valueOf(
						String.valueOf(params.get("userId"))).intValue()) {
			return backModuleDao.findAdminAll(params);
		} else {
			return backModuleDao.findUserAll(params);
		}
	}

	@Override
	public List<BackTree> findModuleTree(HashMap<String, Object> params) {
		if (params.containsKey("userId")
				&& Constant.ADMINISTRATOR_ID.intValue() == Integer.valueOf(
						String.valueOf(params.get("userId"))).intValue()) {
			return backModuleDao.findAdminTree(params);
		} else {
			return backModuleDao.findUserTree(params);
		}
	}

	@Override
	public PageConfig<BackModule> findPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "BackModule");
		PageConfig<BackModule> pageConfig = new PageConfig<BackModule>();
		if (params.containsKey("userId")
				&& Constant.ADMINISTRATOR_ID.intValue() == Integer.valueOf(
						String.valueOf(params.get("userId"))).intValue()) {
			pageConfig = paginationDao.findPage("findAdminAll",
					"findAdminCount", params, null);
		} else {
			pageConfig = paginationDao.findPage("findUserAll", "findUserCount",
					params, null);
		}
		return pageConfig;

	}

	@Override
	public BackModule findById(Integer id) {
		return backModuleDao.findById(id);
	}

	@Override
	public void updateById(BackModule backModule) {
		backModuleDao.updateById(backModule);
	}

	@Override
	public void deleteById(Integer id) {
		backModuleDao.deleteById(id);
	}

	@Override
	public void insert(BackModule backModule) {
		backModuleDao.insert(backModule);
	}

	@Override
	public int findModuleByUrl(HashMap<String, Object> params) {
		return backModuleDao.findModuleByUrl(params);
	}
}
