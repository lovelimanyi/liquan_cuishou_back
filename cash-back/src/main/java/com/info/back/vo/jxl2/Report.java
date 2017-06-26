package com.info.back.vo.jxl2;
/**
 * 报告基本信息
 * @author yyf
 *
 * @version 
 */
public class Report {
    private String token;
    //报告版本
	private String version;
	//报告生成时间
	private String update_time;
	//报告编号
	private String rpt_id;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getRpt_id() {
		return rpt_id;
	}
	public void setRpt_id(String rpt_id) {
		this.rpt_id = rpt_id;
	}
	
}
