package com.info.back.service;

import com.info.back.controller.BaseController;
import com.info.back.dao.IPaginationDao;
import com.info.back.dao.IXiaoShouOrderDao;
import com.info.back.exception.BizException;
import com.info.back.utils.BackConstant;
import com.info.back.utils.ExcelReader;
import com.info.back.utils.MerchantNoUtils;
import com.info.back.utils.StringUtils;
import com.info.constant.Constant;
import com.info.web.pojo.XiaoShouOrder;
import com.info.web.util.PageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class XiaoShouService implements IXiaoShouService{
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(XiaoShouService.class);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    IXiaoShouOrderDao xiaoShouOrderDao;
    @Autowired
    private IPaginationDao paginationDao;

    @Override
    public Integer importExcel(MultipartFile multipartFile,String orderFrom)  throws BizException {
        logger.info("解析excel ");
        Integer count = 0;
        if (BackConstant.ORDER_FROM_XJX.equals(orderFrom)){
            // 解析Excel
            List<Map<String, Object>> paramList = getExcelInfo(multipartFile);
            //导入操作前，删除xiao_shou_info表原来表所有数据(同一个excel操作两次导入)
            xiaoShouOrderDao.deleteAllOrder();
            count = xiaoShouOrderDao.importExcel(paramList);
        }
        if (BackConstant.ORDER_FROM_YMGJ.equals(orderFrom)){
            // 解析Excel
            List<Map<String, Object>> paramList = getExcelInfoFromYoumi(multipartFile);
            //导入操作前，删除xiao_shou_info表原来表所有数据(同一个excel操作两次导入)
            xiaoShouOrderDao.deleteAllOrderToYoumi();
            count = xiaoShouOrderDao.importExcelFromYmgj(paramList);
        }
        setBatchId(orderFrom);
        return count;
    }

    private void setBatchId(String orderFrom) {
        if (BackConstant.ORDER_FROM_XJX.equals(orderFrom)){
            Long maxBatchId = xiaoShouOrderDao.getMaxBatchId();
            if (null == maxBatchId){
                maxBatchId = 0L + 1L;
            }else {
                maxBatchId = maxBatchId + 1;
            }
            xiaoShouOrderDao.setBatchId(maxBatchId);
        }
        if (BackConstant.ORDER_FROM_YMGJ.equals(orderFrom)){
            Long maxBatchId = xiaoShouOrderDao.getMaxBatchIdFromYmgj();
            if (null == maxBatchId){
                maxBatchId = 0L + 1L;
            }else {
                maxBatchId = maxBatchId + 1;
            }
            xiaoShouOrderDao.setBatchIdFromYmgj(maxBatchId);
        }
    }

    @Override
    public PageConfig<XiaoShouOrder> findAllUserPage(HashMap<String, Object> map) {
        map.put(Constant.NAME_SPACE, "XiaoShouOrder");
        return paginationDao.findPage("findAllUser","findAllUserCount",map,null);
    }
    @Override
    public PageConfig<XiaoShouOrder> findAllUserPageFromYoumi(HashMap<String, Object> map) {
        map.put(Constant.NAME_SPACE, "XiaoShouOrder");
        return paginationDao.findPage("findAllUserFromYoumi","findAllUserCountFromYoumi",map,null);
    }

    private List<Map<String,Object>> getExcelInfo(MultipartFile multipartFile) throws BizException {
        ExcelReader readExcel = new ExcelReader();
        try {
            readExcel.open(multipartFile.getInputStream());
            // 设置读取索引为0的工作表
            readExcel.setSheetNum(0);
        } catch (IOException e) {
            logger.error("读取excel失败{} ",e);
            throw new BizException("读取excel失败");
        }
        // 总行数
        int count = readExcel.getRowCount();
        logger.info("读取excel行数: "+ count );
//        if(count > 51 ){
//            throw new BizException("Excel 数量不能超过50条");
//        }
        List<Map<String, Object>> paramList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Map<String, Object> execlParams = new HashMap<>();
            String[] rows = readExcel.readExcelLine(i);
            if(StringUtils.isEmpty(rows[0])){
                continue;
            }
            //商户号
            String merchantNo = MerchantNoUtils.getMerchantNoByMerchantName(String.valueOf(rows[0]));
            execlParams.put("merchantNo",merchantNo);
            //User ID
            execlParams.put("userId",rows[1]);
            //用户姓名
            execlParams.put("userName",rows[2]);
            //注册时间
            try{
                execlParams.put("registerTime",rows[3]);
            }catch (Exception e){
                execlParams.put("registerTime",null);
            }
            paramList.add(execlParams);
        }
        return paramList;
    }

    private List<Map<String,Object>> getExcelInfoFromYoumi(MultipartFile multipartFile) throws BizException {
        ExcelReader readExcel = new ExcelReader();
        try {
            readExcel.open(multipartFile.getInputStream());
            // 设置读取索引为0的工作表
            readExcel.setSheetNum(0);
        } catch (IOException e) {
            logger.error("读取excel失败{} ",e);
            throw new BizException("读取excel失败");
        }
        // 总行数
        int count = readExcel.getRowCount();
        logger.info("读取excel行数: "+ count );
        List<Map<String, Object>> paramList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Map<String, Object> execlParams = new HashMap<>();
            String[] rows = readExcel.readExcelLine(i);
            if(StringUtils.isEmpty(rows[0])){
                continue;
            }
//            //商户号
//            String merchantNo = MerchantNoUtils.getMerchantNoByMerchantName(String.valueOf(rows[0]));
//            execlParams.put("merchantNo",merchantNo);
            //User ID
            execlParams.put("userId",rows[0]);
            //用户姓名
            execlParams.put("userName",rows[1]);
            //注册时间
            try{
                execlParams.put("registerTime",rows[2]);
            }catch (Exception e){
                execlParams.put("registerTime",null);
            }
            paramList.add(execlParams);
        }
        return paramList;
    }


    @Override
    public List<XiaoShouOrder> getXiaoShouOrder() {
        return xiaoShouOrderDao.getXiaoShouOrder();
    }
    @Override
    public List<XiaoShouOrder> getXiaoShouOrderYmgj() {
        return xiaoShouOrderDao.getXiaoShouOrderYmgj();
    }

    @Override
    public void insertXiaoShouOrder(XiaoShouOrder order) {
        xiaoShouOrderDao.insertXiaoShouOrder(order);
    }
    @Override
    public void insertXiaoShouOrderFromYmgj(XiaoShouOrder order) {
        xiaoShouOrderDao.insertXiaoShouOrderFromYmgj(order);
    }

    @Override
    public void delXiaoShouInfo(Long id) {
        xiaoShouOrderDao.delXiaoShouInfo(id);
    }
    @Override
    public void delXiaoShouInfoFromYmgj(Long id) {
        xiaoShouOrderDao.delXiaoShouInfoFromYmgj(id);
    }


}
