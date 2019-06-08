<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="collectionRule/getMmanLoanCollectionRulePage?myId=${params.myId}" method="post">
	
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						催收组
						<select name="collectionGroup">
								<option value="">全部</option>
								<c:forEach items="${groupNameMap}" var="map">
								  	<option value="${map.key}" <c:if test="${params.collectionGroup eq map.key}">selected="selected"</c:if> >${map.value}</option>  
							    </c:forEach>  
						</select>
					</td>
					<td>
						催收公司
						<select name="companyId">
								<option value="">全部</option>
								<c:forEach var="company" items="${companyList}" varStatus="status">
									<option value="${company.id}" <c:if test="${params.companyId eq company.id}">selected="selected"</c:if>>${company.title}</option>
								</c:forEach>
						</select>
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
						<th align="center" width="50">
							编号
						</th>
						<th align="center" width="50">
							催收公司名称
						</th>
						<th align="center" width="50">
							催收组
						</th>
						<th align="center" width="30">
							当前人数
						</th>
						<th align="center" width="100">
							每天上限
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="rule" items="${pm.items }" varStatus="status">
						<tr target="id" rel="${rule.id }">
							<td>
								${status.index+1}
							</td>
							<td>
								${rule.companyName}
							</td>
							<td>
								${groupNameMap[rule.collectionGroup]}
							</td>
							<td>
								${rule.personCount}
							</td>
							<td>
								${rule.everyLimit}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
</form>