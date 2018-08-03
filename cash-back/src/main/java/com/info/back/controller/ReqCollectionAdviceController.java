package com.info.back.controller;

import com.info.back.dao.IReqCollectionAdviceDao;
import com.info.back.result.BaseResponse;
import com.info.web.pojo.CollectionAdviceReq;
import com.info.web.pojo.CollectionAdviceResponse;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：分期还款新增“催收建议”字段,外部调用催收接口，根据借款id获取订单的催收建议字段
 * 创建人：yyf
 * 创建时间：2018/8/3 0003上午 11:20
 */
@Controller
@RequestMapping("/other/collectionAdvice/")
public class ReqCollectionAdviceController extends BaseController {

    @Autowired
    IReqCollectionAdviceDao reqCollectionAdviceDao;
    @RequestMapping(value = "get",method = RequestMethod.POST ,produces="application/json")
    @ResponseBody
    public BaseResponse<CollectionAdviceResponse> getCollectionAdvice(@RequestBody CollectionAdviceReq req){
        BaseResponse<CollectionAdviceResponse> rep = new BaseResponse<>();
        String loanId = req.getLoanId();
        CollectionAdviceResponse response = new CollectionAdviceResponse();
        if (req.getOrderType() == 1){ //大额
            loanId = req.getLoanId()+"-"+req.getTermNumber();
            response = reqCollectionAdviceDao.getCollectionAdvice(loanId);
            response.setTermNumber(req.getTermNumber());
            response.setLoanId(req.getLoanId());
        }else {
            response = reqCollectionAdviceDao.getCollectionAdvice(loanId);
        }
        rep.setData(response);
        return rep;
    }

    @RequestMapping(value = "getAdviceList",method = RequestMethod.POST ,produces="application/json")
    @ResponseBody
    public BaseResponse<List<CollectionAdviceResponse>> getCollectionAdvice(@RequestBody List<CollectionAdviceReq> req){
        BaseResponse<List<CollectionAdviceResponse>> rep = new BaseResponse<>();
        List<CollectionAdviceResponse> responseList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(req)){
            for (CollectionAdviceReq re : req){
                CollectionAdviceResponse response = new CollectionAdviceResponse();
                if (re.getOrderType() == 1){ //大额
                    String loanId = re.getLoanId()+"-"+re.getTermNumber();
                    response = reqCollectionAdviceDao.getCollectionAdvice(loanId);
                    response.setTermNumber(re.getTermNumber());
                    response.setLoanId(re.getLoanId());
                }else {
                    response = reqCollectionAdviceDao.getCollectionAdvice(re.getLoanId());
                }
                responseList.add(response);
            }
        }
        rep.setData(responseList);
        return rep;
    }


}
