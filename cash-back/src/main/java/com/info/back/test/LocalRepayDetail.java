package com.info.back.test;

import com.octo.captcha.engine.sound.utils.SoundToFile;
import org.apache.poi.util.SystemOutLogger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22 0022.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/config/applicationContext.xml"})
public class LocalRepayDetail {
    @Test
    public void test() {
        String payId = "2376568, 2387458, 2401424, 2057024, 2406354, 2371272, 2102403, 2357538, 2405413";
        String[] list = payId.split(",");
        for (int i=0;i<list.length;i++){
            System.out.println(list[i]);
        }

    }


}
