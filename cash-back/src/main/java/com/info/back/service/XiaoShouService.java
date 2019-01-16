package com.info.back.service;

import com.info.back.dao.IXiaoShouDao;
import com.info.back.exception.BizException;
import com.info.back.utils.ExcelReader;
import com.info.back.utils.MerchantNoUtils;
import com.info.back.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class XiaoShouService implements IXiaoShouService {
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(XiaoShouService.class);
    @Autowired
    IXiaoShouDao xiaoShouDao;

    @Override
    public Integer importExcel(MultipartFile multipartFile)  throws BizException {
        logger.info("解析excel ");
        // 解析Excel
        List<Map<String, Object>> paramList = getExcelInfo(multipartFile);
        Integer count = xiaoShouDao.importExcel(paramList);
        return count;
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
            String merchantNo = MerchantNoUtils.getMerchantNoByMerchantName(rows[0]);
            execlParams.put("merchantNo",merchantNo);
            //User ID
            execlParams.put("userId",rows[1]);
            //用户姓名
            execlParams.put("userName",rows[2]);
            //注册时间
            execlParams.put("registerTime",rows[3]);
            paramList.add(execlParams);
        }
        return paramList;
    }

}
