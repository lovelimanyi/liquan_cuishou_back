package com.info.back.utils;

/**
 * 同dwz做ajax交换信息的封装类
 */
public class DwzResult {

	public static final String SUCCESS = "200";
	public static final String FAILD = "300";

	public static final String CALLBACK_CLOSECURRENT = "closeCurrent";
	public static final String CALLBACK_CLOSECURRENTDIALOG = "closeCurrentDialog";
	public static final String CALLBACK_FORWARD = "forward";

	public DwzResult(boolean bool, String message, String callbackType,
			String navTabId) {
		if (bool) {
			this.setStatusCode(DwzResult.SUCCESS);
		} else {
			this.setStatusCode(DwzResult.FAILD);
		}
		this.message = message;
		this.navTabId = navTabId;
		this.callbackType = callbackType;
		this.forwardUrl = "";
		this.rel = "";
	}

	/** 操作结果的提示编码 */
	private String statusCode;

	/** 操作结果的提示消息 */
	private String message;

	/** 操作结果后需要刷新页面的ID，对应的是rel的值 注：如果需要刷新是当前页，则不用赋值 */
	private String navTabId;

	/** 操作结果后跳转页面地址 */
	private String forwardUrl;

	/** 指定返回的rel */
	private String rel;

	/** 操作结果后的执行函数 */
	private String callbackType;

	/** 无参构造方法 */
	public DwzResult() {
	}

	public DwzResult(ServiceResult serviceResult) {
		if (serviceResult.isSuccessed()) {
			this.statusCode = SUCCESS;
		} else {
			this.statusCode = FAILD;
		}
		this.message = serviceResult.getMsg();
	}

	public DwzResult(String code, String msg) {
		this.statusCode = code;
		this.message = msg;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNavTabId() {
		return navTabId;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getCallbackType() {
		return callbackType;
	}

	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

}
