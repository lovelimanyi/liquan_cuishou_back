package com.info.web.pojo;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/15 0015.
 * <p>
 * /**
 * 渠道开关控制实体类
 */
public class ChannelSwitching {

    private int id;

    private String description;

    private String channelValue;

    private Date createDate;

    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChannelValue() {
        return channelValue;
    }

    public void setChannelValue(String channelValue) {
        this.channelValue = channelValue;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
