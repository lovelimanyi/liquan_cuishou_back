package com.info.back.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.info.back.dao.IBackUserDao;
import com.info.back.dao.IEstimateOrderDao;
import com.info.back.dao.IMmanLoanCollectionOrderDao;
import com.info.back.dao.IMmanUserLoanDao;
import com.info.web.pojo.EstimateOrder;
import com.info.web.synchronization.dao.IDataDao;
import com.info.web.util.HttpUtil;
import com.info.web.util.JedisDataClient;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EstimateOrderService implements IEstimateOrderService {
    @Autowired
    private IDataDao dataDao;
    @Autowired
    private IMmanUserLoanDao mmanUserLoanDao;
    @Autowired
    private IEstimateOrderDao estimateOrderDao;
    @Autowired
    private IBackUserDao backUserDao;
    @Autowired
    private IMmanLoanCollectionOrderDao mmanLoanCollectionOrderDao;

    private static final SimpleDateFormat yyyyMMddSdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final Byte ORDER_TYPE_SMALL = 2;
    private static final Byte ORDER_TYPE_BIG = 1;
    private static final BigDecimal PERCENT = new BigDecimal(100);
    private static final BigDecimal AVG_DAYS = new BigDecimal(7);
    private static final String BIG_SUCCESS_CODE = "00";

    //    private static final String BIG_PATH = "http://118.31.47.225:8082/be/getRepaymentOrderForCollection";//预生产
    private static final String BIG_PATH = "http://192.168.5.46:8082/be/getRepaymentOrderForCollection";
    private static final Integer TIME_DAY = 60 * 60 * 24;

    @Override
    public Map<String, Object> getEstimateOrderList(HashMap<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Date now = null;
            String testDate = MapUtils.getString(params, "testDate");
            if (StringUtils.isBlank(testDate)) {
                now = yyyyMMddSdf.parse(yyyyMMddSdf.format(new Date()));
            } else {
                now = yyyyMMddSdf.parse(testDate);
            }
            String redisKey = "cuishou:estimate:" + yyyyMMddSdf.format(now);
            String estimateInfoStr = null;
            JSONObject estimateInfoJSON = null;
            if (StringUtils.isNotBlank(estimateInfoStr)) {
                try {
                    estimateInfoJSON = JSONObject.parseObject(estimateInfoStr);
                    resultMap.putAll(estimateInfoJSON);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (estimateInfoJSON == null) {
                params.put("startOverDate", now);
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.setTime(now);
                endCalendar.add(Calendar.DAY_OF_MONTH, 7);
                params.put("endOverDate", endCalendar.getTime());
                List<EstimateOrder> estimateOrderList = estimateOrderDao.findAll(params);

                Byte orderType = (Byte) params.get("orderType");
                resultMap.put("estimateList", estimateOrderList);
                Integer firstDayEstimateOrder = 0;
                if (estimateOrderList != null && estimateOrderList.size() > 0) {
                    for (EstimateOrder estimateOrder : estimateOrderList) {
                        Calendar overCalendar = Calendar.getInstance();
                        overCalendar.add(Calendar.DAY_OF_MONTH, 1);
                        if (overCalendar.get(Calendar.DAY_OF_MONTH) == 1) {
                            firstDayEstimateOrder = estimateOrder.getEstimateOrderCount();
                        }
                    }
                }

                if (firstDayEstimateOrder == null || firstDayEstimateOrder < 0) {
                    firstDayEstimateOrder = 0;
                }
                Calendar tomorrow = Calendar.getInstance();
                tomorrow.setTime(now);
                tomorrow.add(Calendar.DAY_OF_MONTH, 1);
                Integer tomorrowDay = tomorrow.get(Calendar.DAY_OF_MONTH);
                HashMap<String, HashMap<String, Integer>> dispatchInfo = null;
                if (ORDER_TYPE_SMALL.equals(orderType)) {
                    dispatchInfo = getSmallEstimateInfo(tomorrowDay, firstDayEstimateOrder);
                } else if (ORDER_TYPE_BIG.equals(orderType)) {
                    dispatchInfo = getBigEstimateInfo(firstDayEstimateOrder);
                }
                resultMap.put("dispatchInfo", dispatchInfo);
                JedisDataClient.set(redisKey, JSONObject.toJSONString(resultMap), TIME_DAY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    private HashMap<String, HashMap<String, Integer>> getSmallEstimateInfo(Integer tomorrowDay, Integer firstDayEstimateOrder) {
        HashMap<String, Integer> s1Map = new HashMap<>();
        Integer s1UserCount = backUserDao.selectUserCount4Estimate("3");
        s1Map.put("userCount", s1UserCount);

        HashMap<String, Integer> m1Map = new HashMap<>();
        Integer M1UserCount = backUserDao.selectUserCount4Estimate("5");
        m1Map.put("userCount", M1UserCount);

        HashMap<String, Integer> m2Map = new HashMap<>();
        Integer M2UserCount = backUserDao.selectUserCount4Estimate("6");
        m2Map.put("userCount", M2UserCount);

        HashMap<String, Integer> m3Map = new HashMap<>();
        Integer M3UserCount = backUserDao.selectUserCount4Estimate("7");
        m3Map.put("userCount", M3UserCount);

        if (tomorrowDay != 1) {
            Double s1OrderCount = 0d;
            if (firstDayEstimateOrder > 0 && s1UserCount > 0) {
                s1OrderCount = Math.ceil(firstDayEstimateOrder / s1UserCount);
            }

            s1Map.put("orderCount", s1OrderCount.intValue());
            m1Map.put("orderCount", 0);
            m2Map.put("orderCount", 0);
            m3Map.put("orderCount", 0);
        } else {
            s1Map.put("orderCount", 0);

            Integer m1OrderCount = mmanLoanCollectionOrderDao.selectOrderCount4SmallEstemate("3");
            Double m1Order = 0d;
            if (M1UserCount > 0) {
                m1Order = Math.ceil((m1OrderCount * 0.9 + firstDayEstimateOrder) / M1UserCount);
            }
            m1Map.put("orderCount", m1Order.intValue());

            Integer m2OrderCount = mmanLoanCollectionOrderDao.selectOrderCount4SmallEstemate("5");
            Double m2Order = 0d;
            if (M2UserCount > 0) {
                m2Order = Math.ceil(m2OrderCount * 0.9 / M2UserCount);
            }
            m2Map.put("orderCount", m2Order.intValue());

            Integer m3OrderCount = mmanLoanCollectionOrderDao.selectOrderCount4SmallEstemate("6");
            Double m3Order = 0d;
            if (M3UserCount > 0) {
                m3Order = Math.ceil(m3OrderCount * 0.95 / M3UserCount);
            }
            m3Map.put("orderCount", m3Order.intValue());
        }
        HashMap<String, HashMap<String, Integer>> dispatchInfo = new HashMap<>();
        dispatchInfo.put("s1", s1Map);
        dispatchInfo.put("m1", m1Map);
        dispatchInfo.put("m2", m2Map);
        dispatchInfo.put("m3", m3Map);
        return dispatchInfo;
    }

    private HashMap<String, HashMap<String, Integer>> getBigEstimateInfo(Integer firstDayEstimateOrder) {
        HashMap<String, Integer> m1Map = new HashMap<>();
        Integer M1UserCount = backUserDao.selectUserCount4Estimate("11");
        m1Map.put("userCount", M1UserCount);
        Double m1Order = 0d;
        if (M1UserCount > 0) {
            m1Order = Math.ceil(firstDayEstimateOrder / M1UserCount);
        }
        m1Map.put("orderCount", m1Order.intValue());

        HashMap<String, Integer> m2Map = new HashMap<>();
        Integer M2UserCount = backUserDao.selectUserCount4Estimate("12");
        m2Map.put("userCount", M2UserCount);
        Integer M2OrderCount = mmanLoanCollectionOrderDao.selectOrderCount4BigEstemate(30);
        Double m2Order = 0d;
        if (M2UserCount > 0) {
            m2Order = Math.ceil(M2OrderCount / M2UserCount);
        }
        m2Map.put("orderCount", m2Order.intValue());

        HashMap<String, Integer> m3Map = new HashMap<>();
        Integer M3UserCount = backUserDao.selectUserCount4Estimate("13");
        m3Map.put("userCount", M3UserCount);
        Integer M3OrderCount = mmanLoanCollectionOrderDao.selectOrderCount4BigEstemate(60);
        Double m3Order = 0d;
        if (M3UserCount > 0) {
            m3Order = Math.ceil(M3OrderCount / M3UserCount);
        }
        m3Map.put("orderCount", m3Order.intValue());

        HashMap<String, Integer> m6Map = new HashMap<>();
        Integer M6UserCount = backUserDao.selectUserCount4Estimate("16");
        m6Map.put("userCount", M6UserCount);
        Integer M6OrderCount = mmanLoanCollectionOrderDao.selectOrderCount4BigEstemate(180);
        Double m6Order = 0d;
        if (M6UserCount > 0) {
            m6Order = Math.ceil(M6OrderCount / M6UserCount);
        }
        m6Map.put("orderCount", m6Order.intValue());

        HashMap<String, HashMap<String, Integer>> dispatchInfo = new HashMap<>();
        dispatchInfo.put("m1", m1Map);
        dispatchInfo.put("m2", m2Map);
        dispatchInfo.put("m3", m3Map);
        dispatchInfo.put("m6", m6Map);
        return dispatchInfo;
    }

    @Override
    public void pullEstimateOrder(String pullDate) {
        try {
            Date now = null;
            if (StringUtils.isBlank(pullDate)) {
                now = yyyyMMddSdf.parse(yyyyMMddSdf.format(new Date()));
            } else {
                now = yyyyMMddSdf.parse(pullDate);
            }
            //小额催收预估
            HashMap<String, Object> param = new HashMap<>();
            param.put("startTime", now);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(now);
            endCalendar.add(Calendar.DAY_OF_MONTH, 7);
            param.put("endTime", endCalendar.getTime());
            List<HashMap<String, Object>> smallOrderList = dataDao.getEstimateOrder(param);
            if (smallOrderList != null && smallOrderList.size() > 0) {
                HashMap<String, BigDecimal> smallOldRateMap = getSmallOldCollectionRate(now);
                doRecord(ORDER_TYPE_SMALL, smallOrderList, smallOldRateMap);
            }

            //大额催收预估
            List<HashMap<String, Object>> bigOrderList = getBigOrderInfoList(now, endCalendar.getTime());
            if (bigOrderList != null && bigOrderList.size() > 0) {
                HashMap<String, BigDecimal> bigOldRateMap = getBigOldCollectionRate(now);
                doRecord(ORDER_TYPE_BIG, bigOrderList, bigOldRateMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //处理预估催收记录
    private void doRecord(Byte orderType, List<HashMap<String, Object>> orderList, HashMap<String, BigDecimal> rateMap) {
        for (HashMap<String, Object> info : orderList) {
            try {
                BigDecimal orderCount = new BigDecimal(MapUtils.getInteger(info, "orderCount", 0));
                BigDecimal principal = new BigDecimal(MapUtils.getLong(info, "principal", 0L));
                Date repaymentTime = yyyyMMddSdf.parse(info.get("repaymentTime").toString());
                recordOrder(orderCount, principal, repaymentTime, rateMap, orderType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param orderCount    订单总数
     * @param principal     订单总额
     * @param repaymentTime 到期时间
     * @param orderType     订单类型  1 大额  2 小额
     */
    private void recordOrder(BigDecimal orderCount, BigDecimal principal, Date repaymentTime, HashMap<String, BigDecimal> rateMap, Byte orderType) {
        HashMap<String, Object> tmpParam = new HashMap<>();
        tmpParam.put("overDate", repaymentTime);
        tmpParam.put("orderType", orderType);
        EstimateOrder estimateOrder = estimateOrderDao.findByDate(tmpParam);
        Calendar collectionCalendar = Calendar.getInstance();
        collectionCalendar.setTime(repaymentTime);
        collectionCalendar.add(Calendar.DAY_OF_MONTH, 1);
        if (estimateOrder == null) {
            estimateOrder = new EstimateOrder();
            estimateOrder.setCollectionCompanyId(0L);
            estimateOrder.setOverDate(repaymentTime);
            estimateOrder.setOrderType(orderType);
            estimateOrder.setCollectionDate(collectionCalendar.getTime());
        }
        Integer age = 0;
        if (orderType == ORDER_TYPE_SMALL) {
            if (collectionCalendar.get(Calendar.DAY_OF_MONTH) == 1) {
                age = 5;
            } else {
                age = 3;
            }
        } else {
            age = 11;
        }
        estimateOrder.setOrderAge(age);
        BigDecimal orderRate = rateMap.get("orderRate");
        BigDecimal moneyRate = rateMap.get("moneyRate");
        estimateOrder.setOrderCount(orderCount.intValue());
        estimateOrder.setAmountTotal(principal.longValue());
        estimateOrder.setEstimateOrderCount(orderCount.multiply(orderRate).intValue());
        estimateOrder.setEstimateAmountCount(principal.multiply(moneyRate).longValue());

        estimateOrder.setOldCollectionRate(orderRate.multiply(PERCENT).multiply(PERCENT).intValue());

        if (estimateOrder.getId() == null || estimateOrder.getId() < 1) {
            estimateOrderDao.insert(estimateOrder);
        } else {
            estimateOrderDao.updateById(estimateOrder);
        }
    }

    //计算小额历史入催率
    private HashMap<String, BigDecimal> getSmallOldCollectionRate(Date now) {
        HashMap<String, BigDecimal> rateMap = new HashMap<>();
        BigDecimal orderRate = new BigDecimal(0);
        BigDecimal moneyRate = new BigDecimal(0);
        try {
            HashMap<String, HashMap<String, BigDecimal>> collectionCountMap = getCollectionInfo(ORDER_TYPE_SMALL);
            if (!(collectionCountMap == null || collectionCountMap.isEmpty())) {
                HashMap<String, Object> param = new HashMap<>();
                param.put("endTime", now);
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.setTime(now);
                endCalendar.add(Calendar.DAY_OF_MONTH, -7);
                param.put("startTime", endCalendar.getTime());
                List<HashMap<String, Object>> orderList = dataDao.getEstimateOrder(param);
                rateMap = getRateMap(orderList, collectionCountMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rateMap;
    }

    //获得历史入催订单数量
    private HashMap<String, HashMap<String, BigDecimal>> getCollectionInfo(Byte borrowingType) {
        HashMap<String, HashMap<String, BigDecimal>> collectionCountMap = new HashMap<>();
        try {
            Date now = yyyyMMddSdf.parse(yyyyMMddSdf.format(new Date()));

            HashMap<String, Object> param = new HashMap<>();
            param.put("endDate", now);
            Calendar startDate = Calendar.getInstance();
            startDate.setTime(now);
            startDate.add(Calendar.DAY_OF_MONTH, -7);
            param.put("startDate", startDate.getTime());
            param.put("borrowingType", borrowingType);
            List<HashMap<String, Object>> orderCountList = mmanUserLoanDao.selectCollectionCountBetweenLoanEndTime(param);
            if (orderCountList != null && orderCountList.size() > 0) {
                for (HashMap<String, Object> orderInfo : orderCountList) {
                    HashMap<String, BigDecimal> countMap = new HashMap<>();
                    Date overDate = (Date) orderInfo.get("loadEndTime");
                    countMap.put("orderCount", new BigDecimal(MapUtils.getIntValue(orderInfo, "orderCount", 0)));
                    countMap.put("loanMoney", new BigDecimal(MapUtils.getString(orderInfo, "loanMoney", "0")));
                    collectionCountMap.put(yyyyMMddSdf.format(overDate), countMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return collectionCountMap;
    }

    //大额订单信息查询
    public List<HashMap<String, Object>> getBigOrderInfoList(Date start, Date end) {
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        HashMap<String, String> param = new HashMap<>();
        param.put("startDay", yyyyMMddSdf.format(start));
        end.setTime(end.getTime() - 1);
        param.put("endDay", yyyyMMddSdf.format(end));
        try {
            String response = HttpUtil.get(BIG_PATH, param);
            if (StringUtils.isNotBlank(response)) {
                JSONObject responseJson = JSONObject.parseObject(response);
                if (BIG_SUCCESS_CODE.equals(responseJson.getString("code"))) {
                    JSONArray dataList = responseJson.getJSONArray("data");
                    if (dataList != null && dataList.size() > 0) {
                        for (int i = 0; i < dataList.size(); i++) {
                            JSONObject orderInfo = dataList.getJSONObject(i);
                            HashMap<String, Object> infoMap = new HashMap<>();
                            infoMap.put("principal", orderInfo.getInteger("repayPricipleAmount") * 100);
                            infoMap.put("orderCount", orderInfo.getInteger("orderCount"));
                            infoMap.put("repaymentTime", orderInfo.getString("repaymentPlanTime"));
                            resultList.add(infoMap);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    //获得大额预估入催率
    private HashMap<String, BigDecimal> getBigOldCollectionRate(Date now) {
        HashMap<String, BigDecimal> rateMap = new HashMap<>();
        try {
            HashMap<String, HashMap<String, BigDecimal>> collectionCountMap = getCollectionInfo(ORDER_TYPE_BIG);
            if (!(collectionCountMap == null || collectionCountMap.isEmpty())) {
                Calendar startCalendar = Calendar.getInstance();
                startCalendar.setTime(now);
                startCalendar.add(Calendar.DAY_OF_MONTH, -7);
                List<HashMap<String, Object>> bigOrderList = getBigOrderInfoList(startCalendar.getTime(), now);
                rateMap = getRateMap(bigOrderList, collectionCountMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rateMap;
    }

    private HashMap<String, BigDecimal> getRateMap(List<HashMap<String, Object>> orderList, HashMap<String, HashMap<String, BigDecimal>> collectionCountMap) {
        HashMap<String, BigDecimal> rateMap = new HashMap<>();
        BigDecimal orderRate = new BigDecimal(0);
        BigDecimal moneyRate = new BigDecimal(0);
        if (orderList != null && orderList.size() > 0) {
            for (HashMap<String, Object> orderInfo : orderList) {
                Object repaymentTimeObj = orderInfo.get("repaymentTime");
                String repaymentTime = null;
                if (repaymentTimeObj instanceof Date) {
                    repaymentTime = yyyyMMddSdf.format((Date) repaymentTimeObj);
                } else {
                    repaymentTime = repaymentTimeObj.toString();
                }
                HashMap<String, BigDecimal> collectionInfo = collectionCountMap.get(repaymentTime);
                if (collectionInfo != null) {
                    BigDecimal orderCountBig = new BigDecimal(orderInfo.get("orderCount").toString());
                    if (orderCountBig.longValue() > 0) {
                        BigDecimal collectionOrderCountBig = collectionInfo.get("orderCount");
                        BigDecimal tmp = collectionOrderCountBig.divide(orderCountBig, 2, BigDecimal.ROUND_HALF_UP);
                        orderRate = orderRate.add(tmp);
                    }

                    BigDecimal orderMoneyCountBig = new BigDecimal(MapUtils.getString(orderInfo, "principal", "0"));
                    if (orderMoneyCountBig.doubleValue() > 0) {
                        BigDecimal collectionMoney = collectionInfo.get("loanMoney").multiply(PERCENT);
                        BigDecimal tmp = collectionMoney.divide(orderMoneyCountBig, 2, BigDecimal.ROUND_HALF_UP);
                        moneyRate = moneyRate.add(tmp);
                    }
                }
            }
        }
        rateMap.put("orderRate", orderRate.divide(AVG_DAYS, 2, BigDecimal.ROUND_HALF_UP));
        rateMap.put("moneyRate", moneyRate.divide(AVG_DAYS, 2, BigDecimal.ROUND_HALF_UP));
        return rateMap;
    }
}
