package com.info.back.task;

import com.info.back.service.IMmanLoanCollectionOrderService;
import com.info.back.service.IMmanUserLoanService;
import com.info.back.utils.BackConstant;
import com.info.constant.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Administrator
 * @Description: 订单逾期升级定时任务
 * @Params
 * @Return
 * @CreateTime 2018/03/07  上午 11:29
 */
@Component
public class TaskDealwithOrderUpgrade {
    protected Logger logger = Logger.getLogger(TaskDealwithOrderUpgrade.class);

    @Autowired
    private IMmanLoanCollectionOrderService orderService;

    @Autowired
    private IMmanUserLoanService loanService;
    /*
         逾期升级规则
        逾期1-10天：S1
        逾期11-30天：S2
        逾期31-60天：M1
        由于逾期升级订单时在0点执行，而更新逾期天数，滞纳金定时在3点执行。所以：
        在订单逾期天数为10天时，进行s1->s2逾期升级
        在订单逾期天数为30天时，进行s2->m1-m2逾期升级
    * */
    /**
     * @Description: 订单逾期升级定时任务
     * @CreateTime 2018/12/27
     */
    public void orderUpgrade() {

        //查出逾期天数是10天，30天，且未还款完成的订单。
        List<String> loanIds = loanService.getNeedUpgradeOrderLoanIds();
        ThreadPoolDealwithOrderUpgrade pool = ThreadPoolDealwithOrderUpgrade.getInstance();
        pool.setDaemon(true);
        for (String loanId : loanIds) {
            try {
                if (StringUtils.isEmpty(loanId)) {
                    continue;
                }
                DealwithOrderUpgradeThread thread = new DealwithOrderUpgradeThread(loanId, orderService);
                pool.execute(thread);
            } catch (Exception e) {
                logger.error("处理逾期订单升级出错，借款id: " + loanId);
                e.printStackTrace();
                continue;
            }
        }
    }






//    public void orderUpgrade2() {
//        Map<String, Object> map = new HashMap<>();
//        //处理小额订单
//        map.put("orderStatus", BackConstant.XJX_LOAN_STATUS_RETURN_SUCCESS);
//        map.put("borrowingType", Constant.SMALL);
//        List<String> loanIds = loanService.getOverdueOrderIds(map);
//        ThreadPoolDealwithOrderUpgrade pool = ThreadPoolDealwithOrderUpgrade.getInstance();
//        pool.setDaemon(true);
//        for (String loanId : loanIds) {
//            try {
//                if (StringUtils.isEmpty(loanId)) {
//                    continue;
//                }
//                DealwithOrderUpgradeThread thread = new DealwithOrderUpgradeThread(loanId, orderService);
//                pool.execute(thread);
//            } catch (Exception e) {
//                logger.error("处理逾期订单升级出错，借款id: " + loanId);
//                e.printStackTrace();
//                continue;
//            }
//        }
//
//        //处理大额订单
//        map.put("borrowingType", Constant.BIG);
//        List<String> bigOrderloanIds = loanService.getOverdueOrderIds(map);
//        for (String loanId : bigOrderloanIds) {
//            try {
//                if (StringUtils.isEmpty(loanId)) {
//                    continue;
//                }
////                DealwithBigOrderUpgradeThread thread = new DealwithBigOrderUpgradeThread(loanId, orderService);
////                pool.execute(thread);
//                orderService.dealwithBigOrderUpgrade(loanId);
//            } catch (Exception e) {
//                logger.error("处理逾期订单升级出错，借款id: " + loanId);
//                e.printStackTrace();
//                continue;
//            }
//        }
//    }
}
