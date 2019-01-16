package com.info.back.service;

import org.springframework.web.multipart.MultipartFile;

public interface IXiaoShouService {
    Integer importExcel(MultipartFile multipartFile) throws Exception;
}
