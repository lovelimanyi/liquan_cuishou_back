<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<form id="pagerForm" onsubmit="return navTabSearch(this);" action="problemFeedback/list?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						创 建 时 间：
						<input type="text" id="beginTime" name="beginTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
						至
						<input type="text" id="endTime" name="endTime"  value="${params.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
					</td>
					<c:choose>
						<c:when test="${not empty params.CompanyPermissionsList}">
							<td>催 收 公 司：
								<select name="companyId">
									<option value="">全部</option>
									<c:forEach var="company" items="${MmanLoanCollectionCompanys }">
										<c:forEach var="companyViw" items="${params.CompanyPermissionsList}">
											<c:if test="${companyViw.companyId eq company.id}">
												<option value="${company.id }" <c:if test="${company.id eq params.companyId}">selected = "selected"</c:if>>
														${company.title}
												</option>
											</c:if>
										</c:forEach>
									</c:forEach>
								</select>
							</td>
						</c:when>
						<c:otherwise>
							<td>催 收 公 司：
								<select name="companyId">
									<option value="">全部</option>
									<c:forEach var="company" items="${MmanLoanCollectionCompanys }">
										<option value="${company.id }" <c:if test="${company.id eq params.companyId}">selected = "selected"</c:if>>
												${company.title}
										</option>
									</c:forEach>
								</select>
							</td>
						</c:otherwise>
					</c:choose>
					<td>
						反馈类型：
						<select name="type">
							<option value="">全部</option>
							<c:forEach var="problemFeedBackType" items="${problemFeedBackType}">
								<option value="${problemFeedBackType.key}" <c:if test="${problemFeedBackType.key eq params.type}">selected="selected"</c:if> >${problemFeedBackType.value}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						借款人手机：
						<input type="text" name="loanUserPhone" value="${params.loanUserPhone }"/>
					</td>
				</tr>
				<tr>
					<td>
						借 款 编 号：
						<input type="text" name="loanId" value="${params.loanId }"/>
					</td>
					<td>
						执&nbsp;&nbsp;&nbsp;&nbsp;行&nbsp;&nbsp;&nbsp;人：
						<input type="text" name="operator" value="${params.operator }"/>
					</td>
					<td>
						创&nbsp;&nbsp;建&nbsp;&nbsp;人：
						<input type="text" name="createUsername" value="${params.createUsername }"/>
					</td>
					<td>
						状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：
						<select name="status">
							<option value="">全部</option>
							<c:forEach var="problemFeedBackStatus" items="${problemFeedBackStatus}">
								<option value="${problemFeedBackStatus.key}" <c:if test="${problemFeedBackStatus.key eq params.status}">selected="selected"</c:if>>${problemFeedBackStatus.value}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						问题编号：
						<input type="text" style="width: 50px;" name="problemNumber" value="${params.problemNumber }"/>
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
		<table class="table" style="width: 100%;" layoutH="135"
			   nowrapTD="false">
			<thead>
				<tr>
					<th align="center" width="50">
						编号
					</th>
					<th align="center" width="80">
						时间
					</th>
					<th align="center" width="50">
						反馈类型
					</th>
					<th align="center" width="100">
						催收公司名称
					</th>
					<th align="center" width="50">
						执行人
					</th>
					<th align="center" width="60">
						借款编号
					</th>
					<th align="center" width="60">
						借款人手机
					</th>
					<th align="center" width="200">
						反馈内容
					</th>
					<th align="center" width="60">
						创建人角色
					</th>
					<th align="center" width="80">
						创建人
					</th>
					<th align="center" width="80">
						状态
					</th>
					<%--<th align="center" width="100">
						操作
					</th>--%>
				</tr>
			</thead>
			<tbody id="problem">
			<c:forEach var="problemFeedback" items="${page.items }" varStatus="status">
				<tr target="id" rel="${problemFeedback.id }" >
					<td>
							${problemFeedback.id}
					</td>
					<td>
						<fmt:formatDate value="${problemFeedback.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
							${problemFeedBackType[problemFeedback.type]}
					</td>
					<td>
							${problemFeedback.companyTitle}
					</td>
					<td id="operator">
							${problemFeedback.operator}
					</td>
					<td>
							${problemFeedback.loanId}
					</td>
					<td>
							${problemFeedback.loanUserPhone}
					</td>
					<td>
							${problemFeedback.details}
					</td>
					<td>
							${backUserRolename[problemFeedback.createUserRoleId]}
					</td>
					<td>
							${problemFeedback.createUsername}
					</td>
					<td id="status">
						<c:if test="${problemFeedback.status eq '1'}">
							<span style="color: green">已解决</span>
						</c:if>
						<c:if test="${problemFeedback.status ne '1'}">
							<span style="color: #0000FF">待处理</span>
						</c:if>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${page }"></c:set>
		<!-- 分页222 -->
		<%@ include file="../page.jsp"%>
	</div>
	<c:if test="not empty ${message}">
	<script>
		alert("${message}");
	</c:if>
	</script>
</form>