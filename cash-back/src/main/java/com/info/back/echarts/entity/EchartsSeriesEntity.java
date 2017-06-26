package com.info.back.echarts.entity;

import org.apache.commons.lang3.StringUtils;


/**
 * echarts类
 * 
 * @author admin
 * 
 */
public class EchartsSeriesEntity {
	/** 字段中文描述 */
	private String name;
	/** 字段值 */
	private double values;
	/** 字段在图表中的展示类型，例：pie or bar or line or map */
	private String type;
	/** 坐标 x*/
	private int x;
	/** 坐标 y*/
	private int y;	
	/**该字段是否以右侧纵轴数值为准,默认false 一般用于柱图和线图 */
	private boolean right = false;
	/** 地图类型，如：china为中国地图  map图专用 */
	private String mapType;
	
	/**组合名称，双数值轴时无效，多组数据的堆积图时使用，eg：stack:'group1'，则series数组中stack值等于'group1'的数据做堆积计算 */
	private String stackName;
	
	/** 样式*/
	private EchartsItemStyleEntity itemStyle;


	/************ 构造函数 *************/

	public EchartsSeriesEntity(String name, double values, String type,String mapType) {
		this.name = name;
		this.values = values;
		this.type = type;
		this.mapType=mapType;
	}
	public EchartsSeriesEntity(String name, double values, String type,boolean right) {
		this.name = name;
		this.values = values;
		this.type = type;
		this.right = right;
	}
	public EchartsSeriesEntity(String name,String type,String stackName,double values) {
		this.name = name;
		this.type = type;
		this.stackName = stackName;
		this.values = values;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValues() {
		return values;
	}

	public void setValues(double values) {
		this.values = values;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public String getMapType() {
		return StringUtils.isNotEmpty(mapType) ? mapType : "china";
	}

	public void setMapType(String mapType) {
		this.mapType = mapType;
	}

	public EchartsItemStyleEntity getItemStyle() {
		return itemStyle;
	}

	public void setItemStyle(EchartsItemStyleEntity itemStyle) {
		this.itemStyle = itemStyle;
	}
	public String getStackName() {
		return stackName;
	}
	public void setStackName(String stackName) {
		this.stackName = stackName;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
