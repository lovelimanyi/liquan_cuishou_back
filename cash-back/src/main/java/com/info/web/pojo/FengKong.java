package com.info.web.pojo;

import java.io.Serializable;
import java.util.Date;

public class FengKong implements Serializable {
    private Integer id;
    //风控标签名
    private String fkLabel;
    //可用状态  "0"可用      "1"禁用
    private String status;
    //创建日期
    private Date createtime;
    //修改日期
    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFkLabel() {
        return fkLabel;
    }

    public void setFkLabel(String fkLabel) {
        this.fkLabel = fkLabel;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
