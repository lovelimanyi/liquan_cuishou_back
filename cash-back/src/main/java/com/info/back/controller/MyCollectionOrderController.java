package com.info.back.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.info.back.dao.IMerchantInfoDao;
import com.info.back.dao.IMmanLoanCollectionRecordDao;
import com.info.back.dao.IMmanUserLoanDao;
import com.info.back.dao.ITemplateSmsDao;
import com.info.back.result.JsonResult;
import com.info.back.service.*;
import com.info.back.utils.*;
import com.info.config.PayContents;
import com.info.constant.Constant;
import com.info.web.pojo.*;
import com.info.web.synchronization.dao.IDataDao;
import com.info.web.util.*;
import com.liquan.oss.OSSUpload;
import com.sun.tracing.dtrace.ArgsAttributes;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.security.krb5.internal.PAData;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 我的催收订单控制层
 *
 * @author Administrator
 */
@Controller
@RequestMapping("collectionOrder/")
public class MyCollectionOrderController extends BaseController {

    private static Logger logger = Logger.getLogger(MyCollectionOrderController.class);

    private static final Pattern MOBILE_PATTERN = Pattern.compile("^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$");

    private static final String SHORT_MESSAGE_LIST_REDIS_KEY = "msgs";
    private static final String SHORT_MESSAGE_YOUMI_CHANNEL_FROM_REDIS_KEY = "ymgj";
    private static final String SHORT_MESSAGE_YOUMI_CHANNEL_FROM = "ymgj";

    private static final String SHORT_MESSAGE_LIMIT_COUNT_REDIS_KEY = "msgCountLimit";

    private static final String DICTIONARY_TYPE_OVERDUE_LEVEL = "xjx_overdue_level";

    private static final String DICTIONARY_TYPE_ORDER_STATE = "xjx_collection_order_state";


    @Autowired
    private IMmanLoanCollectionCompanyService mmanLoanCollectionCompanyService;

    @Autowired
    private IMmanLoanCollectionOrderService mmanLoanCollectionOrderService;

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
    private ICreditLoanPayService creditLoanPayService;

    @Autowired
    private ISysUserBankCardService sysUserBankCardService;

    @Autowired
    private ISmsUserService smsUserService;

    @Autowired
    private IMmanUserRelaService mmanUserRelaService;

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

    @Autowired
    private IMmanUserLoanDao mmanUserLoanDao;

    @Autowired
    private IDataDao dataDao;


    @Autowired
    private ICommunicationSituationService situationService;

    @Autowired
    private ISysDictService sysDictService;

    @Autowired
    private IMmanLoanCollectionRecordDao mmanLoanCollectionRecordDao;

    /**
     * 我的订单初始化加载查询
     *
     * @param model
     * @return
     */
    @RequestMapping("getListCollectionOrder")
    public String getListCollectionOrder(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
//        String status = params.get("status") == null ? "1":params.get("status").toString();
//        params.put("status",status);

        BackUser backUser = (BackUser) request.getSession().getAttribute(
                Constant.BACK_USER);
        params.put("currentUserAccount", backUser.getUserAccount());
        List<BackUserCompanyPermissions> CompanyPermissionsList = backUserService
                .findCompanyPermissions(backUser.getId());
        if (CompanyPermissionsList != null && CompanyPermissionsList.size() > 0) {// 指定公司的订单
            params.put("CompanyPermissionsList", CompanyPermissionsList);
        }
        checkPermission(params, backUser);
        params.put("source", BackConstant.OPERATION_RECORD_SOURCE_MY_ORDER);  // 操作來源 我的催收订单
        // 查询公司列表
        MmanLoanCollectionCompany mmanLoanCollectionCompany = new MmanLoanCollectionCompany();
        List<MmanLoanCollectionCompany> ListMmanLoanCollectionCompany = mmanLoanCollectionCompanyService
                .getList(mmanLoanCollectionCompany);
        // 分页的订单信息
        PageConfig<OrderBaseResult> page;
        if (!BackConstant.COLLECTION_ROLE_ID.equals(backUser.getRoleId())) {
            page = mmanLoanCollectionOrderService.getPage(params);
        } else {
            params.put("roleUserId", backUser.getUuid());
            page = mmanLoanCollectionOrderService.getCollectionUserPage(params);
        }
        model.addAttribute("ListMmanLoanCollectionCompany", ListMmanLoanCollectionCompany);

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
        model.addAttribute("userGropLeval", backUser.getRoleId());
        model.addAttribute("dictMap", BackConstant.groupNameMap);
        // 跟进等级
        List<SysDict> levellist = sysDictService.getStatus("xjx_stress_level");
        // 商户信息
//        model.addAttribute("merchantMap", mmanLoanCollectionOrderService.getMerchantMap());
        model.addAttribute("repayChannelMap", mmanLoanCollectionOrderService.getRepayChannelMap());
        HashMap<String, String> levelMap = BackConstant.orderState(levellist);
        model.addAttribute("levellist", levellist);
        model.addAttribute("levelMap", levelMap);
        model.addAttribute("params", params); // 用于搜索框保留值
        model.addAttribute("channelFromMap",BackConstant.channelFromMap);
        model.addAttribute("merchantNoMap",BackConstant.merchantNoMap);
        return "mycollectionorder/collectionOrder";
    }

