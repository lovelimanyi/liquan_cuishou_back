<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="statistics/countAssessmentPage?myId=${params.myId}&type=${params.type}" method="post">
	
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
						类别:
						<select name="groupType" id="groupType">
							<%--<c:if test="${params.type ne 'GS'}">--%>
								<option value="">全部</option>
							<%--</c:if>--%>
							<c:forEach items="${groupNameTypeMap}" var="map">
								<option value="${map.key}" <c:if test="${groupLevel eq map.key}">selected="selected"</c:if> >${map.value}</option>
							</c:forEach>
						</select>
					</td>

					<c:if test="${params.type ne 'GS'}">
						<td>
							催收公司:
							<select name="companyId" id="companyId">
								<option value="">全部</option>
								<c:forEach items="${company}" var="company">
								  <option value="${company.id}" <c:if test="${params.companyId == company.id}">selected="selected"</c:if>>${company.title}</option>
								</c:forEach>
							</select>
						</td>
						<%--<td>--%>
							<%--催收组:--%>
							<%--<select name="groupId" id="groupId">--%>
								<%--<option value="">全部</option>--%>
								<%--<c:forEach items="${dict}" var="dict">--%>
								  <%--<option value="${dict.value}" <c:if test="${params.groupId == dict.value}">selected="selected"</c:if>>${dict.label}</option>--%>
								<%--</c:forEach>--%>
							<%--</select>--%>
						<%--</td>--%>
						<%--<td>--%>
							<%--订单组:--%>
							<%--<select name="orderGroupId" id="orderGroupId">--%>
								<%--<option value="">全部</option>--%>
								<%--<c:forEach items="${dict}" var="dict">--%>
								  <%--<option value="${dict.value}" <c:if test="${params.orderGroupId == dict.value}">selected="selected"</c:if>>${fn:toLowerCase(dict.label)}</option>--%>
								<%--</c:forEach>--%>
							<%--</select>--%>
						<%--</td>--%>
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
								<input type="text" name="personName" id="personName"
									value="${params.personName }" />
							</td>
						</c:if>
					</c:if>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									查询
								</button>
								<button id="btnExport" value="导出"/></button>
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
							日期
						</th>
						<th align="center" width="350">
							催收公司
						</th>
						<th align="center" width="50">
							催收组
						</th>
						<th align="center" width="100">
							催收员
						</th>
						<th align="center" width="100">
							待催收订单
						</th>
						<th align="center" width="100">
							已催收订单
						</th>
						<th align="center" width="100">
							催记率
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
								${list.personName}
							</td>
							<td>
								${list.undoneOrderNum}
							</td>
							<td>
								${list.doneOrderNum}
							</td>
							<td>
							 	${list.orderRate}%
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
		if(href.indexOf('&begDate') > -1){
			href = href.substring(0,href.indexOf('&begDate'));
		}
		var begDate=$("#begDate").val();
		var endDate=$("#endDate").val();
		var personName = $("#personName").val() == undefined ? '' : $("#personName").val();
		var orderGroupId = $("#orderGroupId").val() == undefined ? '' : $("#orderGroupId").val();
		var groupId = $("#groupId").val() == undefined ? '' : $("#groupId").val();
		var companyId = $("#companyId").val() == undefined ? '' : $("#companyId").val();
		var type='${params.type}';
		var toHref=href+"&begDate="+begDate+"&endDate="+endDate+"&personName="+personName+"&orderGroupId="+orderGroupId
		+"&groupId="+groupId+"&companyId="+companyId+"&type="+type;
 
		$(obj).attr("href",toHref);
	} 
</script>