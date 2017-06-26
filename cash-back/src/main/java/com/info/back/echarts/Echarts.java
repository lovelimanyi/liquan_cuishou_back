package com.info.back.echarts;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;

import com.info.back.echarts.entity.EchartsConstant;
import com.info.back.echarts.entity.EchartsEntity;
import com.info.back.echarts.entity.EchartsItemStyleEntity;
import com.info.back.echarts.entity.EchartsParamEntity;
import com.info.back.echarts.entity.EchartsSeriesEntity;


public class Echarts{
	private static Map<String,String> format = new HashMap<String,String>();
	static{
		format.put("pie", "{b}:{c}({d}%) ");
	}
	
	public List<Object> echart(List<EchartsEntity> entity,String type,String title){
		List<Object> result = new ArrayList<Object>();
		EchartsParamEntity param = new EchartsParamEntity();  //样式参数实体类
		param.setText(title);
		if(EchartsConstant.ECHART_LINE.equals(type)){
			Map<String, Object> map = this.echartRightangle(param, entity, type);
			if(!"暂无数据".equals(map.get("option").toString())){
			     result =(List<Object>)map.get("option");	
			}
		}
		return result;
	}
	
    /**
     * 地图
     */
	private Map<String, Object> echartMap(EchartsParamEntity params,List<EchartsEntity> entity) {
		 Map<String,Object> option = new HashMap<String,Object>();
		 if (null == entity || entity.size() < 1){
			 option.put("option", "暂无数据");
			 return option;
		 }
		 option.put("title", this.getTitle(params));
		 option.put("tooltip", this.getTooltip(params,null));
		 option.put("toolbox", this.getToolBox(params));
		 List<Map<String,Object>> data = this.getMapSeries(entity,params);
		 String orient="horizontal";
		 if(null != params.getOrient()){
			 orient = params.getOrient();
		 }
		 //如果是地图和饼图的联动
		 if(params.isLinkage()){
			 params.setOrient("horizontal");  //2个图时因位置不够所以设置拖拽条水平摆放
			 //map图的原数据中查找饼图的数据
			 List<EchartsEntity> pie_data = new ArrayList<EchartsEntity>(); 
			 int i=0;
			 for(EchartsEntity ee:entity){
				 if(i < 3){
					 pie_data.add(ee);
				 }else{
					 break;
				 }
				 i++;
				// if("北京".equals(ee.getLabels()) || "上海".equals(ee.getLabels()) || "广东".equals(ee.getLabels())){
					 
				 //} 
			 }
			 data.add(this.RoseSeries(params, pie_data).get(0));
			 //如果是联动图，则显示饼图的图例
			 params.setOrient("vertical");
			 params.setX("right");
			 params.setY("top");
			 option.put("legend", this.getLegend(pie_data,params,null));
		 }else if(null != entity && entity.size() > 0 && entity.get(0).getValues().size() > 1){  //图例超过1个时显示map图的图例
			 option.put("legend", this.getLegend(entity,params,null));
		 }
		 option.put("dataRange", this.getDataRange(entity,orient));
		 option.put("series", data);
	/*	 Map<String,Object> echarts = new HashMap<String,Object>();
		 echarts.put("option", JSONArray.fromObject(option));
		return echarts;*/
		 return getJson(option,params);
	}

    /**
     * 饼图
     */
	
	private Map<String, Object> echartPie(EchartsParamEntity params,List<EchartsEntity> entity) {
		 Map<String,Object> option = new HashMap<String,Object>();
		 if (null == entity || entity.size() < 1){
			 option.put("option", "暂无数据");
			 return option;
		 }
		 option.put("title", this.getTitle(params));
		 option.put("tooltip", this.getTooltip(params,"pie"));
		 option.put("legend", this.getLegend(entity,params,null));
		 option.put("toolbox", this.getToolBox(params)); 
		 List<Map<String,Object>> series = new ArrayList<Map<String,Object>>();
		 if(params.isRose()){
			 series = this.RoseSeries(params, entity);
		 }else{
			 series = this.getPieSeries(entity, params);
		 }
		 option.put("series", series);
		/* Map<String,Object> echarts = new HashMap<String,Object>();
		 echarts.put("option", JSONArray.fromObject(option)); 
		return echarts;*/
		 return getJson(option,params);
	}


