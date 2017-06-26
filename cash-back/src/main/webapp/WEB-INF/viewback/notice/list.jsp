<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="notice/findList?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
 					<td>
							状态：
							<select name="noticeStatus">
								<option value="">全部</option>
								<c:forEach var="notice" items="${ALL_NOTICE_STATUS}">
									<option value="${notice.key}" <c:if test="${notice.key eq params.noticeStatus}">selected="selected"</c:if> >${notice.value}</option>
								</c:forEach>
							</select>
						</td>
						<td>
							名称：
							<input type="text" name="noticeTitle" value="${params.noticeTitle }"/>
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
						名称
					</th>
					<th align="center" width="80">
						编码
					</th>
					<th align="center" width="50">
						状态
					</th>
					<th align="center" width="60">
						站内信
					</th>
					<th align="center" width="60">
						邮箱
					</th>
					<th align="center" width="60">
						手机短信
					</th>
					<th align="center" width="100">
						添加时间
					</th>
					<th align="center" width="100">
						最后更新时间
					</th>
					<th align="center" width="100">
						排序
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="notice" items="${pm.items }" varStatus="status">
					<tr target="id" rel="${notice.id }">
						<td>
							${status.count}
						</td>
						<td>
							${notice.noticeTitle}
						</td>
						<td>
							${notice.noticeCode}
						</td>
						<td>
							${notice.viewNoticeStatus}
						</td>
						<td>
							 ${notice.viewMessage}
						</td>
						<td>
							${notice.viewEmail}
						</td>
						<td>
							${notice.viewPhone}
						</td>
						<td>
							 <fmt:formatDate value="${notice.noticeAddtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							 <fmt:formatDate value="${notice.noticeUpdatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${notice.noticeSequence}
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