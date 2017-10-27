package com.info.back.test;

import com.alibaba.fastjson.JSONObject;
import com.info.back.utils.HttpUtils;
import com.info.back.vo.JxlResponse;
import com.info.back.vo.jxl.UserReport;
import com.info.back.vo.jxl2.JxlUserReport;
import com.info.back.vo.jxl_360.Rong360Report;
import com.info.back.vo.jxl_jdq.JdqReport;
import com.info.back.vo.jxl_jlm.JlmReport;
import com.info.config.PayContents;
import com.info.constant.Constant;
import com.info.web.util.DateUtil;
import com.liquan.oss.OSSUpload;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URL;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2017/8/31 0031上午 10:30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/resources/applicationContext.xml"})
public class TessOss {

    @Test
    public void test() {

        System.out.println(DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
        String phone = "13351025564"; //聚信立
//        String phone = "13351025562";//借点钱
//        String phone = "13018622852";//融360
//        String phone = "13022100592";//借了吗
//        String phone = "13025597181";//分期管家

        String url = null;
        try {
            JxlResponse jxlResponse = HttpUtils.get(url,null);
            if (jxlResponse != null){
                String jxlType = jxlResponse.getJxlType();
                String result = jxlResponse.getJxlData();
                JSONObject jsonDetail = JSONObject.parseObject(result);
                if (jxlType.equals(Constant.JLM_DEATIL) || jxlType.equals(Constant.FQGJ_DETAIL)){
                    JlmReport jlmReport = JSONObject.toJavaObject(jsonDetail,JlmReport.class);
                    System.out.println(jlmReport);
                }else if (jxlType.equals(Constant.R360_DETAIL)){
                    Rong360Report rong360Report = JSONObject.toJavaObject(jsonDetail,Rong360Report.class);
                    System.out.println(rong360Report);
                }else if(jxlType.equals(Constant.JXL_DETAIL)){
                    handleCashmanJxl(result);
                }else if(jxlType.equals(Constant.JDQ_DETAIL)){
                    JdqReport jdqReport = JSONObject.toJavaObject(jsonDetail,JdqReport.class);
                    System.out.println(jdqReport);
                }
                System.out.println(jxlType);
                System.out.println(jsonDetail);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 现金侠聚信立报告的两种解析
     */
    private void handleCashmanJxl(String jxlDetail) {
        System.out.println(jxlDetail);
        if (jxlDetail.startsWith("{\"report\"")) { //原始现金侠第二种聚信立报告
            JxlUserReport jxl2 = JSONObject.parseObject(jxlDetail, JxlUserReport.class);
            System.out.println(jxl2);
        } else { //原始现金侠第一种聚信立报告
            UserReport jxl1 = JSONObject.parseObject(jxlDetail, UserReport.class);
            System.out.println(jxl1);

        }
    }
    /**
     * 借款用户身份证照片获取
     */
    @Test
    public void testIdAddress() {
        OSSUpload ossUpload = new OSSUpload();
        URL headImageUrl = ossUpload.sampleGetFileUrl("xjx-files", "files/2017-08-25/431a2be478c041f1b4a03b97e2d8fcfb.png", 1000l * 3600l);
        System.out.println(headImageUrl + "");
    }
}
