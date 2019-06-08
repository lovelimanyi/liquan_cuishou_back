<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="msg/findSmsUserList?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						开始行:
						<input type="text" name="bg" value="${params.bg }" onkeyup="this.value=this.value.replace(/\D/g,'')" />
					</td>
					<td>
						结束行:
						<input type="text" name="ed" value="${params.ed }" onkeyup="this.value=this.value.replace(/\D/g,'')" />
					</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
						序号
					</th>
					<th align="center" width="100">
						用户名
					</th>
					<th align="center" width="80">
						手机号
					</th>
					<!--<th align="center" width="50">
						内容
					</th>
					--><th align="center" width="100">
						发送时间
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="msg" items="${pm.items }" varStatus="status">
					<tr target="id" rel="${msg.id}" >
						<td>
							${status.count}
						</td>
						<td>
							${msg.userName}
						</td>
						<td>
							${msg.userPhone}
						</td>
						<!--<td>
							${msg.smsContent}
						</td>-->
						<td>
							 <fmt:formatDate value="${msg.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
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
		function toExport(obj){
				var href = $(obj).attr("href");
				var bg = $("input[name='bg']").val();
				var ed = $("input[name='ed']").val();
				
				$(obj).attr("href",href+"&bg="+bg+"&ed="+ed);
			} 
		</script>
</form>