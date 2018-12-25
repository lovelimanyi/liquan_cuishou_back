package com.info.web.pojo;

/**
 * 类描述：催收员实体类-派单时使用
 * 创建人：yyf
 * 创建时间：2018/12/25 0025上午 10:42
 */

public class CollectionBackUser {

    private Integer id;
    private String uuid;
    private String userName;
    private String companyId;
    private String roleId;
    private Integer currentOrderCount;//当日派单个数
    private Integer userStatus;
    private String groupLevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Integer getCurrentOrderCount() {
        return currentOrderCount;
    }

    public void setCurrentOrderCount(Integer currentOrderCount) {
        this.currentOrderCount = currentOrderCount;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(String groupLevel) {
        this.groupLevel = groupLevel;
    }

    @Override
    public String toString() {
        return "CollectionBackUser{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", userName='" + userName + '\'' +
                ", companyId='" + companyId + '\'' +
                ", roleId='" + roleId + '\'' +
                ", currentOrderCount=" + currentOrderCount +
                ", userStatus=" + userStatus +
                ", groupLevel='" + groupLevel + '\'' +
                '}';
    }
}
