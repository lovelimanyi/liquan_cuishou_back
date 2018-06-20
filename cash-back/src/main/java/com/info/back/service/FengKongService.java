package com.info.back.service;

import com.info.back.dao.IFengKongDao;
import com.info.back.dao.IMmanLoanCollectionOrderDao;
import com.info.back.dao.IPaginationDao;
import com.info.back.result.JsonResult;
import com.info.back.utils.BackConstant;
import com.info.back.utils.IdGen;
import com.info.constant.Constant;
import com.info.web.pojo.CollectionAdvice;
import com.info.web.pojo.FengKong;
import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.util.JedisDataClient;
import com.info.web.util.PageConfig;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FengKongService implements IFengKongService {
    private static Logger logger = Logger.getLogger(FengKongService.class);

    @Autowired
    private IPaginationDao<FengKong> paginationDao;
    @Autowired
    private IFengKongDao fengKongDao;
    @Autowired
    private IMmanLoanCollectionOrderDao mmanLoanCollectionOrderDao;

    @Override
    public PageConfig<FengKong> findPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "FengKong");
        return paginationDao.findPage("findAll", "findAllCount", params, null);
    }

    @Override
    public List<FengKong> getFengKongList() {
        return fengKongDao.getFengKongList();
    }

    @Override
    public FengKong getFengKongById(Integer id) {
        return fengKongDao.getFengKongById(id);
    }

    @Override
    public JsonResult updateFengKong(FengKong fengKong) {
        JsonResult result = new JsonResult("-1", "审核修改风控标签失败");
        try {
            if (fengKongDao.update(fengKong) > 0) {
                result.setCode("0");
                result.setMsg("审核修改风控标签成功");
            }
        } catch (Exception e) {
            logger.error("FengKongService updateFengKong", e);
        }
        return result;
    }

    @Override
    public JsonResult insert(FengKong fengKong) {
        JsonResult result = new JsonResult("-1", "添加风控标签失败");
        try {
            if (fengKongDao.getFengKongByLabel(fengKong) > 0) {
                result.setCode("-2");
                result.setMsg("该标签已经存在！");
            } else {
                if (fengKongDao.insert(fengKong) > 0) {
                    result.setCode("0");
                    result.setMsg("添加风控标签成功");
                }
            }
        } catch (Exception e) {
            logger.error("FengKongService insert", e);
        }
        return result;
    }

    @Override
    public JsonResult saveCollectionAdvice(Map<String, String> params) {
        JsonResult result = new JsonResult("-1", "添加失败");
        try {
            String orderId = params.get("id");
            MmanLoanCollectionOrder mmanLoanCollectionOrder = mmanLoanCollectionOrderDao.getOrderById(orderId);

            CollectionAdvice collectionAdvice = new CollectionAdvice();
            collectionAdvice.setId(IdGen.uuid());
            collectionAdvice.setOrderId(params.get("id"));
            collectionAdvice.setLoanId(mmanLoanCollectionOrder.getLoanId());
            collectionAdvice.setPayId(mmanLoanCollectionOrder.getPayId());
            collectionAdvice.setUserId(mmanLoanCollectionOrder.getUserId());
            collectionAdvice.setLoanUserName(mmanLoanCollectionOrder.getLoanUserName());
            collectionAdvice.setLoanUserPhone(mmanLoanCollectionOrder.getLoanUserPhone());
            collectionAdvice.setBackUserId(Integer.valueOf(params.get("backUserId")));
            collectionAdvice.setUserName(params.get("userName"));
            collectionAdvice.setFengkongIds(params.get("fengkongIds"));
            collectionAdvice.setFkLabels(params.get("fkLabels"));
            collectionAdvice.setCreateDate(new Date());
            collectionAdvice.setCollectionRecordId(params.get("collectionRecordId"));
            collectionAdvice.setStatus(params.get("status"));
            collectionAdvice.setCollectionAdviceRemark(params.get("collectionRemark"));
            if (fengKongDao.insertCollectionAdvice(collectionAdvice) > 0) {
                result.setCode("0");
                result.setMsg("添加成功");
            }
        } catch (Exception e) {
            logger.error("FengKongService insert", e);
        }
        return result;
    }

    @Override
    public int saveAdvice(CollectionAdvice advice) {
        return fengKongDao.insertAdvice(advice);
    }

    @Override
    public Map<String, Object> getFengKongLableMap() {
        List<FengKong> fengKongList = JedisDataClient.getList(BackConstant.REDIS_KEY_PREFIX, "fengKongLables");
        if (CollectionUtils.isEmpty(fengKongList)) {
            fengKongList = fengKongDao.getFengKongList();
            JedisDataClient.setList(BackConstant.REDIS_KEY_PREFIX, "fengKongLables", fengKongList, 60 * 60 * 24);
        }
        Map<String, Object> fengKongLableMap = new HashMap<>(16);
        for (FengKong lable : fengKongList) {
            fengKongLableMap.put(lable.getId().toString(), lable.getFkLabel());
        }
        return fengKongLableMap;
    }

}