	/** 玫瑰图series*/
	private List<Map<String,Object>> RoseSeries(EchartsParamEntity params,List<EchartsEntity> entity) {
		 List<Map<String,Object>> series = new ArrayList<Map<String,Object>>();
		 Map<String,Object> data = this.getPieSeries(entity, params).get(0);
		 int i=0;
		 for(String type : params.getRoseType()){
			 Map<String,Object> map = new HashMap<String,Object>();
			 if(null != params.getCenter()){
				 map.put("center", params.getCenter());  
			 }
			 if(params.getRoseType().size() > 1 && "radius".equals(type)){
				 EchartsItemStyleEntity it = new EchartsItemStyleEntity(false,false);
				 it.setLineLength(5);
				 map.put("itemStyle", this.itemStyle(it,"normal"));
			 }
             map.put("radius", params.getRadius());
		     for(String key:data.keySet()){
				 map.put(key, data.get(key));
			 }
			 map.put("roseType", type);
			 series.add(map);
			 i++;
		 }
		return series;
	}
   
	/***
	 * 仪表盘
	 */
	
	private Map<String, Object> echartGauge(EchartsParamEntity params,List<EchartsEntity> entity) {
		Map<String,Object> option = new HashMap<String,Object>();
		 if (null == entity || entity.size() < 1){
			 option.put("option", "暂无数据");
			 return option;
		 }
		 Map<String,Object> title = this.getTitle(params);
		 title.put("y", "bottom");
		option.put("title", title);
		option.put("tooltip", this.getTooltip(params,null));
		option.put("toolbox", this.getToolBox(params)); 
		option.put("series", this.getGauge(entity, params));
		return getJson(option,params);
	}

	/**
	 * 直角系图表
	 */
	
	private Map<String, Object> echartRightangle(EchartsParamEntity params,List<EchartsEntity> entity,String tab_type) {
		 Map<String,Object> option = new HashMap<String,Object>();
		 if (null == entity || entity.size() < 1){
			 option.put("option", "暂无数据");
			 return option;
		 }
		 option.put("title", this.getTitle(params));
		 option.put("tooltip", this.getTooltip(params,null));
		 option.put("legend", this.getLegend(entity,params,tab_type));
		 option.put("toolbox", this.getToolBox(params));
		 //根据需求，如果直角系图表X轴label超过15个，就显示区域缩放，便于查看
		 if(params.isDataZoom() && entity.size() > 15){
			 option.put("dataZoom", this.getDataZoom(params, entity.size()));
		 }
		 //如果是水平显示，X轴和Y轴调换
		 if(params.isHorizontal()){
			 option.put("xAxis", this.getYAxis(entity, params));
			 option.put("yAxis", this.getXAxis(entity,params,tab_type));
		 }else{ //否则正常显示
			 option.put("xAxis", this.getXAxis(entity,params,tab_type));
			 option.put("yAxis", this.getYAxis(entity, params)); 
		 }
		 option.put("series", this.getSeries(entity,params)); 
		 /*Map<String,Object> echarts = new HashMap<String,Object>();
		 echarts.put("option", JSONArray.fromObject(option));*/
		return getJson(option,params);
	}

    /**
     * 散点图
     */
	private Map<String, Object> echartScatter(EchartsParamEntity params,List<EchartsEntity> entity) {
		 Map<String,Object> option = new HashMap<String,Object>();
		 if (null == entity || entity.size() < 1){
			 option.put("option", "暂无数据");
			 return option;
		 }
		 option.put("title", this.getTitle(params));
		 option.put("tooltip", this.getTooltip(params,null));
		 option.put("legend", this.getLegend(entity,params,"scatter"));
		 option.put("toolbox", this.getToolBox(params));
		 //根据需求，如果直角系图表X轴label超过15个，就显示区域缩放，便于查看
		 if(params.isDataZoom() && entity.size() > 15){
			 option.put("dataZoom", this.getDataZoom(params, entity.size()));
		 }
		 option.put("xAxis", this.getXAxis(entity,params,"scatter"));
		 option.put("yAxis", this.getYAxis(entity, params)); 
		 option.put("series", this.getScatter(entity, params));
		 /*Map<String,Object> echarts = new HashMap<String,Object>();
		 echarts.put("option", JSONArray.fromObject(option));*/
		return getJson(option,params);
	}
	
