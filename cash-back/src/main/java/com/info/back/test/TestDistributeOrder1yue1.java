package com.info.back.test;

import com.info.back.dao.IBackUserDao;
import com.info.back.service.*;
import com.info.back.utils.BackConstant;
import com.info.web.pojo.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/12/29 0029下午 05:01
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/applicationContext.xml"})
public class TestDistributeOrder1yue1 {
    @Autowired
    IBackUserDao backUserDao;

    @Autowired
    private IMmanLoanCollectionOrderService mmanLoanCollectionOrderService;
    @Autowired
    private IMmanLoanCollectionStatusChangeLogService mmanLoanCollectionStatusChangeLogService;
    @Autowired
    private IMmanLoanCollectionCompanyService collectionCompanyService;
    @Autowired
    private IMmanUserLoanService mmanUserLoanService;
    @Autowired
    private ICreditLoanPayService creditLoanPayService;


    @org.junit.Test
    public void doDistributeOrder() {
//
        String userNames = "";
        String loadIds = "";
        String[] userList = userNames.split(",");
        String[] loanIdList = loadIds.split(",");
        //平局单数向上取整
        int averageOrderCount = new BigDecimal(loanIdList.length).divide(new BigDecimal(userList.length), 0, BigDecimal.ROUND_CEILING).intValue();


        int i = 0;//外层循环次数（averageOrderCount）即派单多少轮
        int j = 0;//已分配的订单数（最大为orderCount）
        while (i < averageOrderCount) {
            for (int t = 0; t < userList.length; t++) {
                BackUser user = backUserDao.getUserByName(userList[t]);
                if (j < loanIdList.length) {
                    MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderByLoanId(loanIdList[j]);
                    MmanUserLoan loan = mmanUserLoanService.get(loanIdList[j]);
                    CreditLoanPay pay = creditLoanPayService.get(order.getPayId());
                    updateOrder(user,order); //更新订单
                    updateLoan(loan);//更新借款表
                    updatePay(pay);//更新还款表
                    addChangeLog(user,order);//添加催收流转日志
                    j++;
                    System.out.println("订单：" + order.getLoanId() + " 处理完成！！当前第 " + j + " 条...当前催收员:"+user.getUserName());
                } else {//全部派单完成
                    System.out.println("订单处理完毕");
                }
            }
            i++;
            System.out.println("=================当前轮数："+ i);
        }

    }

    private void addChangeLog(BackUser user, MmanLoanCollectionOrder order) {
        mmanLoanCollectionStatusChangeLogService.deleteLogByOrderId(order.getOrderId());
        MmanLoanCollectionStatusChangeLog log = new MmanLoanCollectionStatusChangeLog();
        log.setLoanCollectionOrderId(order.getOrderId());
        log.setBeforeStatus("");
        log.setAfterStatus("0");
        log.setType("1"); // 1入催 2逾期等级转换 3转单 5催收成功
        log.setOperatorName("系统");
        log.setRemark("系统派单,当前催收员：" + user.getUserName());
        log.setCompanyId(user.getCompanyId());
        log.setCurrentCollectionUserId(user.getUuid());
        log.setCurrentCollectionUserLevel(user.getGroupLevel());
        log.setCurrentCollectionOrderLevel(user.getGroupLevel());

        mmanLoanCollectionStatusChangeLogService.insert(log);

    }

    private void updatePay(CreditLoanPay pay) {
        CreditLoanPay newPay = new CreditLoanPay();
        newPay.setId(pay.getId());
        newPay.setUpdateDate(new Date());
        creditLoanPayService.updateCreditLoanPay(newPay);
    }

    private void updateLoan(MmanUserLoan loan) {
        MmanUserLoan newLoan = new MmanUserLoan();
        newLoan.setId(loan.getId());
        newLoan.setCreateTime(new Date());
        newLoan.setUpdateTime(new Date());
        mmanUserLoanService.updateMmanUserLoan(newLoan);
    }

    private void updateOrder(BackUser user, MmanLoanCollectionOrder order) {
        MmanLoanCollectionOrder collectionOrder = new MmanLoanCollectionOrder();
        collectionOrder.setId(order.getId());
        collectionOrder.setCurrentOverdueLevel(user.getGroupLevel());
        collectionOrder.setLastCollectionUserId("");
//        collectionOrder.setLastCollectionUserId(order.getCurrentCollectionUserId());
        collectionOrder.setCurrentCollectionUserId(user.getUuid());
        collectionOrder.setOperatorName("系统");
        order.setRemark("系统派单");
        collectionOrder.setOutsideCompanyId(user.getCompanyId());
        collectionOrder.setDispatchTime(new Date());
        collectionOrder.setUpdateDate(new Date());
        collectionOrder.setCreateDate(new Date());

        mmanLoanCollectionOrderService.updateRecord(collectionOrder);
    }


}
