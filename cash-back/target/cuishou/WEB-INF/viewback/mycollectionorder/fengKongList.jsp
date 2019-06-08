<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="fengKong/getFengKongPage?myId=${params.myId}" method="post">
	
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						状态:
						<select name="status">
								<option value="">全部</option>
								<option value="0" <c:if test="${params.status eq '0'}">selected="selected"</c:if>>可用</option>
								<option value="1" <c:if test="${params.status eq '1'}">selected="selected"</c:if>>禁用</option>
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
						<th align="center" width="15">
							编号
						</th>
						<th align="center" width="50">
							风控标签名称
						</th>
						<th align="center" width="50">
							状态
						</th>
						<th align="center" width="50">
							创建时间
						</th>
						<th align="center" width="50">
							修改时间
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="fengKong" items="${pm.items }" varStatus="status">
						<tr target="id" rel="${fengKong.id }">
							<td>
								${status.index+1}
							</td>
							<td>
								${fengKong.fkLabel}
							</td>
							<td>
								<c:choose>
									<c:when test="${fengKong.status eq '0' }"> 可用</c:when>
									<c:otherwise>禁用</c:otherwise>
								</c:choose>
							</td>
							<td align="center" width="50">
							 <fmt:formatDate value="${fengKong.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/> 
							</td>
							<td align="center" width="50">
								 <fmt:formatDate value="${fengKong.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/> 
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