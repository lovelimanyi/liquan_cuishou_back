<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="xx?myId=${params.myId}" method="post">
	
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
							名称
						</th>
						<th align="center" width="200">
							描述
						</th>
						<th align="center" width="200">
							链接
						</th>
						<th align="center" width="150">
							图片地址
						</th>
						<th align="center" width="150">
							查看图片
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="link" items="${linkList }" varStatus="status">
						<tr target="index" rel="${status.index }">
							<td>
								${status.count}
							</td>
							<td>
								${link.linkName}
							</td>
							<td>
								${link.content}
							</td>
							<td>
								<a href="${link.linkUrl}" target="blank">${link.linkUrl}</a>
							</td>
							<td>
								${link.imgUrl }
							</td>
							<td>
								<a href="<%=path %>${link.imgUrl }" target="_Blank">查看</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	</div>
</form>