<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="auditCenter/fingAllPageList?myId=${params.myId}" method="post">
	<div class="pageHeader">				
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>借  款 编 号: <input type="text" id="loanId" name="loanId" value="${params.loanId}"/></td>
					<td>借款人姓名: <input type="text" id="loanRealName" name="loanRealName" value="${params.loanRealName}"/></td>
					<td>
						创建时间
						<input type="text" name="beginTime" id="beginTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
						到<input type="text" name="endTime" id="endTime" value="${params.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
					</td>
					 <td>
							审核 状 态:
							<select id="status" name="status">
							    <option value="">全部</option>
								<option value="0" <c:if test="${params.status eq '0'}">selected = "selected"</c:if>>待审核</option>
								<option value="1" <c:if test="${params.status eq '1'}">selected = "selected"</c:if>>申请中</option>
								<option value="2" <c:if test="${params.status eq '2'}">selected = "selected"</c:if>> 审核通过 </option>
								<option value="3" <c:if test="${params.status eq '3'}">selected = "selected"</c:if>> 拒绝</option>
								<option value="5" <c:if test="${params.status eq '5'}">selected = "selected"</c:if>> 通过不计入</option>
								<option value="4" <c:if test="${params.status eq '4'}">selected = "selected"</c:if>> 失效</option>
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
	  <jsp:include page="${BACK_URL}/rightSubList">
			<jsp:param value="${params.myId}" name="parentId"/>
		</jsp:include> 
		<table class="table" style="width: 100%;" layoutH="112"
			nowrapTD="false">
			<thead>
				<tr>
					<th align="center" width="50">
						借款编号
					</th>
					<th align="center" width="50">
						借款人姓名
					</th>
					<th align="center" width="50">
						借款人手机号
					</th>
					<th align="center" width="50">
						审核状态
					</th>
					<th align="center" width="80">
						减免金额
					</th>
					<th align="center" width="80">
						创建时间
					</th>
					<th align="center" width="30">
						审核时间
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="order" items="${pm.items}" varStatus="status">
                    <tr target="id" rel="${order.id }">
						<td align="center" width="50">
								${order.loanId}
						</td>
					    <td align="center" width="50">
							 ${order.loanUserName}
						</td>
						<td align="center" width="50">
							${order.loanUserPhone}
						</td>
						<td>
								<c:choose>
									<c:when test="${order.status eq '1'}">申请中</c:when>
									<c:when test="${order.status eq '2'}">通过</c:when>
									<c:when test="${order.status eq '3'}">拒绝</c:when>
									<c:when test="${order.status eq '4'}" >失效</c:when>
									<c:when test="${order.status eq '5'}" >通过不计入</c:when>
									<c:when test="${order.status eq '0'}" >待审核</c:when>
								</c:choose>
							</td>
						<td align="center" width="50">
							${order.reductionMoney}
						</td>
						<td align="center" width="50">
							  <fmt:formatDate value="${order.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<fmt:formatDate value="${order.audittime}" pattern="yyyy-MM-dd HH:mm:ss"/>
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


