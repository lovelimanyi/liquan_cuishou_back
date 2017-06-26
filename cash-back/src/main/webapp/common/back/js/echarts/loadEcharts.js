var option;
var myChart;
function loadEcharts(data,type){
	$.each(data,function(n,value){
		if(value && '暂无数据' != value){
			myChart = echarts.init(document.getElementById('echart_'+(n+1)),type);
			myChart.setOption(value);
		}else{
			$('#echart_'+(n+1)).html("<div style='text-align:center;'>暂无数据</div>");
		}
	});
}
//图表事件
function events(c,ecConfig,eventType,opt){
	var timeTicket;
	if("click" == eventType){
		c.on(ecConfig.EVENT.CLICK, eConsole);
	}else if("linkage" == eventType){
		c.on(ecConfig.EVENT.MAP_SELECTED, map_selected);	
	}else if("time" == eventType){
		clearInterval(timeTicket);
		timeTicket = setInterval(function (){
			//获取图表的动态值
			opt.series[0].data[0].value = activity();
			c.setOption(opt,true);	
		},2000);
	}
}
//地图和饼图的联动
function map_selected(params){
	var selected = params.selected;
    var mapSeries = option.series[0];
    var data = [];
    var legendData = [];
    var name;
    for (var p = 0, len = mapSeries.data.length; p < len; p++) {
        name = mapSeries.data[p].name;
        if (selected && selected[name]) {
            data.push({
                name: name,
                value: mapSeries.data[p].value
            });
            legendData.push(name);
        }
    }
    option.legend.data = legendData;
   if(option.series[1]){ 
    	 option.series[1].data=data; 
   }else{
    	pie_data={
    			name:option.title.text,
    			type:'pie',
    			roseType:'area',
    			center:['75%','50%'],
    			data:data
    	};
    	option.series[1] = pie_data;
    }
   myChart.setOption(option, true);			
}
