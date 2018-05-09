package com.info.back.dao;

import com.info.web.pojo.CompanyIpAddressDto;

import java.util.List;


/**
 * @author Administrator
 * @Description:
 * @CreateTime 2018-05-07 下午 3:43
 **/
public interface ICompanyIpAddressDao {

    /**
     * 查询所有的公司-ip地址信息
     *
     * @return
     */
    List<CompanyIpAddressDto> listAllIps();


    List<CompanyIpAddressDto> getAllIps();

    /**
     * 公司公司id获取对应的ip信息
     *
     * @param companyId
     * @return
     */
    List<CompanyIpAddressDto> getIpsByCompanyId(String companyId);

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

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    CompanyIpAddressDto getById(Integer id);
}
