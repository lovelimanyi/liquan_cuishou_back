<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<form id="pagerForm" onsubmit="return navTabSearch(this);" action="collectionRecordStatusChangeLog/getMmanLoanCollectionStatusChangeLog?myId=${params.myId}" method="post">
	
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						创 建 时 间: 
						<input type="text" id="beginTime" name="beginTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
						至
						<input type="text" id="endTime" name="endTime"  value="${params.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
					</td>
					<td>
						操作员:
						<input type="text" name="operatorName"
							value="${params.operatorName }" />
					</td>
					<td>
						当前催收员:
						<input type="text" name="collectionUser"
							value="${params.collectionUser }" />
					</td>
					<td>
						借款编号:
						<input type="text" name="loanCollectionOrderId"
							value="${params.loanCollectionOrderId }" />
					</td>
					<td>
						操作类型:
						<select name="type" id="type" >
							<option value="0" <c:if test="${'0' eq params.type}">selected = "selected"</c:if>>全部</option>
							<option value="1" <c:if test="${'1' eq params.type}">selected = "selected"</c:if>>入催</option>
							<option value="2" <c:if test="${'2' eq params.type}">selected = "selected"</c:if>>逾期等级转换</option>
							<option value="3" <c:if test="${'3' eq params.type}">selected = "selected"</c:if>>转单</option>
							<option value="4" <c:if test="${'4' eq params.type}">selected = "selected"</c:if>>委外</option>
							<option value="5" <c:if test="${'5' eq params.type}">selected = "selected"</c:if>>催收完成</option>
							
							<%-- <option value="6" <c:if test="${'6' eq params.type}">selected = "selected"</c:if>>取消委外</option>
							<option value="7" <c:if test="${'7' eq params.type}">selected = "selected"</c:if>>委外成功</option>
							<option value="8" <c:if test="${'8' eq params.type}">selected = "selected"</c:if>>月初分组</option>
							<option value="9" <c:if test="${'9' eq params.type}">selected = "selected"</c:if>>回收</option>
							<option value="100" <c:if test="${'100' eq params.type}">selected = "selected"</c:if>>催收完成</option> --%>
						</select>
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
		<%-- <jsp:include page="${BACK_URL}/rightSubList">
			<jsp:param value="${params.myId}" name="parentId"/>
		</jsp:include> --%>
			<table class="table" style="width: 100%;" layoutH="85"
				nowrapTD="false">
				<thead>
					<tr>
						<th align="center" width="40">
							序号
						</th>
						<th align="center" width="50">
							借款编号
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
						<!-- <th align="center" width="100">
							催收公司
						</th> -->
						<th align="center" width="50">
							催收组
						</th>
						<th align="center" width="50">
							当前催收员
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
					</tr>
				</thead>
				<tbody>
					<c:forEach var="log" items="${pm.items }" varStatus="status">
						<tr target="recordId" rel="${record.id }">
							<td>
								${status.count}
							</td>
							<td>
								${log.loanCollectionOrderId}
							</td>
							<td>
								<%-- ${log.beforeStatus } --%>
								<%-- <c:if test="${log.beforeStatus == 0}">待催收</c:if>
								<c:if test="${log.beforeStatus == 1}">催收中</c:if>
								<c:if test="${log.beforeStatus == 2}">承诺还款</c:if>
								<c:if test="${log.beforeStatus == 3}">待催收</c:if>
								<c:if test="${log.beforeStatus == 4}">催收成功</c:if> --%>
								${orderStatusMap[log.beforeStatus] }
								<%--<c:if test="${log.beforeStatus == 7}">减免审核成功</c:if>
								<c:if test="${log.beforeStatus == 8}">减免审核拒绝</c:if>
								<c:if test="${log.beforeStatus == 6}">减免申请中</c:if>--%>
							</td>
							<td>
								<%-- ${log.afterStatus } --%>
								<%-- <c:if test="${log.afterStatus == 0}">待催收</c:if>
								<c:if test="${log.afterStatus == 1}">催收中</c:if>
								<c:if test="${log.afterStatus == 2}">承诺还款</c:if>
								<c:if test="${log.afterStatus == 3}">待催收</c:if>
								<c:if test="${log.afterStatus == 4}">催收成功</c:if> --%>
								${orderStatusMap[log.afterStatus] }
							</td>
							<td >
								<%-- <c:if test="${log.type == '1'}">入催</c:if>
								<c:if test="${log.type == '2'}">逾期等级转换</c:if>
								<c:if test="${log.type == '3'}">转单</c:if>
								<c:if test="${log.type == '4'}">委外</c:if>
								<c:if test="${log.type == '5'}">催收完成</c:if> --%>
								${collectionStatusMoveTypeMap[log.type] }
							</td>
							<%-- <td>
								${log.companyId }
							</td> --%>
							<td>
								<%-- <c:if test="${log.currentCollectionUserLevel == '3' }">S1组</c:if>
								<c:if test="${log.currentCollectionUserLevel == '4' }">S2组</c:if>
								<c:if test="${log.currentCollectionUserLevel == '5' }">M1-M2组</c:if>
								<c:if test="${log.currentCollectionUserLevel == '6' }">M2-M3组</c:if>
								<c:if test="${log.currentCollectionUserLevel == '7' }">M3+组</c:if> --%>
								<%-- ${log.currentCollectionUserLevel } --%>
								${dictMap[log.currentCollectionUserLevel] }
							</td>
							<td>
								${log.currentCollectionUserId }
							</td>
							<td>
								<%-- <c:if test="${log.currentCollectionOrderLevel == '3' }">s1组</c:if>
								<c:if test="${log.currentCollectionOrderLevel == '4' }">s2组</c:if>
								<c:if test="${log.currentCollectionOrderLevel == '5' }">m1-m2组</c:if>
								<c:if test="${log.currentCollectionOrderLevel == '6' }">m2-m3组</c:if>
								<c:if test="${log.currentCollectionOrderLevel == '7' }">m3+组</c:if> --%>
								<%-- ${log.currentCollectionOrderLevel } --%>
								${dictMap[log.currentCollectionOrderLevel] }
							</td>
							<td>
								<fmt:formatDate value="${log.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/> 
							</td>
							<td>
								${log.operatorName }
							</td>
							<td>
								${log.remark }
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:set var="page" value="${pm}"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
</form>