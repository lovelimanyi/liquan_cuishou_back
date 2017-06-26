<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
	<div class="pageContent" style="">
	<div class="tabs">	
		<div class="tabsContent">
			<div>
				<c:if test="${empty params.moduleId}">
					<div layoutH="14" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">      				       
					    <ul class='tree treeFolder collapse'>
					    <li>
					    	 ${ModuleList }
					     </li>
					     </ul>
					     
					</div>
				</c:if>
				<div layoutH="14" id="moduleBox" class="unitBox" style="background:#fff;">
					<form id="pagerForm" onsubmit="return divSearch(this, 'moduleBox')" 
					action="xxx?myId=${params.myId}" method="post">
					
					<div class="pageContent">
						<jsp:include page="${BACK_URL}/rightSubList">
							<jsp:param value="${params.myId}" name="parentId"/>
						</jsp:include>
						<table class="list" width="100%" layoutH="70">
							<thead>
								<tr>
									<th width="10%"  align="center">序号</th>
									<th width="10%" align="center">菜单名称</th>
									<th width="20%" align="center">菜单路径</th>
									<th width="20%" align="center">菜单描述</th>
									<th width="20%" align="center">排序</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pm.items }" var="rightModule" varStatus="st">
									<tr target="sid_support" rel="${rightModule.id}">
										<th  align="center">
										  ${st.count }
										</th>
										<th  align="center">
										   	${rightModule.moduleName }
										</th>
										<th  align="center">
										   	${rightModule.moduleUrl }
										</th>
										<th  align="center">
										   	${rightModule.moduleDescribe }
										</th>
										<th  align="center">
										   	${rightModule.moduleSequence }
										</th>
									</tr>
									</c:forEach>
							</tbody>
						</table>
						<!-- 分页 -->
						<c:set var="page" value="${pm }"></c:set>
						<c:set var="pagedRel" value="moduleBox"></c:set>
						<%@ include file="../page.jsp" %>
					</div>
					</form>
				</div>
				
			</div>
			
			
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>
<script type="text/javascript">
	if("${message}"){
		alertMsg.error("${message}");
	}
</script>