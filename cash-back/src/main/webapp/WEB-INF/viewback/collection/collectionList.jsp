<%-- <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
						催收时间:
						<input type="text" name=""
							value="" />
						至
						<input type="text" name=""
							value="" />
						派单时间:
						<input type="text" name=""
							value="" />
						至
						<input type="text" name=""
							value="" />
					</td>
						
					<td>
						逾期天数:
						<input type="text" name=""
							value="" />
						至
						<input type="text" name=""
							value="" />
					</td>
					<td>
						承诺还款时间:
						<input type="text" name=""
							value="" />
						至
						<input type="text" name=""
							value="" />
					</td>
					<td>
						借款人姓名:
						<input name="type" name="" value="" />
					</td>
					<td>
						借款人手机:
						<input name="type" name="" value="" />
					</td>
					<td>
						催收人姓名:
						<input name="type" name="" value="" />
					</td>
					<td>
						借款编号:
						<input name="type" name="" value="" />
					</td>
					<td>
						催收公司:
						<input name="type" name="" value="" />
					</td>
					<td>
						催收组:
						<input name="type" name="" value="" />
					</td>
					<td>
						催收状态:
						<input name="type" name="" value="" />
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
			<table class="table" style="width: 100%;" layoutH="112"
				nowrapTD="false">
				<thead>
					<tr>
						<th align="center" width="50">
							借款编号
						</th>
						<th align="center" width="100">
							催收公司
						</th>
						<th align="center" width="50">
							催收组
						</th>
						<th align="center" width="50">
							借款人姓名
						</th>
						<th align="center" width="50">
							身份证号码
						</th>
						<th align="center" width="100">
							借款人手机号
						</th>
						<th align="center" width="50">
							借款金额
						</th>
						<th align="center" width="50">
							逾期天数
						</th>
						<th align="center" width="50">
							滞纳金
						</th>
						<th align="center" width="50">
							催收状态
						</th>
						<th align="center" width="50">
							应还时间
						</th>
						<th align="center" width="50">
							还款状态
						</th>
						<th align="center" width="50">
							已还金额
						</th>
						<th align="center" width="50">
							最新还款时间
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="log" items="${pm.items }" varStatus="status">
						<tr target="recordId" rel="${record.id }">
							<td>
								${status.count}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:set var="page" value="${pm}"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
</form>======= --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="collection/getCollectionPage?myId=${params.myId}" method="post">
	
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						手机号
						<input type="text" name="userMobile" value="${params.userMobile }" />
					</td>
					<td>
						姓名
						<input type="text" name="userName" value="${params.userName }" />
					</td>
					<td>
						催收组
						<select name="groupLevel">
								<option value="">全部</option>
								<c:forEach items="${groupNameMap}" var="map">
								  	<option value="${map.key}" <c:if test="${params.groupLevel eq map.key}">selected="selected"</c:if> >${map.value}</option>  
							     </c:forEach>  
						</select>
					</td>
					<td>
						催收公司
						<select name="companyId">
								<option value="">全部</option>
								<c:forEach var="company" items="${companyList}" varStatus="status">
									<option value="${company.id}" <c:if test="${params.companyId eq company.id}">selected="selected"</c:if>>${company.title}</option>
								</c:forEach>
						</select>
					</td>
					<td>
						催收员状态
						<select name="userStatus">
								<option value="">全部</option>
								<c:forEach var="statuss" items="${groupStatusMap}" varStatus="status">
									<option value="${statuss.key}" <c:if test="${params.userStatus eq statuss.key}">selected="selected"</c:if>>${statuss.value}</option>
								</c:forEach>
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
							编号
						</th>
						<th align="center" width="50">
							登录名称
						</th>
						<th align="center" width="30">
							姓名
						</th>
						<th align="center" width="100">
							手机号
						</th>
						<th align="center" width="100">
							邮箱
						</th>
						<th align="center" width="100">
							公司
						</th>
						<th align="center" width="50">
							催收组
						</th>
						<th align="center" width="30">
							状态
						</th>
						<th align="center" width="30">
							添加时间
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="collection" items="${pm.items }" varStatus="status">
						<tr target="id" rel="${collection.id }">
							<td>
								${status.index+1}
							</td>
							<td>
								${collection.userAccount}
							</td>
							<td>
								${collection.userName}
							</td>
							<td>
								${collection.userMobile}
							</td>
							<td>
								${collection.userEmail}
							</td>
							<td>
								${collection.companyName}
							</td>
							<td>
								${groupNameMap[collection.groupLevel]}
							</td>
							<td>
								<c:choose>
									<c:when test="${collection.userStatus eq 1}">
										启用
									</c:when>
									<c:when test="${collection.userStatus eq 0}">
										禁用
									</c:when>
									<c:otherwise>
										删除
									</c:otherwise>
								</c:choose>
																
							</td>
							<td>
								<fmt:formatDate value="${collection.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
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
