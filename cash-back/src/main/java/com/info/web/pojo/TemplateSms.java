package com.info.web.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信模板
 */
public class TemplateSms implements Serializable {
    //
    private String id;
    // 名称
    private String name;
    // 短信内容
    private String contenttext;
    //短信类型  3为S1组，4为 S2组，5为 M组
    private String msgType;
    // 状态 1 可用 ，2 禁用
    private String status;
    //
    private Date createtime;
    //
    private Date updatetime;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setContenttext(String contenttext) {
        this.contenttext = contenttext;
    }

    public String getContenttext() {
        return this.contenttext;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

}