package com.info.vo.bbd;

public class BBD {
	private String msg;
	private String rsize;
	private String total;
	private DataObj[] results;
	private String err_code;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRsize() {
		return rsize;
	}

	public void setRsize(String rsize) {
		this.rsize = rsize;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public DataObj[] getResults() {
		return results;
	}

	public void setResults(DataObj[] results) {
		this.results = results;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String errCode) {
		err_code = errCode;
	}

}
