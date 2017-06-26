package com.info.back.service;

import com.info.back.dao.IBackUserCompanyPermissionsDao;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.BackUserCompanyPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7 0007.
 */
@Service
public class BackUserCompanyPermissionsService implements IBackUserCompanyPermissionService{
@Autowired
private IBackUserCompanyPermissionsDao backUserCompanyPermissionsDao;


    @Override
    public List<BackUserCompanyPermissions> getPermissionSCompaniesByUser(Integer userId) {
        return backUserCompanyPermissionsDao.findSelfCompanyPermissions(userId);
    }
}
