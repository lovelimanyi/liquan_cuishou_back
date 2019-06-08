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
							页面链接
						</th>
						<th align="center" width="150">
							图片地址
						</th>
						<th align="center" width="150">
							查看图片
						</th>
						<th align="center" width="30">
							排序
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="banner" items="${bannerList }" varStatus="status">
						<tr target="index" rel="${status.index }">
							<td>
								${status.count}
							</td>
							<td>
								${banner.httpUrl}
							</td>
							<td>
								${banner.imgUrl }
							</td>
							<td>
								<a href="<%=path %>${banner.imgUrl }" target="_Blank">查看</a>
							</td>
							<td>
								${banner.index}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	</div>
</form>