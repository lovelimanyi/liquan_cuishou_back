//
//package com.info.back.test;
//
//import com.alibaba.fastjson.JSONObject;
//import com.info.back.utils.WebClient;
//import com.info.back.vo.Base64;
//import com.info.back.vo.GzipUtil;
//import com.info.back.vo.jxl_360.Rong360Report;
//import com.info.back.vo.jxl_jdq.JdqReport;
//import com.info.back.vo.jxl_jlm.JlmReport;
//import com.info.config.PayContents;
//import com.info.web.util.DateUtil;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//
///**
// * Created by Administrator on 2017/6/14 0014.
// */
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"file:src/main/resources/applicationContext.xml"})
//public class TestRong360 {
//
//    @Test
//    public void test(){
//
//        System.out.println(DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
//        JSONObject json = new JSONObject();
//        //借了吗type=6
////        json.put("userid", "5183319");
//        //融360 type=4
////        json.put("userid", "5287531");
//        //借点钱 type=3
////        json.put("userid", "5301097");
//        //现金侠2
////        json.put("userid","1000013");
//
//        json.put("userid","4306914");
//
//
//        String data = json.toString();
//        try {
//            String result = WebClient.getInstance().postJsonData(PayContents.JXL_HBASE_SERVER_URL,  data,null);
//            System.out.println("result:"+result);
//            json = JSONObject.parseObject(result);
//            String type = json.getString("type");
//
//            String jsonString = json.getString("json");
//            if ("3".equals(type)){
//                jsonString = GzipUtil.uncompress(Base64.decode(jsonString),"UTF-8");
//            }
//            JSONObject jsonDetail = JSONObject.parseObject(jsonString);
//            if ("4".equals(type)){
//                Rong360Report rong360Report = JSONObject.toJavaObject(jsonDetail,Rong360Report.class);
//                System.out.println(rong360Report);
//            }else if ("6".equals(type)){
//                JlmReport jlmReport = JSONObject.toJavaObject(jsonDetail,JlmReport.class);
//                System.out.println(jlmReport);
//            }else if ("3".equals(type)){
//                JdqReport jdqReport = JSONObject.toJavaObject(jsonDetail,JdqReport.class);
//                System.out.println(jdqReport);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//}
//
//
