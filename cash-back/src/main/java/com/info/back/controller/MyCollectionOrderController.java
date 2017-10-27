package com.info.back.controller;

import com.alibaba.fastjson.JSONObject;
import com.info.back.dao.ISmsUserDao;
import com.info.back.dao.ITemplateSmsDao;
import com.info.back.result.JsonResult;
import com.info.back.service.*;
import com.info.back.utils.*;
import com.info.config.PayContents;
import com.info.constant.Constant;
import com.info.web.pojo.*;
import com.info.web.util.CompareUtils;
import com.info.web.util.DateUtil;
import com.info.web.util.JSONUtil;
import com.info.web.util.PageConfig;
import com.liquan.oss.OSSUpload;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

/**
 * 我的催收订单控制层
 *
 * @author Administrator
 */
@Controller
@RequestMapping("collectionOrder/")
public class MyCollectionOrderController extends BaseController {

    private static Logger logger = Logger.getLogger(MyCollectionOrderController.class);

    // 公司
    @Autowired
    private IMmanLoanCollectionCompanyService mmanLoanCollectionCompanyService;
    // 订单
    @Autowired
    private IMmanLoanCollectionOrderService mmanLoanCollectionOrderService;

    // 用户信息
    @Autowired
    private IMmanUserInfoService mmanUserInfoService;
    @Autowired
    private IMmanLoanCollectionStatusChangeLogService mmanLoanCollectionStatusChangeLogService;
    @Autowired
    private IMmanLoanCollectionRecordService mmanLoanCollectionRecordService;
    @Autowired
    private IBackUserService backUserService;
    @Autowired
    private ITemplateSmsDao templateSmsDao;
    @Autowired
    private ICreditLoanPayDetailService creditLoanPayDetailService;
    @Autowired
    private IMmanUserLoanService mmanUserLoanService;
    @Autowired
    private IAuditCenterService auditCenterService;
    @Autowired
    private ISysDictService sysDictService;
    @Autowired
    private ICreditLoanPayService creditLoanPayService;
    @Autowired
    private ISysUserBankCardService sysUserBankCardService;
    @Autowired
    private ISmsUserService smsUserService;
    @Autowired
    private IMmanUserRelaService mmanUserRelaService;
    @Autowired
    private ISmsUserDao iSmsUserDao;
    @Autowired
    private IMman_loan_collection_orderdeductionService collection_orderdeductionService;
    @Autowired
    private ICountCollectionAssessmentService countCollectionAssessmentService;

    @Autowired
    private ICountCollectionManageService countCollectionManageService;
    @Autowired
    private IFengKongService fengKongService;
    @Autowired
    private ICollectionWithholdingRecordService collectionWithholdingRecordService;

    /**
     * 我的订单初始化加载查询
     *
     * @param model
     * @return
     */
    @RequestMapping("getListCollectionOrder")
    public String getListCollectionOrder(HttpServletRequest request,
                                         HttpServletResponse response, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);

        BackUser backUser = (BackUser) request.getSession().getAttribute(
                Constant.BACK_USER);
        List<BackUserCompanyPermissions> CompanyPermissionsList = backUserService
                .findCompanyPermissions(backUser.getId());
        if (CompanyPermissionsList != null && CompanyPermissionsList.size() > 0) {// 指定公司的订单
            params.put("CompanyPermissionsList", CompanyPermissionsList);
        }
        if (backUser.getRoleId() != null
                && BackConstant.COLLECTION_ROLE_ID.toString().equals(
                backUser.getRoleId())) {// 如果是催收员只能看自己的订单
            params.put("roleUserId", backUser.getUuid());
            // 催收员查属于自己的组
            params.put("collectionGroup", backUser.getGroupLevel());
        } else {
            // 若组没有 ，则默认查询S1 组
            if (null == params.get("collectionGroup")
                    || StringUtils.isBlank(String.valueOf(params
                    .get("collectionGroup")))) {
                params.put("collectionGroup", "3");
            }

        }

        // 查询公司列表
        MmanLoanCollectionCompany mmanLoanCollectionCompany = new MmanLoanCollectionCompany();
        List<MmanLoanCollectionCompany> ListMmanLoanCollectionCompany = mmanLoanCollectionCompanyService
                .getList(mmanLoanCollectionCompany);
        // 分页的订单信息
        PageConfig<OrderBaseResult> page = new PageConfig<OrderBaseResult>();
        if (!BackConstant.COLLECTION_ROLE_ID.equals(backUser.getRoleId())) {
            page = mmanLoanCollectionOrderService.getPage(params);
        } else {
            params.put("roleUserId", backUser.getUuid());
            page = mmanLoanCollectionOrderService.getCollectionUserPage(params);
        }
        model.addAttribute("ListMmanLoanCollectionCompany",
                ListMmanLoanCollectionCompany);

        if (page != null && page.getItems().size() > 0) {
            for (OrderBaseResult order : page.getItems()) {
                String phoneNumber = "".equals(order.getPhoneNumber()) ? null : order.getPhoneNumber();
                String idNumber = "".equals(order.getIdCard()) ? null : order.getIdCard();
                if (BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS.equals(order.getCollectionStatus())) {
                    order.setPhoneNumber(MaskCodeUtil.getMaskCode(phoneNumber));
                    order.setIdCard(MaskCodeUtil.getMaskCode(idNumber));
                }
            }
        }

