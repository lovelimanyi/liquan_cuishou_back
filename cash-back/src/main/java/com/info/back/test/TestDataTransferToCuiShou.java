package com.info.back.test;

import com.info.back.dao.IBackUserDao;
import com.info.back.service.*;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.CreditLoanPay;
import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.pojo.MmanUserLoan;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * 类描述：将其他系统订单迁移至催收-即重新分配给指定催收员
 * 创建人：yyf
 * 创建时间：2018/12/29 0029上午 11:48
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/applicationContext.xml"})
public class TestDataTransferToCuiShou {
    @Autowired
    DataTransferToCuiShouService cuiShouService;
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
    public void test(){
        String disTime = "2019-01-08 06:10:00";
        String backUserName = "夏栋民";
        String payIds = "50696037";
//
//
        cuiShouService.dataTransferToCuiShou2(backUserName,payIds,disTime);



    }


    @org.junit.Test
    public void test2(){
        String disTime = "2019-01-09 06:10:00";
        String userNames = "";
        String payIds = "";
        String[] userList = userNames.split(",");
        String[] payIdList = payIds.split(",");
        //平局单数向上取整
        int averageOrderCount = new BigDecimal(payIdList.length).divide(new BigDecimal(userList.length), 0, BigDecimal.ROUND_CEILING).intValue();


        int i = 0;//外层循环次数（averageOrderCount）即派单多少轮
        int j = 0;//已分配的订单数（最大为orderCount）
        while (i < averageOrderCount) {
            for (int t = 0; t < userList.length; t++) {
                BackUser user = backUserDao.getUserByName(userList[t]);
                if (j < payIdList.length) {
                    cuiShouService.dataTransferToCuiShou2(user.getUserName(),payIdList[j],disTime);
                    int a = j;
                    j++;
                    System.out.println("订单：" + payIdList[a] + " 处理完成！！当前第 " + a + " 条...当前催收员:"+user.getUserName());
                } else {//全部派单完成
                    System.out.println("订单处理完毕");
                }
            }
            i++;
            System.out.println("=================当前轮数："+ i);
        }

    }

}
