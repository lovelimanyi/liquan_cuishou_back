package com.info.web.synchronization.service;

import com.info.web.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by IntelliJ IDEA
 * User : zhangsh
 * Date : 2017/3/6 0006
 * Time : 21:15
 */

@Service
public class TwoMinSyncOperateService {
    private static Logger loger = Logger.getLogger(TwoMinSyncOperateService.class);
    @Autowired
    private DataSyncService dataSyncService;
    @Autowired
    private DataService dataService;
    @Autowired

    public void twoMinSyncOverdue() {
        loger.error("4点到24点-每2分钟跑逾期(部分还款)..." + DateUtil.getDateFormat(new Date(), "yyyy-MM-dd hh:mm:ss"));

        try {
            this.dataSyncService.syncOverdueDate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void twoMinSyncOther() {
        loger.error(" 每2分钟跑续期，还款，代扣..." + DateUtil.getDateFormat(new Date(), "yyyy-MM-dd hh:mm:ss"));

        try {
            this.dataService.syncDate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
