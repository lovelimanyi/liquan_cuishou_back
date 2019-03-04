package com.info.back.service;

import com.info.web.pojo.XiaoShouOrder;
import com.info.web.util.PageConfig;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface IXiaoShouService {
    Integer importExcel(MultipartFile multipartFile,String orderFrom) throws Exception;

    PageConfig<XiaoShouOrder> findAllUserPage(HashMap<String, Object> params);
    PageConfig<XiaoShouOrder> findAllUserPageFromYoumi(HashMap<String, Object> params);

    List<XiaoShouOrder> getXiaoShouOrder();
    List<XiaoShouOrder> getXiaoShouOrderYmgj();

    void insertXiaoShouOrder(XiaoShouOrder order);
    void insertXiaoShouOrderFromYmgj(XiaoShouOrder order);

    void delXiaoShouInfo(Long id);
    void delXiaoShouInfoFromYmgj(Long id);

}
