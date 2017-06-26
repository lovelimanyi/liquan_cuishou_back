<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="content/findList?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>

					<td>
						栏目:
						<select name="channelType">
							<option value="">全部</option>
						<c:forEach items="${CHANNEL}" var="channel">
							<option value="${channel.key }"
							<c:if test="${channel.key eq params.channelType }">selected="selected"</c:if>
							>${channel.value }</option>
						</c:forEach>
					</select>
					</td>
					<td>
						标题:
						<input type="text" name="contentTitle"
							value="${params.contentTitle }" />
					</td>
					<td>
						文章:
						<input type="text" name="contentTxt"
							value="${params.contentTxt }" />
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
			<table class="table" style="width: 100%;" layoutH="112" nowrapTD="false">
				<thead>
					<tr>
					<th align="center" width="50">序号</th>
					<th align="center" width="100">标题</th>
					<th align="center" width="100">摘要</th>
<!--					<th align="center" width="120" >内容</th>-->
					<th align="center" width="120" >发布人</th>
					<th align="center" width="150">发布时间</th>
					<th align="center" width="150">IP</th>
					<th align="center" width="150">修改人</th>
					<th align="center" width="150">栏目</th>
					<th align="center" width="100">浏览量</th>
					<th align="center" width="100">最后更新时间</th>
					<th align="center" width="100">备注</th>
					<th align="center" width="150">排序</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="content" items="${pm.items }" varStatus="status">
						<tr target="id" rel="${content.id }">
							<td >${status.count}</td>
							<td >${content.contentTitle }</td>
							<td >${content.contentSummary }</td>
<!--							<td >${content.contentTxt }</td>-->
							<td >${content.addUser.userAccount }</td>
							<td ><fmt:formatDate value="${content.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td >${content.addIp }</td>
							<td >${content.updateUser.userAccount }</td>
							<td >${CHANNEL[content.channelType] }</td>
							<td >${content.viewCount }</td>
							<td ><fmt:formatDate value="${content.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td >${content.remark }</td>
							<td >${content.orderNum  }</td>
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