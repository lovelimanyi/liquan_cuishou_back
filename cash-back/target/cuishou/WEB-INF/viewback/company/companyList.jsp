<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="company/getCompanyPage?myId=${params.myId}" method="post">
	
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>

					<td>
						公司名称:
						<input type="text" name="title"
							value="${params.title }" />
					</td>
					<td>
						创建时间
						<input type="text" name="beginTime" id="beginTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
						到<input type="text" name="endTime" id="endTime" value="${params.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
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
						<th align="center" width="100">
							公司名称
						</th>
						<th align="center" width="30">
							人数
						</th>
						<th align="center" width="100">
							添加时间
						</th>
<!-- 						<th align="center" width="30"> -->
<!-- 							优先级 -->
<!-- 						</th> -->
						<th align="center" width="30">
							状态
						</th>
						<th align="center" width="30">
							自营团队
						</th>
						<th align="center" width="30">
							销售公司
						</th>
						<th align="center" width="30">
							地区
						</th>
						<th align="center" width="100">
							修改时间
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="company" items="${pm.items }" varStatus="status">
						<tr target="id" rel="${company.id }">
							<td>
								${status.index+1}
							</td>
							<td>
								${company.title}
							</td>
							<td>
								${company.peopleCount}
							</td>
							<td>
								<fmt:formatDate value="${company.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
<!-- 							<td> -->
<%-- 								${company.priority} --%>
<!-- 							</td> -->
							<td>
								<c:choose>
									<c:when test="${company.status eq '1' }"> 启用</c:when>
									<c:when test="${company.status eq '0' }"> 禁用</c:when>
									<c:otherwise>删除</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${company.selfBusiness eq '1' }">是</c:when>
									<c:otherwise>否</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${company.saleCompany eq '1' }">是</c:when>
									<c:otherwise>否</c:otherwise>
								</c:choose>
							</td>
							<td>
								${company.region}
							</td>
							<td>
								<fmt:formatDate value="${company.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
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