package com.info.back.controller;

import com.info.back.service.IStatisticsService;
import com.info.back.utils.BackConstant;
import com.info.back.utils.CompanyUtils;
import com.info.back.utils.DateKitUtils;
import com.info.constant.Constant;
import com.info.web.pojo.*;
import com.info.web.util.PageConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：新增的统计，包含：派单跟踪统计-机构，派单统计-个人 ,催回率统计
 * 创建人：yyf
 * 创建时间：2018/6/6 0006下午 03:42
 */
@Controller
@RequestMapping("/statisticsNew")
public class StatisticsController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    IStatisticsService statisticsService;

    /**
     * 时间段累计统计(页面)
     */
    @RequestMapping("/trackTotalStatistics")
    public String trackTotalStatistics(@RequestParam(value = "Flag") String Flag, HttpServletRequest request, Model model){
        String pageUrl = "";
        HashMap<String, Object> params = getParametersO(request);
        BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);

        PageConfig<TrackStatistics> pageConfig = new PageConfig<>();

        //处理选择的派单日期
        DateKitUtils.handleSelectDate(params);

        //获取该登录人员可以查看的公司
        List<MmanLoanCollectionCompany> companyList = CompanyUtils.getUserCompanyRelation(backUser);
        if ("person".equals(Flag)){
            pageUrl = "statisticsNew/trackTotalPersonStatistics";
            params.put("companys",companyList);
            if (BackConstant.COLLECTION_ROLE_ID.toString().equals(backUser.getRoleId())){
                params.put("uuid", backUser.getUuid());
            }
            pageConfig = statisticsService.findPage(params);
        }else if ("company".equals(Flag)){
            pageUrl = "statisticsNew/trackTotalCompanyStatistics";
            params.put("companys",companyList);
            pageConfig = statisticsService.findCompanyPage(params);
        }
        params.put("roleId",backUser.getRoleId());
        model.addAttribute("companys",companyList);
        model.addAttribute("groupLevelMap", BackConstant.groupNameMap);
        model.addAttribute("borrowingTypeMap", BackConstant.borrowingTypeMap);
        model.addAttribute("userTypeMap", BackConstant.userTypeMap);
        model.addAttribute("dictMap",BackConstant.groupNameMap);
        model.addAttribute("list",pageConfig.getItems());
        model.addAttribute("pm", pageConfig);
        model.addAttribute("params", params);// 用于搜索框保留值
        return pageUrl;
    }
    /**
     * 时间段累计统计--执行统计
     */
    @RequestMapping("/doTrackStatistics")
    public void doTrackStatistics(){
        statisticsService.doTrackStatistics();
    }

    /**
     * 催回率统计（页面）
     */
    @RequestMapping("/recoveryRateStatistics")
    public String recoveryStatistics(HttpServletRequest request, Model model){
        String pageUrl = "statisticsNew/recoveryRateStatistics";
        HashMap<String, Object> params = getParametersO(request);
        //如果借款类型为空，则默认为 小额的
        if (params.get("borrowingType") == null){
            params.put("borrowingType","2");
        }
        PageConfig<RecoveryRate> pageConfig = statisticsService.findRecoveryPage(params);
        model.addAttribute("borrowingTypeMap", BackConstant.borrowingTypeMap);
        model.addAttribute("list",pageConfig.getItems());
        model.addAttribute("pm", pageConfig);
        model.addAttribute("params", params);// 用于搜索框保留值
        return pageUrl;
    }


    /**
     * 催回率统计--执行统计
     */
    @RequestMapping("/doRecoveryStatistics")
    public void doRecoveryStatistics(){
        statisticsService.doRecoveryStatistics();
    }

    /**
     * 当日催回统计(页面)
     */
    @RequestMapping("/todayStatistics")
    public String todayStatistics(@RequestParam(value = "Flag") String Flag,HttpServletRequest request, Model model){
        String pageUrl = "";
// TODO 当日催回率统计
        HashMap<String, Object> params = getParametersO(request);
        BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);

        PageConfig<TodayRecovery> pageConfig = new PageConfig<>();

        //处理选择的派单日期
        DateKitUtils.handleSelectDateToday(params);

        //获取该登录人员可以查看的公司
        List<MmanLoanCollectionCompany> companyList = CompanyUtils.getUserCompanyRelation(backUser);

        if ("person".equals(Flag)){
            pageUrl = "statisticsNew/todayPersonStatistics";
            params.put("companys",companyList);
            if (BackConstant.COLLECTION_ROLE_ID.toString().equals(backUser.getRoleId())){
                params.put("uuid", backUser.getUuid());
            }
            pageConfig = statisticsService.findTodayPersonPage(params);
        }else if ("company".equals(Flag)){
            pageUrl = "statisticsNew/todayCompanyStatistics";
            params.put("companys",companyList);
            pageConfig = statisticsService.findTodayCompanyPage(params);
        }
        model.addAttribute("roleId",backUser.getRoleId());
        model.addAttribute("companys",companyList);
        model.addAttribute("groupLevelMap", BackConstant.groupNameMap);
        model.addAttribute("borrowingTypeMap", BackConstant.borrowingTypeMap);
        model.addAttribute("userTypeMap", BackConstant.userTypeMap);
        model.addAttribute("dictMap",BackConstant.groupNameMap);
        model.addAttribute("list",pageConfig.getItems());
        model.addAttribute("pm", pageConfig);
        model.addAttribute("params", params);// 用于搜索框保留值
        return pageUrl;

    }

}
