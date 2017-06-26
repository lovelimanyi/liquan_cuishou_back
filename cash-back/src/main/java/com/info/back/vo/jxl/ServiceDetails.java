package com.info.back.vo.jxl;

public class ServiceDetails {
	private Integer interact_cnt;
	private String interact_mth;

	public Integer getInteract_cnt() {
		return interact_cnt;
	}

	public void setInteract_cnt(Integer interactCnt) {
		interact_cnt = interactCnt;
	}

	public String getInteract_mth() {
		return interact_mth;
	}

	public void setInteract_mth(String interactMth) {
		interact_mth = interactMth;
	}

	@Override
	public String toString() {
		return "("+interact_mth+","+interact_cnt+")";
	}

}
