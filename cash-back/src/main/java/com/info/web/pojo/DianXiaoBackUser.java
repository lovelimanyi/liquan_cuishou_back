package com.info.web.pojo;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/11/18 0018上午 12:14
 */

public class DianXiaoBackUser {
    private String id;
    private String uuid; //电催员uuid,关联order表current_collection_user_id
    private String userName;//电催员姓名
    private String status; //'1启用，0禁用,3 删除
    private String companyId;//电催员公司id
    private String roleId;//角色id
    private Integer currentOrderCount;//当前该催收员手上订单量(今日新派订单)
    private Integer todayAssignedCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Integer getTodayAssignedCount() {
        return todayAssignedCount;
    }

    public void setTodayAssignedCount(Integer todayAssignedCount) {
        this.todayAssignedCount = todayAssignedCount;
    }
}
