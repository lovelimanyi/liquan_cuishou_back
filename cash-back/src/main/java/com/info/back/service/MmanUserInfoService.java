package com.info.back.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.info.back.vo.jxl2.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.info.back.dao.IMmanUserInfoDao;
import com.info.back.vo.jxl.UserReport;
import com.info.web.pojo.ContactInfo;
import com.info.web.pojo.MmanUserInfo;

@Service
public class MmanUserInfoService implements IMmanUserInfoService {

    @Autowired
    private IMmanUserInfoDao mmanUserInfoDao;

    @Override
    public MmanUserInfo getUserInfoById(String id) {
        return mmanUserInfoDao.get(id);
    }

    @Override
    public MmanUserInfo getxjxuser(Long id) {
        return mmanUserInfoDao.getxjxuser(id);
    }

    @Override
    public int saveNotNull(MmanUserInfo mmanUserInfo) {
        return mmanUserInfoDao.saveNotNull(mmanUserInfo);
    }

    /**
     * 聚信立报告
     */
    @Override
    public UserReport findJxlDetail(Map<String, Object> map) {
        UserReport ur = new UserReport();
        MmanUserInfo mmanUserInfo = mmanUserInfoDao.findJxlDetail(map);
        if (mmanUserInfo != null) {
            if (mmanUserInfo.getJxlDetail() != null && !"".equals(mmanUserInfo.getJxlDetail())) {
                JSONObject jxlDetailObject = JSONObject.parseObject(mmanUserInfo.getJxlDetail());
                ur = JSONObject.toJavaObject(jxlDetailObject, UserReport.class);
                ur.setRealName(mmanUserInfo.getRealname());
                ur.setGender(mmanUserInfo.getUserSex());
                ur.setIdNumber(mmanUserInfo.getIdNumber());
                ur.setAge(mmanUserInfo.getUserAge());
            }
        }
        return ur;

    }

    /**
     * 解析聚信立报告（类型2）
     * @param map
     * @return
     */
    @Override
    public JxlUserReport parseJxlDetail(Map<String,Object> map){
        JxlUserReport jur = new JxlUserReport();
        MmanUserInfo mmanUserInfo =  mmanUserInfoDao.findJxlDetail(map);
        if(mmanUserInfo != null){
            if(mmanUserInfo.getJxlDetail() != null && !"".equals(mmanUserInfo.getJxlDetail())){
                JSONObject jxlDetailObject = JSONObject.parseObject(mmanUserInfo.getJxlDetail());
                Report report = JSONObject.toJavaObject(jxlDetailObject.getJSONObject("report"),Report.class);
                UserInfoCheck userInfoCheck = JSONObject.toJavaObject(jxlDetailObject.getJSONObject("user_info_check"),UserInfoCheck.class);
                List<ApplicationCheck> applicationCheck = JSONArray.parseArray(String.valueOf(jxlDetailObject.get("application_check")),ApplicationCheck.class);
                List<BehaviorCheck> behaviorCheck = JSONArray.parseArray(String.valueOf(jxlDetailObject.get("behavior_check")),BehaviorCheck.class);
                List<CellBehavior> cellBehavior = JSONArray.parseArray(String.valueOf(jxlDetailObject.get("cell_behavior")),CellBehavior.class);
                List<ContactRegion> contactRegion = JSONArray.parseArray(String.valueOf(jxlDetailObject.get("contact_region")),ContactRegion.class);
                List<ContactList> contactList= JSONArray.parseArray(String.valueOf(jxlDetailObject.get("contact_list")),ContactList.class);
                List<MainService> mainService = JSONArray.parseArray(String.valueOf(jxlDetailObject.get("main_service")),MainService.class);
                List<DeliverAddress> deliverAddress = JSONArray.parseArray(String.valueOf(jxlDetailObject.get("deliver_address")),DeliverAddress.class);
                List<EbusinessExpense> ebusinessExpense = JSONArray.parseArray(String.valueOf(jxlDetailObject.get("ebusiness_expense")),EbusinessExpense.class);
                List<CollectionContact> collectionContact = JSONArray.parseArray(String.valueOf(jxlDetailObject.get("collection_contact")),CollectionContact.class);
                List<TripInfo> tripInfo =JSONArray.parseArray(String.valueOf(jxlDetailObject.get("trip_info")),TripInfo.class);

                jur.setAge(mmanUserInfo.getUserAge());
                jur.setGender(mmanUserInfo.getUserSex());
                jur.setIdNumber(mmanUserInfo.getIdNumber());
                jur.setRealName(mmanUserInfo.getRealname());
                jur.setReport(report);
                jur.setUser_info_check(userInfoCheck);
                jur.setApplication_check(applicationCheck);
                jur.setBehavior_check(behaviorCheck);
                jur.setCell_behavior(cellBehavior);
                jur.setContact_region(contactRegion);
                jur.setContact_list(contactList);
                jur.setMain_service(mainService);
                jur.setDeliver_address(deliverAddress);
                jur.setEbusiness_expense(ebusinessExpense);
                jur.setCollection_contact(collectionContact);
                jur.setTrip_info(tripInfo);
            }
        }
        return jur;
    }

    @Override
    public String getAddressByIDNumber(String idNumber) {
        return mmanUserInfoDao.getAddressByIDNumber(idNumber);
    }

    @Override
    public List<ContactInfo> getContactInfo(String phoneNum) {
        return mmanUserInfoDao.getContactInfo(phoneNum);
    }


}
