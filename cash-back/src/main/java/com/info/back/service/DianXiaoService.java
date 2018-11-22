package com.info.back.service;

import com.info.back.dao.IBackUserDao;
import com.info.back.dao.IDianXiaoDao;
import com.info.back.dao.IPaginationDao;
import com.info.back.result.JsonResult;
import com.info.back.utils.BackConstant;
import com.info.back.utils.IdGen;
import com.info.constant.Constant;
import com.info.web.pojo.DianXiaoBackUser;
import com.info.web.pojo.DianXiaoOrder;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/11/14 0014下午 10:42
 */
@Service
public class DianXiaoService implements IDianXiaoService {
    protected Logger logger = Logger.getLogger(DianXiaoService.class);

    @Autowired
    private IPaginationDao paginationDao;
    @Autowired
    private IDianXiaoDao dianXiaoDao;
    @Autowired
    private IBackUserDao backUserDao;


    @Override
    public PageConfig<DianXiaoOrder> getDianXiaoPage(HashMap<String, Object> params) {
        Object dianXiao = params.put(Constant.NAME_SPACE, "DianXiao");
        return paginationDao.findPage("findDianXiaoPage", "findDianXiaoCount", params,
                null);
    }

    @Override
    public DianXiaoOrder getDianXiaoOrder(HashMap<String, Object> params) {
        return dianXiaoDao.getDianXiaoOrder(params);
    }

    @Override
    public JsonResult updateDianXiaoOrder(HashMap<String, Object> params) {
        JsonResult result = new JsonResult("-1", "电销订单修改失败");
        int count = dianXiaoDao.updateDianXiaoOrder(params);
        if (count > 0){
            result.setCode("0");
            result.setMsg("修改成功");
        }
        return result;
    }

    @Override
    public List<DianXiaoOrder> getDianXiaoOrderList(HashMap<String, Object> params) {
        return dianXiaoDao.getDianXiaoOrderList(params);
    }

    @Override
    public int getDianxiaoOrderCount(HashMap<String, Object> params) {

        return dianXiaoDao.getDianxiaoOrderCount(params);
    }

    /**
     * 电销订单派单逻辑
     * @param borrowOrder 业务端订单
     * */
    @Override
    public boolean dispatchDianXiaoOrder(HashMap<String, Object> borrowOrder) {
        try {
            //查出当前可用电销员，并按当前催收员今日派单量升序排序。取今日派单量最小的催收员
            Map<String, Object> map = new HashedMap();
            map.put("roleId", BackConstant.DIAN_XIAO_ROLE_ID);
            DianXiaoBackUser dianXiaoBackUser = backUserDao.dianXiaoBackUserByOrderCount(map);
            if (dianXiaoBackUser != null){
                //构造电催订单
                DianXiaoOrder order = new DianXiaoOrder();
                order.setId(IdGen.uuid());
                order.setLoanId(borrowOrder.get("id").toString());
                order.setLoanMoney(new BigDecimal(Integer.parseInt(String.valueOf(borrowOrder.get("money_amount")))).divide(new BigDecimal(100)));
                order.setLoanServiceCharge(new BigDecimal(Integer.parseInt(String.valueOf(borrowOrder.get("loan_interests")))).divide(new BigDecimal(100)));
                order.setLoanUserName(borrowOrder.get("realname").toString());
                order.setLoanUserPhone(borrowOrder.get("user_phone").toString());
                order.setLoanStartDate(DateUtil.getDateTimeFormat(String.valueOf(borrowOrder.get("loan_time")), "yyyy-MM-dd HH:mm:ss"));
                order.setLoanEndDate(DateUtil.getDateTimeFormat(String.valueOf(borrowOrder.get("loan_end_time")), "yyyy-MM-dd HH:mm:ss"));
                order.setOrderStatus(1);
                order.setCurrentCollectionUserId(dianXiaoBackUser.getUuid());
                order.setCurrentCollectionUserName(dianXiaoBackUser.getUserName());
                order.setMerchantNo(borrowOrder.get("merchant_number").toString());
                order.setCreateDate(new Date());
                dianXiaoDao.insertDianXiaoOrder(order);
                return true;
            }else {
                logger.error("无可用电销员!!!");
                return false;
            }
        }catch (Exception e){
            logger.error("dispatchDianXiaoOrder-exception:"+borrowOrder,e);
            return false;
        }
    }


}
