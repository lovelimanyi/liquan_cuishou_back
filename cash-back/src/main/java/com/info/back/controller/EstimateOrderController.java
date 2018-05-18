package com.info.back.controller;

import com.info.back.service.EstimateOrderService;
import com.info.back.service.IEstimateOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("estimate/")
public class EstimateOrderController extends BaseController {

    @Autowired
    private IEstimateOrderService estimateOrderService;

    @RequestMapping("list")
    public String list(Byte orderType, Integer orderAge, String testDate, HttpServletRequest request, HttpServletResponse response, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        HashMap<String, Object> objParams = new HashMap<>();
        objParams.put("orderAge", orderAge);
        if (orderType == null || (orderType != 1 && orderType != 2)) {
            orderType = 1;
        }
        objParams.put("orderType", orderType);
        objParams.put("testDate", testDate);
        Map<String, Object> resultMap = estimateOrderService.getEstimateOrderList(objParams);
        model.addAllAttributes(resultMap);

        model.addAttribute("orderType", orderType);
        model.addAttribute("orderAge", orderAge);
        model.addAttribute("params", params);
        model.addAttribute("testDate", testDate);
        return "estimate/list";
    }

    @RequestMapping("pull")
    @ResponseBody
    public String pull(String testDate, HttpServletRequest request, HttpServletResponse response, Model model) {
        estimateOrderService.pullEstimateOrder(testDate);
        return "ok";
    }
}
