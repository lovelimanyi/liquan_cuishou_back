package com.info.back.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @Description: 处理1号逾期升级（S1）过渡产生的错误流转日志
 * @CreateTime 2017-12-05 上午 10:35
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/applicationContext.xml"})
public class DealWithStatusChangeLog {
    @Test
    public void test(){
        HashMap<String,String> map = new HashMap<>();
        map.put("9981198","覃星");
        map.put("9755013","刘光祥");
        map.put("9055552","唐希");
        map.put("9102253","孔琳");
        map.put("7975098","戚义龙");
        for (Map.Entry<String,String> entry:map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }

}
