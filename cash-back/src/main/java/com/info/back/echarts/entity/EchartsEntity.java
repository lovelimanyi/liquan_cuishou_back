package com.info.back.echarts.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;





/**
 * echarts类
 * 
 * @author admin
 * 
 */
public class EchartsEntity {
	/**图表横轴label
	 * 分组条件
	 * */
	private String labels;
	
	/** 图表值*/
	private List<EchartsSeriesEntity> values;
	
	public List<EchartsSeriesEntity> getValues() {
		return values == null ? new ArrayList<EchartsSeriesEntity>() : values;
	}
	public void setValues(List<EchartsSeriesEntity> values) {
		this.values = values;
	}
	public String getLabels() {
		return StringUtils.isNotEmpty(labels) ? labels : "";
	}
	public void setLabels(String labels) {
		this.labels = labels;
	}

}
