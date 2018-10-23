package com.info.back.utils;

import com.info.config.PayContents;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/10/23 0023下午 05:32
 */
public class MerchantNoUtils {

    public static String getMerchantName(){
        String merchantName = "";
        String merchantNo = PayContents.MERCHANT_NUMBER.toString();
        if ("cjxjx".equals(merchantNo)){
            merchantName = "现金侠催收系统";
        }else if ("jxx".equals(merchantNo)){
            merchantName = "金小侠催收系统";
        }else if ("jyb".equals(merchantNo)){
            merchantName = "急用帮催收系统";
        }
        return merchantName;
    }
}
