package com.info.back.service;

import com.info.web.pojo.XiaoShouOrder;
import com.info.web.util.PageConfig;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface IXiaoShouService {
    Integer importExcel(MultipartFile multipartFile) throws Exception;

    PageConfig<XiaoShouOrder> findAllUserPage(HashMap<String, Object> params);

    List<XiaoShouOrder> getXiaoShouOrder();

    void insertXiaoShouOrder(XiaoShouOrder order);

    void delXiaoShouInfo(Long id);
}
