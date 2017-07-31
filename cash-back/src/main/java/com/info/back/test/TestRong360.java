package com.info.back.test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONStreamAware;
import com.info.back.utils.WebClient;
import com.info.web.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2017/6/14 0014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/resources/applicationContext.xml"})
public class TestRong360 {

    @Test
    public void test(){

        System.out.println(DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
//        JSONObject json = new JSONObject();
//        json.put("userid", "1");
//
//        String data = json.toString();
//        String result = WebClient.getInstance().postJsonData("http://192.168.6.66:8888/hbaseserver/webhbase/search",  data,null);
//        System.out.println("result:"+result);
//        json = JSONObject.parseObject(result);
//        String type = json.getString("type");
//        String url = null;
//        if ("1".equals(type)){
//           String josnString = json.getString("json");
//           JSONObject josnDetail = JSONObject.parseObject(josnString);
//           url = josnDetail.getString("downloadUrl");
//        }
//        System.out.println(url);
    }
}

