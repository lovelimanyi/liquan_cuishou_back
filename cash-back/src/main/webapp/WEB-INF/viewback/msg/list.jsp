<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="msg/findList?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
 					<td>
						标题：<input type="text" name="messageTitle" value="${params.messageTitle }"/>
					</td>
					<td>
						消息类型:
						<select name="noticeTypeId">
							<option value="" >全部</option>
							<c:forEach var="type" items="${NOTICE_TYPE }">
							<option value="${type.key }" <c:if test="${params.noticeTypeId eq type.key }">selected="selected"</c:if>>${type.value }</option>
							</c:forEach>
						</select>
					</td>
					<td>
						收信邮箱或手机:
						<input type="text" name="messageAddress" value="${params.messageAddress }"/>
					</td>
					 <td> 
 						收信人账号: 
 						<input type="text" name="receiveAccount" value="${params.receiveAccount}"/> 
 					</td> 
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
						<input type="checkbox" group="ids" class="checkboxCtrl">
					</th>
					<th align="center" width="50">
						序号
					</th>
					<th align="center" width="100">
						标题
					</th>
					<th align="center" width="80">
						内容
					</th>
					<th align="center" width="50">
						发送人
					</th>
					<th align="center" width="60">
						接收人
					</th>
					<th align="center" width="60">
						接收地址
					</th>
					<th align="center" width="60">
						消息状态
					</th>
					<th align="center" width="100">
						发送时间
					</th>
					<th align="center" width="100">
						发送IP
					</th>
					<th align="center" width="100">
						消息类型
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="msg" items="${pm.items }" varStatus="status">
					<tr target="id" rel="${msg.id}" onclick="rowClick(${msg.id })">
						<td>
							<input name="ids" id="msg${msg.id }" value="${msg.id}" onclick="rowClick(${msg.id })" type="checkbox">
						</td>
						<td>
							${status.count}
						</td>
						<td>
							${msg.messageTitle}
						</td>
						<td>
							${msg.messageContent}
						</td>
						<td>
							${msg.sendUser.userAccount}
						</td>
						<td>
							 ${msg.receiveUser.userAccount}
						</td>
						<td>
							${msg.messageAddress}
						</td>
						<td>
							${msg.viewMsgStatus}
						</td>
						<td>
							 <fmt:formatDate value="${msg.messageSendDatetime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${msg.messageSendIp}
						</td>
						<td>
							${msg.viewNoticeType}
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
		<script type="text/javascript">
		function rowClick(id){
			if($("#msg"+id).attr("disabled")!="disabled"){
				var cked = $("#msg"+id).attr("checked");
				if(cked == "checked"){
					$("#msg"+id).removeAttr("checked");
				}else{
					$("#msg"+id).attr("checked","checked");
				}
			}
		}
		</script>
</form>