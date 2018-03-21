package com.info.back.controller;

import com.info.back.service.ISyncService;
import com.info.vo.bigAmount.BigAmountRequestParams;
import com.info.vo.bigAmount.Loan;
import com.info.vo.bigAmount.Repayment;
import com.info.vo.bigAmount.RepaymentDetail;
import com.info.web.pojo.dto.CollectionNotifyDto;
import com.info.web.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * @author Administrator
 * @Description: 接收mq消息，供MQ调用
 * @CreateTime 2017-11-15 上午 9:44
 **/

@Controller
@RequestMapping("/sync")
public class SyncController {

    private static Logger logger = Logger.getLogger(SyncController.class);

    @Autowired
    private ISyncService syncService;
    /**
     * 每日更新滞纳金，罚息。
     * @param
     */
    @RequestMapping(value = "/loan-order-update",method = RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    public void updateOverdueOrder(HttpServletRequest request,@RequestBody CollectionNotifyDto collectionNotifyDto) {
        logger.info("每日更新逾期订单数据：");
        String orderId = null;
        try{
            if (collectionNotifyDto != null) {
                BigAmountRequestParams bigAmount = handleCollectionNotifyDto(collectionNotifyDto);
                Loan loan = bigAmount.getLoan();
                Repayment repayment = bigAmount.getRepayment();
                if (loan != null && repayment!= null) {
                    //逾期同步
                    orderId = loan.getId();
                    syncService.updateOverdue(repayment,loan);
                } else {
                    logger.info("解析json,repayment is null...");
                }
            }
        }catch (Exception e){
            logger.error("overdueOrderUpdateException:orderId="+orderId,e);
        }
    }

    /**
     * 逾期订单推送
     * @param
     */
    @RequestMapping(value = "/loan-order-info",method = RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    public void dealwithOverdueOrder(HttpServletRequest request,@RequestBody CollectionNotifyDto collectionNotifyDto) {
        logger.info("接收到逾期订单数据：");
        try {

            if (collectionNotifyDto != null) {
                BigAmountRequestParams bigAmount  = handleCollectionNotifyDto(collectionNotifyDto);
                Loan loan = bigAmount.getLoan();
                Repayment repayment = bigAmount.getRepayment();
                if (loan != null && repayment!= null) {
                    //逾期同步
                    syncService.handleOverdue(repayment,loan);
                } else {
                    logger.info("解析json,repayment is null...");
                }
            }
        } catch (Exception e) {
            logger.error("json字符串解析错误！");
            e.printStackTrace();
        }
    }



    /**
     * 处理还款数据
     * @param
     */
    @RequestMapping(value = "/repayment" ,method = RequestMethod.POST)
    @ResponseBody
    public void dealwithRepayment(@RequestBody CollectionNotifyDto collectionNotifyDto){
        logger.info("接收到还款数据：");
        try{

            if (collectionNotifyDto != null) {
                BigAmountRequestParams bigAmount  = handleCollectionNotifyDto(collectionNotifyDto);
                Loan loan = bigAmount.getLoan();
                Repayment repayment = bigAmount.getRepayment();
                RepaymentDetail repaymentDetail = bigAmount.getRepaymentDetail();
                if (loan != null && repayment!= null && repaymentDetail != null) {
                    //还款同步
                    syncService.handleRepay(repayment,loan,repaymentDetail);
                } else {
                    logger.info("解析json,repayment is null...");
                }
            }
        }catch (Exception e){
            logger.error("json字符串解析错误！");
            e.printStackTrace();
        }
    }
    private BigAmountRequestParams handleCollectionNotifyDto(CollectionNotifyDto collectionNotifyDto) {
        BigAmountRequestParams bigAmountRequestParams = new BigAmountRequestParams();
        if (collectionNotifyDto.getRepayment()!= null && collectionNotifyDto.getLoan() != null){
            Loan loan = new Loan();
            loan.setId(String.valueOf(collectionNotifyDto.getLoan().getId()));
            loan.setLoanMoney(String.valueOf(collectionNotifyDto.getLoan().getLoanMoney()));
            loan.setLoanRate(String.valueOf(collectionNotifyDto.getLoan().getLoanRate()));
            loan.setServiceCharge(String.valueOf(collectionNotifyDto.getLoan().getServiceCharge()));
            loan.setPaidMoney(String.valueOf(collectionNotifyDto.getLoan().getPaidMoney()));
            loan.setLoanPenalty(String.valueOf(collectionNotifyDto.getLoan().getLoanPenalty()));
            loan.setLoanPenaltyRate(String.valueOf(collectionNotifyDto.getLoan().getLoanPenaltyRate()));
            loan.setLoanEndTime(DateUtil.getDateFormat(new Date(collectionNotifyDto.getLoan().getLoanEndTime()),"yyyy-MM-dd"));
            loan.setUserId(String.valueOf(collectionNotifyDto.getLoan().getUserId()));
            loan.setTermNumber(String.valueOf(collectionNotifyDto.getLoan().getTermNumber()));
            loan.setOverdueDays(collectionNotifyDto.getLoan().getLateDay());
            loan.setAccrual(String.valueOf(collectionNotifyDto.getLoan().getAccrual()));
            loan.setMerchantNo(collectionNotifyDto.getLoan().getMerchantNo());
            bigAmountRequestParams.setLoan(loan);

            Repayment repayment = new Repayment();
            repayment.setId(String.valueOf(collectionNotifyDto.getRepayment().getId()));
            repayment.setLoanId(String.valueOf(collectionNotifyDto.getRepayment().getLoanId()));
            repayment.setReceivableDate(DateUtil.getDateFormat(new Date(collectionNotifyDto.getRepayment().getReceivableDate()),"yyyy-MM-dd"));
            repayment.setReceiveMoney(String.valueOf(collectionNotifyDto.getRepayment().getReceiveMoney()));
            repayment.setLoanPenalty(String.valueOf(collectionNotifyDto.getRepayment().getLoanPenalty()));
            repayment.setRealMoney(String.valueOf(collectionNotifyDto.getRepayment().getRealMoney()));
            repayment.setRealgetPrinciple(String.valueOf(collectionNotifyDto.getRepayment().getRealgetPrinciple()));
            repayment.setReceivablePrinciple(String.valueOf(collectionNotifyDto.getRepayment().getReceivablePrinciple()));
            repayment.setRealgetServiceCharge(String.valueOf(collectionNotifyDto.getRepayment().getRealgetServiceCharge()));
            repayment.setRemainServiceCharge(String.valueOf(collectionNotifyDto.getRepayment().getRemainServiceCharge()));
            repayment.setRealgetInterest(String.valueOf(collectionNotifyDto.getRepayment().getRealgetInterest()));
            repayment.setReceivableInterest(String.valueOf(collectionNotifyDto.getRepayment().getReceivableInterest()));
            repayment.setRealgetAccrual(String.valueOf(collectionNotifyDto.getRepayment().getRealgetAccrual()));
            repayment.setRemainAccrual(String.valueOf(collectionNotifyDto.getRepayment().getRemainAccrual()));
            repayment.setCreateDate(DateUtil.getDateFormat(new Date(collectionNotifyDto.getRepayment().getCreateDate()),"yyyy-MM-dd"));
            bigAmountRequestParams.setRepayment(repayment);
        }
        if (collectionNotifyDto.getRepayment()!= null && collectionNotifyDto.getLoan() != null && collectionNotifyDto.getRepaymentDetail()!= null){
            RepaymentDetail repaymentDetail = new RepaymentDetail();
            repaymentDetail.setId(String.valueOf(collectionNotifyDto.getRepaymentDetail().getId()));
            repaymentDetail.setCreateDate(DateUtil.getDateFormat(new Date(collectionNotifyDto.getRepaymentDetail().getCreateDate()),"yyyy-MM-dd"));
            repaymentDetail.setPayId(String.valueOf(collectionNotifyDto.getRepaymentDetail().getPayId()));
            repaymentDetail.setReturnType(String.valueOf(collectionNotifyDto.getRepaymentDetail().getReturnType()));
            repaymentDetail.setRemark(String.valueOf(collectionNotifyDto.getRepaymentDetail().getRemark()));

            repaymentDetail.setRealInterest(String.valueOf(collectionNotifyDto.getRepaymentDetail().getRealInterest()));
            repaymentDetail.setRealMoney(String.valueOf(collectionNotifyDto.getRepaymentDetail().getRealMoney()));
            repaymentDetail.setRealPenlty(String.valueOf(collectionNotifyDto.getRepaymentDetail().getRealPenlty()));
            repaymentDetail.setRealPrinciple(String.valueOf(collectionNotifyDto.getRepaymentDetail().getRealPrinciple()));
            repaymentDetail.setRealgetAccrual(String.valueOf(collectionNotifyDto.getRepaymentDetail().getRealgetAccrual()));
            repaymentDetail.setRemainAccrual(String.valueOf(collectionNotifyDto.getRepaymentDetail().getRemainAccrual()));
            bigAmountRequestParams.setRepaymentDetail(repaymentDetail);
        }
        return bigAmountRequestParams;
    }

}
