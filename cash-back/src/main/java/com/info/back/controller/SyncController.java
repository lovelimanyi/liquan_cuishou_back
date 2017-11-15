package com.info.back.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author Administrator
 * @Description: 接收mq消息，供MQ调用
 * @CreateTime 2017-11-15 上午 9:44
 **/

@Controller
@RequestMapping("/sync")
public class SyncController {

    private static Logger logger = Logger.getLogger(SyncController.class);

    /**
     * 处理逾期订单数据
     * @param text
     */
    @RequestMapping("/loan-order-info")
    @ResponseBody
    public void dealwithOverdueOrder(String  text) {
        logger.info("接收到逾期订单数据：" + text);
        try {
            JSONObject json = JSONObject.parseObject(text);
            if (json != null) {
                Object loan = json.get("loan");
                Object repayment = json.get("repayment");
                Object repaymentDetail = json.get("repaymentDetail");
                if (loan != null) {
                    // todo
                } else {
                    logger.info("解析json,loan is null...");
                }

                if (repayment != null) {
                    // todo
                } else {
                    logger.info("解析json,repayment is null...");
                }

                if (repaymentDetail != null) {
                    // todo
                } else {
                    logger.info("解析json,repaymentDetail is null...");
                }
            }
        } catch (Exception e) {
            logger.error("json字符串解析错误！");
            e.printStackTrace();
        }
    }

    /**
     * 处理还款数据
     * @param text
     */
    @RequestMapping("/repayment")
    @ResponseBody
    public void dealwithRepayment(String text){
        logger.info("接收到还款数据：" + text);
        try{
            JSONObject json = JSONObject.parseObject(text);
            if(json != null){
                // todo
            }else {

            }
        }catch (Exception e){
            logger.error("json字符串解析错误！");
            e.printStackTrace();
        }
    }
}
