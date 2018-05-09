package com.info.back.service;

import com.alibaba.fastjson.JSONReader;
import com.info.back.dao.IPaginationDao;
import com.info.back.dao.IUserAccountWhiteListDao;
import com.info.back.result.JsonResult;
import com.info.back.utils.BackConstant;
import com.info.back.vo.jxl_dk360.User;
import com.info.web.pojo.MmanLoanCollectionCompany;
import com.info.web.pojo.UserAccountWhiteList;
import com.info.web.util.PageConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 * @Description:
 * @CreateTime 2018-05-09 上午 11:31
 **/

@Service
public class UserAccountWhiteListService implements IUserAccountWhiteListService {

    @Autowired
    private IUserAccountWhiteListDao userAccountWhiteListDao;

    @Autowired
    private IPaginationDao paginationDao;

    @Autowired
    private IMmanLoanCollectionCompanyService mmanLoanCollectionCompanyService;

    @Override
    public PageConfig<UserAccountWhiteList> listAll(HashMap<String, Object> param) {
        return paginationDao.findPage("listAll", "listAllCount", param, null);
    }

    @Override
    public JsonResult deleteById(Integer id) {
        JsonResult result = new JsonResult("-1", "删除失败");
        int i = userAccountWhiteListDao.deleteById(id);
        if (i > 0) {
            result.setCode("0");
            result.setMsg("删除成功！");
            updateUserAccountWhiteList();
        }
        return result;
    }

    @Override
    public JsonResult save(HashMap<String, Object> param) {
        JsonResult result = new JsonResult("-1", "添加失败");
        UserAccountWhiteList whiteList = new UserAccountWhiteList();
        String userAccount = param.get("userAccount") == null ? "" : param.get("userAccount").toString();
        String userName = param.get("userName") == null ? "" : param.get("userName").toString();
        String companyId = param.get("companyId") == null ? "" : param.get("companyId").toString();
        if (StringUtils.isEmpty(userAccount)) {
            result.setMsg("账号不能为空！");
            return result;
        }
        if (StringUtils.isEmpty(companyId)) {
            result.setMsg("账号异常！");
            return result;
        }
        MmanLoanCollectionCompany company = mmanLoanCollectionCompanyService.getCompanyById(companyId);
        whiteList.setUserAccount(userAccount);
        whiteList.setUserName(userName);
        if (company != null) {
            whiteList.setCompanyName(company.getTitle());
        }
        whiteList.setCreateDate(new Date());
        int i = userAccountWhiteListDao.insert(whiteList);
        if (i > 0) {
            result.setCode("0");
            result.setMsg("添加成功！");
            // 更新用户账号白名单缓存
            updateUserAccountWhiteList();
        }
        return result;
    }

    /**
     * 刷新白名单缓存
     */
    @Override
    public void updateUserAccountWhiteList() {
        // 清空map
        BackConstant.userAccountWhiteListList.clear();
        List<UserAccountWhiteList> list = userAccountWhiteListDao.listAll(new HashMap<String, Object>());
        if (!CollectionUtils.isEmpty(list)) {
            for (UserAccountWhiteList account : list) {
                BackConstant.userAccountWhiteListList.add(account.getUserAccount());
            }
        }
    }

}
