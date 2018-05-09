package com.info.web.pojo;

/**
 * @author Administrator
 * @Description: 公司与ip地址对应关系DTO对象
 * @CreateTime 2018-05-07 下午 3:38
 **/
public class CompanyIpAddressDto {

    private Integer id;

    // 公司id
    private String companyId;

    // 公司名称
    private String companyName;

    // ip地址
    private String ipAddress;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
