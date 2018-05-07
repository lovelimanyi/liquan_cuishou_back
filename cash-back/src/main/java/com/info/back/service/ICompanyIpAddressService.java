package com.info.back.service;

import com.info.web.pojo.CompanyIpAddressDto;
import com.info.web.util.PageConfig;

import java.util.HashMap;
import java.util.List;


/**
 * @author Administrator
 * @Description:
 * @CreateTime 2018-05-07 下午 3:43
 **/
public interface ICompanyIpAddressService {

    /**
     * 查询所有的公司-ip地址信息
     *
     * @return
     */
    PageConfig<CompanyIpAddressDto> getPage(HashMap<String,Object> params);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int deleteIpById(int id);

    /**
     * 更新
     *
     * @param companyIpAddress
     * @return
     */
    int updateIpById(CompanyIpAddressDto companyIpAddress);


    /**
     * 保存
     *
     * @param companyIpAddress
     * @return
     */
    int insert(CompanyIpAddressDto companyIpAddress);
}
