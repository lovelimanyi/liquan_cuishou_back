package com.info.back.controller;

import com.info.back.service.ICompanyIpAddressService;
import com.info.back.service.IMmanLoanCollectionCompanyService;
import com.info.web.pojo.CompanyIpAddressDto;
import com.info.web.pojo.MmanLoanCollectionCompany;
import com.info.web.util.PageConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author Administrator
 * @Description:
 * @CreateTime 2018-05-07 下午 4:16
 **/

@RequestMapping("/companyIpAddress")
@Controller
public class CompanyIpAddressController extends BaseController {

    private static Logger logger = Logger.getLogger(IndexController.class);

    @Autowired
    private ICompanyIpAddressService companyIpAddressService;

    @Autowired
    private IMmanLoanCollectionCompanyService mmanLoanCollectionCompanyService;


    @RequestMapping("/list")
    public String list(HttpServletRequest request, Model model) {
        try {
            HashMap<String, Object> params = this.getParametersO(request);
            PageConfig<CompanyIpAddressDto> page = companyIpAddressService.getPage(params);
            model.addAttribute("page", page);
            model.addAttribute("companyList", mmanLoanCollectionCompanyService.getList(new MmanLoanCollectionCompany()));
            model.addAttribute("params", params);
        } catch (Exception e) {
            logger.error("company-ip page error!");
            e.printStackTrace();
        }
        return "company/companyIpAddressPage";
    }

}
