package com.info.back.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.info.back.dao.IBackUserDao;
import com.info.back.utils.BackConstant;
import com.info.web.pojo.CollectionBackUser;
import com.info.web.util.JedisDataClient;
import com.info.web.util.ThreadPoolInstance;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2019/1/15 0015下午 03:20
 */

public class XiaoShou {
    @Autowired
    private IBackUserDao backUserDao;
    @Autowired
    private IXiaoShouOrderService xiaoShouOrderService;

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
        List<XiaoShouOrder> orderList = xiaoShouOrderService.getXiaoShouOrder();
        if (CollectionUtils.isNotEmpty(orderList)){
            for (XiaoShouOrder order : orderList){
                ThreadPoolInstance.getInstance().doExecute(new XiaoShouThread(order));
            }
        }
        JedisDataClient.del(redisKey);

    }


    class XiaoShouThread implements Runnable{

        private XiaoShouOrder order;

        public XiaoShouThread(XiaoShouOrder order){
            this.order = order;

        }
        @Override
        public void run() {
            JSONObject user = null;
            try {
                String backUser = JedisDataClient.lpop(BackConstant.XIAO_SHOU_BACK_USER);
                JedisDataClient.rpush(BackConstant.XIAO_SHOU_BACK_USER,backUser);
                user = JSON.parseObject(backUser);
                String currentCollectionUserId = user.get("uuid").toString();
                String backUserName =  user.get("userName").toString();
                String companyId = user.get("companyId").toString();
                order.setCurrentCollectionUserId(currentCollectionUserId);
                order.setCurrentCollectionUserName(backUserName);
                order.setCompanyId(companyId);
                order.setCreateTime(new Date());
                xiaoShouOrderService.insertXiaoShouOrder(order);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
