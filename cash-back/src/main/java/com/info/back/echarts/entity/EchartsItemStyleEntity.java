package com.info.back.echarts.entity;

import org.apache.commons.lang3.StringUtils;


/**
 * echarts 样式类
 * 
 * @author admin
 * 
 */
public class EchartsItemStyleEntity {
	//颜色,支持颜色表和英文
	private Object color;
	//边框颜色，适用类型：柱图
	private String barBorderColor;
	//边框宽度，适用类型：柱图
	private String barBorderWidth;
	//柱图边框圆角，默认0(不显示圆角)，数值越大，边框圆角越圆，适用类型：柱图
	private String barBorderRadius;
	
	//是否显示标签
	private boolean label=true;
	/***
	 * 标签显示位置
	 * 标签显示位置，地图标签不可指定位置
     * 饼图可选为：'outer'（外部） | 'inner'（内部），
     * 漏斗图可选为：'inner'（内部）| 'left' | 'right'（默认），
     * 折线图，柱形图，K线图，散点图默认根据布局自适应为'top'或者'right'，可选的还有：'inside' | 'left' | 'bottom'
     * 柱形图可选的还有，'insideLeft' | 'insideRight' | 'insideTop' | 'insideBottom' 
	 */
	private String labelPosition;
	//是否设置label的样式，默认false
    private boolean labelStyle;

	//标签颜色 ，默认#fff
    private String labelColor;
	//标签字体大小 默认：10
	private String labelFontSize;
	//粗细，默认normal，可选为：'normal' | 'bold' | 'bolder' | 'lighter' | 100 | 200 |... | 900 
    private String labelFontWeight;
    
    //是否面积图，默认false,面积图属性样式color和type，type为‘default’
	private boolean area;

	//是否显示标签视觉引导线 
	private boolean labelLine=true;
	//标签引导线的长度
	private int lineLength;

	//构造函数
	public EchartsItemStyleEntity(){}

	public EchartsItemStyleEntity(boolean label,boolean labelLine){
		this.label=label;
		this.labelLine=labelLine;
	}
	public Object getColor() {
		return color;
	}
	public void setColor(Object color) {
		this.color = color;
	}
	public String getBarBorderColor() {
		return barBorderColor;
	}
	public void setBarBorderColor(String barBorderColor) {
		this.barBorderColor = barBorderColor;
	}
	public String getBarBorderWidth() {
		return barBorderWidth;
	}
	public void setBarBorderWidth(String barBorderWidth) {
		this.barBorderWidth = barBorderWidth;
	}
	public String getBarBorderRadius() {
		return barBorderRadius;
	}
	public void setBarBorderRadius(String barBorderRadius) {
		this.barBorderRadius = barBorderRadius;
	}
	public String getLabelPosition() {
		return labelPosition;
	}
	public void setLabelPosition(String labelPosition) {
		this.labelPosition = labelPosition;
	}

	public String getLabelFontSize() {
		return StringUtils.isNotEmpty(labelFontSize) ? labelFontSize : "10";
	}
	public void setLabelFontSize(String labelFontSize) {
		this.labelFontSize = labelFontSize;
	}
	public String getLabelFontWeight() {
		return StringUtils.isNotEmpty(labelFontWeight) ? labelFontWeight : "normal" ;
	}
	public void setLabelFontWeight(String labelFontWeight) {
		this.labelFontWeight = labelFontWeight;
	}
    public String getLabelColor() {
		return labelColor;
	}
	public void setLabelColor(String labelColor) {
		this.labelColor = labelColor;
	}
	public boolean isLabelStyle() {
		return labelStyle;
	}
	public void setLabelStyle(boolean labelStyle) {
		this.labelStyle = labelStyle;
	}
	
	public boolean isArea() {
		return area;
	}
	public void setArea(boolean area) {
		this.area = area;
	}
	public boolean isLabel() {
		return label;
	}
	public void setLabel(boolean label) {
		this.label = label;
	}
	public boolean isLabelLine() {
		return labelLine;
	}
	public void setLabelLine(boolean labelLine) {
		this.labelLine = labelLine;
	}

	public int getLineLength() {
		return lineLength;
	}

	public void setLineLength(int lineLength) {
		this.lineLength = lineLength;
	}
	

}
