package com.info.web.pojo;

import java.io.Serializable;
import java.util.Date;

public class RepayChannelConfig implements Serializable {
    private Integer id;

    private Integer repayChannel;

    private String repayChannelName;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRepayChannel() {
        return repayChannel;
    }

    public void setRepayChannel(Integer repayChannel) {
        this.repayChannel = repayChannel;
    }

    public String getRepayChannelName() {
        return repayChannelName;
    }

    public void setRepayChannelName(String repayChannelName) {
        this.repayChannelName = repayChannelName == null ? null : repayChannelName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}