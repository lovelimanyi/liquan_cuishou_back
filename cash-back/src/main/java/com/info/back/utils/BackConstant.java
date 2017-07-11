package com.info.back.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.info.web.pojo.SysDict;

public class BackConstant {
	public static final String DEL_FLAG_ON = "0";// 删除标志 0：有效

	public final static String ON = "1";// 开关-开

	public final static String OFF = "0";// 开关-关


	public static final String CREDITLOANAPPLY_OVERDUE = "4";// 逾期

	public static final Map<String,String> groupNameMap = new LinkedHashMap<String,String>();

	public static final Map<String,String> groupNameTypeMap = new LinkedHashMap<String,String>();

	public static final Map<String,String> groupStatusMap = new HashMap<String,String>();//催收员状态

	public static final Map<String,String> typeMap = new LinkedHashMap<String,String>(); // 分期类型

	public static final Map<String,String> typeNameMap = new LinkedHashMap<String,String>(); // 分期名称

	public final static String XJX_JXL_STATUS_REFUSE = "0";// 现金侠聚信立通讯录申请审核状态 拒绝

	public final static String XJX_JXL_STATUS_APPLY = "1";// 现金侠聚信立通讯录申请审核状态 申请

	public final static String XJX_JXL_STATUS_AGREE = "2";// 现金侠聚信立通讯录申请审核状态 同意



	public final static String XJX_OVERDUE_LEVEL_S1 = "3";// 现金侠逾期等级或催收组S1

	public final static String XJX_OVERDUE_LEVEL_S2 = "4";// 现金侠逾期等级或催收组S2

	public final static String XJX_OVERDUE_LEVEL_M1_M2 = "5";// 现金侠逾期等级或催收组M1_M2

	public final static String XJX_OVERDUE_LEVEL_M2_M3 = "6";// 现金侠逾期等级或催收组M2_M3

	public final static String XJX_OVERDUE_LEVEL_M3P = "7";// 现金侠逾期等级或催收组M3+

	public final static String XJX_OVERDUE_LEVEL_M6P = "8";// 现金侠逾期等级或催收组M6+

	public final static String XJX_OVERDUE_LEVEL_S1_OR_S2 = "34";// 现金侠逾期等级或催收组S1或S2，这个仅用于条件查询



	public final static Integer COLLECTION_ROLE_ID=10021;//催收员的角色ID

	public final static Integer OUTSOURCE_MANAGER_ROLE_ID=10022;//委外经理角色ID

	public final static Integer COLLECTION_MANAGE_ROLE_ID=10025;//催收主管角色ID

	public final static Integer MANAGER_ROLE_ID=10026;//经理角色ID

	public final static String XJX_COLLECTION_ORDER_STATE_SUCCESS = "4";// 现金侠催收状态催收成功

	public final static String XJX_COLLECTION_ORDER_STATE_PAYING = "5";// 现金侠催收状态续期（续期中不管之前怎样现在不能操作）

	public final static String XJX_COLLECTION_ORDER_STATE_ING = "1";// 现金侠催收状态催收中

	public final static String XJX_COLLECTION_ORDER_STATE_PROMISE = "2";// 现金侠催收状态承诺还款
	public final static String XJX_COLLECTION_ORDER_STATE_OUTSIDE = "3";// 现金侠催收状态委外中

	public final static String XJX_COLLECTION_ORDER_STATE_WAIT = "0";// 现金侠催收状态待催收


	public final static String XJX_COLLECTION_STATUS_MOVE_TYPE_IN = "1";// 现金侠催收状态流转类型入催

	public final static String XJX_COLLECTION_STATUS_MOVE_TYPE_CONVERT = "2";// 现金侠催收状态流转类型逾期等级转换

	public final static String XJX_COLLECTION_STATUS_MOVE_TYPE_OTHER = "3";// 现金侠催收状态流转类型转单

	public final static String XJX_COLLECTION_STATUS_MOVE_TYPE_OUTSIDE = "4";// 现金侠催收状态流转类型委外