	/**封装JSONArray*/
	private Map<String,Object> getJson(Map<String,Object> option,EchartsParamEntity params){
		JSONArray arr = JSONArray.fromObject(option);
		if(StringUtils.isNotBlank(params.getEvent())){
			arr.add(1,params.getEvent());
		}
		Map<String,Object> echarts = new HashMap<String,Object>();
		echarts.put("option", arr);
		return echarts;
	}
	
	/**
	 * 公共部分
	 * 填充echarts的标题部分
	 * @param entity
	 * @return
	 */
	private Map<String,Object> getTitle(EchartsParamEntity params){
		Map<String,Object> title=new HashMap<String,Object>();
		title.put("text",params.getText());
		title.put("subtext", params.getSubtext());
		//图表标题在x轴的位置
		title.put("x", "center");
		return title;
	}
	/**
	 * 公共部分
	 * 填充echarts的提示框部分
	 * @param entity
	 * @return
	 */
	private Map<String,Object> getTooltip(EchartsParamEntity params,String tab_type){
		Map<String,Object> toolTip=new HashMap<String,Object>();
		toolTip.put("trigger",params.getTrigger());
		if(null != tab_type){
			toolTip.put("formatter", format.get(tab_type));
		}
		
		return toolTip;
	}

	/**
	 * 公共部分
	 * 填充echarts图例部分
	 * @param entity
	 * @return
	 */
	private Map<String,Object> getLegend(List<EchartsEntity> entity,EchartsParamEntity params,String tab_type){
		Map<String,Object> map=new HashMap<String,Object>();
		if(params.isDataZoom() && entity.size() > 8){
			map.put("x", "right");
			map.put("y", "top");
		}else{
			if(StringUtils.isNotBlank(params.getX())){
				map.put("x", params.getX());
			}
			//图例的垂直安放位置
			map.put("y", params.getY());
		}
		if(StringUtils.isNotBlank(params.getOrient())){
			map.put("orient",params.getOrient());
		}
		map.put("selectedMode", params.isSelectedModel());
		//图例集合,使用set集合用于去重
		Set<String> legend = new HashSet<String>();
		//获取图表数据，从中分离出图例说明
		for(EchartsEntity echart : entity){
			/*if(StringUtils.isBlank(tab_type)){
				legend.add(echart.getLabels());
			}else{*/
			for(EchartsSeriesEntity data : echart.getValues()){
				legend.add(StringUtils.isBlank(data.getName()) ? echart.getLabels() : data.getName());
			}
			/*}*/
		}
		map.put("data", legend);
		return map;
		
	}
	/**
	 * 公共部分
	 * 填充echarts工具箱部分
	 * @param entity
	 * @return
	 */
	private Map<String,Object> getToolBox(EchartsParamEntity params){
		Map<String,Object> map=new HashMap<String,Object>();
		//图例的垂直安放位置
		map.put("show", false);
		return map;
		
	}
	
	/**
	 * 直角系部分：区域缩放
	 * @param params
	 * @return
	 */
	private Map<String,Object> getDataZoom(EchartsParamEntity params,double num){
		Map<String,Object> zoom=new HashMap<String,Object>();
		//直角系图表X轴显示8条数据最佳
		int end = (int)((8/num)*100);
		zoom.put("show", true);
		zoom.put("realtime", false);
		zoom.put("start", 0);
		zoom.put("end", end);
		return zoom;
	}
	
