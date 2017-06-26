package com.info.back.vo.jxl2;
/**
 * 联系人区域汇总
 * @author yyf
 *
 * @version 
 */
public class ContactRegion {
	//地区名称
	private String region_loc;
	//号码数量
	private Integer region_uniq_num_cnt;
	//电话呼入次数
	private Integer region_call_in_cnt;
	//电话呼出次数
	private Integer region_call_out_cnt;
	//电话呼入时间
	private float region_call_in_time;
	//电话呼出时间
	private float region_call_out_time;
	//平均电话呼入时间
	private float region_avg_call_in_time;
	//平均电话呼出时间
	private float region_avg_call_out_time;
	//电话呼入次数百分比
	private float region_call_in_cnt_pct;
	//电话呼出次数百分比
	private float region_call_out_cnt_pct;
	//电话呼入时间百分比
	private float region_call_in_time_pct;
	//电话呼出时间百分比
	private float region_call_out_time_pct;
	
	public String getRegion_loc() {
		return region_loc;
	}
	public void setRegion_loc(String region_loc) {
		this.region_loc = region_loc;
	}
	public Integer getRegion_uniq_num_cnt() {
		return region_uniq_num_cnt;
	}
	public void setRegion_uniq_num_cnt(Integer region_uniq_num_cnt) {
		this.region_uniq_num_cnt = region_uniq_num_cnt;
	}
	public float getRegion_call_out_time() {
		return region_call_out_time;
	}
	public void setRegion_call_out_time(float region_call_out_time) {
		this.region_call_out_time = region_call_out_time;
	}
	public float getRegion_avg_call_in_time() {
		return region_avg_call_in_time;
	}
	public void setRegion_avg_call_in_time(float region_avg_call_in_time) {
		this.region_avg_call_in_time = region_avg_call_in_time;
	}
	public float getRegion_call_in_time() {
		return region_call_in_time;
	}
	public void setRegion_call_in_time(float region_call_in_time) {
		this.region_call_in_time = region_call_in_time;
	}
	public Integer getRegion_call_out_cnt() {
		return region_call_out_cnt;
	}
	public void setRegion_call_out_cnt(Integer region_call_out_cnt) {
		this.region_call_out_cnt = region_call_out_cnt;
	}
	public float getRegion_avg_call_out_time() {
		return region_avg_call_out_time;
	}
	public void setRegion_avg_call_out_time(float region_avg_call_out_time) {
		this.region_avg_call_out_time = region_avg_call_out_time;
	}
	public float getRegion_call_in_cnt_pct() {
		return region_call_in_cnt_pct;
	}
	public void setRegion_call_in_cnt_pct(float region_call_in_cnt_pct) {
		this.region_call_in_cnt_pct = region_call_in_cnt_pct;
	}
	public float getRegion_call_in_time_pct() {
		return region_call_in_time_pct;
	}
	public void setRegion_call_in_time_pct(float region_call_in_time_pct) {
		this.region_call_in_time_pct = region_call_in_time_pct;
	}
	public Integer getRegion_call_in_cnt() {
		return region_call_in_cnt;
	}
	public void setRegion_call_in_cnt(Integer region_call_in_cnt) {
		this.region_call_in_cnt = region_call_in_cnt;
	}
	public float getRegion_call_out_time_pct() {
		return region_call_out_time_pct;
	}
	public void setRegion_call_out_time_pct(float region_call_out_time_pct) {
		this.region_call_out_time_pct = region_call_out_time_pct;
	}
	public float getRegion_call_out_cnt_pct() {
		return region_call_out_cnt_pct;
	}
	public void setRegion_call_out_cnt_pct(float region_call_out_cnt_pct) {
		this.region_call_out_cnt_pct = region_call_out_cnt_pct;
	}
	
}
