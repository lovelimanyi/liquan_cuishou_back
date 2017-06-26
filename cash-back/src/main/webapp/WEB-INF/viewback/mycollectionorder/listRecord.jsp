<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<div class="pageContent">
	<table class="table" style="width: 100%;" layoutH="115"
				nowrapTD="false">
				<thead>
					<tr>
						<th align="center" width="50">
							序号
						</th>
						<!-- <th align="center" width="50">
							催收员姓名
						</th> -->
						<th align="center" width="60">
							借款编号
						</th>
						<th align="center" width="100"> 
							联系人姓名 
						</th> 
						<th align="center" width="100">
							联系人类型
						</th>
						<th align="center" width="100">
							关系
						</th>
						<th align="center" width="100">
							联系人电话
						</th>
						<th align="center" width="100">
							跟进等级
						</th>
						<th align="center" width="120">
							操作时催收状态
						</th>
						<th align="center" width="120">
							催收类型
						</th>
						<th align="center" width="120">
							催收内容
						</th>
						<th align="center" width="120">
							创建时间
						</th>
					</tr>
				</thead>
				<tbody>
				
					<c:forEach var="record" items="${listRecord}" varStatus="status">
						<tr target="recordId" rel="${record.id }">
							<td>
								${status.count}
							</td>
							<td>
								${record.userId}
							</td>
							<td>
								${record.contactName }
							</td>
							<td >
								<c:if test="${record.contactType == '1'}">紧急联系人</c:if>
								<c:if test="${record.contactType == '2'}">通讯录联系人</c:if>
							</td>
							<td>
								${record.relation }
							</td>
							<td>
								${record.contactPhone }
							</td>
							<td>
								${levelMap[record.stressLevel]}
							</td>
							<td>
								<c:if test="${record.orderState == '0'}">待催收</c:if>
								<c:if test="${record.orderState == '1'}">催收中</c:if>
								<c:if test="${record.orderState == '2'}">承诺还款</c:if>
								<c:if test="${record.orderState == '3'}">待催收</c:if>
								<c:if test="${record.orderState == '4'}">催收成功</c:if>
								<c:if test="${record.orderState == '5'}">催收成功</c:if>
								
							</td>
							<td>
								<c:if test="${record.collectionType == '1'}">电话催收</c:if>
								<c:if test="${record.collectionType == '2'}">短信催收</c:if>
							</td>
							<td>
								${record.content }
							</td>
							<td>
								<fmt:formatDate value="${record.createDate }"	pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
</div>