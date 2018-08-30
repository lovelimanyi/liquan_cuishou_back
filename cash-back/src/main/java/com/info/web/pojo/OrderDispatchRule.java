package com.info.web.pojo;

/**
 * @author 重新分单规则信息对象
 * @Description:
 * @CreateTime 2018-08-29 下午 3:22
 **/
public class OrderDispatchRule {

    private Integer id;

    private String companyName;

    private String companyId;

    /**
     * 人均单数
     */
    private Integer averageOrder;

    private Integer status;

    private String groupLevel;

    public String getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(String groupLevel) {
        this.groupLevel = groupLevel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Integer getAverageOrder() {
        return averageOrder;
    }

    public void setAverageOrder(Integer averageOrder) {
        this.averageOrder = averageOrder;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
