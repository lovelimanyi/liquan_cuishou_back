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
				<c:if test="${empty params.roleId}">
					<div layoutH="14"
						style="float: left; display: block; overflow: auto; width: 240px; border: solid 1px #CCC; line-height: 21px; background: #fff">
						<ul class='tree treeFolder collapse'>
							<li>
								${roleList }
							</li>
						</ul>
	
					</div>
				</c:if>
				<div layoutH="14" id="roleBox" class="unitBox"
					style="background: #fff;">
					<form id="pagerForm" onsubmit="return divSearch(this, 'roleBox')"
						method="post" action="xxx?myId=${params.myId}">
						
						<div class="pageContent">
							<jsp:include page="${BACK_URL}/rightSubList">
								<jsp:param value="${params.myId}" name="parentId"/>
							</jsp:include>
							<table class="list" width="100%" layoutH="70">
								<thead>
									<tr>
										<th width="10%" align="center">
											序号
										</th>
										<th width="10%" align="center">
											角色名称
										</th>
										<th width="20%" align="center">
											角色描述
										</th>
										<th width="20%" align="center">
											添加时间
										</th>
										<th width="20%" align="center">
											IPAddress
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${pm.items }" var="role" varStatus="st">
										<tr target="sid_support" rel="${role.id}">
											<th align="center">
												${st.count}
											</th>
											<th align="center">
												${role.roleName }
											</th>
											<th align="center">
												${role.roleSummary }
											</th>
											<th align="center">
												<fmt:formatDate value="${role.roleAddtime  }"
													pattern="yyyy-MM-dd  HH:mm:ss" />
											</th>
											<th align="center">
												${role.roleAddip }
											</th>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<!-- 分页 -->
							<c:set var="page" value="${pm }"></c:set>
							<c:set var="pagedRel" value="roleBox"></c:set>
							<%@ include file="../page.jsp"%>
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
		alertMsg.error(${message});
	}
</script>