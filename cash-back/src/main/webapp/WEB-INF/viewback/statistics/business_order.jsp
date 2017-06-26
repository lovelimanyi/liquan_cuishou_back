<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
    String basePath = path + "/common/back";
%>

<style type="text/css">
			.userTable td {
			    border-top:1px solid #928989;
			    border-bottom: 1px solid #928989;
			    border-right: 1px solid #928989;
			    line-height: 31px;
			    overflow: hidden;
			    padding: 0 3px;
			    vertical-align: middle;
			    font-size:14px;
			    text-align: center;
			}
			.userTable td a{
				color:#5dacd0;
			}
			.userTable{
				padding:0;
				border:solid 1px #928989;
				width:auto;
				line-height:21px;
			}
			.tdGround{background-color:#928989;}
			.userTable th {
				border: 1px solid darkgray;
				color: #555;
				font-weight: bold;
				width: 100px;
				line-height: 21px;
			}
		</style>
<script type="text/javascript" src="<%=basePath%>/js/echarts/echarts.js"></script>
<%-- <script type="text/javascript" src="<%=basePath%>/js/echarts/loadEcharts.js"></script>	 --%>
<form id="pagerForm" onsubmit="return navTabSearch(this);" action="statistics/businessOrder?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>

					<td>时间：
						<input type="text" name="begDate" id="begDate" value="${params.begDate}" class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly" />
					至       <input type="text" name="endDate" id="endDate" value="${params.endDate}" class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly" />
					</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="pageContent">
		<jsp:include page="${BACK_URL}/rightSubList">
			<jsp:param value="${params.myId}" name="parentId"/>
		</jsp:include>
			<table class="userTable" style="width: 100%;height: " layoutH="112" nowrapTD="false">
				<thead>
					<tr>
					<th align="center" width="100" colspan="2">订单总量</th>
					<th align="center" width="100" colspan="2">未还订单量</th>
					<th align="center" width="100" colspan="2">已还订单量</th>
					<th align="center" width="150" colspan="2">订单还款率</th>
					<th align="center" width="150">昨日新增订单量</th>
					</tr>
				</thead>
				<tbody>
					<tr target="id" rel="${content.id }">
						<td colspan="2"><fmt:formatNumber value="${bjzj.loanNum}" maxFractionDigits="2" /></td>
						<td colspan="2"><fmt:formatNumber value="${bjzj.notRepayment}" maxFractionDigits="2" /></td>
						<td colspan="2"><fmt:formatNumber value="${bjzj.yetRepayment}" maxFractionDigits="2" /></td>
						<td colspan="2"><fmt:formatNumber value="${bjzj.loanRate}" maxFractionDigits="2" />%</td>
						<td ><fmt:formatNumber value="${bjzj.yesterday}" maxFractionDigits="2" /></td>
					</tr>
				    <tr target="id" rel="${content.id }">
				       <td colspan="9">
				          	<div style="width:auto;height:400px;" id="echart_o_1"></div>
				             <script type="text/javascript">
				               $(function(){
				               	var list=${line};
				               	if(list && list.length > 0){
				               		if(list[0] && '暂无数据' != list[0]){
										myChart = echarts.init(document.getElementById('echart_o_1'),'line');
										myChart.setOption(list[0]);
									}else{
										$('#echart_o_1').html("<div style='text-align:center;'>暂无数据</div>");
									}
				               		//loadEcharts(list,'${type}','echart_o');
				               	}else{
				               		$('#echart_o_1').html("<div style='text-align:center;'>暂无数据</div>");
				               	}
				               });
				             </script>
				       </td>
				    </tr>
				</tbody>
					
			</table>
	</div>
	<c:if test="${not empty message}">
		<script type="text/javascript">
			alertMsg.error("${message}");
		</script>
	</c:if>
</form>