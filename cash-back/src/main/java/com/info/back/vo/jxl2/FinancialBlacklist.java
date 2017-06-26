package com.info.back.vo.jxl2;

import java.util.List;
/**
 * 金融服务类机构黑名单检查
 * @author yyf
 *
 * @version 
 */
public class FinancialBlacklist {
	//是否出现
	private boolean arised;
	//黑名单机构类型
	private List<BlackType> black_type;
	
	public boolean isArised() {
		return arised;
	}
	public void setArised(boolean arised) {
		this.arised = arised;
	}
	public List<BlackType> getBlack_type() {
		return black_type;
	}
	public void setBlack_type(List<BlackType> black_type) {
		this.black_type = black_type;
	}
}
