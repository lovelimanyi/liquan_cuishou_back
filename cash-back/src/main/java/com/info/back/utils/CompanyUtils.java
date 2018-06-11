package com.info.back.utils;

import com.info.back.service.IBackUserCompanyPermissionService;
import com.info.back.service.IMmanLoanCollectionCompanyService;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.BackUserCompanyPermissions;
import com.info.web.pojo.MmanLoanCollectionCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/6/7 0007下午 02:25
 */
@Component
public class CompanyUtils {

    @Autowired
    private IMmanLoanCollectionCompanyService companyService;

    @Autowired
    private IBackUserCompanyPermissionService companyPermissionService;

    private static CompanyUtils companyUtils;

    @PostConstruct
    public void init() {
        companyUtils = this;
        companyUtils.companyService = this.companyService;
        companyUtils.companyPermissionService = this.companyPermissionService;
    }

    public static List<MmanLoanCollectionCompany> getUserCompanyRelation(BackUser backUser) {

        List<MmanLoanCollectionCompany> companyList = new ArrayList<>();//用来存放当前登录用户可查看的公司

        List<MmanLoanCollectionCompany> companys = companyUtils.companyService.selectCompanyList();//所有公司list
        //根据登录用户权限内的公司
        if(BackConstant.SURPER_MANAGER_ROLE_ID.toString().equals(backUser.getRoleId())){ //2.系统管理员，可产看所有公司
            companyList = companys;
        }else if (BackConstant.COLLECTION_ROLE_ID.toString().equals(backUser.getRoleId())){ //1.如果登录用户是催收员，只能看自己的数据
            companyList.add(companyUtils.companyService.getCompanyById(backUser.getCompanyId()));
        }else { //3.其他权限根据登录用户和公司的关联关系查出，登录用户可查看的公司
            List<String> list = new ArrayList<>();
            List<BackUserCompanyPermissions> companyPermissionsList = companyUtils.companyPermissionService.getPermissionSCompaniesByUser(backUser.getId());
            for (BackUserCompanyPermissions company:companyPermissionsList) {
                list.add(company.getCompanyId());
            }
            for (MmanLoanCollectionCompany company:companys) {
                if(list.contains(company.getId())){
                    companyList.add(company);
                }
            }
        }
        return companyList;
    }
}
