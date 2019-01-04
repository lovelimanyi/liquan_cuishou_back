package com.info.back.test;

import com.info.back.dao.IBackUserDao;
import com.info.back.service.DataTransferToCuiShouService;
import com.info.back.service.IMmanLoanCollectionCompanyService;
import com.info.back.service.IMmanLoanCollectionOrderService;
import com.info.back.service.IMmanLoanCollectionStatusChangeLogService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 类描述：将其他系统订单迁移至催收-即重新分配给指定催收员
 * 创建人：yyf
 * 创建时间：2018/12/29 0029上午 11:48
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/applicationContext.xml"})
public class TestDataTransferToCuiShou {
//    @Autowired
//    DataTransferToCuiShouService cuiShouService;

    @org.junit.Test
    public void test(){
//        String disTime = "2019-01-02 06:10:00";
//        String backUserName = "张磊";
//        String payIds = "50590523,50591192,50590477,50590201,50594588,50593043,50597270,50599093,50597732,50599880,50601599,50604105,50602338,50605215,50605454";
//
//
//        cuiShouService.dataTransferToCuiShou(backUserName,payIds,disTime);



    }

}
