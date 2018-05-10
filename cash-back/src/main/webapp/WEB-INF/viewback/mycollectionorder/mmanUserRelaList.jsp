<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="" method="post">
	
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
			
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
						<th align="center" width="20">
							编号
						</th>
						<th align="center" width="20">
							借款用户
						</th>
						<th align="center" width="40">
							联系人类型
						</th>
						<th align="center" width="25">
							联系人关系
						</th>
						<th align="center" width="20">
							联系人姓名
						</th>

						<th align="center" width="30">
							归属地
						</th>
						<th align="center" width="20">
							联系次数
						</th>
						<th align="center" width="20">
							主叫次数
						</th>
						<th align="center" width="20">
							被叫次数
						</th>
					</tr>
				</thead>
				<c:if test="${not empty pm.items }">
					<tbody>
					<c:forEach var="MmanUserRela" items="${pm.items }" varStatus="status">
						<tr target="id" rel="${MmanUserRela.id },${orderId },${MmanUserRela.infoName },${MmanUserRela.infoValue }">
							<td>
								${status.index+1}
							</td>
							<td>
								${MmanUserRela.realName}
							</td>
							<td>
								<c:if test="${MmanUserRela.contactsKey eq '1' }">直系亲属联系人</c:if>
								<c:if test="${MmanUserRela.contactsKey eq '2' }">其他联系人</c:if>
								<c:if test="${MmanUserRela.contactsKey eq '3' }">第一联系人</c:if>
								<c:if test="${MmanUserRela.contactsKey eq '4' }">第二联系人</c:if>
							</td>
							<td>
								<c:if test="${MmanUserRela.contactsKey eq '1' }">
									<c:if test="${MmanUserRela.relaKey eq '1' }">父亲</c:if>
									<c:if test="${MmanUserRela.relaKey eq '2' }">母亲</c:if>
									<c:if test="${MmanUserRela.relaKey eq '3' }">儿子</c:if>
									<c:if test="${MmanUserRela.relaKey eq '4' }">女儿</c:if>
									<c:if test="${MmanUserRela.relaKey eq '5' }">配偶</c:if>
								</c:if>
								<c:if test="${MmanUserRela.contactsKey eq '2' }">
									<c:if test="${MmanUserRela.relaKey eq '1' }">同学</c:if>
									<c:if test="${MmanUserRela.relaKey eq '2' }">亲戚</c:if>
									<c:if test="${MmanUserRela.relaKey eq '3' }">同事</c:if>
									<c:if test="${MmanUserRela.relaKey eq '4' }">朋友</c:if>
									<c:if test="${MmanUserRela.relaKey eq '5' }">其他</c:if>
								</c:if>
							</td>
							<td>
								${MmanUserRela.infoName}
							</td>

							<td>
								${MmanUserRela.phoneNumLoc}
							</td>
							<td>
								${MmanUserRela.callCnt}
							</td>
							<td>
								${MmanUserRela.callOutCnt}
							</td>
							<td>
								${MmanUserRela.callInCnt}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
				<c:set var="page" value="${pm }"></c:set>
				</c:if>
				
				<c:if test="${not empty list }">
				<tbody>
					<c:forEach var="MmanUserRela" items="${list }" varStatus="status">
						<tr target="id" rel="${MmanUserRela.id },${orderId }">
							<td>
								${status.index+1}
							</td>
							<td>
								${MmanUserRela.realName}
							</td>
							<td>
								<c:if test="${MmanUserRela.contactsKey eq '1' }">直系亲属联系人</c:if>
								<c:if test="${MmanUserRela.contactsKey eq '2' }">其他联系人</c:if>
							</td>
							<td>
								<c:if test="${MmanUserRela.contactsKey eq '1' }">
									<c:if test="${MmanUserRela.relaKey eq '1' }">父亲</c:if>
									<c:if test="${MmanUserRela.relaKey eq '2' }">母亲</c:if>
									<c:if test="${MmanUserRela.relaKey eq '3' }">儿子</c:if>
									<c:if test="${MmanUserRela.relaKey eq '4' }">女儿</c:if>
									<c:if test="${MmanUserRela.relaKey eq '5' }">配偶</c:if>
								</c:if>
								<c:if test="${MmanUserRela.contactsKey eq '2' }">
									<c:if test="${MmanUserRela.relaKey eq '1' }">同学</c:if>
									<c:if test="${MmanUserRela.relaKey eq '2' }">亲戚</c:if>
									<c:if test="${MmanUserRela.relaKey eq '3' }">同事</c:if>
									<c:if test="${MmanUserRela.relaKey eq '4' }">朋友</c:if>
									<c:if test="${MmanUserRela.relaKey eq '5' }">其他</c:if>
								</c:if>
							</td>
							<td>
								${MmanUserRela.infoName}
							</td>

							<td>
								${MmanUserRela.phoneNumLoc}
							</td>
							<td>
								${MmanUserRela.callCnt}
							</td>
							<td>
								${MmanUserRela.callOutCnt}
							</td>
							<td>
								${MmanUserRela.callInCnt}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
</form>
<c:if test="${not empty message}">
	<script type="text/javascript">
	alertMsg.error("${message}");
	</script>
</c:if>