	/**
	 * 公共部分
	 * 值域选择， 可通过拖拽控制图表颜色等
	 * @param entity
	 * @return
	 */
	private Map<String,Object> getDataRange (List<EchartsEntity> entity,String orient){
		//从数据中获取最大值
		List<Double> value = new ArrayList<Double>();
		for(EchartsEntity ee : entity){
			for(EchartsSeriesEntity s: ee.getValues()){
				value.add(s.getValues());
			}
		}
		int max = 1000;
		if(value.size() > 0){
			int v_max = Collections.max(value).intValue() ;
			max = v_max > 0  ? v_max : max;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("min", 0);
		map.put("max", max);
		map.put("x", "left");
		map.put("y", "bottom");
	
		if(StringUtils.isNotBlank(orient)){
			map.put("orient",orient);
		}
		//拖拽条的文本，如有需要可动态传入
		List<String> text = new ArrayList<String>();
		text.add("高");
		text.add("低");
		map.put("text",text);
		map.put("calculable",true);		
		map.put("show", false);
		return map;
	}
	
	
	/**
	 * 拼装echarts xaxis部分
	 * @param entity
	 * @return
	 */
	private Map<String,Object> getXAxis(List<EchartsEntity> entity,EchartsParamEntity params,String tb_type){
		Map<String,Object> xaxis=new HashMap<String,Object>();
		//散点图的X轴也是数值
		if(tb_type.equals(EchartsConstant.ECHARTS_SCATTER)){
			xaxis.put("type", EchartsConstant.ECHARTS_AXIS_TYPE_V);
		}else{
			xaxis.put("type", EchartsConstant.ECHARTS_AXIS_TYPE_C);
			List<String> list =new ArrayList<String>();
			for(EchartsEntity echart : entity){
				list.add(echart.getLabels());
			}
			xaxis.put("data", list);
		}
		//x轴label是否倾斜显示，如为true,默认顺时针倾斜45度
		if(entity.size() > 8 && entity.size() <= 15){
			Map<String,Object> axisLabel = new HashMap<String,Object>();
			axisLabel.put("rotate", 45);
			//填充label  
			xaxis.put("axisLabel", axisLabel);
		}
		
	/*	if(params.isRotate()){
			
		}*/
		//是否显示策略
		if(StringUtils.isNotBlank(params.getScale())){
			xaxis.put("scale", params.getScale());
		}
		//设置线图的起始位置
		if(tb_type.equals(EchartsConstant.ECHART_LINE)){
			xaxis.put("boundaryGap", false);
		}
		if(!params.isShowXAxis()){
			xaxis.put("show", params.isShowXAxis());
		}
		if(StringUtils.isNotBlank(params.getxAxisName())){
			xaxis.put("name", params.getxAxisName());
		}
		return xaxis;		
	}
	
	/**
	 * 拼装echarts yaxis部分
	 * @param entity
	 * @param tab_type
	 * @return
	 */
	private List<Map<String,Object>> getYAxis(List<EchartsEntity> entity, EchartsParamEntity params){
		List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
		Map<String,Object> yaxis=new HashMap<String,Object>();
		yaxis.put("type", EchartsConstant.ECHARTS_AXIS_TYPE_V);
		if(StringUtils.isNotBlank(params.getyAxisName())){
			yaxis.put("name", params.getyAxisName());
		}
		//是否显示策略
		if(StringUtils.isNotBlank(params.getScale())){
			yaxis.put("scale", params.getScale());
		}
		//图表左侧纵轴是否需要添加后缀
		if(StringUtils.isNotBlank(params.getyLeftSuffix())){			
			Map<String,Object> axisLabel = new HashMap<String,Object>();
			//设置后缀格式
			axisLabel.put("formatter", "{"+EchartsConstant.ECHARTS_AXIS_TYPE_V+"}"+params.getyLeftSuffix());
			//填充label
			yaxis.put("axisLabel", axisLabel);
		}

		list.add(yaxis);
		//图表右侧纵轴是否需要显示数据
		if(params.isShowRight()){
			Map<String,Object> yRightAxis = new HashMap<String,Object>();
			yRightAxis.put("type", EchartsConstant.ECHARTS_AXIS_TYPE_V);
			//判断是否有后缀
			if(StringUtils.isNotBlank(params.getyRightSuffix())){
				Map<String,Object> axisLabel = new HashMap<String,Object>();
				//设置后缀格式
				axisLabel.put("formatter", "{"+EchartsConstant.ECHARTS_AXIS_TYPE_V+"}"+params.getyRightSuffix());
				//填充label
				yRightAxis.put("axisLabel", axisLabel);
			}	
			list.add(yRightAxis);
		}	
		return list;		
	}
	
	/**饼图*/
	private List<Map<String,Object>> getPieSeries(List<EchartsEntity> entity,EchartsParamEntity params){
		//结果集
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		//属性
		Map<String, Object> property = new HashMap<String, Object>();
		//data
		List<Map<String, Object>> p_data = new ArrayList<Map<String, Object>>();
		for(EchartsEntity echart : entity){
			for(EchartsSeriesEntity series : echart.getValues()){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("value", series.getValues());
				map.put("name",StringUtils.isBlank(series.getName()) ? echart.getLabels() : series.getName());
				p_data.add(map);
			}
		}	
		
		//设置饼图的大小
		property.put("radius","50%");
		//是否是空心饼图
		if(null != params.getRadius() && params.getRadius().size() == 2){
			//设置环形图的大小
			property.put("radius",params.getRadius());
		}
		//设置label的样式
		if(null != params.getItemStyle()){
			params.getItemStyle().setLineLength(5);   //饼图标签线的长度
			property.put("itemStyle", this.itemStyle(params.getItemStyle(),"normal"));
		}
		property.put("type", "pie");
		property.put("data", p_data);
		result.add(property);
		return result;
	}
	
	/**map图*/
	@SuppressWarnings("unchecked")
	private List<Map<String,Object>> getMapSeries(List<EchartsEntity> entity,EchartsParamEntity params){
		//结果集
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		int j=0;   //用于判断默认选中
		for(EchartsEntity echart : entity){
			int i=0;			
			for(EchartsSeriesEntity series : echart.getValues()){	
				//属性
				Map<String, Object> property = new HashMap<String, Object>();
				if(result.size() < echart.getValues().size()){
					property.put("name", series.getName());
					property.put("type", series.getType());
					property.put("mapType", series.getMapType());
					//是否2图联动，如果是：则需要设置选中模式为多选
					if(params.isLinkage()){
						property.put("selectedMode","multiple");
						Map<String,String> map = new HashMap<String,String>();
						map.put("x", "left");
						map.put("width", "60%");
						property.put("mapLocation",map);
					}					
					//设置label样式,此处使用默认样式，所以只需new一个对象即可
					if(null == params.getItemStyle()){
						params.setItemStyle(new EchartsItemStyleEntity());
					}
					if(null != params.getItemStyle()){
						Map<String,Object> map = this.itemStyle(params.getItemStyle(),"normal");
						map.put("emphasis", this.itemStyle(params.getItemStyle(),"emphasis").get("emphasis"));
						property.put("itemStyle", map);
					}					
					//data
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("name",echart.getLabels());
					map.put("value", series.getValues());
					//如果是2图联动，则默认选中Top3
					if(params.isLinkage() && j < 3){
						 map.put("selected", true);
					}
					//if(params.isLinkage() && ("北京".equals(echart.getLabels()) || "上海".equals(echart.getLabels()) || "广东".equals(echart.getLabels()))){
						
					//}
					List<Map<String,Object>> data =new ArrayList<Map<String,Object>>();
					data.add(map);
					property.put("data", data);
					result.add(property);
				}else{
					property = result.get(i);
					//data
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("name",echart.getLabels());
					map.put("value", series.getValues());
					//如果是2图联动，则默认选中Top3
					if(params.isLinkage() && j < 3){
						 map.put("selected", true);
					}
					//if(params.isLinkage() && ("北京".equals(echart.getLabels()) || "上海".equals(echart.getLabels()) || "广东".equals(echart.getLabels()))){
						 //map.put("selected", true);
					//}
					List<Map<String,Object>> data = (List<Map<String,Object>>)property.get("data");
					data.add(map);
					i+=1;
				}
			
			}
			j++;
		}
		return result;
	}
	
	
	/**
	 * 线图 or 柱图 
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String,Object>> getSeries(List<EchartsEntity> entity,EchartsParamEntity params){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(EchartsEntity echart : entity){
			int i=0;
			for(EchartsSeriesEntity ese : echart.getValues()){	
				Map<String,Object> series = new HashMap<String,Object>();
				if(list.size() < echart.getValues().size()){
					series.put("name", ese.getName());
					series.put("type", ese.getType());
					if(ese.isRight()){
						series.put("yAxisIndex", 1);
					}
					if(StringUtils.isNotBlank(ese.getStackName())){
						series.put("stack", ese.getStackName());
					}
					//设置样式
					if(null != params.getItemStyle()){
						series.put("itemStyle", this.itemStyle(params.getItemStyle(),"normal"));
					}
					//数据过少时设置柱子的宽度
					if(entity.size() <= 5){
						series.put("barWidth", 30);
					}
					List<Double> data = new ArrayList<Double>();
					data.add(ese.getValues());
					series.put("data", data);
					list.add(series);	
				}else{
					series = list.get(i);
					List<Double> data = (List<Double>)series.get("data");
					data.add(ese.getValues());
					i+=1;
				}
				
			}
		}
		return list;
	}
	
	/**
	 * 散点图
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String,Object>> getScatter(List<EchartsEntity> entity,EchartsParamEntity params){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(EchartsEntity echart : entity){
			int i=0;
			for(EchartsSeriesEntity ese : echart.getValues()){	
				Map<String,Object> series = new HashMap<String,Object>();
				if(list.size() < echart.getValues().size()){
					series.put("name", ese.getName());
					series.put("type", ese.getType());
					List<Integer> data = new ArrayList<Integer>();
					data.add(ese.getX());
					data.add(ese.getY());
					List<Object> obj = new ArrayList<Object>();
					obj.add(data);
					series.put("data", obj);
					list.add(series);
				}else{
					series = list.get(i);
					List<Object> data = (List<Object>)series.get("data");
					List<Integer> child = new ArrayList<Integer>();
					child.add(ese.getX());
					child.add(ese.getY());
					data.add(child);
					i+=1;
				}
			}
		}
		return list;
	}
	
	/***
	 * 仪表盘
	 * @param entity
	 * @param params
	 * @return
	 */
	private List<Map<String,Object>> getGauge(List<EchartsEntity> entity,EchartsParamEntity params){
		//仪表盘默认颜色
		List<String> defaultColor = new ArrayList<String>(){{add("#ff4500");add("#228b22");add("#ff4500");}};
	    //仪表盘数据集
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		//属性
		Map<String, Object> property = new HashMap<String, Object>();
		//data
		List<Map<String, Object>> p_data = new ArrayList<Map<String, Object>>();
		for(EchartsEntity echart : entity){
			for(EchartsSeriesEntity series : echart.getValues()){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("value", series.getValues());
				map.put("name","");  //StringUtils.isBlank(series.getName()) ? echart.getLabels() : series.getName()
				p_data.add(map);
			}
		}
		//如果仪表盘刻度不为空，则设置仪表盘的最大值
		List<Object> color = new ArrayList<Object>();
		if(0 < params.getMaxScale()){
			property.put("max", params.getMaxScale());		
		}
		//标识仪表盘的颜色区域为默认
		boolean isLast = true;
		//设置颜色
		for(int i=0;i<params.getSpan().size();i++){
			double  span = Double.valueOf((params.getSpan().get(i).toString()));
			List<Object> list = new ArrayList<Object>();
			if(span >= 1){
				span=1;
				isLast=false;
			}
			if(params.getSpan().size() ==1){
				list.add(span);
				list.add(defaultColor.get(1));
				color.add(list);
			}else{
				list.add(span);
				list.add(defaultColor.get(i));
				color.add(list);
			}
		}
		if(isLast){
			List<Object> list = new ArrayList<Object>();
			list.add(1);
			list.add(defaultColor.get(2));
			color.add(list);
		}
		Map<String,Object> lineStyle = new HashMap<String,Object>();
		lineStyle.put("width", 5);
		lineStyle.put("color",color);
		//仪表盘各数值段的颜色
		Map<String,Object> axisLine=new HashMap<String,Object>();
		axisLine.put("lineStyle", lineStyle);
        //仪表盘详情
        Map<String,Object> detail=new HashMap<String,Object>();
        String value = "{value}";
        if(StringUtils.isNotBlank(params.getDetailSuffix())){
        	value += params.getDetailSuffix();
        	
        }
        //不显示仪表盘上数值
        Map<String,Object> axisLabel = new HashMap<String,Object>();
        axisLabel.put("show", false);
        
        Map<String,Object> textStyle = new HashMap<String,Object>();
        if(null != params.getItemStyle()){
             textStyle = this.textStyle(params.getItemStyle());
        }
        detail.put("formatter", value); 
        detail.put("textStyle", textStyle);
        //设置属性
        property.put("splitNumber", params.getSplitNumber());
        property.put("axisLine", axisLine);
        property.put("axisLabel", axisLabel);
        property.put("detail", detail);
        property.put("type", "gauge");
        property.put("data", p_data);
        data.add(property);
		return data;
	}

    //设置echarts图表的样式,通用，可根据echarts图表需要修改
	private Map<String,Object> itemStyle(EchartsItemStyleEntity itemStyle,String styleKey){
		Map<String,Object> style = new HashMap<String,Object>();
		Map<String,Object> normal = new HashMap<String,Object>();
		if(itemStyle.isArea()){
			normal.put("areaStyle", this.areaStyle(itemStyle));
		}
		if(null != itemStyle.getColor()){
			normal.put("color", itemStyle.getColor());
		}
		if(StringUtils.isNotBlank(itemStyle.getBarBorderColor())){
			normal.put("barBorderColor", itemStyle.getBarBorderColor());
		}
		if(StringUtils.isNotBlank(itemStyle.getBarBorderRadius())){
			normal.put("barBorderRadius", itemStyle.getBarBorderRadius());
		}
		if(StringUtils.isNotBlank(itemStyle.getBarBorderWidth())){
			normal.put("barBorderWidth", itemStyle.getBarBorderWidth());
		}
		//是否显示label
		Map<String,Object> label = new HashMap<String,Object>();
		label.put("show", itemStyle.isLabel());
		if(StringUtils.isNotBlank(itemStyle.getLabelPosition())){
			label.put("position", itemStyle.getLabelPosition());
		}
		//是否显示label样式
		if(itemStyle.isLabelStyle()){
			label.put("textStyle", textStyle(itemStyle));
		}
		//填充label
		normal.put("label",label);

		Map<String,Object> labelLine = new HashMap<String,Object>();
		labelLine.put("show", itemStyle.isLabelLine());
		if(itemStyle.getLineLength() > 0 ){
			labelLine.put("length", itemStyle.getLineLength());
		}
		normal.put("labelLine",labelLine);	
		style.put(styleKey, normal);
		return style;
	}
	//文本样式，通用，可根据需求更改
	private Map<String,Object> textStyle(EchartsItemStyleEntity itemStyle){
		Map<String,Object> style = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(itemStyle.getLabelColor())){
			style.put("color", itemStyle.getLabelColor());
		}
		style.put("fontSize", itemStyle.getLabelFontSize());
		style.put("fontWeight", itemStyle.getLabelFontWeight());
		return style;
	}
	//面积图样式
	private Map<String,Object> areaStyle(EchartsItemStyleEntity itemStyle){
		Map<String,Object> style = new HashMap<String,Object>();
		if(null != itemStyle.getColor()){
			style.put("color", itemStyle.getColor());
		}
		style.put("type", "default");
		return style;
	}
}