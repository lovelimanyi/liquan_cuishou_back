<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="user/getUserPage?myId=${params.myId}" method="post">
	
	<div class="pageContent">
		<jsp:include page="${BACK_URL}/rightSubList">
			<jsp:param value="${params.myId}" name="parentId"/>
		</jsp:include>
			<table class="table" style="width: 100%;" layoutH="112" nowrapTD="false">
				<thead>
					<tr>
						<th align="center" width="50">
							序号
						</th>
						<th align="center" width="200">
							标题
						</th>
						<th align="center" width="150">
							内容
						</th>
						<th align="center" width="30">
							更多链接
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="notice" items="${noticeList }" varStatus="status">
						<tr target="index" rel="${status.index }">
							<td>
								${status.count}
							</td>
							<td>
								${notice.titleName}
							</td>
							<td>
								${notice.content }
							</td>
							<td>
								${notice.moreUrl}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	</div>
</form>