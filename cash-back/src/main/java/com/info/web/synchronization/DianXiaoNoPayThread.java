package com.info.web.synchronization;

import com.info.back.dao.ILocalDataDao;
import com.info.back.service.IDianXiaoService;
import com.info.back.utils.IdGen;
import com.info.constant.Constant;
import com.info.web.pojo.DianXiaoOrder;
import com.info.web.synchronization.dao.IDataDao;
import com.info.web.util.DateUtil;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/11/15 0015下午 06:52
 */

public class DianXiaoNoPayThread implements Runnable {
    private static Logger logger = Logger.getLogger(DianXiaoNoPayThread.class);

    private String loanId;
    private IDataDao dataDao;
    private ILocalDataDao localDataDao;
    private IDianXiaoService dianXiaoService;

    public DianXiaoNoPayThread(){

    }
    public DianXiaoNoPayThread(String loanId, IDataDao dataDao, ILocalDataDao localDataDao, IDianXiaoService dianXiaoService) {
        this.loanId = loanId;
        this.dataDao = dataDao;
        this.localDataDao = localDataDao;
        this.dianXiaoService = dianXiaoService;
    }

    @Override
    public void run() {
        logger.error("sync-DianXiaoNoPay" + loanId);
        System.out.println(Thread.currentThread().getName() + "============================" + loanId);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("loanId", loanId);//借款id
        int orderCount = dianXiaoService.getDianXiaoOrderByLoanId(loanId);
        if (orderCount > 0){
            //如果催收库已存在改定单，则不做处理并删除redis中的key
//            RedisUtil.delRedisKey(Constant.DX_NOPAY + loanId);
            logger.error("sync-DianXiaoNoPay-orderExist="+loanId);
        }

        //从业务端获得该借款id的需要同步的借款定案
        HashMap<String, Object> borrowOrder = this.dataDao.getDianXiaoOrder(map);
        logger.error("sync-DianXiaoNoPay-borrowOrder="+borrowOrder);
        if (borrowOrder != null) {
            //开始进行电销订单派单逻辑
            boolean dispatchResult = this.dianXiaoService.dispatchDianXiaoOrder(borrowOrder);
            if (dispatchResult){
                //派单完成后删除redis的key
//                RedisUtil.delRedisKey(Constant.DX_NOPAY + loanId);
                logger.error("this_order_is_dispatched_loanId="+loanId);

            }

        } else {
            //如果没有查到次借款id的borrowOrder，可能该订单在未同步到催收数据库时已经还款完成，或者该订单不存在于业务库。此时直接删除redis中的key
//            RedisUtil.delRedisKey(Constant.DX_NOPAY + loanId);
        }

    }



}
