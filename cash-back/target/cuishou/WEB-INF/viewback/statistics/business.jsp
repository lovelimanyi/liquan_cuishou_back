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
<%-- <script type="text/javascript" src="<%=basePath%>/js/echarts/loadEcharts.js"></script> --%>	

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="statistics/businessMoney?myId=${params.myId}" method="post">
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
					<th align="center" width="100" colspan="2">本金总额</th>
					<th align="center" width="100" colspan="2">未还本金</th>
					<th align="center" width="100" colspan="2">已还本金</th>
					<th align="center" width="150" colspan="2">本金还款率</th>
					<th align="center" width="150">昨日新增本金</th>
					</tr>
				</thead>
				<tbody>
					<tr target="id" rel="${content.id }">
						<td colspan="2"><fmt:formatNumber value="${bjzj.loanMoney}" maxFractionDigits="2" /></td>
						<td colspan="2"><fmt:formatNumber value="${bjzj.notRepayment}" maxFractionDigits="2" /></td>
						<td colspan="2"><fmt:formatNumber value="${bjzj.yetRepayment}" maxFractionDigits="2" /></td>
						<td colspan="2"><fmt:formatNumber value="${bjzj.loanMoneyRate}" maxFractionDigits="2" />%</td>
						<td ><fmt:formatNumber value="${bjzj.yesterdayMoney}" maxFractionDigits="2" /></td>
					</tr>
				    <tr target="id" rel="${content.id }">
				       <td colspan="9">
				          	<div style="width:100%;height:400px;" id="echart_1"></div>
				          	<script type="text/javascript">
							  $(function(){
							  	var list=${line};
							  	if(list && list.length > 0){
							  		//loadEcharts(list,'${type}','echart');
							  		if(list[0] && '暂无数据' != list[0]){
										myChart = echarts.init(document.getElementById('echart_1'),'line');
										myChart.setOption(list[0]);
									}else{
										$('#echart_1').html("<div style='text-align:center;'>暂无数据</div>");
									}
							  	}else{
							  		$('#echart_1').html("<div style='text-align:center;'>暂无数据</div>");
							  	}
							  });
							</script>
				       </td>
				    </tr>
				    <%--<tr>--%>
					    <%--<th align="center" width="100" >金额分布</th>--%>
						<%--<th align="center" width="100" colspan="2">待催收</th>--%>
						<%--<th align="center" width="100" colspan="2">催收中</th>--%>
						<%--<th align="center" width="100" colspan="2">承诺还款</th>--%>
						<%--<th align="center" width="100" colspan="2">催收成功</th>--%>
				    <%--</tr>--%>
				    <%--<tr>--%>
					    <%--<th align="center" width="100">类别</th>--%>
						<%--<th align="center" width="100">金额</th>--%>
						<%--<th align="center" width="100">比例</th>--%>
						<%--<th align="center" width="100">金额</th>--%>
						<%--<th align="center" width="100">比例</th>--%>
						<%--<th align="center" width="100">金额</th>--%>
						<%--<th align="center" width="100">比例</th>--%>
						<%--<th align="center" width="100">金额</th>--%>
						<%--<th align="center" width="100">比例</th>--%>
				    <%--</tr>--%>
				    <%----%>
				    <%--<tr target="id" rel="${content.id }">--%>
					    <%--<td>本金</td>--%>
						<%--<td ><fmt:formatNumber value="${bjfb.waitMoney}" maxFractionDigits="2" /></td>--%>
						<%--<td ><fmt:formatNumber value="${bjfb.waitRate}" maxFractionDigits="2" />%</td>--%>
						<%--<td ><fmt:formatNumber value="${bjfb.inMoney}" maxFractionDigits="2" /></td>--%>
						<%--<td ><fmt:formatNumber value="${bjfb.inRate}" maxFractionDigits="2" />%</td>--%>
						<%--<td ><fmt:formatNumber value="${bjfb.promiseMoney}" maxFractionDigits="2" /></td>--%>
						<%--<td ><fmt:formatNumber value="${bjfb.promiseRate}" maxFractionDigits="2" />%</td>--%>
						<%--<td ><fmt:formatNumber value="${bjfb.finishMoney}" maxFractionDigits="2" /></td>--%>
						<%--<td ><fmt:formatNumber value="${bjfb.finishRate}" maxFractionDigits="2" />%</td>--%>
					<%--</tr>--%>
					 <%--<tr target="id" rel="${content.id }">--%>
					    <%--<td>滞纳金</td>--%>
						<%--<td ><fmt:formatNumber value="${bjfb.waitPenalty}" maxFractionDigits="2" /></td>--%>
						<%--<td ><fmt:formatNumber value="${bjfb.waitPenaltyRate}" maxFractionDigits="2" />%</td>--%>
						<%--<td ><fmt:formatNumber value="${bjfb.inPenalty}" maxFractionDigits="2" /></td>--%>
						<%--<td ><fmt:formatNumber value="${bjfb.inPenaltyRate}" maxFractionDigits="2" />%</td>--%>
						<%--<td ><fmt:formatNumber value="${bjfb.promisePenalty}" maxFractionDigits="2" /></td>--%>
						<%--<td ><fmt:formatNumber value="${bjfb.promisePenaltyRate}" maxFractionDigits="2" />%</td>--%>
						<%--<td ><fmt:formatNumber value="${bjfb.finishPenalty}" maxFractionDigits="2" /></td>--%>
						<%--<td ><fmt:formatNumber value="${bjfb.finishPenaltyRate}" maxFractionDigits="2" />%</td>--%>
					<%--</tr>--%>


					<tr>
						<th align="center" width="100" >催收类型</th>
						<th align="center" width="100" colspan="2">本金</th>
						<th align="center" width="100" colspan="2">本金比例</th>
						<th align="center" width="100" colspan="2">滞纳金</th>
						<th align="center" width="100" colspan="2">滞纳金比例</th>
					</tr>
					<c:forEach var="bjfb" items="${bjfbList}" varStatus="status">
					<tr target="id" rel="">
						<td >${bjfb.csType}</td>
						<td colspan="2"><fmt:formatNumber value="${bjfb.ownnerMoney}" maxFractionDigits="2" /></td>
						<td colspan="2"><fmt:formatNumber value="${bjfb.ownnerMoneyRate}" maxFractionDigits="2" />%</td>
						<td colspan="2"><fmt:formatNumber value="${bjfb.penalty}" maxFractionDigits="2" /></td>
						<td colspan="2"><fmt:formatNumber value="${bjfb.penaltyRate}" maxFractionDigits="2" />%</td>
					</tr>
					</c:forEach>
				    
				</tbody>
					
			</table>
	</div>
	<c:if test="${not empty message}">
		<script type="text/javascript">
			alertMsg.error("${message}");
		</script>
	</c:if>
</form>			          	
