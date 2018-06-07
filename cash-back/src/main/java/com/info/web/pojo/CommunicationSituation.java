package com.info.web.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 * @Description: 沟通情况实体类
 * @CreateTime 2018-06-07 下午 5:10
 **/
public class CommunicationSituation implements Serializable{

    private Integer id;

    // 沟通情况
    private String communicationLabel;

    // 标签状态  1 可用  0 禁用
    private String status;

    private Date createTime;

    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommunicationLabel() {
        return communicationLabel;
    }

    public void setCommunicationLabel(String communicationLabel) {
        this.communicationLabel = communicationLabel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
