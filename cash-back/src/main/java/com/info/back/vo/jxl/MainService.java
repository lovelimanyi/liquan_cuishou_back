package com.info.back.vo.jxl;
import java.util.List;
/**
 * 常用服务
 * @author yyf
 *
 * @version
 */
public class MainService {
    //月互动详情
    private List<ServiceDetails> service_details;
    //总互动次数
    private Integer total_service_cnt;
    //服务企业类型
    private String company_type;
    //企业名称
    private String company_name;

    public List<ServiceDetails> getService_details() {
        return service_details;
    }

    public void setService_details(List<ServiceDetails> serviceDetails) {
        service_details = serviceDetails;
    }

    public Integer getTotal_service_cnt() {
        return total_service_cnt;
    }

    public void setTotal_service_cnt(Integer totalServiceCnt) {
        total_service_cnt = totalServiceCnt;
    }

    public String getCompany_type() {
        return company_type;
    }

    public void setCompany_type(String companyType) {
        company_type = companyType;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String companyName) {
        company_name = companyName;
    }

}
