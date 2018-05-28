package com.info.back.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.util.StringUtils;

/**
 * @author Administrator
 * @Description: 格式化json输出
 * @CreateTime 2018-05-28 上午 9:52
 **/
public class JsonFormatUtil {

    /**
     * 格式化输出json
     *
     * @param src 需要格式化的json字符串
     * @return
     */
    public static String formatJson(Object src) {
        if (src == null || StringUtils.isEmpty(src)) {
            return null;
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();//设置出漂亮的格式
        Gson gson = gsonBuilder.create();
        return gson.toJson(src);
    }
}
