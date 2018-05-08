package com.info.back.service;

import com.info.back.result.JsonResult;
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
    PageConfig<CompanyIpAddressDto> getPage(HashMap<String, Object> params);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    JsonResult deleteIpById(int id);

    /**
     * 更新
     *
     * @param companyIpAddress
     * @return
     */
    JsonResult updateIpById(CompanyIpAddressDto companyIpAddress);


    /**
     * 保存
     *
     * @param params
     * @return
     */
    JsonResult insert(HashMap<String, Object> params);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    CompanyIpAddressDto getById(Integer id);

    /**
     * 查询所有的ip信息
     *
     * @return
     */
    List<CompanyIpAddressDto> listAll();
}
