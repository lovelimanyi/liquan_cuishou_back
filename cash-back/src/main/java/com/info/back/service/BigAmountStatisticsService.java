package com.info.back.service;

import com.info.back.dao.IBigAmountStatisticsDao;
import com.info.back.dao.IPaginationDao;
import com.info.back.utils.DateKitUtils;
import com.info.constant.Constant;
import com.info.web.pojo.BigAmountStatistics;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/5/2 0002下午 01:58
 */
@Service
public class BigAmountStatisticsService implements IBigAmountStatisticsService {
    @Autowired
    private IPaginationDao paginationDao;
    @Autowired
    private IBigAmountStatisticsDao bigAmountStatisticsDao;

    @Override
    public PageConfig<BigAmountStatistics> findPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "BigAmountStatistics");
        PageConfig<BigAmountStatistics> pageConfig ;
        pageConfig = paginationDao.findPage("findAll", "findAllCount", params, null);
        params.put("numPerPage", pageConfig.getTotalResultSize());
        PageConfig<BigAmountStatistics> pageAllConfig = paginationDao.findPage("findAll", "findAllCount", params, null);
        BigAmountStatistics bas = this.handleData(pageAllConfig.getItems());
        if (bas != null) {
            pageConfig.getItems().add(0, bas);
        }

        return pageConfig;
    }
    @Override
    public PageConfig<BigAmountStatistics> findCompanyPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "BigAmountStatistics");
        PageConfig<BigAmountStatistics> pageConfig ;
        pageConfig = paginationDao.findPage("findCompanyAll", "findCompanyAllCount", params, null);
        params.put("numPerPage", pageConfig.getTotalResultSize());
        PageConfig<BigAmountStatistics> pageAllConfig = paginationDao.findPage("findCompanyAll", "findCompanyAllCount", params, null);
        BigAmountStatistics bas = this.handleData(pageAllConfig.getItems());
        if (bas != null) {
            pageConfig.getItems().add(0, bas);
        }

        return pageConfig;
    }

    private BigAmountStatistics handleData(List<BigAmountStatistics> list) {
        BigAmountStatistics bas = new BigAmountStatistics();
        bas.setTotalAccrual(BigDecimal.ZERO);
        bas.setTotalPrincipal(BigDecimal.ZERO);
        bas.setRealgetTotalPrincipal(BigDecimal.ZERO);
        bas.setRemainPrincipal(BigDecimal.ZERO);
        bas.setTotalPenalty(BigDecimal.ZERO);
        bas.setRealgetTotalPenalty(BigDecimal.ZERO);
        bas.setRemainPenalty(BigDecimal.ZERO);
        bas.setTotalOrderCount(0);
        bas.setUndoneOrderCount(0);
        bas.setDoneOrderCount(0);

        if (list != null && list.size() > 0) {
            for (BigAmountStatistics ba : list) {
                bas.setTotalAccrual(bas.getTotalAccrual().add(ba.getTotalAccrual()));
                bas.setTotalPrincipal(bas.getTotalPrincipal().add(ba.getTotalPrincipal()));
                bas.setRealgetTotalPrincipal(bas.getRealgetTotalPrincipal().add(ba.getRealgetTotalPrincipal()));
                bas.setRemainPrincipal(bas.getRemainPrincipal().add(ba.getRemainPrincipal()));
                bas.setTotalPenalty(bas.getTotalPenalty().add(ba.getTotalPenalty()));
                bas.setRealgetTotalPenalty(bas.getRealgetTotalPenalty().add(ba.getRealgetTotalPenalty()));
                bas.setRemainPenalty(bas.getRemainPenalty().add(ba.getRemainPenalty()));
                bas.setTotalOrderCount(bas.getTotalOrderCount()+ba.getTotalOrderCount());
                bas.setUndoneOrderCount(bas.getUndoneOrderCount()+ba.getUndoneOrderCount());
                bas.setDoneOrderCount(bas.getDoneOrderCount()+ba.getDoneOrderCount());
            }
            BigDecimal br = new BigDecimal(0);     //本金还款率
            BigDecimal zr = new BigDecimal(0);    //滞纳金还款率
            BigDecimal dr = new BigDecimal(0);    //订单还款率
            BigDecimal tOrderCount = new BigDecimal(bas.getTotalOrderCount());
            BigDecimal dOrderCount = new BigDecimal(bas.getDoneOrderCount());
            if (bas.getRealgetTotalPrincipal().compareTo(new BigDecimal(0)) == 1) { //本金还款率
                br = bas.getRealgetTotalPrincipal().divide(bas.getTotalPrincipal(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            if (bas.getRealgetTotalPenalty().compareTo(new BigDecimal(0)) == 1) { //滞纳金还款率
                zr = bas.getRealgetTotalPenalty().divide(bas.getTotalPenalty(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            if (dOrderCount.compareTo(new BigDecimal(0)) == 1) { //滞纳金还款率
                dr = dOrderCount.divide(tOrderCount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            bas.setRepaymentProbability(br);
            bas.setPenaltyProbability(zr);
            bas.setOrderProbability(dr);
        }
        return bas;
    }

    @Override
    public void doStatistics(String beginTime,String endTime) {
        HashMap<String, Object> paramss = DateKitUtils.setDefaultDate(beginTime,endTime);
        List<BigAmountStatistics> list = bigAmountStatisticsDao.getBigAmountPersonStatistics(paramss);

        List<BigAmountStatistics> list2 = bigAmountStatisticsDao.getBigAmountCompanyStatistics(paramss);
        //保存至数据库

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(sdf.format(new Date()));
            for (BigAmountStatistics bigAmountStatistics : list){
                if(StringUtils.isNotBlank(endTime)){
                    bigAmountStatistics.setCreateDate(DateUtil.getDateTimeFormat(endTime,"yyyy-MM-dd"));
                }else {
                    bigAmountStatistics.setCreateDate(date);
                }
                bigAmountStatisticsDao.insertPersonStatistics(bigAmountStatistics);
            }
            for (BigAmountStatistics bigAmountStatistics : list2){
                if(StringUtils.isNotBlank(endTime)){
                    bigAmountStatistics.setCreateDate(DateUtil.getDateTimeFormat(endTime,"yyyy-MM-dd"));
                }else {
                    bigAmountStatistics.setCreateDate(date);
                }
                bigAmountStatisticsDao.insertCompanyStatistics(bigAmountStatistics);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
