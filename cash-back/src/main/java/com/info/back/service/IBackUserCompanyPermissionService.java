package com.info.back.service;

import com.info.web.pojo.BackUser;
import com.info.web.pojo.BackUserCompanyPermissions;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7 0007.
 * 用户对应公司权限service层
 */
public interface IBackUserCompanyPermissionService {
    /**
     * 根据用户查询其权限内的公司
     * @param userId
     * @return
     */
    List<BackUserCompanyPermissions> getPermissionSCompaniesByUser(Integer userId);
}
