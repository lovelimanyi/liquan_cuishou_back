package com.info.back.service;

import com.info.back.dao.ICompanyIpAddressDao;
import com.info.back.dao.IPaginationDao;
import com.info.constant.Constant;
import com.info.web.pojo.CompanyIpAddressDto;
import com.info.web.util.PageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 * @Description:
 * @CreateTime 2018-05-07 下午 4:13
 **/

@Service
public class CompanyIpAddressService implements ICompanyIpAddressService {

    @Autowired
    private ICompanyIpAddressDao companyIpAddressDao;
    @Autowired
    private IPaginationDao paginationDao;

    @Override
    public PageConfig<CompanyIpAddressDto> getPage(HashMap<String, Object> params) {
        return paginationDao.findPage("listAllIps", "listAllIpsCount", params, null);
    }

    @Override
    public int deleteIpById(int id) {
        return companyIpAddressDao.deleteIpById(id);
    }

    @Override
    public int updateIpById(CompanyIpAddressDto companyIpAddress) {
        return companyIpAddressDao.updateIpById(companyIpAddress);
    }

    @Override
    public int insert(CompanyIpAddressDto companyIpAddress) {
        return companyIpAddressDao.insert(companyIpAddress);
    }
}
