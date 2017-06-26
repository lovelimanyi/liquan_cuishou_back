<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="templateSms/getTemplateSmsPage?myId=${params.myId}" method="post">
	
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>

					<td>
						状态:
						<select name="status">
								<option value="">全部</option>
								<option value="1" <c:if test="${params.status eq '1'}">selected="selected"</c:if>>可用</option>
								<option value="0" <c:if test="${params.status eq '0'}">selected="selected"</c:if>>禁用</option>
						</select>
					</td>
					<td>
						状态:
						<select name="msgType">
								<option value="">全部</option>
								<option value="3" <c:if test="${templateSms.msgType eq '3'}">selected="selected"</c:if>>S1组</option>
								<option value="4" <c:if test="${templateSms.msgType eq '4'}">selected="selected"</c:if>>S2组</option>
								<option value="5" <c:if test="${templateSms.msgType eq '5'}">selected="selected"</c:if>>M组</option>
						</select>
					</td>
					<%-- <td>
						创建时间
						<input type="text" name="beginTime" id="beginTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
						到<input type="text" name="endTime" id="endTime" value="${params.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
					</td> --%>
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
							编号
						</th>
						<th align="center" width="50">
							名称
						</th>
						<th align="center" width="50">
							短信类型
						</th>
						<th align="center" width="290">
							短信内容
						</th>
						<th align="center" width="20">
							状态
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="templateSms" items="${pm.items }" varStatus="status">
						<tr target="id" rel="${templateSms.id }">
							<td>
								${status.index+1}
							</td>
							<td>
								${templateSms.name}
							</td>
							<td>
								<c:if test="${templateSms.msgType eq '3'}">S1组</c:if>
								<c:if test="${templateSms.msgType eq '4'}">S2组</c:if>
								<c:if test="${templateSms.msgType eq '5'}">M组</c:if>
							</td>
							<td>
								${templateSms.contenttext}
							</td>
							<td>
								<c:choose>
									<c:when test="${templateSms.status eq '1' }"> 可用</c:when>
									<c:otherwise>禁用</c:otherwise>
								</c:choose>
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