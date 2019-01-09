package com.info.back.test;

import com.info.back.service.DianXiaoService;
import com.info.back.service.MmanLoanCollectionOrderService;
import com.info.back.utils.HttpUtils;
import com.info.back.utils.QRCodeUtil;
import com.info.back.vo.JxlResponse;
import com.info.config.PayContents;
import com.info.constant.Constant;
import com.info.web.synchronization.service.DataSyncService;
import com.info.web.synchronization.service.DianXiaoDataService;
import com.info.web.util.JedisDataClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/11/15 0015下午 10:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/resources/applicationContext.xml"})
public class Test {
    protected Logger logger = Logger.getLogger(Test.class);
//    @Autowired
//    DianXiaoDataService dianXiaoDataService;
@Autowired
MmanLoanCollectionOrderService orderService;
    @org.junit.Test
    public void test() throws Exception {

//        String payIds = "";
//        String[] list = payIds.split(",");
//        for (int i=0;i<list.length;i++){
//            String payId = list[i];
//            String key = "OVERDUE_"+payId;
//            JedisDataClient.set(key,payId);
//            System.out.println(list[i]);
//        }

//        QRCodeUtil.encode("www.baidu.com","D:/b","D:/abc",false,"random2");
//        orderService.orderUpgrade("545260388");






//        dianXiaoDataService.syncDianXiaoNoPay();
//        dianXiaoDataService.syncDianXiaoPay();
//        List<String> overdueList = null;
//        try {
//            overdueList = JedisDataClient.getAllValuesByPattern(Constant.TYPE_OVERDUE_+"*_"+PayContents.MERCHANT_NUMBER);
//            System.out.println(overdueList);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String ossUrl = "http://apigateway.c2580c64562774723a2e4a3d930cfe27b.cn-shanghai.alicontainer.com/api/storage/v2/report/jxl_base_report";
//        String phone = "15021500592";
//
//        String url = ossUrl + "?phone=" + phone;
//        JxlResponse jxlResponse = HttpUtils.get(url, null);
//        System.out.println(jxlResponse);


// 公信宝
//        String ossUrl = "http://apigateway.c2580c64562774723a2e4a3d930cfe27b.cn-shanghai.alicontainer.com/api/storage/v2/report/ecommerce_ana";
//        String phone = "18612567487";
//        String url = ossUrl + "?phone=" + phone;
//        String result = HttpUtils.doGet(url, null);
//        System.out.println(result);




    }
}
