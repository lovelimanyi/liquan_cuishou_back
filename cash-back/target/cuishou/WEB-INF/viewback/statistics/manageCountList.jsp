<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="statistics/countManagePage?myId=${params.myId}" method="post">
	
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
						催收公司:
						<select name="companyId" id="companyId">
							<option value="">全部</option>
							<c:forEach items="${company}" var="company">
							  <option value="${company.id}" <c:if test="${params.companyId == company.id}">selected="selected"</c:if>>${company.title}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						催收组:
						<select name="groupId">
							<option value="">全部</option>
							<c:forEach items="${dict}" var="dict">
							  <option value="${dict.value}" <c:if test="${params.groupId == dict.value}">selected="selected"</c:if>>${dict.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						订单组:
						<select name="orderGroupId">
							<option value="">全部</option>
							<c:forEach items="${dict}" var="dict">
							  <option value="${dict.value}" <c:if test="${params.orderGroupId == dict.value}">selected="selected"</c:if>>${fn:toLowerCase(dict.label)}</option>
							</c:forEach>
						</select>
					</td>
					<c:if test="${params.roleId eq '10021' }">
						<td>
							催收员姓名:
							<input type="text" name="personName" id="personName"
								value="${params.personName }" readonly="readonly"/>
						</td>
					</c:if>
					<c:if test="${params.personId eq null}">
						<td>
							催收员姓名:
							<input type="text" name="personName"
								value="${params.personName }" />
						</td>
						<td>
							排序:
							<select name="orderBy">
								<option value="" <c:if test="${params.orderBy eq ''}">selected="selected"</c:if>>默认排序</option>
								<option value="repayment_reta ASC" <c:if test="${params.orderBy eq 'repayment_reta ASC'}">selected="selected"</c:if>>按本金催回率升序</option>
								<option value="repayment_reta DESC" <c:if test="${params.orderBy eq 'repayment_reta DESC'}">selected="selected"</c:if>>按本金催回率降序</option>
								<option value="penalty_repayment_reta ASC" <c:if test="${params.orderBy eq 'penalty_repayment_reta ASC'}">selected="selected"</c:if>>按滞纳金催回率升序</option>
								<option value="penalty_repayment_reta DESC" <c:if test="${params.orderBy eq 'penalty_repayment_reta DESC'}">selected="selected"</c:if>>按滞纳金催回率降序</option>
								<option value="repayment_order_rate ASC" <c:if test="${params.orderBy eq 'repayment_order_rate ASC'}">selected="selected"</c:if>>按订单催回率升序</option>
								<option value="repayment_order_rate DESC" <c:if test="${params.orderBy eq 'repayment_order_rate DESC'}">selected="selected"</c:if>>按订单催回率降序</option>
							</select>
						</td>
					</c:if>
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
						<th align="center" width="150">
							时间
						</th>
						<th align="center" width="350">
							催收公司
						</th>
						<th align="center" width="50">
							催收组
						</th>
						<th align="center" width="50">
							订单组
						</th>
						<th align="center" width="100">
							催收员姓名
						</th>
						<th align="center" width="100">
							本金金额
						</th>
						<th align="center" width="100">
							已还本金
						</th>
						<th align="center" width="100">
							未还本金
						</th>
						<th align="center" width="100">
							本金催回率
						</th>
						<th align="center" width="100">
							迁徙率
						</th>
						<th align="center" width="100">
							滞纳金金额
						</th>
						<th align="center" width="100">
							已还滞纳金
						</th>
						<th align="center" width="100">
							未还滞纳金
						</th>
						<th align="center" width="100">
							滞纳金催回率
						</th>
						<th align="center" width="100">
							订单量
						</th>
						<%--<th align="center" width="100">--%>
							<%--已操作订单量--%>
						<%--</th>--%>
						<!-- <th align="center" width="100">
							风控标记单量
						</th> -->
						<th align="center" width="100">
							已还订单量
						</th>
						<th align="center" width="100">
							订单还款率
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="list" items="${pm.items }" varStatus="status">
						<tr> <!-- target="attendanceId" rel="attendance.id" -->
							<td>
								<fmt:formatDate value="${list.countDate}" pattern="yyyy-MM-dd"/> 
							</td>
							<td>
								${list.companyTitle}
							</td>
							<td>
								${list.groupName}
							</td>
							<td>
								${list.groupOrderName}
							</td>
							<td>
								${list.personName}
							</td>
							<td>
								${list.loanMoney}
							</td>
							<td>
								${list.repaymentMoney}
							</td>
							<td>
								${list.notYetRepaymentMoney}
							</td>
							<td>
							 ${list.repaymentReta}%
							</td>
							<td>
							 <c:choose>
							   <c:when test="${list.migrateRate eq '-1.00'}">--</c:when>
							   <c:otherwise>${list.migrateRate}%</c:otherwise>
							 </c:choose>
							</td>
							<td>
								${list.penalty}
							</td>
							<td>
								${list.repaymentPenalty}
							</td>
							<td>
								${list.notRepaymentPenalty}
							</td>
							<td>
								${list.penaltyRepaymentReta}%
							</td>
							<td>
								${list.orderTotal}
							</td>
							<%--<td>--%>
								<%--${list.disposeOrderNum}--%>
							<%--</td>--%>
							<%-- <td>
								${list.riskOrderNum}
							</td> --%>
							<td>
								${list.repaymentOrderNum}
							</td>
							<td>
								${list.repaymentOrderRate}%
							</td>
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
		var personName = $("#personName").val() == undefined ? '' : $("#personName").val();
		var orderGroupId = $("#orderGroupId").val() == undefined ? '' : $("#orderGroupId").val();
		var groupId = $("#groupId").val() == undefined ? '' : $("#groupId").val();
		var companyTitle = $("#companyTitle").val() == undefined ? '' : $("#companyTitle").val();

		var toHref=href+"&begDate="+begDate+"&endDate="+endDate+"&personName="+personName+"&orderGroupId="+orderGroupId
		+"&groupId="+groupId+"&companyTitle="+companyTitle;
 
		$(obj).attr("href",toHref);
	} 
</script>