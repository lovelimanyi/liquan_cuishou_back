<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="sysAlertMsg/getAlertMsgPage?myId=${params.myId}" method="post">
	
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						创建时间
						<input type="text" name="beginTime" id="beginTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
						到<input type="text" name="endTime" id="endTime" value="${params.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
					</td>
					<td>
						状态
						<select name="status">
							<option value="">全部</option>
							<option value="1" <c:if test="${params.status eq '1'}">selected="selected"</c:if>>已处理</option>
							<option value="0" <c:if test="${params.status eq 0}">selected="selected"</c:if>>未处理</option>
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
						<th align="center" width="100">
							编号
						</th>
						<th align="center" width="200">
							标题
						</th>
						<th align="center" width="100">
							类型
						</th>
						<th align="center" width="500">
							内容
						</th>
						<th align="center" width="200">
							创建时间
						</th>
						<th align="center" width="200">
							修改时间
						</th>
						<th width="100">
							状态
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="alertmsg" items="${pm.items }" varStatus="status">
						<tr target="id" rel="${alertmsg.id }">
							<td>
								${status.index+1}
							</td>
							<td>
								${alertmsg.title}
							</td>
							<td>
								<c:choose>
									<c:when test="${alertmsg.type eq '0'}">派单</c:when>
									<c:otherwise>--</c:otherwise>
								</c:choose>
							</td>
							<td>
								${alertmsg.content}
							</td>
							<td>
								<fmt:formatDate value="${alertmsg.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<fmt:formatDate value="${alertmsg.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<c:choose>
									<c:when test="${alertmsg.status eq 1}">已读</c:when>
									<c:otherwise>未读</c:otherwise>
								</c:choose>
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