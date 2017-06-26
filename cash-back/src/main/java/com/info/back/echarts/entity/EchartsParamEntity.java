package com.info.back.echarts.entity;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;




/**
 * echarts类
 * 
 * @author admin
 * 
 */
public class EchartsParamEntity {
	/**公共属性：图表标题*/
	private String text;
	/**公共属性：图表副标题*/
	private String subtext;
	/**公共属性：图表悬浮提示框的显示方式，可以选item，axis item：显示单条数据  axis：显示一组数据    暂不支持  默认：item */
	private String trigger;
	/**公共属性：水平安放位置，默认为全图居中，可选为：'center' | 'left' | 'right'  默认center*/
	private String x;
	/**公共属性：垂直安放位置，默认为全图居中，可选为：'center' | 'left' | 'right' 默认bottom */
	private String y;
	/**公共属性： 布局方式，默认为水平布局，可选为：'horizontal' | 'vertical'  */
	private String orient; 
	/**公共属性：只接受true和false，脱离0值比例，放大聚焦到最终_min，_max区间 */
	private String scale;
	/**公共属性 是否2图联动*/
	private boolean linkage;
	/**公共属性： 半径，支持绝对值（px）和百分比，数组实现环形图，[内半径，外半径] */
	private List<String> radius;
	/**公共属性： 中心坐标，支持绝对值（px）和百分比,*/
	private List<String> center;
	/**公共属性：图形点击事件，
	 * 接收值：click(单击事件)
	 * 接收值：linkage(两图联动事件，目前只支持地图和饼图的联动)
	 * 接收值：timer(定时任务)
	 * */
	private String  event;
	/**公共属性：图例点击事件，默认打开*/
	private boolean selectedModel=true;
	

	/**直角系图表属性：图表左侧纵轴后缀*/
	private String yLeftSuffix;
	/**直角系图表属性：图表右侧纵轴后缀*/
	private String yRightSuffix;	
	/**直角系图表属性：是否显示右侧纵轴label， 默认不显示*/
	private boolean showRight=false;
	/**直角系图表属性：是否水平显示，默人false*/
	private boolean horizontal=false;
	/**直角系图表属性：x轴label是否倾斜显示，默认false*/
	private boolean rotate;
	/**直角系图表属性：是否显示缩放区域*/
	private boolean dataZoom=true;
	/**直角系图表属性：是否显示label*/
	private boolean showXAxis=true;
	/**直角系图表属性：xAxis名称*/
	private String xAxisName;
	/**直角系图表属性：yAxis名称*/
	private String yAxisName;
	
	
	/**饼图属性：是否玫瑰图 */
	private boolean rose;
	/**南丁格尔玫瑰图属性：类型：radius and area */
	private List<String> roseType;
	
	/**仪表盘属性：刻度最大值*/
	private long maxScale;
	/**仪表盘属性：描述后缀*/
	private String detailSuffix;
    /**仪表盘属性：仪表盘颜色区域值*/
	private List<Object> span;
	/**仪表盘属性：分割段数*/
	private int splitNumber;

	/** 图表样式实体类*/
	private EchartsItemStyleEntity itemStyle;

	/**构造函数*/
	public EchartsParamEntity(){}
	public EchartsParamEntity(String title){
		this.text=title;
	}
	
	/**get、set*/
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSubtext() {
		return StringUtils.isNotEmpty(subtext) ? subtext : "";
	}

	public void setSubtext(String subtext) {
		this.subtext = subtext;
	}

	public String getyLeftSuffix() {
		return StringUtils.isNotEmpty(yLeftSuffix) ? yLeftSuffix : "";
	}
	public void setyLeftSuffix(String yLeftSuffix) {
		this.yLeftSuffix = yLeftSuffix;
	}
	public String getyRightSuffix() {
		return StringUtils.isNotEmpty(yRightSuffix) ? yRightSuffix : "";
	}
	public void setyRightSuffix(String yRightSuffix) {
		this.yRightSuffix = yRightSuffix;
	}
	
	public boolean isShowRight() {
		return showRight;
	}
	public void setShowRight(boolean showRight) {
		this.showRight = showRight;
	}
	public String getTrigger() {
		return StringUtils.isNotEmpty(trigger) ? trigger : "item";
	}
	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}
	public String getOrient() {
		return orient;
	}
	public void setOrient(String orient) {
		this.orient = orient;
	}
	
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return StringUtils.isNotEmpty(y) ? y : "bottom";
	}
	public void setY(String y) {
		this.y = y;
	}
	public boolean isHorizontal() {
		return horizontal;
	}
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
	
	public EchartsItemStyleEntity getItemStyle() {
		return itemStyle;
	}
	public void setItemStyle(EchartsItemStyleEntity itemStyle) {
		this.itemStyle = itemStyle;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	
	public boolean isRotate() {
		return rotate;
	}
	public void setRotate(boolean rotate) {
		this.rotate = rotate;
	}
	public boolean isLinkage() {
		return linkage;
	}
	public void setLinkage(boolean linkage) {
		this.linkage = linkage;
	}
	public List<String> getRadius() {
		return radius;
	}
	public void setRadius(List<String> radius) {
		this.radius = radius;
	}
	public List<String> getRoseType() {
		if(null == roseType){
			roseType = new ArrayList<String>();
			//roseType.add("radius");
			roseType.add("area");
		}
		return roseType;
	}
	public void setRoseType(List<String> roseType) {
		this.roseType = roseType;
	}
	public List<String> getCenter() {
		if(null == center){
			center = new ArrayList<String>();
			//center.add("25%");
			center.add("50%");
		}
		return center;
	}
	public void setCenter(List<String> center) {
		this.center = center;
	}
	public boolean isRose() {
		return rose;
	}
	public void setRose(boolean rose) {
		this.rose = rose;
	}

	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public long getMaxScale() {
		return maxScale;
	}
	public void setMaxScale(long maxScale) {
		this.maxScale = maxScale;
	}
	public List<Object> getSpan() {
		if(null == span){
			span = new ArrayList<Object>();
			span.add(0.2);
			span.add(0.8);
		}
		return span;
	}
	public void setSpan(List<Object> span) {
		this.span = span;
	}
	public String getDetailSuffix() {
		return detailSuffix;
	}
	public void setDetailSuffix(String detailSuffix) {
		this.detailSuffix = detailSuffix;
	}
	public boolean isDataZoom() {
		return dataZoom;
	}
	public void setDataZoom(boolean dataZoom) {
		this.dataZoom = dataZoom;
	}
	public boolean isShowXAxis() {
		return showXAxis;
	}
	public void setShowXAxis(boolean showXAxis) {
		this.showXAxis = showXAxis;
	}
	
	public int getSplitNumber() {
		return 0 == splitNumber ? 5 : splitNumber;
	}
	public void setSplitNumber(int splitNumber) {
		this.splitNumber = splitNumber;
	}
	public String getxAxisName() {
		return xAxisName;
	}
	public void setxAxisName(String xAxisName) {
		this.xAxisName = xAxisName;
	}
	public String getyAxisName() {
		return yAxisName;
	}
	public void setyAxisName(String yAxisName) {
		this.yAxisName = yAxisName;
	}
	public boolean isSelectedModel() {
		return selectedModel;
	}
	public void setSelectedModel(boolean selectedModel) {
		this.selectedModel = selectedModel;
	}

	
}
