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
//    private static String accessToken = "5beb20d4-d0b5-4de0-9657-7ea16f0a";
//    private static String requestUrl = "http://external.senseinfo.cn:8080/external/openApi/job/batch";

    private static String accessToken = "";
    private static String requestUrl = "http://101.132.86.237:8800/external/openApi/job/batch";
    private static String templateCode = "HM0";
    @Autowired
    IMmanLoanCollectionCompanyDao mmanLoanCollectionCompanyDao;
    @Autowired
    IMmanLoanCollectionOrderDao mmanLoanCollectionOrderDao;


    @Override
    public String batchCommitData() {
        try {
            logger.info("aiCuiShou   batchCommitData开始2......");;
            List<String> current_collection_user_id_list = mmanLoanCollectionCompanyDao.getBackUserUUId();

            Map<String, String> merchantNoMap = MerchantNoUtils.getMerchantNoMap();
            for (Map.Entry<String, String> entry : merchantNoMap.entrySet()) {
                Map<String, Object> map = new HashedMap();
                map.put("merchantNo", entry.getKey());
                map.put("current_collection_user_id_list", current_collection_user_id_list);
                List<JobDomain> JobDomainList = mmanLoanCollectionOrderDao.getBatchCommitData(map);


                if(entry.getValue().toString().equals("cjxjx")){
                    accessToken = "331b3792-0ae0-46d0-8fbd-64d773f3";
                }else if (entry.getValue().toString().equals("jyb")){
                    accessToken = "d2c3f2f5-d266-482a-9a67-f3cc1d19";
                }else if (entry.getValue().toString().equals("jxx")){
                    accessToken = "b443054c-1825-401c-9a74-724e75e8";
                }else if (entry.getValue().toString().equals("jqb")){
                    accessToken = "e88b2a33-438f-41a1-ad0b-f8ab276b";
                }else if (entry.getValue().toString().equals("txlc")){
                    accessToken = "4a3acf98-8de6-46a9-92d9-7ff65b24";
                }else if (entry.getValue().toString().equals("ymjk")){
                    accessToken = "fc6d4494-7425-421f-aa2a-4449df6a";
                }else if (entry.getValue().toString().equals("tkj")){
                    accessToken = "eb5014f7-8942-47e2-89d3-2be79923";
                }else if (entry.getValue().toString().equals("cjs")){
                    accessToken = "d3de9064-58b1-4bcb-9ebd-a22af6a1";
                }

                List<JobList> dataList = new ArrayList<>();
                for (JobDomain jobDomain : JobDomainList) {
                    JobList jobList = new JobList();
                    JobData jobData = new JobData();
                    BeanUtils.copyProperties(jobDomain,jobList);
                    BeanUtils.copyProperties(jobDomain,jobData);
                    jobList.setJobData(jobData);
                    dataList.add(jobList);
                }
                SenseClient client = new DefaultSenseClient(entry.getValue(), accessToken);
//                SenseClient client = new DefaultSenseClient("JXX", accessToken);
                BatchJobRequest batchJobRequest = new BatchJobRequest(requestUrl, templateCode, JSON.toJSONString(dataList));
                client.execute(batchJobRequest);
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
