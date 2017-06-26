package com.info.back.service;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IBackRoleDao;
import com.info.back.dao.IBackRoleModuleDao;
import com.info.back.dao.IBackUserRoleDao;
import com.info.back.dao.IPaginationDao;
import com.info.back.utils.RequestUtils;
import com.info.constant.Constant;
import com.info.web.pojo.BackRole;
import com.info.web.pojo.BackTree;
import com.info.web.pojo.BackUserRole;
import com.info.web.util.PageConfig;

@Service
public class BackRoleService implements IBackRoleService {
	@Autowired
	private IBackRoleDao backRoleDao;
	@Autowired
	private IPaginationDao paginationDao;
	@Autowired
	private IBackUserRoleDao backUserRoleDao;
	@Autowired
	private IBackRoleModuleDao backRoleModuleDao;

	@Override
	public List<BackTree> findRoleTree(HashMap<String, Object> params) {
		if (params.containsKey("userId")
				&& Constant.ADMINISTRATOR_ID.intValue() == Integer.valueOf(
						String.valueOf(params.get("userId"))).intValue()) {
			return backRoleDao.findAdminRoleTree(params);
		} else {
			// showUserListByRoleId(id);
			return backRoleDao.findUserRoleTree(params);
		}
	}

	@Override
	public PageConfig<BackRole> findPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "BackRole");
		PageConfig<BackRole> pageConfig = new PageConfig<BackRole>();
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
	public void deleteById(Integer id) {
		backRoleDao.deleteById(id);
		backUserRoleDao.deleteByRoleId(id);
		backRoleModuleDao.deleteByRoleId(id);
	}

	@Override
	public BackRole findById(Integer id) {
		return backRoleDao.findById(id);
	}

	@Override
	public void insert(BackRole backRole, Integer userId) {
		if (StringUtils.isBlank(backRole.getRoleAddip())) {
			backRole.setRoleAddip(RequestUtils.getIpAddr());
		}
		backRoleDao.insert(backRole);
		if (userId.intValue() != Constant.ADMINISTRATOR_ID.intValue()) {
			BackUserRole backUserRole = new BackUserRole();
			backUserRole.setRoleId(backRole.getId());
			backUserRole.setUserId(userId);
			backUserRoleDao.inserUserRole(backUserRole);
		}
	}

	@Override
	public void updateById(BackRole backRole) {
		backRoleDao.updateById(backRole);
	}

	@Override
	public List<BackTree> showUserListByRoleId(Integer id) {
		List<BackTree> treeList = null;
		List<Integer> roleChildIds = backRoleDao.showChildRoleListByRoleId(id);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("roleChildIds", roleChildIds);
		treeList = backRoleDao.showUserListByRoleId(params);
		return treeList;
	}

	@Override
	public List<BackTree> getTreeByRoleId(Integer roleId) {
		return backRoleDao.getTreeByRoleId(roleId);
	}

	@Override
	public void addRoleModule(HashMap<String, Object> params) {
		backRoleModuleDao.deleteByRoleId(Integer.valueOf(String.valueOf(params
				.get("id"))));
		String[] rightIds = (String[]) params.get("rightIds");
		if (null != rightIds && rightIds.length > 0) {
			backRoleModuleDao.insertModuleRole(params);
		}
	}
}
