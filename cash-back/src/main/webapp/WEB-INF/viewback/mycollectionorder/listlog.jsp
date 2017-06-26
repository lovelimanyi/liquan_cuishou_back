<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<div class="pageContent">
		<table class="table" style="width: 100%;" layoutH="85" nowrapTD="false">
				<thead>
					<tr>
						<th align="center" width="50">
							序号
						</th>
						<th align="center" width="50">
							操作前状态
						</th>
						<th align="center" width="50">
							操作后状态
						</th>
						<th align="center" width="50">
							操作类型
						</th>
						<th align="center" width="50">
							催收组
						</th>
						<th align="center" width="50">
							订单组
						</th>
						<th align="center" width="100">
							创建时间
						</th>
						<th align="center" width="50">
							操作人
						</th>
						<th align="center" width="200">
							操作备注
						</th>
						<th align="center" width="50">
							当前催收员
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="log" items="${listlog}" varStatus="status">
						<tr target="recordId" rel="${record.id }">
							<td>
								${status.count}
							</td>
							<td>
								${StatuMap[log.beforeStatus] }
							</td>
							<td>
								${StatuMap[log.afterStatus] }
							</td>
							<td >
								<c:if test="${log.type == '1'}">入催</c:if>
								<c:if test="${log.type == '2'}">派单</c:if>
								<c:if test="${log.type == '3'}">逾期等级转换</c:if>
								<c:if test="${log.type == '4'}">转单</c:if>
								<c:if test="${log.type == '5'}">委外</c:if>
								<c:if test="${log.type == '6'}">取消委外</c:if>
								<c:if test="${log.type == '7'}">委外成功</c:if>
								<c:if test="${log.type == '8'}">月初分组</c:if>
								<c:if test="${log.type == '9'}">回收(重置催收状态为待催收)</c:if>
								<c:if test="${log.type == '100'}">催收完成</c:if>
							</td>
							<td>
								${group[log.currentCollectionUserLevel] }
							</td>
							<td>
								${group[log.currentCollectionOrderLevel] }
							</td>
							<td>
								<fmt:formatDate value="${log.createDate }" pattern="yyyy MM dd HH:mm:ss"/> 
							</td>
							<td>
								${log.operatorName }
							</td>
							<td>
								${log.remark }
							</td>
							<td>
								${log.currentCollectionUserId }
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
</div>
