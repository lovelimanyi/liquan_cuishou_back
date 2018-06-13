package com.info.back.controller;

import com.info.back.service.ISyncService;
import com.info.back.utils.MQResponse;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    public MQResponse dealwithOverdueOrder(HttpServletRequest request, @RequestBody CollectionNotifyDto collectionNotifyDto) {
        try {
            logger.info("accept_overdue_order_or_update=" + collectionNotifyDto);
            if (collectionNotifyDto != null) {
                BigAmountRequestParams bigAmount  = handleCollectionNotifyDto(collectionNotifyDto);
                Loan loan = bigAmount.getLoan();
                logger.info("order_loanId_termNumber" + loan.getId().toString());
                Repayment repayment = bigAmount.getRepayment();
                List<RepaymentDetail> repaymentDetail = null;
                if (bigAmount.getRepaymentDetailList() != null && bigAmount.getRepaymentDetailList().size()>0){
                    repaymentDetail = bigAmount.getRepaymentDetailList();
                }
                if (loan != null && repayment!= null ) {
                    //逾期同步或者每日更新
                    syncService.handleOverdue(repayment,loan,repaymentDetail);
                    return new MQResponse();
                } else {
                    logger.info("loan-order-info,repayment is null...");
                    return new MQResponse(MQResponse.Code.ERROR) ;
                }
            }
            return new MQResponse(MQResponse.Code.ERROR) ;
        } catch (Exception e) {
            logger.error("loan-order-info-exception");
            e.printStackTrace();
            return new MQResponse(MQResponse.Code.ERROR) ;
        }
    }



    /**
     * 处理还款数据
     * @param
     */
    @RequestMapping(value = "/repayment" ,method = RequestMethod.POST)
    @ResponseBody
    public MQResponse dealwithRepayment(@RequestBody CollectionNotifyDto collectionNotifyDto){
        logger.info("order_repayment=" + collectionNotifyDto);
        try{

            if (collectionNotifyDto != null) {
                BigAmountRequestParams bigAmount  = handleCollectionNotifyDto(collectionNotifyDto);
                Loan loan = bigAmount.getLoan();
                logger.info("repay_order_loanId_termNumber" + loan.getId().toString());
                Repayment repayment = bigAmount.getRepayment();
                List<RepaymentDetail> repaymentDetailList = bigAmount.getRepaymentDetailList();
                if (loan != null && repayment!= null && repaymentDetailList != null && repaymentDetailList.size()>0) {
                    //还款同步
                    syncService.handleRepay(repayment,loan,repaymentDetailList);
                    return new MQResponse();
                } else {
                    logger.info("repayment,repayment is null...");
                    return new MQResponse(MQResponse.Code.ERROR) ;
                }
            }
            return new MQResponse(MQResponse.Code.ERROR) ;
        }catch (Exception e){
            logger.error("repayment-exception");
            e.printStackTrace();
            return new MQResponse(MQResponse.Code.ERROR) ;
        }
    }


    private BigAmountRequestParams handleCollectionNotifyDto(CollectionNotifyDto collectionNotifyDto) {
        BigAmountRequestParams bigAmountRequestParams = new BigAmountRequestParams();
        if (collectionNotifyDto.getRepayment()!= null && collectionNotifyDto.getLoan() != null){
            Loan loan = new Loan();
            String termNumber = String.valueOf(collectionNotifyDto.getLoan().getTermNumber());
            String loanId = String.valueOf(collectionNotifyDto.getLoan().getId())+"-"+termNumber;

            loan.setId(loanId);
            loan.setLoanMoney(String.valueOf(collectionNotifyDto.getLoan().getLoanMoney()));
            loan.setLoanRate(String.valueOf(collectionNotifyDto.getLoan().getLoanRate()));
            loan.setServiceCharge(String.valueOf(collectionNotifyDto.getLoan().getServiceCharge()));
            loan.setPaidMoney(String.valueOf(collectionNotifyDto.getLoan().getPaidMoney()));
            loan.setLoanPenalty(String.valueOf(collectionNotifyDto.getLoan().getLoanPenalty()));
            loan.setLoanPenaltyRate(String.valueOf(collectionNotifyDto.getLoan().getLoanPenaltyRate()));
            loan.setLoanEndTime(DateUtil.getDateFormat(new Date(collectionNotifyDto.getLoan().getLoanEndTime()),"yyyy-MM-dd"));
            loan.setLoanStartTime(DateUtil.getDateFormat(new Date(collectionNotifyDto.getLoan().getLoanStartTime()),"yyyy-MM-dd"));
            loan.setUserId(String.valueOf(collectionNotifyDto.getLoan().getUserId()));
            loan.setTermNumber(String.valueOf(collectionNotifyDto.getLoan().getTermNumber()));
            loan.setOverdueDays(collectionNotifyDto.getLoan().getLateDay());
            loan.setAccrual(String.valueOf(collectionNotifyDto.getLoan().getAccrual()));
            loan.setMerchantNo(collectionNotifyDto.getLoan().getMerchantNo());
            bigAmountRequestParams.setLoan(loan);

            Repayment repayment = new Repayment();
            //为了防止payId与小额重复，在payId后面加上-0
            String payId =String.valueOf(collectionNotifyDto.getRepayment().getId())+"-0" ;
            repayment.setId(payId);
            repayment.setLoanId(loanId);
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
        if (collectionNotifyDto.getRepayment()!= null && collectionNotifyDto.getLoan() != null && collectionNotifyDto.getRepaymentDetailList()!= null && collectionNotifyDto.getRepaymentDetailList().size()>0){
            String payId =String.valueOf(collectionNotifyDto.getRepayment().getId())+"-0" ;
            List<RepaymentDetail> repaymentDetails = new ArrayList<>();
            for (int i = 0;  i<collectionNotifyDto.getRepaymentDetailList().size() ;i++){
                RepaymentDetail repaymentDetail = new RepaymentDetail();
                repaymentDetail.setId(String.valueOf(collectionNotifyDto.getRepaymentDetailList().get(i).getId()));
                repaymentDetail.setCreateDate(DateUtil.getDateFormat(new Date(collectionNotifyDto.getRepaymentDetailList().get(i).getCreateDate()),"yyyy-MM-dd"));
                repaymentDetail.setPayId(payId);
                repaymentDetail.setReturnType(String.valueOf(collectionNotifyDto.getRepaymentDetailList().get(i).getReturnType()));
                repaymentDetail.setRemark(String.valueOf(collectionNotifyDto.getRepaymentDetailList().get(i).getRemark()));

                repaymentDetail.setRealInterest(String.valueOf(collectionNotifyDto.getRepaymentDetailList().get(i).getRealInterest()));
                repaymentDetail.setRealMoney(String.valueOf(collectionNotifyDto.getRepaymentDetailList().get(i).getRealMoney()));
                repaymentDetail.setRealPenlty(String.valueOf(collectionNotifyDto.getRepaymentDetailList().get(i).getRealPenlty()));
                repaymentDetail.setRealPrinciple(String.valueOf(collectionNotifyDto.getRepaymentDetailList().get(i).getRealPrinciple()));
                repaymentDetail.setRealgetAccrual(String.valueOf(collectionNotifyDto.getRepaymentDetailList().get(i).getRealgetAccrual()));
                repaymentDetail.setRemainAccrual(String.valueOf(collectionNotifyDto.getRepaymentDetailList().get(i).getRemainAccrual()));
                repaymentDetails.add(repaymentDetail);
            }
            bigAmountRequestParams.setRepaymentDetailList(repaymentDetails);
        }
        return bigAmountRequestParams;
    }

}
