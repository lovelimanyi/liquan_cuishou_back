package com.info.back.utils;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

import org.apache.commons.lang3.StringUtils;

/**
 * 处理身份证、手机号等以掩码方式显示
 */
public class MaskCodeUtil {
    public static String getMaskCode (String code) {
        String maskCode = null;
        if (StringUtils.isNotEmpty(code) && code.length() > 10) {
            int length = code.length();
            if (length == 11) {
                // 手机号码（4-9位以掩码方式显示）
                maskCode = code.substring(0, 3) + "* * * *" + code.substring(9, code.length());
            } else if(length < 16){
                throw new RuntimeException("身份证号码或手机号不正确,请核实：" + code);
            }else {
                // 身份证号码（7-15位以掩码方式显示）
                maskCode =  code.substring(0, 6) + "* * * *" + code.substring(15, code.length());
            }
        }
        return maskCode;
    }
}