        model.addAttribute("page", page);
        model.addAttribute("params", params);
        model.addAttribute("userGropLeval", backUser.getRoleId());
        model.addAttribute("dictMap", BackConstant.groupNameMap);
        // 跟进等级
        List<SysDict> levellist = sysDictService.getStatus("xjx_stress_level");
        HashMap<String, String> levelMap = BackConstant.orderState(levellist);
        model.addAttribute("levellist", levellist);// 用于搜索框保留值
        model.addAttribute("levelMap", levelMap);
        return "mycollectionorder/collectionOrder";
    }

    /**
     * 我的催收订单导出
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping("exportCollectionOrder")
    public void exportCollectionOrder(HttpServletRequest request, HttpServletResponse response, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
            List<BackUserCompanyPermissions> CompanyPermissionsList = backUserService.findCompanyPermissions(backUser.getId());
            if (CompanyPermissionsList != null && CompanyPermissionsList.size() > 0) {// 指定公司的订单
                params.put("CompanyPermissionsList", CompanyPermissionsList);
            }
            if (backUser.getRoleId() != null && BackConstant.COLLECTION_ROLE_ID.toString().equals(backUser.getRoleId())) {// 如果是催收员只能看自己的订单
                params.put("roleUserId", backUser.getUuid());
                // 催收员查属于自己的组
                params.put("collectionGroup", backUser.getGroupLevel());
            } else {
                // 若组没有 ，则默认查询S1 组
                if (null == params.get("collectionGroup") || StringUtils.isBlank(String.valueOf(params.get("collectionGroup")))) {
                    params.put("collectionGroup", "3");
                }
            }
            int size = 50000;
            int total = 0;
            params.put(Constant.PAGE_SIZE, size);
            int totalPageNum = mmanLoanCollectionOrderService.findAllCount(params);
            if (totalPageNum > 0) {
                if (totalPageNum % size > 0) {
                    total = totalPageNum / size + 1;
                } else {
                    total = totalPageNum / size;
                }
            }

            OutputStream os = response.getOutputStream();
            response.reset();// 清空输出流
            ExcelUtil.setFileDownloadHeader(request, response, "我的催收订单.xlsx");
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
            String[] titles = {"借款编号", "借款人姓名", "借款手机号", "催收状态", "跟进等级", "借款金额", "滞纳金",
                    "减免滞纳金", "已还金额", "逾期天数", "催收组", "应还时间", "派单时间", "最新催收时间", "承诺还款时间", "最新还款时间", "派单人"};
            List<SysDict> dictlist = sysDictService.getStatus("collection_group");
            HashMap<String, String> dictMap = BackConstant.orderState(dictlist);
            List<SysDict> statulist = sysDictService.getStatus("xjx_collection_order_state");
            HashMap<String, String> StatuMap = BackConstant.orderState(statulist);
            // 跟进等级
            List<SysDict> levellist = sysDictService.getStatus("xjx_stress_level");
            HashMap<String, String> levelMap = BackConstant.orderState(levellist);
            for (int i = 1; i <= total; i++) {
                params.put(Constant.CURRENT_PAGE, i);
                PageConfig<OrderBaseResult> pm = mmanLoanCollectionOrderService.getPage(params);
                List<OrderBaseResult> list = pm.getItems();
//				System.out.println("list>>>>>>>>="+list.size());
                List<Object[]> contents = new ArrayList<Object[]>();
                for (OrderBaseResult r : list) {
                    String[] conList = new String[titles.length];
                    conList[0] = r.getLoanId();
                    conList[1] = r.getRealName();
                    conList[2] = r.getPhoneNumber();
                    conList[3] = StatuMap.get(r.getCollectionStatus());
                    conList[4] = levelMap.get(r.getTopImportant()); //dictMap.get(r.getCollectionGroup()+"");
                    conList[5] = r.getLoanMoney() == null ? "0" : r.getLoanMoney() + "";
                    conList[6] = r.getLoanPenlty() + "";
                    conList[7] = r.getReductionMoney() + "";
                    conList[8] = r.getReturnMoney() == null ? "0" : r.getReturnMoney() + "";  //StatuMap.get(r.getCollectionStatus());
                    conList[9] = r.getOverdueDays() + ""; //r.getLoanEndTime()==null?"":DateUtil.getDateFormat(r.getLoanEndTime(), "yyyy-MM-dd HH:mm:ss");
                    conList[10] = dictMap.get(r.getCollectionGroup() + "");
                    conList[11] = r.getLoanEndTime() == null ? "" : DateUtil.getDateFormat(r.getLoanEndTime(), "yyyy-MM-dd HH:mm:ss");
                    conList[12] = r.getDispatchTime() == null ? "" : DateUtil.getDateFormat(r.getDispatchTime(), "yyyy-MM-dd HH:mm:ss");
                    conList[13] = r.getLastCollectionTime() == null ? "" : DateUtil.getDateFormat(r.getLastCollectionTime(), "yyyy-MM-dd HH:mm:ss");
                    conList[14] = r.getPromiseRepaymentTime() == null ? "" : DateUtil.getDateFormat(r.getPromiseRepaymentTime(), "yyyy-MM-dd HH:mm:ss");
                    if (r.getReturnMoney() != null && CompareUtils.greaterThanZero(r.getReturnMoney())) {
                        conList[15] = r.getCurrentReturnDay() == null ? "" : DateUtil.getDateFormat(r.getCurrentReturnDay(), "yyyy-MM-dd HH:mm:ss");
                    } else {
                        conList[15] = "";
                    }
                    conList[16] = r.getDispatchName() == null ? "" : r.getDispatchName();
                    contents.add(conList);
                }
                ExcelUtil.buildExcel(workbook, "我的催收订单", titles, contents, i, total, os);
            }
        } catch (Exception e) {
            logger.error("我的催收订单导出excel失败", e);
        }
    }

    /**
     * 添加催记录和催收建议页面
     *
     * @param model
     * @return
     */
    @RequestMapping("toCollectionRecordAndAdvice")
    public String toAddCollectionRecord(HttpServletRequest request,
                                        HttpServletResponse response, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        String orderId = null;
        List<SysDict> statulist = null;
        try {
            if ("other".equals(params.get("type"))) {
//                logger.error("CollectionRecordAndAdvice=" + params.get("id").toString());
                String ids = params.get("id").toString();
                String[] list = ids.split(",");
                String userRelaId = null;
                if (list.length > 1) {
                    userRelaId = list[0];
                    orderId = list[1];
//                    logger.error("CollectionRecordAndAdvice-userRelaId=" + list[0]);
//                    logger.error("CollectionRecordAndAdvice-orderId=" + list[1]);
                } else {
                    logger.error("前台参数异常，list = " + list);
                }
                //根据userRelaId查询联系人
                MmanUserRela userRela = mmanUserRelaService.getUserRealByUserId(userRelaId);
                if (userRela != null) {
                    params.put("infoVlue", userRela.getInfoValue());
                    params.put("infoName", userRela.getInfoName());
                } else {
                    logger.error("userRela is null,userRelaId = " + userRelaId);
                }
            } else {
                //根据orderId查询订单
                orderId = params.get("id").toString();
                MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderById(orderId);
                if (order != null) {
                    params.put("infoName", order.getLoanUserName());
                    params.put("infoVlue", order.getLoanUserPhone());
                } else {
                    logger.error("添加催收记录和催收建议，该订单未null ,借款id: " + orderId + " 请核实!");
                }
            }
            OrderBaseResult baseOrder = mmanLoanCollectionOrderService.getBaseOrderById(orderId);
            if (baseOrder != null) {
                params.put("loanId", baseOrder.getLoanId());
                params.put("loanUserName", baseOrder.getRealName());
                params.put("loanMoney", baseOrder.getLoanMoney());
                params.put("loanPenlty", baseOrder.getLoanPenlty());
                // 逾期8天以内订单催收建议不允许拒绝
                if (baseOrder.getOverdueDays() > 8) {
                    statulist = sysDictService.getStatus("xjx_collection_advise");
                } else {
                    statulist = sysDictService.getOtherStatus(params);
                }
            } else {
                logger.error("baseOrder is null, orderId : " + orderId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        params.put("id", orderId);
        List<SysDict> dictlist = sysDictService.getStatus("xjx_stress_level ");
        model.addAttribute("dictlist", dictlist);// 用于搜索框保留值
        List<FengKong> fengKongList = fengKongService.getFengKongList();
        model.addAttribute("statulist", statulist);
        model.addAttribute("fengKongList", fengKongList);
        model.addAttribute("params", params);// 用于搜索框保留值
        return "mycollectionorder/collectionRecordAndAdvice";
    }

    /**
     * 添加催收记录和催收建议
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("addRecordAndAdvice")
    public String addcuishou(HttpServletRequest request,
                             HttpServletResponse response, Model model) {
        String orderId = null;
        JsonResult result = new JsonResult("-1", "添加催收记录和催收建议失败");
        Map<String, String> params = this.getParameters(request);
        BackUser backUser = this.loginAdminUser(request);
        MmanLoanCollectionOrder mmanLoanCollectionOrderOri = mmanLoanCollectionOrderService
                .getOrderById(params.get("id").toString());
        String recordId = IdGen.uuid();
        params.put("recordId", recordId);
//		System.out.println("订单状态=【===========================】"+mmanLoanCollectionOrderOri.getStatus());
//		if(!"4".equals(mmanLoanCollectionOrderOri.getStatus())){
        try {
            String status = params.get("status");
            String content = params.get("collectionRemark");
            if ("2".equals(status) && StringLengthUtil.getLength(content) < 15) {
                result.setCode("-1");
                result.setMsg("添加催收记录和催收建议失败:催收建议拒绝，请填写不少于15字催收建议描述！");
            } else {
                result = mmanLoanCollectionRecordService.saveCollection(params,
                        backUser);
                if (result.isSuccessed()
                        && StringUtils.isNotBlank(params.get("stressLevel"))) {
                    HashMap<String, Object> topMap = new HashMap<String, Object>();
                    topMap.put("id", params.get("id") + "");
                    topMap.put("topLevel", params.get("stressLevel"));
                    mmanLoanCollectionOrderService.saveTopOrder(topMap);
                }
                params.put("backUserId", backUser.getId().toString());
                params.put("userName", backUser.getUserName());
                params.put("collectionRecordId", recordId);
                result = fengKongService.saveCollectionAdvice(params);
            }
        } catch (Exception e) {
            logger.error(" error", e);
        }
//		}else {
//			result.setMsg("催收成功订单不能 添加催收记录");
//			result.setCode("-1");
//		}
        SpringUtils.renderDwzResult(response, "0".equals(result.getCode()),
                result.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
                "");
        model.addAttribute("params", params);
        return null;
    }

    /**
     * 催收流转日志
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("getlistlog")
    public String getCollectionStatusChangeLog(HttpServletRequest request,
                                               HttpServletResponse response, Model model) {
        Map<String, String> params = this.getParameters(request);
        List<MmanLoanCollectionStatusChangeLog> list = null;
        try {
            if (StringUtils.isNotBlank(params.get("id") + "")) {
                MmanLoanCollectionOrder mmanLoanCollectionOrderOri = mmanLoanCollectionOrderService
                        .getOrderById(params.get("id").toString());
                if (mmanLoanCollectionOrderOri != null) {
                    list = mmanLoanCollectionStatusChangeLogService
                            .findListLog(mmanLoanCollectionOrderOri.getOrderId());
                } else {
                    logger.error("催收流转日志，该订单异常，请核实：" + params.get("id"));
                }
                List<SysDict> statulist = sysDictService
                        .getStatus("xjx_collection_order_state");
                // 查询所有的催收状态
                HashMap<String, String> StatuMap = BackConstant
                        .orderState(statulist);
                model.addAttribute("StatuMap", StatuMap);
                model.addAttribute("group", BackConstant.groupNameMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("params", params);
        model.addAttribute("listlog", list);
        return "mycollectionorder/listlog";
    }

    /**
     * 跳转到订单详情页
     *
     * @param request
     * @param response
     * @param orderId
     * @param model
     * @return
     */
    @RequestMapping("toxianqin")
    public String toxianqin(HttpServletRequest request,
                            HttpServletResponse response, String orderId, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        String url = "mycollectionorder/toApplyCsDetail";
        try {
            if (StringUtils.isNotBlank(params.get("id") + "")) {
                BackUser backUser = this.loginAdminUser(request);
                //该条订单是否已审//			int count = auditCenterService.findAuditStatus(params);
//			if(count != 0 || !BackConstant.COLLECTION_ROLE_ID.toString().equals(backUser.getRoleId())){
                MmanLoanCollectionOrder mmanLoanCollectionOrderOri = mmanLoanCollectionOrderService.getOrderById(params.get("id").toString());
                if (mmanLoanCollectionOrderOri != null) {
                    MmanUserLoan userLoan = mmanUserLoanService.get(mmanLoanCollectionOrderOri.getLoanId());
                    model.addAttribute("userLoan", userLoan);
                } else {
                    logger.error("mmanLoanCollectionOrderOri 为null 借款id:" + params.get("id").toString());
                }
                MmanUserInfo userInfo = mmanUserInfoService.getUserInfoById(mmanLoanCollectionOrderOri.getUserId());
                // add by yyf 根据身份证前6位 映射用户地址
                if (userInfo != null) {
                    if (StringUtils.isBlank(userInfo.getIdcardImgZ()) || StringUtils.isBlank(userInfo.getIdcardImgF())) {
                        String idNumber = userInfo.getIdNumber().substring(0, 6);
                        String presentAddress = mmanUserInfoService.getAddressByIDNumber(idNumber);
                        userInfo.setPresentAddress(presentAddress);
                    }
                }
                // 从oss获取图片地址
                OSSUpload ossUpload = new OSSUpload();
                if (userInfo != null) {
                    String userHeadUrl = userInfo.getHeadPortrait();
                    String userFrontImgUrl = userInfo.getIdcardImgZ();
                    String userBackImgUrl = userInfo.getIdcardImgF();

                    // 针对老用户特殊处理
                    if (userHeadUrl != null && userHeadUrl.startsWith("/")) {
                        userHeadUrl = userHeadUrl.substring(1);
                    }
                    if (userFrontImgUrl != null && userFrontImgUrl.startsWith("/")) {
                        userFrontImgUrl = userFrontImgUrl.substring(1);
                    }
                    if (userBackImgUrl != null && userBackImgUrl.startsWith("/")) {
                        userBackImgUrl = userBackImgUrl.substring(1);
                    }
                    URL headImageUrl = ossUpload.sampleGetFileUrl("xjx-files", userHeadUrl, 1000l * 3600l);
                    URL frontImageUrl = ossUpload.sampleGetFileUrl("xjx-files", userFrontImgUrl, 1000l * 3600l);
                    URL backImageUrl = ossUpload.sampleGetFileUrl("xjx-files", userBackImgUrl, 1000l * 3600l);
                    userInfo.setHeadPortrait(headImageUrl.toString());
                    userInfo.setIdcardImgZ(frontImageUrl.toString());
                    userInfo.setIdcardImgF(backImageUrl.toString());
                }
                List<CreditLoanPayDetail> detailList = creditLoanPayDetailService
                        .findPayDetail(mmanLoanCollectionOrderOri.getPayId());
                BigDecimal payMonery = new BigDecimal(0);
                if (detailList != null) {
                    for (CreditLoanPayDetail pay : detailList) {
                        payMonery = payMonery.add(pay.getRealMoney()).add(
                                pay.getRealPenlty());
                    }
                }
                // 银行卡
                SysUserBankCard userCar = sysUserBankCardService.findUserId(mmanLoanCollectionOrderOri.getUserId());
                // 代扣记录
                List<CollectionWithholdingRecord> withholdList = mmanLoanCollectionRecordService.findWithholdRecord(mmanLoanCollectionOrderOri.getId());

                if (BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS.equals(mmanLoanCollectionOrderOri.getStatus())) {
                    userInfo.setIdNumber(MaskCodeUtil.getMaskCode(userInfo.getIdNumber()));
                    userInfo.setUserPhone(MaskCodeUtil.getMaskCode(userInfo.getUserPhone()));
                    userCar.setBankCard(MaskCodeUtil.getMaskCode(userCar.getBankCard()));
                    for (CollectionWithholdingRecord withholdingRecord : withholdList) {
                        withholdingRecord.setLoanUserPhone(MaskCodeUtil.getMaskCode(withholdingRecord.getLoanUserPhone()));
                    }
                }

                model.addAttribute("collectionOrder", mmanLoanCollectionOrderOri);
                model.addAttribute("userInfo", userInfo);
                model.addAttribute("userCar", userCar);// 银行卡
                model.addAttribute("payMonery", payMonery);// 已还金额
                model.addAttribute("detailList", detailList);
                model.addAttribute("withholdList", withholdList);
                model.addAttribute("domaiName", PayContents.XJX_DOMAINNAME_URL);
                url = "mycollectionorder/myorderDetails";
//			}
            }
        } catch (Exception e) {
            logger.error("跳转到订单详情页error " + e);
        }
        params.put("type", '4'); //审核类型 4:催收详情审核
        model.addAttribute("params", params);
        return url;
    }

    /**
     * 催收记录表
     *
     * @param request
     * @param response
     * @param //orderId
     * @param model
     * @return
     */
    @RequestMapping("collectionRecordList")
    public String getloanCollectionRecordList(HttpServletRequest request,
                                              HttpServletResponse response, Model model) {
        List<MmanLoanCollectionRecord> list = null;
        Map<String, String> params = this.getParameters(request);
        try {
            list = mmanLoanCollectionRecordService.findListRecord(params
                    .get("id"));
            model.addAttribute("listRecord", list);
            // 跟进等级
            List<SysDict> levellist = sysDictService
                    .getStatus("xjx_stress_level ");
            HashMap<String, String> levelMap = BackConstant
                    .orderState(levellist);
            model.addAttribute("levelMap", levelMap);// 用于搜索框保留值
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("params", params);
        return "mycollectionorder/listRecord";
    }


    /**
     * 跳转扣款页面
     *
     * @param request
     * @param model
     * @return
     */

    @RequestMapping("tokokuan")
    public String tokokuan(HttpServletRequest request, Model model) {
        Map<String, String> params = this.getParameters(request);
        if (StringUtils.isNotBlank(params.get("id"))) {
            MmanLoanCollectionOrder mmanLoanCollectionOrderOri = mmanLoanCollectionOrderService
                    .getOrderById(params.get("id").toString());
            if (mmanLoanCollectionOrderOri != null) {
                CreditLoanPay creditLoanPay = creditLoanPayService
                        .get(mmanLoanCollectionOrderOri.getPayId());
                model.addAttribute(
                        "totalPayMonery",
                        creditLoanPay.getReceivablePrinciple().add(
                                creditLoanPay.getReceivableInterest()));
            } else {
                logger.error("mmanLoanCollectionOrderOri is null, loanId : " + params.get("id"));
            }
        }
        model.addAttribute("params", params);
        return "mycollectionorder/tokokuan";
    }

    /**
     * 发起扣款
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("kokuan")
    public String kokuan(HttpServletRequest request, HttpServletResponse response, Model model) {
        String url = null;
        String erroMsg = null;
        JsonResult result = new JsonResult("-1", "代扣失败");
        Map<String, String> params = this.getParameters(request);
        BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
        if (backUser != null) {
            try {
                params.put("roleId", backUser.getRoleId());
                params.put("operationUserId", backUser.getId().toString());
                result = mmanLoanCollectionRecordService.xjxWithholding(params);
            } catch (Exception e) {
                logger.error("saveCompany error", e);
            }
            SpringUtils.renderDwzResult(response, "0".equals(result.getCode()),
                    result.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
                    params.get("parentId").toString());
            model.addAttribute(MESSAGE, erroMsg);
            model.addAttribute("params", params);
        } else {
            result.setMsg("登录已失效，请重新登录！");
        }
        return url;

    }

    /**
     * 异步处理（更新代扣结果）,回调接口，用于告知代扣处理结果(代扣走支付中心)
     */
    @RequestMapping(value = "withhold-callback")
    @ResponseBody
    public void dealWithholdResult(String text) {
        logger.info("接收到代扣回调请求参数 " + text);
        JSONObject obj = JSONObject.parseObject(text);
        String uuid = (String) obj.get("uuid");
        boolean code = (boolean) obj.get("result");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", uuid);
        if (code) {
            map.put("status", 1); // 代扣成功
        } else {
            map.put("status", 2); // 代扣失败
        }
        map.put("updateDate", new Date());
        int count = collectionWithholdingRecordService.updateWithholdStatus(map);// 更新代扣记录状态
        if (count > 0) {
            obj.put("0", "更新成功！");
        } else {
            obj.put("1", "更新失败！");
        }
    }


    /**
     * 跳转转派页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    // 第一步 选择转派公司
    @RequestMapping("toOrderDistibute")
    public String toOrderDistibute(HttpServletRequest request,
                                   HttpServletResponse response, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        try {
            if (params.get("ids") != null
                    && StringUtils.isNotBlank(params.get("ids").toString())) {
                if (params.get("groupStatus") != null
                        && !"0".equals(params.get("groupStatus"))) {
                    model.addAttribute("message", "只能相同组之间转派。请选择同组的数据");
                }

            } else {
                model.addAttribute("message", "请选择要转派的订单");
            }
            // 催收公司
            MmanLoanCollectionCompany mmanLoanCollectionCompany = new MmanLoanCollectionCompany();
            mmanLoanCollectionCompany.setStatus(BackConstant.ON);// 启用的催收公司
            List<MmanLoanCollectionCompany> companyList = mmanLoanCollectionCompanyService
                    .getList(mmanLoanCollectionCompany);
            List<SysDict> dict = sysDictService
                    .findDictByType("xjx_overdue_level"); // 催收组
            model.addAttribute("companyList", companyList);// 启用的公司列表
            model.addAttribute("group", dict);
            model.addAttribute("params", params);
        } catch (Exception e) {
            logger.error("getCollectionPage error", e);
        }
        model.addAttribute("params", params);
        return "mycollectionorder/orderDispatch";
    }

    /**
     * 转派-根据公司查询分组or催收员
     *
     * @param //mmanLoanCollectionOrder
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("findPerson")
    @ResponseBody
    public void orderDisBackUser(HttpServletRequest request,
                                 HttpServletResponse response, Model model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            BackUser backUser = new BackUser();
            backUser.setCompanyId(String.valueOf(params.get("companyId")));// 选择的催收公司
            backUser.setGroupLevel(String.valueOf(params.get("groupId")));// 催收组
            backUser.setNotMineId(String.valueOf(params.get("currUserId")));// 剔除自己
            backUser.setUserStatus(BackUser.STATUS_USE);// 启用的催收员

            List<BackUser> backUserList = backUserService
                    .findUserByDispatch(backUser);
            if (CollectionUtils.isNotEmpty(backUserList)) {
                SpringUtils.renderJson(response,
                        JSONArray.fromObject(backUserList).toString(),
                        new String[0]);
            }
        } catch (Exception e) {
            logger.error("findPerson error", e);
        }
    }

    // 转派
    @RequestMapping(value = "doDispatch")
    public String doDispatch(MmanLoanCollectionOrder order,
                             HttpServletRequest request, HttpServletResponse response,
                             Model model) {
        JsonResult json = new JsonResult("-1", "转派失败");
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            if (StringUtils.isNoneBlank(order.getId())) {
                Calendar calendar = Calendar.getInstance();
                int now = calendar.get(Calendar.HOUR_OF_DAY);
                if (now >= 6) {
                    if (params.get("groupStatus") != null
                            && !"0".equals(params.get("groupStatus"))) {
                        json.setMsg("只能相同组之间转派。请选择同组的数据");
                    } else {
                        BackUser backUser = (BackUser) request.getSession()
                                .getAttribute(Constant.BACK_USER);
                        json = mmanLoanCollectionRecordService.batchDispatch(
                                backUser, order);
                    }
                } else {
                    json.setMsg("0点到6点之间不允许进行转派操作");
                }
            } else {
                json.setMsg("请选择要转派的订单");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SpringUtils.renderDwzResult(response, "0".equals(json.getCode()),
                json.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
                params.get("parentId").toString());
        model.addAttribute("params", params);

		/*
         * SpringUtils.renderDwzResult(response, "0".equals(json.getCode()),
		 * json.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
		 * params.get("parentId").toString());
		 */
        return null;
    }

    /**
     * 将cookie封装到Map里面
     *
     * @param request
     * @return
     */
    private static Map<String, String> ReadCookieMap(HttpServletRequest request) {
        Map<String, String> cookieMap = new HashMap<String, String>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie.getValue());
            }
        }
        return cookieMap;
    }

    /**
     * 跳转到发送短信页面
     *
     * @param model 需要发短信的手机号
     * @return
     */
    @RequestMapping("gotoSendMsg")
    public String gotoSendMsg(HttpServletRequest request,
                              HttpServletResponse response, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            if (StringUtils.isNotBlank(params.get("id") + "")) {
                MmanLoanCollectionOrder mmanLoanCollectionOrderOri = mmanLoanCollectionOrderService
                        .getOrderById(params.get("id").toString());
                MmanUserInfo userInfo = mmanUserInfoService
                        .getUserInfoById(mmanLoanCollectionOrderOri.getUserId());
                model.addAttribute("loanOrderId",
                        mmanLoanCollectionOrderOri.getLoanId());
                model.addAttribute("userPhone", userInfo.getUserPhone());
                model.addAttribute("orderId",
                        mmanLoanCollectionOrderOri.getUserId());
                model.addAttribute("userId", userInfo.getId());
                model.addAttribute("originalNum", userInfo.getUserPhone());
                model.addAttribute("msgCount", iSmsUserDao
                        .getSendTotalMsgCount(mmanLoanCollectionOrderOri
                                .getLoanId()));
                String msgType = mmanLoanCollectionOrderOri.getCurrentOverdueLevel();
                if ("5".equals(msgType)) {
                    List<String> list = new ArrayList<String>();
                    list.add("3");
                    list.add("4");
                    list.add("5");
                    map.put("msgType", list);
                } else if ("6".equals(msgType) || "7".equals(msgType)) {
                    List<String> list = new ArrayList<String>();
                    list.add("5");
                    map.put("msgType", list);
                } else if ("3".equals(msgType)) {
                    List<String> list = new ArrayList<String>();
                    list.add("3");
                    map.put("msgType", list);
                } else {
                    List<String> list = new ArrayList<String>();
                    list.add("4");
                    map.put("msgType", list);
                }
                model.addAttribute("msgs", templateSmsDao.getType(map));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("params", params);
        return "mycollectionorder/smsSendDetail";
    }

    /**
     * 发送催收短信
     *
     * @param //mobiles        需要发送短信的手机号
     * @param //smsContent     短信内容
     * @param //isSendSmsToAll 是否发送给所有人
     * @return
     */
    @RequestMapping("sendMsg")
    public ServiceResult SendSms(HttpServletRequest request,
                                 HttpServletResponse response, Model model) {
        JsonResult result = new JsonResult("-1", "发送短信失败");
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            String mobiles = request.getParameter("phoneNumber");
            String msgId = request.getParameter("msgId");
            // String smsContent =
            // templateSmsDao.getTemplateSmsById(msgId).getContenttext();
            String smsContent = null;
            if (msgId == null || msgId == "") {
                result.setMsg("请选择需要发送的短信内容!");
            } else {
                smsContent = templateSmsDao.getTemplateSmsById(msgId)
                        .getContenttext();
                String orderId = request.getParameter("loanOrderId");
                String userName = mmanUserInfoService.getUserInfoById(
                        request.getParameter("orderId")).getRealname();
                String originalNum = request.getParameter("originalNum");
                String userId = request.getParameter("userId");
                smsContent = userName + smsContent;
                // 查询出该借款人所有的联系人
                List<MmanUserRela> userReal = mmanUserRelaService
                        .getContactPhones(userId);
                List<String> phones = new ArrayList<String>();
                for (MmanUserRela mmanUserRela : userReal) {
                    phones.add(mmanUserRela.getInfoValue());
                }
                if (phones.contains(mobiles) || mobiles.equals(originalNum)) {
                    // 查询出对应借款订单信息
                    MmanLoanCollectionOrder order = mmanLoanCollectionOrderService
                            .getOrderWithId(orderId);
                    // 查询出该订单当天已发短信的次数
                    int count = smsUserService.getSendMsgCount(orderId);
                    int overdueDays = order.getOverdueDays();
                    // System.out.println("***********************************");
                    // System.out.println("手机号:" + mobiles);
                    // System.out.println("短息内容:" + smsContent);
                    // System.out.println("借款编号:" + orderId);
                    // System.out.println("订单逾期天数:" + overdueDays);
                    // System.out.println("今日已发短信次数:" + count);
                    // System.out.println("***********************************");
                    // 逾期10天以内(包括10) 可发短信的数量为每天3条
                    if (overdueDays <= 10) {
                        if (count < 3) {
                            sendMsg(result, mobiles, orderId, userName,
                                    smsContent, request);
                        } else {
                            result.setCode("3");
                            result.setMsg("今日该订单短信已达上限");
                        }
                    } else {
                        // 逾期10天以上(不包括10) 可发短信的数量为每天5条
                        if (count < 5) {
                            sendMsg(result, mobiles, orderId, userName,
                                    smsContent, request);
                        } else {
                            result.setCode("3");
                            result.setMsg("今日该订单短信已达上限");
                        }
                    }
                } else {
                    result.setCode("5");
                    result.setMsg("请检查您输入的手机号码!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("params", params);
        SpringUtils.renderDwzResult(response, "0".equals(result.getCode()),
                result.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
                params.get("parentId").toString());
        return null;
    }

    /**
     * 发送短信方法
     *
     * @param result
     * @param mobiles
     * @param orderId
     * @param userName
     * @param smsContent
     * @throws InterruptedException
     */
    private void sendMsg(JsonResult result, String mobiles, String orderId,
                         String userName, String smsContent, HttpServletRequest request)
            throws InterruptedException {
        if (StringUtils.isNotBlank(smsContent)
                && StringUtils.isNotBlank(mobiles)) {
            if (!"0".equals(new SMSUtils().sendSms(mobiles, "【"
                    + SmsYunFengUtil.TITLE + "】" + smsContent))) {
                result.setCode("204");
                result.setMsg("发送失败");
            } else {
                result.setMsg("发送成功");
                result.setCode("0");
                // 新增一条短信发送记录
                SmsUser msg = new SmsUser();
                BackUser user = this.loginAdminUser(request);
                msg.setSendUserId(user.getUuid());
                msg.setAddTime(new Date());
                msg.setLoanOrderId(orderId);
                msg.setSmsContent(smsContent);
                msg.setUserPhone(mobiles);
                msg.setUserName(userName);
                this.smsUserService.insert(msg);
                // 每个数据包发送后等待一秒 云峰短信有频率限制
                Thread.sleep(1000);
            }
        } else {
            result.setMsg("参数不正确");
        }
    }

    /**
     * 分期还款计算
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("installmentPay")
    public String getInstallmentPay(HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, String> params = this.getParameters(request);
        model.addAttribute("params", params);

        if (!params.containsKey("installmentpay") || StringUtils.isBlank(params.get("installmentpay"))) {
            params.put("installmentpay", "2");
        }
        try {
            if (StringUtils.isNotBlank(params.get("id"))) {
                MmanLoanCollectionOrder mmanLoanCollectionOrderOri = mmanLoanCollectionOrderService.getOrderById(params.get("id").toString());
                MmanUserLoan userLoan = mmanUserLoanService.get(mmanLoanCollectionOrderOri.getLoanId());
                if (userLoan != null) {
                    model.addAttribute("loanUserName", mmanLoanCollectionOrderOri.getLoanUserName());
                    model.addAttribute("loanUserPhone", mmanLoanCollectionOrderOri.getLoanUserPhone());
                    model.addAttribute("ownMoney", userLoan.getLoanMoney());// 总本金
                    model.addAttribute("overdueMoney", userLoan.getLoanPenalty());// 总滞纳金
                    int count = Integer.valueOf(params.get("installmentpay"));
                    // 获取分期明细信息
                    List<InstallmentPayInfoVo> list = getInstallmentPayInfoList(count, userLoan);

                    model.addAttribute("installmentPayInfoVoList", list);
                    model.addAttribute("installmentList", BackConstant.typeMap);
                }

                List<InstallmentPayRecord> installmentPayRecordList = mmanLoanCollectionRecordService.findInstallmentList(params.get("id").toString());
                model.addAttribute("installmentPayRecordList", installmentPayRecordList);
            }
        } catch (Exception e) {
            logger.error("分期还款计算异常(installmentPay)：", e);
        }
        return "mycollectionorder/toInstallmentPay";
    }

    private List<InstallmentPayInfoVo> getInstallmentPayInfoList(int count, MmanUserLoan userLoan) {
        if (count > 4) {
            count = 2; // 分期最多为4期
        }
        List<InstallmentPayInfoVo> list = new ArrayList<InstallmentPayInfoVo>();
        InstallmentPayInfoVo installmentPayInfoVo = null;

        for (int i = 0; i < count; i++) {
            installmentPayInfoVo = new InstallmentPayInfoVo();
            installmentPayInfoVo.setInstallmentType(BackConstant.typeNameMap.get(String.valueOf(i + 1)));
            installmentPayInfoVo.setRepayTime(getNextWeek(i));
            BigDecimal stagesOwnMoney = userLoan.getLoanMoney().divide(new BigDecimal(count), 2, BigDecimal.ROUND_UP);
            installmentPayInfoVo.setStagesOwnMoney(stagesOwnMoney); // 分期本金
            BigDecimal stagesOverdueMoney = userLoan.getLoanPenalty().divide(new BigDecimal(count), 2, BigDecimal.ROUND_UP);
            installmentPayInfoVo.setStagesOverdueMoney(stagesOverdueMoney); // 分期滞纳金
            installmentPayInfoVo.setTotalRepay(stagesOwnMoney.add(stagesOverdueMoney)); // 分期还款总计
            if (i == 0) {
                BigDecimal serviceCharge = new BigDecimal(10).multiply(new BigDecimal(count));
                installmentPayInfoVo.setServiceCharge(serviceCharge);
                installmentPayInfoVo.setTotalRepay(stagesOwnMoney.add(stagesOverdueMoney).add(serviceCharge)); // 分期还款总计
            }
            list.add(installmentPayInfoVo);
        }
        return list;
    }

    private Date getNextWeek(int i) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_YEAR, i);
        return calendar.getTime();
    }

    /**
     * 分期账单创建
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("insertInstallmentPayRecord")
    public void insertInstallmentPayRecord(HttpServletRequest request, HttpServletResponse response, Model model) {
        String url = null;
        String erroMsg = null;
        List<InstallmentPayRecord> installmentPayRecordList = null;
        Map<String, Object> map = new HashMap<String, Object>();
        JsonResult result = new JsonResult("-1", "创建分期失败");
        map.put("code", "-1");
        Map<String, String> params = this.getParameters(request);
        BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
        try {
            List<InstallmentPayRecord> countList = mmanLoanCollectionRecordService.findInstallmentList(params.get("id").toString());
//			countList = null;
            if (CollectionUtils.isEmpty(countList)) {
                MmanLoanCollectionOrder mmanLoanCollectionOrderOri = mmanLoanCollectionOrderService.getOrderById(params.get("id").toString());
                MmanUserLoan userLoan = mmanUserLoanService.get(mmanLoanCollectionOrderOri.getLoanId());
                if (userLoan != null) {
                    map.put("loanUserName", mmanLoanCollectionOrderOri.getLoanUserName());
                    map.put("loanUserPhone", mmanLoanCollectionOrderOri.getLoanUserPhone());
                    int count = Integer.valueOf(params.get("installmentpay"));
                    // 获取分期明细信息
                    List<InstallmentPayInfoVo> list = getInstallmentPayInfoList(count, userLoan);
                    params.put("roleId", backUser.getRoleId());
                    params.put("operationUserId", backUser.getId().toString());
                    params.put("payMoney", String.valueOf(list.get(0).getTotalRepay())); // 扣款金额
                    JsonResult withholdingResult = mmanLoanCollectionRecordService.xjxWithholding(params);

                    withholdingResult.setCode("0");
                    // 代扣成功
                    if ("0".equals(withholdingResult.getCode())) {
                        result = mmanLoanCollectionRecordService.insertInstallmentPayRecord(list, mmanLoanCollectionOrderOri);
                        installmentPayRecordList = (List<InstallmentPayRecord>) result.getData();
                        map.put("installmentPayRecordList", installmentPayRecordList);
                        map.put("code", "0"); //  成功
                    }
                }
            } else {
                result.setMsg("该订单已创建过了");
                map.put("code", "1");
            }
            String json = JSONUtil.beanToJson(map);
            response.getWriter().write(json);
            response.getWriter().flush();

        } catch (Exception e) {
            logger.error("分期账单创建失败！", e);
        }
    }

    @RequestMapping("fqWithholding")
    public void fqWithholding(HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "-1");
        Map<String, String> params = this.getParameters(request);
        BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
        try {
            params.put("roleId", backUser.getRoleId());
            params.put("operationUserId", backUser.getId().toString());
            JsonResult result = mmanLoanCollectionRecordService.fqWithholding(params);

            String json = JSONUtil.beanToJson(result);
            response.getWriter().write(json);
            response.getWriter().flush();
        } catch (Exception e) {
            logger.error("分期列表中代扣失败 ", e);
        }

    }

    @RequestMapping("getContactInfo")
    private String gotoGetContactInfoPage(HttpServletRequest request,
                                          HttpServletResponse response, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        String phoneNum = request.getParameter("phoneNum");
        if (phoneNum != "" && phoneNum != null) {
            model.addAttribute("contactInfo",
                    mmanUserInfoService.getContactInfo(phoneNum));
        }
        model.addAttribute("params", params);
        return "mycollectionorder/getContactInfo";
    }

    /**
     * 减免页面-订单减免
     *
     * @param request
     * @author yyf
     */
    @RequestMapping("jianmian")
    public String toReductionPage(HttpServletRequest request,
                                  HttpServletResponse response, String orderId, Model model) {

        HashMap<String, Object> params = this.getParametersO(request);
        params = mmanLoanCollectionOrderService.toReductionPage(params);
        model.addAttribute("params", params);
        return "mycollectionorder/reductionPage";

    }

    /**
     * 提交减免申请-订单减免
     *
     * @author yyf
     */
    @RequestMapping("doReduction")
    public void doReduction(HttpServletRequest request, HttpServletResponse response, Model model) {
        JsonResult result = new JsonResult("-1", "申请减免失败");
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            int checkingNumber = auditCenterService.getAuditChecking(params);//获取状态为审核中的减免申请个数
            if (checkingNumber == 0) {//审核状态为非审核中，就添加减免申请
                result = auditCenterService.saveAuditReduction(params, request);
            } else {//审核状态为审核中
                result.setMsg("减免正在审核，不能重复申请！");
            }
        } catch (Exception e) {
            result.setMsg("申请减免异常");
            e.printStackTrace();
        }
        SpringUtils.renderDwzResult(response, "0".equals(result.getCode()),
                result.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
                params.get("parentId").toString());
    }


    @RequestMapping("getorderPage")
    public String getorderPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            //add  减免列表默认申请中状态
            if (!params.containsKey("status") && params.containsKey("--")) {
                params.put("status", "0");
                params.put("pageNum", 1);
            }
            params.put("noAdmin", Constant.ADMINISTRATOR_ID);
            PageConfig<Mman_loan_collection_orderdeduction> pageConfig = collection_orderdeductionService.findPage(params);
            model.addAttribute("pm", pageConfig);
            model.addAttribute("params", params);// 用于搜索框保留值
        } catch (Exception e) {
            logger.error("getCompanyPage error", e);
        }
        return "mycollectionorder/getorderPage";
    }

    @RequestMapping("testKh")
    public String testKh(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            Date date = new Date();
            HashMap<String, Object> params = new HashMap<String, Object>();
//			params.put("currDate", DateUtil.getBeforeOrAfter(date,-1));
            params.put("currDate", request.getParameter("currDate"));
            params.put("begDate", request.getParameter("begDate"));
            params.put("endDate", request.getParameter("endDate"));
            params.put("isFirstDay", date.getDate());   //获取当前日
            // 考核统计
            countCollectionAssessmentService.countCallAssessment(params);
        } catch (Exception e) {
            logger.error("testKh error", e);
        }
        return null;
    }

    @RequestMapping("testGl")
    public String testGl(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            Date date = new Date();
            HashMap<String, Object> params = new HashMap<String, Object>();
//			params.put("currDate", DateUtil.getBeforeOrAfter(date,-1));
            params.put("currDate", request.getParameter("currDate"));
            params.put("begDate", DateUtil.getDayFirst());
            params.put("endDate", DateUtil.getDayLast());
            params.put("isFirstDay", date.getDate());   //获取当前日
            // 管理统计
            countCollectionManageService.countCallManage(params);
        } catch (Exception e) {
            logger.error("testGl error", e);
        }
        return null;
    }

    @RequestMapping("testCj")
    public String testCj(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            Date date = new Date();
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("begDate", DateUtil.getDateFormat(DateUtil.getBeforeOrAfter(date, -1), "yyyy-MM-dd"));

            // 管理统计
            countCollectionAssessmentService.countCallOrder(params);
        } catch (Exception e) {
            logger.error("testCj error", e);
        }
        return null;
    }

}
