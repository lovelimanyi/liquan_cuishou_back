package com.info.back.service;

import com.info.web.pojo.XiaoShouOrder;
import com.info.web.util.PageConfig;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface IXiaoShouService {
    Integer importExcel(MultipartFile multipartFile) throws Exception;

    PageConfig<XiaoShouOrder> findAllUserPage(HashMap<String, Object> params);
}
