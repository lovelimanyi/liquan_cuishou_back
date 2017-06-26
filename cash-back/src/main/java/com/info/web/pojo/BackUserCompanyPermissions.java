package com.info.web.pojo;
/**
 * 用户所以查询公司信息的关联关系表
 * @author Administrator
 */
public class BackUserCompanyPermissions {
	private Integer id;
	private Integer userId;
	private String companyId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
}
