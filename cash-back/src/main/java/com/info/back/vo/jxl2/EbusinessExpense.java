package com.info.back.vo.jxl2;

import java.util.List;
/**
 * 电商月消费
 * @author yyf
 *
 * @version 
 */
public class EbusinessExpense {
	//月份
	private String trans_mth;
	//全部消费金额
	private float all_amount;
	//全部消费次数
	private Integer all_count;
	//本月商品品类
	private List<String> category;

	public String getTrans_mth() {
		return trans_mth;
	}
	public void setTrans_mth(String trans_mth) {
		this.trans_mth = trans_mth;
	}
	public float getAll_amount() {
		return all_amount;
	}
	public void setAll_amount(float all_amount) {
		this.all_amount = all_amount;
	}
	public float getAll_count() {
		return all_count;
	}

	public List<String> getCategory() {
		return category;
	}

	public void setCategory(List<String> category) {
		this.category = category;
	}

	public void setAll_count(Integer all_count) {
		this.all_count = all_count;

	}
}
