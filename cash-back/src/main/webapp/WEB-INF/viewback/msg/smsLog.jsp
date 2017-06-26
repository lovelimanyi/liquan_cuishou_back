<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="msg/findSmsLog?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						短信商:
						<input type="text" name="smsWay" value="${params.smsWay }"/>
					</td>
					<td>
						签名:
						<input type="text" name="smsLog" value="${params.smsLog }"/>
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
						记录
					</th>
					<th align="center" width="80">
						时间
					</th>
					<th align="center" width="50">
						签名
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="log" items="${pm.items }" varStatus="status">
					<tr>
						<td>
							${status.count}
						</td>
						<td>
							${log.smsLog}
						</td>
						<td>
							 <fmt:formatDate value="${log.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${log.smsWay}
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