package com.info.back.vo.jxl;

import java.util.List;

public class EbusinessExpense {
	private float all_count;
	private float all_amount;
	private String trans_mth;
	private List<String> all_category;
	private String category;

	public List<String> getAll_category() {
		return all_category;
	}

	public void setAll_category(List<String> allCategory) {
		all_category = allCategory;
	}

	public float getAll_count() {
		return all_count;
	}

	public void setAll_count(float allCount) {
		all_count = allCount;
	}

	public float getAll_amount() {
		return all_amount;
	}

	public void setAll_amount(float allAmount) {
		all_amount = allAmount;
	}

	public String getTrans_mth() {
		return trans_mth;
	}

	public void setTrans_mth(String transMth) {
		trans_mth = transMth;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
