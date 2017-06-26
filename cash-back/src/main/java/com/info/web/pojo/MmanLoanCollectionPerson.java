package com.info.web.pojo;

import com.info.back.utils.BackConstant;



/**
 * 催收员entity
 *
 * @version 2016-11-15
 */
public class MmanLoanCollectionPerson {
   

	private String id;
	
	private String notMineId;//转派催收人剔除自己使用

    private String userId;//用户uid
    
    private String loginName;//登录名（关联用户uid）

    private String username;//姓名

    private String phone;//手机号--关联用户表存催收角色的用户登录名和密码，结构ctn手机号

    private String groupLevel;//分组 3:M1;4:M2;5:M3;6:坏账(逾期表字典)

    private String companyId;//催收公司id
    
    private String companyName;//催收公司名称（关联催收公司cid）

    private String operatorName;//操作人(暂时不用)

    private String userStatus;//状态 0禁用 1可用

    private String realName;//委外机构人员真实姓名
    
    private Integer currentUnCompleteCount;//当前未处理订单量
    
    private Integer todayAssignedCount;//今日派到手里的订单数(包括已完成的),需要单独查询
    
    private Integer todayLimitCount;//当前公司催收组下每人每天上限,需要单独查询
    
    private String groupName;//用户组名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotMineId() {
		return notMineId;
	}

	public void setNotMineId(String notMineId) {
		this.notMineId = notMineId;
	}

	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGroupLevel() {
		return groupLevel;
	}

	public void setGroupLevel(String groupLevel) {
		this.groupLevel = groupLevel;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

	public Integer getCurrentUnCompleteCount() {
		return currentUnCompleteCount;
	}

	public void setCurrentUnCompleteCount(Integer currentUnCompleteCount) {
		this.currentUnCompleteCount = currentUnCompleteCount;
	}

	public Integer getTodayAssignedCount() {
		return todayAssignedCount;
	}

	public void setTodayAssignedCount(Integer todayAssignedCount) {
		this.todayAssignedCount = todayAssignedCount;
	}

	public Integer getTodayLimitCount() {
		return todayLimitCount;
	}

	public void setTodayLimitCount(Integer todayLimitCount) {
		this.todayLimitCount = todayLimitCount;
	}

	public String getGroupName() {
		
		return BackConstant.groupNameMap.get(this.groupLevel);
		
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
	
    
}