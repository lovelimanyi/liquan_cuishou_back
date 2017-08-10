package com.info.back.vo.jxl2;

import com.info.back.vo.jxl.*;

import java.util.List;


/**
 * 聚信立报告总VO类型2
 * @author yyf
 *
 * @version 
 */
public class JxlUserReport {
    private String realName;
    private String gender;
    private String idNumber;
    private Integer age;
	//报告基本信息
	private Report report;
	//用户申请表检测
	private List<ApplicationCheck> application_check;
	//用户信息检测
	private UserInfoCheck user_info_check;
	//用户行为检测
	private List<BehaviorCheck> behavior_check;
	//运营商数据整理
	private List<CellBehavior> cell_behavior;
	//联系人区域汇总
	private List<ContactRegion> contact_region;
	//运营商联系人通话详情
	private List<ContactList> contact_list;
	//常用服务
	private List<MainService> main_service;
	//电商地址分析
	private List<DeliverAddress> deliver_address;
	//电商月消费
	private List<EbusinessExpense> ebusiness_expense;
	//联系人名单
	private List<CollectionContact> collection_contact;
	//出行分析
	private List<TripInfo> trip_info;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public List<ApplicationCheck> getApplication_check() {
        return application_check;
    }

    public void setApplication_check(List<ApplicationCheck> application_check) {
        this.application_check = application_check;
    }

    public UserInfoCheck getUser_info_check() {
        return user_info_check;
    }

    public void setUser_info_check(UserInfoCheck user_info_check) {
        this.user_info_check = user_info_check;
    }

    public List<BehaviorCheck> getBehavior_check() {
        return behavior_check;
    }

    public void setBehavior_check(List<BehaviorCheck> behavior_check) {
        this.behavior_check = behavior_check;
    }

    public List<CellBehavior> getCell_behavior() {
        return cell_behavior;
    }

    public void setCell_behavior(List<CellBehavior> cell_behavior) {
        this.cell_behavior = cell_behavior;
    }

    public List<ContactRegion> getContact_region() {
        return contact_region;
    }

    public void setContact_region(List<ContactRegion> contact_region) {
        this.contact_region = contact_region;
    }

    public List<ContactList> getContact_list() {
        return contact_list;
    }

    public void setContact_list(List<ContactList> contact_list) {
        this.contact_list = contact_list;
    }

    public List<MainService> getMain_service() {
        return main_service;
    }

    public void setMain_service(List<MainService> main_service) {
        this.main_service = main_service;
    }

    public List<DeliverAddress> getDeliver_address() {
        return deliver_address;
    }

    public void setDeliver_address(List<DeliverAddress> deliver_address) {
        this.deliver_address = deliver_address;
    }

    public List<EbusinessExpense> getEbusiness_expense() {
        return ebusiness_expense;
    }

    public void setEbusiness_expense(List<EbusinessExpense> ebusiness_expense) {
        this.ebusiness_expense = ebusiness_expense;
    }

    public List<CollectionContact> getCollection_contact() {
        return collection_contact;
    }

    public void setCollection_contact(List<CollectionContact> collection_contact) {
        this.collection_contact = collection_contact;
    }

    public List<TripInfo> getTrip_info() {
        return trip_info;
    }

    public void setTrip_info(List<TripInfo> trip_info) {
        this.trip_info = trip_info;
    }
}
