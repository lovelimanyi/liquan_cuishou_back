package com.info.back.service;

import com.info.back.dao.ICompanyIpAddressDao;
import com.info.back.dao.IPaginationDao;
import com.info.back.result.JsonResult;
import com.info.web.pojo.CompanyIpAddressDto;
import com.info.web.util.PageConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static final Pattern ipAddressPattern = Pattern.compile("((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))");

    @Override
    public PageConfig<CompanyIpAddressDto> getPage(HashMap<String, Object> params) {
        return paginationDao.findPage("listAllIps", "listAllIpsCount", params, null);
    }

    @Override
    public JsonResult deleteIpById(int id) {
        JsonResult result = new JsonResult("-1", "删除公司ip失败");
        try {
            int i = companyIpAddressDao.deleteIpById(id);
            if (i > 0) {
                result.setCode("0");
                result.setMsg("删除成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public JsonResult updateIpById(CompanyIpAddressDto companyIpAddress) {
        JsonResult result = new JsonResult("-1", "更新公司ip白名单失败");
        try {
            String companyId = companyIpAddress.getCompanyId();
            String ipAddress = companyIpAddress.getIpAddress();
            // 校验用户输入信息的合法性
            if (checkUserInput(companyId, ipAddress, result)) {
                return result;
            }
            int i = companyIpAddressDao.updateIpById(companyIpAddress);
            if (i > 0) {
                result.setCode("0");
                result.setMsg("更新成功！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 校验用户输入信息的合法性
     *
     * @param companyId
     * @param ipAddress
     * @param result
     * @return
     */
    private boolean checkUserInput(String companyId, String ipAddress, JsonResult result) {
        Matcher m = ipAddressPattern.matcher(ipAddress);
        if (!m.matches()) {
            result.setCode("-1");
            result.setMsg("ip地址不合法，请重新添加！");
            return true;
        }
        if (StringUtils.isEmpty(companyId)) {
            result.setCode("-1");
            result.setMsg("公司错误，请重新选择！");
            return true;
        }
        return false;
    }

    @Override
    public JsonResult insert(HashMap<String, Object> params) {
        JsonResult result = new JsonResult("-1", "添加公司ip白名单失败");
        try {
            CompanyIpAddressDto companyIpAddressDto = new CompanyIpAddressDto();
            String companyId = params.get("companyId") == null ? "" : params.get("companyId").toString();
            String ipAddress = params.get("ipAddress") == null ? "" : params.get("ipAddress").toString();
            // 校验用户输入信息的合法性
            if (checkUserInput(companyId, ipAddress, result)) {
                return result;
            }
            companyIpAddressDto.setCompanyId(companyId);
            companyIpAddressDto.setIpAddress(ipAddress);
            int i = companyIpAddressDao.insert(companyIpAddressDto);
            if (i > 0) {
                result.setCode("0");
                result.setMsg("添加成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CompanyIpAddressDto getById(Integer id) {
        return companyIpAddressDao.getById(id);
    }

    @Override
    public List<CompanyIpAddressDto> listAll() {
        return companyIpAddressDao.listAllIps();
    }

    @Override
    public List<CompanyIpAddressDto> getAllIps() {
        return companyIpAddressDao.getAllIps();
    }
}
