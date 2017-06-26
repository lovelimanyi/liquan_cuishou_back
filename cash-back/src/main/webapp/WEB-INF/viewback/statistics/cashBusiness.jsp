<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="statistics/countCashBusinessPage?myId=${params.myId}" method="post">
	
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						时间:
						<input type="text" name="begDate" id="begDate" value="${params.begDate}" class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly" />
					至       <input type="text" name="endDate" id="endDate" value="${params.endDate}" class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly" />
					</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									查询
								</button>
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
			<table class="table" style="width: 100%;" layoutH="112"
				nowrapTD="false">
				<thead>
					<tr>
						<th align="center" width="100">
							时间
						</th>
						<th align="center" width="100">
							到期金额
						</th>
						<th align="center" width="100">
							放款总量
						</th>
						<th align="center" width="100">
							7天放款金额
						</th>
						<th align="center" width="100">
							14天放款金额
						</th>
						<th align="center" width="100">
							21天放款金额
						</th>
						<th align="center" width="100">
							逾期金额总量
						</th>
						<th align="center" width="100">
							7天逾期金额
						</th>
						<th align="center" width="100">
							14天逾期金额
						</th>
						<th align="center" width="100">
							21天逾期金额
						</th>
						<%--<th align="center" width="100">--%>
							<%--7天产品金额逾期率--%>
						<%--</th>--%>
						<%--<th align="center" width="100">--%>
							<%--14天产品金额逾期率--%>
						<%--</th>--%>
						<%--<th align="center" width="100">--%>
							<%--金额坏账率--%>
						<%--</th>--%>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="list" items="${pm.items }" varStatus="status">
						<tr> <!-- target="attendanceId" rel="attendance.id" -->
							<td>
								<fmt:formatDate value="${list.reportDate}" pattern="yyyy-MM-dd"/>
							</td>
							<td>
								${list.expireAmount}
							</td>
							<td>
								${list.moneyAmountCount}
							</td>
							<td>
								${list.sevendayMoenyCount}
							</td>
							<td>
								${list.fourteendayMoneyCount}
							</td>
							<td>
								${list.twentyonedayMoenyCount}
							</td>
							<td>
								${list.overdueAmount}
							</td>
							<td>
								${list.overdueRateSevenAmount}
							</td>
							<td>
								${list.overdueRateFourteenAmount}
							</td>
							<td>
								${list.overdueRateTwentyoneAmount}
							</td>

							<%--<td><fmt:formatNumber value="${list.overdueMoneyStatistic7value}" maxFractionDigits="2" />%</td>--%>
							<%--<td><fmt:formatNumber value="${list.overdueMoneyStatistic14value}" maxFractionDigits="2" />%</td>--%>
							<%--<td><fmt:formatNumber value="${list.baddebtMoneyRate}" maxFractionDigits="2" />%</td>--%>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:set var="page" value="${pm}"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
</form>
	<script type="text/javascript">

function reportExcel(obj){
		var href=$(obj).attr("href");
		var begDate=$("#begDate").val();
		var endDate=$("#endDate").val();
		var personName = $("#personName").val();
		var orderGroupId = $("#orderGroupId").val();
		var groupId = $("#groupId").val();
		var companyTitle = $("#companyTitle").val();
		var toHref=href+"&begDate="+begDate+"&endDate="+endDate+"&personName="+personName+"&orderGroupId="+orderGroupId
		+"&groupId="+groupId+"&companyTitle="+companyTitle;
 
		$(obj).attr("href",toHref);
	} 
</script>