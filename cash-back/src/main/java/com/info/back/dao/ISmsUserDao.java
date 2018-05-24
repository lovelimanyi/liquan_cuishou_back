package com.info.back.dao;

import com.info.web.pojo.SmsUser;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface ISmsUserDao {
    /**
     * 查询手机号
     *
     * @param params
     * @return
     */
    List<String> findPhones(HashMap<String, Object> params);

    /**
     * 查询从N行到X行的数据
     *
     * @param params
     * @return
     */
    List<SmsUser> findPartList(HashMap<String, Object> params);

    /**
     * 查询Id
     *
     * @param params
     * @return
     */
    List<Integer> findIds(HashMap<String, Object> params);

    /**
     * 批量更新
     *
     * @param params
     * @return
     */
    int batchInsert(HashMap<String, Object> params);

    int findAllCount(HashMap<String, Object> params);

    /**
     * 获得逗号隔开的手机号码
     *
     * @param params
     * @return
     */
    String findStringPhones(HashMap<String, Object> params);

    /**
     * 获得分号隔开的手机号码
     *
     * @param params begin从第几行开始<br>
     *               end到底几行结束<br>
     *               比如想获取前三条数据，那么sql语句是limit 0,3,所以在传入参数为1,3，然后构造查询参数为1-1=0；3-0=3，即0,3
     * @return
     */
    String findPhonesForSend(HashMap<String, Object> params);

    /**
     * 插入一条短信发送记录
     *
     * @param msg
     */
    void insert(SmsUser msg);

    /**
     * 查询该借款当天已发短信次数
     *
     * @param orderId
     * @return
     */
    int getSendMsgCount(String orderId);

    /**
     * 查询该借款的所有短信数
     *
     * @param orderId
     * @return
     */
    int getSendTotalMsgCount(String orderId);
}
