<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<form id="pagerForm" onsubmit="return navTabSearch(this);" action="collectionOrder/getContactInfo?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>联系人电话: <input type="text" id="phoneNum" name="phoneNum" value="${params.phoneNum }"/></td>
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
		<%-- <jsp:include page="${BACK_URL}/rightSubList">
			<jsp:param value="${params.myId}" name="parentId"/>
		</jsp:include> --%>  
		<table class="table" style="width: 100%;" layoutH="160"
			nowrapTD="false">
			<thead>
				<tr>
<!-- 				    <th align="center" width="10"> -->
<!-- 				      <input type="checkbox" id="checkAlls" onclick="checkAll(this);"/> -->
<!-- 				    </th> -->
				    <th align="center" width="50">
						借款人姓名
					</th>
				    <th align="center" width="50">
						借款人电话
					</th>
				    <th align="center" width="50">
						联系人姓名
					</th>
					<th align="center" width="50">
						联系人电话
					</th>
					<th align="center" width="50">
						联系人类型
					</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach var="info" items="${contactInfo}" varStatus="status">
					<%-- <tr target="id" rel="${info.id }"> --%>
					<tr>
					    <td align="center" width="50">
							 ${info.loanUserName}
						</td>
						<td align="center" width="50">
							 ${info.loanUserPhone}
						</td>
						<td align="center" width="50">
							 ${info.contactUserName}
						</td>
						<td align="center" width="50">
							 ${info.contactUserPhone}
						</td>
						<td align="center" width="50">
							<c:if test="${info.contactUserType eq '1'}">直系亲属联系人</c:if>
							<c:if test="${info.contactUserType eq '2'}">其他联系人</c:if>
							<c:if test="${info.contactUserType eq '' or info.contactUserType eq null}">通讯录备份联系人</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="contactInfo" value="${contactInfo }"></c:set>
		<!-- 分页 -->
		<%@ include file="/WEB-INF/viewback/page.jsp"%>
	</div>
</form>

