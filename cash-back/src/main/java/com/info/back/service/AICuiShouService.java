package com.info.back.service;



import com.info.back.dao.IMmanLoanCollectionCompanyDao;
import com.info.back.dao.IMmanLoanCollectionOrderDao;
import com.info.back.utils.MerchantNoUtils;
import com.info.config.PayContents;
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

    private static String accessToken = "";
    private static String requestUrl = PayContents.AI_CUISHOU_URL;
    //    private static String requestUrl = "http://101.132.86.237:8800/external/openApi/job/batch";//线上环境
//    private static String requestUrl = "http://external.senseinfo.cn:8080/external/openApi/job/batch";//测试环境
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


                if(entry.getValue().toString().equals("CJXJX")){
                    accessToken = "331b3792-0ae0-46d0-8fbd-64d773f3";
                    map.put("loanChannel", "JiSuJinZhangGui");
                }else if (entry.getValue().toString().equals("JYB")){
                    accessToken = "d2c3f2f5-d266-482a-9a67-f3cc1d19";
                    map.put("loanChannel", "JiYongBang");
                }else if (entry.getValue().toString().equals("JXX")){
                    accessToken = "b443054c-1825-401c-9a74-724e75e8";
//                    accessToken = "5beb20d4-d0b5-4de0-9657-7ea16f0a";//测试环境
                    map.put("loanChannel", "JinXiaoXia");
                }else if (entry.getValue().toString().equals("JQB")){
                    accessToken = "e88b2a33-438f-41a1-ad0b-f8ab276b";
                    map.put("loanChannel", "JiSuBiXia");
                }else if (entry.getValue().toString().equals("TXLC")){
                    accessToken = "4a3acf98-8de6-46a9-92d9-7ff65b24";
                    map.put("loanChannel", "ZhuYouQian");
                }else if (entry.getValue().toString().equals("YMJK")){
                    accessToken = "fc6d4494-7425-421f-aa2a-4449df6a";
                    map.put("loanChannel", "YiMiaoJieKuan");
                }else if (entry.getValue().toString().equals("TKJ")){
                    accessToken = "eb5014f7-8942-47e2-89d3-2be79923";
                    map.put("loanChannel", "TiKuanJi");
                }else if (entry.getValue().toString().equals("CJS")){
                    accessToken = "d3de9064-58b1-4bcb-9ebd-a22af6a1";
                    map.put("loanChannel", "CaiJiSong");
                }

                List<JobDomain> JobDomainList = mmanLoanCollectionOrderDao.getBatchCommitData(map);
                if(null != JobDomainList && JobDomainList.size() > 0){
                    List<JobList> dataList = new ArrayList<>();
                    for (JobDomain jobDomain : JobDomainList) {
                        JobList jobList = new JobList();
                        JobData jobData = new JobData();
                        BeanUtils.copyProperties(jobDomain,jobList);
                        BeanUtils.copyProperties(jobDomain,jobData);
                        jobList.setJobData(jobData);
                        dataList.add(jobList);
                    }
//                    SenseClient client = new DefaultSenseClient(entry.getValue(), accessToken);//第一个参数是corpCode
//                    BatchJobRequest batchJobRequest = new BatchJobRequest(requestUrl, templateCode, JSON.toJSONString(dataList));
//                    try{
//                        client.execute(batchJobRequest);
//                    }catch (Exception e){
//                        if("http://www.baidu.com".equals(requestUrl)){
//                            logger.info("测试环境请求地址不匹配，非bug");
//                        }
//                    }
                }
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
