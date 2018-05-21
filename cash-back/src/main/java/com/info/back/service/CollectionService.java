package com.info.back.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.BackUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.ICollectionDao;
import com.info.back.dao.IMmanLoanCollectionCompanyDao;
import com.info.back.dao.IPaginationDao;
import com.info.back.result.JsonResult;
import com.info.back.utils.BackConstant;
import com.info.constant.Constant;
import com.info.web.pojo.Collection;
import com.info.web.util.PageConfig;

import javax.servlet.http.HttpServletRequest;

@Service
public class CollectionService implements ICollectionService {
    private static Logger logger = LoggerFactory.getLogger(CollectionService.class);
    @Autowired
    private IPaginationDao paginationDao;
    @Autowired
    private ICollectionDao collectionDao;
    @Autowired
    private IMmanLoanCollectionCompanyDao mmanLoanCollectionCompanyDao;
    @Autowired
    private IBackUserService backUserService;

    @Override
    public PageConfig<Collection> findPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "Collection");
        PageConfig<Collection> pageConfig = new PageConfig<Collection>();
        pageConfig = paginationDao.findPage("findAll", "findAllCount", params, null);
        return pageConfig;
    }

    @Override
    public Collection findOneCollection(Integer id) {
        return collectionDao.findOneCollection(id);
    }

    @Override
    public JsonResult updateById(Collection collection) {
        JsonResult result = new JsonResult("-1", "修改催收员失败");
        try {
            Collection oldCollection = collectionDao.findOneCollection(collection.getId());
            int count = 0;
            if (oldCollection.getGroupLevel().equals(collection.getGroupLevel())) {
                count = collectionDao.updateById(collection);
            } else {
                int orderCount = collectionDao.findOrderCollection(oldCollection.getUuid());//统计催员手上未完成的订单
                if (orderCount <= 0) {
                    count = collectionDao.updateById(collection);
                } else {
                    result.setMsg("该催收员还有" + orderCount + "条订单未完成不能转组修改。等完成订单或转派给他人后再修改");
                }
            }
            if (count > 0) {
                result.setCode("0");
                result.setMsg("修改催收员成功");
            }
        } catch (Exception e) {
            logger.error("CollectionService updateById", e);
        }
        return result;
    }

    @Override
    public JsonResult insert(Collection collection) {
        JsonResult result = new JsonResult("-1", "添加催员失败");
        collection.setUserName(collection.getUserName().trim());  // 对催收员真实姓名去空格处理，防止添加催收员时无意中输入空格，查询时按催收员查询找不到对应信息
        Integer id = collectionDao.insert(collection);
        if (id > 0) {//赋予催收员角色
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("roleIds", BackConstant.COLLECTION_ROLE_ID.toString().split(","));//催收员角色ID
            params.put("id", collection.getId().toString());
            backUserService.addUserRole(params);
            result.setCode("0");
            result.setMsg("添加催收员成功");
        }
        return result;
    }

    @Override
    public JsonResult deleteCollection(Integer id) {
        JsonResult result = new JsonResult("-1", "删除催员失败");
        try {
            Collection collection = collectionDao.findOneCollection(id);
            if (collection != null) {
                int orderCount = collectionDao.findOrderCollection(collection.getUuid());//统计催员手上未完成的订单
                if (orderCount <= 0) {
                    // 标记用户为删除
                    orderCount = collectionDao.updateDeleteCoection(collection.getUuid());
                    if (orderCount > 0) {
                        List<String> UUIdlist = new ArrayList<String>();
                        UUIdlist.add(collection.getUuid());
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("UUIdlist", UUIdlist);
                        //标记订单为需要删除 统计过后删除该数据
                        mmanLoanCollectionCompanyDao.updateOrderStatus(map);
                    }
                    result.setCode("0");
                    result.setMsg("删除催收员成功");
                } else {
                    result.setMsg("该催收员还有" + orderCount + "条订单未完成不能删除。等完成订单或转派给他人后再删除");
                }
            } else {
                result.setMsg("该催收员不存在");
            }
        } catch (Exception e) {
            logger.error("CollectionService insert", e);
        }
        return result;
    }

    @Override
    public void deleteTagDelete() {
        logger.error("删除标记为删除的订单 CollectionService deleteTagDelete");
        try {
            int deleteCoun = collectionDao.findTagDelete();
            if (deleteCoun > 0) {
                deleteCoun = collectionDao.deleteTagDelete();
                logger.error("删除标记为删除的订单 总条数" + deleteCoun);
            } else {
                logger.error("CollectionService deleteTagDelete 暂时没有需要删除的订单");
            }
        } catch (Exception e) {
            logger.error("CollectionService deleteTagDelete", e);
        }
    }

    @Override
    public Collection getCollectionByUserName(String username) {
        return collectionDao.getCollectionByUserName(username);
    }

    @Override
    public Collection getCollectionByUserAccount(String userAccount) {
        return collectionDao.getCollectionByUserAccount(userAccount);
    }

    /**
     * 判断当前用户是有有查询验证码的权限
     *
     * @param request
     * @return
     */
    public boolean verifyCodeAccess(HttpServletRequest request) {
        BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
        String roleId = backUser.getRoleId();
        List<String> roleList = new ArrayList<>();
        roleList.add(BackConstant.SURPER_MANAGER_ROLE_ID.toString()); // 超级管理员
        roleList.add(BackConstant.SUPER_MANAGE_ROLE_ID.toString()); // 高级经理
        roleList.add(BackConstant.OUTSOURCE_MANAGER_ROLE_ID.toString()); // 委外经理
        if (roleList.contains(roleId)) {
            return true;
        }
        return true;
    }
}
