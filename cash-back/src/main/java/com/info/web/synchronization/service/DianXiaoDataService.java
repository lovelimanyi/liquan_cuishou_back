package com.info.web.synchronization.service;

import com.info.back.dao.ILocalDataDao;
import com.info.back.service.IDianXiaoService;
import com.info.config.PayContents;
import com.info.constant.Constant;
import com.info.web.synchronization.DianXiaoNoPayThread;
import com.info.web.synchronization.RedisUtil;
import com.info.web.synchronization.dao.IDataDao;
import com.info.web.util.JedisDataClient;
import com.info.web.util.ThreadPoolInstance;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/11/15 0015下午 06:23
 */
@Service
public class DianXiaoDataService{

    private static Logger loger = Logger.getLogger(DianXiaoDataService.class);

    @Autowired
    private IDataDao dataDao;
    @Autowired
    private ILocalDataDao localDataDao;
    @Autowired
    private IDianXiaoService dianXiaoService;
    /**
     * 同步还款截止日当天应还款的订单
     */
    public void syncDianXiaoNoPay(){
        //电销订单只同步到现金侠系统
        loger.error("syncDianXiaoNoPay-----------start");
        if ("cjxjx".equals(PayContents.MERCHANT_NUMBER)){
            List<String> valueList = new ArrayList<>();
            try {
                //获取所有电销未还款的redis的key对应的value
                valueList = JedisDataClient.getAllValuesByPattern("dx:unrepay:*");
            } catch (Exception e) {
                loger.error("getAllNoPayValuesByPattern-exception",e);
                e.printStackTrace();
                return;
            }
            //如果redis中存在未还款的value,则把订单同步到电销订单表
            if (CollectionUtils.isNotEmpty(valueList)){
                for (String loanId : valueList){
//                    System.out.println("nopay_pay_loanId="+loanId);
                    ThreadPoolInstance.getInstance().doExecute(new DianXiaoNoPayThread(loanId,dataDao,localDataDao,dianXiaoService));
                    try {
                        //可以根据实际情况做下发送速度控制
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                loger.error("don_not_get_noPay_value");
            }
        }

    }

    /**
     * 同步还款截止日当天已还款完成的订单
     */
    public void syncDianXiaoPay(){
        loger.error("syncDianXiaoPay-----------------start");
        if ("cjxjx".equals(PayContents.MERCHANT_NUMBER)){
            List<String> valueList = new ArrayList<>();//存放redis中所有已还款的value
            try {
                //获取所有电销已还款的redis的key对应的value
                valueList = JedisDataClient.getAllValuesByPattern("dx:repay:*");
            } catch (Exception e) {
                loger.error("getAllPayValuesByPattern-exception",e);
                e.printStackTrace();
                return;
            }
            //如果redis中存在已还款的value,则把更新催收库电销表中的订单状态->将orderStatus由未还款改为已还款
            if (CollectionUtils.isNotEmpty(valueList)){
                for (String loanId : valueList){
                    final String loanID = loanId;
                    ThreadPoolInstance.getInstance().doExecute(new Runnable() {
                        @Override
                        public void run() {
                            String loanId = loanID;
                            //检查催收库-电销表中是否有该订单id的未还款订单，如果有，更新为已还款，如果没有，删除redis中的key
                            int count = localDataDao.checkDianXiaoOrder(loanId);
                            if (count>0){
                                try {
                                    localDataDao.updateDianXiaoOrderStatus(loanId);
                                    RedisUtil.delRedisKey(Constant.DX_PAY+loanId);
                                }catch (Exception e){
                                    loger.error("update-exception"+loanId);
                                    loger.error("update-exception=",e);
                                }

                            }
                            RedisUtil.delRedisKey(Constant.DX_PAY+loanId);
//                            System.out.println(Thread.currentThread().getName()+"_"+loanId);
                        }
                    });
                    try {
                        //可以根据实际情况做下发速度控制
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }else {
                loger.error("don_not_get_pay_value");
            }
        }

    }


}
