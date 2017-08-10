package com.info.back.vo.jxl;

import com.info.back.vo.jxl2.*;

import java.util.List;

public class ReportData {
	private Report report;
	private UserInfoCheck user_info_check;
	private List<MainService> main_service;
	private List<BehaviorCheck> behavior_check;
	private List<CollectionContact> collection_contact;
	private List<ContactList> contact_list;				//联系人列表
	private List<ApplicationCheck> application_check;
	private List<DeliverAddress> deliver_address;
	private List<TripInfo> trip_info;
	private List<CellBehavior> cell_behavior;
	private List<EbusinessExpense> ebusiness_expense;
	private List<ContactRegion> contact_region;

	public List<ContactRegion> getContact_region() {
		return contact_region;
	}

	public void setContact_region(List<ContactRegion> contactRegion) {
		contact_region = contactRegion;
	}

	public List<EbusinessExpense> getEbusiness_expense() {
		return ebusiness_expense;
	}

	public void setEbusiness_expense(List<EbusinessExpense> ebusinessExpense) {
		ebusiness_expense = ebusinessExpense;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public UserInfoCheck getUser_info_check() {
		return user_info_check;
	}

	public void setUser_info_check(UserInfoCheck userInfoCheck) {
		user_info_check = userInfoCheck;
	}

	public List<MainService> getMain_service() {
		return main_service;
	}

	public void setMain_service(List<MainService> mainService) {
		main_service = mainService;
	}

	public List<BehaviorCheck> getBehavior_check() {
		return behavior_check;
	}

	public void setBehavior_check(List<BehaviorCheck> behaviorCheck) {
		behavior_check = behaviorCheck;
	}

	public List<CollectionContact> getCollection_contact() {
		return collection_contact;
	}

	public void setCollection_contact(List<CollectionContact> collectionContact) {
		collection_contact = collectionContact;
	}

	public List<ContactList> getContact_list() {
		return contact_list;
	}

	public void setContact_list(List<ContactList> contactList) {
		contact_list = contactList;
	}

	public List<ApplicationCheck> getApplication_check() {
		return application_check;
	}

	public void setApplication_check(List<ApplicationCheck> applicationCheck) {
		application_check = applicationCheck;
	}

	public List<DeliverAddress> getDeliver_address() {
		return deliver_address;
	}

	public void setDeliver_address(List<DeliverAddress> deliverAddress) {
		deliver_address = deliverAddress;
	}

	public List<TripInfo> getTrip_info() {
		return trip_info;
	}

	public void setTrip_info(List<TripInfo> tripInfo) {
		trip_info = tripInfo;
	}

	public List<CellBehavior> getCell_behavior() {
		return cell_behavior;
	}

	public void setCell_behavior(List<CellBehavior> cellBehavior) {
		cell_behavior = cellBehavior;
	}

}
