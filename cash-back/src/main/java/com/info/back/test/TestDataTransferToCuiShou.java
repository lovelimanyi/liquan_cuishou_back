package com.info.back.test;

import com.info.back.dao.*;
import com.info.back.service.*;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.CreditLoanPay;
import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.pojo.MmanUserLoan;
import com.info.web.synchronization.dao.IDataDao;
import com.info.web.synchronization.syncUtils;
import com.info.web.util.DateUtil;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

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
    private IDataDao dataDao;
    @Autowired
    private ILocalDataDao localDataDao;
    @Autowired
    private IMmanLoanCollectionOrderDao manLoanCollectionOrderDao;
    @Autowired
    private ICreditLoanPayService payService;
    @Autowired
    private IMmanUserLoanDao loanDao;
    @Autowired
    private IMmanLoanCollectionOrderService orderService;
    @Autowired
    private IMmanLoanCollectionStatusChangeLogDao statusChangeLogDao;
    @Autowired
    private IMmanUserInfoService infoService;


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
        String disTime = "2019-01-01 06:10:00";
        String backUserName = "韩志敏";
        String payIds = "50438241";
//
//
        cuiShouService.dataTransferToCuiShou(backUserName,payIds,disTime);



    }

    //派单
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

    //保存还款表
    @org.junit.Test
    public void test3(){
        String payId = "";
        String[] list = payId.split(",");
        for (int i=0;i<list.length;i++){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("ID", list[i]);//还款id
            HashMap<String,Object> repayment = this.dataDao.getAssetRepayment(map);
            CreditLoanPay pay = payService.get(payId);
            if (null == pay){
                saveCreditLoanPay(repayment);
            }
            System.out.println(list[i]);
        }
    }
    private void saveCreditLoanPay(HashMap<String,Object> repaymentMap){
        try{
            CreditLoanPay creditLoanPay = new CreditLoanPay();
            creditLoanPay.setId(String.valueOf(repaymentMap.get("id")));
            creditLoanPay.setLoanId(String.valueOf(repaymentMap.get("asset_order_id")));
            creditLoanPay.setCreateDate(DateUtil.getDateTimeFormat(String.valueOf(repaymentMap.get("created_at")), "yyyy-MM-dd HH:mm:ss"));
            creditLoanPay.setReceivableStartdate(DateUtil.getDateTimeFormat(String.valueOf(repaymentMap.get("credit_repayment_time")), "yyyy-MM-dd HH:mm:ss"));
//			System.out.println("=====================================================");
//			System.out.println(repaymentMap.get("credit_repayment_time"));
//			System.out.println(DateUtil.getDateTimeFormat(String.valueOf(repaymentMap.get("repayment_time")), "yyyy-MM-dd HH:mm:ss"));
//			System.out.println("保存还款表 ：" + String.valueOf(repaymentMap.get("credit_repayment_time")));
//			System.out.println("=====================================================");
            creditLoanPay.setReceivableDate(DateUtil.getDateTimeFormat(String.valueOf(repaymentMap.get("repayment_time")), "yyyy-MM-dd HH:mm:ss"));//应还时间
            creditLoanPay.setReceivableMoney(new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("repayment_amount")))/100.00));//应还金额
            creditLoanPay.setRealMoney(new BigDecimal(Integer.parseInt(String.valueOf(repaymentMap.get("repaymented_amount")))/100.00));//实收(本金+服务费)
            creditLoanPay.setStatus(syncUtils.getPayStatus(String.valueOf(repaymentMap.get("status")))); //还款状态
            creditLoanPay.setUpdateDate(new Date());
            creditLoanPay = syncUtils.operaRealPenlty(repaymentMap,creditLoanPay);
            this.localDataDao.saveCreditLoanPay(creditLoanPay);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
