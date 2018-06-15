package com.info.web.pojo;

/**
 * 通话记录
 * lmy
 */
public class ContactRecord {
    // 联系人姓名
    private String contactUserName;
    // 联系人电话
    private String contactUserPhone;
    // 联系人类型
    private String contactUserType;
    //通话次数
    private String contactTimes;

    public String getContactUserName() {
        return contactUserName;
    }

    public String getContactTimes() {
        return contactTimes;
    }

    public void setContactTimes(String contactTimes) {
        this.contactTimes = contactTimes;
    }

    public void setContactUserName(String contactUserName) {
        this.contactUserName = contactUserName;
    }

    public String getContactUserPhone() {
        return contactUserPhone;
    }

    public void setContactUserPhone(String contactUserPhone) {
        this.contactUserPhone = contactUserPhone;
    }

    public String getContactUserType() {
        return contactUserType;
    }

    public void setContactUserType(String contactUserType) {
        this.contactUserType = contactUserType;
    }


}
