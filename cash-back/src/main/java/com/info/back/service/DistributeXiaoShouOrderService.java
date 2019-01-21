package com.info.back.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.info.back.dao.IBackUserDao;
import com.info.back.utils.BackConstant;
import com.info.web.pojo.CollectionBackUser;
import com.info.web.pojo.XiaoShouOrder;
import com.info.web.synchronization.dao.IDataDao;
import com.info.web.util.JedisDataClient;
import com.info.web.util.ThreadPoolInstance;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：分配销售订单service
 * 创建人：yyf
 * 创建时间：2019/1/17 0017下午 03:49
 */
@Service
public class DistributeXiaoShouOrderService {
    @Autowired
    private IBackUserDao backUserDao;
    @Autowired
    IXiaoShouService xiaoShouService;
    @Autowired
    private IDataDao dataDao;

    public void handleXiaoShouOrder() throws Exception {


        String redisKey = BackConstant.XIAO_SHOU_BACK_USER;
        Integer length = JedisDataClient.llen(redisKey);
        if (length <= 0){
            //查询当前系统中销售坐席，并根据今日派单量排序，缓存至redis中
            List<CollectionBackUser> xiaoshouBackUserList = backUserDao.getXiaoShouBackUser();
            if (CollectionUtils.isNotEmpty(xiaoshouBackUserList)){
                for (CollectionBackUser backUser : xiaoshouBackUserList){
                    try {
                        JedisDataClient.rpush(redisKey , JSONObject.toJSONString(backUser));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        List<XiaoShouOrder> orderList = xiaoShouService.getXiaoShouOrder();
        if (CollectionUtils.isNotEmpty(orderList)){
            for (XiaoShouOrder order : orderList){
                String backUser = JedisDataClient.lpop(BackConstant.XIAO_SHOU_BACK_USER);
                JedisDataClient.rpush(BackConstant.XIAO_SHOU_BACK_USER,backUser);
                JSONObject user = JSON.parseObject(backUser);
                ThreadPoolInstance.getInstance().doExecute(new XiaoShouThread(order,user));
            }
            JedisDataClient.del(redisKey);
        }



    }


    class XiaoShouThread implements Runnable{

        private XiaoShouOrder order;
        private JSONObject user;

        public XiaoShouThread(XiaoShouOrder order,JSONObject user){
            this.order = order;
            this.user = user;

        }
        @Override
        public void run() {

            try {
                String currentCollectionUserId = user.get("uuid").toString();
                String backUserName =  user.get("userName").toString();
                String companyId = user.get("companyId").toString();
                Long batchId = order.getBatchId();
                String userId = order.getUserId();
                order.setBatchId(batchId);
                order.setCurrentCollectionUserId(currentCollectionUserId);
                order.setCurrentCollectionUserName(backUserName);
                order.setCompanyId(companyId);
                order.setCreateTime(new Date());
                order.setDispatcherTime(new Date());
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("USER_ID", userId);//还款id
                Map<String, Object> userInfo = dataDao.getUserInfo(map);
                if (null != userInfo && null != userInfo.get("user_name")){
                    order.setMobile(userInfo.get("user_name").toString());
                }else {
                    order.setMobile("");
                }

//                order.setMobile("18737191376");
                xiaoShouService.insertXiaoShouOrder(order);
                xiaoShouService.delXiaoShouInfo(order.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
