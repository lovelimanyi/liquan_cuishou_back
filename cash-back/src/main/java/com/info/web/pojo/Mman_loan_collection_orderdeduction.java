package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Mman_loan_collection_orderdeduction {
    private String id;

    private String loanrealname;

    private String loanuserphone;

    private BigDecimal returnmoney;

    private BigDecimal deductionmoney;

    private Date createtime;

    private String deductionremark;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoanrealname() {
		return loanrealname;
	}

	public void setLoanrealname(String loanrealname) {
		this.loanrealname = loanrealname;
	}

	public String getLoanuserphone() {
		return loanuserphone;
	}

	public void setLoanuserphone(String loanuserphone) {
		this.loanuserphone = loanuserphone;
	}

	public BigDecimal getReturnmoney() {
		return returnmoney;
	}

	public void setReturnmoney(BigDecimal returnmoney) {
		this.returnmoney = returnmoney;
	}

	public BigDecimal getDeductionmoney() {
		return deductionmoney;
	}

	public void setDeductionmoney(BigDecimal deductionmoney) {
		this.deductionmoney = deductionmoney;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getDeductionremark() {
		return deductionremark;
	}

	public void setDeductionremark(String deductionremark) {
		this.deductionremark = deductionremark;
	}
		
}