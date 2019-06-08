<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="infoMsg/findList?myId=${params.myId}" method="post">
	
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>

					<td>
						手机号:
						<input type="text" name="userPhone" value="${params.userPhone }"/>
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
							序号
						</th>
						<th align="center" width="50">
							用户名称
						</th>
						<th align="center" width="50">
							用户手机号
						</th>
						<th align="center" width="290">
							短信内容
						</th>
						<th align="center" width="150">
							创建时间
						</th>
						<th align="center" width="20">
							借款编号
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="infoSms" items="${pm.items }" varStatus="status">
						<tr target="id" rel="${templateSms.id }">
							<td>
								${status.index+1}
							</td>
							<td>
								${infoSms.userName}
							</td>
							<td>
								${infoSms.userPhone}
							</td>
							<td>
								${infoSms.smscontent}
							</td>
							<td>
								 <fmt:formatDate value="${infoSms.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								${infoSms.loanOrderId}
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