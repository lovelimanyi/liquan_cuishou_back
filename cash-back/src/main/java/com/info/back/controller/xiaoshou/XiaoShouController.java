package com.info.back.controller.xiaoshou;

import com.info.back.controller.BaseController;
import com.info.back.result.JsonResult;
import com.info.back.service.IXiaoShouService;
import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
@RequestMapping("xiaoShou/")
public class XiaoShouController  extends BaseController {

    private static Logger logger = Logger.getLogger(XiaoShouController.class);

    @Autowired
    IXiaoShouService xiaoShouService;

    /**
     * 导入客户信息页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("toImportUserInfoPage")
    public String toImportUserInfoPage(HttpServletRequest request, HttpServletResponse response,Model model) {
        HashMap<String, Object> params = getParametersO(request);
        model.addAttribute("params", params);
        return "xiaoshou/importUserInfo";
    }


    /**
     * excel 导入
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/importExcel", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String uploadExcel(HttpServletRequest request, HttpServletResponse response, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        JsonResult result = new JsonResult("-1", "导入失败！");
        try {
            logger.info("开始导入excel");
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile multipartFile = multipartRequest.getFile("file");
//            String filename = file.getOriginalFilename();
            Integer count = xiaoShouService.importExcel(multipartFile);
            result.setCode("0");
            result.setMsg("导入成功！");
            model.addAttribute("params", params);
        } catch (Exception e) {
            logger.error("导入订单出错");
            result.setCode("-1");
            result.setMsg("导入失败！");
            e.printStackTrace();
        }
        SpringUtils.renderDwzResult(response, "0".equals(result.getCode()),
                result.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
                null);
        return null;
    }







}