    /**
     * 我的催收订单导出
     *
     * @param request
     * @param response
     */
    @RequestMapping("exportCollectionOrder")
    public void exportCollectionOrder(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
            List<BackUserCompanyPermissions> CompanyPermissionsList = backUserService.findCompanyPermissions(backUser.getId());
            if (CompanyPermissionsList != null && CompanyPermissionsList.size() > 0) {// 指定公司的订单
                params.put("CompanyPermissionsList", CompanyPermissionsList);
            }

            checkPermission(params, backUser);

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
                List<Object[]> contents = new ArrayList<>();
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
     * 根据操作账号取货其对应的权限信息
     *
     * @param params
     * @param backUser
     */
    private void checkPermission(HashMap<String, Object> params, BackUser backUser) {
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
    }

    /**
     * 添加催记录和催收建议页面
     *
     * @param model
     * @return
     */
    @RequestMapping("toCollectionRecordAndAdvice")
    public String toAddCollectionRecord(HttpServletRequest request, Model model) {
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
                    String infoName = list[2];
                    String infoVlue = list[3];
                    params.put("infoVlue", infoVlue);
                    params.put("infoName", infoName);
//                    logger.error("CollectionRecordAndAdvice-userRelaId=" + list[0]);
//                    logger.error("CollectionRecordAndAdvice-orderId=" + list[1]);
                } else {
                    logger.error("前台参数异常，list = " + list);
                }
                //根据userRelaId查询联系人
                if (StringUtils.isNotEmpty(userRelaId)) {
                    MmanUserRela userRela = mmanUserRelaService.getUserRealByUserId(userRelaId);
                    if (userRela != null) {
                        params.put("infoVlue", userRela.getInfoValue());
                        params.put("infoName", userRela.getInfoName());
                    } else {
//                        logger.error("userRela is null,userRelaId = " + userRelaId);
                    }
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
                if (baseOrder.getOverdueDays() > 3) {
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
//        MmanLoanCollectionOrder mmanLoanCollectionOrderOri = mmanLoanCollectionOrderService
//                .getOrderById(params.get("id").toString());
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
     * @param model
     * @return
     */
    @RequestMapping("getlistlog")
    public String getCollectionStatusChangeLog(HttpServletRequest request, Model model) {
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

    //将补充手机号字符串转化为数组返回
    public static ArrayList<String> getAdditPhonesList(String str) {
        ArrayList<String> additPhonesArrary = new ArrayList<String>();
        if (str == "") {
            return additPhonesArrary;
        } else if (str != "" && !str.contains(",")) {
            additPhonesArrary.add(str);
            return additPhonesArrary;
        } else {
            additPhonesArrary.addAll(Arrays.asList(str.split(",")));
            return additPhonesArrary;
        }
    }


    /**
     * 调第三方风控，获取通话记录
     * lmy
     * List<Map<String,Object>>
     */
    @RequestMapping("/getContactRecords")
    @ResponseBody
    public String getContactRecords(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashedMap(8);
        String returnInfo = null;
        try {
            HashMap<String, Object> params = getParametersO(request);
            String orderId = params.get("orderId").toString();
            MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderById(orderId);
            if (order != null) {
                if (!BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS.equals(order.getStatus())) {
                    String idNumber = params.get("idNumber") + "";
                    if (idNumber != null && idNumber != "") {
                        Map<String, String> map = new HashMap();
                        map.put("id", idNumber);
                        //调用第三方风控
                        returnInfo = HttpUtil.getInstance().doPost(PayContents.XJX_GET_PHONES, JSON.toJSONString(map));
                        resultMap.put("returnInfo", returnInfo);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取通话记录出错:" + e);
            e.printStackTrace();
        }
        return JSON.toJSONString(returnInfo);
    }

    /**
     * 跳转到订单详情页
     *
     * @param request
     * @param
     * @param
     * @param model
     * @return
     */
    @RequestMapping("toxianqin")
    public String toxianqin(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        String url = "mycollectionorder/toApplyCsDetail";
        BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
        try {
            String id = params.get("id") + "";
            if (StringUtils.isNotBlank(id)) {
                //该条订单是否已审//			int count = auditCenterService.findAuditStatus(params);
//			if(count != 0 || !BackConstant.COLLECTION_ROLE_ID.toString().equals(backUser.getRoleId())){
                MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderById(id);

                if (order != null) {
                    MmanUserLoan userLoan = mmanUserLoanService.get(order.getLoanId());
                    //如果是分期商城的订单则判断是否获取商品名称
                    if (Constant.ORDER_TYPE_FEN.equals(userLoan.getBorrowingType())) {
                        if (StringUtils.isBlank(order.getProductName())) {
                            String LoanId = StringUtils.substringBefore(order.getLoanId(), Constant.SEPARATOR_FOR_ORDER_SOURCE);
                            Map<String, String> paramMap = new HashedMap();
                            paramMap.put("id", LoanId);
                            //TODO 分期商城上线后更改地址
//                            String result = HttpUtil.get(PayContents.PRODUCT_NAME_URL,paramMap);
                            String result = "{\"code\":\"00\",\"productName\":\"分期商城\"}";
                            logger.error("productName:" + LoanId + result);

                            if (result != null) {
                                JSONObject jsonResult = JSONObject.parseObject(result);
                                if ("00".equals(jsonResult.get("code"))) {
                                    String productName = jsonResult.get("productName").toString();
                                    order.setProductName(productName);
                                    mmanLoanCollectionOrderService.updateProductName(order);
                                }
                            }
                        }
                    }

                    if (userLoan.getPaidMoney().compareTo(BigDecimal.ZERO) <= 0) {
                        userLoan.setServiceCharge(BigDecimal.ZERO);
                    }
                    model.addAttribute("userLoan", userLoan);
                } else {
                    logger.error("mmanLoanCollectionOrderOri 为null 借款id:" + params.get("id").toString());
                }
                MmanUserInfo userInfo = mmanUserInfoService.getUserInfoAccordId(order.getUserId());
                if (userInfo != null) {
                    // 调用接口获取用户共债手机号
                    String phones = getPhones(userInfo);
                    // 判断数据库数据和数据库存储数据是否一致，不一致则更新数据库数据
                    if (!phones.equals(userInfo.getUserPhones())) {
                        // 更新userInfo中数据
                        updateUserInfo(userInfo, phones);
                    }
                    userInfo.setUserPhones(phones);
                }

                //获取补充手机号
                String additionalPhones = getAdditionalPhones(userInfo.getUserName(), userInfo.getUserPhones());
                ArrayList<String> AdditPhonesList = getAdditPhonesList(additionalPhones);

                // add by yyf 根据身份证前6位 映射用户地址
                if (userInfo != null) {
                    if (StringUtils.isBlank(userInfo.getIdcardImgZ()) || StringUtils.isBlank(userInfo.getIdcardImgF())) {
                        String idNumber = userInfo.getIdNumber().substring(0, 6);
                        String presentAddress = mmanUserInfoService.getAddressByIDNumber(idNumber);
                        userInfo.setPresentAddress(presentAddress);
                    }
                }

                // 从oss获取图片地址
                getUserImageUrl(userInfo);

                // 银行卡
                SysUserBankCard userCar = sysUserBankCardService.findUserId(order.getUserId());

                // 还款完成用户信息掩码处理
                dealwithUserInfo(order, userInfo, userCar);

                List<TemplateSms> msgs = getMatchMsgsTemplate(order);
                int count = smsUserService.getSendMsgCount(order.getLoanId());
                String msgLimitCountKey = BackConstant.REDIS_KEY_PREFIX + SHORT_MESSAGE_LIMIT_COUNT_REDIS_KEY;
                int msgCountLimit = JedisDataClient.get(msgLimitCountKey) == null ? 0 : Integer.valueOf(JedisDataClient.get(msgLimitCountKey));
                if (msgCountLimit == 0) {
                    // 默认短信发送上限为2条
                    msgCountLimit = 2;
                }

                // 沟通情况
                List<CommunicationSituation> communicationSituations = situationService.getLableList();
                // 订单状态
                Map<String, Object> orderStatusMap = getOrderStatusMap();
                // 沟通情况
                Map<String, Object> communicationSituationsMap = getCommunicationSituationsMap(communicationSituations);
                // 风控标签
                Map<String, Object> fengKongLableMap = fengKongService.getFengKongLableMap();
                // 逾期等级
                Map<String, Object> overdueLevelMap = getOverdueLevelMap(getAllOverdueLevel());
                int remainCount = msgCountLimit - count > 0 ? msgCountLimit - count : 0;
                model.addAttribute("communicationSituationsMap", communicationSituationsMap);
                model.addAttribute("overdueLevelMap", overdueLevelMap);
                model.addAttribute("orderStatusMap", orderStatusMap);
                model.addAttribute("remainMsgCount", remainCount);
                model.addAttribute("msgCountLimit", msgCountLimit);
                model.addAttribute("fengKongLableMap", fengKongLableMap);
                model.addAttribute("msgs", msgs);
                model.addAttribute("orderId", id);
                model.addAttribute("phoneNumber", order.getLoanUserPhone());
                model.addAttribute("collectionOrder", order);
                model.addAttribute("userInfo", userInfo);
                model.addAttribute("userCar", userCar);// 银行卡
                model.addAttribute("backUser", backUser);
                model.addAttribute("additionalPhones", additionalPhones);
                model.addAttribute("AdditPhonesList", AdditPhonesList);
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
     * 对于还款完成用户手机号，身份证号等信息掩码处理
     *
     * @param mmanLoanCollectionOrderOri
     * @param userInfo
     * @param userCar
     */
    private void dealwithUserInfo(MmanLoanCollectionOrder mmanLoanCollectionOrderOri, MmanUserInfo userInfo, SysUserBankCard userCar) {
        if (BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS.equals(mmanLoanCollectionOrderOri.getStatus())) {
            userInfo.setIdNumber(MaskCodeUtil.getMaskCode(userInfo.getIdNumber()));
            userInfo.setUserPhone(MaskCodeUtil.getMaskCode(userInfo.getUserPhone()));
            userCar.setBankCard(MaskCodeUtil.getMaskCode(userCar.getBankCard()));
        }
    }

    /**
     * 获取用户头像地址
     *
     * @param userInfo
     */
    private void getUserImageUrl(MmanUserInfo userInfo) {
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
    }

    /**
     * 更新用户共债手机号信息
     *
     * @param userInfo
     * @param phones
     */
    private void updateUserInfo(MmanUserInfo userInfo, String phones) {
        MmanUserInfo info = new MmanUserInfo();
        info.setUserPhones(phones);
        info.setId(userInfo.getId());
        mmanUserInfoService.updateUserPhonesByUserId(info);
    }

    /**
     * 获取共债手机号
     *
     * @param userInfo
     * @throws
     */
    private String getPhones(MmanUserInfo userInfo) {
        logger.debug(">>>调起共债接口,参数： " + userInfo.getIdNumber());
        Set<String> set = new HashSet<>();
        try {
            Map<String, String> map = new HashMap();
            map.put("id", userInfo.getIdNumber());
            String returnInfo = HttpUtil.getInstance().doPost(PayContents.XJX_GET_PHONES, JSON.toJSONString(map));
            // 自己平台的手机号
            set.add(userInfo.getUserName());
            Map<String, Object> o = (Map<String, Object>) JSONObject.parse(returnInfo);
            if (o != null && "0".equals(String.valueOf(o.get("code")))) {
                Map<String, Object> data = (Map<String, Object>) o.get("data");
                List<Map<String, String>> platformList = (List<Map<String, String>>) data.get("platform_list");
                for (Map<String, String> mArray : platformList) {
                    set.add(mArray.get("reg_mobile"));
                    set.add(mArray.get("bc_mobile"));
                }
            }
        } catch (Exception e) {
            logger.error("调用共债接口获取手机号出错：" + e);
            e.printStackTrace();
        }
        return StringUtils.join(set.toArray(), ",");
    }

    /**
     * 获取补充手机号
     */
    private String getAdditionalPhones(String phone, String phones) {
        Set<String> pSet = new HashSet<>();
        try {
            String[] pArrays = phones.split(",");
            pSet.addAll(Arrays.asList(pArrays));
            pSet.remove(phone);
        } catch (Exception e) {
            logger.error("获取补充手机号出错：" + e);
            e.printStackTrace();
        }
        return StringUtils.join(pSet.toArray(), " , ");
    }


    /**
     * 查询借款人借款及还款详情信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUserRepayInfo")
    public String getUserRepayInfo(HttpServletRequest request) {
        HashMap<String, Object> params = this.getParametersO(request);
        Map<String, Object> result = new HashMap<>();
        String id = params.get("id") + "";
        try {
            if (StringUtils.isEmpty(id)) {
                return null;
            }
            MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderById(id);
            if (order == null) {
                return null;
            }
            // 银行卡信息
            SysUserBankCard userCard = sysUserBankCardService.findUserId(order.getUserId());
            // 还款信息
            CreditLoanPay creditLoanPay = creditLoanPayService.get(order.getPayId());
            // 代扣记录
            List<CollectionWithholdingRecord> withholdList = mmanLoanCollectionRecordService.findWithholdRecord(order.getId());
            if (BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS.equals(order.getStatus())) {
                for (CollectionWithholdingRecord withholdingRecord : withholdList) {
                    withholdingRecord.setLoanUserPhone(MaskCodeUtil.getMaskCode(withholdingRecord.getLoanUserPhone()));
                }
                userCard.setBankCard(MaskCodeUtil.getMaskCode(userCard.getBankCard()));
            }

            // 还款详情
            List<CreditLoanPayDetail> detailList = creditLoanPayDetailService.findPayDetail(order.getPayId());
            // 借款信息
            MmanUserLoan userLoan = mmanUserLoanService.get(order.getLoanId());
            if (userLoan.getPaidMoney().compareTo(BigDecimal.ZERO) <= 0) {
                userLoan.setServiceCharge(BigDecimal.ZERO);
            }

            // 应还总额
            BigDecimal totalAmount = getTotalAmount(userLoan);
            // 剩余应还金额
            BigDecimal paidMoney = creditLoanPay.getRealMoney() == null ? BigDecimal.ZERO : creditLoanPay.getRealMoney();
            BigDecimal remainAmount = totalAmount.subtract(paidMoney);

            result.put("collectionOrder", order);
            result.put("totalAmount", totalAmount);
            result.put("remainAmount", remainAmount);
            result.put("userLoan", userLoan);
            result.put("bankCard", userCard);
            result.put("pay", creditLoanPay);
            result.put("withholdList", withholdList);
            result.put("payDetail", detailList);
            result.put("payMonery", creditLoanPay.getRealMoney());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(result);
    }

    /**
     * 计算订单应还总金额
     *
     * @param userLoan
     * @return
     */
    private BigDecimal getTotalAmount(MmanUserLoan userLoan) {
        BigDecimal loanMoney = userLoan.getLoanMoney() == null ? BigDecimal.ZERO : userLoan.getLoanMoney();
        BigDecimal loanPenalty = userLoan.getLoanPenalty() == null ? BigDecimal.ZERO : userLoan.getLoanPenalty();
        BigDecimal serviceCharge = userLoan.getServiceCharge() == null ? BigDecimal.ZERO : userLoan.getServiceCharge();
        BigDecimal accrual = userLoan.getAccrual() == null ? BigDecimal.ZERO : userLoan.getAccrual();
        return loanMoney.add(loanPenalty).add(serviceCharge).add(accrual);
    }


    /**
     * 催收记录表
     *
     * @param request
     * @param //orderId
     * @param model
     * @return
     */
    /*@RequestMapping("collectionRecordList")
    public String getloanCollectionRecordList(HttpServletRequest request, Model model) {
        List<MmanLoanCollectionRecord> list;
        Map<String, String> params = this.getParameters(request);
        try {
//            list = mmanLoanCollectionRecordService.findListRecord(params.get("id"));
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
    }*/


    /**
     * 跳转扣款页面
     *
     * @param request
     * @param model
     * @return
     */

    @RequestMapping("tokokuan")
    public String tokokuan(HttpServletRequest request, Model model) {
        String url = "mycollectionorder/tokokuan";
        Map<String, String> params = this.getParameters(request);
        if (StringUtils.isNotBlank(params.get("id"))) {
            String loanId = params.get("id").toString();
            MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderById(loanId);
            if (order != null) {
                CreditLoanPay creditLoanPay = creditLoanPayService.get(order.getPayId());
                MmanUserLoan loan = mmanUserLoanDao.get(order.getLoanId());
                //应还金额（本金+利息+滞纳金，欠款金额）
                BigDecimal receivableMoney = creditLoanPay.getReceivableMoney();
                // 借款利息（欠款利息）
                BigDecimal accrual = loan.getAccrual() == null ? BigDecimal.ZERO : loan.getAccrual();
                //欠款本金--后期变为 本金+服务费（大额为本金+利息）
                BigDecimal loanMoney;
                if (loan.getPaidMoney() != null && loan.getPaidMoney().compareTo(BigDecimal.ZERO) == 1) {
                    loanMoney = loan.getPaidMoney().add(accrual);
                } else {
                    loanMoney = loan.getLoanMoney().add(accrual);
                }
                // 滞纳金
                BigDecimal loanPenalty = loan.getLoanPenalty();
                // 已还金额
                BigDecimal realMoney = creditLoanPay.getRealMoney();

                // 剩余应还本金和剩余应还服务费之和（小额）
                BigDecimal remainPrinciple = creditLoanPay.getReceivablePrinciple();
                // 剩余应还罚息
                BigDecimal remainInterest = creditLoanPay.getReceivableInterest();
                // 剩余应还利息
                BigDecimal remainAccrual = creditLoanPay.getRemainAccrual() == null ? BigDecimal.ZERO : creditLoanPay.getRemainAccrual();
                BigDecimal totalPayMoney = remainPrinciple.add(remainInterest).add(remainAccrual);

                model.addAttribute("receivableMoney", receivableMoney); //欠款金额
                model.addAttribute("accrual", accrual);//利息，前端大额显示欠款利息，小额不显示
                model.addAttribute("loanMoney", loanMoney);//欠款本金
                model.addAttribute("loanPenalty", loanPenalty);//滞纳金
                model.addAttribute("realMoney", realMoney);//已还金额
                model.addAttribute("deductibleMoney", totalPayMoney);//剩余待还金额
                model.addAttribute("type", loan.getBorrowingType());


                // 大额代扣跳转到一个专门的页面
                if (loan != null && !Constant.SMALL.equals(loan.getBorrowingType())) {
                    url = "mycollectionorder/toBigkoukuan";
                }

            } else {
                logger.error("mmanLoanCollectionOrderOri is null, loanId : " + params.get("id"));
            }
        }
        model.addAttribute("params", params);
        return url;
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
    @RequestMapping(value = "/withhold-callback")
    @ResponseBody
    public JsonResult dealWithholdResult(String text) {
        JsonResult jsonResult = new JsonResult();
        try {
            logger.info("接收到代扣回调请求参数(小额)： " + text);
            JSONObject obj = JSONObject.parseObject(text);
            String uuid = (String) obj.get("uuid");
            // 根据uuid查询代扣记录表，如果有就更新，没有就返回
            int counts = collectionWithholdingRecordService.getById(uuid);
            if (counts < 1){
                jsonResult.setCode("00");
                return  jsonResult;
            }

            boolean code = (boolean) obj.get("result");
            Object msg = obj.get("msg");
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", uuid);
            if (code) {
                map.put("status", 1); // 代扣成功
            } else {
                map.put("status", 2); // 代扣失败
                map.put("msg", msg); // 代扣失败原因
            }
            map.put("updateDate", new Date());
            int count = collectionWithholdingRecordService.updateWithholdStatus(map);// 更新代扣记录状态
            if (count > 0) {
                obj.put("0", "更新成功！");
            } else {
                obj.put("1", "更新失败！");
            }
            jsonResult.setCode("00");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理代扣回调异常");
        }
        return jsonResult;
    }


    /**
     * 异步处理（更新代扣结果(大额)）,回调接口，用于告知代扣处理结果(代扣走支付中心)
     */
    @RequestMapping(value = "/withhold-callback-big")
    @ResponseBody
    public JsonResult updateWithholdResult(@RequestBody HashMap<String, Object> map) {
        JsonResult jsonResult = new JsonResult();
        try {
            JSONObject obj = JSONObject.parseObject(JSON.toJSONString(map));
            logger.info("接收到代扣回调请求参数(大额)： " + JSON.toJSONString(obj));
            String uuid = (String) obj.get("uuid");
            boolean result = (boolean) obj.get("result");
            Object msg = obj.get("msg");
            HashMap<String, Object> parammMap = new HashMap<>();
            parammMap.put("id", uuid);
            if (result) {
                parammMap.put("status", 1); // 代扣成功
            } else {
                parammMap.put("status", 2); // 代扣失败
                parammMap.put("msg", msg); // 代扣失败原因
            }
            parammMap.put("updateDate", new Date());
            // 更新代扣记录状态
            collectionWithholdingRecordService.updateWithholdStatus(parammMap);
            jsonResult.setCode("00");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理代扣回调异常");
        }
        return jsonResult;
    }


    /**
     * 跳转转派页面
     *
     * @param request
     * @param model
     * @return
     */
    // 第一步 选择转派公司
    @RequestMapping("toOrderDistibute")
    public String toOrderDistibute(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
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
            List<MmanLoanCollectionCompany> companyList = null;
            MmanLoanCollectionCompany mmanLoanCollectionCompany = new MmanLoanCollectionCompany();
            mmanLoanCollectionCompany.setStatus(BackConstant.ON);// 启用的催收公司

            List<BackUserCompanyPermissions> CompanyPermissionsList = backUserService
                    .findCompanyPermissions(backUser.getId());
            if (CompanyPermissionsList.size() == 0) {
                companyList = mmanLoanCollectionCompanyService
                        .getList(mmanLoanCollectionCompany);
            } else {
                List<String> companys = new ArrayList<>();
                for (BackUserCompanyPermissions permission : CompanyPermissionsList) {
                    companys.add(permission.getCompanyId());
                }
                HashMap<String, Object> param = new HashMap<>();
                param.put("ids", companys);
                param.put("status", BackConstant.ON);
                companyList = mmanLoanCollectionCompanyService.getCompanyByIds(param);
            }
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
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("findPerson")
    @ResponseBody
    public void orderDisBackUser(HttpServletRequest request, HttpServletResponse response) {
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
    public String doDispatch(MmanLoanCollectionOrder order, HttpServletRequest request, HttpServletResponse response, Model model) {
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
        Map<String, String> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie.getValue());
            }
        }
        return cookieMap;
    }

    private List<TemplateSms> getMatchMsgsTemplate(MmanLoanCollectionOrder order){
        List<TemplateSms> msgs = new ArrayList<>();
        if (order != null) {
            MmanUserLoan userLoan = mmanUserLoanService.get(order.getLoanId());
            if (SHORT_MESSAGE_YOUMI_CHANNEL_FROM.equals(userLoan.getChannelFrom())){
                msgs = getYoumiAllMsg();
            }else {
                msgs = getCjxjxAllMsg();
            }
        }else {
            logger.error("MmanLoanCollectionOrder 为null,获取getMatchMsgsTemplate出错！");
        }
        return msgs;
    }

    /**
     * 跳转到发送短信页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/gotoSendMsg")
    public String gotoSendMsg(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            String id = params.get("id") + "";
            if (StringUtils.isNotBlank(id)) {
                MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderById(id);
                List<TemplateSms> msgs = getMatchMsgsTemplate(order);

//                int code = RandomUtils.nextInt(0, msgs.size());
//                TemplateSms msg = msgs.get(code);
//                String content = MessageFormat.format(msg.getContenttext(), StringUtils.split(getMsgParam(order), ','));
                // 是否显示更换短信按钮
//                model.addAttribute("refreshMsg", JedisDataClient.get(BackConstant.REDIS_KEY_PREFIX + "refreshMsg"));
//                if (BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS.equals(order.getStatus())) {
//                    content = "该订单已还款完成，请核实！";
//                }
//                model.addAttribute("msgContent", content);
//                model.addAttribute("msgId", msg.getId());
                // 查询当日该订单已发短信条数
                int count = smsUserService.getSendMsgCount(order.getLoanId());
                String msgLimitCountKey = BackConstant.REDIS_KEY_PREFIX + SHORT_MESSAGE_LIMIT_COUNT_REDIS_KEY;
                int msgCountLimit = JedisDataClient.get(msgLimitCountKey) == null ? 0 : Integer.valueOf(JedisDataClient.get(msgLimitCountKey));
                if (msgCountLimit == 0) {
                    // 默认短信发送上限为2条
                    msgCountLimit = 2;
                }
                int remainCount = msgCountLimit - count > 0 ? msgCountLimit - count : 0;
                model.addAttribute("remainMsgCount", remainCount);
                model.addAttribute("msgCountLimit", msgCountLimit);
                model.addAttribute("msgs", msgs);
                model.addAttribute("orderId", id);
                model.addAttribute("phoneNumber", order.getLoanUserPhone());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("params", params);
        return "mycollectionorder/smsSendDetail";
    }

    /**
     * 查询所有cjxjx的短信模板
     *
     * @return
     */
    private List<TemplateSms> getCjxjxAllMsg() {
        String msgKey = PayContents.MERCHANT_NUMBER+"_"+SHORT_MESSAGE_LIST_REDIS_KEY;
        List<TemplateSms> msgs = JedisDataClient.getList(BackConstant.REDIS_KEY_PREFIX,msgKey);
        if (CollectionUtils.isEmpty(msgs)) {
            msgs = templateSmsDao.getMsgs();
            JedisDataClient.setList(BackConstant.REDIS_KEY_PREFIX, msgKey, msgs, 60 * 60);
        }
        return msgs;
    }

    /**
     * 查询所有有米管家的短信模板
     *
     * @return
     */
    private List<TemplateSms> getYoumiAllMsg() {
        String msgKey = SHORT_MESSAGE_YOUMI_CHANNEL_FROM_REDIS_KEY +"_" + PayContents.MERCHANT_NUMBER +"_"+SHORT_MESSAGE_LIST_REDIS_KEY;
        List<TemplateSms> msgs = JedisDataClient.getList(BackConstant.REDIS_KEY_PREFIX,msgKey);
        if (CollectionUtils.isEmpty(msgs)) {
            msgs = templateSmsDao.getYoumiAllMsg();
            JedisDataClient.setList(BackConstant.REDIS_KEY_PREFIX, msgKey, msgs, 60 * 60);
        }
        return msgs;
    }

    /**
     * 拼接短信模板中需要的参数
     *
     * @param order
     * @return
     */
    private String getMsgParam(MmanLoanCollectionOrder order,String msgId) {
        if (order == null) {
            return null;
        }
        MmanUserInfo userInfo = mmanUserInfoService.getUserInfoById(order.getUserId());
        String merchantNo = getMerchantNoByOrder(order);
        String merchantName = MerchantNoUtils.getMerchantName2(merchantNo);
        StringBuilder msgParam = new StringBuilder();
        if("notification-86".equals(msgId)
                || "notification-87".equals(msgId)
                || "notification-88".equals(msgId)){
            msgParam.append(merchantName).append(",");
            if (StringUtils.isNotEmpty(userInfo.getUserSex())) {
                if ("男".equals(userInfo.getUserSex())) {
                    msgParam.append(order.getLoanUserName() + "先生");
                } else {
                    msgParam.append(order.getLoanUserName() + "女士");
                }
            }
            msgParam.append(",");
            msgParam.append(merchantName).append(",");
            CreditLoanPay pay = creditLoanPayService.findByLoanId(order.getLoanId());
            BigDecimal remainMoney = pay.getReceivableMoney().subtract(pay.getRealMoney());
            msgParam.append(order.getOverdueDays()).append(",").append(remainMoney);
        }
        if ("notification-89".equals(msgId)
                || "notification-90".equals(msgId)
                || "notification-91".equals(msgId)
                || "notification-92".equals(msgId)){
            msgParam.append(merchantName).append(",");
            msgParam.append(merchantName).append(",");
            msgParam.append(merchantName).append(",");
        }
        return msgParam.toString();
    }

    //拼接发送短信的参数（传值给短信系统，不包含签名参数）
    private String getMsgParam2(MmanLoanCollectionOrder order,String msgId) {
        if (order == null) {
            return null;
        }
        MmanUserInfo userInfo = mmanUserInfoService.getUserInfoById(order.getUserId());
        String merchantNo = getMerchantNoByOrder(order);
        String merchantName = MerchantNoUtils.getMerchantName2(merchantNo);
        StringBuilder msgParam = new StringBuilder();
        if("notification-86".equals(msgId)
                || "notification-87".equals(msgId)
                || "notification-88".equals(msgId)){
            if (StringUtils.isNotEmpty(userInfo.getUserSex())) {
                if ("男".equals(userInfo.getUserSex())) {
                    msgParam.append(order.getLoanUserName() + "先生");
                } else {
                    msgParam.append(order.getLoanUserName() + "女士");
                }
            }
            msgParam.append(",");
            msgParam.append(merchantName).append(",");
            CreditLoanPay pay = creditLoanPayService.findByLoanId(order.getLoanId());
            BigDecimal remainMoney = pay.getReceivableMoney().subtract(pay.getRealMoney());
            msgParam.append(order.getOverdueDays()).append(",").append(remainMoney);
        }
        if ("notification-89".equals(msgId)
                || "notification-90".equals(msgId)
                || "notification-91".equals(msgId)
                || "notification-92".equals(msgId)){
            msgParam.append(merchantName).append(",");
            msgParam.append(merchantName).append(",");
            msgParam.append(merchantName).append(",");
        }
        return msgParam.toString();
    }
    /**
     * 获取订单对应的商户名
     *
     * @param order
     * @return
     */
    private String getMerchantName(MmanLoanCollectionOrder order) {
        // 获取所有商户信息
        Map<String, String> merchantMap = mmanLoanCollectionOrderService.getMerchantMap();
        String merchantNanme = null;
        MmanUserLoan mmanUserLoan = mmanUserLoanService.get(order.getLoanId());
        for (Map.Entry<String,String> map : merchantMap.entrySet()) {
            if (map.getKey().equals(mmanUserLoan.getChannelFrom())) {
                merchantNanme = map.getValue();
                break;
            }
        }
        return merchantNanme;
    }

    /**
     * 发送催收短信
     *
     * @param //mobiles        需要发送短信的手机号
     * @param //smsContent     短信内容
     * @param //isSendSmsToAll 是否发送给所有人
     * @return
     */
    @RequestMapping("/sendMsg")
    @ResponseBody
    public ServiceResult SendSms(HttpServletRequest request) {
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            String orderId = params.get("orderId") + "";
            if (StringUtils.isBlank(orderId)) {
                return new ServiceResult("-1", "订单异常！");
            }
            MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderById(orderId);
            if (order == null) {
                logger.error("订单为null,loanId : " + orderId);
                return new ServiceResult("-2", "订单异常！");
            }

            if (BackConstant.XJX_COLLECTION_ORDER_STATE_SUCCESS.equals(order.getStatus())) {
                logger.error("催收成功订单不能发送催收短信！" + orderId);
                return new ServiceResult("-3", "催收成功订单不能发送催收短信！");
            }

            String mobile;
            String collectionRecordId = params.get("collectionRecordId") == null ? null : params.get("collectionRecordId").toString();
            if (StringUtils.isNotEmpty(collectionRecordId)) {
                // 查询催收记录
                MmanLoanCollectionRecord loanCollectionRecord = mmanLoanCollectionRecordDao.getOneCollectionRecordById(collectionRecordId);
                mobile = loanCollectionRecord.getContactPhone();
            } else {
                mobile = request.getParameter("phoneNumber") == null ? "" : request.getParameter("phoneNumber").trim();
            }
            if (StringUtils.isEmpty(mobile)) {
                return new ServiceResult("-4", "手机号不能为空！");
            }
            Matcher matcher = MOBILE_PATTERN.matcher(mobile);
            if (!matcher.matches()) {
                return new ServiceResult("-5", "手机号异常！");
            }
            String msgCode = params.get("msgId") + "";
            if (StringUtils.isEmpty(msgCode) || "0".equals(msgCode)) {
                return new ServiceResult("-6", "请选择正确的短信模板！");
            }
            String msgParam = null;
            if(SHORT_MESSAGE_YOUMI_CHANNEL_FROM.equals(getChannelFrom(order))){
                msgParam = getYoumiMsgParam(order);
            }else {
                msgParam = getMsgParam2(order,msgCode);
            }
            if (msgParam == null) {
                return new ServiceResult("-7", "短信参数异常！");
            }
            // 查询出该订单当天已发短信的次数
            int count = smsUserService.getSendMsgCount(order.getLoanId());
            String msgLimitCountKey = BackConstant.REDIS_KEY_PREFIX + SHORT_MESSAGE_LIMIT_COUNT_REDIS_KEY;
            int msgCountLimit = JedisDataClient.get(msgLimitCountKey) == null ? 0 : Integer.valueOf(JedisDataClient.get(msgLimitCountKey));
            if (msgCountLimit == 0) {
                // 默认短信发送上限为2条
                msgCountLimit = 2;
            }
            if (msgCountLimit <= count) {
                return new ServiceResult("-8", "今日该订单发送短信已达上限" + (msgCountLimit) + "条！");
            }
            logger.error("发送催收短信，参数：mobile:"+ mobile + "msgParam:" + JSONObject.toJSONString(msgParam) + "msgCode:" + msgCode);
            boolean smsResult = false;
//            if (SHORT_MESSAGE_YOUMI_CHANNEL_FROM.equals(getChannelFrom(order))){
//                String merchantNo = PayContents.MERCHANT_NUMBER;
//                smsResult = SmsSendUtil.sendSmsYoumi(mobile, msgParam, msgCode,merchantNo);
//            }else {
//                String merchantNo = getMerchantNoByOrder(order);
//                smsResult = SmsSendUtil.sendSmsNew(mobile, msgParam, msgCode,merchantNo);
//            }
            String merchantNo = getMerchantNoByOrder(order);
            smsResult = SmsSendUtil.sendSmsNew(mobile, msgParam, msgCode,merchantNo);
            if (smsResult) {
                // 插入短信记录
                insertMsg(mobile, order, msgCode, request);
                return new ServiceResult("200", "发送短信成功！");
            } else {
                return new ServiceResult("-9", "发送失败！");
            }
        } catch (Exception e) {
            logger.error("发送短信失败，订单id：" + params.get("id"));
            e.printStackTrace();
        }
        return null;
    }

    private String getMerchantNoByOrder(MmanLoanCollectionOrder order) {
        String merchantNo = null;
        if (null == order){
            return merchantNo;
        }
        String loanId = order.getLoanId();
//        String merchantNo = dataDao.getMerchantNumberByLoanId(loanId);//从业务库中查询
        merchantNo = mmanUserLoanService.getMerchantNoByLoanId(loanId);//从催收库中查询
        return merchantNo;
    }

    /**
     * 处理相应数据
     *
     * @param response
     * @param model
     * @param result
     * @param params
     * @return
     */
    private ServiceResult getServiceResult(HttpServletResponse response, Model model, JsonResult result, HashMap<String, Object> params) {
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
//                insertMsg(mobiles, orderId, userName, smsContent, request);
                // 每个数据包发送后等待一秒 云峰短信有频率限制
                Thread.sleep(1000);
            }
        } else {
            result.setMsg("参数不正确");
        }
    }

    /**
     * 插入发送的短信记录
     *
     * @param mobiles
     * @param
     * @param
     * @param request
     */
    private void insertMsg(String mobiles, MmanLoanCollectionOrder order, String msgCode, HttpServletRequest request) {
        SmsUser msg = new SmsUser();
        BackUser user = this.loginAdminUser(request);
        if (user == null) {
            return;
        }
        msg.setSendUserId(user.getUuid());
        msg.setAddTime(new Date());
        msg.setLoanOrderId(order.getLoanId());
        List<TemplateSms> msgs = getMatchMsgsTemplate(order);
        TemplateSms sms = null;
        for (TemplateSms s : msgs) {
            if (s.getId().equals(msgCode)) {
                sms = s;
            }
        }
        String msgContent = MessageFormat.format(sms.getContenttext(), StringUtils.split(getMsgParam(order,sms.getId()), ','));
        msg.setSmsContent(msgContent);
        msg.setUserPhone(mobiles);
        msg.setUserName(order.getLoanUserName() == null ? mobiles : order.getLoanUserName());
        this.smsUserService.insert(msg);
    }

    /**
     * 更换短信内容
     *
     * @return
     */
    @RequestMapping("/refreshMsg")
    @ResponseBody
    public String refreshMsg(HttpServletRequest request) {
        Map<String, String> params = this.getParameters(request);
        String id = params.get("id") + "";
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(id)) {
            MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderById(id);
            if(null == order) {
                logger.error("refreshMsg抛异常问题排查，催收订单id：" + id);
            }
            List<TemplateSms> msgs = getMatchMsgsTemplate(order);
            TemplateSms templateSms = null;
            String msgId = params.get("msgId") + "";
            for (TemplateSms msg : msgs) {
                if (msg.getId().equals(msgId)) {
                    templateSms = msg;
                    break;
                }
            }
            String content = null;
            if(SHORT_MESSAGE_YOUMI_CHANNEL_FROM.equals(getChannelFrom(order))){
                content = MessageFormat.format(templateSms.getContenttext(), StringUtils.split(getYoumiMsgParam(order), ','));
            }else {
                content = MessageFormat.format(templateSms.getContenttext(), StringUtils.split(getMsgParam(order,templateSms.getId()), ','));
            }
            map.put("msgContent", content);
            map.put("msgId", templateSms.getId());
        }
        return JSON.toJSONString(map);
    }

    /**
     * 拼接有  米渠道来源  短信模板中需要的参数
     *
     * @param order
     * @return
     */
    private String getYoumiMsgParam(MmanLoanCollectionOrder order) {
        if (order == null) {
            return null;
        }
        String loanId = order.getLoanId();
//        String merchantNo = dataDao.getMerchantNumberByLoanId(loanId);//从业务库中查询
        String merchantNo = mmanUserLoanService.getMerchantNoByLoanId(loanId);
        String merchantName = MerchantNoUtils.getMerchantName2(merchantNo);
        StringBuilder msgParam = new StringBuilder();
        msgParam.append(merchantName).append(",").append(merchantName);
        return msgParam.toString();
    }

    //获取渠道来源
    private String getChannelFrom(MmanLoanCollectionOrder order) {
        if (order != null) {
            MmanUserLoan userLoan = mmanUserLoanService.get(order.getLoanId());
            if (SHORT_MESSAGE_YOUMI_CHANNEL_FROM.equals(userLoan.getChannelFrom())) {
                return "ymgj";
            } else {
                return userLoan.getChannelFrom();
            }
        }else {
            logger.error("MmanLoanCollectionOrder 为null,获取渠道来源getChannelFrom出错！"+ JSONObject.toJSONString(order));
            return null;
        }
    }

    /**
     * 分期还款计算
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("installmentPay")
    public String getInstallmentPay(HttpServletRequest request, Model model) {
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
        List<InstallmentPayInfoVo> list = new ArrayList<>();
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
     * @return
     */
    @RequestMapping("insertInstallmentPayRecord")
    public void insertInstallmentPayRecord(HttpServletRequest request, HttpServletResponse response) {
        String url = null;
        String erroMsg = null;
        List<InstallmentPayRecord> installmentPayRecordList = null;
        Map<String, Object> map = new HashMap<>();
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
    public void fqWithholding(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
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
    private String gotoGetContactInfoPage(HttpServletRequest request, Model model) {
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
    public String toReductionPage(HttpServletRequest request, Model model) {

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
    public void doReduction(HttpServletRequest request, HttpServletResponse response) {
        JsonResult result = new JsonResult("-1", "申请减免失败");
        HashMap<String, Object> params = this.getParametersO(request);

        MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderById(params.get("id").toString());
        if ("6".equals(order.getStatus())) {
            result.setMsg("该订单已经发起减免了，不能重复申请！");
        } else {
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
        }
        SpringUtils.renderDwzResult(response, "0".equals(result.getCode()),
                result.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
                params.get("parentId").toString());
    }


    @RequestMapping("getorderPage")
    public String getorderPage(HttpServletRequest request, Model model) {
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
    public String testKh(HttpServletRequest request) {
        try {
            Date date = new Date();
            HashMap<String, Object> params = new HashMap<>();
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
    public String testGl(HttpServletRequest request) {
        try {
            Date date = new Date();
            HashMap<String, Object> params = new HashMap<>();
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
    public String testCj() {
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


    private Map<String, Object> getCommunicationSituationsMap(List<CommunicationSituation> communicationSituations) {
        Map<String, Object> map = new HashMap<>(16);
        if (CollectionUtils.isNotEmpty(communicationSituations)) {
            for (CommunicationSituation situation : communicationSituations) {
                map.put(situation.getId().toString(), situation.getCommunicationLabel());
            }
        }
        return map;
    }


    private Map<String, Object> getOverdueLevelMap(List<SysDict> overdueLevel) {
        Map<String, Object> overdueLevelMap = new LinkedHashMap<>(16);
        for (SysDict sysDict : overdueLevel) {
            overdueLevelMap.put(sysDict.getValue(), sysDict.getLabel());
        }
        return overdueLevelMap;
    }

    private List<SysDict> getAllOverdueLevel() {
        List<SysDict> overdueLevel = JedisDataClient.getList(BackConstant.REDIS_KEY_PREFIX, "overdueLevel");
        if (CollectionUtils.isEmpty(overdueLevel)) {
            overdueLevel = sysDictService.findDictByType(DICTIONARY_TYPE_OVERDUE_LEVEL);
            JedisDataClient.setList(BackConstant.REDIS_KEY_PREFIX, "overdueLevel", overdueLevel, 60 * 60 * 24);
        }
        return overdueLevel;
    }


    private Map<String, Object> getOrderStatusMap() {
        List<SysDict> orderStatus = JedisDataClient.getList(BackConstant.REDIS_KEY_PREFIX, "orderStatus");
        if (CollectionUtils.isEmpty(orderStatus)) {
            orderStatus = sysDictService.findDictByType(DICTIONARY_TYPE_ORDER_STATE);
            JedisDataClient.setList(BackConstant.REDIS_KEY_PREFIX, "orderStatus", orderStatus, 60 * 60 * 24);
        }
        Map<String, Object> orderStatusMap = new HashMap<>(8);
        for (SysDict sysDict : orderStatus) {
            orderStatusMap.put(sysDict.getValue(), sysDict.getLabel());
        }
        return orderStatusMap;
    }


    @RequestMapping("/get-user-ecommerce-info")
    @ResponseBody
    public List<EcommerceInfo> getEconmerceInfo(HttpServletRequest request) {
        HashMap<String, Object> map = getParametersO(request);
        try {
            logger.info("获取用户电商信息,手机号: " + map.get("phone"));
            List<EcommerceInfo> list = mmanUserInfoService.getEconmerceInfo(map);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取用户电商信息失败 " + map.toString());
        }
        return null;
    }

    @RequestMapping("qrCode")
    public String qrCode(HttpServletRequest request, Model model) {

        HashMap<String, Object> params = this.getParametersO(request);
        String id = params.get("id").toString();
        MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderById(id);
        String assetRepaymentId = order.getPayId();

        model.addAttribute("payId", assetRepaymentId);
        model.addAttribute("params", params);
        return "mycollectionorder/qrCodePage";

    }

    @RequestMapping("getQrCode")
    public void getQrCode(HttpServletRequest request,HttpServletResponse response,String payId,String payMoney) {

        String assetRepaymentId ="";
        int withholdAmount = 0;
        if(StringUtils.isNotBlank(payMoney)){
            assetRepaymentId = payId;
            BigDecimal money = new BigDecimal(payMoney);
            withholdAmount = money.multiply(new BigDecimal(100)).intValue();
        }else {
            CreditLoanPay creditLoanPay = creditLoanPayService.get(payId);
            int receivableMoney = creditLoanPay.getReceivableMoney().multiply(new BigDecimal(100)).intValue();
            int realMoney = creditLoanPay.getRealMoney().multiply(new BigDecimal(100)).intValue();
            assetRepaymentId = creditLoanPay.getId();
            withholdAmount = receivableMoney - realMoney;
        }
//        assetRepaymentId= "154514530";
//        withholdAmount = 100;
        String url = PayContents.QRCODE_URL + "?assetRepaymentId=" + assetRepaymentId+"&withholdAmount="+withholdAmount;
        try {
            URL urls = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(3000);
            connection.connect();
            int statusCode = connection.getResponseCode();
            OutputStream outputStream = response.getOutputStream();
            if (statusCode != HttpURLConnection.HTTP_OK) {/* 4 判断访问的状态码 */
                return;
            }else {
                InputStream inputStream = connection.getInputStream();
                int len = 0;
                byte buffer[] = new byte[1024 * 10];
                while((len=inputStream.read(buffer))>0){
                    outputStream.write(buffer, 0, len);
                }
                inputStream.close();
            }
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            logger.error("getQrCode-exception", e);
        }
    }


    @RequestMapping("jianmianQrCode")
    public String jianmianQrCode(HttpServletRequest request, Model model) {

        HashMap<String, Object> params = this.getParametersO(request);
        String id = params.get("id").toString();
        MmanLoanCollectionOrder order = mmanLoanCollectionOrderService.getOrderById(id);
        String assetRepaymentId = order.getPayId();
        CreditLoanPay creditLoanPay = creditLoanPayService.get(assetRepaymentId);
        BigDecimal remainMoney = creditLoanPay.getReceivableMoney().subtract(creditLoanPay.getRealMoney());
        BigDecimal minMoney =creditLoanPay.getReceivablePrinciple();
        model.addAttribute("minMoney", minMoney);
        model.addAttribute("creditLoanPay", creditLoanPay);
        model.addAttribute("remainMoney", remainMoney);
        model.addAttribute("payId", assetRepaymentId);
        model.addAttribute("params", params);
        return "mycollectionorder/jianmianQrCodePage";

    }


}