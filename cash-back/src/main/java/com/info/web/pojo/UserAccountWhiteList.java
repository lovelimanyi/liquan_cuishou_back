package com.info.web.pojo;

import java.util.Date;

/**
 * @author Administrator
 * @Description: 用户白名单
 * @CreateTime 2018-05-09 上午 11:15
 **/
public class UserAccountWhiteList {

    private int id;

    private String userAccount;

    private String userName;

    private Date createDate;

    private String companyName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
