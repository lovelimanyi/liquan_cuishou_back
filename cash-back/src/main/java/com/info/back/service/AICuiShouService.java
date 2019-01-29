package com.info.back.service;

import cn.senseinfo.api.DefaultSenseClient;
import cn.senseinfo.api.SenseClient;
import cn.senseinfo.api.request.BatchJobRequest;
import com.alibaba.fastjson.JSON;
import com.info.back.dao.IMmanLoanCollectionCompanyDao;
import com.info.back.dao.IMmanLoanCollectionOrderDao;
import com.info.back.utils.MerchantNoUtils;
import com.info.web.pojo.JobData;
import com.info.web.pojo.JobDomain;
import com.info.web.pojo.JobList;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class AICuiShouService implements IAICuiShouService {

    private static Logger logger = Logger.getLogger(AICuiShouService.class);

    //    private static String corpCode = "JXX";
    private static String accessToken = "5beb20d4-d0b5-4de0-9657-7ea16f0a";
    private static String requestUrl = "http://external.senseinfo.cn:8080/external/openApi/job/batch";
    private static String templateCode = "HM0";
    @Autowired
    IMmanLoanCollectionCompanyDao mmanLoanCollectionCompanyDao;
    @Autowired
    IMmanLoanCollectionOrderDao mmanLoanCollectionOrderDao;


    @Override
    public String batchCommitData() {
        try {
            logger.info("aiCuiShou   batchCommitData开始2......");
            List<String> current_collection_user_id_list = mmanLoanCollectionCompanyDao.getBackUserUUId();

            Map<String, String> merchantNoMap = MerchantNoUtils.getMerchantNoMap();
            for (Map.Entry<String, String> entry : merchantNoMap.entrySet()) {
                Map<String, Object> map = new HashedMap();
                map.put("merchantNo", entry.getKey());
                map.put("current_collection_user_id_list", current_collection_user_id_list);
                List<JobDomain> JobDomainList = mmanLoanCollectionOrderDao.getBatchCommitData(map);

                List<JobList> dataList = new ArrayList<>();
                for (JobDomain jobDomain : JobDomainList) {
                    JobList jobList = new JobList();
                    JobData jobData = new JobData();
                    BeanUtils.copyProperties(jobDomain,jobList);
                    BeanUtils.copyProperties(jobDomain,jobData);
                    jobList.setJobData(jobData);
                    dataList.add(jobList);
                }
//                SenseClient client = new DefaultSenseClient(entry.getValue(), accessToken);
                SenseClient client = new DefaultSenseClient("JXX", accessToken);
                BatchJobRequest batchJobRequest = new BatchJobRequest(requestUrl, templateCode, JSON.toJSONString(dataList));
                client.execute(batchJobRequest);
                System.out.println("555");
            }
            logger.info("aiCuiShou   batchCommitData结束......");
            return "提交批量任务完成";
        } catch (Exception e) {
            logger.error("提交批量任务处理异常......");
            e.printStackTrace();
            return "提交批量任务处理异常";
        }
    }
}
