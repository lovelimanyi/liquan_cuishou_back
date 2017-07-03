package com.info.back.service;

import com.info.back.dao.*;
import com.info.back.result.JsonResult;
import com.info.back.utils.IdGen;
import com.info.config.PayContents;
import com.info.constant.Constant;
import com.info.web.pojo.AuditCenter;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.util.HttpUtil;
import com.info.web.util.PageConfig;
import com.info.web.util.encrypt.AESUtil;
import com.info.web.util.encrypt.MD5coding;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuditCenterService implements IAuditCenterService {
    private static Logger logger = LoggerFactory.getLogger(AuditCenterService.class);
    @Autowired
    private IAuditCenterDao auditCenterDao;
    @Autowired
    private IPaginationDao paginationDao;
    //订单
    @Autowired
    private IMmanLoanCollectionOrderDao manLoanCollectionOrderDao;
    @Autowired
    private IMman_loan_collection_orderdeductionService collection_orderdeductionService;
    @Autowired
    private IMmanUserLoanService iMmanUserLoanService;
    @Autowired
    private IMmanUserLoanDao iMmanUserLoanDao;
    @Autowired
    private ICreditLoanPayDao iCreditLoanPayDao;
    private MmanLoanCollectionOrder order;

    @Override
    public JsonResult svueAuditCenter(Map<String, String> params) {
        JsonResult result = new JsonResult("-1", "操作失败");
        MmanLoanCollectionOrder mmanLoanCollectionOrderOri = manLoanCollectionOrderDao.getOrderById(params.get("id"));
        if(mmanLoanCollectionOrderOri == null){
            result.setMsg("该条审核订单不存在,请核实");
            return result;
        }
        AuditCenter auditCenter = new AuditCenter();
        if (StringUtils.isNotBlank(params.get("id"))) {
            auditCenter.setOrderid(mmanLoanCollectionOrderOri.getId());
            auditCenter.setLoanUserId(mmanLoanCollectionOrderOri.getUserId());

        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id",params.get("id"));
        param.put("type",params.get("type"));
        int count = auditCenterDao.findAuditStatusByOrderId(param);
        if(count != 0){
            result.setMsg("已提交过该条审核,请核实");
            return result;
        }
        auditCenter.setId(IdGen.uuid());
        auditCenter.setLoanId(mmanLoanCollectionOrderOri.getLoanId());
        auditCenter.setCreatetime(new Date());
        auditCenter.setNote(params.get("note"));
        auditCenter.setType(params.get("type"));
        auditCenter.setOperationUserId(params.get("operationUserId"));
        auditCenter.setRemark(params.get("remark"));
        auditCenter.setStatus("0");
        int cont = auditCenterDao.saveUpdate(auditCenter);
        if (cont > 0) {
//			HashMap<String, Object> xjxparams =new HashMap<String, Object>();
//			params.put("id",auditCenter.getLoanUserId());
//			params.put("collection_advise", auditCenter.getNote());
//			String collectionAdviseUpdateResult = HttpUtil.dopostMap(PayContents.COLLECTION_ADVISE_UPDATE_URL, params);
            result.setMsg("操作成功");
            result.setCode("0");
        }
        return result;
    }
    @Override
    public PageConfig<AuditCenter> findPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "AuditCenter");
        PageConfig<AuditCenter> pageConfig = new PageConfig<AuditCenter>();
        pageConfig = paginationDao.findPage("findAll", "findAllCount", params, null);
        return pageConfig;
    }
    /**
     * 审核处理
     */
    @Override
    public JsonResult updateAuditCenter(Map<String, String> params) {
        JsonResult result = new JsonResult("-1", "审核失败了");

        String ids = params.get("id");
        String[] auditIds = null;
        if(StringUtils.isNotBlank(ids)){
            auditIds= ids.split(",");
        }else{
            result.setMsg("请至少勾选一条数据！");
            return result;
        }
        if (auditIds.length > 200) {
            result.setMsg("勾选的条数不能超过200条！");
        }else{
            AuditCenter auditCenter = null;
            //判断所选的是否申请类型一致且审核状态为审核中
            for(int i = 0;i < auditIds.length;i++){
                AuditCenter auditCenter0 = auditCenterDao.findAuditId(auditIds[0]);
                auditCenter = auditCenterDao.findAuditId(auditIds[i]);
                if(!auditCenter0.getType().equals(auditCenter.getType()) || !auditCenter.getStatus().equals(Constant.AUDIT_CHECKING)){
                    //申请类型不一致或审核状态非审核中-->返回
                    result.setMsg("请选择申请类型一致且状态为审核中的");
                    return result;
                }
            }
            //所选的申请类型一致且审核状态为审核中-->逐条审核
            for(String auditId : auditIds){
                HashMap<String ,Object > map = new HashMap<>();
                auditCenter = auditCenterDao.findAuditId(auditId);
                map.put("auditId",auditId);
                map.put("orderId",auditCenter.getOrderid());
                map.put("auditTime",new Date());
                map.put("updateTime",new Date());
                map.put("status",params.get("status"));
                try {
                    //add by yyf 在审核中时，客户还款，只能等待审核失效
                    MmanLoanCollectionOrder order = manLoanCollectionOrderDao.getOrderById(auditCenter.getOrderid());
                    if (Constant.STATUS_OVERDUE_FOUR.equals(order.getStatus())){
                        result.setCode("-1");
                        result.setMsg(order.getLoanId()+"该借款编号的订单在审核前已经完成，请等待审核失效！");
                        return  result;
                    }
                    if (auditCenter.getType().equals(Constant.AUDIT_TYPE_REDUCTION)){ //申请类型--减免
                        if (params.get("status").equals(Constant.AUDIT_REFUSE)){ // 审核状态--3拒绝
                            map.put("orderStatus",Constant.STATUS_OVERDUE_EIGHT);
                            auditCenterDao.updateAuditStatus(map);
                            manLoanCollectionOrderDao.updateReductionOrder(map);
                            result.setCode("0");
                            result.setMsg("审核已拒绝");
                        }else{ //审核状态  5通过不计入考核  或   2通过
                            BigDecimal reductionMoneyBig = auditCenter.getReductionMoney();
                            BigDecimal big100 = new BigDecimal(100);
                            int reductionMoney = reductionMoneyBig.multiply(big100).intValue();//减免滞纳金

                            String sign = MD5coding.MD5(AESUtil.encrypt(auditCenter.getLoanUserId()+auditCenter.getPayId()+reductionMoney+auditCenter.getId(),PayContents.XJX_WITHHOLDING_NOTIFY_KEY));
                            //调用减免http接口
                            String withholdPostUrl=PayContents.XJX_JIANMIAN_URL+"/"+auditCenter.getLoanUserId()+"/"+auditCenter.getPayId()+"/"+reductionMoney+"/"+auditCenter.getId()+"/"+sign;
                            String xjxWithholdingStr = HttpUtil.getHttpMess(withholdPostUrl, "", "POST", "UTF-8");
                            if(StringUtils.isNotBlank(xjxWithholdingStr)) {
                                JSONObject jos = new JSONObject().fromObject(xjxWithholdingStr);
                                logger.info("返回还款结果信息jos转换"+jos);
                                if("0".equals("0")){ //接口返回成功 -- 本地update审核表，订单表
                                    if(params.get("status").equals(Constant.AUDIT_PASS)){ //减免计入考核
                                        map.put("reductionMoney",auditCenter.getReductionMoney()); //减免金额
                                    }else {
                                        map.put("reductionMoney",0);
                                    }
                                    auditCenterDao.updateAuditStatus(map);
                                    manLoanCollectionOrderDao.updateReductionOrder(map);
                                    result.setCode("0");
                                    result.setMsg("审核已通过");
                                    logger.error("0","减免成功！");
                                }else if("-101".equals(jos.get("code"))){
                                    result.setMsg("参数错误 减免失败！！！");
                                }else if (!"-100".equals(jos.get("code"))) {
                                    result.setMsg("系统错误！！！");
                                }
                            }
                        }
                    }else { //申请类型--聚信立，详情
                        if (params.get("status").equals(Constant.AUDIT_REFUSE)){ // 审核状态--3拒绝
                            map.put("orderStatus",Constant.STATUS_OVERDUE_EIGHT);
                            auditCenterDao.updateAuditStatus(map);
                        }else{ //审核状态--同意
                            params.put("id", auditCenter.getId());
                            auditCenterDao.updateStatus(params);
                            HashMap<String, String> auditMap = new HashMap<String, String>();
                            auditMap.put("id", auditCenter.getOrderid());
                            if ("2".equals(auditCenter.getType())) {
                                auditMap.put("csstatus", params.get("status"));
                                manLoanCollectionOrderDao.updateAuditStatus(auditMap);
                                if ("2".equals(auditCenter.getStatus())) {
                                    HashMap<String, Object> xjxparams = new HashMap<String, Object>();
                                    params.put("id", auditCenter.getLoanUserId());
                                    params.put("collection_advise", auditCenter.getNote());
                                    String resultT = HttpUtil.dopostMap(PayContents.COLLECTION_ADVISE_UPDATE_URL, params);
                                    logger.error("updateAuditStatus COLLECTION_ADVISE_UPDATE_URL=" + PayContents.COLLECTION_ADVISE_UPDATE_URL + " params=" + params.toString() + " resultT=" + resultT);
                                }
                            } else if ("1".equals(auditCenter.getType())) {
                                auditMap.put("jxlStatus", params.get("status"));
                                manLoanCollectionOrderDao.updateAuditStatus(auditMap);
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error("减免审核："+auditCenter.getLoanId()+"申请类型type:"+auditCenter.getType()+"审核状态status:"+params.get("stauts"),e);
                }
            }
        }
        return result;
    }

    @Override
    public JsonResult saveorderdeduction(Map<String, Object> params) throws ParseException {
        return null;
    }

    @Override
    public PageConfig<AuditCenter> findAllPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "AuditCenter");
        PageConfig<AuditCenter> pageConfig = new PageConfig<AuditCenter>();
        pageConfig = paginationDao.findPage("find", "findCount", params, null);
        return pageConfig;
    }
    @Override
    public void updateSysStatus(Map<String, String> params) {
        auditCenterDao.updateSysStatus(params);
    }

    @Override
    public int findAuditStatus(Map<String, Object> params) {
        return auditCenterDao.findAuditStatus(params);
    }

    @Override
    public int getAuditChecking(HashMap<String, Object> params) {
        params.put("loanId",params.get("loanId").toString());
        params.put("type",Constant.AUDIT_TYPE_REDUCTION);//type 3 减免
        params.put("status",Constant.AUDIT_CHECKING );//status 0 审核中
        return auditCenterDao.getAuditChecking(params);
    }
    /**
     * 申请减免审核-->保存至审核中心
     */
    @Override
    public JsonResult saveAuditReduction(HashMap<String, Object> params,HttpServletRequest request) {
        JsonResult result = new JsonResult("-1", "申请减免失败");
        BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER);
        params.put("backUserId",backUser.getId());
        params.put("backUserName", backUser.getUserName());
        try {
            MmanLoanCollectionOrder order = manLoanCollectionOrderDao.getOrderById(params.get("id").toString());
            AuditCenter auditCenter = new AuditCenter();
            auditCenter.setId(IdGen.uuid());
            auditCenter.setOperationUserId(params.get("backUserId").toString());
            auditCenter.setLoanUserId(order.getUserId());
            auditCenter.setLoanId(order.getLoanId());
            auditCenter.setPayId(order.getPayId());
            auditCenter.setOrderid(order.getId());
            auditCenter.setType(Constant.AUDIT_TYPE_REDUCTION);//type 3 减免
            auditCenter.setStatus(Constant.AUDIT_CHECKING);//status 0 审核中
            auditCenter.setCreatetime(new Date());
            auditCenter.setRemark(params.get("reductionRemark").toString());
            auditCenter.setLoanUserName(order.getLoanUserName());
            auditCenter.setLoanUserPhone(order.getLoanUserPhone());
            auditCenter.setReductionMoney(new BigDecimal(params.get("reductionMoney").toString()).setScale(2,BigDecimal.ROUND_HALF_UP));
            auditCenter.setOrderStatus(order.getStatus());
            int cont = auditCenterDao.saveNotNull(auditCenter);
            if (cont > 0) {
                order.setStatus(Constant.STATUS_OVERDUE_SIX);
                manLoanCollectionOrderDao.updateJmStatus(order);//将订单status置为 减免审核中
                result.setMsg("申请成功！等待审核");
                result.setCode("0");
            }
        }catch (Exception e){
            result.setMsg("申请减免异常");
            e.printStackTrace();
        }
        return result;
    }

}
