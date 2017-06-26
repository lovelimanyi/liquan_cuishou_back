package com.info.back.vo.jxl2;

public class Behavior {
	//运营商
	private String cell_operator;
	//运营商（中文）
	private String cell_operator_zh;
	//号码
	private String cell_phone_num;
	//归属地
	private String cell_loc;
	//月份
	private String cell_mth; 
	//呼叫次数
	private Integer call_cnt;
	//主叫次数
	private Integer call_out_cnt;
	//主叫时间
	private float call_out_time;
	//被叫次数
	private Integer call_in_cnt;
	//被叫时间
	private float call_in_time;
	//流量
	private float net_flow;
	//短信数目
	private Integer sms_cnt;
	//话费消费
	private float total_amount;
	
	public String getCell_operator() {
		return cell_operator;
	}
	public void setCell_operator(String cell_operator) {
		this.cell_operator = cell_operator;
	}
	public String getCell_operator_zh() {
		return cell_operator_zh;
	}
	public void setCell_operator_zh(String cell_operator_zh) {
		this.cell_operator_zh = cell_operator_zh;
	}
	public String getCell_phone_num() {
		return cell_phone_num;
	}
	public void setCell_phone_num(String cell_phone_num) {
		this.cell_phone_num = cell_phone_num;
	}
	public String getCell_loc() {
		return cell_loc;
	}
	public void setCell_loc(String cell_loc) {
		this.cell_loc = cell_loc;
	}
	public String getCell_mth() {
		return cell_mth;
	}
	public void setCell_mth(String cell_mth) {
		this.cell_mth = cell_mth;
	}
	public Integer getCall_cnt() {
		return call_cnt;
	}
	public void setCall_cnt(Integer call_cnt) {
		this.call_cnt = call_cnt;
	}
	public Integer getCall_out_cnt() {
		return call_out_cnt;
	}
	public void setCall_out_cnt(Integer call_out_cnt) {
		this.call_out_cnt = call_out_cnt;
	}
	public float getCall_out_time() {
		return call_out_time;
	}
	public void setCall_out_time(float call_out_time) {
		this.call_out_time = call_out_time;
	}
	public Integer getCall_in_cnt() {
		return call_in_cnt;
	}
	public void setCall_in_cnt(Integer call_in_cnt) {
		this.call_in_cnt = call_in_cnt;
	}
	public float getCall_in_time() {
		return call_in_time;
	}
	public void setCall_in_time(float call_in_time) {
		this.call_in_time = call_in_time;
	}
	public float getNet_flow() {
		return net_flow;
	}
	public void setNet_flow(float net_flow) {
		this.net_flow = net_flow;
	}
	public Integer getSms_cnt() {
		return sms_cnt;
	}
	public void setSms_cnt(Integer sms_cnt) {
		this.sms_cnt = sms_cnt;
	}
	public float getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(float total_amount) {
		this.total_amount = total_amount;
	}
	
	
	
}