	public final static String XJX_COLLECTION_STATUS_MOVE_TYPE_SUCCESS = "5";// 现金侠催收状态流转类型催收完成

	public static final Map<String,Object> backUserRolename = new HashMap<String,Object>();  //  后台用户角色名称

	public static final Map<String,Object> problemFeedBackType = new HashMap<String,Object>();  //  后台用户角色名称

	public static final Map<String,Object> problemFeedBackStatus = new HashMap<String,Object>();  //  反馈问题类型

	public static final Map<String,Object> fengKongLabels = new HashMap<String,Object>();  //  风控标签


	static{
		groupNameMap.put(BackConstant.XJX_OVERDUE_LEVEL_S1,"S1");
		groupNameMap.put(BackConstant.XJX_OVERDUE_LEVEL_S2,"S2");
		groupNameMap.put(BackConstant.XJX_OVERDUE_LEVEL_M1_M2,"M1-M2");
		groupNameMap.put(BackConstant.XJX_OVERDUE_LEVEL_M2_M3,"M2-M3");
		groupNameMap.put(BackConstant.XJX_OVERDUE_LEVEL_M3P,"M3+");
		groupNameMap.put(BackConstant.XJX_OVERDUE_LEVEL_M6P,"M6+");

		groupNameTypeMap.put("33","S1");
		groupNameTypeMap.put("43","S2-S1");
		groupNameTypeMap.put("44","S2-S2");
		groupNameTypeMap.put("55","M1-M2");
		groupNameTypeMap.put("66","M2-M3");
		groupNameTypeMap.put("77","M3+");

		groupStatusMap.put("0", "禁用");
		groupStatusMap.put("1", "启用");
		groupStatusMap.put("3", "删除");

		typeMap.put("2","二期还款");
		typeMap.put("3","三期还款");
		typeMap.put("4","四期还款");

		typeNameMap.put("1","一期");
		typeNameMap.put("2","二期");
		typeNameMap.put("3","三期");
		typeNameMap.put("4","四期");
	}

	public final static String XJX_COLLECTION_ORDER_USED = "1";// 派单状态-有效
	public final static String XJX_COLLECTION_ORDER_DELETED = "0";// 派单状态-无效

	/**
	 * 获取字典Map
	 * @return
	 */
	public static HashMap<String, String> orderState(List<SysDict> dictList){
		HashMap<String, String> dictMap=new HashMap<String, String>();
		for(SysDict dict:dictList){
			dictMap.put(dict.getValue(), dict.getLabel());
		}
		return dictMap;
	}


	static {
		// 后台用户角色名称
		backUserRolename.put("10001","系统管理员");
		backUserRolename.put("10019","经理");
		backUserRolename.put("10021","催收员");
		backUserRolename.put("10022","委外经理");
		backUserRolename.put("10023","财务");
		backUserRolename.put("10024","客服");
		backUserRolename.put("10025","技术");

		// 反馈问题类型
		problemFeedBackType.put("1","电催类");
		problemFeedBackType.put("2","财务类");
		problemFeedBackType.put("3","技术类");

		// 反馈问题状态
		problemFeedBackStatus.put("0","未解决");
		problemFeedBackStatus.put("1","已解决");

		// 风控标签
		fengKongLabels.put("%失联%","1");
		fengKongLabels.put("%难联系%","2");
		fengKongLabels.put("%首通后屏蔽%","3");
		fengKongLabels.put("%首通前屏蔽%","4");
		fengKongLabels.put("%联系人不匹配%","5");
		fengKongLabels.put("%联系人重号%","6");
		fengKongLabels.put("%疑似黑中介办理%","7");
		fengKongLabels.put("%各平台欠款%","8");
		fengKongLabels.put("%各银行欠款%","9");
		fengKongLabels.put("%无赖不还%","10");
		fengKongLabels.put("%诚信度低%","11");
		fengKongLabels.put("%无偿还能力%","12");
		fengKongLabels.put("%忘记还款%","13");

	}

}
