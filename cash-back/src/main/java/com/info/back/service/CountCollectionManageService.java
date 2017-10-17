package com.info.back.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.info.back.dao.ICountCollectionAssessmentDao;
import com.info.web.pojo.CountCashBusiness;
import com.info.web.synchronization.dao.IPaginationXjxDao;
import com.info.web.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.ICountCollectionManageDao;
import com.info.back.dao.IPaginationDao;
import com.info.constant.Constant;
import com.info.web.pojo.CountCollectionAssessment;
import com.info.web.pojo.CountCollectionManage;
import com.info.web.util.PageConfig;

/**
 * 管理跟踪统计
 *
 * @author Administrator
 */
@Service
public class CountCollectionManageService implements ICountCollectionManageService {
    private static Logger logger = LoggerFactory.getLogger(CountCollectionManageService.class);
    @Autowired
    private ICountCollectionManageDao countCollectionManageDao;
    @Autowired
    private IPaginationDao paginationDao;
    @Autowired
    private IPaginationXjxDao paginationDaoXjx;
    @Autowired
    private ICountCollectionAssessmentDao countCollectionAssessmentDao;

    @Override
    public PageConfig<CountCollectionManage> findPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "CountCollectionManage");
        PageConfig<CountCollectionManage> pageConfig = new PageConfig<CountCollectionManage>();
        pageConfig = paginationDao.findPage("findAll", "findAllCount", params, null);
        if (!"C".equals(params.get("method"))) {
            if (pageConfig != null && CollectionUtils.isNotEmpty(pageConfig.getItems())) {
                params.put("numPerPage", pageConfig.getTotalResultSize());

                PageConfig<CountCollectionManage> pageConfigAll = paginationDao.findPage("findAll", "findAllCount", params, null);
                CountCollectionManage cca = this.handleData(pageConfigAll.getItems());
                if (cca != null) {
                    pageConfig.getItems().add(0, cca);
                }
            }
//			this.handleData(pageConfig.getItems());
        }
        return pageConfig;
    }

    @Override
    public PageConfig<CountCashBusiness> getCountCashBusinessPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "CountCashBusiness");
        PageConfig<CountCashBusiness> pageConfig = new PageConfig<CountCashBusiness>();
        pageConfig = paginationDaoXjx.findPage("findAll", "findAllCount", params, null);
        if (pageConfig != null && CollectionUtils.isNotEmpty(pageConfig.getItems())) {
            params.put("numPerPage", pageConfig.getTotalResultSize());
            PageConfig<CountCashBusiness> pageConfigAll = paginationDaoXjx.findPage("findAll", "findAllCount", params, null);
            CountCashBusiness ccb = this.handleCashData(pageConfigAll.getItems());
            if (ccb != null) {
                pageConfig.getItems().add(0, ccb);
            }
        }
        return pageConfig;
    }

    @Override
    public void deleteManageList(HashMap<String, Object> params) {
        countCollectionManageDao.deleteManageList(params);
    }

    public List<CountCollectionManage> findAll(HashMap<String, Object> params) {
        return countCollectionManageDao.findAll(params);
    }

    @Override
    public Integer findAllCount(HashMap<String, Object> params) {
        return countCollectionManageDao.findAllCount(params);
    }

    @Override
    public CountCollectionManage getOne(HashMap<String, Object> params) {
        List<CountCollectionManage> list = this.findAll(params);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 数据处理
     *
     * @param list
     */
    private CountCollectionManage handleData(List<CountCollectionManage> list) {
        CountCollectionManage cc = new CountCollectionManage();
        if (list != null && list.size() > 0) {
            for (CountCollectionManage ca : list) {
                cc.setLoanMoney(cc.getLoanMoney().add(ca.getLoanMoney()));   //本金
                cc.setRepaymentMoney(cc.getRepaymentMoney().add(ca.getRepaymentMoney()));  //已还本金
                cc.setNotYetRepaymentMoney(cc.getNotYetRepaymentMoney().add(ca.getNotYetRepaymentMoney()));   //未还本金
                cc.setPenalty(cc.getPenalty().add(ca.getPenalty()));   //滞纳金
                cc.setRepaymentPenalty(cc.getRepaymentPenalty().add(ca.getRepaymentPenalty()));   //已还滞纳金
                cc.setNotRepaymentPenalty(cc.getNotRepaymentPenalty().add(ca.getNotRepaymentPenalty()));   //未还滞纳金
                cc.setOrderTotal(cc.getOrderTotal() + ca.getOrderTotal());   // 订单量
                cc.setRepaymentOrderNum(cc.getRepaymentOrderNum() + ca.getRepaymentOrderNum());  //已还订单量
                cc.setDisposeOrderNum(cc.getDisposeOrderNum() + ca.getDisposeOrderNum());  //已操作订单量
            }
            BigDecimal rr = new BigDecimal(0);     //本金还款率
            BigDecimal rpr = new BigDecimal(0);    //滞纳金还款率
            BigDecimal ror = new BigDecimal(0);    //订单还款率
            if (cc.getLoanMoney().compareTo(new BigDecimal(0)) == 1) {
                rr = cc.getRepaymentMoney().divide(cc.getLoanMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);

            }
            if (cc.getPenalty().compareTo(new BigDecimal(0)) == 1) {  //滞纳金还款率
                rpr = cc.getRepaymentPenalty().divide(cc.getPenalty(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            if (cc.getOrderTotal() > 0) {   //订单还款率
                ror = new BigDecimal(cc.getRepaymentOrderNum()).divide(new BigDecimal(cc.getOrderTotal()), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
//	    		double rate = cc.getRepaymentOrderNum() / cc.getOrderTotal() * 100d;
//	    		ror = new BigDecimal(rate).setScale(2,BigDecimal.ROUND_HALF_UP);
            }
            cc.setRepaymentReta(rr);
            cc.setMigrateRate(new BigDecimal(-1.00).setScale(2, BigDecimal.ROUND_HALF_UP));
            cc.setPenaltyRepaymentReta(rpr);
            cc.setRepaymentOrderRate(ror);
//	    	list.add(0, cc);
        }
        return cc;
    }

    /**
     * 现金侠业务量总计
     *
     * @param list
     */
    private CountCashBusiness handleCashData(List<CountCashBusiness> list) {
        CountCashBusiness ccb = new CountCashBusiness();
        if (CollectionUtils.isNotEmpty(list)) {
            for (CountCashBusiness countCashBusiness : list) {
                ccb.setExpireAmount((ccb.getExpireAmount() == null ? 0 : ccb.getExpireAmount()) + countCashBusiness.getExpireAmount());
                ccb.setMoneyAmountCount((ccb.getMoneyAmountCount() == null ? 0 : ccb.getMoneyAmountCount()) + countCashBusiness.getMoneyAmountCount());
                ccb.setSevendayMoenyCount((ccb.getSevendayMoenyCount() == null ? 0 : ccb.getSevendayMoenyCount()) + countCashBusiness.getSevendayMoenyCount());
                ccb.setFourteendayMoneyCount((ccb.getFourteendayMoneyCount() == null ? 0 : ccb.getFourteendayMoneyCount()) + countCashBusiness.getFourteendayMoneyCount());
                ccb.setTwentyonedayMoenyCount((ccb.getTwentyonedayMoenyCount() == null ? 0 : ccb.getTwentyonedayMoenyCount()) + countCashBusiness.getTwentyonedayMoenyCount());
                ccb.setOverdueAmount((ccb.getOverdueAmount() == null ? 0 : ccb.getOverdueAmount()) + countCashBusiness.getOverdueAmount());
                ccb.setOverdueRateSevenAmount((ccb.getOverdueRateSevenAmount() == null ? 0 : ccb.getOverdueRateSevenAmount()) + countCashBusiness.getOverdueRateSevenAmount());
                ccb.setOverdueRateFourteenAmount((ccb.getOverdueRateFourteenAmount() == null ? 0 : ccb.getOverdueRateFourteenAmount()) + countCashBusiness.getOverdueRateFourteenAmount());
                ccb.setOverdueRateTwentyoneAmount((ccb.getOverdueRateTwentyoneAmount() == null ? 0 : ccb.getOverdueRateTwentyoneAmount()) + countCashBusiness.getOverdueRateTwentyoneAmount());


                BigDecimal oms7 = new BigDecimal(0);     //7天产品金额逾期率
                BigDecimal oms14 = new BigDecimal(0);    //14天产品金额逾期率
                BigDecimal bmr = new BigDecimal(0);    //金额坏账率
                if (ccb.getSevendayMoenyCount() != null && ccb.getSevendayMoenyCount() > 0) {
                    BigDecimal mc7 = new BigDecimal(ccb.getSevendayMoenyCount());
                    BigDecimal ora7 = new BigDecimal(ccb.getOverdueRateSevenAmount());
                    oms7 = ora7.divide(mc7, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                if (ccb.getFourteendayMoneyCount() != null && ccb.getFourteendayMoneyCount() > 0) {
                    BigDecimal boc14 = new BigDecimal(ccb.getFourteendayMoneyCount());
                    BigDecimal oc14 = new BigDecimal(ccb.getOverdueRateFourteenAmount());
                    oms14 = oc14.divide(boc14, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                ccb.setOverdueMoneyStatistic7value(oms7);
                ccb.setOverdueMoneyStatistic14value(oms14);
//				ccb.setBaddebtCountRate();

                ccb.setExpireCount((ccb.getExpireCount() == null ? 0 : ccb.getExpireCount()) + countCashBusiness.getExpireCount());
                ccb.setBorrowOrderCount((ccb.getBorrowOrderCount() == null ? 0 : ccb.getBorrowOrderCount()) + countCashBusiness.getBorrowOrderCount());
                ccb.setBorrowOrderSevenCount((ccb.getBorrowOrderSevenCount() == null ? 0 : ccb.getBorrowOrderSevenCount()) + countCashBusiness.getBorrowOrderSevenCount());
                ccb.setBorrowOrderFourteenCount((ccb.getBorrowOrderFourteenCount() == null ? 0 : ccb.getBorrowOrderFourteenCount()) + countCashBusiness.getBorrowOrderFourteenCount());
                ccb.setBorrowOrderTwentyoneCount((ccb.getBorrowOrderTwentyoneCount() == null ? 0 : ccb.getBorrowOrderTwentyoneCount()) + countCashBusiness.getBorrowOrderTwentyoneCount());
                ccb.setOverdueCount((ccb.getOverdueCount() == null ? 0 : ccb.getOverdueCount()) + countCashBusiness.getOverdueCount());
                ccb.setOverdueSevenCount((ccb.getOverdueSevenCount() == null ? 0 : ccb.getOverdueSevenCount()) + countCashBusiness.getOverdueSevenCount());
                ccb.setOverdueFourteenCount((ccb.getOverdueFourteenCount() == null ? 0 : ccb.getOverdueFourteenCount()) + countCashBusiness.getOverdueFourteenCount());
                ccb.setOverdueTwentyoneCount((ccb.getOverdueTwentyoneCount() == null ? 0 : ccb.getOverdueTwentyoneCount()) + countCashBusiness.getOverdueTwentyoneCount());

                BigDecimal omscs7 = new BigDecimal(0);     //7天产品数量逾期率
                BigDecimal omscs14 = new BigDecimal(0);    //14天产品数量逾期率
                BigDecimal bcr = new BigDecimal(0);    //数量坏账率
                if (ccb.getBorrowOrderSevenCount() != null && ccb.getBorrowOrderSevenCount() > 0) {
                    BigDecimal boc7 = new BigDecimal(ccb.getBorrowOrderSevenCount());
                    BigDecimal oc7 = new BigDecimal(ccb.getOverdueSevenCount());
                    omscs7 = oc7.divide(boc7, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                if (ccb.getBorrowOrderFourteenCount() != null && ccb.getBorrowOrderFourteenCount() > 0) {
                    BigDecimal boc14 = new BigDecimal(ccb.getBorrowOrderFourteenCount());
                    BigDecimal oc14 = new BigDecimal(ccb.getOverdueFourteenCount());
                    omscs14 = oc14.divide(boc14, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                ccb.setOverdueMoneySumCountStatistic7Value(omscs7);
                ccb.setOverdueMoneySumCountStatistic14Value(omscs14);
//				ccb.setBaddebtCountRate();


            }
        }
        return ccb;
    }

    @Override
    public void countCallManage(HashMap<String, Object> params) {
//		countCollectionManageDao.callManage(params);
        params.put("begDate", DateUtil.getDayFirst());
        params.put("endDate", new Date());
        countCollectionManageDao.deleteManageList(params);
//		Date date = new Date();
//		int count = 0;
//		try {
//			count = DateUtil.daysBetween(DateUtil.getDayFirst(),new Date());
//		} catch (ParseException e) {
//			logger.error("计算时间天数异常",e);
//		}
//		logger.info("天数："+count);
//		for (int i = count; i>=0 ; i--){
//			params.put("currDate",DateUtil.getBeforeOrAfter(date,-i));
        List<CountCollectionAssessment> manageList = countCollectionAssessmentDao.queryManageList(params);
        logger.info("管理统计报表manageList = {}", manageList != null ? manageList.size() : null);
        if (CollectionUtils.isNotEmpty(manageList)) {
            countCollectionAssessmentDao.insertManageList(manageList);
        }
//		}
    }

    public static void main(String[] args) {
        System.out.println(999);
        String begDate = "2017-04-19";
        String endDate = "2017-04-19";

        int count = 0;
        try {
            count = DateUtil.daysBetween(DateUtil.formatDate(begDate, "yyyy-MM-dd"), DateUtil.formatDate(endDate, "yyyy-MM-dd"));
        } catch (ParseException e) {
            logger.error("计算时间天数异常", e);
        }

        System.out.println("==========================================================================" + count);
        for (int i = 0; i < count; i++) {
            System.out.println("currDate : " + DateUtil.getBeforeOrAfter(DateUtil.getDateTimeFormat(begDate, "yyyy-MM-dd"), i));
        }
    }
}
