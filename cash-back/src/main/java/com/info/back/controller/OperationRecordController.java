package com.info.back.controller;

import com.info.back.service.IOperationRecordService;
import com.info.back.utils.BackConstant;
import com.info.back.utils.ExcelUtil;
import com.info.back.utils.MaskCodeUtil;
import com.info.constant.Constant;
import com.info.web.pojo.*;
import com.info.web.util.CompareUtils;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 * @Description: OperationRecord控制层
 * @CreateTime 2017-10-30 上午 10:33
 **/

@Controller
@RequestMapping("/operationRecord")
public class OperationRecordController extends BaseController {

    @Autowired
    private static Logger logger = Logger.getLogger(OperationRecordController.class);

    @Autowired
    private IOperationRecordService operationRecordService;

    @RequestMapping("/list")
    public String getOperationRecordList(HttpServletRequest request, Model model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            PageConfig<OperationRecord> pageConfig = operationRecordService.getList(params);
            model.addAttribute("page", pageConfig);
            model.addAttribute("params", params);
        } catch (Exception e) {
            logger.info("操作记录列表显示出错" + e);
            e.printStackTrace();
        }
        return "operationRecord/list";
    }

    /**
     * 操作记录导出
     * @param response
     * @param request
     * @param
     */
    @RequestMapping("/toExcel")
    public void reportManage(HttpServletResponse response, HttpServletRequest request) {
        HashMap<String, Object> params = getParametersO(request);
        logger.info("操作记录导出开始......");
        try {
            BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
            if (!BackConstant.SURPER_MANAGER_ROLE_ID.toString().equals(backUser.getRoleId())) {
                logger.info("只有超级管理员拥有该权限！！！");
                return;
            }
            int size = 50000;
            int total = 0;
            params.put(Constant.PAGE_SIZE, size);
            int totalPageNum = operationRecordService.findAllCount(params);
            if (totalPageNum > 0) {
                if (totalPageNum % size > 0) {
                    total = totalPageNum / size + 1;
                } else {
                    total = totalPageNum / size;
                }
            }
            OutputStream os = response.getOutputStream();
            response.reset();// 清空输出流
            ExcelUtil.setFileDownloadHeader(request, response, "操作记录.xlsx");
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
            String[] titles = {"操作账号", "操作时间", "借款编号", "借款人姓名", "借款人电话", "催收时间(开始)", "催收时间(结束)",
                    "分单时间(开始)", "分单时间(结束)", "逾期天数(开始)", "逾期天数(结束)", "跟进等级", "催收公司", "催收组", "催收状态", "当前催收员", "操作来源"};
            for (int i = 1; i <= total; i++) {
                params.put(Constant.CURRENT_PAGE, i);
                PageConfig<OperationRecord> pm = operationRecordService.getList(params);
                List<OperationRecord> list = pm.getItems();
                List<Object[]> contents = new ArrayList<Object[]>();
                for (OperationRecord r : list) {
                    String[] conList = new String[titles.length];
                    conList[0] = r.getOperateUserAccount() == null ? "" : r.getOperateUserAccount();
                    conList[1] = r.getOperateTime() == null ? "" : DateUtil.getDateFormat(r.getOperateTime(), "yyyy-MM-dd HH:mm:ss");
                    conList[2] = r.getLoanId() == null ? "" : r.getLoanId();
                    conList[3] = r.getLoanUserName() == null ? "" : r.getLoanUserName();
                    conList[4] = r.getLoanUserPhone() == null ? "" : r.getLoanUserPhone();
                    conList[5] = r.getBeginCollectionTime() == null ? "" : r.getBeginCollectionTime();
                    conList[6] = r.getEndCollectionTime() == null ? "" : r.getEndCollectionTime();
                    conList[7] = r.getBeginDispatchTime() == null ? "" : r.getBeginDispatchTime();
                    conList[8] = r.getEndDispatchTime() == null ? "" : r.getEndDispatchTime();
                    conList[9] = r.getBeginOverDuedays() == null ? "" : r.getBeginOverDuedays().toString();
                    conList[10] = r.getEndOverDuedays() == null ? "" : r.getEndOverDuedays().toString();
                    conList[11] = r.getFollowUpGrad() == null ? "" : r.getFollowUpGrad();
                    conList[12] = r.getCollectionCompanyId() == null ? "" : r.getCollectionCompanyId();
                    conList[13] = r.getCollectionGroup() == null ? "" : r.getCollectionGroup();
                    conList[14] = r.getCollectionStatus() == null ? "" : r.getCollectionStatus();
                    conList[15] = r.getCurrentCollectionUserName() == null ? "" : r.getCurrentCollectionUserName();
                    conList[16] = r.getSource() == null ? "" : r.getSource().toString();
                    contents.add(conList);
                }
                ExcelUtil.buildExcel(workbook, "操作记录", titles, contents, i, total, os);
            }
        } catch (Exception e) {
            logger.error("操作记录导出excel失败", e);
        }
    }
}
