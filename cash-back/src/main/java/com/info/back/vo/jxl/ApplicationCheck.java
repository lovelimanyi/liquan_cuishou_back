package com.info.back.vo.jxl;

import net.sf.json.JSONObject;

public class ApplicationCheck {
	private String app_point;
	private JSONObject check_points;

	public String getApp_point() {
		return app_point;
	}

	public void setApp_point(String appPoint) {
		app_point = appPoint;
	}

	public JSONObject getCheck_points() {
		return check_points;
	}

	public void setCheck_points(JSONObject checkPoints) {
		check_points = checkPoints;
	}

}
