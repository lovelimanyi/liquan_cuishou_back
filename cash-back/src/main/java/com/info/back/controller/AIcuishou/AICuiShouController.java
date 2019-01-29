package com.info.back.controller.AIcuishou;

import com.info.back.controller.BaseController;
import com.info.back.service.IAICuiShouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.apache.log4j.Logger;

@Controller
@RequestMapping("aiCuiShou/")
public class AICuiShouController extends BaseController {

    private static Logger logger = Logger.getLogger(AICuiShouController.class);

    @Autowired
    IAICuiShouService aiCuiShouService;

    @RequestMapping("batchCommitData")
    public String batchCommitData() {
        logger.info("aiCuiShou   batchCommitData开始......");
        return aiCuiShouService.batchCommitData();
    }


}
