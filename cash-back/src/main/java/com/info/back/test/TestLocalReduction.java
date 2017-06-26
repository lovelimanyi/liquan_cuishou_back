package com.info.back.test;


import com.info.config.PayContents;
import com.info.web.util.HttpUtil;
import com.info.web.util.encrypt.AESUtil;
import com.info.web.util.encrypt.MD5coding;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2017/5/26 0026.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/config/applicationContext.xml"})
public class TestLocalReduction {
    private static Logger loger = Logger.getLogger(TestLocalReduction.class);

    @Test
    public void test() {
        String user_id="3264050";
        String pay_id="1402439";
        int money= 7650;
        String  uuid ="734aa7a95b39403ca0242adcf56e4e8d";
        String sign = MD5coding.MD5(AESUtil.encrypt(user_id + pay_id + money + uuid, PayContents.XJX_WITHHOLDING_NOTIFY_KEY));
        //2、发起请求
        String withholdPostUrl= PayContents.XJX_JIANMIAN_URL+"/"+ user_id +"/"+ pay_id +"/"+ money+"/"+ uuid +"/"+sign;
        loger.error("减免请求地址："+withholdPostUrl);
        String xjxWithholdingStr = HttpUtil.getHttpMess(withholdPostUrl, "", "POST", "UTF-8");
        JSONObject jos = new JSONObject().fromObject(xjxWithholdingStr);
        System.out.println(jos);
    }

}
