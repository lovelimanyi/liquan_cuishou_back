package com.info.back.test;

import com.info.back.dao.IBackUserDao;
import com.info.back.service.IMmanLoanCollectionCompanyService;
import com.info.back.service.IMmanLoanCollectionOrderService;
import com.info.back.service.IMmanLoanCollectionStatusChangeLogService;
import com.info.back.utils.BackConstant;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.MmanLoanCollectionCompany;
import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.pojo.MmanLoanCollectionStatusChangeLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author hxj
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/applicationContext.xml"})
public class DealWithM6OrderTest {

    @Autowired
    IBackUserDao backUserDao;

    @Autowired
    private IMmanLoanCollectionOrderService mmanLoanCollectionOrderService;
    @Autowired
    private IMmanLoanCollectionStatusChangeLogService mmanLoanCollectionStatusChangeLogService;
    @Autowired
    private IMmanLoanCollectionCompanyService collectionCompanyService;


    @Test
    public void dealM6Orders() {
        String username = "陈雨";
        String loadIds = "50340524";
        BackUser user = backUserDao.getUserByName(username);
        if (user == null) {
            System.out.println("该催收员不存在或该催收员已被禁用，请核实！");
            return;
        }
        if ("".equals(user.getUuid()) || user.getUuid() == null) {
            System.out.println("该催收员uuid非法，请核实！");
            return;
        }
        if (user.getCompanyId() == null || "".equals(user.getCompanyId())) {
            System.out.println("该催收员公司id非法，请核实！");
            return;
        }
        MmanLoanCollectionCompany company = collectionCompanyService.getCompanyById(user.getCompanyId());
        if (company == null) {
            System.out.println("该催收员公司id非法，请核实！" + user.getCompanyId());
            return;
        }

        int count = 0;
        for (String loanId : loadIds.split(",")) {
            MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderByLoanId(loanId);
            if (order == null) {
                System.out.println("该订单不存在，请核实，订单id: " + loanId);
                continue;
            }
            if (order.getStatus().equals(BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS)) {
                System.out.println("该订单已还款完成，订单id: " + loanId);
                continue;
            }

            updateOrder(user, order);

            saveLog(user, company, order);

            count++;
            System.out.println("订单：" + loanId + " 处理完成！！当前第 " + count + " 条...");
        }
        System.out.println("任务执行完成，共处理 " + count + " 条数据。");

    }


    /**
     * 新增一条流转日志
     *
     * @param user
     * @param company
     * @param order
     */
    private void saveLog(BackUser user, MmanLoanCollectionCompany company, MmanLoanCollectionOrder order) {
        MmanLoanCollectionStatusChangeLog log = new MmanLoanCollectionStatusChangeLog();
        log.setAfterStatus("1");
        log.setBeforeStatus("1");
        log.setCompanyId(user.getCompanyId());
        log.setCompanyTitle(company.getTitle());
        log.setCurrentCollectionOrderLevel(user.getGroupLevel());
        log.setCurrentCollectionUserLevel(user.getGroupLevel());
        log.setCurrentCollectionUserId(user.getUuid());
        log.setCurrentCollectionUserLevel(user.getGroupLevel());
        log.setType("1");
        log.setOperatorName("系统");
        log.setLoanCollectionOrderId(order.getOrderId());
        log.setRemark("系统转单,当前催收员：" + user.getUserName());

        mmanLoanCollectionStatusChangeLogService.insert(log);
    }

    /**
     * 更新订单信息
     *
     * @param user
     * @param
     * @param order
     */
    private void updateOrder(BackUser user, MmanLoanCollectionOrder order) {
        MmanLoanCollectionOrder collectionOrder = new MmanLoanCollectionOrder();
        collectionOrder.setId(order.getId());
        collectionOrder.setLastCollectionUserId(order.getCurrentCollectionUserId());
        collectionOrder.setCurrentCollectionUserId(user.getUuid());
        collectionOrder.setOutsideCompanyId(user.getCompanyId());
        collectionOrder.setDispatchTime(new Date());

        mmanLoanCollectionOrderService.updateRecord(collectionOrder);
    }


    /**
     * 分配指定订单至指定催收员（均分）
     */
    @Test
    public void dealwithOrders() {

//        String usernames = getUsernamesByCompany("上海坤锦企业管理有限公司");
        String usernames = "谢志辉";
        String loadIds = "12376369";
        String[] users = usernames.split(",");
        String[] ids = loadIds.split(",");
        if (StringUtils.isEmpty(usernames) || StringUtils.isEmpty(loadIds)) {
            return;
        }
        int max = new BigDecimal(ids.length).divide(new BigDecimal(users.length), 0, BigDecimal.ROUND_CEILING).intValue();


        int i = 0;//外层循环次数（ceilAvgCount）
        int j = 0;//已分配的订单数（最大为orderCount）

        while (i < max) {
            for (int t = 0; t < users.length; t++) {
                BackUser user = backUserDao.getUserByName(users[t]);
                if (user == null) {
                    System.out.println("催收员: " + users[t] + " 不存在，轻核实！！");
                    continue;
                }
                if ("".equals(user.getUuid()) || user.getUuid() == null) {
                    System.out.println("该催收员uuid非法，请核实！");
                    continue;
                }
                if (user.getCompanyId() == null || "".equals(user.getCompanyId())) {
                    System.out.println("该催收员公司id非法，请核实！");
                    continue;
                }
                MmanLoanCollectionCompany company = collectionCompanyService.getCompanyById(user.getCompanyId());
                if (j < ids.length) {
                    MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderByLoanId(ids[j]);
                    if (order == null) {
                        break;
                    }
                    if (order.getStatus().equals(BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS)) {
                        System.out.println("该订单已还款完成，订单id: " + order.getLoanId());
                        break;
                    }
                    updateOrder(user, order);
                    saveLog(user, company, order);
                    j++;
                    System.out.println("订单：" + ids[j] + " 催收员：" + user.getUserName() + " 处理完成！！当前第 " + j + " 条...");
                }
            }
        }
        System.out.println("任务执行完成，共处理 " + (j + 1) + " 条数据。");
    }

    private String getUsernamesByCompany(String companyName) {
        StringBuilder sb = new StringBuilder();
        List<BackUser> users = backUserDao.getUserByCompany(companyName);
        if (CollectionUtils.isEmpty(users)) {
            return "";
        }
        for (BackUser user : users) {
            sb.append(user.getUserName()).append(",");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }
}
