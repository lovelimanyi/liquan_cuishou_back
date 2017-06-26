<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="msg/findGroupList?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						手机:
						<input type="text" name="userPhone" value="${params.userPhone }"/>
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
		<table class="table" style="width: 100%;" layoutH="112"
			nowrapTD="false">
			<thead>
				<tr>
					<th align="center" width="50">
						序号
					</th>
					<th align="center" width="100">
						用户名
					</th>
					<th align="center" width="80">
						手机号
					</th>
					<th align="center" width="50">
						内容
					</th>
					<th align="center" width="100">
						发送时间
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="msg" items="${pm.items }" varStatus="status">
					<tr target="id" rel="${msg.id}" >
						<td>
							${status.count}
						</td>
						<td>
							${msg.userName}
						</td>
						<td>
							${msg.userPhone}
						</td>
						<td>
							${msg.smsContent}
						</td>
						<td>
							 <fmt:formatDate value="${msg.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
	<c:if test="${not empty message}">
		<script type="text/javascript">
			alertMsg.error("${message}");
		</script>
	</c:if>
</form>