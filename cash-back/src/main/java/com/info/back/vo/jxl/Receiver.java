package com.info.back.vo.jxl;

import java.util.List;
/**
 * 收货人
 * @author yyf
 *
 * @version
 */
public class Receiver {
    //收货次数
    private Integer count;
    //收货金额
    private float amount;
    //收货人名称
    private String name;
    //收货人号码
    private List<String> phone_num_list;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhone_num_list() {
        return phone_num_list;
    }

    public void setPhone_num_list(List<String> phoneNumList) {
        phone_num_list = phoneNumList;
    }

}
